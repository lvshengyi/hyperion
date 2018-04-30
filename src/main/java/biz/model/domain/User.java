package biz.model.domain;

import lombok.Data;

/**
 * @author LvShengyI
 */
@Data
public class User {

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 密码
     */
    private String password;

    /**
     * 年龄
     */
    private Integer age;
}
