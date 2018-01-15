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

    public static void export(String fileAlias, String fullDestZipFileName) {
        try {
            File editorDataDir = new File(EditorUtils.class.getResource(URLDecoder.decode(RC.EDITOR_BASE_DIR, "utf-8")).toURI());
            File testPaperDir = new File(editorDataDir.toString() + MT.STRING_SLASH + fileAlias);
            if (!testPaperDir.exists()) {
                return;
            }
            File storeDataDir = new File(EditorUtils.class.getResource(URLDecoder.decode(RC.STORE_BASE_DIR, "utf-8")).toURI());
            File outputDir = new File(storeDataDir.toString() + MT.STRING_SLASH + fileAlias);
            if (!outputDir.exists()) {
                logger.info("Test paper directory created: {}.", outputDir.mkdir());
            }
            FileUtils.copyDirectory(testPaperDir, outputDir);
            format(fileAlias);
            List<File> fileList = new ArrayList<>();
            collect(outputDir, fileList);
            zip(outputDir, fileList, fullDestZipFileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void format(String fileAlias) {
        /* Initializes a testEditorVo and a testVo */
        TestEditorVo testEditorVo = ConfigUtils.pullFromStoreBaseDir(fileAlias, TestEditorVo.class);
        TestVo testVo = new TestVo();
        testVo.setTid(testEditorVo.getTid());
        testVo.setTagId(testEditorVo.getTagId());
        testVo.setTitle(testEditorVo.getTitle());
        testVo.setStars(testEditorVo.getStars());
        testVo.setCreator(testEditorVo.getCreator());
        testVo.setCreatedTime(testEditorVo.getCreatedTime());
        testVo.setCreatedTime(testEditorVo.getUpdatedTime());
        testVo.setVersion(testEditorVo.getVersion());
        List<TestViewVo> readingViewVos = testEditorVo.getReadingViewVos();
        List<TestViewVo> listeningViewVos = testEditorVo.getListeningViewVos();
        List<TestViewVo> speakingViewVos = testEditorVo.getSpeakingViewVos();
        List<TestViewVo> writingViewVos = testEditorVo.getWritingViewVos();
        /* Generates viewVos */
        List<TestViewVo> viewVos = new ArrayList<>();
        int viewId = 0;
        viewVos.add(TestViewVoUtils.initTestIntroViewVo(++viewId));
        viewVos.add(TestViewVoUtils.initGeneralTestInfoViewVo(++viewId));
        viewVos.add(TestViewVoUtils.initReadingSectionDirectionsViewVo(++viewId));
        /* Adds readingViewVos to viewVos */
        boolean firstPassage = false;
        int readingQuestionNumber = 0;
        for (TestViewVo vo : readingViewVos) {
            vo.setViewId(++viewId);
            switch (vo.getViewType()) {
                case VT.VIEW_TYPE_READING_PASSAGE:
                    if (!firstPassage) {
                        vo.setFirstPassage(true);
                        firstPassage = true;
                    }
                    break;
                case VT.VIEW_TYPE_READING_MULTIPLE_CHOICE_QUESTION:
                    vo.setQuestionNumberInSection(++readingQuestionNumber);
                    TestViewVoUtils.updatePassageOffset(vo);
                    break;
                case VT.VIEW_TYPE_READING_INSERT_TEXT_QUESTION:
                    vo.setQuestionNumberInSection(++readingQuestionNumber);
                    TestViewVoUtils.updateInsertionPoints(vo);
                    TestViewVoUtils.updatePassageOffset(vo);
                    break;
                case VT.VIEW_TYPE_READING_PROSE_SUMMARY_QUESTION:
                    vo.setQuestionNumberInSection(++readingQuestionNumber);
                    break;
                case VT.VIEW_TYPE_READING_FILL_IN_A_TABLE_QUESTION:
                    vo.setQuestionNumberInSection(++readingQuestionNumber);
                    break;
            }
            viewVos.add(vo);
        }
        viewVos.add(TestViewVoUtils.initReadingSectionEndViewVo(++viewId));
        viewVos.add(TestViewVoUtils.initListeningHeadsetOnViewVo(++viewId));
        viewVos.add(TestViewVoUtils.initChangingVolumeViewVo(++viewId));
        viewVos.add(TestViewVoUtils.initListeningSectionDirectionsViewVo(++viewId));
        /* Adds listeningViewVos to viewVos */
        for (TestViewVo vo : listeningViewVos) {
            vo.setViewId(++viewId);
            viewVos.add(vo);
        }
        // TODO START
        viewVos.add(TestViewVoUtils.initListeningDirectionsViewVo(++viewId));
        // TODO END
        viewVos.add(TestViewVoUtils.initListeningSectionEndViewVo(++viewId));
        viewVos.add(TestViewVoUtils.initBreakPointViewVo(++viewId));
        viewVos.add(TestViewVoUtils.initSpeakingHeadsetOnViewVo(++viewId));
        viewVos.add(TestViewVoUtils.initAdjustingMicrophoneViewVo(++viewId));
        viewVos.add(TestViewVoUtils.initSpeakingSectionDirectionsViewVo(++viewId));
        /* Adds speakingViewVos to viewVos */
        for (TestViewVo vo : speakingViewVos) {
            vo.setViewId(++viewId);
            viewVos.add(vo);
        }
        viewVos.add(TestViewVoUtils.initSpeakingSectionEndViewVo(++viewId));
        viewVos.add(TestViewVoUtils.initWritingSectionDirectionsViewVo(++viewId));
        // TODO START
        viewVos.add(TestViewVoUtils.initIntegratedWritingDirectionsViewVo(++viewId));
        viewVos.add(TestViewVoUtils.initIntegratedWritingTaskEndViewVo(++viewId));
        // TODO END
        /* Adds writingViewVos to viewVos */
        for (TestViewVo vo : writingViewVos) {
            vo.setViewId(++viewId);
            viewVos.add(vo);
        }
        // TODO START
        viewVos.add(TestViewVoUtils.initIndependentWritingDirectionsViewVo(++viewId));
        viewVos.add(TestViewVoUtils.initIndependentWritingTaskEndViewVo(++viewId));
        // TODO END
        viewVos.add(TestViewVoUtils.initTestEndViewVo(++viewId));
        /* Sets viewVos to testVo */
        testVo.setViewVos(viewVos);
        /* Saves testVo */
        ConfigUtils.pushToTestBaseDir(testVo.getTid(), testVo);
    }

    private static void collect(File parent, List<File> fileList) {
        try {
            File[] files = parent.listFiles();
            if (files == null) {
                return;
            }
            for (File file : files) {
                fileList.add(file);
                if (file.isDirectory()) {
                    collect(file, fileList);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void zip(File outputDir, List<File> fileList, String fullDestZipFileName) {
        try {
            FileOutputStream fos = new FileOutputStream(fullDestZipFileName);
            ZipOutputStream zos = new ZipOutputStream(fos);
            for (File file : fileList) {
                if (!file.isDirectory()) {
                    String zipFilePath = file.getCanonicalPath().substring(outputDir.getParentFile().getCanonicalPath().length() + 1, file.getCanonicalPath().length());
                    ZipEntry ze = new ZipEntry(zipFilePath);
                    zos.putNextEntry(ze);
                    FileInputStream fis = new FileInputStream(file);
                    byte[] bytes = new byte[1024];
                    int length;
                    while ((length = fis.read(bytes)) > 0) {
                        zos.write(bytes, 0, length);
                    }
                    zos.closeEntry();
                    fis.close();
                }
            }
            zos.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
