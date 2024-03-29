package com.etheller.warsmash.viewer5.handlers.w3x.simulation.sound;

public class CMIDISound implements CSound {
    private final String soundLabel;

    public CMIDISound(final String soundLabel, final int fadeInRate, final int fadeOutRate) {
        this.soundLabel = soundLabel;
    }

    @Override
    public void start() {
        System.err.println(
                "Not starting MIDI sound because we don't have a LibGDX API to play those: " + this.soundLabel);
    }
}
