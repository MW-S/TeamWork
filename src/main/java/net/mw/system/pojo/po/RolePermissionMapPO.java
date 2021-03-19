/**
 * 
 */
package net.mw.system.pojo.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description 角色权限关系
 * @author Administrator
 *
 */
@Data
public class RolePermissionMapPO implements Serializable {

	/**
	 * 主键
	 * 
	 */
	private Long id;

	/**
	 * 角色ID
	 * 
	 */
	private Long roleId;

	/**
	 * 权限ID
	 * 
	 */
	private Long permissionId;
	
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
