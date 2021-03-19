/**
 * @Description Power
 * @Author W_Messi
 * @CrateTime 2020-03-04 14:25:11
 * 
 */
package net.mw.system.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Power {
	/**
	 * 接口名称
	 */
	String name();
	/**
	 * 权限列表
	 */
	Perm[] perms() default {};

}
