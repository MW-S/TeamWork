/**
 * @Description ReformService
 * @Author W_Messi
 * @CrateTime 2021-03-08 12:37:48
 * 
 */
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
import net.mw.teamwork.pojo.po.ReformPO;
import net.mw.teamwork.pojo.vo.ReformVO;
import net.mw.teamwork.service.ReformService;

/**
 * @Description ReformService接口实现
 * @Author W_Messi
 * @CrateTime 2021-03-08 12:37:48
 */
@RestController
@RequestMapping("/reform")
public class ReformController {
	/*
	 * log4j实例对象.
	 */
	private static Logger logger = LogManager.getLogger(ReformController.class);

	@Autowired
	private ReformService service;
	
    @GetMapping(value = "/getList")
    public ResultMessage getList(@RequestParam("page") int page,@RequestParam("size") int size,
    		HttpServletRequest request){
		logger.trace("进入getList方法");
		PageRequest pageVo = new PageRequest(page, size);
		ResultMessage rs=service.getList(pageVo);
		logger.trace("退出getList方法");
		return rs;
    }
    
/*    @GetMapping(value = "/getById")
    public ResultMessage getById(@RequestParam("id") String id){
		logger.trace("进入getById方法");
//		TaskPO po = vo.voToPo(TaskPO.class);
		ResultMessage rs=service.getById(Long.valueOf(id));
		logger.trace("退出getById方法");
		return rs;
    }*/
    
    @PostMapping(value = "/del")
    public ResultMessage del(@RequestBody ReformVO vo){
		logger.trace("进入add方法");
		ReformPO po = vo.voToPo(ReformPO.class);
		ResultMessage rs=service.del(po);
		logger.trace("退出add方法");
		return rs;
    }
}
