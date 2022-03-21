/**
 * @Description ReformServiceImpl
 * @Author W_Messi
 * @CrateTime 2021-03-06 21:30:31
 * 
 */
package net.mw.teamwork.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.mw.system.pojo.po.UserPO;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import net.mw.system.dao.UserDao;
import net.mw.system.result.ResultMessage;
import net.mw.system.utils.JwtTokenUtils;
import net.mw.teamwork.dao.ProjectDao;
import net.mw.teamwork.dao.ReformDao;
import net.mw.teamwork.dao.TaskDao;
import net.mw.teamwork.pojo.po.ReformPO;
import net.mw.teamwork.pojo.po.TaskPO;
import net.mw.teamwork.pojo.vo.ReformVO;
import net.mw.teamwork.pojo.vo.TaskVO;
import net.mw.teamwork.service.ReformService;

/**
 * @Description ReformServiceImpl接口实现
 * @Author W_Messi
 * @CrateTime 2021-03-06 21:30:31
 */
@Service
public class ReformServiceImpl implements ReformService {
	/**
	 * log4j实例对象.
	 */
	private static Logger logger = LogManager.getLogger(ReformServiceImpl.class);
	
	@Autowired
	private ReformDao dao;
	
	@Autowired
	private JwtTokenUtils jwtTokenUtils;
	    
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private ProjectDao projectDao;
	
	@Autowired
	private TaskDao taskDao;
	/* (non-Javadoc)
	 * @see net.mw.teamwork.service.ReformService#getList()
	 */
	@Override
	public ResultMessage getList(PageRequest page) {
		logger.trace("进入getList方法");
    	ResultMessage rs = new ResultMessage();
		try {
			Map<String,Object> data = new HashMap<String,Object>();
			PageHelper.startPage(page.getPageNumber(), page.getPageSize());
			List<ReformPO> pos = dao.getReformList();
			List<ReformVO> vos = new ArrayList<>();
			pos.forEach((item)->{
				item.setUserName(userDao.getUserById(item.getUserId()).getName());
				item.setProjectName(projectDao.getProjectById(item.getProjectId()).getName());
				item.setTaskName(taskDao.getTaskById(item.getTaskId()).getName());
				ReformVO vo = new ReformVO();
				vo.poToVo(item);
				vos.add(vo);
			});
			data.put("size", vos.size());
			data.put("data", vos);
			rs.setData(data);
			rs.setCode(1L);
			rs.setMsg("获取成功!");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			rs.setMsg("参数不正确");
			rs.setCode(2L);
		} catch (Exception e) {
			e.printStackTrace();
			rs.setMsg("获取失败");
			rs.setCode(0L);
		}
		logger.trace("退出getList方法");
		return rs;
	}

	/* (non-Javadoc)
	 * @see net.mw.teamwork.service.ReformService#getById(net.mw.teamwork.pojo.po.ReformPO)
	 */
	@Override
	public ResultMessage getById(Long id) {
		logger.trace("进入getById方法");
    	ResultMessage rs = new ResultMessage();
		try {
			Map<String,Object> data = new HashMap<String,Object>();
			ReformPO resPo = dao.getReformById(id);
			resPo.setUserName(userDao.getUserById(resPo.getUserId()).getName());
			resPo.setProjectName(projectDao.getProjectById(resPo.getProjectId()).getName());
			resPo.setTaskName(taskDao.getTaskById(resPo.getTaskId()).getName());
			ReformVO resVo = new ReformVO();
			resVo.poToVo(resPo);
			data.put("data",resVo);
			rs.setData(data);
			rs.setCode(1L);
			rs.setMsg("获取成功!");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			rs.setMsg("参数不正确");
			rs.setCode(2L);
		} catch (Exception e) {
			e.printStackTrace();
			rs.setMsg("获取失败");
			rs.setCode(0L);
		}
		logger.trace("退出getById方法");
		return rs;
	}

	/* (non-Javadoc)
	 * @see net.mw.teamwork.service.ReformService#add(net.mw.teamwork.pojo.po.ReformPO)
	 */
	@Override
	public ResultMessage add(ReformPO po, UserPO user) {
		logger.trace("进入add方法");
    	ResultMessage rs = new ResultMessage();
		try {
			if(!ObjectUtils.allNotNull(user)){
				throw new IllegalArgumentException("登录用户不得为空！");
			}
			po.setUserId(user.getId());
			if(dao.save(po) > 0 ) {
				rs.setCode(1L);
				rs.setMsg("添加成功!");
			}else {
				rs.setCode(1L);
				rs.setMsg("添加失败!");
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			rs.setMsg("参数不正确");
			rs.setCode(2L);
		} catch (Exception e) {
			e.printStackTrace();
			rs.setMsg("添加失败");
			rs.setCode(0L);
		}
		logger.trace("退出add方法");
		return rs;
	}

	/* (non-Javadoc)
	 * @see net.mw.teamwork.service.ReformService#update(net.mw.teamwork.pojo.po.ReformPO)
	 */
	@Override
	public ResultMessage update(ReformPO po, UserPO user) {
		logger.trace("进入update方法");
    	ResultMessage rs = new ResultMessage();
		try {
			if(!ObjectUtils.allNotNull(user)){
				throw new IllegalArgumentException("登录用户不得为空！");
			}
			ReformPO lastPo =dao.getReformById(po.getId());
			lastPo.setContent(po.getContent());
			if(dao.update(lastPo) > 0 ) {
				rs.setCode(1L);
				rs.setMsg("修改成功!");
			}else {
				rs.setCode(1L);
				rs.setMsg("修改失败!");
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			rs.setMsg("参数不正确");
			rs.setCode(2L);
		} catch (Exception e) {
			e.printStackTrace();
			rs.setMsg("修改失败");
			rs.setCode(0L);
		}
		logger.trace("退出update方法");
		return rs;
	}

	/* (non-Javadoc)
	 * @see net.mw.teamwork.service.ReformService#del(net.mw.teamwork.pojo.po.ReformPO)
	 */
	@Override
	public ResultMessage del(ReformPO po) {
		logger.trace("进入del方法");
    	ResultMessage rs = new ResultMessage();
		try {
			if(dao.del(po) > 0 ) {
				rs.setCode(1L);
				rs.setMsg("删除成功!");
			}else {
				rs.setCode(1L);
				rs.setMsg("删除失败!");
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			rs.setMsg("参数不正确");
			rs.setCode(2L);
		} catch (Exception e) {
			e.printStackTrace();
			rs.setMsg("删除失败");
			rs.setCode(0L);
		}
		logger.trace("退出del方法");
		return rs;
	}

}
