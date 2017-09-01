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
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class ExportUtils {

    /* Logger */

    protected static final Logger logger = LogManager.getLogger();

    public static void exportTestRecordAsPdf(String fullDestFileName) {
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
            doc.save(fullDestFileName);
            doc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void exportTestPaperAsZip(String fullDestFileName, String fileAlias) {
        logger.info(fullDestFileName);
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
        viewVos.addAll(readingViewVos);
        viewVos.addAll(listeningViewVos);
        viewVos.addAll(speakingViewVos);
        viewVos.addAll(writingViewVos);
        testVo.setViewVos(viewVos);
        JSONUtils.pushToOutputs(testVo.getTid(), testVo);
    }
}
