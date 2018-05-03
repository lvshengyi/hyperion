package core.hades.dispatch;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

/**
 * @author LvShengyI
 */
@AllArgsConstructor
public class Param {

    @Getter
    private Map<String, Object> paramMap;
}
