package com.pp100.seal.utils;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.pdf.PDFEncryption;

import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;


public class PdfGenerator {
    private static String LOGO_PATH ="template/protocol/water.png";
    private static String LOGO_PATH_YBFKJ ="template/protocol/ybfkj/water.png";

    private static String FONT_PATH_ARIALUNI="template/pdf/arialuni.ttf";
    private static String FONT_PATH_MSYH="template/pdf/MSYH.TTC";
    private static String FONT_PATH_MSYHBD="template/pdf/MSYHBD.TTC";
    private static String FONT_PATH_MSYHL="template/pdf/MSYHL.TTC";

    /**
     * Output a pdf to the specified outputstream
     * @param htmlStr the htmlstr
     * @param out the specified outputstream
     * @throws Exception
     */
    public static void generate(String htmlStr, File tempFile)
            throws Exception {
        //创建临时输出文件
        OutputStream out = null;
        out = new FileOutputStream(tempFile);
        
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = builder.parse(new ByteArrayInputStream(htmlStr.getBytes("UTF-8")));
        
        ITextRenderer renderer = new ITextRenderer();
        
        PDFEncryption pdfEncryption = new PDFEncryption(null,null,PdfWriter.ALLOW_ASSEMBLY);  
        renderer.setPDFEncryption(pdfEncryption);
        
        String basePath = PdfGenerator.class.getResource("/").toString();
        String waterImagePath = basePath + LOGO_PATH;
        
        ITextFontResolver fontResolver = renderer.getFontResolver();
        fontResolver.addFont(basePath + FONT_PATH_ARIALUNI, BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
        fontResolver.addFont(basePath + FONT_PATH_MSYH, BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
        fontResolver.addFont(basePath + FONT_PATH_MSYHBD, BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
        fontResolver.addFont(basePath + FONT_PATH_MSYHL, BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
        
        renderer.setDocument(doc, null);
        renderer.getSharedContext().setBaseURL(waterImagePath);
        renderer.layout();
        renderer.createPDF(out);
        out.close();
    }
    
    /**
     * Output a pdf to the specified outputstream (no verify)
     * 
     * @param htmlStr the htmlstr
     * @param out the specified outputstream
     * @throws Exception
     */
    public static void generateNoVerify2ESign(String htmlStr, File tempFile, String waterImage)
            throws Exception {
        //创建临时输出文件
        OutputStream out = null;
        out = new FileOutputStream(tempFile);
        
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = builder.parse(new ByteArrayInputStream(htmlStr.getBytes("UTF-8")));
        
        ITextRenderer renderer = new ITextRenderer();
        
        //PDFEncryption pdfEncryption = new PDFEncryption(null,null,PdfWriter.ALLOW_ASSEMBLY);  
        //renderer.setPDFEncryption(pdfEncryption);
        
        String basePath = PdfGenerator.class.getResource("/").toString();
        String waterImagePath = basePath + LOGO_PATH;
        
        if ("YBFKJ_WATER".equals(waterImage)) { // 壹佰分科技
            waterImagePath = basePath + LOGO_PATH_YBFKJ;
        }
        
        ITextFontResolver fontResolver = renderer.getFontResolver();
        fontResolver.addFont(basePath + FONT_PATH_ARIALUNI, BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
        fontResolver.addFont(basePath + FONT_PATH_MSYH, BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
        fontResolver.addFont(basePath + FONT_PATH_MSYHBD, BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
        fontResolver.addFont(basePath + FONT_PATH_MSYHL, BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
        
        renderer.setDocument(doc, null);
        renderer.getSharedContext().setBaseURL(waterImagePath);
        renderer.layout();
        renderer.createPDF(out);
        out.close();
    }
}