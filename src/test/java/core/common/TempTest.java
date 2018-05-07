package core.common;

import biz.model.domain.User;
import core.poseidon.configuration.Configuration;
import core.poseidon.session.PoseidonSession;
import core.poseidon.session.PoseidonSessionFactory;

/**
 * @author LvShengyI
 */
public class TempTest {
    public static void main(String[] args) {
        String id = "mysql";
        Configuration config = Configuration.build(id);
        PoseidonSession session = PoseidonSessionFactory.build(config).openSession();

        User user = session.selectOne("user.findById", 1);

        System.out.println(user);

        user.setMaxSalary(user.getMaxSalary() + 100);
        session.insert("user.insert", user);
    }
}
