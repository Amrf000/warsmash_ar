package net.warsmash.networking.util;

import net.warsmash.nio.channels.WritableOutput;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class AbstractWriter {
    protected final ByteBuffer writeBuffer;
    private final WritableOutput writableOutput;

    public AbstractWriter(final WritableOutput writableOutput) {
        this.writableOutput = writableOutput;
        this.writeBuffer = ByteBuffer.allocateDirect(8 * 1024).order(ByteOrder.LITTLE_ENDIAN);
        this.writeBuffer.clear();
    }

    private void ensureCapacity(final int length) {
        if (this.writeBuffer.remaining() < length) {
            send();
        }
    }

    protected final void beginMessage(final int protocol, final int length) {
        ensureCapacity(length + 4 + 4);
        this.writeBuffer.putInt(protocol);
        this.writeBuffer.putInt(length);
    }

    protected final void send() {
        this.writeBuffer.flip();
        this.writableOutput.write(this.writeBuffer);
        this.writeBuffer.clear();
    }

    protected final void close() {
        this.writableOutput.close();
    }

}
