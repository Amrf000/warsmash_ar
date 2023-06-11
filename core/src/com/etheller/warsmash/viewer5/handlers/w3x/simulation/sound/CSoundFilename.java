package com.etheller.warsmash.viewer5.handlers.w3x.simulation.sound;

import com.badlogic.gdx.audio.Sound;
import com.etheller.warsmash.viewer5.AudioBufferSource;
import com.etheller.warsmash.viewer5.AudioContext;
import com.etheller.warsmash.viewer5.AudioPanner;

public class CSoundFilename implements CSound {

    private final Sound sound;
    private final boolean looping;
    private final boolean stopWhenOutOfRange;
    private final AudioContext audioContext;
    private float x;
    private float y;
    private float z;

    public CSoundFilename(final Sound sound, final AudioContext audioContext, final boolean looping,
                          final boolean stopWhenOutOfRange, final int fadeInRate, final int fadeOutRate, final String eaxSetting) {
        this.sound = sound;
        this.audioContext = audioContext;
        this.looping = looping;
        this.stopWhenOutOfRange = stopWhenOutOfRange;
    }

    public void setPosition(final float x, final float y, final float z) {
        this.x = x;
        this.y = y;
        this.z = z;

    }

    @Override
    public void start() {
        if (this.audioContext == null) {
            return;
        }
        final AudioPanner panner = this.audioContext.createPanner(this.stopWhenOutOfRange);
        final AudioBufferSource source = this.audioContext.createBufferSource();

        // Panner settings
        panner.setPosition(this.x, this.y, this.z);
        float distanceCutoff = 99999;
        float minDistance = 99999;
        panner.setDistances(distanceCutoff, minDistance);
        panner.connect(this.audioContext.destination);

        // Source.
        source.buffer = this.sound;
        source.connect(panner);

        // Make a sound.
        float pitch = 1.0f;
        float volume = 1.0f;
        source.start(volume, pitch, this.looping);
    }

}
