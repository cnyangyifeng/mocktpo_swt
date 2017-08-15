package com.mocktpo.util;

import com.mocktpo.util.constants.RC;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;

import java.net.URLDecoder;

public class AnimatedGifUtils {

    /* Logger */

    protected static final Logger logger = LogManager.getLogger();

    private AnimatedGifUtils() {
    }

    public static void load(final GC gc, final String fileName) {
        final ImageLoader loader = new ImageLoader();
        try {
            ImageData[] data = loader.load(ImageUtils.class.getResourceAsStream(URLDecoder.decode(RC.IMAGES_DIR + fileName + RC.GIF_FILE_TYPE_SUFFIX, "utf-8")));
            if (data.length > 1) {
                new Thread().start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
