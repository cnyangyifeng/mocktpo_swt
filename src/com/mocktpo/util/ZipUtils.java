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
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipUtils {

    /* Logger */

    protected static final Logger logger = LogManager.getLogger();

    private List<String> srcFileNames;

    private ZipUtils() {
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
            String rootDir = ZipUtils.class.getResource(URLDecoder.decode(RC.TESTS_DATA_DIR, "utf-8")).getPath();
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

    /*
     * ==================================================
     *
     * Zip
     *
     * ==================================================
     */

    public static void zip(String fileAlias, String zipFileName) {
//        String packageDir = UnzipUtils.class.getResource(URLDecoder.decode(RC.OUTPUTS_DATA_DIR, "utf-8")).getPath();
//
//        generateFileList(sourcePath);
//
//        byte[] buffer = new byte[1024];
//        try {
//            FileOutputStream fos = new FileOutputStream(zipFileName);
//            ZipOutputStream zos = new ZipOutputStream(fos);
//            for (String file : sourceFileNames) {
//                ZipEntry ze = new ZipEntry(file);
//                zos.putNextEntry(ze);
//                FileInputStream in = new FileInputStream(sourcePath + File.separator + file);
//                int len;
//                while ((len = in.read(buffer)) > 0) {
//                    zos.write(buffer, 0, len);
//                }
//                in.close();
//            }
//            zos.closeEntry();
//            zos.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    private static void generateFileList(File node) {
//        if (node.isFile()) {
//            fileList.add(generateZipEntry(node.getAbsoluteFile().toString()));
//        }
//        if (node.isDirectory()) {
//            String[] subNote = node.list();
//            for (String filename : subNote) {
//                generateFileList(new File(node, filename));
//            }
//        }
    }
}
