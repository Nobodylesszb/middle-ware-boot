package com.bo.elk.db.num;


import com.alibaba.druid.util.StringUtils;

/**
 * @author yuanqing
 * @since 2020/3/18
 */

public enum SettingTargetType {

    REQUEST_LOG_SWITCH,
    IP_ACCESS_SWITCH,
    AUTHENTICATION_SYSTEM_SWITCH,
    SMTP_SWITCH,
    MONITOR_SYSTEM,
    AUTHENTICATION_SYSTEM,
    SMTP;

    public static SettingTargetType get(String value) {
        SettingTargetType[] values = SettingTargetType.values();
        for (SettingTargetType settingTargetType : values) {
            if (StringUtils.equals(value, settingTargetType.name())) {
                return settingTargetType;
            }
        }
        return null;
    }

}
