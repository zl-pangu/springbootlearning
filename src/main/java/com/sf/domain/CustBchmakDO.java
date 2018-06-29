package com.sf.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: zhouliang
 * @Date: 2018/6/21 9:57
 */
@Data
public class CustBchmakDO implements Serializable {

    private static final long serialVersionUID = 2459069502274783063L;

    private Long id;
    //标识
    private String flag;
    //模板表
    private String templateTable;

    //模板表的基准列
    private String benchmarkClo;
}
