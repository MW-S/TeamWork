/**
 * 
 */
package net.mw.teamwork.pojo.po;

import lombok.Data;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

/**
 * @Description 用户实体
 * @author W_Messi
 * @CreateTime 2020/3/2 12:02
 *
 */
@Data
public class ReformPO implements Serializable  {

	/**
	 * 主键
	 */
	private Long id;

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
	private Long projectId;
	
	/**
	 * 任务名称
	 *
	 */
	private String taskName;
	/**
	 * 任务ID
	 */
	private Long taskId;
	/**
	 * 执行人名称
	 *
	 */
	private String userName;
	/**
	 * 执行人ID
	 */
	private Long userId;
	
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
