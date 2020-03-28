package read.write.talk.beyou.aspect;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface LogAspectInterface {
    boolean check() default false;
}
