package com.bo.elk.db.dto;

import com.bo.elk.db.num.SettingTargetType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SystemSettingDTO {
    private SettingTargetType target;
    private String value;

}