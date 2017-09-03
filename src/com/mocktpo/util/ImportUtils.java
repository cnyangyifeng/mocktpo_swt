package com.mocktpo.util;

import com.mocktpo.util.constants.RC;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ImportUtils {

    /* Logger */

    protected static final Logger logger = LogManager.getLogger();

    private ImportUtils() {
    }

    /*
     * ==================================================
     *
     * Unzip
     *
     * ==================================================
     */

    public static void unzip(String fullSrcZipFileName) {
        if (fullSrcZipFileName == null) {
            return;
        }
        try {
            ZipInputStream zis = new ZipInputStream(new FileInputStream(fullSrcZipFileName));
            File testsDir = new File(ImportUtils.class.getResource(URLDecoder.decode(RC.TESTS_DATA_DIR, "utf-8")).toURI());
            ZipEntry ze = zis.getNextEntry();
            while (ze != null) {
                File file = new File(testsDir, ze.getName());
                if (file.isDirectory()) {
                    logger.debug("Directory created: {}.", file.mkdirs());
                } else {
                    if (!file.exists()) {
                        file.getParentFile().mkdirs();
                        file.createNewFile();
                    }
                    OutputStream fos = new FileOutputStream(file);
                    IOUtils.copy(zis, fos);
                    IOUtils.closeQuietly(fos);
                }
                zis.closeEntry();
                ze = zis.getNextEntry();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
