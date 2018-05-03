package core.hades.dispatch;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * @author LvShengyI
 */
@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class Request {

    private String requestMethod;

    private String requestPath;
}
