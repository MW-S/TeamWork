/**
 * @Description WxAuthVO
 * @Author W_Messi
 * @CrateTime 2020-04-17 20:20:42
 * 
 */
package net.mw.system.pojo.vo;

/**
 * @Description WxAuthVO接口实现
 * @Author W_Messi
 * @CrateTime 2020-04-17 20:20:42
 */
public class WxAuthVO {
	//用户唯一标识
	private String openid ;
	
	// 会话密钥
	private String session_key;
	
	//用户在开放平台的唯一标识符，在满足 UnionID 下发条件的情况下会返回
	private String unionid;
	//错误码
	private int errcode ;
	//错误信息
	private String errmsg ;

    public String getOpenid() {
        return this.openid;
    }

    public String getSession_key() {
        return this.session_key;
    }

    public String getUnionid() {
        return this.unionid;
    }

    public int getErrcode() {
        return this.errcode;
    }

    public String getErrmsg() {
        return this.errmsg;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public void setSession_key(String session_key) {
        this.session_key = session_key;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }


    /**
	 * 错误码枚举类
	 */
	public enum ErrorCodeEnum {
		fail(-1, "系统繁忙，此时请开发者稍候再试"), OK(0, "请求成功"), UNKNOWNCODE(40029, "code 无效"),LIMIT(45011,"频率限制，每个用户每分钟100次");

		private int code;
		private String value;

		ErrorCodeEnum(int code, String value) {
			this.code = code;
			this.value = value;
		}

		public int getCode() {
			return code;
		}

		public String getValue() {
			return value;
		}

		public static String codeOf(int code) {
			for (ErrorCodeEnum type : values()) {
				if (type.getCode() == code)
					return type.getValue();
			}
			return null;
		}
	}
}
