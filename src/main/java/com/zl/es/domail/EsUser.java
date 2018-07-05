package com.zl.es.domail;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: zhouliang
 * @Date: 2018/7/5 11:37
 */
@Data
@Builder
public class EsUser implements Serializable {
    private static final long serialVersionUID = 86896404598985002L;
    private String id;
    private int age;
    private String name;
    private Date date;
}
