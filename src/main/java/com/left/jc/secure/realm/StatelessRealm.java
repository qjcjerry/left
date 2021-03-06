package com.left.jc.secure.realm;

import com.left.jc.jopo.UserPrincipal;
import com.left.jc.model.User;
import com.left.jc.service.UserService;
import org.apache.log4j.Logger;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * 域（认证所需数据源）封装
 * 
 * @author Administrator
 *
 */

public class StatelessRealm extends AuthorizingRealm {
	private static Logger logger = Logger.getLogger(StatelessRealm.class);

	@Autowired
	UserService userService;
	/*@Autowired
	@Qualifier("userService")
	IUserService userService;
*/
	/**
	 * 获取认证信息
	 */
	@Override
	/*protected AuthenticationInfo doGetAuthenticationInfo(

	AuthenticationToken token) throws AuthenticationException {

		StatelessToken statelessToken = (StatelessToken) token;

		UserPrincipal userPrincipal = statelessToken.getUserPrincipal();

		String pass = statelessToken.getPassword();

		*//*User user = userService.getUserByPhonePass(userPrincipal.getUsername(),
				pass);

		if (user == null) {
			throw new AccountException();
		}*//*

		statelessToken.setUser(null);
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo();
		SimplePrincipalCollection simplePrincipalCollection = new SimplePrincipalCollection();
		simplePrincipalCollection.add(userPrincipal, getName());
		authenticationInfo.setPrincipals(simplePrincipalCollection);
		authenticationInfo.setCredentials(pass);
		logger.info("认证成功!!! principalls:" + userPrincipal + " credentiasl:"
				+ pass);

		return authenticationInfo;

	}*/
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		User user = userService.getUserById(1);
		System.out.println("kello"+user.getName());
		if (user != null) {
			return new SimpleAuthenticationInfo(user.getName(), user.getPassword(), getName());
		} else {
			return null;
		}
	}

	@Override
	public boolean supports(AuthenticationToken token) {

		//boolean result = token instanceof StatelessToken;
		boolean result = token instanceof UsernamePasswordToken;
		return result;

	}

	/**
	 * 获取授权信息,这里会进行判断，如果是认证登录通过，则拥有user:*权限 如果是
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		UserPrincipal userPrincipal = (UserPrincipal) principals.getPrimaryPrincipal();
		UserPrincipal.PrincipType principType = userPrincipal.getPrincipType();
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		authorizationInfo.addRole(principType.getRoleName());
		switch (principType) {
		case USER:
			authorizationInfo.addStringPermission("user:*");
			authorizationInfo.addStringPermission("avatar:read");
			break;
		case ADMIN:
			authorizationInfo.addStringPermission("user:*");
			authorizationInfo.addStringPermission("avatar:*");
			authorizationInfo.addStringPermission("admin:*");
			break;
		}
		logger.info("authrizations: Roles:" + authorizationInfo.getRoles()
				+ " permesins" + authorizationInfo.getStringPermissions());

		return authorizationInfo;
	}

}
