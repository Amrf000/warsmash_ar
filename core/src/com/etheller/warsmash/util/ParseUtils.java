package com.etheller.warsmash.util;

import com.google.common.io.LittleEndianDataInputStream;
import com.google.common.io.LittleEndianDataOutputStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class ParseUtils {
    public static final Charset UTF8 = StandardCharsets.UTF_8;

    public static long readUInt32(final LittleEndianDataInputStream stream) throws IOException {
        return stream.readInt() & 0xFFFFFFFFL;
    }

    public static int readUInt16(final LittleEndianDataInputStream stream) throws IOException {
        return stream.readShort() & 0xFFFF;
    }

    public static short readUInt8(final LittleEndianDataInputStream stream) throws IOException {
        return (short) (stream.readByte() & (short) 0xFF);
    }

    public static void readFloatArray(final LittleEndianDataInputStream stream, final float[] array)
            throws IOException {
        for (int i = 0; i < array.length; i++) {
            array[i] = stream.readFloat();
        }
    }

    public static float[] readFloatArray(final LittleEndianDataInputStream stream, final int length)
            throws IOException {
        final float[] array = new float[length];
        readFloatArray(stream, array);
        return array;
    }

    public static void readUInt32Array(final LittleEndianDataInputStream stream, final long[] array)
            throws IOException {
        for (int i = 0; i < array.length; i++) {
            array[i] = readUInt32(stream);
        }
    }

    public static long[] readUInt32Array(final LittleEndianDataInputStream stream, final int length)
            throws IOException {
        final long[] array = new long[length];
        readUInt32Array(stream, array);
        return array;
    }

    public static void readInt32Array(final LittleEndianDataInputStream stream, final int[] array) throws IOException {
        for (int i = 0; i < array.length; i++) {
            array[i] = stream.readInt();
        }
    }

    public static int[] readInt32Array(final LittleEndianDataInputStream stream, final int length) throws IOException {
        final int[] array = new int[length];
        readInt32Array(stream, array);
        return array;
    }

    public static void readUInt16Array(final LittleEndianDataInputStream stream, final int[] array) throws IOException {
        for (int i = 0; i < array.length; i++) {
            array[i] = readUInt16(stream);
        }
    }

    public static int[] readUInt16Array(final LittleEndianDataInputStream stream, final int length) throws IOException {
        final int[] array = new int[length];
        readUInt16Array(stream, array);
        return array;
    }

    public static void readUInt8Array(final LittleEndianDataInputStream stream, final short[] array)
            throws IOException {
        for (int i = 0; i < array.length; i++) {
            array[i] = readUInt8(stream);
        }
    }

    public static short[] readUInt8Array(final LittleEndianDataInputStream stream, final int length)
            throws IOException {
        final short[] array = new short[length];
        readUInt8Array(stream, array);
        return array;
    }

    public static War3ID readWar3ID(final LittleEndianDataInputStream stream) throws IOException {
        return new War3ID(Integer.reverseBytes(stream.readInt()));
    }

    public static void writeWar3ID(final LittleEndianDataOutputStream stream, final War3ID id) throws IOException {
        stream.writeInt(Integer.reverseBytes(id.getValue()));
    }

    public static void writeFloatArray(final LittleEndianDataOutputStream stream, final float[] array)
            throws IOException {
        for (float v : array) {
            stream.writeFloat(v);
        }
    }

    public static void writeUInt32(final LittleEndianDataOutputStream stream, final long uInt) throws IOException {
        stream.writeInt((int) (uInt & 0xFFFFFFFFL));
    }

    public static void writeUInt16(final LittleEndianDataOutputStream stream, final int uInt) throws IOException {
        stream.writeShort((short) (uInt & 0xFFFF));
    }

    public static void writeUInt8(final LittleEndianDataOutputStream stream, final short uInt) throws IOException {
        stream.writeByte((byte) (uInt & 0xFF));
    }

    public static void writeUInt32Array(final LittleEndianDataOutputStream stream, final long[] array)
            throws IOException {
        for (long l : array) {
            writeUInt32(stream, l);
        }
    }

    public static void writeInt32Array(final LittleEndianDataOutputStream stream, final int[] array)
            throws IOException {
        for (int j : array) {
            stream.writeInt(j);
        }
    }

    public static void writeUInt16Array(final LittleEndianDataOutputStream stream, final int[] array)
            throws IOException {
        for (int j : array) {
            writeUInt16(stream, j);
        }
    }

    public static void writeUInt8Array(final LittleEndianDataOutputStream stream, final short[] array)
            throws IOException {
        for (short value : array) {
            writeUInt8(stream, value);
        }
    }

    public static String readString(final LittleEndianDataInputStream stream, final byte[] recycleByteArray)
            throws IOException {
        stream.read(recycleByteArray);
        int i;
        for (i = 0; (i < recycleByteArray.length) && (recycleByteArray[i] != 0); i++) {
        }
        return new String(recycleByteArray, 0, i, ParseUtils.UTF8);
    }

    public static String readUntilNull(final LittleEndianDataInputStream stream) throws IOException {

        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int c;
        while (((c = stream.read()) != 0) && (c != -1)) {
            baos.write(c);
        }
        return new String(baos.toByteArray(), ParseUtils.UTF8);
    }

    public static void writeWithNullTerminator(final LittleEndianDataOutputStream stream, final String name)
            throws IOException {
        final byte[] nameBytes = name.getBytes(ParseUtils.UTF8);
        stream.write(nameBytes);
        stream.write(0);
    }

    public static float[] flipRGB(final float[] color) {
        final float r = color[0];
        color[0] = color[2];
        color[2] = r;
        return color;
    }

    public static float[] newFlippedRGB(final float[] color) {
        return new float[]{color[2], color[1], color[0]};
    }
}
