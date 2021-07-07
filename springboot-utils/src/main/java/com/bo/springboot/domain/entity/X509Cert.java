package com.bo.springboot.domain.entity;

import lombok.Data;


/**
 * @author Y.H.Zhou - zhouyuhang@deepexi.com
 * @since 2021/1/18.
 * <p></p>
 */
@Data
public class X509Cert extends BaseCredential {
    private String clientSecretKey;
    private String clientCertificate;
    private String serverCaCertificate;
}
