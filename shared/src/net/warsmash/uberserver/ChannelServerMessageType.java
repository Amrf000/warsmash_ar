package net.warsmash.uberserver;

public enum ChannelServerMessageType {
    JOIN_GAME, LEAVE_GAME;

    public static final ChannelServerMessageType[] VALUES = values();
}
