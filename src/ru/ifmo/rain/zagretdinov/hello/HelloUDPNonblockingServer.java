package ru.ifmo.rain.zagretdinov.hello;

import info.kgeorgiy.java.advanced.hello.HelloServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class HelloUDPNonblockingServer implements HelloServer {

    private static final String MAIN_USAGE = "Usage: HelloUDPNonblockingServer <port> <threads>";
    private static int BUFFER_SIZE = 1024;
    private static final int TERMINATION_AWAIT_TIMEOUT = 1;
    private static final int REQUESTS_LIMIT = 1000;

    private Selector selector;
    private DatagramChannel datagramChannel;

    private ExecutorService mainExecutor;
    //private ExecutorService executors;

    private Queue<ByteBuffer> buffersToRead;
    private Queue<ByteBuffer> responseBuffers;
    private Queue<SocketAddress> responseAddresses;

    @Override
    public void start(int port, int threads) {
        buffersToRead = new LinkedList<>();
        responseBuffers = new LinkedList<>();
        responseAddresses = new LinkedList<>();
        for (int i = 0; i < threads; i++) {
            buffersToRead.add(ByteBuffer.allocate(BUFFER_SIZE));
        }
        /*
        executors = new ThreadPoolExecutor(
                threads,
                threads,
                0,
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(REQUESTS_LIMIT),
                new ThreadPoolExecutor.DiscardPolicy()
        );*/
        mainExecutor = Executors.newSingleThreadExecutor();
        process(port);
    }

    private void process(int port) {
        try {
            datagramChannel = DatagramChannel.open();
            selector = Selector.open();
            datagramChannel.configureBlocking(false);
            datagramChannel.setOption(StandardSocketOptions.SO_REUSEADDR, true);
            datagramChannel.bind(new InetSocketAddress(port));
//            BUFFER_SIZE = datagramChannel.socket().getReceiveBufferSize();
            datagramChannel.register(selector, SelectionKey.OP_READ);
//            System.out.println("Ddwd");
            mainExecutor.submit(() -> {
                while (!Thread.interrupted() && !datagramChannel.socket().isClosed()) {
                    try {
//                        System.out.println("fewfwf");
                        selector.select();
                        Set<SelectionKey> selectedKeys = selector.selectedKeys();
                        if (!selectedKeys.isEmpty()) {
                            SelectionKey key = selectedKeys.iterator().next();
                            if (key.isReadable()) {
//                                System.out.println("lel");
                                read(key);
                            } if (key.isWritable()) {
//                                System.out.println("write");
                                write(key);
                            }
                            selectedKeys.remove(key);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            } catch(IOException e){
                e.printStackTrace();
            }
        }

        private void read (SelectionKey key) throws IOException {
//        System.out.println("kek");
            ByteBuffer buffer = buffersToRead.remove();
            Objects.requireNonNull(buffer);
            buffer.clear();
            SocketAddress address = datagramChannel.receive(buffer);
            String responseBody = "Hello, " + StandardCharsets.UTF_8.decode(buffer.flip()).toString();
//            String requestBody = new String(buffer.array(), StandardCharsets.UTF_8);
//            String responseBody = HelloUDPUtils.getResponseBody(requestBody);
            System.out.println(responseBody);
//            buffer.flip();
            responseBuffers.add(ByteBuffer.wrap(responseBody.getBytes(StandardCharsets.UTF_8)));
//        responseBuffers.add(StandardCharsets.UTF_8.newEncoder().encode(CharBuffer.wrap(responseBody)));
            responseAddresses.add(address);
//            System.out.println("kok");
            key.interestOpsOr(SelectionKey.OP_WRITE);
            if (buffersToRead.isEmpty()) {
                key.interestOpsAnd(~SelectionKey.OP_READ);
            }
        }

        private void write (SelectionKey key) throws IOException {
            ByteBuffer responseBuffer = responseBuffers.remove();
            SocketAddress responseAddress = responseAddresses.remove();
            datagramChannel.send(responseBuffer, responseAddress);
            responseBuffer.flip();
            buffersToRead.add(responseBuffer);
            key.interestOpsOr(SelectionKey.OP_READ);
            if (responseBuffers.isEmpty()) {
                key.interestOpsAnd(~SelectionKey.OP_WRITE);
            }
        }

        @Override
        public void close () {
            try {
                selector.close();
                datagramChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mainExecutor.shutdown();
            //executors.shutdown();
            try {
                mainExecutor.awaitTermination(TERMINATION_AWAIT_TIMEOUT, TimeUnit.SECONDS);
                //executors.awaitTermination(TERMINATION_AWAIT_TIMEOUT, TimeUnit.SECONDS);
            } catch (InterruptedException ignored) {
            }
        }

        public static void main (String[]args){
            if (args == null || args.length != 2 || args[0] == null || args[1] == null) {
                throw new IllegalArgumentException("Invalid input format: all arguments must be non-null. " + MAIN_USAGE);
            } else {
                int port = HelloUDPUtils.getIntegerArgumentSafely(args, 0, "port", MAIN_USAGE);
                int threadCount = HelloUDPUtils.getIntegerArgumentSafely(args, 1, "threads", MAIN_USAGE);
                new HelloUDPNonblockingServer().start(port, threadCount);
            }
        }
    }
