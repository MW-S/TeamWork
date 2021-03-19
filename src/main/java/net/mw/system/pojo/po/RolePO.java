/**
 * 
 */
package net.mw.system.pojo.po;


import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * @Description
 * @author W_Messi
 * @CrateTime 2020/3/2 12:22
 */
@Data
public class RolePO implements Serializable {
	/**
	 * 主键
	 * 
	 */
	private Long id;

	/**
	 * 角色名
	 * 
	 */
	private String name;
	
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
