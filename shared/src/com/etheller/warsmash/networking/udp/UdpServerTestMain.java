package com.etheller.warsmash.networking.udp;

import net.warsmash.networking.udp.UdpServer;
import net.warsmash.networking.udp.UdpServerListener;
import net.warsmash.uberserver.GamingNetwork;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;

public class UdpServerTestMain {

    static UdpServer warsmashGameServer;

    public static void main(final String[] args) {
        try {
            warsmashGameServer = new UdpServer(GamingNetwork.UDP_SINGLE_GAME_PORT, new UdpServerListener() {
                final ByteBuffer sendBuffer = ByteBuffer.allocate(1024);
                int n = 0;

                @Override
                public void parse(final SocketAddress sourceAddress, final ByteBuffer buffer) {
                    System.out.println("Got packet from: " + sourceAddress);
                    while (buffer.hasRemaining()) {
                        System.out.println("Received: " + buffer.get());
                    }
                    try {
                        this.sendBuffer.clear();
                        this.sendBuffer.putInt(this.n++);
                        this.sendBuffer.flip();
                        warsmashGameServer.send(sourceAddress, this.sendBuffer);
                    } catch (final IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            new Thread(warsmashGameServer).start();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

}
