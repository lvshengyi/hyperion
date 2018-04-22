package core.poseidon;

import common.utils.PropUtil;

/**
 * @author LvShengyI
 *
 * 使用登记式单例模式
 */
public class DataSource {

    /**
     * jdbc驱动
     */
    private String jdbcDriver;

    /**
     * 数据库url
     */
    private String url;

    /**
     * 用户名
     */
    private String user;

    /**
     * 密码
     */
    private String password;

    private DataSource(){
        this.jdbcDriver = PropUtil.getString("jdbc.driver");
        this.url = PropUtil.getString("db.url");
        this.user = PropUtil.getString("db.user");
        this.password = PropUtil.getString("db.password");
    }

    public static final DataSource getDataSource(){
        return DataSourceHolder.INSTANCE;
    }

    private static class DataSourceHolder{
        private static final DataSource INSTANCE = new DataSource();
    }

    @Override
    public String toString(){
        return String.format("driver: %s, url: %s, user: %s, password: %s", this.jdbcDriver, this.url, this.user, this.password);
    }
}
