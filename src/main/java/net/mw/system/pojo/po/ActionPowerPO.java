package net.mw.system.pojo.po;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description ActionPower接口实现
 * @Author W_Messi
 * @CrateTime 2020-03-05 16:03:34
 */
@Accessors(chain= true)
@Data
public class ActionPowerPO implements Serializable {
	/**
	 * 权限主键
	 * 
	 */
	private Long id;

	/**
	 * action名称
	 * 
	 */
	private String actionName;

	/**
	 * 对应权限
	 * 
	 */
	private String perm;

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
