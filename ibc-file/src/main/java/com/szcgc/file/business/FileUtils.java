package com.szcgc.file.business;

import com.szcgc.comm.IbcResult;
import com.szcgc.comm.util.StringUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @Author liaohong
 * @create 2020/12/14 18:58
 */
public class FileUtils {

    public static final char URL_SEP = '/';
    private static final String EXT = ".jpg,.jpeg,.png,.bmp,.pdf,.doc,.docx,.xls,.xlsx,.txt";
    private static final String DIR_RESOURCE = "resources";
    private static final String DIR_UPLOAD = "upload";
    //private static final String[] DIRS = new String[]{DIR_RESOURCE, DIR_UPLOAD};

    public static IbcResult<String> saveFile(String name, String content, String uploadPath) throws Exception {
        IbcResult<String> fileName = makeFileName(name);
        if (!fileName.isOk())
            return fileName;
        LocalDate now = LocalDate.now();
        String[] paths = makePath(fileName.getValue(), uploadPath, DIR_RESOURCE, DIR_UPLOAD,
                String.valueOf(now.getYear()), String.valueOf(now.getMonthValue()), String.valueOf(now.getDayOfMonth()));
        if (paths == null)
            return IbcResult.error("上传目录没有写权限");
        writeFile(content, paths[0]);
        return IbcResult.ok(paths[1]);
    }

    public static IbcResult<String> saveFile(MultipartFile mFile, String uploadPath) throws Exception {
        IbcResult<String> fileName = makeFileName(mFile.getOriginalFilename());
        if (!fileName.isOk())
            return fileName;
        LocalDate now = LocalDate.now();
        String[] paths = makePath(fileName.getValue(), uploadPath, DIR_RESOURCE, DIR_UPLOAD,
                String.valueOf(now.getYear()), String.valueOf(now.getMonthValue()), String.valueOf(now.getDayOfMonth()));
        if (paths == null)
            return IbcResult.error("上传目录没有写权限");
        writeFile(mFile, paths[0]);
        return IbcResult.ok(paths[1]);
    }

    private static void writeFile(MultipartFile mFile, String filePath) throws Exception {
        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(filePath));
        FileCopyUtils.copy(mFile.getInputStream(), outputStream);
    }

    private static void writeFile(String fileContent, String filePath) throws Exception {
        byte[] bytes = Base64.getDecoder().decode(fileContent);
        Files.write(Paths.get(filePath), bytes, StandardOpenOption.CREATE);
    }

    private static IbcResult<String> makeFileName(String oriFileName) throws Exception {
        int index = oriFileName.lastIndexOf(".");
        if (index <= 0)
            return IbcResult.error("未检测到文件后缀");
        String fileExt = oriFileName.substring(index).toLowerCase();
        if (EXT.indexOf(fileExt) < 0)
            return IbcResult.error("上传文件扩展名是不允许的扩展名,\n只允许" + EXT + "格式");
        String fileName = StringUtils.first(oriFileName.substring(0, index), 20).trim() + randName() + fileExt;
        return IbcResult.ok(fileName);
    }

    //private static String[] makePath(String fileName, String realPath, String[] directNames) {
    private static String[] makePath(String fileName, String realPath, String... directNames) {
        // 文件保存目录路径
        StringBuilder urlPath = new StringBuilder();
        StringBuilder phyPath = new StringBuilder();// 文件保存目录路径
        phyPath.append(realPath);
        for (String dirName : directNames) {
            phyPath.append(dirName);
            phyPath.append(File.separator);

            urlPath.append(dirName);
            urlPath.append(URL_SEP);
        }
        File uploadDir = new File(phyPath.toString());
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        if (!uploadDir.canWrite())
            return null;
        phyPath.append(fileName);
        urlPath.append(fileName);
        return new String[]{phyPath.toString(), urlPath.toString()};
    }

    private static String randName() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        char f = (char) (random.nextInt(26) + 65); // 小写字母
        char e = (char) (random.nextInt(26) + 97); // 大写字母
        int n = random.nextInt(8999) + 1000; // 1000-9999
        if (n % 2 == 0)
            return String.valueOf(f) + e + n;
        return String.valueOf(e) + f + n;
    }

    /**
     * 需求需要，保存实际上传文件的名称
     *
     * @param mFile
     * @return
     * @throws Exception
     */
//    public static IbcResult<String> saveFile2(MultipartFile mFile) throws Exception {
//        String fileName = mFile.getOriginalFilename();
//        if (fileName.lastIndexOf(".") <= 0)
//            return IbcResult.error("未检测到文件后缀");
//        String fileExt = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
//        if (EXTS.indexOf(fileExt) < 0)
//            return IbcResult.error("上传文件扩展名是不允许的扩展名,\n只允许" + EXTS + "格式");
//        //fileName = FileUtils.makeName() + fileExt;
//        LocalDate now = LocalDate.now();
//        return FileUtils.saveFile(mFile, fileName, realPath(), DIR_RESOURCE, DIR_UPLOAD,
//                String.valueOf(now.getYear()), String.valueOf(now.getMonthValue()),
//                String.valueOf(now.getDayOfMonth()), IdUtils.fastUUID());
//    }
//
//
//    public static IbcResult<String> renameFile(String oriFilePath, int id, int version) throws Exception {
//        String fileExt = oriFilePath.substring(oriFilePath.lastIndexOf(".")).toLowerCase();
//        StringBuilder path = new StringBuilder(realPath());
//        path.append(oriFilePath);
//        String oriPath = path.toString();
//        path.delete(oriPath.length() - fileExt.length(), oriPath.length());
//        path.append('_');
//        path.append(id);
//        path.append('_');
//        path.append(version);
//        path.append(fileExt);
//        String newPath = path.toString();
//        File oriFile = new File(oriPath);
//        File newFile = new File(newPath);
//        oriFile.renameTo(newFile);
//        return IbcResult.OK();
//    }
//
//    public static IbcResult<String> copeFile(String oriFilePath) throws Exception {
//        String realPath = realPath();
//        String oriPath = realPath + oriFilePath;
//        String fileExt = oriFilePath.substring(oriFilePath.lastIndexOf(".")).toLowerCase();
//        String fileName = FileUtils.makeName() + fileExt;
//        LocalDate now = LocalDate.now();
//        String[] directNames = new String[]{DIR_RESOURCE, DIR_UPLOAD,
//                String.valueOf(now.getYear()), String.valueOf(now.getMonthValue()),
//                String.valueOf(now.getDayOfMonth())};
//        StringBuilder[] paths = makePath(fileName, realPath, directNames);
//        if (paths == null)
//            return IbcResult.error("上传目录没有写权限");
//        StringBuilder phyPath = paths[0];
//        StringBuilder urlPath = paths[1];
//        FileCopyUtils.copy(new File(oriPath), new File(phyPath.toString()));
//        return IbcResult.ok(urlPath.toString());
//    }
}
