package core.common;

import base.BaseTest;
import biz.model.domain.User;
import core.poseidon.configuration.Configuration;
import core.poseidon.session.PoseidonSession;
import core.poseidon.session.PoseidonSessionFactory;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.List;

/**
 * @author LvShengyI
 */
public class TempTest {
    public static void main(String[] args) {
        String id = "mysql";
        Configuration config = Configuration.build(id);
        PoseidonSession session = PoseidonSessionFactory.build(config).openSession();

        User user = session.selectOne("user.findById", 4);
        session.insert("user.insert", user);

        session.commit();
//        session.rollback();
//        session.delete("user.delete", user);
//        System.out.println(user);
//
//        user.setMaxSalary(200);
//        session.update("user.update", user);
//        session.delete("user.delete", user);
//        List<User> userList = session.selectList("user.findAll", null);
//        System.out.println(userList);
    }
}
