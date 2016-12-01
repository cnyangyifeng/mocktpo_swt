package com.mocktpo.util;

import com.mocktpo.orm.domain.UserTest;
import com.mocktpo.util.constants.RC;

import javax.sound.sampled.*;
import java.net.URL;
import java.net.URLDecoder;

public class TestAudioPlayer {

    private static final int BUFFER_SIZE = 4096;

    private SourceDataLine line;
    private volatile boolean stopped;
    private volatile boolean paused;
    private final Object lock = new Object();

    public TestAudioPlayer() {
    }

    public void play(UserTest ut, String fileName) {

        try {
            URL url = this.getClass().getResource(URLDecoder.decode(RC.TESTS_DATA_DIR + ut.getAlias() + "/" + fileName + RC.MP3_FILE_TYPE_SUFFIX, "utf-8"));
            AudioInputStream encoded = AudioSystem.getAudioInputStream(url);
            AudioFormat encodedFormat = encoded.getFormat();
            AudioFormat decodedFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, encodedFormat.getSampleRate(), 16, encodedFormat.getChannels(), encodedFormat.getChannels() * 2, encodedFormat.getSampleRate(), false);
            AudioInputStream decoded = AudioSystem.getAudioInputStream(decodedFormat, encoded);
            line = AudioSystem.getSourceDataLine(decodedFormat);
            line.open(decodedFormat);
            line.start();
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;
            synchronized (lock) {
                while (true) {
                    bytesRead = decoded.read(buffer, 0, buffer.length);
                    if (isPaused()) {
                        line.stop();
                        lock.wait();
                        line.start();
                    }
                    if (-1 == bytesRead || isStopped()) {
                        break;
                    }
                    line.write(buffer, 0, bytesRead);
                }
            }
            line.drain();
            line.stop();
            line.close();
            decoded.close();
            encoded.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        if (null != line && !isStopped()) {
            stopped = true;
        }
    }

    public void pause() {
        if (null != line && !isPaused()) {
            paused = true;
        }
    }

    public void resume() {
        if (isPaused()) {
            synchronized (lock) {
                lock.notifyAll();
                paused = false;
            }
        }
    }

    public boolean isPlaying() {
        return (null != line && line.isOpen());
    }

    public boolean isStopped() {
        return stopped;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setVolume(double value) {

        if (!line.isOpen()) {
            return;
        }

        value = (value <= 0.0) ? 0.0001 : ((value > 1.0) ? 1.0 : value);
        float dB = (float) (Math.log(value) / Math.log(10.0) * 20.0);
        ((FloatControl) line.getControl(FloatControl.Type.MASTER_GAIN)).setValue(dB);
    }
}
