package core.poseidon.Executor.handler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author LvShengyI
 */
public class ParameterHandler {

    private ParameterHandler() {
    }

    public static void handle(PreparedStatement stat, Object param) {
        baseHandle(stat, param, 1);
    }

    private static void baseHandle(PreparedStatement stat, Object param, Integer index) {
        try {
            if (param instanceof Integer) {
                stat.setInt(index, (int) param);
            } else if (param instanceof Long) {
                stat.setLong(index, (long) param);
            } else if (param instanceof Float) {
                stat.setFloat(index, (float) param);
            } else if (param instanceof Double) {
                stat.setDouble(index, (double) param);
            } else if (param instanceof String) {
                stat.setString(index, (String) param);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void handle(PreparedStatement stat, ParamNode node, Object param) {
        Class clz = param.getClass();
        Method[] methods = clz.getMethods();

        Integer index = 1;
        while (node != null) {
            String methodName = ResultSetHandler.getMethodNameFromFieldName(node.getFieldName(), "get");
            for (Method method : methods) {
                if (method.getName().equals(methodName)) {
                    try {
                        Object val = method.invoke(param);

                        baseHandle(stat, val, index++);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }

            node = node.next();
        }
    }
}
