package com.eicas.cms.service.impl;

import com.eicas.cms.entity.AttachmentRefEntity;
import com.eicas.cms.mapper.AttachmentRefMapper;
import com.eicas.cms.service.IAttachmentRefService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 附件关联表 服务实现类
 * </p>
 *
 * @author osnudt
 * @since 2022-04-20
 */
@Service
public class AttachmentRefServiceImpl extends ServiceImpl<AttachmentRefMapper, AttachmentRefEntity> implements IAttachmentRefService {

}
