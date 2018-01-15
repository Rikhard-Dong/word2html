package io.ride;

import jdk.internal.util.xml.impl.Input;
import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.*;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Word2Pdf {
    public static void main(String[] args) throws IOException {
        String filepath = "/home/ride/文档/设备试运申请单（新版）.docx";
        String outpath = "/home/ride/test.pdf";

        InputStream source = new FileInputStream(filepath);
        OutputStream target = new FileOutputStream(outpath);
        Map<String, String> params = new HashMap<String, String>();

        PdfOptions options = PdfOptions.create();

        wordConverterToPdf(source, target, options, params);
    }

    private static void wordConverterToPdf(InputStream source, OutputStream target, PdfOptions options, Map<String, String> params)
            throws IOException {
        XWPFDocument doc = new XWPFDocument(source);
        paragraphReplace(doc.getParagraphs(), params);
        for (XWPFTable table : doc.getTables()) {
            for (XWPFTableRow row : table.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    paragraphReplace(cell.getParagraphs(), params);
                }
            }
        }
        PdfConverter.getInstance().convert(doc, target, options);
    }

    private static void paragraphReplace(List<XWPFParagraph> paragraphs, Map<String, String> params) {

    }
}
