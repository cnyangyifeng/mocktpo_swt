package com.mocktpo.util;

import com.mocktpo.orm.domain.UserTestSession;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.constants.RC;

import javax.sound.sampled.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.net.URL;
import java.net.URLDecoder;

public class TestAudioPlayer {

    private static final int BUFFER_SIZE = 4096;

    private AudioInputStream encoded, decoded;
    private SourceDataLine line;
    private volatile boolean stopped;
    private volatile boolean paused;
    private final Object lock = new Object();
    private long timeElapsed;

    private PropertyChangeSupport support = new PropertyChangeSupport(this);

    public TestAudioPlayer(UserTestSession userTestSession, String fileName, boolean isUserData) {
        if (isUserData) {
            try {
                URL url = this.getClass().getResource(URLDecoder.decode(RC.USERS_DATA_DIR + fileName + RC.WAV_FILE_TYPE_SUFFIX, "utf-8"));
                encoded = AudioSystem.getAudioInputStream(url);
                AudioFormat encodedFormat = encoded.getFormat();
                AudioFormat decodedFormat = getAudioFormat(encodedFormat);
                decoded = AudioSystem.getAudioInputStream(decodedFormat, encoded);
                line = AudioSystem.getSourceDataLine(decodedFormat);
                line.open(decodedFormat);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                URL url = this.getClass().getResource(URLDecoder.decode(RC.TESTS_DATA_DIR + userTestSession.getFileAlias() + MT.STRING_SLASH + fileName + RC.MP3_FILE_TYPE_SUFFIX, "utf-8"));
                encoded = AudioSystem.getAudioInputStream(url);
                AudioFormat encodedFormat = encoded.getFormat();
                AudioFormat decodedFormat = getAudioFormat(encodedFormat);
                decoded = AudioSystem.getAudioInputStream(decodedFormat, encoded);
                line = AudioSystem.getSourceDataLine(decodedFormat);
                line.open(decodedFormat);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private AudioFormat getAudioFormat(AudioFormat encodedFormat) {
        return new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, encodedFormat.getSampleRate(), 16, encodedFormat.getChannels(), encodedFormat.getChannels() * 2, encodedFormat.getSampleRate(), false);
    }

    public void play() {
        try {
            long start = System.currentTimeMillis();
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
                        stopped = true;
                        break;
                    }
                    line.write(buffer, 0, bytesRead);
                    setTimeElapsed(System.currentTimeMillis() - start);
                }
            }
            line.drain();
            line.stop();
            line.close();
            decoded.close();
            encoded.close();
            setTimeElapsed(System.currentTimeMillis() - start);
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
        value = (value <= 0.0) ? 0.0001 : ((value > 1.0) ? 1.0 : value);
        float dB = (float) (Math.log(value) / Math.log(10.0) * 20.0);
        ((FloatControl) line.getControl(FloatControl.Type.MASTER_GAIN)).setValue(dB);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    /*
     * ==================================================
     *
     * Getters and Setters
     *
     * ==================================================
     */

    public void setTimeElapsed(long timeElapsed) {
        support.firePropertyChange("timeElapsed", this.timeElapsed, timeElapsed);
        this.timeElapsed = timeElapsed;
    }
}
