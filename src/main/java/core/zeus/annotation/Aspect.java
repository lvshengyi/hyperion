package core.zeus.annotation;

import java.lang.annotation.*;

/**
 * @author LvShengyI
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {

    Class<? extends Annotation> value();
}
