package com.mocktpo.util;

import com.mocktpo.util.constants.MT;
import com.mocktpo.util.constants.RC;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sound.sampled.*;
import java.io.File;
import java.net.URLDecoder;

public class UserAudioRecorder {

    /* Logger */

    protected static final Logger logger = LogManager.getLogger();

    private TargetDataLine line;
    private File file;

    private final Object lock = new Object();

    public UserAudioRecorder(String fileAlias) {
        try {
            File rootDir = new File(this.getClass().getResource(URLDecoder.decode(RC.REPORTS_DATA_DIR, "utf-8")).toURI());
            file = new File(rootDir.toString() + MT.STRING_SLASH + fileAlias + RC.WAV_FILE_TYPE_SUFFIX);
            if (!file.exists()) {
                logger.info("User audio file created: {}.", file.createNewFile());
            }
            AudioFormat format = getAudioFormat();
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            if (!AudioSystem.isLineSupported(info)) {
                logger.error("Line is not supported.");
                System.exit(-1);
            }
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private AudioFormat getAudioFormat() {
        final float sampleRate = 44100.0f;
        final int sampleSizeInBits = 16;
        final int channels = 2;
        final int frameSize = 2 * 2;
        final float frameRate = 44100.0f;
        final boolean bigEndian = false;
        return new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, sampleRate, sampleSizeInBits, channels, frameSize, frameRate, bigEndian);
    }

    public void start() {
        try {
            line.start();
            AudioInputStream is = new AudioInputStream(line);
            logger.info("Audio recording started.");
            synchronized (lock) {
                AudioSystem.write(is, AudioFileFormat.Type.WAVE, file);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        line.stop();
        line.close();
        logger.info("Audio recording stopped.");
    }
}
