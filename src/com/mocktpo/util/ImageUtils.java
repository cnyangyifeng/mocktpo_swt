package com.mocktpo.util;

import com.mocktpo.util.constants.LC;
import com.mocktpo.util.constants.RC;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageDataProvider;
import org.eclipse.swt.widgets.Display;

import java.net.URLDecoder;

public class ImageUtils {

    protected static final Logger logger = LogManager.getLogger();

    private ImageUtils() {
    }

    public static Image load(final Display d, final String fileName) {
        return new Image(d, new ImageDataProvider() {
            @Override
            public ImageData getImageData(int zoom) {
                try {
                    switch (zoom) {
                    case 200:
                        if ("splash".equals(fileName)) {
                            return new Image(Display.getCurrent(), ImageUtils.class.getResourceAsStream(URLDecoder.decode(RC.IMAGES_DIR + fileName + responsive(d) + RC.HIDPI_SUFFIX + RC.PNG_FILE_TYPE_SUFFIX, "utf-8"))).getImageData();
                        }
                        return new Image(Display.getCurrent(), ImageUtils.class.getResourceAsStream(URLDecoder.decode(RC.IMAGES_DIR + fileName + RC.HIDPI_SUFFIX + RC.PNG_FILE_TYPE_SUFFIX, "utf-8"))).getImageData();
                    case 100:
                    default:
                        if ("splash".equals(fileName)) {
                            return new Image(Display.getCurrent(), ImageUtils.class.getResourceAsStream(URLDecoder.decode(RC.IMAGES_DIR + fileName + responsive(d) + RC.PNG_FILE_TYPE_SUFFIX, "utf-8"))).getImageData();
                        }
                        return new Image(Display.getCurrent(), ImageUtils.class.getResourceAsStream(URLDecoder.decode(RC.IMAGES_DIR + fileName + RC.PNG_FILE_TYPE_SUFFIX, "utf-8"))).getImageData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
    }

    private static String responsive(Display d) {
        switch (ScreenUtils.getScreenType(d)) {
        case LC.SCREEN_LARGE:
            return RC.LARGE_SUFFIX;
        case LC.SCREEN_MEDIUM:
            return RC.MEDIUM_SUFFIX;
        case LC.SCREEN_SMALL:
        default:
            return RC.SMALL_SUFFIX;
        }
    }
}
