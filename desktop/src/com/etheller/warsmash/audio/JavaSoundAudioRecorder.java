/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 *   http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.etheller.warsmash.audio;

import com.badlogic.gdx.audio.AudioRecorder;
import com.badlogic.gdx.utils.GdxRuntimeException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioFormat.Encoding;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.TargetDataLine;

/**
 * @author mzechner
 */
public class JavaSoundAudioRecorder implements AudioRecorder {
    private final TargetDataLine line;
    private byte[] buffer = new byte[1024 * 4];

    public JavaSoundAudioRecorder(final int samplingRate, final boolean isMono) {
        try {
            final AudioFormat format = new AudioFormat(Encoding.PCM_SIGNED, samplingRate, 16, isMono ? 1 : 2,
                    isMono ? 2 : 4, samplingRate, false);
            this.line = AudioSystem.getTargetDataLine(format);
            this.line.open(format, this.buffer.length);
            this.line.start();
        } catch (final Exception ex) {
            throw new GdxRuntimeException("Error creating JavaSoundAudioRecorder.", ex);
        }
    }

    @Override
    public void read(final short[] samples, final int offset, final int numSamples) {
        if (this.buffer.length < (numSamples * 2)) {
            this.buffer = new byte[numSamples * 2];
        }

        final int toRead = numSamples * 2;
        int read = 0;
        while (read != toRead) {
            read += this.line.read(this.buffer, read, toRead - read);
        }

        for (int i = 0, j = 0; i < (numSamples * 2); i += 2, j++) {
            samples[offset + j] = (short) ((this.buffer[i + 1] << 8) | (this.buffer[i] & 0xff));
        }
    }

    @Override
    public void dispose() {
        this.line.close();
    }
}
