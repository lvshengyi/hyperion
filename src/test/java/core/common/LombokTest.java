package core.common;

import lombok.NonNull;
import org.junit.Test;

/**
 * @author LvShengyI
 */
public class LombokTest {

    @Test
    public void nonNullTest(){
        Integer num11 = null;
        func(num11);
    }

    private static void func(@NonNull Integer num) {
    }
}
