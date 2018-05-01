package core.poseidon.session;

import core.poseidon.executor.Executor;
import core.poseidon.configuration.DataSource;
import core.poseidon.configuration.StatementMapper;
import core.poseidon.executor.cache.CacheExecutor;
import core.poseidon.executor.cache.ICacheExecutor;
import core.poseidon.transaction.Transaction;
import core.poseidon.transaction.TransactionFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author LvShengyI
 */
public class PoseidonSession implements IPoseidonSession {

    /**
     * 执行器
     */
    private ICacheExecutor executor = new CacheExecutor(new Executor());

    /**
     * 数据源，来自poseidonSessionFactory
     */
    private DataSource dataSource;

    /**
     * 映射，来自poseidonSessionFactory
     */
    private Map<String, StatementMapper> mapper;

    /**
     * 实际数据库连接
     */
    private Connection connection;

    /**
     * 事务
     */
    private Transaction transaction;

    /**
     * 是否已经执行了close方法
     */
    private Boolean isClose = false;

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
        openConnection();

        List<T> result = executor.select(connection, mapper, param);

        return result == null || result.isEmpty() ? null : result.get(0);
    }

    @Override
    public <E> List<E> selectList(String key, Object param) {
        StatementMapper mapper = this.mapper.get(key);
        openConnection();

        return executor.select(connection, mapper, param);
    }

    @Override
    public Integer insert(String key, Object param) {
        StatementMapper mapper = this.mapper.get(key);
        openConnection();

        return executor.insert(connection, mapper, param);
    }

    @Override
    public Integer update(String key, Object param) {
        StatementMapper mapper = this.mapper.get(key);
        openConnection();

        return executor.update(connection, mapper, param);
    }

    @Override
    public Integer delete(String key, Object param) {
        StatementMapper mapper = this.mapper.get(key);
        openConnection();

        return executor.delete(connection, mapper, param);
    }

    @Override
    public <T> T getMapper(Class<T> type) {
        return null;
    }

    @Override
    public void commit() {
        if(isClose){
            return;
        }

        try {
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void rollback() {
        if(isClose){
            return;
        }

        try {
            connection.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        if(isClose){
            return;
        }

        rollback();
        isClose = true;
    }

    @Override
    public void clearCache() {
        executor.clearCache();
    }

    private void openConnection(){
        if(connection == null){
            connection = dataSource.getConnectionManage().getConnection();

            try {
                connection.setAutoCommit(false);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void openTransaction(Connection conn){
        if(transaction == null){
            transaction = TransactionFactory.newTransaction(conn);
        }
    }
}
