package core.poseidon.executor;

import core.poseidon.executor.handler.ParamNode;
import core.poseidon.executor.handler.ParameterHandler;
import core.poseidon.executor.handler.ResultSetHandler;
import core.poseidon.executor.handler.StatementHandler;
import core.poseidon.configuration.StatementMapper;

import java.sql.*;
import java.util.List;

/**
 * @author LvShengyI
 */
public class Executor implements IExecutor {

    @Override
    public <E> List<E> select(Connection conn, StatementMapper statementMapper, Object param) {
        String sql = StatementHandler.handle(statementMapper.getStat());
        PreparedStatement stat;

        try {
            stat = conn.prepareStatement(sql);
            ParameterHandler.handle(stat, param);
            ResultSet rs = stat.executeQuery();
            String resultType = statementMapper.getResultType();
            Class clz = Class.forName(resultType);

            return ResultSetHandler.handle(rs, clz);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Integer insert(Connection conn, StatementMapper statementMapper, Object param) {
        String rawSql = statementMapper.getStat();
        ParamNode node = StatementHandler.preProcess(rawSql);
        String sql = StatementHandler.handle(rawSql);
        PreparedStatement stat;
        try {
            stat = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ParameterHandler.handle(stat, node, param);

            stat.execute();

            ResultSet rs = stat.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Integer update(Connection conn, StatementMapper statementMapper, Object param) {
        String rawSql = statementMapper.getStat();
        ParamNode node = StatementHandler.preProcess(rawSql);
        String sql = StatementHandler.handle(rawSql);
        PreparedStatement stat;
        try {
            stat = conn.prepareStatement(sql);
            ParameterHandler.handle(stat, node, param);

            return stat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Integer delete(Connection conn, StatementMapper statementMapper, Object param) {
        String rawSql = statementMapper.getStat();
        ParamNode node = StatementHandler.preProcess(rawSql);
        String sql = StatementHandler.handle(rawSql);
        PreparedStatement stat;
        try {
            stat = conn.prepareStatement(sql);
            ParameterHandler.handle(stat, node, param);

            return stat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
