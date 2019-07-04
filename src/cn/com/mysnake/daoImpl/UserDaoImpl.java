package cn.com.mysnake.daoImpl;

import cn.com.mysnake.dao.BaseDao;
import cn.com.mysnake.dao.DaoHepler;
import cn.com.mysnake.dao.UserDao;
import cn.com.mysnake.model.ScoreAndUser;
import cn.com.mysnake.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {

    Connection conn = DaoHepler.getConn();
    private PreparedStatement statement;

    @Override
    public User queryByUsername(String username) {

       String  sql = "select id,username,userpassword from user where username= '"+username+"'";
        User user = new User();
        int col=0;
        ResultSet resultSet=null;
        try {

           statement = conn.prepareStatement(sql);

            resultSet = statement.executeQuery();


        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            while (resultSet.next()) {

                user.setId(resultSet.getInt(1));
                user.setUserName(resultSet.getString(2));
                user.setUserpassword(resultSet.getString(3));
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return user;
    }


    @Override
    public List<User> selectAll() {
        String  sql = "select * from user";
        User user = new User();
        int col=0;
        ResultSet resultSet=null;
        try {

            statement = conn.prepareStatement(sql);

            resultSet = statement.executeQuery();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        List<User> users=new ArrayList<User>();
        try {
            while (resultSet.next()){
                User user1=new User();
                user1.setId(resultSet.getInt(1));
                user1.setUserName(resultSet.getString(2));
                user1.setUserpassword(resultSet.getString(3));
                users.add(user1);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return users;
    }

    @Override
    public List<ScoreAndUser> selectScoreAndUserList() {

        String  sql = "select user.username,score.score from user inner join score on user.id=score.userid order by score.score desc ";
        ScoreAndUser scoreAndUser = new ScoreAndUser();
        int col=0;
        ResultSet resultSet=null;
        try {

            statement = conn.prepareStatement(sql);

            resultSet = statement.executeQuery();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        List<ScoreAndUser> scoreAndUsers=new ArrayList<ScoreAndUser>();
        try {
            while (resultSet.next()){
                ScoreAndUser scoreAndUser1=new ScoreAndUser();

                scoreAndUser1.setUsername(resultSet.getString(1));
                scoreAndUser1.setScore(resultSet.getString(2));
                scoreAndUsers.add(scoreAndUser1);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return scoreAndUsers;
    }
}
