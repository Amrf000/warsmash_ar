package net.warsmash.nio.util;

public interface ExceptionListener {
    ExceptionListener THROW_RUNTIME = e -> {
        throw new RuntimeException(e);
    };

    void caught(Exception e);
}
