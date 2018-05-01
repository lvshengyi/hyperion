package core.poseidon.executor.cache;

import core.poseidon.executor.IExecutor;

/**
 * @author LvShengyI
 */
public interface ICacheExecutor extends IExecutor {

    /**
     * 清空缓存
     */
    void clearCache();
}
