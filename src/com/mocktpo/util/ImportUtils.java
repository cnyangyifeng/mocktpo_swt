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
            String testsDirName = ImportUtils.class.getResource(URLDecoder.decode(RC.TESTS_DATA_DIR, "utf-8")).getPath();
            ZipEntry entry = zis.getNextEntry();
            while (entry != null) {
                File file = new File(testsDirName, entry.getName());
                if (entry.isDirectory()) {
                    logger.debug("Directory created for unzipping the file: {}.", file.mkdirs());
                } else {
                    OutputStream fos = new FileOutputStream(file);
                    IOUtils.copy(zis, fos);
                    IOUtils.closeQuietly(fos);
                }
                zis.closeEntry();
                entry = zis.getNextEntry();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
