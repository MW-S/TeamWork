package net.mw.system.init;

import net.mw.system.annotation.CurrentUser;
import net.mw.system.dao.UserDao;
import net.mw.system.pojo.po.UserPO;
import net.mw.system.utils.JwtTokenUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @Description 用于绑定@CurrentUser的方法参数解析器
 * @Author W_Messi
 * @CrateTime 2020-03-05 14:57:09
 */
@Configuration
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

	public static final String LOGIN_TOKEN_KEY = "Authorization";

	@Autowired
	private UserDao userDao;

	@Autowired
	private JwtTokenUtils jwtTokenUtils;
	
	public CurrentUserMethodArgumentResolver() {
		
    }
	/**
	 * 判断参数类型是否为User,并且判断是否有注解@CurrentUser
	 */
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(UserPO.class) &&
                parameter.hasParameterAnnotation(CurrentUser.class);
    }

	/**
	 *@CurrentUser绑定当前用户
	 * 
	 */
	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		String Authorization = webRequest.getHeader(LOGIN_TOKEN_KEY);
		if (Authorization == null || Authorization.isEmpty()) {
			return null;
		}
		String token = Authorization.split("Bearer ")[1];
		User user =(User) ((UsernamePasswordAuthenticationToken) jwtTokenUtils.getAuthentication(token)).getPrincipal();
		UserPO currentUser=new UserPO();
		currentUser=userDao.getUserByAccount(user.getUsername());
		return currentUser;
	}
}
