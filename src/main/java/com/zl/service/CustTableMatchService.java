package com.zl.service;

import com.zl.dto.CustTableMatchDto;

/**
 * @Author: zhouliang
 * @Date: 2018/6/19 17:00
 */
public interface CustTableMatchService {
    /**
     * 匹配表格，匹配结果入库
     * @param matchDto
     * @throws Exception
     */
    void parseExcles(CustTableMatchDto matchDto) throws Exception;
}
