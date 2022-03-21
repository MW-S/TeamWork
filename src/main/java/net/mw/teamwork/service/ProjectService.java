package net.mw.teamwork.service;

import net.mw.system.pojo.po.UserPO;
import org.springframework.data.domain.PageRequest;

import net.mw.system.result.ResultMessage;
import net.mw.teamwork.pojo.po.ProjectPO;

public interface ProjectService {
	
	
	public ResultMessage getList(PageRequest page, UserPO user);
	
	public ResultMessage getById(Long id);
	
	public ResultMessage add(ProjectPO po , UserPO user);
	
	public ResultMessage update(ProjectPO po,  UserPO user);
	
	public ResultMessage del(ProjectPO po);
	
	public ResultMessage getMyProject( UserPO user);
	
	public ResultMessage join(String code, UserPO user);
	
	

}
