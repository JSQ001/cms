package com.eicas.cms.controller;

import com.eicas.cms.entity.AttachmentEntity;
import com.eicas.cms.service.IAttachmentService;
import com.eicas.common.ResultData;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * 附件资源前端控制器
 *
 * @author osnudt
 * @since 2022-04-20
 */
@RestController
@RequestMapping("/api/attachment")
public class AttachmentController {
    @Resource
    IAttachmentService attachmentService;

    @ApiOperation("文件上传")
    @PostMapping(value = "/upload")
    @ResponseBody
    public ResultData<AttachmentEntity> uploadAttachment(@RequestParam("file") MultipartFile file) {
        AttachmentEntity entity = attachmentService.upload(file);
        if (entity == null)
            return ResultData.failed("上传文件失败！");
        return ResultData.success(entity);
    }

    @ApiOperation("文件下载")
    @PostMapping(value = "/download")
    @ResponseBody
    public ResultData<Boolean> downAttachment(@RequestParam("id") Long id) {
        // TODO 文件下载
        return ResultData.failed();
    }

    @ApiOperation("文件删除")
    @PostMapping(value = "/delete")
    @ResponseBody
    public ResultData<Boolean> delete(@RequestParam("id") String id) {
        // TODO
        return ResultData.success(attachmentService.removeById(id),"文件删除成功");
    }
}
