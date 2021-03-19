package net.mw.teamwork.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.github.pagehelper.PageHelper;

import net.mw.teamwork.pojo.po.ProjectPO;

@Mapper
@Repository
public interface ProjectDao {
	
    @Select("select * from project ")
    public List<ProjectPO>  getProjectList();

    @Select("select * from project where id = #{id}")
    public ProjectPO getProjectById(Long id);
    
    @Select("select * from project where id in "
    		+ "(select project_id from project_task_map where task_id  =  #{id} )")
    public ProjectPO getProjectByTaskId(Long id);
    

    @Select("select id from project order by id DESC LIMIT 1")
    public Long getLastId();
    
    @Select("select * from project where user_id = #{id}")
    public List<ProjectPO> getProjectByUserId(Long id);
    
    
    @Select("select count(code) from project where code = #{code}")
    public Integer ContinerCode(String code);
    
    @Select("select * from project where code = #{code}")
    public ProjectPO getProjectByCode(String code);

    @Insert("insert into project(name, type, descript, code, user_id) " +
            "value(#{name}, #{type}, #{descript}, #{code}, #{userId})")
    @SelectKey(statement = "select last_insert_id()", keyProperty = "id", before = false, resultType = Long.class)
    public Integer save(ProjectPO po);

    @Update("update project set name = #{name} ,type = #{type}, descript = #{descript}, state = #{state} where id = #{id}")
    public Integer update(ProjectPO po);
    
    @Insert("insert into project_user_map(project_id, user_id) "
    		+ "value(#{projectId}, #{userId})" )
    public Integer joinProject(Long projectId, Long userId);
    
    @Delete("delete from project where  id = #{id}")
    public Integer del(ProjectPO po);
}
