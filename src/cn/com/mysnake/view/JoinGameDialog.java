package cn.com.mysnake.view;

import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.TreeSet;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListModel;

/**
 *
 * @author ccq
 * 贪食蛇的多人游戏设置窗口
 *
 */
public class JoinGameDialog extends javax.swing.JDialog {

    private JButton JoinGameButton;
    private JButton CreateGameButton;
    private static JList GameList = null;
    private JoinGameDialog myThis;
    private SnakeJPanel panel;
    /**
     * Auto-generated main method to display this JDialog
     */

    public JoinGameDialog(Window frame,SnakeJPanel panel)
    {
        super(frame);
        initGUI();
        myThis = this;
        this.panel = panel;
    }

    private void initGUI()
    {
        try {
            FlowLayout thisLayout = new FlowLayout();
            getContentPane().setLayout(thisLayout);
            JoinGameButton = new JButton();
            CreateGameButton = new JButton();
            {
                getContentPane().add(JoinGameButton);
                JoinGameButton.setText("\u52a0\u5165\u6e38\u620f");
                JoinGameButton.addMouseListener(new MouseAdapter() {

                    public void mouseClicked(MouseEvent e)
                    {
                        System.out.println("加入游戏");
                        String serverIP = (String) GameList.getModel().getElementAt(GameList.getSelectedIndex());
                        //关闭窗口
                        myThis.setVisible(false);
                        if(serverIP != null)
                        {
                            //启动Tcp数据发送线程
                            panel.startMorePersonGameClientThread(serverIP);
                        }
                        else
                        {

                            JOptionPane.showMessageDialog(null,"请选择","没有选择",JOptionPane.ERROR_MESSAGE);//提示框
                        }
                    }

                });
            }

            {
                getContentPane().add(CreateGameButton);
                CreateGameButton.setText("\u521b\u5efa\u6e38\u620f");
                CreateGameButton.addMouseListener(new MouseAdapter() {

                    public void mouseClicked(MouseEvent e)
                    {
                        System.out.println("创建游戏");
                        //启动Tcp数据接收服务器
                        panel.startMorePersonGameServerThread();
                        //关闭窗口
                        myThis.setVisible(false);
                    }

                });
            }

            {

                ListModel GameListModel = new DefaultComboBoxModel();
                GameList = new JList();
                JScrollPane scrollPane = new JScrollPane(GameList);
                getContentPane().add(scrollPane);

                GameList.setModel(GameListModel);
                GameList.setPreferredSize(new java.awt.Dimension(343, 212));
            }

            setSize(400, 300);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateList(TreeSet set)
    {
        if(GameList != null)
        {
            ListModel GameListModel = new DefaultComboBoxModel(set.toArray());
            GameList.setModel(GameListModel);
        }
    }

}
