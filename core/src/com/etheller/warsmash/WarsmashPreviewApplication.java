package com.etheller.warsmash;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.etheller.warsmash.datasources.DataSource;
import com.etheller.warsmash.units.DataTable;
import com.etheller.warsmash.viewer5.CanvasProvider;
import com.etheller.warsmash.viewer5.ModelViewer;
import com.etheller.warsmash.viewer5.PathSolver;
import com.etheller.warsmash.viewer5.Scene;
import com.etheller.warsmash.viewer5.handlers.ResourceHandlerConstructionParams;
import com.etheller.warsmash.viewer5.handlers.mdx.MdxComplexInstance;
import com.etheller.warsmash.viewer5.handlers.mdx.MdxHandler;
import com.etheller.warsmash.viewer5.handlers.mdx.MdxModel;
import com.etheller.warsmash.viewer5.handlers.mdx.MdxViewer;
import com.etheller.warsmash.viewer5.handlers.w3x.camera.PortraitCameraManager;
import com.hiveworkshop.rms.parsers.mdlx.MdlxModel;
import com.hiveworkshop.rms.parsers.mdlx.util.MdxUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

public class WarsmashPreviewApplication extends ApplicationAdapter implements CanvasProvider {
    public static int VAO;
    private final Rectangle tempRect = new Rectangle();
    private final DataTable warsmashIni;
    private ModelViewer viewer;
    private PortraitCameraManager cameraManager;
    private int frame = 0;
    private MdxComplexInstance mainInstance;
    private Scene scene;
    private MdxHandler mdxHandler;

    public WarsmashPreviewApplication(final DataTable warsmashIni) {
        this.warsmashIni = warsmashIni;
    }


    @Override
    public void create() {

        final ByteBuffer tempByteBuffer = ByteBuffer.allocateDirect(4);
        tempByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        final IntBuffer temp = tempByteBuffer.asIntBuffer();
//
        Gdx.gl30.glGenVertexArrays(1, temp);
        VAO = temp.get(0);

        Gdx.gl30.glBindVertexArray(VAO);

        final String renderer = Gdx.gl.glGetString(GL20.GL_RENDERER);
        System.err.println("Renderer: " + renderer);

        DataSource codebase = WarsmashGdxMapScreen.parseDataSources(this.warsmashIni);
        this.viewer = new MdxViewer(codebase, this, new Vector3(0.3f, 0.3f, -0.25f));

        this.mdxHandler = new MdxHandler();
        this.viewer.addHandler(this.mdxHandler);
        this.viewer.enableAudio();

        this.scene = this.viewer.addSimpleScene();
        this.scene.enableAudio();

        this.cameraManager = new PortraitCameraManager();
        this.cameraManager.setupCamera(this.scene);
        this.cameraManager.distance = 500;
        this.cameraManager.horizontalAngle = (float) ((Math.PI) / 2);

        System.out.println("Loaded");
        Gdx.gl30.glClearColor(0.5f, 0.5f, 0.5f, 1); // TODO remove white background

    }

    @Override
    public void render() {
        Gdx.gl30.glBindVertexArray(VAO);
        this.cameraManager.updateCamera();
        this.viewer.updateAndRender();

        this.frame++;
        if ((this.frame % 1000) == 0) {
            System.out.println(Gdx.graphics.getFramesPerSecond());
        }

    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public float getWidth() {
        return Gdx.graphics.getWidth();
    }

    @Override
    public float getHeight() {
        return Gdx.graphics.getHeight();
    }

    @Override
    public void resize(final int width, final int height) {
        this.tempRect.width = width;
        this.tempRect.height = height;
        this.cameraManager.camera.viewport(this.tempRect);
    }

    public MdlxModel loadCustomModel(final String filename) {
        clearMainInstance();
        final MdxModel mdx = (MdxModel) this.mdxHandler.construct(new ResourceHandlerConstructionParams(this.viewer, this.mdxHandler, ".mdx", PathSolver.DEFAULT, filename));
        final MdlxModel mdlxModel;
        try (FileInputStream stream = new FileInputStream(filename)) {
            mdlxModel = MdxUtils.loadMdlx(stream);
            mdx.load(mdlxModel);
            mdx.ok = true;
//			mdx.lateLoad();
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
        final MdxComplexInstance instance = (MdxComplexInstance) mdx.addInstance();
//		this.cameraManager.setModelInstance(instance, mdx);
        instance.setScene(this.scene);
        this.mainInstance = instance;
        return mdlxModel;
    }

    private void clearMainInstance() {
        if (this.mainInstance != null) {
            this.mainInstance.detach();
            this.mainInstance = null;
        }
    }

    public PortraitCameraManager getCameraManager() {
        return this.cameraManager;
    }

    public MdxComplexInstance getMainInstance() {
        return this.mainInstance;
    }
}
