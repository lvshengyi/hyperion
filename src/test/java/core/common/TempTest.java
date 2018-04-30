package core.common;

import base.BaseTest;
import core.poseidon.configuration.Configuration;
import core.poseidon.session.PoseidonSession;
import core.poseidon.session.PoseidonSessionFactory;

import java.lang.reflect.Method;
import java.sql.Connection;

/**
 * @author LvShengyI
 */
public class TempTest extends BaseTest {
    public static void main(String[] args) {
        String id = "mysql";
        Configuration config = Configuration.build(id);
        PoseidonSession session = PoseidonSessionFactory.build(config).openSession();

        System.out.println(session);
    }
}
