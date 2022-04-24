DROP TABLE IF EXISTS cms_attachment;
CREATE TABLE cms_attachment
(
    id          bigint(20) UNSIGNED NOT NULL,
    filename    varchar(64)         NOT NULL DEFAULT '' COMMENT '文件显示名称',
    size        bigint              NOT NULL DEFAULT 0 COMMENT '文件大小，以KB为单位',
    ext         varchar(10)         NULL COMMENT '文件类型',
    mime        varchar(10)         NULL COMMENT 'mime类型',
    path        varchar(255)        NULL COMMENT '存储路径',
    hit_nums    int(11)             NULL COMMENT '下载次数',
    uploader_id bigint(20)          NULL COMMENT '上传人ID',
    uploader    bigint(20)          NULL COMMENT '上传人',
    ipaddr      varchar(64)         NULL COMMENT '上传IP',
    is_deleted  tinyint(1)          NOT NULL DEFAULT 0 COMMENT '逻辑删除',
    PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB COMMENT = '附件资源表';

DROP TABLE IF EXISTS cms_attachment_ref;
create table cms_attachment_ref
(
    id            bigint(20) UNSIGNED NOT NULL,
    attachment_id bigint(20)          NOT NULL,
    symbol        varchar(64)         NOT NULL COMMENT '表标识：如article,notice,memorial',
    key_id        bigint(20)          NOT NULL COMMENT '表主键ID',
    PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB COMMENT ='附件关联表';


DROP TABLE IF EXISTS cms_column;
CREATE TABLE cms_column
(
    id                   bigint(20)   NOT NULL,
    parent_id            bigint(20)   NULL     DEFAULT NULL COMMENT '所属父栏目id',
    code                 varchar(20)  NOT NULL COMMENT '栏目编码，层级关系',
    name                 varchar(255) NULL     DEFAULT NULL COMMENT '栏目名称',
    cover_img_url        varchar(255) NULL     DEFAULT NULL COMMENT '栏目封面图片地址',
    flag                 varchar(10)  NULL     DEFAULT NULL COMMENT '栏目标记属性（扩展用）',
    path                 varchar(255) NULL     DEFAULT NULL COMMENT '栏目路径',
    type                 int(4)       NULL     DEFAULT 0 COMMENT '栏目类型：0-内部栏目，1-外部栏目',
    keyword              varchar(255) NULL     DEFAULT NULL COMMENT '关键字',
    custom_url           varchar(255) NULL     DEFAULT NULL COMMENT '自定义链接',
    publish_time         datetime     NULL     DEFAULT NULL COMMENT '栏目上线时间',
    content_template_url varchar(255) NULL     DEFAULT NULL COMMENT '内容页模板',
    list_template_url    varchar(255) NULL     DEFAULT NULL COMMENT '列表页模板',
    sort_order           int(11)      NULL     DEFAULT NULL COMMENT '自定义顺序',
    remarks              varchar(255) NULL     DEFAULT NULL COMMENT '栏目描述',
    is_deleted           tinyint(1)   NOT NULL DEFAULT 0 COMMENT '逻辑删除',
    PRIMARY KEY (id) USING BTREE,
    UNIQUE INDEX code (code) USING BTREE
) ENGINE = InnoDB COMMENT = '栏目信息表';

DROP TABLE IF EXISTS cms_article;
CREATE TABLE cms_article
(
    id             bigint(20) UNSIGNED NOT NULL,
    column_id      bigint(20)          NOT NULL COMMENT '所属栏目id',
    column_code    varchar(255)                 DEFAULT NULL COMMENT '栏目层级关系',
    column_name    varchar(64)                  DEFAULT NULL COMMENT '栏目名称',
    title          varchar(255)                 DEFAULT NULL COMMENT '文章标题',
    sub_title      varchar(255)                 DEFAULT NULL COMMENT '副标题',
    keyword        varchar(255)                 DEFAULT NULL COMMENT '关键字',
    essential      longtext COMMENT '文章摘要',
    content        longtext COMMENT '内容',
    cover_img_url  varchar(255)                 DEFAULT NULL COMMENT '标题图片',
    link_url       varchar(255)                 DEFAULT NULL COMMENT '跳转链接地址',
    type           varchar(10)                  DEFAULT NULL COMMENT '类型（扩展使用）',
    author         varchar(20)                  DEFAULT NULL COMMENT '作者',
    source         varchar(255)                 DEFAULT NULL COMMENT '来源',
    remarks        varchar(255)                 DEFAULT NULL COMMENT '描述',
    audit_comments varchar(255)                 DEFAULT NULL COMMENT '审核批注',
    auditor_id     bigint(20)                   DEFAULT NULL COMMENT '审核人id',
    audit_time     datetime                     DEFAULT CURRENT_TIMESTAMP COMMENT '审核时间',
    status         tinyint(4)                   DEFAULT NULL COMMENT '文章状态：0-草稿,1-待审核,2-审核通过,3-审核不通过',
    is_show        tinyint(1)                   DEFAULT NULL COMMENT '是否前台显示：0-不显示，1-显示',
    is_focus       tinyint(1)                   DEFAULT NULL COMMENT '是否焦点图显示',
    is_recommended tinyint(1)                   DEFAULT NULL COMMENT '是否推荐',
    is_top         tinyint(1)                   DEFAULT NULL COMMENT '是否置顶',
    publish_time   datetime                     DEFAULT NULL COMMENT '发布时间',
    sort_order     int(11)                      DEFAULT NULL COMMENT '自定义顺序',
    hit_nums       int(11)                      DEFAULT NULL COMMENT '点击次数',
    is_deleted     tinyint(1)          NOT NULL default '0' COMMENT '逻辑删除',
    PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB COMMENT ='文章信息表';



CREATE TABLE cms_comment
(
    id           bigint(20) UNSIGNED NOT NULL,
    parent_id    bigint(20) UNSIGNED          DEFAULT NULL COMMENT '父评论ID',
    content      mediumtext COMMENT '评论内容',
    symbol       varchar(100)        NOT NULL COMMENT '表标识：如article,notice,memorial',
    key_id       bigint(20)          NOT NULL COMMENT '表主键ID',
    publish_time datetime            NOT NULL COMMENT '评论时间',
    audit_time   datetime                     DEFAULT NULL COMMENT '审核时间',
    status       tinyint(1)          NOT NULL DEFAULT '0' COMMENT '0:未审核;1:已审核;2:推荐;3:屏蔽',
    creator_id   bigint(20)          NOT NULL COMMENT '创建人',
    auditor_id   bigint(20)                   DEFAULT NULL COMMENT '审核人',
    is_deleted   tinyint(1)          NOT NULL default '0' COMMENT '逻辑删除',
    PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB COMMENT ='评论表';

DROP TABLE IF EXISTS cms_audit;
CREATE TABLE cms_audit
(
    id         bigint(20) UNSIGNED NOT NULL,
    symbol     varchar(100)        NOT NULL COMMENT '表标识：如article,notice,memorial',
    key_id     bigint(20)          NOT NULL COMMENT '表主键ID',
    annotation varchar(255)                 DEFAULT NULL COMMENT '审核批注',
    auditor_id bigint(20)                   DEFAULT NULL COMMENT '审核人id',
    auditor    varchar(20)                  DEFAULT NULL COMMENT '审核人',
    audit_time datetime                     DEFAULT CURRENT_TIMESTAMP COMMENT '审核时间',
    is_deleted tinyint(1)          NOT NULL default '0' COMMENT '逻辑删除',
    PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB COMMENT ='审核表';

DROP TABLE IF EXISTS cms_collect_rule;
CREATE TABLE cms_collect_rule
(
    id             bigint(20) UNSIGNED NOT NULL,
    type           tinyint(1)          NOT NULL COMMENT '规则类型：1-XPath，2-正则表达式',
    collect_url    varchar(255)        NOT NULL COMMENT '采集地址地址',
    links_rule     varchar(255)        NOT NULL COMMENT '采集列表规则',
    title_rule     varchar(255)        NOT NULL COMMENT '标题规则',
    sub_title_rule varchar(255)        NULL     DEFAULT NULL COMMENT '副标题规则',
    pub_time_rule  varchar(255)        NULL     DEFAULT NULL COMMENT '发布时间规则',
    essential_rule varchar(255)        NULL     DEFAULT NULL COMMENT '文章摘要规则',
    author_rule    varchar(255)        NULL     DEFAULT NULL COMMENT '作者规则',
    content_rule   varchar(255)        NOT NULL COMMENT '正文规则',
    remarks        varchar(255)        NULL     DEFAULT NULL COMMENT '备注',
    is_deleted     tinyint(1)          NOT NULL default '0' COMMENT '逻辑删除',
    PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB COMMENT = '采集规则表';

DROP TABLE IF EXISTS cms_collect_node;
CREATE TABLE cms_collect_node
(
    id         bigint(20) UNSIGNED NOT NULL,
    name       varchar(32)         NOT NULL COMMENT '节点名称',
    column_id  bigint(20)          NOT NULL COMMENT '入库栏目id',
    column     varchar(64)         NOT NULL COMMENT '入库栏目',
    source     varchar(255)        NULL     DEFAULT NULL COMMENT '采集来源',
    remarks    varchar(255)        NULL     DEFAULT NULL COMMENT '备注',
    is_auto    tinyint(1)          NULL     DEFAULT NULL COMMENT '是否自动采集：0-非自动，1-自动',
    auto_time  datetime            NULL     DEFAULT NULL COMMENT '自动采集时间',
    is_deleted tinyint(1)          NOT NULL default '0' COMMENT '逻辑删除',
    PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB COMMENT = '采集节点表';




DROP TABLE IF EXISTS cms_notice;
CREATE TABLE cms_notice
(
    id            bigint(20) UNSIGNED NOT NULL,
    title         varchar(255)        NOT NULL COMMENT '文章标题',
    content       longtext            NOT NULL COMMENT '内容',
    essential     longtext COMMENT '摘要',
    publisher_id  bigint(20)                   DEFAULT NULL COMMENT '发布者用户编号',
    publisher     varchar(20)                  DEFAULT NULL COMMENT '发布者',
    publish_unit  bigint(20)                   DEFAULT NULL COMMENT '发布单位',
    publish_time  datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
    attachment_id bigint(20)                   DEFAULT NULL COMMENT '附件',
    remarks       varchar(255)                 DEFAULT NULL COMMENT '描述',
    state         int(4)                       DEFAULT NULL COMMENT '文章状态：0-草稿,1-发布',
    is_visible    tinyint(1)                   DEFAULT '1' COMMENT '是否显示：0-不显示，1-显示',
    sort_order    int(11)                      DEFAULT NULL COMMENT '自定义顺序',
    hit_nums      int(11)                      DEFAULT NULL COMMENT '点击次数',
    is_deleted    tinyint(1)          NOT NULL default '0' COMMENT '逻辑删除',
    PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB COMMENT ='通知公告表';
DROP TABLE IF EXISTS cms_memorial;

CREATE TABLE cms_memorial
(
    id            bigint(20) UNSIGNED NOT NULL,
    title         varchar(255)                 DEFAULT NULL COMMENT '标题',
    content       longtext COMMENT '内容',
    essential     longtext COMMENT '摘要',
    cover_img_url varchar(255)                 DEFAULT NULL COMMENT '标题图片',
    compiler      varchar(20)                  DEFAULT NULL COMMENT '编制人',
    compiler_id   bigint(20)                   DEFAULT NULL COMMENT '编制人用户编号',
    compiler_unit varchar(255)                 DEFAULT NULL COMMENT '编制单位',
    remarks       varchar(255)                 DEFAULT NULL COMMENT '描述',
    state         int(4)                       DEFAULT NULL COMMENT '状态：0-草稿,1-已发布',
    event_time    datetime                     DEFAULT NULL COMMENT '事件时间',
    publish_time  datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
    sort_order    int(11)                      DEFAULT '255' COMMENT '自定义顺序',
    hit_nums      int(11)                      DEFAULT 0 COMMENT '点击次数',
    is_deleted    tinyint(1)          NOT NULL default '0' COMMENT '逻辑删除',
    PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB COMMENT ='大事记表';


DROP TABLE IF EXISTS cms_attachment;
CREATE TABLE cms_attachment
(
    id          bigint(20) UNSIGNED NOT NULL,
    filename    varchar(255)        NOT NULL DEFAULT '' COMMENT '文件显示名称',
    size        bigint              NOT NULL DEFAULT 0 COMMENT '文件大小，以KB为单位',
    ext         varchar(10)         NULL COMMENT '文件类型',
    mime        varchar(10)         NULL COMMENT 'mime类型',
    path        varchar(255)        NULL COMMENT '存储路径',
    hit_nums    int(11)             NULL COMMENT '下载次数',
    uploader_id bigint(20)          NULL COMMENT '上传人ID',
    uploader    bigint(20)          NULL COMMENT '上传人',
    ipaddr      varchar(100)        NULL COMMENT '上传IP',
    is_deleted  tinyint(1)          NOT NULL DEFAULT 0 COMMENT '逻辑删除',
    PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB COMMENT = '附件资源表';

# 附件关联表，约定外表标识，如article,notice,memory
DROP TABLE IF EXISTS cms_attachment_ref;
create table cms_attachment_ref
(
    id            bigint(20) UNSIGNED NOT NULL,
    attachment_id bigint(20)          NOT NULL,
    symbol        varchar(100)        NOT NULL COMMENT '表标识：如article,notice,memorial',
    key_id        bigint(20)          NOT NULL COMMENT '表主键ID',
    PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB COMMENT ='附件关联表';

DROP TABLE IF EXISTS sys_config;
CREATE TABLE sys_config
(
    id           bigint(20) UNSIGNED NOT NULL,
    config_name  varchar(100)        NULL     DEFAULT '' COMMENT '参数名称',
    config_key   varchar(100)        NULL     DEFAULT '' COMMENT '参数键名',
    config_value varchar(500)        NULL     DEFAULT '' COMMENT '参数键值',
    config_type  char(1)             NULL     DEFAULT 'N' COMMENT '系统内置（Y是 N否）',
    is_deleted   tinyint(1)          NOT NULL DEFAULT 0 COMMENT '逻辑删除',
    PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB COMMENT = '参数配置表';

DROP TABLE IF EXISTS sys_account;
CREATE TABLE sys_account
(
    id                         bigint(20) UNSIGNED NOT NULL,
    user_id                    bigint(20) UNSIGNED NOT NULL,
    password                   varchar(64)         NULL     DEFAULT NULL COMMENT '用户密码',
    is_enabled                 tinyint(1)          NULL     DEFAULT 1 COMMENT '账号是否可用。默认为1（可用）',
    is_not_expired             tinyint(1)          NULL     DEFAULT 1 COMMENT '是否过期。默认为1（没有过期）',
    is_account_not_locked      tinyint(1)          NULL     DEFAULT 1 COMMENT '账号是否锁定。默认为1（没有锁定）',
    is_credentials_not_expired tinyint(1)          NULL     DEFAULT 1 COMMENT '证书（密码）是否过期。默认为1（没有过期）',
    is_deleted                 tinyint(1)          NOT NULL default '0' COMMENT '逻辑删除',
    PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB COMMENT = '系统账号表';

DROP TABLE IF EXISTS sys_account_role_r;
CREATE TABLE sys_account_role_r
(
    id         bigint(20) UNSIGNED NOT NULL,
    account_id bigint(20) UNSIGNED NOT NULL COMMENT '账号id',
    role_id    bigint(20) UNSIGNED NOT NULL COMMENT '角色id',
    PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB COMMENT = '账号角色关联表';


DROP TABLE IF EXISTS sys_userinfo;
CREATE TABLE sys_userinfo
(
    id         bigint(20) UNSIGNED NOT NULL,
    name       varchar(32)         NULL     DEFAULT NULL COMMENT '姓名',
    sex        tinyint(1)          NULL     DEFAULT NULL COMMENT '性别',
    portrait   varchar(32)         NULL     DEFAULT NULL COMMENT '头像',
    type       varchar(32)         NULL     DEFAULT NULL COMMENT '人员类别',
    id_card    varchar(32)         NULL     DEFAULT NULL COMMENT '身份证号',
    contact    varchar(32)         NULL     DEFAULT NULL COMMENT '联系方式',
    birthday   varchar(32)         NULL     DEFAULT NULL COMMENT '出生日期',
    ethnic     varchar(32)         NULL     DEFAULT NULL COMMENT '民族',
    post_id    varchar(32)         NULL     DEFAULT NULL COMMENT '职务ID',
    post       varchar(32)         NULL     DEFAULT NULL COMMENT '职务',
    ext01      varchar(32)         NULL     DEFAULT NULL COMMENT '扩展字段',
    ext02      varchar(32)         NULL     DEFAULT NULL COMMENT '扩展字段',
    ext03      varchar(32)         NULL     DEFAULT NULL COMMENT '扩展字段',
    ext04      varchar(32)         NULL     DEFAULT NULL COMMENT '扩展字段',
    ext05      varchar(32)         NULL     DEFAULT NULL COMMENT '扩展字段',
    ext06      varchar(32)         NULL     DEFAULT NULL COMMENT '扩展字段',
    ext07      varchar(32)         NULL     DEFAULT NULL COMMENT '扩展字段',
    ext08      varchar(32)         NULL     DEFAULT NULL COMMENT '扩展字段',
    ext09      varchar(32)         NULL     DEFAULT NULL COMMENT '扩展字段',
    ext10      varchar(32)         NULL     DEFAULT NULL COMMENT '扩展字段',
    is_deleted tinyint(1)          NOT NULL DEFAULT 0 COMMENT '逻辑删除',
    PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB COMMENT = '用户信息表';


