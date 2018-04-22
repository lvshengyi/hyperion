package core.hades.configuration;

import lombok.Data;

/**
 * @author LvShengyI
 */
@Data
public class DataSource {

    /**
     * 数据源id
     * 用于区分不同数据源
     */
    private String id;

    /**
     * 数据源类型（使用连接池或不使用）
     */
    private String type;

    /**
     * 数据库驱动
     */
    private String drive;

    /**
     * 数据库url
     */
    private String url;

    /**
     * 数据库用户名
     */
    private String username;

    /**
     * 数据库密码
     */
    private String password;
}
