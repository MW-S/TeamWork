package net.mw.teamwork.service;

import net.mw.system.pojo.po.UserPO;
import org.springframework.data.domain.PageRequest;

import net.mw.system.result.ResultMessage;
import net.mw.teamwork.pojo.po.TaskPO;

public interface TaskService {

	
	public ResultMessage getList(PageRequest page,  UserPO user);
	
	public ResultMessage getById(Long id);
	
	public ResultMessage add(TaskPO po);
	
	public ResultMessage update(TaskPO po,  UserPO user);
	
	public ResultMessage del(TaskPO po);
	
   public ResultMessage getUserListByProjectId(Long projectId);
}
