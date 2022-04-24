package com.eicas.cms.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eicas.cms.entity.NoticeEntity;
import com.eicas.cms.pojo.param.NoticeQueryParam;
import com.eicas.cms.service.INoticeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 通知公告控制器
 *
 * @author osnudt
 * @since 2022-04-18
 */
@RestController
@RequestMapping("/api/notices")
public class NoticeController {

    @Resource
    INoticeService noticeService;

    /**
     * 根据ID获取单个通知公告
     */
    @GetMapping(value = "/{id}")
    public NoticeEntity getNoticeById(@PathVariable(value = "id") Long id) {
        return noticeService.getById(id);
    }

    /**
     * 根据参数查询通知公告
     * @param param 查询参数
     * @param current 当前分页
     * @param size 分页大小
     * @return 通知公告分页数据
     */
    @PostMapping(value = "/list")
    public Page<NoticeEntity> listNotice(NoticeQueryParam param,
                                         @RequestParam(value = "current", defaultValue = "1") Integer current,
                                         @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return noticeService.listNotice(param, current, size);
    }

    /**
     * 创建一条新的通知公告
     * @param entity 通知公告对象
     * @return 是否创建成功
     */
    @PostMapping(value = "/create")
    public Boolean createNotice(@Valid @RequestBody NoticeEntity entity) {
        return noticeService.save(entity);
    }

    /**
     * 根据ID删除单个通知公告
     * @param id 通知公告编号
     * @return 是否删除成功
     */
    @PostMapping("/delete/{id}")
    public Boolean deleteNoticeById(@PathVariable(value = "id") Long id) {
        return noticeService.removeById(id);
    }

    /**
     * 根据ID列表批量删除通知公告
     * @param ids ID列表
     * @return 是否删除成功
     */
    @PostMapping("/delete")
    public Boolean batchDelete(@RequestBody List<Long> ids) {
        return noticeService.removeBatchByIds(ids);
    }

    /**
     * 更新通知公告
     * @param entity 通知公告对象
     * @return 更新是否成功
     */
    @PostMapping("/update")
    public Boolean updateNotice(@Valid @RequestBody NoticeEntity entity) {
        return noticeService.updateById(entity);
    }
}
