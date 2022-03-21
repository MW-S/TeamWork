package net.mw.teamwork.controller;

import javax.servlet.http.HttpServletRequest;

import net.mw.system.annotation.CurrentUser;
import net.mw.system.pojo.po.UserPO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.mw.system.result.ResultMessage;
import net.mw.teamwork.pojo.po.ProjectPO;
import net.mw.teamwork.pojo.vo.ProjectVO;
import net.mw.teamwork.service.ProjectService;

/**
 * @Description ProjectController接口实现
 * @Author W_Messi
 * @CrateTime 2021-03-06 23:42:23
 */
@RestController
@RequestMapping("project")
public class ProjectController {

	/**
	 * log4j实例对象.
	 */
	private static Logger logger = LogManager.getLogger(ProjectController.class);
	

    @Autowired
    private ProjectService service;
    
    
    @GetMapping(value = "/getList")
    public ResultMessage getList(@RequestParam("page") int page, @RequestParam("size") int size
    		, @CurrentUser UserPO user){
		logger.trace("进入getList方法");
		PageRequest pageVo = new PageRequest(page, size);
		ResultMessage rs=service.getList(pageVo, user);
		logger.trace("退出getList方法");
		return rs;
    }
    
    @GetMapping(value = "/getById")
    public ResultMessage getById(@RequestParam("id") String id){
		logger.trace("进入getById方法");
//		ProjectPO po = vo.voToPo(ProjectPO.class);
		ResultMessage rs=service.getById(Long.valueOf(id));
		logger.trace("退出getById方法");
		return rs;
    }
    
    @PostMapping(value = "/add")
    public ResultMessage add(@RequestBody ProjectVO vo, @CurrentUser UserPO user){
		logger.trace("进入add方法");
		ProjectPO po = vo.voToPo(ProjectPO.class);
		ResultMessage rs=service.add(po, user);
		logger.trace("退出add方法");
		return rs;
    }
    
    @PostMapping(value = "/update")
    public ResultMessage update(@RequestBody ProjectVO vo, @CurrentUser UserPO user){
		logger.trace("进入add方法");
		ProjectPO po = vo.voToPo(ProjectPO.class);
		ResultMessage rs=service.update(po, user);
		logger.trace("退出add方法");
		return rs;
    }
    
    
    @PostMapping(value = "/del")
    public ResultMessage del(@RequestBody ProjectVO vo){
		logger.trace("进入add方法");
		ProjectPO po = vo.voToPo(ProjectPO.class);
		ResultMessage rs=service.del(po);
		logger.trace("退出add方法");
		return rs;
    }
    
    @PostMapping(value = "/join")
    public ResultMessage join(@RequestBody ProjectVO vo,@CurrentUser UserPO user){
		logger.trace("进入join方法");
		ResultMessage rs=service.join(vo.getCode(), user);
		logger.trace("退出join方法");
		return rs;
    }
    
}
