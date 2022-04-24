package com.eicas.cms.pojo.param;

import lombok.Data;

@Data
public class ArticleStaticsResult {

    //文章状态：0-草稿,1-待审核,2-审核通过,3-审核不通过

    // 全部
    private Integer allNum;
    // 草稿
    private Integer draftNum;
    // 待审核
    private Integer notAuditNum;
    // 审核通过
    private Integer approvedNum;
    // 审核不通过
    private Integer rejectNum;

}
