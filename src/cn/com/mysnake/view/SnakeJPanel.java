package cn.com.mysnake.view;

import cn.com.mysnake.SnakeClient;
import cn.com.mysnake.SnakeMoveThread;
import cn.com.mysnake.SnakeServer;
import cn.com.mysnake.UdpThread;
import cn.com.mysnake.controller.ScoreController;
import cn.com.mysnake.model.Snake;
import cn.com.mysnake.model.SnakeBody;
import cn.com.mysnake.model.SnakeFood;
import com.cloudgarden.layout.AnchorConstraint;
import com.cloudgarden.layout.AnchorLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.Properties;

/**
 *
 * @author ccq
 * ̰ʳ�ߵĻ���
 *
 */
public class SnakeJPanel extends JPanel implements KeyListener
{
    public static final int STARTPOINT_X = 20;//�ڿ�ʼ��X
    public static final int STARTPOINT_Y = 10;//�ڿ�ʼ��Y
    public static final int SIZE_X = 580; //�ڿ򳤶�
    public static final int SIZE_Y = 320; //�ڿ�߶�
    public static final int BOX_GAP = 10; //�����϶
    public static final int SPEED = 500; //���Զ������ٶ�

    public static boolean isGameStart = false;//��Ϸ״̬��ʶ
    public static boolean isMorePerson =false;//������Ϸ���
    public static final ImageIcon backImg=new ImageIcon("F:\\code\\java\\snake2\\src\\images\\playground.png");
    private static SnakeFood food;//ʳ��
    private static Snake snake;//��
    private static LinkedList<Snake> netSnakelist; //���������ߵļ���
    private SnakeMoveThread snakeMoveThread;//���Զ������߳�
    private JList jScoreList;//�б��


    public Color boxColor;//�����ɫ

    public Color boxLowColor;//��ײ�����ɫ

    private SnakeServer snakeServer;//������
    private SnakeClient snakeClient;//�ͻ���
    File file = new File(".\\snake.config");
    private UdpThread udpThread;
    private Properties pro;//�����ļ�
    private LinkedList<String> scoreList;

    public SnakeJPanel()
    {
        super();
        initGUI();

        scoreList = new LinkedList<String>();

        //�����ļ�
        this.setBackground(new Color(64,86,30));

        this.addKeyListener(this);//��Ӽ����¼�����
        food = new SnakeFood();//����ʳ��
        pro = new Properties();
        //������
        try {

            pro.load(new FileReader(file));
            snake = new Snake(InetAddress.getLocalHost().toString(),pro.getProperty("name"));//��������Ҫ�������ж�ȡ

        } catch (UnknownHostException e) {
            System.out.println("ϵͳ����������");
        }catch (Exception ex){
            ex.printStackTrace();
        }
        //������·�߿�������
        netSnakelist = new LinkedList<Snake>();

        boxColor = new Color(0,0,0); //�����ɫ
        boxLowColor = new Color(0,69,232);//��׵���ɫ
        //�������Զ������߳�
        snakeMoveThread = new SnakeMoveThread(this,SPEED);
        snakeMoveThread.start();
        //����udp�߳�
        udpThread = new UdpThread();
        udpThread.start();
    }

    //�����ʼ��
    private void initGUI()
    {
        try {
//            ���ò��ֹ�������ʼ��С
//            java.awt.Dimension ���������ȸ߶�
            this.setPreferredSize(new java.awt.Dimension(620, 440));
//            ���ò���
            AnchorLayout thisLayout = new AnchorLayout();
            this.setLayout(thisLayout);
//            ���û�����С
            this.setSize(620, 440);

            /**
             * ����б�� �����
             */
            {
                ListModel scoreListModel = new DefaultComboBoxModel();
//                �б�
                jScoreList = new JList();
                JScrollPane scrollPane = new JScrollPane(jScoreList);

                this.add(scrollPane, new AnchorConstraint(785, 1000, 1001, 0, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
                jScoreList.setModel(scoreListModel);
                jScoreList.setPreferredSize(new java.awt.Dimension(620, 95));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    //��ȡ������
    public SnakeServer getServer()
    {
        return this.snakeServer;
    }
    //�����������߳�
    public void startMorePersonGameServerThread()
    {
        snakeServer = new SnakeServer(this);
        snakeServer.start();
    }
    //��ȡ�ͻ���
    public SnakeClient getClient()
    {
        return this.snakeClient;
    }

    //�����ͻ����߳�
    public void startMorePersonGameClientThread(String serverIP)
    {
        snakeClient = new SnakeClient(this,serverIP);
        snakeClient.start();
    }



    //�߿����
    public void paintBox(Graphics g)
    {

        //���Ƹ��ӵĵײ���ɫ
//        g.setColor(boxLowColor);//������ɫ
//        g.fill3DRect(STARTPOINT_X,STARTPOINT_Y,SIZE_X,SIZE_Y,true);
        backImg.paintIcon(this,g,SIZE_X,SIZE_Y);
        g.drawImage(backImg.getImage(),0,0,SIZE_X+30,SIZE_Y+20,backImg.getImageObserver());
        //�������
        g.setColor(this.boxColor);//������ɫ
        g.drawRect(STARTPOINT_X-BOX_GAP, STARTPOINT_Y-BOX_GAP, SIZE_X+BOX_GAP*2,SIZE_Y+BOX_GAP*2);
        g.drawRect(STARTPOINT_X, STARTPOINT_Y,SIZE_X,SIZE_Y);

        //���ƺ��
        for(int i = 0; i < SIZE_Y/ SnakeBody.SIZE; i ++)
        {
            g.drawLine(STARTPOINT_X,STARTPOINT_Y+SnakeBody.SIZE*(i+1),SIZE_X+SnakeBody.SIZE,STARTPOINT_Y+SnakeBody.SIZE*(i+1));
        }

        //��������
        for(int i = 0 ; i < SIZE_X/SnakeBody.SIZE;i ++)
        {
            g.drawLine(STARTPOINT_X+SnakeBody.SIZE*(i+1),STARTPOINT_Y,STARTPOINT_X+SnakeBody.SIZE*(i+1),SIZE_Y+SnakeBody.SIZE/2);
        }
    }

    //�����µ�ʳ��
    public void createFood()
    {
        food.crateFood();
    }

    //�����µ�ʳ��
    public void drawFood(Graphics g)
    {
        if(!this.isMorePerson)
        {
            //���Ƶ�ʳ��������������������ʳ��
            while(this.snake.getList().contains(new SnakeBody(food.getX(), food.getY())))
            {
                this.createFood();
//                ��ʳ��

            }
        }
        else if(this.snake.getIsServerSnake())//�ڶ�����Ϸ�л��Ƶ�ʳ��������������������ʳ��
        {
            //���Ƶ�ʳ��������������������ʳ��
            while(this.snake.getList().contains(new SnakeBody(food.getX(), food.getY())))
            {
                this.createFood();
                //                ��ʳ��
            }

            for(Snake temp:netSnakelist)
            {
                while(temp.getList().contains(new SnakeBody(food.getX(), food.getY())))
                {
                    this.createFood();
                }
            }
        }

        SnakeFood.snakeFoodImg.paintIcon(this,g,food.getX()*SnakeFood.FOODSIZE + STARTPOINT_X,food.getY()*SnakeFood.FOODSIZE + STARTPOINT_Y);
    }
    public static SnakeFood getFood()
    {
        return food;

    }

    public static Snake getSnake()
    {
        return snake;
    }

    //������
    public void createSnake(int x,int y)
    {
        //snake = new Snake();
        snake.recreat(x,y);
    }

    //���Ƶ�����
    public void drawSingleSnake(Snake snake, Graphics g)
    {

        System.out.println(Snake.snakeHeadImg.toString());

        if(snake.getState())
        {
            LinkedList<SnakeBody> list = snake.getList();
            //��������

            for (int i = 1; i < list.size(); i++)// ���ߵ�����
            {

                Snake.snakeBodyImg.paintIcon(this,g,list.get(i).getX()*SnakeBody.SIZE + STARTPOINT_X,list.get(i).getY()*SnakeBody.SIZE + STARTPOINT_Y);
            }
            //������ͷ

            Snake.snakeHeadImg.paintIcon(this,g,list.get(0).getX()*SnakeBody.SIZE + STARTPOINT_X,list.get(0).getY()*SnakeBody.SIZE + STARTPOINT_Y);

        }
    }

    //������
    public void drawSnake(Graphics g)
    {
//        �Ȼ�һ����
        drawSingleSnake(this.snake,g);
        //����Ƕ�����Ϸ
        if(this.isMorePerson)
        {
            for(int i = 0 ; i < netSnakelist.size() ; i++)
            {
                drawSingleSnake(netSnakelist.get(i),g);
            }
        }
    }

    //�������
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        paintBox(g);

        if(this.isGameStart)
        {
            //���õ�ǰ�ؼ�Ϊ����
            this.requestFocus(true);

            drawFood(g);//����ʳ��
            drawSnake(g);//������
            updateScore();//���³ɼ�
        }
    }

    public void startGame()
    {
        //����ߵĳɼ�
        this.snake.setScore(0);
        //�������
        this.netSnakelist.clear();
        //����ʳ��
        this.createFood();
        //������
        this.createSnake(10,10);
        //�ı���Ϸ��ʼ��ʶ
        snake.setState(true);
        this.isGameStart = true;
        this.isMorePerson = false;

        //���»��ƽ���
        this.repaint();

        System.out.println("��Ϸ��ʼ");
    }

    //��ʼ������Ϸ
    public void startMorePersonGame()
    {
        //����ߵĳɼ�
        this.snake.setScore(0);
        //�������
        this.netSnakelist.clear();
        //����ʳ��
        this.createFood();
        //������
        this.createSnake(10,10);

        snake.setState(true);
        //��Ϸ��ʼ��־
        isGameStart = true;
        //������Ϸ��־
        isMorePerson = true;

        //���»��ƽ���
        this.repaint();

        System.out.println("��Ϸ��ʼ");
    }
    //��ȡ�����߿�������
    public static LinkedList<Snake> getList()
    {
        return netSnakelist;
    }

    //���³ɼ�
    public void updateScore()
    {
        scoreList.clear();
        scoreList.add(this.snake.getName()+" ����:"+this.snake.getScore());
        pro.setProperty("score",this.snake.getScore()+"");
        try {
            pro.store(new FileWriter(file), "snake score");
        }catch (Exception ex){
            ex.printStackTrace();
        }

        for(Snake temp : this.netSnakelist)
        {
            scoreList.add(temp.getName()+" ����:"+temp.getScore());

        }

        //ListModel GameListModel = new DefaultComboBoxModel(set.toArray());
        jScoreList.setListData(scoreList.toArray());
    }


    //���̼��� �ӿ�KeyListener��ʵ�ַ���
    public void keyPressed(KeyEvent e)
    {
        if(this.snake.getState())
        {
            switch (e.getKeyCode())
            {
                case KeyEvent.VK_UP: //���ϼ�
                {
                    snake.move(0,-1);
                    break;
                }
                case KeyEvent.VK_DOWN: //���¼�
                {
                    snake.move(0,1);
                    break;
                }
                case KeyEvent.VK_LEFT: //�����
                {
                    snake.move(-1,0);
                    break;
                }
                case KeyEvent.VK_RIGHT: //���Ҽ�
                {
                    snake.move(1,0);
                    break;
                }
                default:
                    break;
            }
        }
        this.repaint();
    }
    //�ӿ�KeyListener��ʵ�ַ���
    public void keyTyped(KeyEvent e)
    {

    }
    //�ӿ�KeyListener��ʵ�ַ���
    public void keyReleased(KeyEvent e)
    {

    }
}
