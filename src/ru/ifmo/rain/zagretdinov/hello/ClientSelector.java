package ru.ifmo.rain.zagretdinov.hello;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.StandardCharsets;


public class ClientSelector {
    private static SocketAddress address;
    public static void main(String[] args) throws IOException {
        final DatagramChannel open = DatagramChannel.open();
        address = new InetSocketAddress("localhost", 8080);
        open.configureBlocking(false);
        open.connect(address);
        ByteBuffer buf = ByteBuffer.wrap("fuck".getBytes());
        try {
            open.send(buf, address);
            System.out.println(StandardCharsets.UTF_8.decode(buf.flip()).toString());
            buf.clear();
            open.receive(buf);
            String response = new String(buf.array());
            System.out.println(response);
            buf.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}