package com.mocktpo.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;

public class ReportUtils {

    /* Logger */

    protected static final Logger logger = LogManager.getLogger();

    public static void export(String fullDestPDFFileName) {
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
            doc.save(fullDestPDFFileName);
            doc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
