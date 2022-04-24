package com.eicas.cms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eicas.cms.entity.AttachmentEntity;
import com.eicas.cms.mapper.AttachmentMapper;
import com.eicas.cms.service.IAttachmentService;
import com.eicas.utils.FileUtils;
import com.eicas.utils.NetworkUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

/**
 * 附件资源表 服务实现类
 *
 * @author osnudt
 * @since 2022-04-20
 */
@Service
public class AttachmentServiceImpl extends ServiceImpl<AttachmentMapper, AttachmentEntity> implements IAttachmentService {

    @Value("${ecms.store-root-path}")
    private String storeRootPath;
    @Value("${ecms.mapping-root-path}")
    private String mappingRootPath;

    @Override
    public AttachmentEntity upload(MultipartFile file) {
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();

        String relativePath = FileUtils.upload(file, storeRootPath);
        if (relativePath == null)
            return null;
        String originName = file.getOriginalFilename();
        String filename = relativePath.substring(relativePath.lastIndexOf("/") + 1);
        String extension = FileUtils.getExtension(file);
        String ipaddr = NetworkUtil.getIpAddress(request);
        String mime = file.getContentType();
        Long size = file.getSize() / 1024;
        String absolutePath = new File(storeRootPath, relativePath).getAbsolutePath();
        String mappingPath = mappingRootPath + "/" + relativePath;

        AttachmentEntity entity = new AttachmentEntity()
                .setIpaddr(ipaddr)
                .setMime(mime)
                .setOrigin(originName)
                .setFilename(filename)
                .setExt(extension)
                .setRelativePath(relativePath)
                .setSize(size)
                .setAbsolutePath(absolutePath)
                .setMappingPath(mappingPath);
        this.save(entity);
        return entity;
    }
}
