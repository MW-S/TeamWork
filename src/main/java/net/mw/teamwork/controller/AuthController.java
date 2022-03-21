package net.mw.teamwork.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.mw.system.dao.UserDao;
import net.mw.system.pojo.po.UserPO;
import net.mw.system.pojo.vo.UserVO;
import net.mw.system.result.ResultMessage;
import net.mw.system.utils.JwtTokenUtils;
import net.mw.teamwork.service.UserService;

/**
 *
 */
@RestController
@RequestMapping("auth")
@Async
public class AuthController {
	
	/**
	 * log4j实例对象.
	 */
	private static Logger logger = LogManager.getLogger(AuthController.class);
	

    @Autowired
    private UserService service;
    @Autowired
    private JwtTokenUtils jwtTokenUtils;
    @Autowired
    private UserDao userDao;

    @GetMapping(value = "/info")
    public ResultMessage info(HttpServletRequest request){
		logger.trace("进入info方法");
		ResultMessage rs;
		String token = request.getHeader("Authorization").split("Bearer ")[1];
		if(StringUtils.isBlank(token)){
			rs = new ResultMessage();
			rs.setCode(403L);
			rs.setMsg("未登录！");
		}else{
			User user =(User) ((UsernamePasswordAuthenticationToken) jwtTokenUtils.getAuthentication(token)).getPrincipal();
			rs = service.getById(userDao.getUserByAccount(user.getUsername()).getId());
		}
		logger.trace("退出info方法");
		return rs;
    }
    
    @PostMapping(value = "/login")
    public ResultMessage login(@RequestBody UserVO vo){
		logger.trace("进入login方法");
		UserPO po = vo.voToPo(UserPO.class);
		ResultMessage rs=service.login(po);
		logger.trace("退出login方法");
		return rs;
    }
    
    @PostMapping(value = "/register")
    public ResultMessage register(@RequestBody UserVO vo){
		logger.trace("进入register方法");
		UserPO po = vo.voToPo(UserPO.class);
		ResultMessage rs=service.register(po);
		logger.trace("退出register方法");
		return rs;
    }
    
    @GetMapping(value = "/logout")
    public ResultMessage logout(){
		logger.trace("进入login方法");
		ResultMessage rs=service.logout();
		logger.trace("退出login方法");
		return rs;
    }
}
