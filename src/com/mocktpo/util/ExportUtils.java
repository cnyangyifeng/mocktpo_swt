package com.mocktpo.util;

import com.mocktpo.util.constants.MT;
import com.mocktpo.util.constants.RC;
import com.mocktpo.vo.TestEditorVo;
import com.mocktpo.vo.TestViewVo;
import com.mocktpo.vo.TestVo;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ExportUtils {

    /* Logger */

    protected static final Logger logger = LogManager.getLogger();

    public static void exportTestRecordAsPdf(String fullDestPdfFileName) {
        PDDocument doc = new PDDocument();
        try {
            PDPage page = new PDPage();
            doc.addPage(page);
            PDPageContentStream cs = new PDPageContentStream(doc, page);
            cs.beginText();
            cs.setFont(PDType1Font.HELVETICA, 14);
            cs.newLineAtOffset(50, 700);
            cs.showText("HELLO WORLD");
            cs.endText();
            cs.close();
            doc.save(fullDestPdfFileName);
            doc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void exportTestPaperAsZip(String fileAlias, String fullDestZipFileName) {
        try {
            File worksDir = new File(ExportUtils.class.getResource(URLDecoder.decode(RC.WORKS_DATA_DIR, "utf-8")).toURI());
            File workDir = new File(worksDir.toString() + MT.STRING_SLASH + fileAlias);
            if (!workDir.exists()) {
                return;
            }
            File outputsDir = new File(ExportUtils.class.getResource(URLDecoder.decode(RC.OUTPUTS_DATA_DIR, "utf-8")).toURI());
            File outputDir = new File(outputsDir.toString() + MT.STRING_SLASH + fileAlias);
            if (!outputDir.exists()) {
                logger.info("Test package folder created: {}.", outputDir.mkdir());
            }
            FileUtils.copyDirectory(workDir, outputDir);
            format(fileAlias);
            List<File> fileList = new ArrayList<File>();
            getAllFiles(outputDir, fileList);
            logger.info(fileList);
            zip(outputDir, fileList, fullDestZipFileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void format(String fileAlias) {
        TestEditorVo testEditorVo = JSONUtils.pullFromOutputs(fileAlias, TestEditorVo.class);
        TestVo testVo = new TestVo();
        testVo.setTid(testEditorVo.getTid());
        testVo.setTitle(testEditorVo.getTitle());
        testVo.setStars(testEditorVo.getStars());
        testVo.setAuthor(testEditorVo.getAuthor());
        testVo.setCreatedTime(testEditorVo.getCreatedTime());
        List<TestViewVo> readingViewVos = testEditorVo.getReadingViewVos();
        List<TestViewVo> listeningViewVos = testEditorVo.getListeningViewVos();
        List<TestViewVo> speakingViewVos = testEditorVo.getSpeakingViewVos();
        List<TestViewVo> writingViewVos = testEditorVo.getWritingViewVos();
        List<TestViewVo> viewVos = new ArrayList<TestViewVo>();

        int viewId = 0;
        viewVos.add(TestViewUtils.initTestIntroView(++viewId));
        viewVos.add(TestViewUtils.initGeneralTestInfoView(++viewId));
        viewVos.add(TestViewUtils.initReadingSectionDirectionsView(++viewId));

        for (TestViewVo vo : readingViewVos) {
            vo.setViewId(++viewId);
            viewVos.add(vo);
        }
        for (TestViewVo vo : listeningViewVos) {
            vo.setViewId(++viewId);
            viewVos.add(vo);
        }
        for (TestViewVo vo : speakingViewVos) {
            vo.setViewId(++viewId);
            viewVos.add(vo);
        }
        for (TestViewVo vo : writingViewVos) {
            vo.setViewId(++viewId);
            viewVos.add(vo);
        }
        viewVos.add(TestViewUtils.initBreakPointView(++viewId));
        viewVos.add(TestViewUtils.initTestEndView(++viewId));
        testVo.setViewVos(viewVos);
        JSONUtils.pushToOutputs(testVo.getTid(), testVo);
    }

    private static void getAllFiles(File parent, List<File> fileList) {
        try {
            fileList.add(parent);
            File[] files = parent.listFiles();
            for (File file : files) {
                fileList.add(file);
                if (file.isDirectory()) {
                    getAllFiles(file, fileList);
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
                    addToZip(outputDir, file, zos);
                }
            }
            zos.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addToZip(File outputDir, File file, ZipOutputStream zos) {
        try {
            FileInputStream fis = new FileInputStream(file);
            // we want the zipEntry's path to be a relative path that is relative
            // to the directory being zipped, so chop off the rest of the path.
            String zipFilePath = file.getCanonicalPath().substring(outputDir.getCanonicalPath().length() + 1, file.getCanonicalPath().length());
            ZipEntry ze = new ZipEntry(zipFilePath);
            zos.putNextEntry(ze);
            byte[] bytes = new byte[1024];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zos.write(bytes, 0, length);
            }
            zos.closeEntry();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
