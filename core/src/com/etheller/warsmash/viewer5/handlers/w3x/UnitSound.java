package com.etheller.warsmash.viewer5.handlers.w3x;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.TimeUtils;
import com.etheller.warsmash.datasources.DataSource;
import com.etheller.warsmash.units.DataTable;
import com.etheller.warsmash.units.Element;
import com.etheller.warsmash.util.DataSourceFileHandle;
import com.etheller.warsmash.viewer5.AudioBufferSource;
import com.etheller.warsmash.viewer5.AudioContext;
import com.etheller.warsmash.viewer5.AudioPanner;
import com.etheller.warsmash.viewer5.gl.Extensions;
import com.etheller.warsmash.viewer5.handlers.w3x.rendersim.RenderUnit;

import java.util.ArrayList;
import java.util.List;

public final class UnitSound {
    private static final UnitSound SILENT = new UnitSound(0, 0, 0, 0, 0, 0, false);

    private final List<Sound> sounds = new ArrayList<>();
    private final float volume;
    private final float pitch;
    private final float pitchVariance;
    private final float minDistance;
    private final float distanceCutoff;
    private final boolean looping;

    private Sound lastPlayedSound;

    public UnitSound(final float volume, final float pitch, final float pitchVariation, final float minDistance,
                     final float maxDistance, final float distanceCutoff, final boolean looping) {
        this.volume = volume;
        this.pitch = pitch;
        this.pitchVariance = pitchVariation;
        this.minDistance = minDistance;
        this.distanceCutoff = distanceCutoff;
        this.looping = looping;
    }

    public static UnitSound create(final DataSource dataSource, final DataTable unitAckSounds, final String soundName,
                                   final String soundType) {
        final Element row = unitAckSounds.get(soundName + soundType);
        if (row == null) {
            return SILENT;
        }
        final String fileNames = row.getField("FileNames");
        String directoryBase = row.getField("DirectoryBase");
        if ((directoryBase.length() > 1) && !directoryBase.endsWith("\\")) {
            directoryBase += "\\";
        }
        final float volume = row.getFieldFloatValue("Volume") / 127f;
        final float pitch = row.getFieldFloatValue("Pitch");
        float pitchVariance = row.getFieldFloatValue("PitchVariance");
        if (pitchVariance == 1.0f) {
            pitchVariance = 0.0f;
        }
        final float minDistance = row.getFieldFloatValue("MinDistance");
        final float maxDistance = row.getFieldFloatValue("MaxDistance");
        final float distanceCutoff = row.getFieldFloatValue("DistanceCutoff");
        final String[] flags = row.getField("Flags").split(",");
        boolean looping = false;
        for (final String flag : flags) {
            if ("LOOPING".equals(flag)) {
                looping = true;
                break;
            }
        }
        final UnitSound sound = new UnitSound(volume, pitch, pitchVariance, minDistance, maxDistance, distanceCutoff,
                looping);
        for (final String fileName : fileNames.split(",")) {
            final String filePath = directoryBase + fileName;
            final Sound newSound = createSound(dataSource, filePath);
            if (newSound != null) {
                sound.sounds.add(newSound);
            }
        }
        return sound;
    }

    public static Sound createSound(final DataSource dataSource, String filePath) {
        final int lastDotIndex = filePath.lastIndexOf('.');
        if (lastDotIndex != -1) {
            filePath = filePath.substring(0, lastDotIndex);
        }
        Sound newSound = null;
        if (dataSource.has(filePath + ".wav") || dataSource.has(filePath + ".flac")) {
            try {
                newSound = Gdx.audio.newSound(new DataSourceFileHandle(dataSource, filePath + ".wav"));
            } catch (final Exception exc) {
                exc.printStackTrace();
            }
        }
        return newSound;
    }

    public boolean playUnitResponse(final AudioContext audioContext, final RenderUnit unit) {
        return playUnitResponse(audioContext, unit, (int) (Math.random() * this.sounds.size()));
    }

    public boolean playUnitResponse(final AudioContext audioContext, final RenderUnit unit, final int index) {
        final long millisTime = TimeUtils.millis();
        if (millisTime < unit.lastUnitResponseEndTimeMillis) {
            return false;
        }
        if (play(audioContext, unit.location[0], unit.location[1], unit.location[2], index)) {
            final float duration = Extensions.audio.getDuration(this.lastPlayedSound);
            unit.lastUnitResponseEndTimeMillis = millisTime + (long) (1000 * duration);
            return true;
        }
        return false;
    }

    public void play(final AudioContext audioContext, final float x, final float y, final float z) {
        play(audioContext, x, y, z, (int) (Math.random() * this.sounds.size()));
    }

    public boolean play(final AudioContext audioContext, final float x, final float y, final float z, final int index) {
        if (this.sounds.isEmpty()) {
            return false;
        }

        if (audioContext == null) {
            return true;
        }
        final AudioPanner panner = audioContext.createPanner();
        final AudioBufferSource source = audioContext.createBufferSource();

        // Panner settings
        panner.setPosition(x, y, z);
        panner.setDistances(this.distanceCutoff, this.minDistance);
        panner.connect(audioContext.destination);

        // Source.
        source.buffer = this.sounds.get(index);
        source.connect(panner);

        // Make a sound.
        source.start(this.volume,
                (this.pitch + ((float) Math.random() * this.pitchVariance * 2)) - this.pitchVariance, this.looping);
        this.lastPlayedSound = source.buffer;
        return true;
    }

    public int getSoundCount() {
        return this.sounds.size();
    }

    public void stop() {
        for (final Sound sound : this.sounds) {
            sound.stop();
        }
    }
}