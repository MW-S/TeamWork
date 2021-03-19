package net.mw.system.pojo.po;


import java.io.Serializable;
import java.util.Date;

/**
 * @Description 用户角色关系实体
 * @author Administrator
 *
 */
public class UserRoleMapPO implements Serializable {
	/**
	 * 主键
	 * 
	 */
	private Long id;

	/**
	 * 用户id
	 */
	private Long userId;

	/**
	 * 角色id
	 */
	private Long roleId;

	/**
	 *创建时间
	 */
	private Date gmtCreate;
	/**
	 *修改时间
	 */
	private Date gmtUpdate;
	/**
	 *是否删除
	 */
	private String isDeleted;
	/**
	 *是否冻结
	 */
	private String isLocked;

}
