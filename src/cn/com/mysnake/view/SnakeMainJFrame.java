package cn.com.mysnake.view;

import com.cloudgarden.layout.AnchorLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SnakeMainJFrame extends javax.swing.JFrame {
    private JMenuBar sankeMenuBar;
    private JMenu jMenu1;
    private JMenuItem setMenuItem;
    private JMenu helpjMenu;
    private JMenuItem morePersonGameMenuItem;
    private JMenuItem findHelpMenuItem;
    private JMenuItem newGameMenuItem;
    private SnakeJPanel panel;
    private JFrame mainFrame;
    private JoinGameDialog joinGameDialog;
    private SetJDialog setJDialog;
    private HelpJDialog helpJDialog;
    public SnakeMainJFrame()
    {
        super();
        mainFrame = this;
        initGUI();
    }

    private void initGUI() {
        try {

            AnchorLayout thisLayout = new AnchorLayout();
            getContentPane().setLayout(thisLayout);
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            this.setTitle("̰ʳ��");
            {
                sankeMenuBar = new JMenuBar();
                setJMenuBar(sankeMenuBar);

                {
                    panel = new SnakeJPanel();
                    //��ӻ���
                    this.add(panel);
                }

                {
                    jMenu1 = new JMenu();
                    sankeMenuBar.add(jMenu1);
                    jMenu1.setText("\u6e38\u620f(G)");
                    {
                        newGameMenuItem = new JMenuItem();
                        jMenu1.add(newGameMenuItem);
                        newGameMenuItem.setText("\u65b0\u6e38\u620f");
                        //Ϊitem����¼�
                        newGameMenuItem.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent evt) {
                                if(newGameMenuItem.isEnabled())
                                    panel.startGame();
                            }
                        });
                    }

                    {
                        setMenuItem = new JMenuItem();
                        jMenu1.add(setMenuItem);
                        setMenuItem.setText("\u8bbe\u7f6e");
                        setJDialog = new SetJDialog(mainFrame);
                        //Ϊitem����¼�
                        setMenuItem.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent evt)
                            {
                                if(setMenuItem.isEnabled())
                                {
                                    //��������ƽ̨ѡ���
                                    setJDialog.setVisible(true);
                                    setJDialog.setLocation(mainFrame.getX(),mainFrame.getY());
                                    setJDialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                                }
                            }
                        });
                    }

                    {
                        morePersonGameMenuItem = new JMenuItem();
                        jMenu1.add(morePersonGameMenuItem);
                        morePersonGameMenuItem.setText("\u591a\u4eba\u6e38\u620f");
                        joinGameDialog = new JoinGameDialog(mainFrame,panel);
                        //Ϊitem����¼�
                        morePersonGameMenuItem.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent evt)
                            {
                                if(morePersonGameMenuItem.isEnabled())
                                {
                                    //��������ƽ̨ѡ���
                                    joinGameDialog.setVisible(true);
                                    joinGameDialog.setLocation(mainFrame.getX(),mainFrame.getY());
                                    joinGameDialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                                }
                            }
                        });
                    }
                }


                {
                    helpjMenu = new JMenu();
                    sankeMenuBar.add(helpjMenu);
                    helpjMenu.setText("\u5e2e\u52a9");
                    {
                        findHelpMenuItem = new JMenuItem();
                        helpjMenu.add(findHelpMenuItem);
                        findHelpMenuItem.setText("\u67e5\u770b\u5e2e\u52a9");
                        helpJDialog = new HelpJDialog(mainFrame);
                        //Ϊitem����¼�
                        findHelpMenuItem.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent evt)
                            {
                                if(findHelpMenuItem.isEnabled())
                                {
                                    //��������ƽ̨ѡ���
                                    helpJDialog.setVisible(true);
                                    helpJDialog.setLocation(mainFrame.getX(),mainFrame.getY());
                                    helpJDialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                                }
                            }
                        });
                    }
                }

            }
            pack();
            this.setSize(640, 500);


            //����һ���̵߳������ڲ��������Ϸ��ť�Ƿ�ɰ�Ȩ�޵Ŀ���
            new Thread(){
                public void run()
                {
                    while(true)
                    {
                        if(panel.isMorePerson)
                        {
                            newGameMenuItem.setEnabled(false);
                            morePersonGameMenuItem.setEnabled(false);
                        }
                        else
                        {
                            newGameMenuItem.setEnabled(true);
                            morePersonGameMenuItem.setEnabled(true);
                        }

                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            // TODO �Զ����ɵ� catch ��
                            e.printStackTrace();
                        }
                    }
                };
            }.start();


        } catch (Exception e) {
            //add your error handling code here
            e.printStackTrace();
        }
    }
}
