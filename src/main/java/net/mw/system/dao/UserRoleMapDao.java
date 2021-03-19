/**
 * @Description UserRoleMapDao
 * @Author W_Messi
 * @CrateTime 2020-04-18 00:13:35
 * 
 */
package net.mw.system.dao;

import net.mw.system.pojo.po.RolePO;
import net.mw.system.pojo.po.UserRoleMapPO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description UserRoleMapDao接口实现
 * @Author W_Messi
 * @CrateTime 2020-04-18 00:13:35
 */
@Mapper
@Repository
public interface UserRoleMapDao  {

	@Select("select * from userrolemap")
	public List<UserRoleMapPO> getUserRoleMapList();

	@Select("select * from userrolemap where id = #{id}")
	public UserRoleMapPO getUserRoleMapById(Long id);

	@Select("select * from userrolemap where userid = #{id}")
	public List<UserRoleMapPO> getUserRoleMapByUserId(Long id);

	@Select("select * from userrolemap where roleid = #{id}")
	public List<UserRoleMapPO> getUserRoleMapByRoleId(Long id);

	@Insert("insert into userrolemap(userid, roleid) value(#{userId}, #{roleId})")
	@SelectKey(statement = "select last_insert_id()", keyProperty = "id", before = false, resultType = Long.class)
	public Integer save(UserRoleMapPO po);
}
