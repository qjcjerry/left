package com.left.jc.controller;

import com.left.jc.jopo.UserPrincipal;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by Jerry on 16/4/6.
 */
@Controller
@RequestMapping("")
public class FormFilterLoginController extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(FormFilterLoginController.class);

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(HttpServletRequest request){
        return "login";

    }
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String isLogin(HttpServletRequest request) {
       // UserPrincipal userPrincipal = request.getUserPrincipal();
        String loginName = request.getParameter("username");
        String loginPassword = request.getParameter("password");
        LOGGER.info("loginName:" + loginName + ";loginPassword:" + loginPassword);

        HttpSession session = request.getSession(true);
        String errorMessage = "";

        Subject user = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken(loginName, loginPassword);
        token.setRememberMe(true);

        try {
            user.login(token);
            String userID = (String) user.getPrincipal();
            LOGGER.info("User [" + userID + "] logged in successfully.");
            session.setAttribute("USERNAME", userID);
            return "success";
        } catch (UnknownAccountException uae) {
            errorMessage = "用户认证失败：" + "username wasn't in the system.";
            LOGGER.info(errorMessage);
        } catch (IncorrectCredentialsException ice) {
            errorMessage = "用户认证失败：" + "password didn't match.";
            LOGGER.info(errorMessage);
        } catch (LockedAccountException lae) {
            errorMessage = "用户认证失败：" + "account for that username is locked - can't login.";
            LOGGER.info(errorMessage);
        } catch (AuthenticationException e) {
            errorMessage = "登录失败错误信息：" + e;
            LOGGER.error(errorMessage);
            e.printStackTrace();
            token.clear();
        }
        session.setAttribute("ErrorMessage", errorMessage);
        return "error";
    }
}
