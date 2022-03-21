/**
 * @Description ProjectServiceImpl
 * @Author W_Messi
 * @CrateTime 2021-03-06 21:29:14
 * 
 */
package net.mw.teamwork.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.netty.util.internal.ObjectUtil;
import net.mw.system.pojo.po.UserPO;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;

import net.mw.system.dao.UserDao;
import net.mw.system.result.ResultMessage;
import net.mw.system.utils.JwtTokenUtils;
import net.mw.teamwork.dao.ProjectDao;
import net.mw.teamwork.pojo.po.ProjectPO;
import net.mw.teamwork.pojo.vo.ProjectVO;
import net.mw.teamwork.service.ProjectService;

/**
 * @Description ProjectServiceImpl接口实现
 * @Author W_Messi
 * @CrateTime 2021-03-06 21:29:14
 */
@Service
@Transactional(rollbackFor=Exception.class)
public class ProjectServiceImpl implements ProjectService {
	
	/**
	 * log4j实例对象.
	 */
	private static Logger logger = LogManager.getLogger(ProjectServiceImpl.class);
	
	
    @Autowired
    private JwtTokenUtils jwtTokenUtils;
    
    @Autowired
    private ProjectDao dao;
    
    @Autowired
    private UserDao userDao;

	/* (non-Javadoc)
	 * @see net.mw.teamwork.service.ProjectService#getList()
	 */
	@Override
	public ResultMessage getList(PageRequest page,  UserPO user) {
		logger.trace("进入getList方法");
    	ResultMessage rs = new ResultMessage();
		try {
			if(!ObjectUtils.allNotNull(user)){
				throw new IllegalArgumentException("登录用户不得为空！");
			}
			Map<String,Object> data = new HashMap<String,Object>();
			PageHelper.startPage(page.getPageNumber(), page.getPageSize());
			List<ProjectPO> projects = user.getType() == 1?dao.getProjectList():dao.getProjectByUserId(user.getId());
			List<ProjectVO> vos = new ArrayList<>();
			projects.forEach((item)->{
				item.setUserName(userDao.getUserById(item.getUserId()).getName());
				ProjectVO vo = new ProjectVO();
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
	 * @see net.mw.teamwork.service.ProjectService#getById(net.mw.teamwork.pojo.po.ProjectPO)
	 */
	@Override
	public ResultMessage getById(Long id) {
		logger.trace("进入getById方法");
    	ResultMessage rs = new ResultMessage();
		try {
			Map<String,Object> data = new HashMap<String,Object>();
			ProjectPO resPo = dao.getProjectById(id);
			resPo.setUserName(userDao.getUserById(resPo.getUserId()).getName());
			ProjectVO resVo = new ProjectVO();
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
	 * @see net.mw.teamwork.service.ProjectService#add(net.mw.teamwork.pojo.po.ProjectPO)
	 */
	@Override
	public ResultMessage add(ProjectPO po ,UserPO user) {
		logger.trace("进入add方法");
    	ResultMessage rs = new ResultMessage();
		try {
			if(!ObjectUtils.allNotNull(user)){
				throw new IllegalArgumentException("登录用户不得为空！");
			}
			String code = null;
			do{
				code = RandomStringUtils.randomAlphanumeric(6);
			}while (dao.ContinerCode(code) > 0);
			po.setCode(code);
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
	 * @see net.mw.teamwork.service.ProjectService#update(net.mw.teamwork.pojo.po.ProjectPO)
	 */
	@Override
	public ResultMessage update(ProjectPO po, UserPO user) {
		logger.trace("进入update方法");
    	ResultMessage rs = new ResultMessage();
		try {
			if(!ObjectUtils.allNotNull(user)){
				throw new IllegalArgumentException("登录用户不得为空！");
			}
			ProjectPO lastPo =dao.getProjectById(po.getId());
			lastPo.setName(po.getName());
			lastPo.setType(po.getType());
			lastPo.setDescript(po.getDescript());
			lastPo.setState(po.getState());
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
	 * @see net.mw.teamwork.service.ProjectService#del(net.mw.teamwork.pojo.po.ProjectPO)
	 */
	@Override
	public ResultMessage del(ProjectPO po) {
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

	/* (non-Javadoc)
	 * @see net.mw.teamwork.service.ProjectService#getMyProject()
	 */
	@Override
	public ResultMessage getMyProject(UserPO user) {
		logger.trace("进入getMyProject方法");
    	ResultMessage rs = new ResultMessage();
		try {
			if(!ObjectUtils.allNotNull(user)){
				throw new IllegalArgumentException("登录用户不得为空！");
			}
			List<ProjectPO> resPos = dao.getProjectByUserId(user.getId());
			List<ProjectVO> resVos = new ArrayList<>();
			resPos.forEach(item->{
				ProjectVO vo = new ProjectVO();
				item.setUserName(userDao.getUserById(item.getUserId()).getName());
				vo.poToVo(item);
				resVos.add(vo);
			});
			Map<String, Object> data = new HashMap<>();
			data.put("data", resVos);
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
		logger.trace("退出getMyProject方法");
		return rs;
	}

	/* (non-Javadoc)
	 * @see net.mw.teamwork.service.ProjectService#join(java.lang.String)
	 */
	@Override
	public ResultMessage join(String code, UserPO user) {
		logger.trace("进入join方法");
    	ResultMessage rs = new ResultMessage();
		try {
			if(!ObjectUtils.allNotNull(user)){
				throw new IllegalArgumentException("登录用户不得为空！");
			}
			ProjectPO projectPo = dao.getProjectByCode(code);
			if(dao.joinProject(projectPo.getId(), user.getId()) > 0 ) {
				rs.setCode(1L);
				rs.setMsg("加入成功!");
			}else {
				rs.setCode(1L);
				rs.setMsg("加入失败!");
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			rs.setMsg("参数不正确");
			rs.setCode(2L);
		} catch (Exception e) {
			e.printStackTrace();
			rs.setMsg("加入失败");
			rs.setCode(0L);
		}
		logger.trace("退出join方法");
		return rs;
	}

}
