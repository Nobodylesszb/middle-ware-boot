package com.jwt.token.demo.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultEnum implements BaseEnumType {

    /**
     * 系统异常
     */
    UNKNOWN_ERROR("500", "系统异常，请联系管理员！"),

    CODE_EXIST("400", "编码已存在"),

    /**
     * 正常成功的响应
     */
    SUCCESS("200", "success"),

    /**
     * token令牌不能为空
     */
    TOKEN_NOT_FOUND("200001", "token不能为空！"),

    /**
     * 租户ID不能为空
     */
    TENANT_ID_NOT_FOUND("200002", "tenantId不能为空！"),

    /**
     * 用户名不能为空
     */
    USERNAME_NOT_FOUND("200003", "用户名不能为空！"),

    REQUEST_ID_NOT_FOUND("200005", "requestId不能为空！"),



    /**
     * 企业ID不能为空
     */
    ENTERPRISE_ID_NOT_FOUND("200004", "请求ID不能为空！"),

    CREATE_ACCOUNT_EXCEPTION("400", "创建账号异常！"),

    CREATE_ACCOUNT_TYPE_ERROR("400", "创建账号类型异常！"),

    USER_ID_IS_NULL("400", "用户ID不能为空！"),

    USER_PASSWORD_ERROR("400", "用户密码错误"),
    USER_PASSWORD_LAST_SAME_ERROR("400", "新密码不能与旧密码一致"),
    USER_PASSWORD_NOT_ALLOW_UPDATE_ERROR("400", "用户密码不允许更新"),

    USER_NOT_FOUND("400", "用户不存在！"),

    USER_DEPARTURE_TIME_BEFORE_JOIN_TIME("400", "用户离职时间不得早于入职时间！"),

    UPDATE_ACCOUNT_EXCEPTION("400", "更新账号信息异常！"),

    UPDATE_USER_EXCEPTION("400", "更新员工信息异常！"),

    CHECK_PHONE_EXCEPTION("400", "手机格式不正确！"),

    CHECK_EMAIL_EXCEPTION("400", "邮箱格式不正确！"),

    DEPARTMENT_CHILDREN_EXIST("400", "该部门下存在级联的子部门，不允许删除！"),

    APPLICATION_IS_NOT_EXIST("400", "当前应用不存在！"),

    DEPARTMNET_MANNAGER_RELATION_UPDATE_EXCEPTION("400", "设置部门主管出现异常！"),

    DELETE_DEPPARTMENT_USER_RELATION_EXCEPTION("400", "删除用户与部门的关联出现异常！"),

    DELETE_USER_MANANGER_RELATION_EXCEPTION("400", "删除用户与部门主管的关联出现异常！"),

    APP_IS_REGISTER_ACCOUNT_TYPE("400", "当前应用已经注册权限服务！"),

    TENANTID_IS_NULL("400", "租户ID为空！"),

    GROUP_USER_RELATION_USERID_IS_NULL("400", "群组新增成员的用户ID数组为空！"),

    GROUP_USER_RELATION_GROUPID_IS_NULL("400", "群组新增成员的群组ID为空！"),

    DELETE_GROUP_ERROR("400", "删除群组出错！"),

    UPDATE_GROUP_MANANGER_IS_NULL("400", "设置组长的组长标识为空！"),

    ACCOUNT_TYPE_NOT_FOUND("400", "账号类型ID不能为空！"),

    USER_DEPARTMENT_RELATION_ADD_ERROR("400", "新增用户与部门的关联出错！"),

    USER_JOB_RELATION_ADD_ERROR("400", "新增用户与职位的关联出错！"),

    PHONE_IS_EXISTS("400", "该手机号已注册！"),

    EMAIL_IS_EXISTS("400", "该邮箱已注册！"),

    ACCOUNT_NAME_IS_EXISTS("400", "该账号名已存在，请重新输入！"),

    ACCOUNT_NOT_FOUND("400", "该账号不存在！"),

    PERMISSION_SERVICE_IS_NULL("400", "当前应用的权限服务为空!"),

    ACCOUNT_NAME_IS_NOT_NULL("400", "账号名不能为空！"),

    USER_NUMBER_IS_NOT_NULL("400", "工号不能为空！"),

    USER_NUMBER_IS_EXIST("400", "该工号已注册！"),

    DEPARTMENT_HAVE_USERS("400", "该部门下存在关联的用户，不能删除！"),

    ACCOUNT_TYPE_STATUS_NOT_NULL("400", "账号类型状态不能为空！"),

    JOB_NAME_IS_EXIST("400", "职位名称已存在！"),

    JOB_NAME_NOT_NULL("400", "职位名称不能为空!"),

    GROUP_ID_NOT_NULL("400", "群组ID不能为空!"),

    GROUP_NOT_EXIST("400", "群组不存在!"),

    GROUP_NAME_NOT_NULL("400", "群组名称不能为空!"),

    GROUP_NAME_IS_EXIST("400", "群组名称已存在！"),

    USER_ID_IS_NOT_NULL("400", "用户ID不能为空！"),

    ROLE_GROUP_NAME_IS_EXIST("400", "角色组名称已存在!"),

    ROLE_GROUP_EXIST_ROLE("400", "角色组下存在角色不可以删除!"),

    DEFAULT_ROLE_GROUP_NOT_UPDATED_OR_DELETE("400", "默认角色组不可修改或删除"),

    APP_NAME_IS_EXIST("400", "应用名已存在！"),

    APP_CODE_IS_EXIST("400", "应用编码已存在！"),

    APP_BATCH_CREATE_NAME_REPETITION("400", "应用传入名称重复！"),

    APP_BATCH_CREATE_CODE_REPETITION("400", "应用传入编码重复！"),

    MENU_GROUP_NAME_IS_EXIST("400", "菜单分组名称已存在！"),

    MENU_GROUP_CODE_IS_EXIST("400", "菜单分组编码已存在！"),

    USER_ATTR_NOT_NULL("400", "用户属性ID不能为空！"),

    ACCOUNT_ID_IS_NULL("400", "账号ID不能为空！"),

    APP_ID_IS_NULL("400", "应用ID不能为空！"),

    API_RESOURCE_GROUP_NOT_FOUND("400", "api分类不存在"),

    API_RESOURCE_GROUP_NAME_EXISTS("400", "api分类名称重复"),

    BATCH_UPDATE_API_APP_RELATION_ERROR("400", "批量更新API与应用权限的关联异常！"),

    API_ID_NOT_NULL("400", "API主键ID不能为空！"),

    PARENT_ID_IS_NOT_OWN("400", "更改上级不能选部门自己！"),

    USER_ATTR_NAME_NOT_NULL("400", "自定义字段名称不能为空！"),

    API_RESOURCE_TYPE_PARENT_IS_NOT_OWN("400", "api分组父级不能选自身"),

    API_RESOURCE_TYPE_PARENT_NOT_EXIST("400", "api分组父级不存在"),

    FUNCTION_GROUP_ELEMENT_RESOURCE_PARENT_IS_NOT_OWN("400", "元素父级不能选自身"),

    ELEMENT_HAS_MORE_GROUP("400", "同个菜单编码关联多个菜单组编码"),

    FUNCTION_GROUP_ELEMENT_RESOURCE_PARENT_IS_NOT("400", "元素父级不存在"),

    MANAGER_TYPE_NOT_EXIST("400", "该主管类型不存在！"),

    MANAGER_TYPE_IS_EXISTED("400", "该主管类型已存在！"),

    API_RESOURCE_TYPE_IS_EXIST_API("400", "api分组存在api"),

    API_RESOURCE_TYPE_IS_EXIST_CHRILDREN("400", "存在子节点，不能删除"),

    USER_DETAIL_ID_NOT_NULL("400", "用户详情ID不能为空！"),

    JOIN_DEPARTURE_TIME_ERROR("400", "离职日期不能小于入职日期！"),

    JOIN_FROM_TO_TIME_ERROR("400", "入职开始时间不能大于入职结束时间！"),

    DEPARTURE_FROM_TO_TIME_ERROR("400", "离职开始时间不能大于离职结束时间！"),

    DEPARTMENT_CODE_IS_USE("400", "当前组织编码已存在！"),

    CHECK_IDNUMBER_EXCEPTION("400", "身份证号码格式不正确！"),

    ACCOUNT_STATUS_NOT_NULL("400", "账号状态不能为空！"),

    JOB_ID_NOT_NULL("400", "职位ID不能为空！"),

    DEPARTMENT_NAME_NOT_NULL("400", "组织名称不能为空！"),

    DEPARTMENT_CODE_NOT_NULL("400", "组织编码不能为空！"),

    DEPARTMENT_TYPE_NOT_NULL("400", "组织分类不能为空！"),

    DEPARTMENT_NAME_IS_EXIST("400", "部门已存在"),

    DEPARTMENT_CODE_IS_EXIST("400", "部门编码已存在"),

    DEPARTMENT_NOT_EXIST("400", "父级部门不存在"),

    VALID_INET4_FAILER("400", "校验IPV4失败"),
    REQUEST_METHOD_IS_NULL("400", "请求方法为空"),
    PERMISSION_SERVICE_IS_DISABLED("400", "权限服务已禁用"),
    DELETE_APP_IS_REGISTER_ACCOUNT_TYPE("400", "当前应用已关联权限服务，禁止删除!"),

    /**
     * 应用code 重复
     */
    APPLICATION_CODE_REPEAT("200005", "应用code已经被使用，请修改后在添加"),

    DEPARTMENT_TYPE_IS_EXIST("400", "该组织分类已存在"),

    DATA_ALREADY_EXISTS("400","数据已经存在，请勿重复添加"),

    APPLICATION_STATUS_NOT_NULL("400","应用状态不能为空"),

    ELEMENT_RESOURCE_CODE_NOT_REPLATE("400","编码不能重复"),

    FUNCTION_CODE_EXIST("400", "功能组编码已被占用，请重新输入！"),

    NAME_LENGTH_OVERLONG("400", "功能组名称过长!"),


    CODE_LENGTH_OVERLONG("400", "功能组编码过长!"),

    FUNCTION_GROUP_IS_NOT_EXIST("400", "功能组不存在!"),

    ACCOUNT_TYPE_IS_NOT_EXISTS("400", "该账号类型不正确"),

    SAVE_ERROR("400", "功能组保存失败!"),

    BODY_NOT_NULL("400","请求体不能为空"),

    /**
     * 应用类型
     */
    APPLICATION_TYPE_NAME_EXISTS("400", "应用类型名称重复"),

    APPLICATION_TYPE_CODE_EXISTS("400", "应用类型编码重复"),

    APPLICATION_EXISTS_UNDER_APPLICATION_TYPE("400", "应用类型下存在应用，不可删除"),

    API_IMPORT_PATTERN_ERROR("400", "文件格式错误"),

    API_IMPORT_NAME_PATTERN_ERROR("400", "接口名称或者分类名不符合规范，英文，数字，下划线，且只能以英文开头"),

    /**
     * 验证码
     */
    AUTHENTICATION_PARAMETER_MISSING_CODE("22001010009", "请传递验证码参数"),
    AUTHENTICATION_PARAMETER_CODE_NOT_EXISTS("22001010010", "验证码不存在"),
    AUTHENTICATION_PARAMETER_CODE_NOT_MATCHES("22001010011", "验证码不匹配"),
    AUTHENTICATION_PARAMETER_CODE_EXPIRED("22001010012", "验证码已过期"),

    /**
     * 角色用户关联
     */
    USER_ROLE_RELATION_NOT_DUPLICATION("400", "角色用户关联不可重复！"),
    /**
     * 文件上传
     */
    TABLE_NAME_EMPTY("400","表名不能为空"),
    TABLE_NAME_NOT_CHINESE("400","表名不能为中文"),
    FILE_PARSE_FAIL("400","文件解析错误"),


    /**
     * 角色群组关联
     */
    GROUP_ROLE_RELATION_NOT_DUPLICATION("400", "角色群组关联不可重复！"),
    DEPARTMENT_ROLE_RELATION_NOT_DUPLICATION("400", "角色部门关联不可重复！"),


    TERMINAL_IP_EXISTED("400", "设备IP已被占用"),

    ;

    private String code;

    private String msg;

}