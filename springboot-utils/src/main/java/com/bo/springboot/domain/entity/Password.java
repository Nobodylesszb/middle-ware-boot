package com.bo.springboot.domain.entity;

import lombok.Data;


/**
 * @author Y.H.Zhou - zhouyuhang@deepexi.com
 * @since 2021/1/18.
 * <p></p>
 */
@Data
public class Password extends BaseCredential {
    private String username;
    private String password;
}
