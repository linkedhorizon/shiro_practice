package org.lyg.controller;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author :lyg
 * @time :2018/8/1 0001
 */
@Controller
public class AppController {

    // 登录提交地址，和配置中的loginurl一致
    @RequestMapping("/login")
    public String login(HttpServletRequest request){
        // 如果登录失败，从request中获取认证异常信息，shiroLoginFailur就是shiro异常类的全限定名
        String exceptionClassName = (String)request.getAttribute("shiroLoginFailure");
        if(exceptionClassName != null){
            if(UnknownAccountException.class.getName().equals(exceptionClassName)){
                // 最终会抛给异常处理器
                //throw new CustomException("账号不存在");
                return "refuse";
            }else if(IncorrectCredentialsException.class.getName().equals(exceptionClassName)){
                //throw new CustomException("用户名/密码错误");
                return "refuse";
            }else{
                //throw new Exception();
                return "refuse";
            }
        }
        // 此方法不处理登录成功（认证成功），shiro认证成功会自动跳转到上一个请求路径
        // 登录失败还到login页面
        return "index";
    }

    @RequestMapping("/successurl")
    @ResponseBody
    public String success(){
        return "登录成功";
    }

    @RequestMapping("/unauthorizedurl")
    @ResponseBody
    public String unauthorized(){
        return "无权限操作";
    }


    @RequestMapping("/content")
    @ResponseBody
    @RequiresPermissions("user:update:*")
    public String content(){
        return "内容页面";
    }

    @RequestMapping("/mail")
    @ResponseBody
    @RequiresRoles("元帅")
    public String mail(){
        return "有埋伏";
    }

    @RequestMapping("/logout")
    public String logout(){
        return "index";
    }
}
