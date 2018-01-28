package com.mocktpo.util;

import com.alibaba.fastjson.JSON;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.constants.RC;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.net.URL;
import java.net.URLDecoder;

public class ConfigUtils {

    /* Logger */

    protected static final Logger logger = LogManager.getLogger();

    private ConfigUtils() {
    }

    public static <T> T pullFromTestBaseDir(String fileAlias, Class<T> clazz) {
        if (fileAlias == null) {
            return null;
        }
        try {
            URL url = ConfigUtils.class.getResource(URLDecoder.decode(RC.TEST_BASE_DIR + fileAlias + MT.STRING_SLASH + fileAlias + RC.JSON_FILE_TYPE_SUFFIX, "utf-8"));
            String json = FileUtils.readFileToString(new File(url.toURI()), "utf-8");
            return JSON.parseObject(json, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T pullFromEditorBaseDir(String fileAlias, Class<T> clazz) {
        if (fileAlias == null) {
            return null;
        }
        try {
            URL url = ConfigUtils.class.getResource(URLDecoder.decode(RC.EDITOR_BASE_DIR + fileAlias + MT.STRING_SLASH + fileAlias + RC.JSON_FILE_TYPE_SUFFIX, "utf-8"));
            String json = FileUtils.readFileToString(new File(url.toURI()), "utf-8");
            return JSON.parseObject(json, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void pushToEditorBaseDir(String fileAlias, Object object) {
        if (fileAlias == null) {
            return;
        }
        try {
            File editorBaseDir = new File(ConfigUtils.class.getResource(URLDecoder.decode(RC.EDITOR_BASE_DIR, "utf-8")).toURI());
            File testDir = new File(editorBaseDir.toString() + MT.STRING_SLASH + fileAlias);
            if (!testDir.exists()) {
                logger.info("Test directory created in editor base dir: {}.", testDir.mkdir());
            }
            File file = new File(testDir.toString() + MT.STRING_SLASH + fileAlias + RC.JSON_FILE_TYPE_SUFFIX);
            if (!file.exists()) {
                logger.info("- Test description file created: {}.", file.createNewFile());
            }
            FileUtils.writeStringToFile(file, JSON.toJSONString(object), "utf-8", false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
