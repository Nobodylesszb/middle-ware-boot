package com.bo.springboot.domain.entity;

import com.bo.springboot.utils.JenkinsCredentialType;
import lombok.Data;

@Data
public abstract class BaseCredential {
    private String credentialId;
    private JenkinsCredentialType type;
    private String description;
}