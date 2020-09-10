package ru.ifmo.rain.zagretdinov.helloAsyncExample;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import static ru.ifmo.rain.zagretdinov.helloAsyncExample.Monitoring.*;


/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class AsyncHelloServer implements Runnable {
    private static final AtomicInteger clients = new AtomicInteger();
    private static final AtomicInteger active = new AtomicInteger();
    private static final AtomicInteger requests = new AtomicInteger();
    private static final AtomicLong received = new AtomicLong();
    private static final AtomicLong sent = new AtomicLong();

    private static final AsynchronousChannelGroup group;
    static {
        try {
            group = AsynchronousChannelGroup.withFixedThreadPool(10, Executors.defaultThreadFactory());
        } catch (final IOException e) {
            throw new AssertionError(e);
        }
    }

    private static class Client {
        private final AsynchronousSocketChannel channel;
        private final ByteBuffer input = ByteBuffer.allocateDirect(10);
        private final Queue<ByteBuffer> output = new ArrayDeque<>();
        private byte[] bytes = new byte[10];
        private int total = 0;

        public Client(final AsynchronousSocketChannel channel) {
            this.channel = channel;
            active.incrementAndGet();
        }

        public void read(final int read) {
            if (read == -1) {
                active.decrementAndGet();
                return;
            }
            input.flip();
            for (int i = 0; i < input.limit(); i++) {
                append(input.get());
            }
            input.clear();
            nextRead();
        }

        private void append(final byte b) {
            if (b == 0) {
                final String request = new String(bytes, 0, total);
//                System.out.println("Server:  " + request);
                requests.incrementAndGet();
                final byte[] response = ("Hello, " + request).getBytes(StandardCharsets.UTF_8);
                send(ByteBuffer.wrap(response));
                send(ByteBuffer.wrap(new byte[1]));
                total = 0;
            } else {
                if (bytes.length == total) {
                    final byte[] newBytes = new byte[bytes.length * 2];
                    System.arraycopy(bytes, 0, newBytes, 0, bytes.length);
                    bytes = newBytes;
                }
                bytes[total++] = b;
            }
        }

        private synchronized void send(final ByteBuffer buffer) {
            output.add(buffer);
            if (output.size() == 1) {
                nextWrite();
            }
        }

        private void nextWrite() {
            channel.write(output.peek(), this, CLIENT_WRITE_HANDLER);
        }

        public void nextRead() {
            channel.read(input, this, CLIENT_READ_HANDLER);
        }

        public synchronized void write(final int write) {
            if (output.peek().remaining() == 0) {
                output.poll();
            }
            if (!output.isEmpty()) {
                nextWrite();
            }
        }
    }

    private static final CompletionHandler<AsynchronousSocketChannel, AsynchronousServerSocketChannel> SERVER_HANDLER =
            new CompletionHandler<AsynchronousSocketChannel, AsynchronousServerSocketChannel>() {
                @Override
                public void completed(final AsynchronousSocketChannel channel, final AsynchronousServerSocketChannel serverChannel) {
                    serverChannel.accept(serverChannel, this);
                    clients.incrementAndGet();
                    new Client(channel).nextRead();
                }

                @Override
                public void failed(final Throwable e, final AsynchronousServerSocketChannel channel) {
                    exception(e);
                    io(channel::close);
                }
            };

    public static final CompletionHandler<Integer, Client> CLIENT_READ_HANDLER = new CompletionHandler<Integer, Client>() {
        @Override
        public void completed(final Integer result, final Client client) {
            if (result != -1) {
                received.addAndGet(result);
            }
            client.read(result);
        }

        @Override
        public void failed(final Throwable exc, final Client attachment) {
            exception(exc);
        }
    };

    public static final CompletionHandler<Integer, Client> CLIENT_WRITE_HANDLER = new CompletionHandler<Integer, Client>() {
        @Override
        public void completed(final Integer result, final Client client) {
            sent.addAndGet(result);
            client.write(result);
        }

        @Override
        public void failed(final Throwable exc, final Client attachment) {
            exception(exc);
        }
    };

    private final int port;

    public AsyncHelloServer(final int port) {
        this.port = port;
    }

    @Override
    public void run() {
        io(() -> {
            final AsynchronousServerSocketChannel serverChannel = AsynchronousServerSocketChannel.open(group);
            serverChannel.bind(new InetSocketAddress(port), 1000);
            serverChannel.accept(serverChannel, SERVER_HANDLER);
        });
    }

    public static void main(final String[] args) throws IOException {
        new AsyncHelloServer(Integer.parseInt(args[0])).run();
        monitor(
                indicator("clients", clients),
                indicator("active", active),
                indicator("requests", active),
                indicator("received", received),
                indicator("sent", sent)
        );
    }
}
