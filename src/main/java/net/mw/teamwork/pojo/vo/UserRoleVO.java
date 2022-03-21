package net.mw.teamwork.pojo.vo;

import lombok.Data;
import net.mw.system.pojo.po.UserPO;
import net.mw.system.pojo.vo.UserVO;

import java.util.List;

/**
 * @Description 用户实体
 * @author W_Messi
 * @CreateTime 2020/3/2 12:02
 *
 */
@Data
public class UserRoleVO extends UserVO {
    private List<String> roles;
}
