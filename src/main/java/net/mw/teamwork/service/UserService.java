package net.mw.teamwork.service;

import org.springframework.data.domain.PageRequest;

import net.mw.system.pojo.po.UserPO;
import net.mw.system.result.ResultMessage;
import net.mw.teamwork.pojo.po.TaskPO;

/**
 * @Description UserService接口实现
 * @Author W_Messi
 * @CrateTime 2021-03-03 16:21:52
 */
public interface UserService {


    /**
    *
    * 2020年4月17日
    * @Param user 用户信息
    * @Param code 信息标识码
    * Map<String,Object>
    */
   public ResultMessage getList(PageRequest page);
	
    /**
     *
     * 2020年4月17日
     * @Param user 用户信息
     * @Param code 信息标识码
     * Map<String,Object>
     */
    public ResultMessage login(UserPO user);


    /**
     *
     * 2020年4月17日
     * @Param user 用户信息
     * Map<String,Object>
     */
    public ResultMessage register(UserPO user);
    
    
    /**
    *
    * 2020年4月17日
    * @Param user 用户信息
    * @Param code 信息标识码
    * Map<String,Object>
    */
   public ResultMessage logout();
   
 
	public ResultMessage getById(Long id);
	
	public ResultMessage update(UserPO po);
	
	public ResultMessage del(UserPO po);
   
	
	public ResultMessage resetPwd(UserPO po);
   
   

}
