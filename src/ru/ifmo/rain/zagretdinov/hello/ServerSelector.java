package ru.ifmo.rain.zagretdinov.hello;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;


public class ServerSelector {
	
	private final int port;
	
	public ServerSelector(int port) {
		this.port = port;
	}
	
	public static void main(String[] args) {
		new ServerSelector(8080).run();
	}
	
	private void run(Selector selector) throws IOException {
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		while (!Thread.interrupted()) {
			selector.select();
			final Set<SelectionKey> selectedKeys = selector.selectedKeys();
			final Iterator<SelectionKey> iter = selectedKeys.iterator();
			while (iter.hasNext()) {
				SelectionKey key = iter.next();
				try {
					if (key.isReadable()) {
						DatagramChannel socketChannel = (DatagramChannel) key.channel();
						try {
                            buffer.clear();
                            SocketAddress address = socketChannel.receive(buffer);
                            String request = StandardCharsets.UTF_8.decode(buffer.flip()).toString();
                            System.out.println(request);
                            socketChannel.send(buffer, address);
						} catch (IOException e) {
							socketChannel.close();
						}
					}
				} finally {
					iter.remove();
				}
			}
		}
	}
	
	public void run() {
		try (
				Selector selector = Selector.open();
				DatagramChannel serverChannel = DatagramChannel.open()
		) {
			serverChannel.bind(new InetSocketAddress(port));
			serverChannel.configureBlocking(false);
			serverChannel.register(selector, SelectionKey.OP_READ);
			run(selector);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}