package net.warsmash.uberserver;

public enum LoginFailureReason {
    INVALID_CREDENTIALS, UNKNOWN_USER;

    public static final LoginFailureReason[] VALUES = values();
}
