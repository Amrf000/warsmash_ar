package com.etheller.warsmash.viewer5.handlers.mdx;

import com.etheller.warsmash.util.WarsmashConstants;
import com.etheller.warsmash.viewer5.Texture;
import com.etheller.warsmash.viewer5.handlers.EmitterObject;
import com.hiveworkshop.rms.parsers.mdlx.AnimationMap;
import com.hiveworkshop.rms.parsers.mdlx.MdlxParticleEmitter2;
import com.hiveworkshop.rms.parsers.mdlx.MdlxParticleEmitter2.HeadOrTail;

public class ParticleEmitter2Object extends GenericObject implements EmitterObject {
    public final float width;
    public final float length;
    public final float speed;
    public final float latitude;
    public final float gravity;
    public final float emissionRate;
    public final long squirt;
    public final float lifeSpan;
    public final float variation;
    public final float tailLength;
    public final float timeMiddle;
    public final long columns;
    public final long rows;
    public int teamColored = 0;
    public Texture internalTexture;
    public final long replaceableId;
    public final HeadOrTail headOrTail;
    public final float cellWidth;
    public final float cellHeight;
    public final float[][] colors;
    public final float[] scaling;
    public final float[][] intervals;
    public final int blendSrc;
    public final int blendDst;
    public final int filterModeForSort;
    public final int priorityPlane;

    public ParticleEmitter2Object(final MdxModel model, final MdlxParticleEmitter2 emitter, final int index) {
        super(model, emitter, index);
        this.width = emitter.getWidth();
        this.length = emitter.getLength();
        this.speed = emitter.getSpeed();
        this.latitude = emitter.getLatitude();
        this.gravity = emitter.getGravity();
        this.emissionRate = emitter.getEmissionRate();
        this.squirt = emitter.getSquirt();
        this.lifeSpan = emitter.getLifeSpan();
        this.variation = emitter.getVariation();
        this.tailLength = emitter.getTailLength();
        this.timeMiddle = emitter.getTimeMiddle();

        final long replaceableId = emitter.getReplaceableId();

        this.columns = emitter.getColumns();
        this.rows = emitter.getRows();

        if (replaceableId == 0) {
            this.internalTexture = model.getTextures().get(emitter.getTextureId());
        } else if ((replaceableId == 1) || (replaceableId == 2)) {
            this.teamColored = 1;
        } else {
            this.internalTexture = (Texture) model.viewer.load(
                    "ReplaceableTextures\\" + ReplaceableIds.getPathString(replaceableId) + ".blp", model.pathSolver,
                    model.solverParams);
        }

        this.replaceableId = emitter.getReplaceableId();

        this.headOrTail = emitter.getHeadOrTail();

        this.cellWidth = 1f / emitter.getColumns();
        this.cellHeight = 1f / emitter.getRows();
        this.colors = new float[3][0];

        final float[][] colors = emitter.getSegmentColors();
        final short[] alpha = emitter.getSegmentAlphas();

        for (int i = 0; i < 3; i++) {
            final float[] color = colors[i];

            this.colors[i] = new float[]{color[0], color[1], color[2],
                    (alpha[i] / 255f) * WarsmashConstants.MODEL_DETAIL_PARTICLE_FACTOR_INVERSE};
        }

        this.scaling = emitter.getSegmentScaling();

        final long[][] headIntervals = emitter.getHeadIntervals();
        final long[][] tailIntervals = emitter.getTailIntervals();

        // Change to Float32Array instead of Uint32Array to be able to pass the
        // intervals directly using uniform3fv().
        this.intervals = new float[][]{{headIntervals[0][0], headIntervals[0][1], headIntervals[0][2]},
                {headIntervals[1][0], headIntervals[1][1], headIntervals[1][2]},
                {tailIntervals[0][0], tailIntervals[0][1], tailIntervals[0][2]},
                {tailIntervals[1][0], tailIntervals[1][1], tailIntervals[1][2]},};

        final int[] blendModes = FilterMode.emitterFilterMode(emitter.getFilterMode());

        this.filterModeForSort = emitter.getFilterMode().ordinal() + 2;
        this.blendSrc = blendModes[0];
        this.blendDst = blendModes[1];

        this.priorityPlane = emitter.getPriorityPlane();
    }

    public void getWidth(final float[] out, final int sequence, final int frame, final int counter) {
        this.getScalarValue(out, AnimationMap.KP2N.getWar3id(), sequence, frame, counter, this.length);
    }

    public void getLength(final float[] out, final int sequence, final int frame, final int counter) {
        this.getScalarValue(out, AnimationMap.KP2W.getWar3id(), sequence, frame, counter, this.width);
    }

    public void getSpeed(final float[] out, final int sequence, final int frame, final int counter) {
        this.getScalarValue(out, AnimationMap.KP2S.getWar3id(), sequence, frame, counter, this.speed);
    }

    public void getLatitude(final float[] out, final int sequence, final int frame, final int counter) {
        this.getScalarValue(out, AnimationMap.KP2L.getWar3id(), sequence, frame, counter, this.latitude);
    }

    public void getGravity(final float[] out, final int sequence, final int frame, final int counter) {
        this.getScalarValue(out, AnimationMap.KP2G.getWar3id(), sequence, frame, counter, this.gravity);
    }

    public int getEmissionRate(final float[] out, final int sequence, final int frame, final int counter) {
        return this.getScalarValue(out, AnimationMap.KP2E.getWar3id(), sequence, frame, counter, this.emissionRate);
    }

    @Override
    public void getVisibility(final float[] out, final int sequence, final int frame, final int counter) {
        this.getScalarValue(out, AnimationMap.KP2V.getWar3id(), sequence, frame, counter, 1);
    }

    public void getVariation(final float[] out, final int sequence, final int frame, final int counter) {
        this.getScalarValue(out, AnimationMap.KP2R.getWar3id(), sequence, frame, counter, this.variation);
    }

    @Override
    public boolean ok() {
        return true;
    }

    @Override
    public int getGeometryEmitterType() {
        return GeometryEmitterFuncs.EMITTER_PARTICLE2;
    }

}
