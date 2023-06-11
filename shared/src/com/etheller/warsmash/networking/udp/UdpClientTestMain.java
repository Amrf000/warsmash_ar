package com.etheller.warsmash.networking.udp;

import net.warsmash.networking.udp.UdpClient;
import net.warsmash.uberserver.GamingNetwork;

import java.io.IOException;
import java.net.InetAddress;
import java.nio.ByteBuffer;

public class UdpClientTestMain {

    static UdpClient warsmashUdpClient;

    public static void main(final String[] args) {
        try {
            warsmashUdpClient = new UdpClient(InetAddress.getLocalHost(), GamingNetwork.UDP_SINGLE_GAME_PORT,
                    buffer -> {
                        System.out.println("got " + buffer.remaining() + " bytes, pos=" + buffer.position()
                                + ", lim=" + buffer.limit());
                        System.out.println("got from server: " + buffer.getInt());
                    });
            new Thread(warsmashUdpClient).start();

            final ByteBuffer sendBuffer = ByteBuffer.allocate(1024);
            for (int i = 0; i < 10; i++) {
                sendBuffer.clear();
                sendBuffer.put((byte) (1 + i));
                sendBuffer.put((byte) 3);
                sendBuffer.put((byte) 5);
                sendBuffer.put((byte) 7);
                sendBuffer.flip();
                warsmashUdpClient.send(sendBuffer);
                try {
                    Thread.sleep(1000);
                } catch (final InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

}
