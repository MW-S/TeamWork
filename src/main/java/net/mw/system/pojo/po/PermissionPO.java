/**
 * 
 */
package net.mw.system.pojo.po;


import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * @Description 权限表
 * @Author W_Messi
 * @CreateTime 2020/3/2 12:34
 * 
 */
@Data
public class PermissionPO implements Serializable {
	/**
	 * 权限主键
	 * 
	 */
	private Long id;

	/**
	 * 权限名称
	 * 
	 */
	private String name;

	/**
	 * 权限标识
	 * 
	 */
	private String perms;

	/**
	 * 父级权限
	 * 
	 */
	private Long parentId;

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
