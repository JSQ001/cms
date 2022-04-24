package com.eicas.cms.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONObject;
import com.alibaba.fastjson.JSONException;
import com.eicas.cms.pojo.vo.UploadFileResult;
import com.eicas.common.ResultData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;

/**
 * 用于处理关于ueditor插件相关的请求
 *
 * @author jsq
 * @date 2022-04-23
 */

@Slf4j
@RestController
@RequestMapping("/ueditor")
public class UeditorController {

    @Value("${ueditor.configPath}")
    private String configPath;

    @Value("${ecms.upload}")
    private String uploadPath;

    @Value("${ecms.filePrefix}")
    private String filePrefix;

    @GetMapping(value = "/config")
    @ResponseBody
    public String getConfig(HttpServletRequest request) throws UnsupportedEncodingException, JSONException {
        String config = null;
        if(StringUtils.hasText(configPath)){
            try {
                config = FileUtil.readString(configPath,"utf8");
                JSONObject configJson =  new JSONObject(config);
                config = configJson.toString();
            }catch (Exception e){
                log.error(e.getMessage());
            }
        }else {
            log.info(configPath + "不存在！");
        }
        return config;
    }

    @PostMapping(value = "/config")
    @ResponseBody
    public ResultData uploadFile(MultipartFile file){
        String filename = file.getOriginalFilename();
        String time = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        if(StringUtils.hasText(filename)){
            int index = filename.lastIndexOf(".");
            filename = filename.substring(0,index) + time + filename.substring(index);
        }else {
            filename = time;
        }

        String storePath = uploadPath + (uploadPath.endsWith("/") ? filename : "/" + filename);
        try {
            FileUtil.writeBytes(file.getBytes(), storePath);

            UploadFileResult result =  new UploadFileResult();
            String url = filePrefix + (filePrefix.endsWith("/") ? file.getOriginalFilename() : "/" + file.getOriginalFilename());

            result.setUrl(url);
            result.setTitle(filename);
            result.setState("SUCCESS");
            result.setOriginal(filename);

            return ResultData.success(result,"上传成功！");
        } catch (IOException e) {
            return ResultData.failed("上传文件失败！");
        }
    }

}