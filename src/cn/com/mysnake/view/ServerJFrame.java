package cn.com.mysnake.view;

import cn.com.mysnake.dao.UserDao;
import cn.com.mysnake.daoImpl.UserDaoImpl;
import cn.com.mysnake.model.ScoreAndUser;
import cn.com.mysnake.model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * 服务器数据监控
 *
 * 用户列表
 * 玩家排名
 * 服务器状态数据
 */
public class ServerJFrame extends JFrame {

    private UserDao userDao=new UserDaoImpl();
    private JLabel titleLable;
    private JLabel userTitleLable;
    private JLabel userScoretiTleLable;
    private JLabel userStatetitleLable;
    private JLabel userlist;
    private JLabel userScoreList;
    private JLabel serverstateList;

    public ServerJFrame(){
        super("服务器监控数据");
        this.setSize(830,420);
        this.setLayout(new BorderLayout());
        titleLable=new JLabel("服务器监控数据",JLabel.CENTER);
        userTitleLable=new JLabel("用户列表",JLabel.CENTER);
        userScoretiTleLable=new JLabel("玩家排名",JLabel.CENTER);
        userStatetitleLable=new JLabel("服务器状态",JLabel.CENTER);
        getContentPane().add("North",titleLable);

        paint();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);

//        serverstateList



    }

    public void reflash(){

    }



    public void paint(){
        JPanel tempJpanel=new JPanel(new GridLayout(1,3,10,0));


        JPanel leftPanel=new JPanel(new GridLayout(2,1,10,0));
        leftPanel.setSize(100,100);
        leftPanel.add(userTitleLable);

        JLabel userlistdata=new JLabel("",JLabel.CENTER);

        userlistdata.setPreferredSize(new Dimension(100,100));

        List<User> users=userDao.selectAll();
        StringBuilder stringBuilder=new StringBuilder();
        String   userdatastrMsgstart = "<html><body>";
        String   userdatastrMsgend="</body></html>";
        stringBuilder.append("用户id"+"&emsp;"+"用户名"+"<br>");
        for(int i=0;i<users.size();i++){
            stringBuilder.append(users.get(i).getId()+"&emsp;&emsp;"+users.get(i).getUserName()+"<br>");
        }

        userlistdata.setText(userdatastrMsgstart+stringBuilder.toString()+userdatastrMsgend);
        leftPanel.add(userlistdata);


        tempJpanel.add(leftPanel);

        JPanel centerPanel=new JPanel(new GridLayout(2,1,10,0));
        centerPanel.add(userScoretiTleLable);

        JLabel userscorelistdata=new JLabel("",JLabel.CENTER);

        userlistdata.setPreferredSize(new Dimension(100,100));
        List<ScoreAndUser> userscore=userDao.selectScoreAndUserList();
        StringBuilder userscorestringBuilder=new StringBuilder();
        String   userscorestrMsgstart = "<html><body>";
        String   userscorestrMsgend="</body></html>";
        userscorestringBuilder.append("排名"+"&emsp;"+"用户名"+"&emsp;"+"得分"+"<br>");

        for(int i=0;i<userscore.size();i++){
            userscorestringBuilder.append((i+1)+"&emsp;&emsp;"+userscore.get(i).getUsername()+"&emsp;&emsp;"+userscore.get(i).getScore()+"<br>");
        }

        userscorelistdata.setText(userscorestrMsgstart+userscorestringBuilder.toString()+userscorestrMsgend);
        centerPanel.add(userscorelistdata);
        tempJpanel.add(centerPanel);


        JPanel rightPanel=new JPanel(new GridLayout(2,1,10,0));
        rightPanel.add(userStatetitleLable);

        JLabel userStatelistdata=new JLabel("",JLabel.CENTER);

        userlistdata.setPreferredSize(new Dimension(100,100));
        Vector<String> userState=this.getServerData();
        StringBuilder userStatestringBuilder=new StringBuilder();
        String   userStatestrMsgstart = "<html><body>";
        String   userStatestrMsgend="</body></html>";
        for(int i=0;i<userState.size();i++){
            userStatestringBuilder.append("&emsp;&emsp;"+userState.get(i)+"<br><br>");
        }
        userStatelistdata.setText(userStatestrMsgstart+userStatestringBuilder.toString()+userStatestrMsgend);
        rightPanel.add(userStatelistdata);

        tempJpanel.add(rightPanel);

        this.add("South",tempJpanel);


        try {
            Thread.sleep(5000);//在此处睡眠一会，要不运动太快
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public StringBuilder getcmd(String cmdstr,boolean iscol,String option){

        String line = null;
        StringBuilder sb = new StringBuilder();
        Runtime runtime = Runtime.getRuntime();
        try {
            Process process = runtime.exec(cmdstr);
            BufferedReader bufferedReader = new BufferedReader
                    (new InputStreamReader(process.getInputStream()));


            while ((line = bufferedReader.readLine()) != null) {
                if(iscol){
                    if(line.contains(option)){
                        sb.append(line + "\n");
                    }

                }else{
                    sb.append(line + " ");
                }


            }

        return sb;


        } catch (IOException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    return sb;
    }

    public Vector<String> getServerData(){

        Vector<String> strings=new Vector<String>();


        strings.add(this.getcmd("systeminfo",true,"产品 ID").toString());

        strings.add(this.getcmd("wmic cpu get NumberOfLogicalProcessors",false,"").toString());

        strings.add(this.getcmd("systeminfo",true,"物理内存总量:").toString());

        strings.add(this.getcmd("systeminfo",true,"可用的物理内存:").toString());
        strings.add(this.getcmd("systeminfo",true,"OS 名称:").toString());

        return strings;

    }




    public static void main(String args[]){
//        UserDao userDao=new UserDaoImpl();
//        List<User> users=userDao.selectAll();
//        List<ScoreAndUser> scoreAndUsers=userDao.selectScoreAndUserList();
        ServerJFrame serverJFrame=new ServerJFrame();


    }

}
