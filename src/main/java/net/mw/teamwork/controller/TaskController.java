package net.mw.teamwork.controller;

import javax.servlet.http.HttpServletRequest;

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
import net.mw.teamwork.pojo.po.TaskPO;
import net.mw.teamwork.pojo.vo.TaskVO;
import net.mw.teamwork.service.TaskService;


/**
 * @Description TaskController接口实现
 * @Author W_Messi
 * @CrateTime 2021-03-06 23:51:25
 */
@RestController
@RequestMapping("task")
public class TaskController {
	/**
	 * log4j实例对象.
	 */
	private static Logger logger = LogManager.getLogger(TaskController.class);
	

    @Autowired
    private TaskService service;
    
    
    
    @GetMapping(value = "/getList")
    public ResultMessage getList(@RequestParam("page") int page,@RequestParam("size") int size
			,HttpServletRequest request){
		logger.trace("进入getList方法");
		PageRequest pageVo = new PageRequest(page, size);
		ResultMessage rs=service.getList(pageVo, request.getHeader("Authorization").split("Bearer ")[1]);
		logger.trace("退出getList方法");
		return rs;
    }
    
    @GetMapping(value = "/getById")
    public ResultMessage getById(@RequestParam("id") String id){
		logger.trace("进入getById方法");
//		TaskPO po = vo.voToPo(TaskPO.class);
		ResultMessage rs=service.getById(Long.valueOf(id));
		logger.trace("退出getById方法");
		return rs;
    }
    
    @GetMapping(value = "/getUserListByProjectId")
    public ResultMessage getUserListByProjectId(@RequestParam("id") String id){
		logger.trace("进入getUserListByProjectId方法");
		ResultMessage rs=service.getUserListByProjectId(Long.valueOf(id));
		logger.trace("退出getUserListByProjectId方法");
		return rs;
    }
    
    @PostMapping(value = "/add")
    public ResultMessage add(@RequestBody TaskVO vo){
		logger.trace("进入add方法");
		TaskPO po = vo.voToPo(TaskPO.class);
		ResultMessage rs=service.add(po);
		logger.trace("退出add方法");
		return rs;
    }
    
    @PostMapping(value = "/update")
    public ResultMessage update(@RequestBody TaskVO vo,HttpServletRequest request){
		logger.trace("进入add方法");
		TaskPO po = vo.voToPo(TaskPO.class);
		ResultMessage rs=service.update(po, request.getHeader("Authorization").split("Bearer ")[1]);
		logger.trace("退出add方法");
		return rs;
    }
    
    
    
    @PostMapping(value = "/del")
    public ResultMessage del(@RequestBody TaskVO vo){
		logger.trace("进入add方法");
		TaskPO po = vo.voToPo(TaskPO.class);
		ResultMessage rs=service.del(po);
		logger.trace("退出add方法");
		return rs;
    }
}
