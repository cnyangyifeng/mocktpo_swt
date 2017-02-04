package com.mocktpo.util;

import com.mocktpo.orm.domain.UserTestSession;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.constants.RC;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageDataProvider;
import org.eclipse.swt.widgets.Display;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class IllustrationUtils {

    /* Logger */

    protected static final Logger logger = LogManager.getLogger();

    private IllustrationUtils() {
    }

    public static Map<Integer, Image> load(final Display d, final UserTestSession userTestSession, final String fileNames) {
        Map<Integer, Image> map = new HashMap<Integer, Image>();
        String[] arr = fileNames.split(MT.STRING_SEMICOLON);
        for (final String fileNameWithLocation : arr) {
            final String[] fa = fileNameWithLocation.split(MT.STRING_COLON);
            final Image image = new Image(d, new ImageDataProvider() {
                @Override
                public ImageData getImageData(int zoom) {
                    ImageData data = null;
                    switch (zoom) {
                        case 200:
                            try {
                                data = new Image(Display.getCurrent(), IllustrationUtils.class.getResourceAsStream(URLDecoder.decode(RC.TESTS_DATA_DIR + userTestSession.getFileAlias() + MT.STRING_SLASH + fa[0] + RC.HIDPI_SUFFIX + RC.PNG_FILE_TYPE_SUFFIX, "utf-8"))).getImageData();
                            } catch (Exception e) {
                                logger.info("Image \"{}{}{}\" not found.", fa[0], RC.HIDPI_SUFFIX, RC.PNG_FILE_TYPE_SUFFIX);
                            }
                            break;
                        case 100:
                        default:
                            try {
                                data = new Image(Display.getCurrent(), IllustrationUtils.class.getResourceAsStream(URLDecoder.decode(RC.TESTS_DATA_DIR + userTestSession.getFileAlias() + MT.STRING_SLASH + fa[0] + RC.PNG_FILE_TYPE_SUFFIX, "utf-8"))).getImageData();
                            } catch (Exception e) {
                                logger.info("Image \"{}{}\" not found.", fa[0], RC.PNG_FILE_TYPE_SUFFIX);
                            }
                    }
                    return data;
                }
            });
            map.put(Integer.parseInt(fa[1]), image);
        }
        return map;
    }
}
