package com.zl.thread.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: zhouliang
 * @Date: 2018/6/26 18:33
 */
@Data
@Builder
public class CustTableMatchDto implements Serializable {
    private static final long serialVersionUID = -6796374833500131913L;
    private String flag;
    private String userNo;
}
