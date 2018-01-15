package io.ride.util;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

public final class DocUtil {

    public static String doc2Html(InputStream inputStream, String path) throws Exception {
        if (!new File(path).exists()) {
            return null;
        }
        System.out.println("method in ======> doc2Html");

        HWPFDocument hwpfDocument = new HWPFDocument(inputStream);
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(document);

        wordToHtmlConverter.processDocument(hwpfDocument);
        Document htmlDocument = wordToHtmlConverter.getDocument();
        DOMSource domSource = new DOMSource(htmlDocument);

        // 保存到临时目录
        String targetFilename = path + "/" + UUIDUtil.getUUID() + ".html";
        StreamResult streamResult = new StreamResult(new File(targetFilename));

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.METHOD, "html");
        transformer.transform(domSource, streamResult);

        String content = readHtmlContent(targetFilename);
        // System.out.println(content);

        // 取得html代码后将html文件删除
        File file = new File(targetFilename);
        if (file.exists()) {
            if (!file.delete()) {
                System.out.println("Doc util =======> delete temp html file fail");
            }
        }

        return content;
    }

    private static String readHtmlContent(String filename) {
        File file = new File(filename);
        Long length = file.length();
        byte[] content = new byte[length.intValue()];

        try {
            FileInputStream in = new FileInputStream(file);
            in.read(content);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return new String(content);
    }
}
