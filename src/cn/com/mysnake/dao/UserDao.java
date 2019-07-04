package cn.com.mysnake.dao;

import cn.com.mysnake.model.ScoreAndUser;
import cn.com.mysnake.model.User;

import java.util.List;

public interface UserDao extends BaseDao<User> {

    User queryByUsername(String username);
    List<User> selectAll();
    List<ScoreAndUser> selectScoreAndUserList();
}
