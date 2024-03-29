package com.hiveworkshop.rms.parsers.mdlx;

import com.etheller.warsmash.util.War3ID;
import com.hiveworkshop.rms.parsers.mdlx.mdl.MdlTokenInputStream;
import com.hiveworkshop.rms.parsers.mdlx.mdl.MdlTokenOutputStream;
import com.hiveworkshop.rms.parsers.mdlx.mdl.MdlUtils;
import com.hiveworkshop.rms.parsers.mdlx.timeline.MdlxTimeline;
import com.hiveworkshop.rms.util.BinaryReader;
import com.hiveworkshop.rms.util.BinaryWriter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Based on the works of Chananya Freiman.
 */
public abstract class MdlxAnimatedObject implements MdlxChunk, MdlxBlock {
    public final List<MdlxTimeline<?>> timelines = new ArrayList<>();

    public void readTimelines(final BinaryReader reader, long size) {
        while (size > 0) {
            final War3ID name = new War3ID(reader.readTag());
            final AnimationMap animationMap = AnimationMap.ID_TO_TAG.get(name);
            if (animationMap == null) {
                throw new IllegalStateException("Unknown node tag: " + name);
            }
            final MdlxTimeline<?> timeline = animationMap.getImplementation().createTimeline();

            timeline.readMdx(reader, name);

            size -= timeline.getByteLength();

            this.timelines.add(timeline);
        }
    }

    public void writeTimelines(final BinaryWriter writer) {
        for (final MdlxTimeline<?> timeline : this.timelines) {
            timeline.writeMdx(writer);
        }
    }

    public Iterator<String> readAnimatedBlock(final MdlTokenInputStream stream) {
        return new TransformedAnimatedBlockIterator(stream.readBlock().iterator());
    }

    public void readTimeline(final MdlTokenInputStream stream, final AnimationMap name) {
        final MdlxTimeline<?> timeline = name.getImplementation().createTimeline();

        timeline.readMdl(stream, name.getWar3id());

        this.timelines.add(timeline);
    }

    public boolean writeTimeline(final MdlTokenOutputStream stream, final AnimationMap name) {
        for (final MdlxTimeline<?> timeline : this.timelines) {
            if (timeline.name.equals(name.getWar3id())) {
                timeline.writeMdl(stream);
                return true;
            }
        }
        return false;
    }

    @Override
    public long getByteLength(final int version) {
        long size = 0;
        for (final MdlxTimeline<?> timeline : this.timelines) {
            size += timeline.getByteLength();
        }
        return size;
    }

    public List<MdlxTimeline<?>> getTimelines() {
        return this.timelines;
    }

    /**
     * TODO: This code uses StringBuilder implicitly during string concat. This
     * should be upgraded for performance.
     *
     * @author micro
     */
    private static final class TransformedAnimatedBlockIterator implements Iterator<String> {
        private final Iterator<String> delegate;

        public TransformedAnimatedBlockIterator(final Iterator<String> delegate) {
            this.delegate = delegate;
        }

        @Override
        public boolean hasNext() {
            return this.delegate.hasNext();
        }

        @Override
        public String next() {
            final String token = this.delegate.next();
            if (token.equals(MdlUtils.TOKEN_STATIC) && hasNext()) {
                return MdlUtils.TOKEN_STATIC + " " + this.delegate.next();
            }
            return token;
        }
    }
}
