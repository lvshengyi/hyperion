package biz.controller;

import biz.service.PageService;
import core.hades.annotation.Action;
import core.hades.annotation.Controller;
import core.hades.dispatch.Param;
import core.hades.dispatch.View;
import core.zeus.annotation.Inject;

/**
 * @author LvShengyI
 */
@Controller
public class TestController {

    @Inject
    private PageService pageService;

    @Action("get:/test")
    public View index(Param param){
        System.out.println(2222);
        return new View("index.jsp");
    }

    @Action("post:/test")
    public View show(Param param){
        System.out.println(3333);
        return new View("index.jsp");
    }
}
