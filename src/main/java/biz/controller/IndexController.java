package biz.controller;

import biz.model.domain.User;
import biz.service.UserService;
import common.utils.CastUtil;
import core.hades.annotation.Action;
import core.hades.annotation.Controller;
import core.hades.dispatch.Param;
import core.hades.dispatch.View;
import core.zeus.annotation.Inject;

import java.util.Map;

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

    @Action("post:/add")
    public View add(Param param){
        User user = new User();

        Map map = param.getParamMap();
        String username = map.get("username").toString();
        String password = map.get("password").toString();
        Integer age = CastUtil.castToInteger(map.get("age"));
        Integer maxSalary = CastUtil.castToInteger(map.get("maxSalary"));

        user.setUsername(username);
        user.setPassword(password);
        user.setAge(age);
        user.setMaxSalary(maxSalary);

        userService.save(user);

        return new View("index.jsp");
    }
}
