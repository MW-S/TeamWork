package net.mw.system.result;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;


/**
 * 统一返回结构通知器.
 * 
 * @author 侯骏雄
 * @since 6.0.0
 */
@ControllerAdvice
public class ResultMessageAdvice implements ResponseBodyAdvice<Object> {
	
	/**
	 * log4j实例对象.
	 */
	private static Logger logger = LogManager.getLogger(ResultMessageAdvice.class.getName());

	/**
	 * 拦截所有的返回值.
	 * 
	 * @param returnType    返回的参数类型.
	 * @param converterType 待转型的类型.
	 * @return 只返回true.
	 * 
	 * @author 侯骏雄
	 * @since 6.0.0
	 */
	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return true;
	}

	/**
	 * 在写入http之前进行的处理.
	 * 
	 * @param body                  待写入的body内容.
	 * @param returnType            方法的返回类型.
	 * @param selectedContentType   所选content-type
	 * @param selectedConverterType 所选转型类型.
	 * @param request               当前的request
	 * @param response              当前的response
	 * @return 转换为json的统一结果对象.
	 * 
	 * @author 侯骏雄
	 * @since 6.0.0
	 */
	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
			Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
			ServerHttpResponse response) {
		logger.trace("进入beforeBodyWrite");
		Object result = body;
		if (ResultMessage.class.isAssignableFrom(returnType.getDeclaringClass())) {
			result = ((ResultMessage) result).toJson();
		}
		logger.trace("退出beforeBodyWrite");
		return result;
	}
}
