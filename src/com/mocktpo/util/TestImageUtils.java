package com.mocktpo.util;

import com.mocktpo.orm.domain.UserTest;
import com.mocktpo.util.constants.RC;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageDataProvider;
import org.eclipse.swt.widgets.Display;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class TestImageUtils {

    protected static final Logger logger = LogManager.getLogger();

    private TestImageUtils() {
    }

    public static List<Image> load(final Display d, final UserTest ut, final String fileNames) {
        List<Image> images = new ArrayList<Image>();
        String[] arr = fileNames.split(";");
        for (final String fileName : arr) {
            final Image image = new Image(d, new ImageDataProvider() {
                @Override
                public ImageData getImageData(int zoom) {
                    try {
                        switch (zoom) {
                            case 200:
                                return new Image(Display.getCurrent(), TestImageUtils.class.getResourceAsStream(URLDecoder.decode(RC.TESTS_DATA_DIR + ut.getAlias() + "/" + fileName + RC.HIDPI_SUFFIX + RC.PNG_FILE_TYPE_SUFFIX, "utf-8"))).getImageData();
                            case 100:
                            default:
                                return new Image(Display.getCurrent(), TestImageUtils.class.getResourceAsStream(URLDecoder.decode(RC.TESTS_DATA_DIR + ut.getAlias() + "/" + fileName + RC.PNG_FILE_TYPE_SUFFIX, "utf-8"))).getImageData();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            });
            images.add(image);
        }
        return images;
    }

    public static void unload(List<Image> images) {
        for (Image image : images) {
            image.dispose();
        }
    }
}
