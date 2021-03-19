package net.mw.system.dao;

import net.mw.system.pojo.po.ActionPowerPO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ActionPowerDao {
    @Select("select * from actionpower")
    public List<ActionPowerPO> getList();

    @Select("select * from actionpower where id = #{id}")
    public ActionPowerPO getActionPowerById(Long id);

    @Select("select * from actionpower where name = #{name}")
    public List<ActionPowerPO> getActionPowerByName(String name);

    @Insert("insert into actionpower(name, perm ) value(#{actionName}, #{perm})")
    @SelectKey(statement = "select last_insert_id()", keyProperty = "id", before = false, resultType = Long.class)
    public Integer save(ActionPowerPO actionPowerPo);

}
