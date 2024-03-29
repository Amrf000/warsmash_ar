package com.hiveworkshop.blizzard.casc.vfs;

import com.hiveworkshop.blizzard.casc.Key;

/**
 * A reference to a file in CASC storage.
 */
public class StorageReference {
    /**
     * Logical offset of this chunk.
     */
    private final long offset;

    /**
     * Logical size of this chunk.
     */
    private final long size;

    /**
     * Encoding key of chunk.
     */
    private final Key encodingKey;

    /**
     * Physical size of stored data.
     */
    private final long physicalSize;

    /**
     * Total size of all decompressed data banks.
     */
    private final long actualSize;

    public StorageReference(final long offset, final long size, final Key encodingKey, final int physicalSize,
                            final int actualSize) {
        this.offset = offset;
        this.size = size;
        this.encodingKey = encodingKey;
        this.physicalSize = physicalSize;
        this.actualSize = actualSize;
    }

    @Override
    public String toString() {

        return "FileReference{encodingKey=" +
                encodingKey +
                ", offset=" +
                offset +
                ", size=" +
                size +
                ", physicalSize=" +
                physicalSize +
                ", actualSize=" +
                actualSize +
                "}";
    }

    public long getOffset() {
        return offset;
    }

    public long getSize() {
        return size;
    }

    public Key getEncodingKey() {
        return encodingKey;
    }

    public long getPhysicalSize() {
        return physicalSize;
    }

    public long getActualSize() {
        return actualSize;
    }

}
