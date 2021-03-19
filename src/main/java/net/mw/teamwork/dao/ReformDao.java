package net.mw.teamwork.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import net.mw.teamwork.pojo.po.ProjectPO;
import net.mw.teamwork.pojo.po.ReformPO;

@Mapper
@Repository
public interface ReformDao {
    @Select(" select r.*,p.name as project_name, u.name as user_name,t.name as task_name "
    		+ " from reform as r,project as p,user as u,task as t "
    		+ " where r.project_id = p.id and r.task_id = t.id and r.user_id = u.id ")
    public List<ReformPO>  getReformList();

    @Select("select r.*,p.name as project_name, u.name as user_name,t.name as task_name"
    		+ " from reform as r,project as p,user as u,task as t "
    		+ " where r.project_id = p.id and r.task_id = t.id and r.user_id = u.id and r.id = #{id} ")
    public ReformPO getReformById(Long id);

    @Select("select id from reform order by id DESC LIMIT 1")
    public Long getLastId();
    
    @Select("select r.*,p.name as project_name, u.name as user_name,t.name as task_name"
    		+ " from reform as r,project as p,user as u,task as t "
    		+ " where r.project_id = p.id and r.task_id = t.id and r.user_id = u.id and  r.user_id = #{id} ")
    public List<ReformPO> getReformByUserId(Long id);

    @Select("select r.*,p.name as project_name, u.name as user_name,t.name as task_name"
    		+ " from reform as r,project as p,user as u,task as t "
    		+ " where r.project_id = p.id and r.task_id = t.id and r.user_id = u.id and  r.task_id = #{id} ")
    public List<ReformPO> getReformByTaskId(Long id);

    @Select("select r.*,p.name as project_name, u.name as user_name,t.name as task_name "
    		+ " from reform as r,project as p,user as u,task as t "
    		+ " where r.project_id = p.id and r.task_id = t.id and r.user_id = u.id and  r.project_id = #{id} ")
    public List<ReformPO> getReformByPorjectId(Long id);
    
    @Insert("insert into reform(content, project_id, task_id, user_id) " +
            "value(#{content}, #{projectId}, #{taskId}, #{userId})")
    @SelectKey(statement = "select last_insert_id()", keyProperty = "id", before = false, resultType = Long.class)
    public Integer save(ReformPO po);
    
    @Update("update reform set content = #{content} where id = #{id}")
    public Integer update(ReformPO po);
    
    @Delete("delete from reform where id = #{id}")
    public Integer del(ReformPO po);

}
