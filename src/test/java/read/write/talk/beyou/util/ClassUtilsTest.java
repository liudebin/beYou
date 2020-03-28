package read.write.talk.beyou.util;

import org.junit.jupiter.api.Test;
import org.springframework.util.ClassUtils;

public class ClassUtilsTest {

    @Test
    public void test() {
        System.out.println(ClassUtils.resolvePrimitiveClassName("int"));
        System.out.println(ClassUtils.resolvePrimitiveClassName("int[]"));
        System.out.println(ClassUtils.resolvePrimitiveClassName("[B"));
        System.out.println(ClassUtils.resolvePrimitiveClassName("[b"));
        System.out.println( boolean[].class.getName());
    }
}
