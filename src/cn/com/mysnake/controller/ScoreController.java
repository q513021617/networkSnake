package cn.com.mysnake.controller;

import cn.com.mysnake.dao.ScoreDao;
import cn.com.mysnake.dao.UserDao;
import cn.com.mysnake.daoImpl.ScoreDaoImpl;
import cn.com.mysnake.daoImpl.UserDaoImpl;
import cn.com.mysnake.model.score;

public class ScoreController {
    private ScoreDao scoreDao=new ScoreDaoImpl();
    private UserDao userDao=new UserDaoImpl();
    public boolean eatFood(String username,int score){
        int userid=userDao.queryByUsername(username).getId();
        cn.com.mysnake.model.score score1=new score(0,userid,score);
        return scoreDao.add(score1)>0;
    }
}
