package com.left.jc.controller;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.left.jc.model.User;
import com.left.jc.service.UserService;
import com.left.jc.utils.URIUtils;
import com.left.jc.utils.AjaxHelper;

@RequestMapping("users-a")
@RestController
public class UserController_a {
    private Logger logger = LoggerFactory.getLogger(UserController_a.class);

    @Autowired
    UserService userService;


    @RequestMapping(value = "{id}",method = RequestMethod.GET)
    public ResponseEntity getUserBYId(@PathVariable int id){
        User user = userService.getUserById(id);
        if(user==null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(user);
    }

    @RequestMapping(value = "",method = RequestMethod.POST)
    public ResponseEntity addUser(@RequestBody User user)  {

        User countUser = new User();
        user.setName(user.getName());
        //如果存在，返回错误码
        if (userService.isExist(countUser)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        userService.addUser(user);
        return ResponseEntity.created(URIUtils.createURI("users/{id}",user.getId())).body(user);

    }


    @RequestMapping(value = "",method =RequestMethod.GET )
    public Object users(){
        List<User> users =userService.getUsers(null);
       // return new ResponseEntity(users, HttpStatus.OK);
        return AjaxHelper.sendData(users);
    }



    @RequestMapping(value = "{id}",method = RequestMethod.DELETE)
    public void deleteById(@PathVariable int id){
        userService.deleteById(id);
    }

    @RequestMapping
    public void update(@RequestBody User user){
        userService.update(user);
    }











}
