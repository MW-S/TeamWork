package net.mw.teamwork.service;

import org.springframework.data.domain.PageRequest;

import net.mw.system.result.ResultMessage;
import net.mw.teamwork.pojo.po.ProjectPO;

public interface ProjectService {
	
	
	public ResultMessage getList(PageRequest page, Boolean isAdmin, String token);
	
	public ResultMessage getById(Long id);
	
	public ResultMessage add(ProjectPO po ,String token);
	
	public ResultMessage update(ProjectPO po, String token);
	
	public ResultMessage del(ProjectPO po);
	
	public ResultMessage getMyProject(String token);
	
	public ResultMessage join(String code,String token);
	
	

}
