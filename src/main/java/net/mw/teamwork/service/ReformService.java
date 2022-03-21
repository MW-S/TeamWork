package net.mw.teamwork.service;

import net.mw.system.pojo.po.UserPO;
import org.springframework.data.domain.PageRequest;

import net.mw.system.result.ResultMessage;
import net.mw.teamwork.pojo.po.ReformPO;

public interface ReformService {

	
	public ResultMessage getList(PageRequest page);
	
	public ResultMessage getById(Long id);
	
	public ResultMessage add(ReformPO po,  UserPO user);
	
	public ResultMessage update(ReformPO po,  UserPO user);
	
	public ResultMessage del(ReformPO po);
}
