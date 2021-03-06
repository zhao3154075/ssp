package com.es.ssp.configuration;

import com.es.ssp.model.LoginUser;
import com.es.ssp.service.LoginUserManager;
import com.es.ssp.service.RolePermissionManager;
import com.es.ssp.service.UserRoleManager;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;



/**
 * 获取用户的角色和权限信息
 * Created by bamboo on 2017/5/10.
 */
public class ShiroRealm extends AuthorizingRealm {

    private Logger logger = LoggerFactory.getLogger(ShiroRealm.class);

    @Autowired
    private LoginUserManager loginUserManager;
    @Autowired
    private UserRoleManager userRoleManager;
    @Autowired
    private RolePermissionManager rolePermissionManager;

    /**
     * 登录认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        logger.info("验证当前Subject时获取到token为：" + token.toString());
        //查出是否有此用户
        LoginUser hasUser = loginUserManager.getByUserName(token.getUsername());
        if (hasUser != null) {
//            Session session = SecurityUtils.getSubject().getSession();
//            session.setAttribute("user", hasUser);//成功则放入session
            // 若存在，将此用户存放到登录认证info中，无需自己做密码对比，Shiro会为我们进行密码对比校验
            return new SimpleAuthenticationInfo(hasUser, hasUser.getPsw(), getName());
        }
        return null;
    }

    /**
     * 权限认证
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info("##################执行Shiro权限认证##################");
        //获取当前登录输入的用户名，等价于(String) principalCollection.fromRealm(getName()).iterator().next();
//        String loginName = (String) super.getAvailablePrincipal(principalCollection);
        LoginUser user = (LoginUser) principalCollection.getPrimaryPrincipal();
//        //到数据库查是否有此对象
//        User user = null;// 实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
//        user = userMapper.findByName(loginName);
        if (user != null) {
            //权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            //用户的角色集合
            info.addRoles(user.getRolesName());
            System.out.println(user.getRolesName());
            //用户的权限集合
            info.addStringPermissions(user.getPermissionsName());
            System.out.println(user.getPermissionsName());

            return info;
        }
        // 返回null的话，就会导致任何用户访问被拦截的请求时，都会自动跳转到unauthorizedUrl指定的地址
        return null;
    }


}
