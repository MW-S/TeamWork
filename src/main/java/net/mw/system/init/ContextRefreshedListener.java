package net.mw.system.init;

import net.mw.system.annotation.Perm;
import net.mw.system.annotation.Power;
import net.mw.system.dao.ActionPowerDao;
import net.mw.system.pojo.po.ActionPowerPO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @Description 注入action权限关系
 * @Author W_Messi
 * @CrateTime 2020-03-05 11:32:21
 */
@Component
@Transactional(rollbackFor = Exception.class)
public class ContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent> {
	/**
	 * log4j实例对象.
	 */
	private static Logger logger = LogManager.getLogger(ContextRefreshedListener.class.getName());

	/**
	 * PermissionDao注入
	 */
	@Autowired
	private ActionPowerDao actionPowerDao;
	private static ActionPowerDao actionPowerStaticDao;

	@PostConstruct
	public void beforeInit() {
		actionPowerStaticDao = actionPowerDao;
	}

	/**
	 * @Description ContextRefreshedListener接口实现
	 * @Author W_Messi
	 * @CrateTime 2020-03-05 11:32:21
	 */

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		logger.info("正在注入ActionPower数据!");

		if (event.getApplicationContext().getParent() == null) {
			//获取所有使用了注解RestController的bean
			Map<String, Object> beans = event.getApplicationContext().getBeansWithAnnotation(RestController.class);
			try {
				//遍历每一个bean
				for (Object bean : beans.values()) {
					
					String name=bean.getClass().getName();
					String className=name;
					//对获取回来的bean进行解析
					if(name.indexOf("$")!=-1) {
						className=name.substring(0,name.indexOf("$"));
					}					
					if (bean != null) {
						//通过bean路径获取准确的类
						Class<?> info = Class.forName(className);
						ActionPowerPO insertPo = new ActionPowerPO();
						Method[] methods = info.getDeclaredMethods();
						Power pow = null;
						for (Method method : methods) {
							// 获取当前方式的指定注释
							pow = method.getAnnotation(Power.class);
							// 判断当前方法是否存在该注释
							if (pow != null) {
								insertPo.setActionName(pow.name());
								Perm[] perms = pow.perms();
								for (Perm perm : perms) {
									insertPo.setPerm(perm.name());
//									ActionPowerPO po=new ActionPowerPO();
//									po.setIsDeleted("false");
//									po.setActionName(insertPo.getActionName());
//									Example<ActionPowerPO> example = Example.of(po);
//									Optional<ActionPowerPO> op =  actionPowerStaticDao.findOne(example);
									List<ActionPowerPO> queryPos = actionPowerStaticDao.getActionPowerByName(insertPo.getActionName());
									if (queryPos.isEmpty()) {
										actionPowerStaticDao.save(insertPo);
										System.out.println(insertPo.getActionName() + "权限注入成功!");
									} else {
										System.out.println(insertPo.getPerm() + "权限已存在!无需注入");
									}
								}
//								actionPowerStaticDao.flush();
							}
						}
					}
				}
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			logger.info("权限注入完毕!");
		}
	}
}