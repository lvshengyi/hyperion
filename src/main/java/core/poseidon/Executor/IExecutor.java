package core.poseidon.Executor;

import core.poseidon.configuration.StatementMapper;

import java.sql.Connection;
import java.util.List;

/**
 * @author LvShengyI
 */
public interface IExecutor {

    /**
     * 执行方法
     *
     * @param conn
     * @param statementMapper
     * @param param
     * @param <E>
     * @return
     */
    <E> List<E> select(Connection conn, StatementMapper statementMapper, Object param);

    /**
     * 插入数据
     *
     * @param conn
     * @param statementMapper
     * @param param
     * @return
     */
    Boolean insert(Connection conn, StatementMapper statementMapper, Object param);
}
