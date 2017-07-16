package com.mocktpo.util;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import java.io.IOException;

public class PDFUtils {

    public static void save(String absoluteFileName) {
        PDDocument doc = new PDDocument();

        PDPage page = new PDPage();
        doc.addPage(page);

        try {
            doc.save(absoluteFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
