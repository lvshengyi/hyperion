package core.poseidon;

/**
 * @author LvShengyI
 */
public class SqlSessionFactory {

    public static SqlSession openSqlSession(){
        return new SqlSession();
    }
}
