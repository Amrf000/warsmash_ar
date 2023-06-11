package net.warsmash.nio.channels;

import net.warsmash.networking.udp.UdpServerListener;
import net.warsmash.nio.channels.tcp.TCPClientParser;
import net.warsmash.nio.util.ExceptionListener;

import java.net.SocketAddress;
import java.nio.ByteOrder;

public interface ChannelOpener {
    void openUDPServerChannel(int port, UdpServerListener listener, ExceptionListener exceptionListener,
                              int bufferSize, ByteOrder byteOrder);

    void openTCPServerChannel(int port, SocketChannelCallback callback,
                              final ExceptionListener exceptionListener, final int bufferSize, ByteOrder byteOrder);

    WritableOutput openTCPClientChannel(SocketAddress socketAddress, TCPClientParser tcpClientParser,
                                        ExceptionListener exceptionListener, int bufferSize, ByteOrder byteOrder);
}
