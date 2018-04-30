package core.poseidon.constant;

import lombok.Getter;

/**
 * @author LvShengyI
 *
 * 数据源类型（使用连接池与否）
 */
public enum DataSourceTypeEnum {
    UNPOOLED(1, "UNPOOL"),
    POOL(2, "POOL");

    @Getter
    private Integer code;

    @Getter
    private String desc;

    DataSourceTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
