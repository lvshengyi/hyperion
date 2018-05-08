package biz.service;

import biz.model.domain.User;
import core.hades.annotation.Service;
import core.poseidon.configuration.Configuration;
import core.poseidon.session.PoseidonSession;
import core.poseidon.session.PoseidonSessionFactory;

/**
 * @author LvShengyI
 */
@Service
public class UserService {

    private static final String dataSourceId = "mysql";
    private static Configuration config;
    private static PoseidonSession session;

    static {
        config = Configuration.build(dataSourceId);
        session = PoseidonSessionFactory.build(config).openSession();
    }

    public User getUserById(Integer id){
        return session.selectOne("user.findById", id);
    }

    public Integer save(User user){
        Integer result = session.insert("user.insert", user);
        session.commit();

        return result;
    }
}
