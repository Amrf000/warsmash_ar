package net.warsmash.nio.channels;

import net.warsmash.nio.channels.tcp.TCPClientParser;

import java.net.SocketAddress;

public interface SocketChannelCallback {
    TCPClientParser onConnect(WritableOutput writableOpenedChannel, SocketAddress remoteAddress);
}
