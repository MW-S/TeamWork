/**
 * @Description SecurityConfig
 * @Author W_Messi
 * @CrateTime 2020-04-16 23:38:27
 * 
 */
package net.mw.system.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

import net.mw.system.config.jwt.JwtAccessDeniedHandler;
import net.mw.system.config.jwt.JwtAuthenticationEntryPoint;
import net.mw.system.config.jwt.JwtAuthenticationTokenFilter;
import net.mw.system.dao.ActionPowerDao;
import net.mw.system.dao.RoleDao;
import net.mw.system.dao.UserDao;
import net.mw.system.dao.UserRoleMapDao;
import net.mw.system.utils.Encrypt;
import net.mw.system.utils.JwtTokenUtils;

/**
 * @Description SecurityConfig接口实现
 * @Author W_Messi
 * @CrateTime 2020-04-16 23:38:27
 */
@EnableWebSecurity
public class SecurityConfig  extends WebSecurityConfigurerAdapter{
	/**
	 * 注入ActionPowerDao
	 */
	@Autowired
	ActionPowerDao actionPowerDao;
	/**
	 * 注入UserDao
	 */
	@Autowired
	UserDao userDao;
	
	/**
	 * 注入RoleDao
	 */
	@Autowired
	UserRoleMapDao userRoleMapDao;
	/**
	 * 注入RoleDao
	 */
	@Autowired
	RoleDao roleDao;
	
	
	//注入数据源
		@Autowired
		private DataSource dataSource;
		
		//根据用户名查询用户信息
		private final String getUserByUsername = "SELECT account as username, password as password, state as enabled "
													+ "FROM user "
													+ "WHERE account = ?";
		
		//根据用户名查询角色信息
		private final String getRoleByUsername = "SELECT u.account as username, r.name as authority "
												+ "FROM user u, userrolemap ur, role r "
												+ "WHERE u.id = ur.userid AND r.id = ur.roleid AND u.account = ?";

/*		private final String springboot = "http://localhost/springboot";
		private final String vue = "http://localhost/vue";*/


	private JwtAccessDeniedHandler jwtAccessDeniedHandler;
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private JwtTokenUtils jwtTokenUtils;

	public SecurityConfig(JwtAccessDeniedHandler jwtAccessDeniedHandler, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, JwtTokenUtils jwtTokenUtils) {
		this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
		this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
		this.jwtTokenUtils = jwtTokenUtils;

	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		//@formatter:off
		super.configure(web);
		web.httpFirewall(allowUrlEncodedSlashHttpFirewall());
	}

	/**
	 * 重写授权规则
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http               // 授权异常
				.exceptionHandling()
				.authenticationEntryPoint(jwtAuthenticationEntryPoint)
				.accessDeniedHandler(jwtAccessDeniedHandler)

				// 防止iframe 造成跨域
				.and()
				.headers()
				.frameOptions()
				.disable()

				// 不创建会话
				.and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)

				.and()
		//配置规则
		.authorizeRequests()
		.antMatchers("/.well-known/**").permitAll()  //设置所有人可以访问
		.antMatchers("/user/login").permitAll()  //设置所有人可以访问
		.antMatchers("/user/**").hasAnyAuthority("admin") //设置指定角色才能访问
		.antMatchers("/swagger-ui.html").permitAll() //设置指定角色才能访问
		.antMatchers("/swagger-resources/**").permitAll()
		.antMatchers("/swagger-ui.html/swagger-resources/**").permitAll()
		.antMatchers("/webjars/**").permitAll()
		.antMatchers("/v2/**").permitAll()
		.antMatchers("/csrf").permitAll()
		// 放行druid
		.antMatchers("/druid/**").permitAll()
		//允许匿名及登录用户访问
		.antMatchers("/auth/**", "/error/**").permitAll()
		.anyRequest().authenticated()
//		.and().formLogin().defaultSuccessUrl(springboot+"/user/loginSuccess").loginPage(vue).loginProcessingUrl(springboot+"/user/login")//配置登录、登录页面、登录请求URL
//		.usernameParameter("username").passwordParameter("password")//自定义登录参数
//		.and().logout().logoutSuccessUrl(vue) //配置登出以及登出成功页面
//		.and().rememberMe().rememberMeParameter("rememberMe")	//开启记住我功能以及自定义参数 ,默认保存两周
		;

/*		List<ActionPowerPO> actionList=actionPowerDao.findAll();
		for(ActionPowerPO po:actionList) {
			http.authorizeRequests().antMatchers("/"+po.getPerm()).hasAnyRole("student","teacher");
		}*/
		// 禁用缓存
		http.headers().cacheControl();

		// 添加JWT filter
		http.apply(new TokenConfigurer(jwtTokenUtils));
		//关闭跨域伪造
		http.csrf().disable();
	}
/*	@Bean
	UserDetailsService myUserDetailService() {
	   return new UserDetailsServiceImpl();
	}*/
	/**
	 * 重写认证规则
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		// 密码编码器-在Spring5的Security中都要求使用密码编码器，否则会发生异常
		Encrypt passwordEncoder = new Encrypt();
//		auth.userDetailsService(myUserDetailService()).passwordEncoder(passwordEncoder);
//		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		// 使用数据库签名服务
		auth.jdbcAuthentication()
				// 设置密码编码器
				.passwordEncoder(passwordEncoder)
				// 数据源
				.dataSource(dataSource)
				// 查询用户，自动判断密码是否一致
				.usersByUsernameQuery(getUserByUsername)
				// 赋予权限
				.authoritiesByUsernameQuery(getRoleByUsername);
/*		auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
		.withUser("root").password(new BCryptPasswordEncoder().encode("123456")).roles("student");*/
		/*List<UserPO> list = userDao.findAll();
		for(UserPO po:list) {
			userRoleMapDao.findByUserId(po.getId()).forEach(item ->{
				RolePO rolePo = roleDao.findById(item.getRoleId()).get();
				try {
					auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
					.withUser(po.getOpenId()).password(po.getPassword()).roles(rolePo.getRoleName());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
		}*/
		//数据库中读取
/*		auth.jdbcAuthentication().passwordEncoder(new BCryptPasswordEncoder()).dataSource()
		.withUser("root").password(new BCryptPasswordEncoder().encode("123456")).roles("student")
		.and().withUser("teacher").password(new BCryptPasswordEncoder().encode("123456")).roles("teacher");*/
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	public class TokenConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

		private final JwtTokenUtils jwtTokenUtils;

		public TokenConfigurer(JwtTokenUtils jwtTokenUtils){

			this.jwtTokenUtils = jwtTokenUtils;
		}

		@Override
		public void configure(HttpSecurity http) {
			JwtAuthenticationTokenFilter customFilter = new JwtAuthenticationTokenFilter(jwtTokenUtils);
			http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
		}
	}

	@Bean
	public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
		StrictHttpFirewall firewall = new StrictHttpFirewall();
		firewall.setAllowUrlEncodedSlash(true);
		return firewall;
	}
}