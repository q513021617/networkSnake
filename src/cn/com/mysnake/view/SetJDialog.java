package cn.com.mysnake.view;

import cn.com.mysnake.controller.UserController;
import cn.com.mysnake.dao.UserDao;
import cn.com.mysnake.daoImpl.UserDaoImpl;
import cn.com.mysnake.model.User;
import com.cloudgarden.layout.AnchorConstraint;
import com.cloudgarden.layout.AnchorLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.Properties;

/**
 * 设置对话框
 */
public class SetJDialog extends javax.swing.JDialog implements ActionListener {
    private JButton SureButton;
    private JTextField NameTextField;
    private JLabel nameLabel;
    private JTextField passwordField;
    private JLabel passwordLabel;

    private File file;
    private UserController userController=new UserController();
    private UserDao userDao=new UserDaoImpl();
    private User guser=new User();
    private Properties pro = new Properties();
    /**
     * Auto-generated main method to display this JDialog
     */

    public SetJDialog(JFrame frame)
    {
        super(frame);

        //配置文件
        pro = new Properties();
        file = new File(".\\snake.config");
        try {
            pro.load(new FileReader(file));
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }

        initGUI();
    }

    private void initGUI() {
        this.setSize(300,200);
        this.setLayout(new FlowLayout());
        JPanel newjpanel=new JPanel();
        newjpanel.setLayout(new GridLayout(2,2));
        try {


            {
                nameLabel = new JLabel();
                newjpanel.add(nameLabel);
                nameLabel.setText("\u540d\u79f0:");
                nameLabel.setPreferredSize(new java.awt.Dimension(47, 19));
            }

            {
                NameTextField = new JTextField(16);
                NameTextField.setText(pro.getProperty("name"));
                newjpanel.add(NameTextField);

            }


            {
                passwordLabel = new JLabel();
                newjpanel.add(passwordLabel);
                passwordLabel.setText("\u5bc6\u7801:");
                passwordLabel.setPreferredSize(new java.awt.Dimension(47, 19));
            }

            {
                passwordField = new JTextField(16);
                User user=userDao.queryByUsername(pro.getProperty("name"));
                guser=user;
                passwordField.setText(user.getUserpassword());
                newjpanel.add(passwordField);
                passwordField.setPreferredSize(new java.awt.Dimension(136, 24));
            }


            this.add(newjpanel);
            {
                SureButton = new JButton();
                this.add(SureButton);
                SureButton.setText("\u4fdd\u5b58");
                SureButton.setPreferredSize(new java.awt.Dimension(67, 24));
                SureButton.addActionListener(this);
            }
            setSize(400, 300);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        User tempuser=new User(0,NameTextField.getText(),passwordField.getText());
        boolean status=userController.updateUser(guser.getUserName(),tempuser);
        pro.setProperty("name",NameTextField.getText());
        try {
            pro.store(new FileWriter(file), "snake");
        }catch (Exception ex){
            ex.printStackTrace();
        }

        this.setVisible(false);
        if(status){
            JOptionPane.showMessageDialog(null,"修改信息成功!","提示信息:",JOptionPane.INFORMATION_MESSAGE);//提示框
        }else{
            JOptionPane.showMessageDialog(null,"修改信息失败!","提示信息:",JOptionPane.ERROR_MESSAGE);//提示框
        }
        this.dispose();


    }
}
