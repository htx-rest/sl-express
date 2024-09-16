package com.htx.common.util;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PageUtils {

    /**
     * 将分页数据转换成分页信息转化成返回信息
     * @param page 数据库查询结果page
     * @param clazz 转化后的模型类型
     * @param pageFunction 需要特别处理的分页数据转化接口
     * @param <T> 返回数据类型
     * @param <X> 数据库查询类型
     * @return
     */
    public static <T, X> PageResponse<T> toResponsePage(IPage<X> page, Class<T> clazz, PageFunction<X, T> pageFunction) {
        List<X> records = page.getRecords();
        List<T> result = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(records)) {
            result = records.stream().map(x -> {
                JSONObject entries = JSONUtil.parseObj(x);
                T t = JSONUtil.toBean(entries, clazz);
                if (pageFunction != null) {
                    pageFunction.map(x, t);
                }
                return t;
            }).collect(Collectors.toList());
        }
        return PageResponse.<T>builder()
                .items(result) //查询结果
                .counts(page.getTotal()) //查询总条数
                .page((int) page.getCurrent()) //当前码数
                .pages(page.getPages()) //总页码数
                .pageSize((int) page.getSize()) //每页条数
                .build();
    }

}
