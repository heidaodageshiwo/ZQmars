package com.iekun.ef.shiro;

import com.iekun.ef.dao.RoleMapper;
import com.iekun.ef.dao.UeInfoMapper;
import com.iekun.ef.dao.UserMapper;
import com.iekun.ef.model.User;
import com.iekun.ef.service.PasswordHelper;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by feilong.cai on 2016/11/18.
 */

public class EfShiroRealm  extends AuthorizingRealm {

    private static final Logger logger = LoggerFactory.getLogger(EfShiroRealm.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UeInfoMapper ueInfoMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private PasswordHelper passwordHelper;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        String loginName = (String)super.getAvailablePrincipal(principalCollection);

        //到数据库查是否有此对象
        User user =  userMapper.findByLoginName( loginName );
        if(user!=null){

            //权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

            // info.setRoles(this.findAllRoles(loginName)); //// TODO: 2016/11/18

            return info;

        }

        return null;
    }

    /**
     * 登录认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo( AuthenticationToken authenticationToken)
            throws AuthenticationException {

        String username = (String)authenticationToken.getPrincipal();

        //查出是否有此用户
        User user = userMapper.findByLoginName(username);

        if(user == null) {
            throw new UnknownAccountException();//没找到帐号
        }

        if( user.getLocked() ==  Integer.valueOf(1) ) {
            throw new LockedAccountException(); //帐号锁定
        }

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user.getLoginName(), //用户名
                user.getPassword(), //密码
                ByteSource.Util.bytes(user.getSalt()==null?"":user.getSalt()),
                getName()  //realm name
        );
        return authenticationInfo;

    }

//    protected Set<String> findAllRoles(String userName){
//
//        Set<String> set = new HashSet<String>();
//
//        List<UserRoles> list = usersDao.findAllRoles(userName);
//
//        for(UserRoles userRoles : list){
//            if(userRoles.getResourceUrl() != null && !userRoles.getResourceUrl().equals("")){
//                set.add(userRoles.getResourceUrl());
//            }
//        }
//
//        return set;
//    }

    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }

    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }

}
