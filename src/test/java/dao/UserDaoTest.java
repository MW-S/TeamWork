package dao;


import net.mw.TeamWorkApplication;
import net.mw.system.dao.ActionPowerDao;
import net.mw.system.pojo.po.ActionPowerPO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Description AreaDataDaoTest接口实现
 * @Author W_Messi
 * @CrateTime 2020-04-08 15:16:39
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= TeamWorkApplication.class)
public class UserDaoTest {

    @Autowired
    ActionPowerDao actionPowerDao;

    /**
     * log4j实例对象.
     */
    private static Logger logger = LogManager.getLogger(UserDaoTest.class);

    @Test
    public void addActionPowerTest(){
        logger.info("开始测试！");
        ActionPowerPO insertPo = new ActionPowerPO().setActionName("测试名").setPerm("权限名");
        actionPowerDao.save(insertPo);
        logger.info("结束测试！");
    }
}
