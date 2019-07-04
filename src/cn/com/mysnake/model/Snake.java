package cn.com.mysnake.model;


import cn.com.mysnake.view.SnakeJPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Collections;
import java.util.LinkedList;

public class Snake {

    //贪吃蛇身体节点
    private LinkedList<SnakeBody> snakeBodyLinkedList;

    private boolean isLive = false;
    private static final int MAX_X_NUM = SnakeJPanel.SIZE_X/SnakeBody.SIZE;
    private static final int MAX_Y_NUM = SnakeJPanel.SIZE_Y/SnakeBody.SIZE;
    private static final int SCORE_MULTIPLE = 10;//吃到食物份数倍数

    public static final ImageIcon snakeHeadImg = new ImageIcon("F:\\code\\java\\snake2\\src\\images\\head.png");
    public static final ImageIcon snakeBodyImg =new ImageIcon("F:\\code\\java\\snake2\\src\\images\\body.png");

//    public Color snakeColor;//蛇的颜色
//    public Color snakeHeadColor;//蛇头的颜色
    private int score;
    private int direction;//当前蛇前进方向
    private String ip;
    private String name;
    private boolean isServerSnake = false;

    public Snake(String ip, String name)
    {
//        snakeColor = new Color(255,255,0);
//        snakeHeadColor = new Color(255,0,0);
        this.snakeBodyLinkedList = new LinkedList<SnakeBody>();
        this.score = 0;
        this.direction = 0;
        this.ip = ip;
        this.name = name;
    }

    //设置该蛇是否在服务端上
    public void setIsServerSnake(boolean isServerSnake)
    {
        this.isServerSnake = isServerSnake;
    }
    //获取该蛇是否在服务端上
    public boolean getIsServerSnake()
    {
        return this.isServerSnake;
    }

    //获取蛇的IP
    public String getIP()
    {
        return this.ip;
    }
    //获取蛇名
    public String getName()
    {
        return this.name;
    }


    //设置蛇名
    public void setName(String name)
    {
        this.name = name;
    }

    //创建蛇头
    public void createHead(int x,int y)
    {
        SnakeBody head = new SnakeBody(x,y);
        snakeBodyLinkedList.add(head);
        snakeBodyLinkedList.add(new SnakeBody(x, y));
        snakeBodyLinkedList.add(new SnakeBody(x, y));
    }

    //重新创建蛇
    public void recreat(int x,int y)
    {
        snakeBodyLinkedList.clear();
        this.direction = 0;
        createHead(x,y);
    }

    //获取蛇控制链表
    public LinkedList<SnakeBody> getList()
    {
        return this.snakeBodyLinkedList;
    }

    //获取份数
    public int getScore()
    {
        return this.score;
    }

    //设置份数
    public void setScore(int score)
    {
        this.score = score;
    }

    //设置蛇的状态
    public void setState(boolean isLive)
    {
        this.isLive = isLive;
        if(this.isLive == false)
        {
            if(this.isServerSnake)
                this.isServerSnake = false;
        }
    }
    //获取蛇的状态
    public boolean getState()
    {
        return this.isLive;
    }

    //吃食物 方法
    public void eatFood(int x,int y)
    {
        if(x==SnakeJPanel.getFood().getX() && y==SnakeJPanel.getFood().getY())
        {
            this.score+=SCORE_MULTIPLE;
            snakeBodyLinkedList.add(new SnakeBody(snakeBodyLinkedList.getLast().getX(), snakeBodyLinkedList.getLast().getY()));
            SnakeJPanel.getFood().crateFood();
        }
    }

    //界限判定
    public void boxLimits(int [] intArr)
    {
        if(intArr[0] < 0)
            intArr[0] = MAX_X_NUM-1;
        else if(intArr[0] > MAX_X_NUM-1)
            intArr[0] = 0;
        if(intArr[1] < 0)
            intArr[1] = MAX_Y_NUM-1;
        else if(intArr[1] > MAX_Y_NUM-1)
            intArr[1] = 0;
    }

    //获取蛇当前移动方向
    public int getDirection()
    {
        return this.direction;
    }

    //方向判断
    public boolean isRightDirection(int x,int y)
    {
        int direction = 0;

        if(x == 0 && y == -1)
            direction = KeyEvent.VK_UP;
        else if(x == 0 && y == 1)
            direction = KeyEvent.VK_DOWN;
        else if(x == -1 && y == 0)
            direction = KeyEvent.VK_LEFT;
        else if(x == 1 && y == 0)
            direction = KeyEvent.VK_RIGHT;

        switch (direction)
        {
            case KeyEvent.VK_UP:
            {
                if(this.direction == KeyEvent.VK_DOWN)
                    return false;
                break;
            }

            case KeyEvent.VK_DOWN:
            {
                if(this.direction == KeyEvent.VK_UP)
                    return false;
                break;
            }

            case KeyEvent.VK_LEFT:
            {
                if(this.direction == KeyEvent.VK_RIGHT)
                    return false;
                break;
            }

            case KeyEvent.VK_RIGHT:
            {
                if(this.direction == KeyEvent.VK_LEFT)
                    return false;
                break;
            }

            default:
                break;
        }
        this.direction = direction;
        return true;
    }

    //死亡判定
    public boolean isDead(int [] intArr)
    {
        return snakeBodyLinkedList.contains(new SnakeBody(intArr[0],intArr[1]));
    }

    public void move(int x, int y)
    {
        if(this.snakeBodyLinkedList.size()!=0)
        {
            //方向判定
            if(!isRightDirection(x,y))
                return;
            //吃食物
            eatFood(snakeBodyLinkedList.get(0).getX()+x,snakeBodyLinkedList.get(0).getY()+y);
            //蛇身先进行移动
            for(int i = snakeBodyLinkedList.size()-1; i >0;i--)
            {
                if(i > 1)
                    Collections.swap(snakeBodyLinkedList, i, i-1);
                else
                {
                    snakeBodyLinkedList.get(i).setX(snakeBodyLinkedList.get(0).getX());
                    snakeBodyLinkedList.get(i).setY(snakeBodyLinkedList.get(0).getY());
                }
            }
            //进行界限判定
            int [] intArr = new int[2];
            intArr[0] = snakeBodyLinkedList.get(0).getX()+x;
            intArr[1] = snakeBodyLinkedList.get(0).getY()+y;
            this.boxLimits(intArr);

            //死亡判定
            if(isDead(intArr))
            {
                this.isLive=false;//设置蛇状态
                if(SnakeJPanel.isMorePerson == false)
                {
                    System.out.println("游戏结束");
                    JOptionPane.showMessageDialog(null,"Game over","游戏结束",JOptionPane.ERROR_MESSAGE);//提示框
                }
                else
                    JOptionPane.showMessageDialog(null,"sorry,多人游戏中你已经死亡","游戏结束",JOptionPane.ERROR_MESSAGE);//提示框
            }
            //蛇头进行移动
            snakeBodyLinkedList.get(0).setX(intArr[0]);
            snakeBodyLinkedList.get(0).setY(intArr[1]);
        }
    }

    //建立蛇
    public String buildSanke()
    {
        StringBuilder date = new StringBuilder();
        date.append(this.name+":");//"name:"+
        date.append(this.ip+":");//"ip:"+
        date.append(this.isLive+":");//"isLive:"+
        date.append(this.score+":");//"score:"+
        date.append(isServerSnake+":");//"isServer:"+
//        食物的坐标
        date.append(SnakeJPanel.getFood().getX()+":"+SnakeJPanel.getFood().getY()+":");//"food:"+
//        蛇的身体的长度
        date.append(snakeBodyLinkedList.size()+":");//"size:"+

        for(int i = 0; i < snakeBodyLinkedList.size(); i++)
        {
            date.append(snakeBodyLinkedList.get(i).getX()+":"+snakeBodyLinkedList.get(i).getY()+":");//"x:"+  +"y:"
        }
        date.append("end");

        return date.toString();
    }


    //分解蛇
    public void resolveSanke(String date)
    {
        if(date == null)
            return;
        String []strs = date.split(":");
        //进行简单的数据效验
        if(strs[strs.length-1].equals("end"))
        {
            //蛇的状态
            if(strs[2].equals("true"))
                this.isLive = true;
            else
                this.isLive =false;

            //蛇的成绩
            this.score = Integer.parseInt(strs[3]);

            //如果是主服务器的蛇需要跟新下食物
            if(strs[4].equals("true"))
            {
                SnakeJPanel.getFood().setX(Integer.parseInt(strs[5]));
                SnakeJPanel.getFood().setY(Integer.parseInt(strs[6]));
            }
            int size = Integer.parseInt(strs[7]);
            //防止同名蛇重复加入
            if(size < snakeBodyLinkedList.size())
                snakeBodyLinkedList.clear();

            for(int i = 0; i < size; i ++)
            {
                if(snakeBodyLinkedList.size() == i)
                {
                    snakeBodyLinkedList.add(new SnakeBody(Integer.parseInt(strs[(i*2)+8]), Integer.parseInt(strs[(i*2)+9])));
                }
                else
                {
                    snakeBodyLinkedList.get(i).setX(Integer.parseInt(strs[(i*2)+8]));
                    snakeBodyLinkedList.get(i).setY(Integer.parseInt(strs[(i*2)+9]));
                }
            }
        }
        else
            System.out.println("数据不正确");
    }

    //获取蛇的名称
    public static String getDateName(String date)
    {
        String []strs = date.split(":");
        //进行简单的数据效验
        if(strs[strs.length-1].equals("end"))
        {
            return strs[0];
        }
        return null;
    }
    //获取蛇的IP
    public static String getDateIP(String date)
    {
        String []strs = date.split(":");
        //进行简单的数据效验
        if(strs[strs.length-1].equals("end"))
        {
            return strs[1];
        }
        return null;
    }

    public boolean equals(Object obj)
    {
        if(this.ip.equals(((Snake)obj).ip) && this.name.equals(((Snake)obj).name))
            return true;
        else
            return false;
    }
}
