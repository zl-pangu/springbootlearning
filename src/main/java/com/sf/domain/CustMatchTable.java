package com.sf.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: zhouliang
 * @Date: 2018/6/20 14:00
 */
@Data
public class CustMatchTable implements Serializable {
    private static final long serialVersionUID = -5979198513599827507L;
    //源文件名字
    private String sourceFileName;
    //目标文件名字
    private String targetFileName;
    //规则匹配标示
    private String flag;
}
