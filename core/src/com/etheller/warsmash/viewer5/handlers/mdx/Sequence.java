package com.etheller.warsmash.viewer5.handlers.mdx;

import com.etheller.warsmash.viewer5.Bounds;
import com.etheller.warsmash.viewer5.handlers.w3x.AnimationTokens;
import com.etheller.warsmash.viewer5.handlers.w3x.AnimationTokens.PrimaryTag;
import com.etheller.warsmash.viewer5.handlers.w3x.AnimationTokens.SecondaryTag;
import com.hiveworkshop.rms.parsers.mdlx.MdlxExtent;
import com.hiveworkshop.rms.parsers.mdlx.MdlxSequence;

import java.util.EnumSet;

public class Sequence {
    private final MdlxSequence sequence;
    private final Bounds bounds;
    private final EnumSet<AnimationTokens.PrimaryTag> primaryTags = EnumSet.noneOf(AnimationTokens.PrimaryTag.class);
    private final EnumSet<AnimationTokens.SecondaryTag> secondaryTags = EnumSet
            .noneOf(AnimationTokens.SecondaryTag.class);

    public Sequence(final MdlxSequence sequence) {
        this.sequence = sequence;
        this.bounds = new Bounds();
        final MdlxExtent sequenceExtent = sequence.getExtent();
        this.bounds.fromExtents(sequenceExtent.getMin(), sequenceExtent.getMax(), sequenceExtent.getBoundsRadius());
        populateTags();
    }

    private void populateTags() {
        this.primaryTags.clear();
        this.secondaryTags.clear();
        TokenLoop:
        for (final String token : this.sequence.name.split("\\s+")) {
            final String upperCaseToken = token.toUpperCase();
            for (final PrimaryTag primaryTag : PrimaryTag.values()) {
                if (upperCaseToken.equals(primaryTag.name())) {
                    this.primaryTags.add(primaryTag);
                    continue TokenLoop;
                }
            }
            for (final SecondaryTag secondaryTag : SecondaryTag.values()) {
                if (upperCaseToken.equals(secondaryTag.name())) {
                    this.secondaryTags.add(secondaryTag);
                    continue TokenLoop;
                }
            }
            break;
        }
    }

    public String getName() {
        return this.sequence.getName();
    }

    public long[] getInterval() {
        return this.sequence.getInterval();
    }

    public float getMoveSpeed() {
        return this.sequence.getMoveSpeed();
    }

    public int getFlags() {
        return this.sequence.getFlags();
    }

    public float getRarity() {
        return this.sequence.getRarity();
    }

    public long getSyncPoint() {
        return this.sequence.getSyncPoint();
    }

    public Bounds getBounds() {
        return this.bounds;
    }

    public MdlxExtent getExtent() {
        return this.sequence.getExtent();
    }

    public EnumSet<AnimationTokens.PrimaryTag> getPrimaryTags() {
        return this.primaryTags;
    }

    public EnumSet<AnimationTokens.SecondaryTag> getSecondaryTags() {
        return this.secondaryTags;
    }
}
