package core.common;

import base.BaseTest;
import biz.model.domain.User;
import biz.service.PageService;
import biz.service.UserService;
import core.poseidon.configuration.Configuration;
import core.poseidon.session.PoseidonSession;
import core.poseidon.session.PoseidonSessionFactory;
import core.zeus.annotation.Inject;
import core.zeus.bean.BeanFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.List;

/**
 * @author LvShengyI
 */
public class TempTest {
    public static void main(String[] args) {
        BeanFactory beanFactory = BeanFactory.ini();
        PageService pageService = beanFactory.getBean(PageService.class.getName());
        UserService userService = beanFactory.getBean(UserService.class.getName());

        userService.test();
    }
}
