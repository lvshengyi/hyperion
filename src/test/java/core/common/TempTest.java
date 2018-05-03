package core.common;

import biz.service.PageService;
import biz.service.UserService;
import core.zeus.bean.ZeusContext;

/**
 * @author LvShengyI
 */
public class TempTest {
    public static void main(String[] args) {
        ZeusContext zeusContext = ZeusContext.ini();
        PageService pageService = zeusContext.getBean(PageService.class.getName());
        UserService userService = zeusContext.getBean(UserService.class.getName());

        userService.test();
    }
}
