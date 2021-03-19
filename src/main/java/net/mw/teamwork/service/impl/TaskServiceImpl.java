package net.mw.teamwork.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import net.mw.system.dao.UserDao;
import net.mw.system.pojo.po.UserPO;
import net.mw.system.pojo.vo.UserVO;
import net.mw.system.result.ResultMessage;
import net.mw.system.utils.JwtTokenUtils;
import net.mw.teamwork.dao.ProjectDao;
import net.mw.teamwork.dao.ReformDao;
import net.mw.teamwork.dao.TaskDao;
import net.mw.teamwork.pojo.po.ReformPO;
import net.mw.teamwork.pojo.po.TaskPO;
import net.mw.teamwork.pojo.vo.TaskVO;
import net.mw.teamwork.service.TaskService;

/**
 * @Description TaskServiceImpl接口实现
 * @Author W_Messi
 * @CrateTime 2021-03-06 21:30:03
 */
@Service
public class TaskServiceImpl implements TaskService {

	/**
	 * log4j实例对象.
	 */
	private static Logger logger = LogManager.getLogger(TaskServiceImpl.class);
	
	@Autowired
	private TaskDao dao;
	
	@Autowired
	private JwtTokenUtils jwtTokenUtils;
	    
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private ProjectDao projectDao;
	
	@Autowired
	private ReformDao reformDao;
	
	@Override
	public ResultMessage getList(PageRequest page, Boolean isAdmin, String token) {
		logger.trace("进入getList方法");
    	ResultMessage rs = new ResultMessage();
		try {
			User user =(User) ((UsernamePasswordAuthenticationToken) jwtTokenUtils.getAuthentication(token)).getPrincipal();
			Long userId = userDao.getUserByAccount(user.getUsername()).getId();
			Map<String,Object> data = new HashMap<String,Object>();
			PageHelper.startPage(page.getPageNumber(), page.getPageSize());
			List<TaskPO> pos = isAdmin?dao.getTaskList():dao.getTaskByUserId(userId);
			List<TaskVO> vos = new ArrayList<>();
			pos.forEach((item)->{
				item.setUserName(userDao.getUserById(item.getUserId()).getName());
				TaskVO vo = new TaskVO();
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

	@Override
	public ResultMessage getById(Long id) {
		logger.trace("进入getMyProject方法");
    	ResultMessage rs = new ResultMessage();
		try {
			Map<String,Object> data = new HashMap<String,Object>();
			TaskPO resPo = dao.getTaskById(id);
			resPo.setUserName(userDao.getUserById(resPo.getUserId()).getName());
			TaskVO resVo = new TaskVO();
			resVo.poToVo(resPo);
			data.put("data",resVo);
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
		logger.trace("退出getMyProject方法");
		return rs;
	}

	@Override
	public ResultMessage add(TaskPO po) {
		logger.trace("进入add方法");
    	ResultMessage rs = new ResultMessage();
		try {
			if(dao.save(po) > 0 && dao.flushSave(po.getProjectId(), po.getId()) > 0) {
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

	@Override
	public ResultMessage update(TaskPO po, String token) {
		logger.trace("进入update方法");
    	ResultMessage rs = new ResultMessage();
		try {
			User user =(User) ((UsernamePasswordAuthenticationToken) jwtTokenUtils.getAuthentication(token)).getPrincipal();
			UserPO userPo = userDao.getUserByAccount(user.getUsername());
			TaskPO lastPo =dao.getTaskById(po.getId());
			lastPo.setName(po.getName());
			lastPo.setType(po.getType());
			lastPo.setDescript(po.getDescript());
			//判断是否为修改状态
			String content = null;
			boolean isFinished = lastPo.getState() != po.getState();
			if(isFinished) {
				content = po.getState() == 1?"任务"+po.getName()+"被"+userPo.getName()+"完成":"任务"+po.getName()+"被重启";
				lastPo.setState(po.getState());
			}
			ReformPO reformPo = new ReformPO();
			reformPo.setContent(content);
			reformPo.setUserId(po.getUserId());
			reformPo.setTaskId(lastPo.getId());
//			reformPo.setProjectId(projectDao.getProjectByTaskId(lastPo.getId()).getId());
			reformPo.setProjectId(lastPo.getProjectId());
			if(dao.update(lastPo) > 0 && reformDao.save(reformPo) > 0 ) {
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

	@Override
	public ResultMessage del(TaskPO po) {
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

	@Override
	public ResultMessage getUserListByProjectId(Long projectId) {
		logger.trace("进入getUserListByProjectId方法");
    	ResultMessage rs = new ResultMessage();
		try {
			Map<String,Object> data = new HashMap<String,Object>();
			List<UserPO> pos = dao.getUserListByProjectId(projectId);
			List<UserVO> vos = new ArrayList<>();
			pos.forEach((item)->{
				UserVO vo = new UserVO();
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
		logger.trace("退出getUserListByProjectId方法");
		return rs;
	}

}
