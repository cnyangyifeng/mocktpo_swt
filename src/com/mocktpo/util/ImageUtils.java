package com.mocktpo.util;

import com.mocktpo.util.constants.RC;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageDataProvider;
import org.eclipse.swt.widgets.Display;

import java.net.URLDecoder;

public class ImageUtils {

    /* Logger */

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
                            return new Image(Display.getCurrent(), ImageUtils.class.getResourceAsStream(URLDecoder.decode(RC.IMAGES_DIR + fileName + RC.HIDPI_SUFFIX + RC.PNG_FILE_TYPE_SUFFIX, "utf-8"))).getImageData();
                        case 100:
                        default:
                            return new Image(Display.getCurrent(), ImageUtils.class.getResourceAsStream(URLDecoder.decode(RC.IMAGES_DIR + fileName + RC.PNG_FILE_TYPE_SUFFIX, "utf-8"))).getImageData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
    }
}
