package com.mocktpo.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageDataProvider;
import org.eclipse.swt.widgets.Display;

import com.mocktpo.util.constants.ResourceConstants;

public class ImageUtils {

    protected static final Logger logger = LogManager.getLogger();

    private ImageUtils() {
    }

    public static Image load(Display d, String fileName) {
        return new Image(d, new ImageDataProvider() {
            @Override
            public ImageData getImageData(int zoom) {
                switch (zoom) {
                case 200:
                    return new Image(Display.getCurrent(), ImageUtils.class.getResourceAsStream(ResourceConstants.IMAGES_DIR + ResourceConstants.HIDPI_IMAGE_PREFIX + fileName)).getImageData();
                case 100:
                default:
                    return new Image(Display.getCurrent(), ImageUtils.class.getResourceAsStream(ResourceConstants.IMAGES_DIR + fileName)).getImageData();
                }
            }
        });
    }
}
