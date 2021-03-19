/**
 * @Description RoleDao
 * @Author W_Messi
 * @CrateTime 2020-04-18 00:12:55
 * 
 */
package net.mw.system.dao;

import net.mw.system.pojo.po.ActionPowerPO;
import net.mw.system.pojo.po.RolePO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description RoleDao接口实现
 * @Author W_Messi
 * @CrateTime 2020-04-18 00:12:55
 */
@Mapper
@Repository
public interface RoleDao {

    @Select("select * from role")
    public List<RolePO> getRoleList();

    @Select("select * from role where id = #{id}")
    public RolePO getRoleById(Long id);

    @Select("select * from role where name = #{name}")
    public List<RolePO> getRoleByName(String name);
    
    @Select("select * from role where id in  ("
    		+ "select roleid from userrolemap where userid in"
    		+ "(select id from user where account = #{name})"
    		+ ")")
    public List<RolePO> getRoleByUserName(String name);

    @Insert("insert into role(name) value(#{roleName})")
    @SelectKey(statement = "select last_insert_id()", keyProperty = "id", before = false, resultType = Long.class)
    public Integer save(RolePO actionPowerPo);
}
