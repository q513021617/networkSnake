package cn.com.mysnake;

import cn.com.mysnake.controller.ScoreController;
import cn.com.mysnake.model.Snake;
import cn.com.mysnake.view.SnakeJPanel;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.Properties;

import javax.swing.JOptionPane;

/**
 *
 * @author ccq
 * �����ߵ��Զ�����
 *
 */
public class SnakeMoveThread extends Thread
{
    SnakeJPanel snakeJPanel;
    private int speed; //�ٶ�
    private ScoreController scoreController=new ScoreController();
    Properties pro = new Properties();
    File file = new File(".\\snake.config");
    public SnakeMoveThread(SnakeJPanel snakeJPanel,int speed)
    {
        super();
        this.snakeJPanel = snakeJPanel;
        this.speed = speed;
    }

    public int getSpeed()
    {
        return this.speed;
    }

    public void setSpeed(int speed)
    {
        this.speed = speed;
    }

    public void run()
    {
        Snake snake = SnakeJPanel.getSnake();
        while(true)
        {
            snakeMove(snake);

            if(SnakeJPanel.isMorePerson)//����Ƕ�����Ϸ
            {
                LinkedList<Snake> list = SnakeJPanel.getList();
                for(int i = 0 ; i < list.size() ; i++)
                {
                    snakeMove(list.get(i));
                }
            }

            //����ˢ�½���
            this.snakeJPanel.repaint();

            //�ж϶�����Ϸ����
            if(SnakeJPanel.isMorePerson)
            {
                if(isAllDead())
                {
                    System.out.println("������Ϸ����");
                    SnakeJPanel.isGameStart = false;
                    SnakeJPanel.isMorePerson = false;
                    //�رշ�����socket
                    if(SnakeJPanel.getSnake().getIsServerSnake())
                    {
                        snakeJPanel.getServer().stopServer();
                        snakeJPanel.getServer().setState(false);
                    }
                    else
                        snakeJPanel.getClient().stopClient();
                    JOptionPane.showMessageDialog(null,"Game over","��Ϸ����",JOptionPane.ERROR_MESSAGE);//��ʾ��
                    try {
                        pro.load(new FileReader(file));
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                    String username=pro.getProperty("name");
                    String score=pro.getProperty("score");
                    scoreController.eatFood(username,Integer.parseInt(score));

                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    SnakeJPanel.getList().clear();
                    UdpThread.clearList();
                }
            }

            try {
                Thread.sleep(this.speed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isAllDead()
    {
        boolean flag = false;
        for(Snake temp : SnakeJPanel.getList())
        {
            if(temp.getState())
                flag = true;
        }
        if(SnakeJPanel.getSnake().getState())
            flag = true;

        if(flag)
            return false;
        else
            return true;
    }


    private void snakeMove(Snake snake)
    {
        if(snake.getState())
        {
            int direction = snake.getDirection();
            switch (direction)
            {
                case KeyEvent.VK_UP: //���ϼ�
                {
                    snake.move(0,-1); //���Ը�Ϊ����snakeJPanel��keyPressed����
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
    }


}
