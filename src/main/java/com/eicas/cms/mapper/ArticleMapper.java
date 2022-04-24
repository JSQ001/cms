package com.eicas.cms.mapper;

import com.eicas.cms.entity.ArticleEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eicas.cms.pojo.vo.ArticleBriefVO;

import java.io.Serializable;

/**
 * 文章信息表 Mapper 接口
 *
 * @author osnudt
 * @since 2022-04-19
 */
public interface ArticleMapper extends BaseMapper<ArticleEntity> {

    ArticleBriefVO selectBriefById(Serializable id);
}
