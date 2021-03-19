/**
 * 
 */
package net.mw.teamwork.pojo.vo;

import java.util.Date;

import lombok.Data;
import net.mw.system.pojo.base.Base;

/**
 * @Description 用户实体
 * @author W_Messi
 * @CreateTime 2020/3/2 12:02
 *
 */
@Data
public class TaskVO extends Base  {

	/**
	 * 主键
	 */
	private String id;

	/**
	 * 昵称
	 *
	 */
	private String name;

	/**
	 * 简述
	 * 
	 */
	private String descript;
	
	/**
	 * 类型
	 * 
	 */
	private String type;

	/**
	 * 创建人名称
	 */
	private String userName;
	
	/**
	 * 创建人ID
	 */
	private String userId;
	/**
	 * 项目名称
	 */
	private String projectName;
	
	/**
	 * 项目ID
	 */
	private String projectId;
	
	/**
	 * 项目状态
	 */
	private String state;
	
	/**
	 *创建时间
	 */
	private String gmtCreate;
	/**
	 *修改时间
	 */
	private String gmtUpdate;
	/**
	 *是否删除
	 */
	private String isDeleted;
	/**
	 *是否冻结
	 */
	private String isLocked;

}
