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
    private Properties pro;//�����ļ�
    public LoginDialog(String title) {
        super(title);
        this.setSize(500,300);

        this.loginButton = new JButton("��¼");
        this.registerButton=new JButton("ע��");
        this.username=new JTextField(16);
        this.userpassword=new JPasswordField(16);
        this.usernameLable=new JLabel("�û���:");
        this.userpasswordLabel=new JLabel("����:");
        this.title=new JLabel("̰������Ϸ��¼",SwingConstants.CENTER);
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
            JOptionPane.showMessageDialog(null,message,title,JOptionPane.ERROR_MESSAGE);//��ʾ��
            return;
        }
        JOptionPane.showMessageDialog(null,message,title,JOptionPane.INFORMATION_MESSAGE);//��ʾ��
    }

    @Override
    public void actionPerformed(ActionEvent e) {


        if(e.getActionCommand().equals("��¼")){
            System.out.println("��¼");
          boolean islogin = userController.login(this.username.getText(),this.userpassword.getText());

          if(!islogin){

              showInfo("��֤ʧ��!","��ʾ��Ϣ:",islogin);
              return;
          }
            this.setVisible(false);
            pro = new Properties();
            try {
                File file = new File(".\\snake.config");


                pro.setProperty("name",this.username.getText());
                pro.store(new FileWriter(file), "snake");

            } catch (IOException ex) {
                System.out.println("�ļ���ȡ�쳣");
            }
            showInfo("��֤�ɹ�!","��ʾ��Ϣ:",islogin);

            SnakeMainJFrame sankeMain = new SnakeMainJFrame();
            sankeMain.setLocationRelativeTo(null);
            sankeMain.setVisible(true);
        }

        if(e.getActionCommand().equals("ע��")){
            System.out.println("ע��");
            new RegisterDialog("ע�ᴰ��");
            this.setVisible(false);
        }

    }



}
