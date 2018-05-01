package core.poseidon.session;

import java.util.List;

/**
 * @author LvShengyI
 */
public interface IPoseidonSession {

    /**
     * 获取单个数据
     *
     * @param key   语句对应的mapper
     * @param params 参数
     * @param <T>
     * @return
     */
    <T> T selectOne(String key, Object params);

    /**
     * 获取数据列表
     *
     * @param key   语句对应的mapper
     * @param params 参数
     * @param <E>
     * @return
     */
    <E> List<E> selectList(String key, Object params);

    /**
     * 插入数据
     *
     * @param key   语句对应的mapper
     * @param params 参数
     * @return       数据id
     */
    Integer insert(String key, Object params);

    /**
     * 更新数据
     *
     * @param key   语句对应的mapper
     * @param params 参数
     * @return       更新的条数
     */
    Integer update(String key, Object params);

    /**
     * 删除数据
     *
     * @param key   语句对应的mapper
     * @param params 参数
     * @return       删除的条数
     */
    Integer delete(String key, Object params);

    <T> T getMapper(Class<T> type);

    /**
     * 事务提交
     */
    void commit();

    /**
     * 事务回滚
     */
    void rollback();

    /**
     * 关闭session
     */
    void close();

    /**
     * 清空缓存
     */
    void clearCache();
}
