package core.poseidon.executor.cache;

import core.poseidon.configuration.StatementMapper;
import core.poseidon.executor.Executor;
import core.poseidon.executor.IExecutor;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LvShengyI
 */
public class CacheExecutor implements IExecutor, ICacheExecutor {

    private Executor executor;

    private Map<CacheKey, List<Object>> cache = new HashMap<>(16);

    public CacheExecutor(Executor executor){
        this.executor = executor;
    }

    @Override
    public <E> List<E> select(Connection conn, StatementMapper statementMapper, Object param) {
        CacheKey key = new CacheKey(statementMapper, param);
        List<Object> value = cache.get(key);

        if(value != null){
            System.out.println("本次查询使用了缓存");
            return (List<E>) value;
        }

        List<E> result = executor.select(conn, statementMapper, param);
        System.out.println("本次查询访问了数据库");
        cache.put(key, (List<Object>) result);

        return result;
    }

    @Override
    public Integer insert(Connection conn, StatementMapper statementMapper, Object param) {
        clearCache();

        return executor.insert(conn, statementMapper, param);
    }

    @Override
    public Integer update(Connection conn, StatementMapper statementMapper, Object param) {
        clearCache();

        return executor.update(conn, statementMapper, param);
    }

    @Override
    public Integer delete(Connection conn, StatementMapper statementMapper, Object param) {
        clearCache();

        return executor.delete(conn, statementMapper, param);
    }

    @Override
    public void clearCache() {
        cache = new HashMap<>(16);
    }
}
