#!/bin/bash
java -cp . -p . -m info.kgeorgiy.java.advanced.hello server-evil ru.ifmo.rain.zagretdinov.hello.HelloUDPNonblockingServer
java -cp . -p . -m info.kgeorgiy.java.advanced.hello client-evil ru.ifmo.rain.zagretdinov.hello.HelloUDPNonblockingClient