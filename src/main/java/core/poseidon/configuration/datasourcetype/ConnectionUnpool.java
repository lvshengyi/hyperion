package core.poseidon.configuration.datasourcetype;

import core.poseidon.configuration.DataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author LvShengyI
 */
public class ConnectionUnpool {

    /**
     * 是否初始化（读取driver）
     */
    protected static volatile Boolean hadIni = false;

    /**
     * 数据源
     */
    protected DataSource dataSource;

    public ConnectionUnpool(DataSource dataSource) {
        if(dataSource == null) {
            throw new IllegalArgumentException("dataSource must not be null");
        }

        if (hadIni) {
            return;
        }

        this.dataSource = dataSource;
        try {
            Class.forName(this.dataSource.getDrive());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        hadIni = true;
    }

    /**
     * 获取Connection，之所以作为包装方法，是为了createConnection可以被继承
     * @return
     */
    public Connection getConnection(){
        return createConnection();
    }

    /**
     * 创建Connection并返回
     * @return
     */
    protected Connection createConnection(){
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(dataSource.getUrl(), dataSource.getUsername(), dataSource.getPassword());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conn;
    }
}
