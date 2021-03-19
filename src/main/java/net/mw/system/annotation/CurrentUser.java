/**
 * @Description CurrentUser
 * @Author W_Messi
 * @CrateTime 2020-03-05 14:51:02
 * 
 */
package net.mw.system.annotation;

import java.lang.annotation.*;


/**
 * @Description CurrentUser当前用户绑定
 * @Author W_Messi
 * @CrateTime 2020-03-05 14:51:02
 */
@Documented
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface CurrentUser {
	/**
	 * 当前用户
	 */
	String value()default "current";

}
