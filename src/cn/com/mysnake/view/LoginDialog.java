package cn.com.mysnake.view;

import cn.com.mysnake.controller.UserController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class LoginDialog extends JFrame implements ActionListener {

    private JButton loginButton;
    private JButton registerButton;
    private JLabel usernameLable;
    private JLabel userpasswordLabel;
    private JTextField username;
    private JPasswordField userpassword;
    private JPanel loginPanl;
    private JLabel title;
    public static final ImageIcon backImg=new ImageIcon("F:\\code\\java\\snake2\\src\\images\\playground.png");
    UserController userController=new UserController();
    private Properties pro;//配置文件
    public LoginDialog(String title) {
        super(title);
        this.setSize(500,300);

        this.loginButton = new JButton("登录");
        this.registerButton=new JButton("注册");
        this.username=new JTextField(16);
        this.userpassword=new JPasswordField(16);
        this.usernameLable=new JLabel("用户名:");
        this.userpasswordLabel=new JLabel("密码:");
        this.title=new JLabel("贪吃蛇游戏登录",SwingConstants.CENTER);
        this.title.setSize(200,200);
        this.setLayout(new BorderLayout(20,50));
        this.add(this.title,BorderLayout.NORTH);

        JPanel tempJpanel=new JPanel();

        tempJpanel.setSize(250,50);
        this.add(tempJpanel,BorderLayout.EAST);
        JPanel tempJpanel1=new JPanel();

        tempJpanel1.setSize(250,50);
        this.add(tempJpanel1,BorderLayout.WEST);
        loginPanl=new JPanel();

        loginPanl.setLayout(new GridLayout(3,2,2,50));
        loginPanl.add(this.usernameLable);
        loginPanl.add(this.username);

        loginPanl.add(this.userpasswordLabel);
        loginPanl.add(this.userpassword);

        loginPanl.add(this.loginButton);
        loginPanl.add(this.registerButton);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.add(loginPanl);
        loginButton.addActionListener(this);
        registerButton.addActionListener(this);
        this.setVisible(true);
    }




    public void showInfo(String message,String title,Boolean bool){

        if(!bool){
            JOptionPane.showMessageDialog(null,message,title,JOptionPane.ERROR_MESSAGE);//提示框
            return;
        }
        JOptionPane.showMessageDialog(null,message,title,JOptionPane.INFORMATION_MESSAGE);//提示框
    }

    @Override
    public void actionPerformed(ActionEvent e) {


        if(e.getActionCommand().equals("登录")){
            System.out.println("登录");
          boolean islogin = userController.login(this.username.getText(),this.userpassword.getText());

          if(!islogin){

              showInfo("验证失败!","提示信息:",islogin);
              return;
          }
            this.setVisible(false);
            pro = new Properties();
            try {
                File file = new File(".\\snake.config");


                pro.setProperty("name",this.username.getText());
                pro.store(new FileWriter(file), "snake");

            } catch (IOException ex) {
                System.out.println("文件读取异常");
            }
            showInfo("验证成功!","提示信息:",islogin);

            SnakeMainJFrame sankeMain = new SnakeMainJFrame();
            sankeMain.setLocationRelativeTo(null);
            sankeMain.setVisible(true);
        }

        if(e.getActionCommand().equals("注册")){
            System.out.println("注册");
            new RegisterDialog("注册窗口");
            this.setVisible(false);
        }

    }



}
