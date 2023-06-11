package com.etheller.warsmash.networking.uberserver;

import net.warsmash.nio.channels.ChannelOpener;
import net.warsmash.uberserver.GamingNetwork;

import java.nio.ByteOrder;

public class TCPGamingNetworkServer {
    private final ChannelOpener channelOpener;
    private final GamingNetworkServerClientBuilder gamingNetworkServerClientBuilder;

    public TCPGamingNetworkServer(final ChannelOpener channelOpener,
                                  final GamingNetworkServerClientBuilder gamingNetworkServerClientBuilder) {
        this.channelOpener = channelOpener;
        this.gamingNetworkServerClientBuilder = gamingNetworkServerClientBuilder;
    }

    public void start() {
        this.channelOpener.openTCPServerChannel(GamingNetwork.PORT, (writableOpenedChannel, remoteAddress) -> {
            System.out.println("Received connection from " + remoteAddress);
            return new TCPGamingNetworkServerClientParser(
                    TCPGamingNetworkServer.this.gamingNetworkServerClientBuilder
                            .createClient(writableOpenedChannel));
        }, Throwable::printStackTrace, 8 * 1024 * 1024, ByteOrder.LITTLE_ENDIAN);

        this.channelOpener.openUDPServerChannel(GamingNetwork.PORT, (sourceAddress, buffer) -> {

        }, Throwable::printStackTrace, 8 * 1024 * 1024, ByteOrder.LITTLE_ENDIAN);
    }

}
