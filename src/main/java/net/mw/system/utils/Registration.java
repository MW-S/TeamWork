package net.mw.system.utils;
import java.security.SecureRandom;
import java.util.Map;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

/**
 * 短信验证码工具类.
 * 
 * @author 王熙华
 * @since 6.0.0
 */
public class Registration {
	private final static String accessKeyId="";//商家注册阿里短信平台后添加的accessKeyId
	private final static String accessSecret="";//商家注册阿里短信平台后添加的accessSecret
	
	public  static Map<String, Object> SendSms(String phoneNum) {
		DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId,accessSecret);
        IAcsClient client = new DefaultAcsClient(profile);
        Map<String,Object> reslut=null;
        CommonRequest request = new CommonRequest();
        //配置参数
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phoneNum);
        request.putQueryParameter("SignName", "欣龙");
        request.putQueryParameter("TemplateCode", "SMS_185230393");
        
        SecureRandom random=new SecureRandom();
        int verifyCode=random.nextInt(999999);//生成随机数
        request.putQueryParameter("TemplateParam","{\"code\":"+verifyCode+"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            reslut=JsonHelper.jsonToMap(response.getData());
            reslut.put("verifyCode", verifyCode);
            System.out.println(reslut);
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }		
		return reslut;
	}
}
