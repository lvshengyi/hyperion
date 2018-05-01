package core.poseidon.session;

import core.poseidon.Executor.Executor;
import core.poseidon.configuration.DataSource;
import core.poseidon.configuration.StatementMapper;
import core.poseidon.configuration.datasourcetype.ConnectionUnpool;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 * @author LvShengyI
 */
public class PoseidonSession implements IPoseidonSession {

    /**
     * 执行器
     */
    private Executor executor = new Executor();

    /**
     * 数据源，来自poseidonSessionFactory
     */
    private DataSource dataSource;

    /**
     * 映射，来自poseidonSessionFactory
     */
    private Map<String, StatementMapper> mapper;

    /**
     * 对外屏蔽构造函数
     */
    private PoseidonSession() {
    }

    public PoseidonSession(DataSource dataSource, Map<String, StatementMapper> mapper) {
        this.dataSource = dataSource;
        this.mapper = mapper;
    }

    @Override
    public <T> T selectOne(String key, Object param) {
        StatementMapper mapper = this.mapper.get(key);
        ConnectionUnpool connManage = this.dataSource.getConnectionManage();
        Connection conn = connManage.getConnection();

        List<T> result = executor.select(conn, mapper, param);

        return result == null || result.isEmpty() ? null : result.get(0);
    }

    @Override
    public <E> List<E> selectList(String key, Object param) {
        StatementMapper mapper = this.mapper.get(key);
        ConnectionUnpool connManage = this.dataSource.getConnectionManage();
        Connection conn = connManage.getConnection();

        return executor.select(conn, mapper, param);
    }

    @Override
    public Integer insert(String key, Object param) {
        StatementMapper mapper = this.mapper.get(key);
        ConnectionUnpool connManage = this.dataSource.getConnectionManage();
        Connection conn = connManage.getConnection();

        return executor.insert(conn, mapper, param);
    }

    @Override
    public Integer update(String key, Object param) {
        StatementMapper mapper = this.mapper.get(key);
        ConnectionUnpool connManage = this.dataSource.getConnectionManage();
        Connection conn = connManage.getConnection();

        return executor.update(conn, mapper, param);
    }

    @Override
    public Integer delete(String key, Object param) {
        StatementMapper mapper = this.mapper.get(key);
        ConnectionUnpool connManage = this.dataSource.getConnectionManage();
        Connection conn = connManage.getConnection();

        return executor.delete(conn, mapper, param);
    }

    @Override
    public <T> T getMapper(Class<T> type) {
        return null;
    }

    @Override
    public void close() {

    }

    @Override
    public void clearCache() {

    }
}
