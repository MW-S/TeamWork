package net.mw.teamwork.service;

import org.springframework.data.domain.PageRequest;

import net.mw.system.result.ResultMessage;
import net.mw.teamwork.pojo.po.TaskPO;

public interface TaskService {

	
	public ResultMessage getList(PageRequest page, Boolean isAdmin, String token);
	
	public ResultMessage getById(Long id);
	
	public ResultMessage add(TaskPO po);
	
	public ResultMessage update(TaskPO po, String token);
	
	public ResultMessage del(TaskPO po);
	
   public ResultMessage getUserListByProjectId(Long projectId);
}
