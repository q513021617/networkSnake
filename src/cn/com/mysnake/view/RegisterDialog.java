package cn.com.mysnake.view;

import cn.com.mysnake.controller.UserController;
import cn.com.mysnake.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterDialog extends JFrame implements ActionListener {

    private JButton submitButton;
    private JButton returnButton;
    private JLabel usernameLable;
    private JLabel userpasswordLabel;
    private JLabel reuserpasswordLabel;
    private JTextField username;
    private JPasswordField userpassword;
    private JPasswordField reuserpassword;
    private JPanel registerPanl;
    private JLabel title;
    private UserController userController=new UserController();
    public RegisterDialog(String title){
        super(title);
        this.setSize(500,350);
        this.submitButton = new JButton("提交");
        this.returnButton=new JButton("返回");
        this.username=new JTextField(16);
        this.userpassword=new JPasswordField(16);
        this.reuserpassword=new JPasswordField(16);
        this.usernameLable=new JLabel("用户名:");
        this.userpasswordLabel=new JLabel("密码:");
        this.reuserpasswordLabel=new JLabel("确认密码:");
        this.title=new JLabel("用户注册",SwingConstants.CENTER);
        this.title.setSize(200,200);
        this.setLayout(new BorderLayout(20,30));
        this.add(this.title,BorderLayout.NORTH);

        JPanel tempJpanel=new JPanel();
        tempJpanel.setSize(250,50);
        this.add(tempJpanel,BorderLayout.EAST);
        JPanel tempJpanel1=new JPanel();
        tempJpanel1.setSize(250,50);
        this.add(tempJpanel1,BorderLayout.WEST);
        registerPanl=new JPanel();
        registerPanl.setLayout(new GridLayout(4,2,2,50));
        registerPanl.add(this.usernameLable);
        registerPanl.add(this.username);

        registerPanl.add(this.userpasswordLabel);
        registerPanl.add(this.userpassword);

        registerPanl.add(this.reuserpasswordLabel);
        registerPanl.add(this.reuserpassword);

        registerPanl.add(this.submitButton);
        registerPanl.add(this.returnButton);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.add(registerPanl);
        this.submitButton.addActionListener(this);
        this.returnButton.addActionListener(this);
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

        if(e.getActionCommand().equals("返回")){
            new LoginDialog("登录");
            this.setVisible(false);
        }

        if(e.getActionCommand().equals("提交")){
            if(!reuserpassword.getText().equals(userpassword.getText())){
                showInfo("两次输入密码不一致请检查!","提示信息",false);
                return;
            }

           boolean isregister= userController.register(this.username.getText(),this.userpassword.getText());
            if(!isregister){
                showInfo("注册失败!","提示信息",false);
                return;
            }
            this.setVisible(false);
            showInfo("注册成功!","提示信息",true);
            this.dispose();
            new LoginDialog("登录界面");
        }
    }

    public static void main(String args[]){

        RegisterDialog registerDialog=new RegisterDialog("登录界面");

    }

}
