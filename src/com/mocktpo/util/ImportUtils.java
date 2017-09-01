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

    public static void unzip(String zipFileName) {
        if (zipFileName == null) {
            return;
        }
        try {
            ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFileName));
            String rootDir = ImportUtils.class.getResource(URLDecoder.decode(RC.TESTS_DATA_DIR, "utf-8")).getPath();
            unzip(zis, rootDir);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void unzip(ZipInputStream zipInputStream, String packageDirName) throws Exception {
        ZipEntry entry = zipInputStream.getNextEntry();
        while (entry != null) {
            File file = new File(packageDirName, entry.getName());
            if (entry.isDirectory()) {
                logger.debug("Directory created for unzipping the file: {}.", file.mkdirs());
            } else {
                OutputStream fos = new FileOutputStream(file);
                IOUtils.copy(zipInputStream, fos);
                IOUtils.closeQuietly(fos);
            }
            zipInputStream.closeEntry();
            entry = zipInputStream.getNextEntry();
        }
    }
}
