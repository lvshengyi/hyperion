package core.poseidon;

import common.annotation.Sql;
import sun.java2d.pipe.SpanIterator;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author LvShengyI
 */
public class SqlSession implements InvocationHandler{

    public <T> T getMapper(Class<T> clz){
        return (T) Proxy.newProxyInstance(clz.getClassLoader(), new Class[]{clz}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Sql sqlAnno = method.getAnnotation(Sql.class);
        Parameter[] parameters = method.getParameters();

        String rawSql = sqlAnno.value();
        String sql = parseSql(rawSql, args);

        System.out.println(sql);

//        for(int i = 0; i < args.length; i++) {
//            System.out.println(args[i]);
//        }

        return new User();
    }

    /**
     * 解析sql字符串
     *
     * @param sql
     * @return
     */
    private String parseSql(String sql, Object[] args){
        String paramName = getParamNames(sql);

        return sql.replace(paramName, args[0].toString());
    }

    private String getParamNames(String sql) {
        String rgx = "#\\{.+?}";
        Pattern pattern = Pattern.compile(rgx);
        Matcher matcher = pattern.matcher(sql);

        if(matcher.find()) {
            return matcher.group(0);
        }

        return null;
    }

    private String getSimpleParamName(String paramName) {
        return paramName.replace("#{", "").replace("}", "");
    }

    private String getFullParamName(String paramName){
        return "#{" + paramName + "}";
    }
}
