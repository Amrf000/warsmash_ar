package com.etheller.warsmash.viewer5.handlers.blp;

import com.etheller.warsmash.viewer5.ModelViewer;
import com.etheller.warsmash.viewer5.PathSolver;
import com.etheller.warsmash.viewer5.RawOpenGLTextureResource;
import com.etheller.warsmash.viewer5.handlers.ResourceHandler;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class DdsTexture extends RawOpenGLTextureResource {

    public DdsTexture(final ModelViewer viewer, final ResourceHandler handler, final String extension,
                      final PathSolver pathSolver, final String fetchUrl) {
        super(viewer, extension, pathSolver, fetchUrl, handler);
    }

    @Override
    protected void lateLoad() {

    }

    @Override
    protected void load(final InputStream src, final Object options) {
        BufferedImage img;
        try {
            img = ImageIO.read(src);
            update(img, false);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

}