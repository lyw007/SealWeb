package com.pp100.seal.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import javax.imageio.ImageIO;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

public class PDFFileUtil {
    private static Logger logger = Logger.getLogger(PDFFileUtil.class);

    //判断是不是PDF文件
    public static final boolean isPdf(MultipartFile pdfFile, String suffixName) {
        String type = getSuffixName(suffixName);
        if (type == null) {
            return false;
        }
        if (!"GIF,JPG,JPEG,PNG,BMP".contains(type.toUpperCase())) {
            return false;
        }
        try (InputStream in = pdfFile.getInputStream()) {
            BufferedImage bufferReader = ImageIO.read(in);
            return bufferReader.getWidth() > 0 && bufferReader.getHeight() > 0;
        } catch (IOException e) {
            return false;
        }
    }

    //获取文件后缀名
    public static final String getSuffixName(String fileName) {
        if (fileName == null || "".equals(fileName)) {
            return null;
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
    
    //生成文件
    public static String upload(MultipartFile file, String path, String suffixName) {
        String uuid = UUID.randomUUID().toString() + suffixName;
        try {
            InputStream is = file.getInputStream();
            FileOutputStream os = new FileOutputStream(new File(path + uuid));
            IOUtils.copy(is, os);
        } catch (Exception e) {
            logger.error("上传文件出现异常", e);
        }
        return uuid;
    }
}
