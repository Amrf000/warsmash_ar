package net.warsmash.uberserver;

public enum HandshakeDeniedReason {
    BAD_GAME, BAD_GAME_VERSION, SERVER_ERROR;

    public static final HandshakeDeniedReason[] VALUES = values();
}
