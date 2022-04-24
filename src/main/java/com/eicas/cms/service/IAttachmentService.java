package com.eicas.cms.service;

import com.eicas.cms.entity.AttachmentEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 附件资源表 服务类
 * </p>
 *
 * @author osnudt
 * @since 2022-04-20
 */
public interface IAttachmentService extends IService<AttachmentEntity> {
    /**
     * 上传附件，返回附件对象
     * @param file 文件对象
     * @return 附件对象
     */
    AttachmentEntity upload(MultipartFile file);
}
