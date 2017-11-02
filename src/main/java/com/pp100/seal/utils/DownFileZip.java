package com.pp100.seal.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.pp100.utils.Util;

public class DownFileZip {
    private static final Logger logger = Logger.getLogger(DownFileZip.class);

    public static File zipFile(String contractName, List<File> files) {
        if (Util.isNone(files)) {
            return null;
        }
        File zipFile = null;
        ZipOutputStream zipStream = null;
        FileInputStream zipSource = null;
        try {
            zipFile = File.createTempFile(contractName, ".zip");
            zipStream = new ZipOutputStream(new FileOutputStream(zipFile));
            for (int i = 0; i < files.size(); i++) {
                File file = files.get(i);
                zipSource = new FileInputStream(file);
                ZipEntry zipEntry = new ZipEntry(file.getName());
                zipStream.putNextEntry(zipEntry);
                IOUtils.write(IOUtils.toByteArray(zipSource), zipStream);
            }
        } catch (Exception e) {
            logger.error("合同压缩异常！contractName=" + contractName, e);
        } finally {
            try {
                if (null != zipStream) {
                    zipStream.close();
                }
                if (null != zipSource) {
                    zipSource.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return zipFile;
    }

    public static File zipFile(String contractName, String[] path) {
        if (path == null || path.length <= 0) {
            return null;
        }
        File zipFile = null;
        ZipOutputStream zipStream = null;
        FileInputStream zipSource = null;
        try {
            zipFile = File.createTempFile(contractName, ".zip");
            zipStream = new ZipOutputStream(new FileOutputStream(zipFile));
            for (int i = 0; i < path.length; i++) {
                File file = new File(path[i]);
                zipSource = new FileInputStream(file);
                ZipEntry zipEntry = new ZipEntry(file.getName());
                zipStream.putNextEntry(zipEntry);
                IOUtils.write(IOUtils.toByteArray(zipSource), zipStream);
            }
        } catch (Exception e) {
            logger.error("合同压缩异常！contractName=" + contractName, e);
        } finally {
            try {
                if (null != zipStream) {
                    zipStream.close();
                }
                if (null != zipSource) {
                    zipSource.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return zipFile;
    }
}
