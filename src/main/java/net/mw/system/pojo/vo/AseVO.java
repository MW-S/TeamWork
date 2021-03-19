/**
 * @Description AseVo
 * @Author W_Messi
 * @CrateTime 2020-04-18 11:46:14
 * 
 */
package net.mw.system.pojo.vo;

/**
 * @Description AseVo接口实现
 * @Author W_Messi
 * @CrateTime 2020-04-18 11:46:14
 */
public class AseVO {

	//用户信息对象，不包含 openid 等敏感信息
	private String rawData;
	//不包括敏感信息的原始数据字符串，用于计算签名
	private String signature;
	//使用 sha1( rawData + sessionkey ) 得到字符串，用于校验用户信息
	private String encryptedData;
	//包括敏感数据在内的完整用户信息的加密数据
	private String iv;
	//加密算法的初始向量
	private String cloudID;
	//wx.login()返回的校验凭证
	private String code;
	//会话校验
	private String sessionId;

    public String getRawData() {
        return this.rawData;
    }

    public String getSignature() {
        return this.signature;
    }

    public String getEncryptedData() {
        return this.encryptedData;
    }

    public String getIv() {
        return this.iv;
    }

    public String getCloudID() {
        return this.cloudID;
    }

    public String getCode() {
        return this.code;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public void setRawData(String rawData) {
        this.rawData = rawData;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public void setEncryptedData(String encryptedData) {
        this.encryptedData = encryptedData;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }

    public void setCloudID(String cloudID) {
        this.cloudID = cloudID;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
