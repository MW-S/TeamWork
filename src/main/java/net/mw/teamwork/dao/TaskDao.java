package net.mw.teamwork.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import net.mw.system.pojo.po.UserPO;
import net.mw.teamwork.pojo.po.TaskPO;

@Mapper
@Repository
public interface TaskDao {
	@Select("select id,avatar_url,gender,type,account as user_name,name,phone,create_gtm as gmt_create from user where id in "
			+ "(select user_id from project_user_map where project_id = #{projectId})")
	public List<UserPO> getUserListByProjectId(Long projectId);
	
    @Select("SELECT t.*,pt.project_id,p.name as project_name "
    		+ "FROM task AS t, project_task_map AS pt, project as p  where t.id = pt.task_id and p.id = pt.project_id ")
    public List<TaskPO>  getTaskList();

    @Select("SELECT t.*,pt.project_id,p.name as project_name "
    		+ " FROM task AS t, project_task_map AS pt, project as p where t.id = pt.task_id and p.id = pt.project_id and t.id = #{id}")
    public TaskPO getTaskById(Long id);

    @Select("select id from task order by id DESC LIMIT 1")
    public Long getLastId();
    
    @Select("SELECT t.*,pt.project_id,p.name as project_name "
    		+ "FROM task AS t, project_task_map AS pt, project as p "
    		+ "where t.id = pt.task_id and p.id = pt.project_id and t.user_id = #{userId}")
    public List<TaskPO> getTaskByUserId(Long userId);

    @Insert("insert into task(name, type, descript, user_id) " +
            "value(#{name}, #{type}, #{descript},  #{userId})")
    @SelectKey(statement = "select last_insert_id()", keyProperty = "id", before = false, resultType = Long.class)
    public Integer save(TaskPO po);

    @Insert("insert into project_task_map(project_id, task_id) " +
            "value(#{projectId}, #{taskId})")
//    @SelectKey(statement = "select last_insert_id()", keyProperty = "id", before = false, resultType = Long.class)
    public Integer flushSave(Long projectId, Long taskId);
    
    @Update("update task set name = #{name} ,type = #{type}, descript = #{descript}, state = #{state} where id = #{id}")
    public Integer update(TaskPO po);
    
    @Delete("delete from task where  id = #{id}")
    public Integer del(TaskPO po);
    
    @Update("update task set state = #{state} where id = #{id}")
    public Integer updateState(TaskPO po);
}
