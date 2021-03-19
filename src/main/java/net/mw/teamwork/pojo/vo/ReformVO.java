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
public class ReformVO extends Base  {

	/**
	 * 主键
	 */
	private String id;

	/**
	 * 动态内容
	 *
	 */
	private String content;

	/**
	 * 项目名称
	 *
	 */
	private String projectName;
	/**
	 * 项目ID
	 */
	private String projectId;
	
	/**
	 * 任务名称
	 *
	 */
	private String taskName;
	/**
	 * 任务ID
	 */
	private String taskId;
	/**
	 * 执行人名称
	 *
	 */
	private String userName;
	/**
	 * 执行人ID
	 */
	private String userId;
	
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
