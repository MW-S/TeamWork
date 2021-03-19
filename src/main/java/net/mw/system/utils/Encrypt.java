/**
 * 
 */
package net.mw.system.utils;

import org.apache.commons.codec.Charsets;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.google.common.hash.Hashing;

/**
 * @Description Sha-256加密工具
 * @author wessi
 * @CreateTime 2020/3/2 11:49
 */
public class Encrypt implements PasswordEncoder{
	/**	 私有构造器	**/
	public Encrypt() {};
	/**	加密算法	**/
    public final static String HASH_ALGORITHM_NAME = "SHA-256";
    /**  循环次数 **/
    public final static int HASH_ITERATIONS = 20;
    
    /**  循环次数 **/
    public static String TEMP_SALT = "";
    public static void setTempSalt(String salt) {
    	TEMP_SALT = salt;
    }
    /**  执行加密-采用SHA256和盐值加密 **/
    public static String encrypt(String password, String salt) {
    	String afterEncryptPwd = password;    
	    //循环加密
	    for (int i = 0; i < HASH_ITERATIONS;i++) {
	    	afterEncryptPwd = Hashing.sha256().newHasher().putString(salt + afterEncryptPwd, Charsets.UTF_8).hash().toString();
	    }
    
	    return afterEncryptPwd;
    }
	@Override
	public String encode(CharSequence password) {
		String afterEncryptPwd = password.toString();    
	    for (int i = 0; i < HASH_ITERATIONS;i++) {
	    	afterEncryptPwd = Hashing.sha256().newHasher().putString(TEMP_SALT + afterEncryptPwd, Charsets.UTF_8).hash().toString();
	    }
	    return afterEncryptPwd;
	}
	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		String resStr = encode(rawPassword);
		return StringUtils.equals(resStr,encodedPassword);
	}

}
