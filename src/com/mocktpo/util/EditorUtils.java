package com.mocktpo.util;

import com.mocktpo.util.constants.MT;
import com.mocktpo.util.constants.RC;
import com.mocktpo.util.constants.VT;
import com.mocktpo.vo.TestEditorVo;
import com.mocktpo.vo.TestViewVo;
import com.mocktpo.vo.TestVo;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class EditorUtils {

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    public static void validate(String fileAlias) {
        if (fileAlias == null) {
            return;
        }
        try {
            File editorDataDir = new File(ConfigUtils.class.getResource(URLDecoder.decode(RC.EDITOR_BASE_DIR, "utf-8")).toURI());
            File testPaperDir = new File(editorDataDir.toString() + MT.STRING_SLASH + fileAlias);
            if (!testPaperDir.exists()) {
                logger.info("Test paper directory created: {}.", testPaperDir.mkdir());
            }
            File descFile = new File(testPaperDir.toString() + MT.STRING_SLASH + fileAlias + RC.JSON_FILE_TYPE_SUFFIX);
            if (!descFile.exists()) {
                logger.info(" Test paper description file created: {}.", descFile.createNewFile());
            }
            File listeningDir = new File(testPaperDir.toString() + MT.STRING_SLASH + msgs.getString("listening"));
            if (!listeningDir.exists()) {
                logger.info(" Listening directory created: {}.", listeningDir.mkdir());
                File readmeFile = new File(listeningDir.toString() + MT.STRING_SLASH + RC.README_FILE);
                if (!readmeFile.exists()) {
                    logger.info("  Listening directory readme file created: {}.", readmeFile.createNewFile());
                }
            }
            File speakingDir = new File(testPaperDir.toString() + MT.STRING_SLASH + msgs.getString("speaking"));
            if (!speakingDir.exists()) {
                logger.info(" Speaking directory created: {}.", speakingDir.mkdir());
                File readmeFile = new File(speakingDir.toString() + MT.STRING_SLASH + RC.README_FILE);
                if (!readmeFile.exists()) {
                    logger.info("  Speaking directory readme file created: {}.", readmeFile.createNewFile());
                }
            }
            File writingDir = new File(testPaperDir.toString() + MT.STRING_SLASH + msgs.getString("writing"));
            if (!writingDir.exists()) {
                logger.info(" Writing directory created: {}.", writingDir.mkdir());
                File readmeFile = new File(writingDir.toString() + MT.STRING_SLASH + RC.README_FILE);
                if (!readmeFile.exists()) {
                    logger.info("  Writing directory readme file created: {}.", readmeFile.createNewFile());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
