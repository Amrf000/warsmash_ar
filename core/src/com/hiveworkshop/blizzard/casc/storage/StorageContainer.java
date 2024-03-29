package com.hiveworkshop.blizzard.casc.storage;

import com.hiveworkshop.blizzard.casc.Key;
import com.hiveworkshop.blizzard.casc.nio.HashMismatchException;
import com.hiveworkshop.blizzard.casc.nio.MalformedCASCStructureException;

import java.io.IOException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * High level storage container representing a storage entry.
 */
public class StorageContainer {
    /**
     * Size of storage encoding key in bytes.
     */
    private static final int ENCODING_KEY_SIZE = 16;

    /**
     * Container encoding key.
     */
    private final Key key;
    private final long size;
    private final short flags;

    public StorageContainer(final ByteBuffer storageBuffer) throws IOException {
        final ByteBuffer containerBuffer = storageBuffer.slice();
        containerBuffer.order(ByteOrder.LITTLE_ENDIAN);

        // key is in reversed byte order
        final int checksumA;
        final int checksumB;
        try {
            final byte[] keyArray = new byte[ENCODING_KEY_SIZE];
            final int keyEnd = containerBuffer.position() + keyArray.length;
            for (int writeIndex = 0, readIndex = keyEnd
                    - 1; writeIndex < keyArray.length; writeIndex += 1, readIndex -= 1) {
                keyArray[writeIndex] = containerBuffer.get(readIndex);
            }
            containerBuffer.position(keyEnd);

            key = new Key(keyArray);
            size = Integer.toUnsignedLong(containerBuffer.getInt());
            flags = containerBuffer.getShort();

            checksumA = containerBuffer.getInt();
            checksumB = containerBuffer.getInt();
        } catch (final BufferUnderflowException e) {
            throw new MalformedCASCStructureException("storage buffer too small");
        }

        storageBuffer.position(storageBuffer.position() + containerBuffer.position());
    }

    @Override
    public String toString() {

        return "FileEntry{key=" +
                key +
                ", size=" +
                size +
                ", flags=" +
                Integer.toBinaryString(flags) +
                "}";
    }

    public long getSize() {
        return size;
    }

    public short getFlags() {
        return flags;
    }

    public Key getKey() {
        return key;
    }

}
