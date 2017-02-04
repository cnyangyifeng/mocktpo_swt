package com.mocktpo.util;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class UnzipUtils {

    private UnzipUtils() {
    }

    public static void unzip(ZipInputStream zis, String localPath) throws Exception {
        ZipEntry entry = zis.getNextEntry();
        while (entry != null) {
            File file = new File(localPath, entry.getName());
            if (entry.isDirectory()) {
                file.mkdirs();
            } else {
                OutputStream fos = new FileOutputStream(file);
                IOUtils.copy(zis, fos);
                IOUtils.closeQuietly(fos);
            }
            zis.closeEntry();
            entry = zis.getNextEntry();
        }
    }
}
