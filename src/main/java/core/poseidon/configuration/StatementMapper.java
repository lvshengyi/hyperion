package core.poseidon.configuration;

import core.poseidon.constant.StatementType;
import lombok.Data;
import lombok.ToString;

/**
 * @author LvShengyI
 */
@Data
@ToString
public class StatementMapper {

    /**
     * 映射id
     */
    private String id;

    /**
     * 命名空间
     */
    private String nameSpace;

    /**
     * 语句类型
     */
    private StatementType statementType;

    /**
     * 语句
     */
    private String stat;

    /**
     * 参数类型
     */
    private String parameterType;

    /**
     * 返回结果类型
     */
    private String resultType;
}
