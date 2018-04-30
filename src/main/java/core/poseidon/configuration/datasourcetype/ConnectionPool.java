package core.poseidon.configuration.datasourcetype;

import common.utils.TimeUtil;
import core.poseidon.configuration.DataSource;
import lombok.Getter;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * @author LvShengyI
 */
public class ConnectionPool extends ConnectionUnpool{

    /**
     * 空闲列表
     */
    private LinkedList<PoolConnection> idleConnectionList;

    /**
     * 活跃列表
     */
    private LinkedList<PoolConnection> activeConnectionList;

    public ConnectionPool(DataSource dataSource) {
        super(dataSource);

        idleConnectionList = new LinkedList<>();
        activeConnectionList = new LinkedList<>();
    }

    @Override
    public Connection getConnection() {
        return popConnection().getConnection();
    }

    /**
     * 获取连接
     * 当空闲连接池有连接的时候，直接取出返回
     * 如果空闲连接池没有，则检查活跃连接池是否达到最大活跃连接数
     * 如果没有达到，则实例化连接返回
     * 如果已达到，则对第一个连接进行检查，如果已失效，则回滚并实例化一个新的连接返回
     * 如果未达到，则循环等待
     *
     * @return
     */
    private synchronized PoolConnection popConnection(){
        if (!idleConnectionList.isEmpty()) {
            return idleConnectionList.removeFirst();
        }

        while (true){
            if (activeConnectionList.size() < dataSource.getMaxActiveConnectionNum()) {
                PoolConnection poolConnection = new PoolConnection(createConnection());

                activeConnectionList.add(poolConnection);

                return poolConnection;
            }

            PoolConnection poolConnection = activeConnectionList.getFirst();
            Long expireTime = poolConnection.getStartRunTime() + dataSource.getExpireTime();
            Long curTime = TimeUtil.mescToSec(TimeUtil.getTimeStamp());

            if(expireTime > curTime) {
                Connection realConnection = poolConnection.getConnection();

                try {
                    if(!realConnection.getAutoCommit()){
                        realConnection.rollback();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                poolConnection = new PoolConnection(createConnection());

                activeConnectionList.removeFirst();
                activeConnectionList.add(poolConnection);

                return poolConnection;
            }
        }
    }

    /**
     * Connection包装类，主要为了保存Connection开始时间状态
     */
    private class PoolConnection implements InvocationHandler{

        @Getter
        private Connection connection;

        @Getter
        private Long startRunTime;

        /**
         * Connection的close方法方法名
         */
        private static final String CLOSE_METHOD_NAME = "close";

        PoolConnection(Connection connection){
            this.connection = connection;
            this.startRunTime = TimeUtil.mescToSec(TimeUtil.getTimeStamp());
        }

        /**
         * 对close方法进行代理，使Connection并不真正关闭，而是存入空闲列表
         *
         * @param proxy
         * @param method
         * @param args
         * @return
         * @throws Throwable
         */
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if(CLOSE_METHOD_NAME.equals(method.getName())){
                idleConnectionList.push(this);

                return null;
            }

            return method.invoke(connection, args);
        }
    }
}
