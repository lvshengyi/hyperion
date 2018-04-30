package core.poseidon.session;

import core.poseidon.configuration.Configuration;

/**
 * @author LvShengyI
 */
public class PoseidonSessionFactory {

    /**
     * 配置
     */
    private Configuration config;

    /**
     * 建造者模式，产生一个poseidonSessionFactory实例
     *
     * @return
     */
    public static PoseidonSessionFactory build(Configuration config) {
        return new PoseidonSessionFactory(config);
    }

    /**
     * 对外屏蔽构造函数
     */
    private PoseidonSessionFactory() {
    }

    /**
     * 实际的构造方法
     */
    private PoseidonSessionFactory(Configuration config) {
        this.config = config;
    }

    public PoseidonSession openSession() {
        return new PoseidonSession(config.getDataSource(), config.getStatementMapperMap());
    }
}
