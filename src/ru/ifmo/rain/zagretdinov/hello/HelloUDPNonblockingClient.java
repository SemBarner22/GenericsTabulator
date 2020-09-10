package ru.ifmo.rain.zagretdinov.hello;

import info.kgeorgiy.java.advanced.hello.HelloClient;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

public class HelloUDPNonblockingClient implements HelloClient {

    private static final String MAIN_USAGE = "Usage: HelloUDPNonblockingClient <host> <port> <prefix> <threads> <requests>";
    private static final long SELECT_TIMEOUT = 200;
    private static int bufferSize;
    int requests;

    @Override
    public void run(String host, int port, String prefix, int threads, int requests) {
        this.requests = requests;
        InetSocketAddress socketAddress = null;
        try {
            socketAddress = new InetSocketAddress(InetAddress.getByName(host), port);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        Selector selector;
        try {
            selector = Selector.open();
            for (int threadIndex = 0; threadIndex < threads; threadIndex++) {
                DatagramChannel datagramChannel;
                datagramChannel = DatagramChannel.open();
                datagramChannel.setOption(StandardSocketOptions.SO_REUSEADDR, true);
                datagramChannel.configureBlocking(false);
                datagramChannel.connect(socketAddress);
                bufferSize = datagramChannel.socket().getReceiveBufferSize();
                datagramChannel.register(selector, SelectionKey.OP_WRITE, new Context(threadIndex, 0));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        for (int threadIndex = 0; threadIndex < threads; threadIndex++) {
////            for (int requestIndex = 0; requestIndex < requests; requestIndex++) {
//                try {
//                    SelectionKey key = datagramChannel.register(selector, SelectionKey.OP_READ);
//                    key.attach(new Context(threadIndex, 0));
////                    System.out.println(threadIndex + " " + requestIndex);
//                } catch (ClosedChannelException ignored) {
//                }
////            }
//        }
        while (!Thread.interrupted() && !selector.keys().isEmpty()) {
            try {
//                System.out.println("!");
                selector.select(SELECT_TIMEOUT);
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                if (selectedKeys.isEmpty()) {
//                    System.out.println("selectedKeys.isEmpty");
                    for (SelectionKey key : selector.keys()) {
                        if (key.isWritable()) {
                            processRequest(key, prefix, socketAddress);
                        }
                    }
                } else {
                    for (final Iterator<SelectionKey> i = selector.selectedKeys().iterator(); i.hasNext(); ) {
                        final SelectionKey key = i.next();
//                    for (SelectionKey key : selectedKeys) {
//                        System.out.println("for (SelectionKey key : selectedKeys)");
                        if (key.isWritable()) {
                            processRequest(key, prefix, socketAddress);
                        }
                        if (key.isReadable()) {
//                            System.out.println("fwefefwefwefewfwef");
                            processResponse(key);
                        }
                        i.remove();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void processResponse(SelectionKey key) throws IOException {
        DatagramChannel datagramChannel = (DatagramChannel) key.channel();
        Context context = (Context) key.attachment();
//        ByteBuffer buffer = ByteBuffer.allocate(1024);
        context.buffer.clear();
        datagramChannel.receive(context.buffer);
        String responseBody = StandardCharsets.UTF_8.decode(context.buffer.flip()).toString();
//        System.out.println(responseBody);
        if (Utilities.validate(responseBody, context.threadIndex, context.requestIndex)) {
//            System.out.println("fwefwefef");
            context.requestIndex++;
            if (context.requestIndex != requests) {
                key.interestOps(SelectionKey.OP_WRITE);
            } else {
                System.out.println("Fwfef");
                key.channel().close();
                return;
            }
        }
        key.interestOps(SelectionKey.OP_WRITE);
    }

    private void processRequest(SelectionKey key, final String prefix, final SocketAddress socketAddress) throws IOException {
        DatagramChannel channel = (DatagramChannel) key.channel();
        Context context = (Context) key.attachment();
        context.buffer.clear();
        String requestBody = HelloUDPUtils.getRequestBody(context.getThreadIndex(), context.getRequestIndex(), prefix);
//        context.buffer = ByteBuffer.wrap(requestBody.getBytes(StandardCharsets.UTF_8));
        channel.send(ByteBuffer.wrap(requestBody.getBytes(StandardCharsets.UTF_8)), socketAddress);
//        System.out.println(requestBody);
        context.buffer.flip();
        key.interestOps(SelectionKey.OP_READ);

    }

    private static class Context {
        private final int threadIndex;
        int requestIndex;
        private ByteBuffer buffer;

        public Context(int threadIndex, int requestIndex) {
            this.threadIndex = threadIndex;
            this.requestIndex = requestIndex;
            this.buffer = ByteBuffer.allocate(bufferSize);
        }

        public int getThreadIndex() {
            return threadIndex;
        }

        public int getRequestIndex() {
            return requestIndex;
        }
    }

    public static void main(String[] args) {
        if (args == null || args.length != 5 || Arrays.stream(args).anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("Invalid input format: all arguments must be non-null. " + MAIN_USAGE);
        } else {
            int port = HelloUDPUtils.getIntegerArgumentSafely(args, 1, "port", MAIN_USAGE);
            int threadCount = HelloUDPUtils.getIntegerArgumentSafely(args, 3, "threads", MAIN_USAGE);
            int requestCount = HelloUDPUtils.getIntegerArgumentSafely(args, 4, "requests", MAIN_USAGE);
            new HelloUDPNonblockingClient().run(args[0], port, args[2], threadCount, requestCount);
        }
    }
}