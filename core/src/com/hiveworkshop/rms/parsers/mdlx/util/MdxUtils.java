package com.hiveworkshop.rms.parsers.mdlx.util;

import com.hiveworkshop.rms.parsers.mdlx.MdlxModel;
import org.apache.commons.compress.utils.IOUtils;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.file.Files;

public class MdxUtils {
    public static MdlxModel loadMdlx(final InputStream inputStream) throws IOException {
        return new MdlxModel(ByteBuffer.wrap(IOUtils.toByteArray(inputStream)));
    }

    public static void saveMdx(final MdlxModel model, final OutputStream outputStream) throws IOException {
        outputStream.write(model.saveMdx().array());
    }

    public static void saveMdl(final MdlxModel model, final OutputStream outputStream) throws IOException {
        outputStream.write(model.saveMdl().array());
    }

    public static void saveMdl(final MdlxModel model, final File file) throws IOException {
        saveMdl(model, Files.newOutputStream(file.toPath()));
    }

}
