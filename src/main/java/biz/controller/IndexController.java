package biz.controller;

import biz.model.domain.User;
import biz.service.PageService;
import biz.service.UserService;
import common.utils.CastUtil;
import core.hades.annotation.Action;
import core.hades.annotation.Controller;
import core.hades.dispatch.Param;
import core.hades.dispatch.View;
import core.zeus.annotation.Inject;

/**
 * @author LvShengyI
 */
@Controller
public class IndexController {

    @Inject
    private UserService userService;

    @Action("get:/index")
    public View index(Param param){
        return new View("index.jsp");
    }

    @Action("post:/show")
    public View show(Param param){
        Integer userId = CastUtil.castToInteger(param.getParamMap().get("userId"));
        User user = userService.getUserById(userId);

        return new View("show.jsp").addModel("user", user);
    }
}
