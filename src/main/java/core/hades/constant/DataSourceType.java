package core.hades.constant;

/**
 * @author LvShengyI
 * 数据源类型（使用连接池与否）
 */
public enum DataSourceType {
    UNPOOLED(1, "UNPOOLED"),
    POOL(2, "POOL");

    private Integer code;
    private String desc;

    DataSourceType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
