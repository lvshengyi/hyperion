package biz.service;

import core.zeus.annotation.Inject;
import core.hades.annotation.Service;
import lombok.Getter;

/**
 * @author LvShengyI
 */
@Service
public class PageService {

    @Getter
    @Inject
    private UserService userService;
}
