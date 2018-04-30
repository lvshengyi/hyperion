package core.poseidon.session;

import core.poseidon.configuration.DataSource;
import core.poseidon.configuration.StatementMapper;

import java.util.List;
import java.util.Map;

/**
 * @author LvShengyI
 */
public class PoseidonSession implements IPoseidonSession{

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
    private PoseidonSession(){};

    public PoseidonSession(DataSource dataSource, Map<String, StatementMapper> mapper){
        this.dataSource = dataSource;
        this.mapper = mapper;
    }

    @Override
    public <T> T selectOne(String stat, Object params) {
        return null;
    }

    @Override
    public <E> List<E> selectList(String stat, Object params) {
        return null;
    }

    @Override
    public Integer insert(String stat, Object params) {
        return null;
    }

    @Override
    public Integer update(String stat, Object params) {
        return null;
    }

    @Override
    public Integer delete(String stat, Object params) {
        return null;
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
