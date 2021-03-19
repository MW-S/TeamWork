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
public class ProjectPO implements Serializable  {

	/**
	 * 主键
	 */
	private Long id;

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
	 * 唯一邀请码
	 * 
	 */
	private String code;
	
	/**
	 * 创建人名称
	 */
	private String userName;
	
	/**
	 * 创建人ID
	 */
	private Long userId;
	
	/**
	 * 项目状态
	 */
	private Integer state;
	
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
