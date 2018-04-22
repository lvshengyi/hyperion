package core.poseidon;

import org.junit.Test;

/**
 * @author LvShengyI
 */
public class SqlSessionTest {

    @Test
    public void sqlSessionTest(){
        SqlSession session = SqlSessionFactory.openSqlSession();

        UserMapper mapper = session.getMapper(UserMapper.class);

        mapper.getUser(1);
    }
}
