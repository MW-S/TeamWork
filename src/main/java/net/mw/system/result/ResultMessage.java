package net.mw.system.result;

import java.util.Map;

import org.springframework.stereotype.Component;

import net.mw.system.utils.JsonHelper;



/**
 * 服务返回信息类.
 * 
 * @author 侯骏雄
 * @since 6.0.0
 */
@Component
public class ResultMessage {
	/**
	 * 服务类执行结果编码.
	 */
	private Long code;

	/**
	 * 服务类执行结果信息.
	 */
	private String msg;

	/**
	 * 返回参数，由vo的json字符串组成.
	 */
	private Map<String, Object> data;

	/**
	 * @return 获取的code
	 */
	public final Long getCode() {
		return code;
	}

	/**
	 * 设置code的方法.
	 * 
	 * @param code赋值给code
	 */
	public final void setCode(Long code) {
		this.code = code;
	}

	/**
	 * @return 获取的msg
	 */
	public final String getMsg() {
		return msg;
	}

	/**
	 * 设置msg的方法.
	 * 
	 * @param msg赋值给msg
	 */
	public final void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * @return 获取的data
	 */
	public final Map<String, Object> getData() {
		return data;
	}

	/**
	 * 设置data的方法.
	 * 
	 * @param data赋值给data
	 */
	public final void setData(Map<String, Object> data) {
		this.data = data;
	}

	/**
	 * 拼接json字符串以返回.
	 * 
	 * @return 返回信息的json字符串
	 */
	public final String toJson() {
		return JsonHelper.toJson(this);
	}
}
