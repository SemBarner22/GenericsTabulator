package ru.ifmo.rain.zagretdinov.hello;

import java.net.SocketAddress;
import java.nio.ByteBuffer;

public class Pacckage {
    private ByteBuffer request;
    private SocketAddress socketAddress;

    public Pacckage(ByteBuffer request, SocketAddress socketAddress) {
        this.request = request;
        this.socketAddress = socketAddress;
    }

    public ByteBuffer getRequest() {
        return request;
    }

    public SocketAddress getSocketAddress() {
        return socketAddress;
    }

    public void setRequest(ByteBuffer request) {
        this.request = request;
    }

    public void setSocketAddress(SocketAddress socketAddress) {
        this.socketAddress = socketAddress;
    }
}
