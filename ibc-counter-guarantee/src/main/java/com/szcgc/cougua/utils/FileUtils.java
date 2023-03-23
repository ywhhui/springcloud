package com.szcgc.cougua.utils;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class FileUtils {

    public static void main(String[] args) {
        File fileAuth = new File("http://10.8.5.71:7089/resources/upload/2022/11/22/Pq1592In8386.docx");
        MultipartFile file = null;
        try {
            file = urlToMultipartFile("http://10.8.5.71:7089/resources/upload/2022/11/22/Pq1592In8386.docx", fileAuth.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
//        MultipartFile file = fileToMultipartFile(fileAuth);
        System.out.println("file-----11-"+file);
    }

    /**
     * url转MultipartFile
     * @param url
     * @return
     * @throws Exception
     */
    public static MultipartFile urlToMultipartFile(String url,String fileName) throws Exception {
        File file = null;
        MultipartFile multipartFile = null;
        try {
            HttpURLConnection httpUrl = (HttpURLConnection) new URL(url).openConnection();
            httpUrl.connect();
            System.out.println("成功建立httpUrl连接"+httpUrl.toString());
            file = inputStreamToFile(httpUrl.getInputStream(),fileName);
            multipartFile = fileToMultipartFile(file);
            httpUrl.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return multipartFile;
    }
    /**
     * inputStream 转 File
     * @param ins
     * @param name
     * @return
     * @throws Exception
     */
    public static File inputStreamToFile(InputStream ins, String name) throws Exception{
        System.out.println("inputStream 转 File中"+name);
        File file = new File(System.getProperty("java.io.tmpdir") + File.separator + name);
        OutputStream os = new FileOutputStream(file);
        int bytesRead;
        int len = 8192;
        byte[] buffer = new byte[len];
        while ((bytesRead = ins.read(buffer, 0, len)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        os.close();
        ins.close();
        System.out.println("inputStream 转 File转换完成"+name);
        return file;
    }

    /**
     * file转multipartFile
     * @param file
     * @return
     */
    public static MultipartFile fileToMultipartFile(File file) {
        System.out.println("fileToMultipartFile文件转换中："+file.getAbsolutePath());
        FileItemFactory factory = new DiskFileItemFactory(16, null);
        FileItem item=factory.createItem(file.getName(),"text/plain",true,file.getName());
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        try {
            FileInputStream fis = new FileInputStream(file);
            OutputStream os = item.getOutputStream();
            while ((bytesRead = fis.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new CommonsMultipartFile(item);
    }
}
