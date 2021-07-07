package com.bo.springboot.utils;

import com.bo.springboot.domain.entity.BaseCredential;
import com.bo.springboot.domain.entity.Password;
import com.bo.springboot.domain.entity.SecretTxt;
import com.bo.springboot.domain.entity.X509Cert;
import com.bo.springboot.exception.JsonParamException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.BindException;

/**
 * @author Y.H.Zhou - zhouyuhang@deepexi.com
 * @since 2020/10/27.
 * <p></p>
 */
@Getter
@AllArgsConstructor
public enum JenkinsCredentialType implements JsonParser<BaseCredential> {
    /** 密钥文本 */
    SECRET_TXT(SecretTxt.class),
    /** x509证书 */
    X509_CERT(X509Cert.class),
    /** 用户名密码 */
    PASSWORD(Password.class);

    private Class<? extends BaseCredential> typeRefClazz;

    @Override
    public BaseCredential parse(String json) {
        return JsonUtils.parse(json, this.getTypeRefClazz());
    }

    @Override
    public BaseCredential parseAndValid(String json) {
        try {
            return JsonUtils.parseAndValidate(json, this.getTypeRefClazz());
        } catch (BindException e) {
            throw new JsonParamException(e.getMessage());
        }
    }

    public static void main(String[] args) {
        BaseCredential test = JenkinsCredentialType.SECRET_TXT.parse("test");
        System.out.println(test);
    }
}