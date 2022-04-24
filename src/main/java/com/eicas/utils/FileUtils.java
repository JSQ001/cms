package com.eicas.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Slf4j
public class FileUtils {

    /**
     * 上传文件，返回相对baseDir的路径
     *
     * @param file    文件对象
     * @param baseDir 存储根路径地址
     * @return 上传成功返回相对baseDir的存储路径，失败则返回null
     */
    public static String upload(MultipartFile file, String baseDir) {
        String relativePath;
        String dir = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        File targetDir = new File(baseDir, dir);
        if (!targetDir.exists()) {
            targetDir.mkdirs();
        }
        String filename = UUID.randomUUID() + "." + getExtension(file);
        File targetFile = new File(targetDir, filename);
        try {
            file.transferTo(targetFile);
            relativePath = dir + "/" + filename;
        } catch (Exception ex) {
            relativePath = null;
        }
        return relativePath;
    }

    /**
     * 下载图片保存至服务器
     *
     * @param imageUrl      图片URL
     * @param filename      图片名称
     * @param storeRootPath 图片保存根路径
     * @param imageMapping  图片映射根路径
     * @return 图片映射全路径
     */

    public static String crabImage(String imageUrl, String filename, String storeRootPath, String imageMapping) {
        String newImageUrl;
        try {
            URL url = new URL(imageUrl);
            URLConnection con = url.openConnection();
            con.setConnectTimeout(5 * 1000);
            InputStream is = con.getInputStream();


            String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            String catalogName = storeRootPath + "/" + format;
            File storePath = new File(catalogName);
            if (!storePath.exists()) {
                storePath.mkdirs();
            }
            String[] split = filename.split("\\.");
            String ext = "." + split[split.length - 1];
            String newFilename = UUID.randomUUID() + ext;
            File targetFile = new File(storePath, newFilename);

            byte[] buffer = new byte[1024];
            int len;

            OutputStream os = new FileOutputStream(targetFile);

            while ((len = is.read(buffer)) != -1) {
                os.write(buffer, 0, len);
            }
            newImageUrl = imageMapping + format + "/" + newFilename;
            os.close();
            is.close();
            log.debug("成功下载图片:" + newImageUrl);
        } catch (Exception e) {
            newImageUrl = null;
            log.error(e.getMessage());
        }
        return newImageUrl;
    }

    /**
     * 获取上传文件的扩展名
     *
     * @param file 文件对象
     * @return 文件扩展名
     */
    public static String getExtension(MultipartFile file) {
        String filename = file.getOriginalFilename();
        String extension;
        if (filename == null) {
            return null;
        } else {
            String[] split = filename.split("\\.");
            extension = split[split.length - 1];
        }

        if (!StringUtils.hasText(extension)) {
            extension = MimeTypeUtils.getExtension(Objects.requireNonNull(file.getContentType()));
        }
        return extension;
    }


}

