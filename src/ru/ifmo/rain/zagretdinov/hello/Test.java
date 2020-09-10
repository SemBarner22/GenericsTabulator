package ru.ifmo.rain.zagretdinov.hello;

import java.nio.ByteBuffer;

public class Test {
    public static void main(String[] args) {
        ByteBuffer a = ByteBuffer.allocate(100);
        a.put("cba".getBytes());
        a.put("1234".getBytes());
        a.put("cba".getBytes());
        a.put("234".getBytes());
        a.put("cba".getBytes());
        System.out.println(Utilities.validateBuf(a.flip(), ByteBuffer.wrap("1234".getBytes()), ByteBuffer.wrap("234".getBytes())));
    }
}
