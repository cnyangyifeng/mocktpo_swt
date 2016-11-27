package com.mocktpo.util;

import com.alibaba.fastjson.JSON;
import com.mocktpo.util.constants.RC;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.net.URL;
import java.net.URLDecoder;

public class ConfigUtils {

    private static final Logger logger = LogManager.getLogger();

    private ConfigUtils() {
    }

    public static <T> T load(String testAlias, Class<T> clazz) {
        try {
            URL url = ConfigUtils.class.getResource(URLDecoder.decode(RC.TESTS_DATA_ROOT_DIR + testAlias + "/" + testAlias + RC.JSON_FILE_TYPE_SUFFIX, "utf-8"));
            String json = FileUtils.readFileToString(new File(url.toURI()), "utf-8");
            return JSON.parseObject(json, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void save(String testAlias, Object object) {
        try {
            URL url = ConfigUtils.class.getResource(URLDecoder.decode(RC.TESTS_DATA_ROOT_DIR + testAlias + "/" + testAlias + RC.JSON_FILE_TYPE_SUFFIX, "utf-8"));
            File file = new File(url.toURI());
            if (file.createNewFile()) {
                logger.debug("File '" + testAlias + RC.JSON_FILE_TYPE_SUFFIX + "' saved successfully.");
            }
            FileUtils.writeStringToFile(file, JSON.toJSONString(object), "utf-8", false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
