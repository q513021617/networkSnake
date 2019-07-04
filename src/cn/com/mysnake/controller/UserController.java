package cn.com.mysnake.controller;

import cn.com.mysnake.dao.UserDao;
import cn.com.mysnake.daoImpl.UserDaoImpl;
import cn.com.mysnake.model.User;

import javax.swing.*;
import java.util.List;

public class UserController {

        private UserDao userDao=new UserDaoImpl();

    public boolean login(String username,String password){


        User user=userDao.queryByUsername(username);
        if(user==null || user.getId()==null){
            JOptionPane.showMessageDialog(null,"用户名或密码错误!","提示信息:",JOptionPane.ERROR_MESSAGE);//提示框
            return false;
        }
        return user.getUserpassword().equals(password);

    }

    public boolean register(String username,String password){

        User user=new User(0,username,password);


       return userDao.add(user)>0;
    }

    public boolean updateUser(String username,User newuser){
       User user= userDao.queryByUsername(username);
       user.setUserName(newuser.getUserName());
        user.setUserpassword(newuser.getUserpassword());
        return userDao.update(user);
    }

    public List<User> selectAll(){


        return null;
    }

}
