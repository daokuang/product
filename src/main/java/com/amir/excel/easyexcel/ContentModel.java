package com.amir.excel.easyexcel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Auther: liuyuqing
 * @Date: 2019-12-05 20:31
 * @Description:
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContentModel {
    private List<String> response;

    private String operator;

    private Integer size = 0;
}
