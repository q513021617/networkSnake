package test;

import cn.com.mysnake.dao.UserDao;
import cn.com.mysnake.daoImpl.UserDaoImpl;
import cn.com.mysnake.model.User;

public class TestDemo {

    public static void main(String args[]){
//        UserDao userDao=new UserDaoImpl();
//        User user=new User(1,"hello","123456");
//
//        userDao.add(user);

//        try {
//            userDao.delete(user);
//        }catch (Exception e){
//
//        }

//        User tempUser=userDao.select(user);
//        System.out.println(tempUser);

//        ScoreDao scoreDao=new ScoreDaoImpl();
//
//        score score=new score(1,1,30);
//        scoreDao.add(score);
//
//        score score1=scoreDao.select(score);
//        System.out.println(score1);

        UserDao userDao=new UserDaoImpl();
        User user=userDao.queryByUsername("hello");
        System.out.println(user);
    }
}
