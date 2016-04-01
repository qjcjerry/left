package com.left.jc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by chenkaihua on 15-12-15.
 */
@Controller
@RequestMapping("")
public class HomeController {


    @RequestMapping("index")
    public ModelAndView home(){
        return new ModelAndView("index");
    }

    @RequestMapping(value="/*.html")
    public void htmlView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // ...
//      request.getRequestDispatcher("index.html").forward(request, response);
        response.sendRedirect(request.getContextPath()+"/html"+request.getServletPath());
    }
}
