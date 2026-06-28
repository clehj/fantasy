package fantasyclehj.registry.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface AutoRegister {
    /**
     * 注册名称，如果不指定则使用字段名
     */
    String name() default "";

    /**
     * 创造模式标签页的ID
     */
    String tab() default "misc";

    /**
     * 是否创建对应的 ItemBlock（仅对 Block 有效）
     */
    boolean createItemBlock() default true;
}