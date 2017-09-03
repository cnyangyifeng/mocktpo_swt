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

public class JSONSUtils {

    /* Logger */

    protected static final Logger logger = LogManager.getLogger();

    private JSONSUtils() {
    }

    public static <T> T pullFromTest(String fileAlias, Class<T> clazz) {
        if (fileAlias == null) {
            return null;
        }
        try {
            URL url = JSONSUtils.class.getResource(URLDecoder.decode(RC.TESTS_DATA_DIR + fileAlias + MT.STRING_SLASH + fileAlias + RC.JSON_FILE_TYPE_SUFFIX, "utf-8"));
            String json = FileUtils.readFileToString(new File(url.toURI()), "utf-8");
            return JSON.parseObject(json, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T pullFromProject(String fileAlias, Class<T> clazz) {
        if (fileAlias == null) {
            return null;
        }
        try {
            URL url = JSONSUtils.class.getResource(URLDecoder.decode(RC.PROJECTS_DATA_DIR + fileAlias + MT.STRING_SLASH + fileAlias + RC.JSON_FILE_TYPE_SUFFIX, "utf-8"));
            String json = FileUtils.readFileToString(new File(url.toURI()), "utf-8");
            return JSON.parseObject(json, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T pullFromOutput(String fileAlias, Class<T> clazz) {
        if (fileAlias == null) {
            return null;
        }
        try {
            URL url = JSONSUtils.class.getResource(URLDecoder.decode(RC.OUTPUTS_DATA_DIR + fileAlias + MT.STRING_SLASH + fileAlias + RC.JSON_FILE_TYPE_SUFFIX, "utf-8"));
            String json = FileUtils.readFileToString(new File(url.toURI()), "utf-8");
            return JSON.parseObject(json, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void pushToProject(String fileAlias, Object object) {
        if (fileAlias == null) {
            return;
        }
        try {
            File projectsDir = new File(JSONSUtils.class.getResource(URLDecoder.decode(RC.PROJECTS_DATA_DIR, "utf-8")).toURI());
            File projectDir = new File(projectsDir.toString() + MT.STRING_SLASH + fileAlias);
            if (!projectDir.exists()) {
                logger.info("Test paper directory created: {}.", projectDir.mkdir());
            }
            File file = new File(projectDir.toString() + MT.STRING_SLASH + fileAlias + RC.JSON_FILE_TYPE_SUFFIX);
            if (!file.exists()) {
                logger.info("- Test paper description file created: {}.", file.createNewFile());
            }
            FileUtils.writeStringToFile(file, JSON.toJSONString(object), "utf-8", false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void pushToOutput(String fileAlias, Object object) {
        if (fileAlias == null) {
            return;
        }
        try {
            File outputsDir = new File(JSONSUtils.class.getResource(URLDecoder.decode(RC.OUTPUTS_DATA_DIR, "utf-8")).toURI());
            File outputDir = new File(outputsDir.toString() + MT.STRING_SLASH + fileAlias);
            if (!outputDir.exists()) {
                logger.info("Test paper directory created: {}.", outputDir.mkdir());
            }
            File file = new File(outputDir.toString() + MT.STRING_SLASH + fileAlias + RC.JSON_FILE_TYPE_SUFFIX);
            if (!file.exists()) {
                logger.info("- Test paper description file created: {}.", file.createNewFile());
            }
            FileUtils.writeStringToFile(file, JSON.toJSONString(object), "utf-8", false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
