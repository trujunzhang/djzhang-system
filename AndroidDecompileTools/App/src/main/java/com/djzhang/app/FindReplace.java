package com.djzhang.app;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class FindReplace {

    public Map<String, String> resources = new HashMap<String, String>(); // key:id value:R.id.bg

    // "D:/qq"
    private static final String APK_PUBLIC_XML_FOLD =
            "/Volumes/SHARE/MacSystem/Home/Users/djzhang/DevIntellijIdea/apk-decompile/Projects/qq2013_4.5.2.1605_android/App/src/main/res/values";
    private static final String SRC_RESOURCE_ID_JAVA_FILE =
            "/Volumes/SHARE/MacSystem/Home/Users/djzhang/DevIntellijIdea/apk-decompile/Projects/qq2013_4.5.2.1605_android/App/src/main/java/com/tencent/mobileqq/utils";

    public static File xml = new File(String.format("%s/public.xml", APK_PUBLIC_XML_FOLD));    //XML路径
    public static File file = new File(SRC_RESOURCE_ID_JAVA_FILE);    //待替换的源码文件夹

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        FindReplace replace = new FindReplace();
        replace.findReplace(replace.findReplaceXml(xml), file);
        System.out.println("用时:" + (System.currentTimeMillis() - start));
    }

    /**
     * 生成十六进制转成十进制的资源文件
     *
     * @param file
     * @return
     */
    public File findReplaceXml(File file) {
        FileReader fr = null;
        FileWriter fw = null;
        File fileTemp = new File(file.getParent(), "re" + file.getName());
        try {
            fr = new FileReader(file);

            fw = new FileWriter(fileTemp);

            BufferedReader bf = new BufferedReader(fr);
            String str = null;// 取一行
            while ((str = bf.readLine()) != null) { // 一行一行的读取全部内容
                // 显示行号
                int index = str.lastIndexOf("id=\"0x");
                if (index != -1) {
                    index = index + 6;
                    String s = str.substring(index, index + 8);
                    str = str.replace("0x" + s, hexToDecimal(s));
                }
                fw.write(str);
                fw.write("\r\n");// 写入换行
            }
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileTemp;
    }

    /**
     * 十六进制转成十进制
     *
     * @param hexString
     * @return
     */
    private static String hexToDecimal(String hexString) {
        BigInteger bi = null;
        bi = new BigInteger(hexString, 16);
        String show = bi.toString(10);
        return show;
    }

    /**
     * 查找替换
     *
     * @param xml
     * @param fileDir
     */
    public void findReplace(File xml, File fileDir) {
        InputStream inStream = null;
        try {
            initMap(new FileInputStream(xml));
            findFileList(fileDir);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (inStream != null)
                try {
                    inStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    /**
     * 正则表达式查找替换
     */
    private String matcher(String regex) {
        Matcher m = Pattern.compile("[0-9]\\d{9}").matcher(regex);
        while (m.find()) {
            String recource = resources.get(m.group());
            if (recource != null) {
                regex = regex.replaceAll(m.group(), recource);
            }
        }
        return regex;
    }

    /**
     * 递归遍历目录文件
     *
     * @param file
     */
    public void findFileList(File file) {
        File[] files = file.listFiles();
        if (files == null) {
            return;
        }
        for (File f : files) {
            if (f.isDirectory()) {
                findFileList(f);
            } else {
                if (f.getName().endsWith(".java")) {
                    transferFile(f); // 查找替换
                }
            }
        }
    }

    /**
     * 保存到原文件
     *
     * @param file
     */
    private void transferFile(File file) {
        try {
            FileReader reader = new FileReader(file);
            char[] dates = new char[1024];
            int count = 0;
            StringBuilder sb = new StringBuilder();
            while ((count = reader.read(dates)) > 0) {
                String str = String.valueOf(dates, 0, count);
                sb.append(str);
            }
            reader.close();
            // 从构造器中生成字符串，并替换搜索文本
            String fileContent = matcher(sb.toString());
            FileWriter writer = new FileWriter(file);
            writer.write(fileContent.toCharArray());
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 从流中初始化资源集合
     * // R.attr.name 2130771968
     *
     * @param inStream
     */
    public void initMap(InputStream inStream) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document dom = builder.parse(inStream);
            Element root = dom.getDocumentElement();
            NodeList items = root.getElementsByTagName("public");// 查找所有person节点
            System.out.println("XML文件中有记录:" + items.getLength());
            for (int i = 0; i < items.getLength(); i++) {
                // 得到public节点
                Element publicNode = (Element) items.item(i);
                resources.put(publicNode.getAttribute("id"), "R." + publicNode.getAttribute("type") + "." + publicNode.getAttribute("name"));
            }
            System.out.println("添加记录：" + resources.size());
            inStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}