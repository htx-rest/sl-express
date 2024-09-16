package com.htx.common.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Collections;
import java.util.List;

/**
 * 分页结果包装
 */
@Data
@ApiModel(value = "分页数据消息体", description = "分页数据统一对象")
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class PageResponse<T> {

    @ApiModelProperty(value = "总条目数", required = true)
    private Long counts = 0L;

    @ApiModelProperty(value = "页尺寸", required = true)
    private Integer pageSize = 0;

    @ApiModelProperty(value = "总页数", required = true)
    private Long pages = 0L;

    @ApiModelProperty(value = "页码", required = true)
    private Integer page = 0;

    @ApiModelProperty(value = "数据列表", required = true)
    private List<T> items = Collections.EMPTY_LIST;

    /**
     * 通过mybatis-plus的分页对象构造对象，不封装 items 属性
     *
     * @param page 分页对象
     */
    public PageResponse(Page<?> page) {
        this.page = Convert.toInt(page.getCurrent());
        this.counts = page.getTotal();
        this.pageSize = Convert.toInt(page.getSize());
        this.pages = page.getPages();
    }

    /**
     * 通过mybatis-plus的分页对象构造对象，封装 items 属性
     *
     * @param page  分页对象
     * @param clazz 指定items 属性的类型
     */
    public PageResponse(Page<?> page, Class<T> clazz) {
        this.page = Convert.toInt(page.getCurrent());
        this.counts = page.getTotal();
        this.pageSize = Convert.toInt(page.getSize());
        this.pages = page.getPages();
        this.items = BeanUtil.copyToList(page.getRecords(), clazz);
    }
}
