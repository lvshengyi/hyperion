package core.poseidon.session;

import java.util.List;

/**
 * @author LvShengyI
 */
public interface IPoseidonSession {

    /**
     * 获取单个数据
     *
     * @param stat   语句对应的mapper
     * @param params 参数
     * @param <T>
     * @return
     */
    <T> T selectOne(String stat, Object params);

    /**
     * 获取数据列表
     *
     * @param stat   语句对应的mapper
     * @param params 参数
     * @param <E>
     * @return
     */
    <E> List<E> selectList(String stat, Object params);

    /**
     * 插入数据
     *
     * @param stat   语句对应的mapper
     * @param params 参数
     * @return       数据id
     */
    Integer insert(String stat, Object params);

    /**
     * 更新数据
     *
     * @param stat   语句对应的mapper
     * @param params 参数
     * @return       更新的条数
     */
    Integer update(String stat, Object params);

    /**
     * 删除数据
     *
     * @param stat   语句对应的mapper
     * @param params 参数
     * @return       删除的条数
     */
    Integer delete(String stat, Object params);

    <T> T getMapper(Class<T> type);

    /**
     * 关闭session
     */
    void close();

    /**
     * 清空缓存
     */
    void clearCache();
}
