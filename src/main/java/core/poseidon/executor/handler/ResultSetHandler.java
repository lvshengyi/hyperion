package core.poseidon.executor.handler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LvShengyI
 */
public class ResultSetHandler {

    private ResultSetHandler(){}

    public static <E, T> List<E> handle(ResultSet rs, Class<T> clz) {
        List<E> result = new ArrayList<>();

        try {
            Object obj = clz.newInstance();
            Method[] methods = clz.getMethods();
            ResultSetMetaData rsmd = rs.getMetaData();

            while (rs.next()) {
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    String fieldName = rsmd.getColumnName(i);
                    String methodName = getMethodNameFromFieldName(fieldName, "set");

                    for (Method method : methods) {
                        if(methodName.equals(method.getName())){
                            method.invoke(obj, rs.getObject(fieldName));
                        }
                    }
                }
                result.add((E)obj);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static String getMethodNameFromFieldName(String fieldName, String prefix) {
        final Character UNDER_LINE = '_';
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < fieldName.length(); i++) {
            Character ch = fieldName.charAt(i);

            if (i == 0) {
                sb.append((char) (ch - 32));
                continue;
            }

            if (UNDER_LINE.equals(ch)) {
                sb.append((char) (fieldName.charAt(++i) - 32));
            } else {
                sb.append(ch);
            }
        }

        return sb.insert(0, prefix).toString();
    }

    public static void main(String[] args) {
        String str = "maxSalary";
        String prefix = "get";

        System.out.println(getMethodNameFromFieldName(str, prefix));
    }
}
