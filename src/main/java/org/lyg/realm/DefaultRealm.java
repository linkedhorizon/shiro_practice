package org.lyg.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :lyg
 * @time :2018/8/2 0002
 */
public class DefaultRealm extends AuthorizingRealm{
    /**
     * 授权
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String principal = (String) principals.getPrimaryPrincipal();
        List<String> roles = new ArrayList<String>();
        roles.add("将军");
        roles.add("元帅");
        List<String> permissions = new ArrayList<String>();
        permissions.add("user:update:*");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRoles(roles);
        info.addStringPermissions(permissions);
        System.out.println("进入授权方法");
        return info;
    }

    /**
     * 认证
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String principal = (String)token.getPrincipal();
        //根据用户名principal查询用户登录名，获取密码,这里做简单处理
        if(!"root".equals(principal)){
            throw new UnknownAccountException();
        }
        // 从数据库中查到的盐值和密码
        String salt = "salt";
        // 123经过加密后的值
        String credentials = "8c4fb7bf681156b52fea93442c7dffc9";
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal,credentials, ByteSource.Util.bytes(salt),getName());
        return info;
    }

    @Override
    public void setName(String name) {
        super.setName("DefaultRealm");
    }
}
