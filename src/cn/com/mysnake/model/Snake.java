package cn.com.mysnake.model;


import cn.com.mysnake.view.SnakeJPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Collections;
import java.util.LinkedList;

public class Snake {

    //̰��������ڵ�
    private LinkedList<SnakeBody> snakeBodyLinkedList;

    private boolean isLive = false;
    private static final int MAX_X_NUM = SnakeJPanel.SIZE_X/SnakeBody.SIZE;
    private static final int MAX_Y_NUM = SnakeJPanel.SIZE_Y/SnakeBody.SIZE;
    private static final int SCORE_MULTIPLE = 10;//�Ե�ʳ���������

    public static final ImageIcon snakeHeadImg = new ImageIcon("F:\\code\\java\\snake2\\src\\images\\head.png");
    public static final ImageIcon snakeBodyImg =new ImageIcon("F:\\code\\java\\snake2\\src\\images\\body.png");

//    public Color snakeColor;//�ߵ���ɫ
//    public Color snakeHeadColor;//��ͷ����ɫ
    private int score;
    private int direction;//��ǰ��ǰ������
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

    //���ø����Ƿ��ڷ������
    public void setIsServerSnake(boolean isServerSnake)
    {
        this.isServerSnake = isServerSnake;
    }
    //��ȡ�����Ƿ��ڷ������
    public boolean getIsServerSnake()
    {
        return this.isServerSnake;
    }

    //��ȡ�ߵ�IP
    public String getIP()
    {
        return this.ip;
    }
    //��ȡ����
    public String getName()
    {
        return this.name;
    }


    //��������
    public void setName(String name)
    {
        this.name = name;
    }

    //������ͷ
    public void createHead(int x,int y)
    {
        SnakeBody head = new SnakeBody(x,y);
        snakeBodyLinkedList.add(head);
        snakeBodyLinkedList.add(new SnakeBody(x, y));
        snakeBodyLinkedList.add(new SnakeBody(x, y));
    }

    //���´�����
    public void recreat(int x,int y)
    {
        snakeBodyLinkedList.clear();
        this.direction = 0;
        createHead(x,y);
    }

    //��ȡ�߿�������
    public LinkedList<SnakeBody> getList()
    {
        return this.snakeBodyLinkedList;
    }

    //��ȡ����
    public int getScore()
    {
        return this.score;
    }

    //���÷���
    public void setScore(int score)
    {
        this.score = score;
    }

    //�����ߵ�״̬
    public void setState(boolean isLive)
    {
        this.isLive = isLive;
        if(this.isLive == false)
        {
            if(this.isServerSnake)
                this.isServerSnake = false;
        }
    }
    //��ȡ�ߵ�״̬
    public boolean getState()
    {
        return this.isLive;
    }

    //��ʳ�� ����
    public void eatFood(int x,int y)
    {
        if(x==SnakeJPanel.getFood().getX() && y==SnakeJPanel.getFood().getY())
        {
            this.score+=SCORE_MULTIPLE;
            snakeBodyLinkedList.add(new SnakeBody(snakeBodyLinkedList.getLast().getX(), snakeBodyLinkedList.getLast().getY()));
            SnakeJPanel.getFood().crateFood();
        }
    }

    //�����ж�
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

    //��ȡ�ߵ�ǰ�ƶ�����
    public int getDirection()
    {
        return this.direction;
    }

    //�����ж�
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

    //�����ж�
    public boolean isDead(int [] intArr)
    {
        return snakeBodyLinkedList.contains(new SnakeBody(intArr[0],intArr[1]));
    }

    public void move(int x, int y)
    {
        if(this.snakeBodyLinkedList.size()!=0)
        {
            //�����ж�
            if(!isRightDirection(x,y))
                return;
            //��ʳ��
            eatFood(snakeBodyLinkedList.get(0).getX()+x,snakeBodyLinkedList.get(0).getY()+y);
            //�����Ƚ����ƶ�
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
            //���н����ж�
            int [] intArr = new int[2];
            intArr[0] = snakeBodyLinkedList.get(0).getX()+x;
            intArr[1] = snakeBodyLinkedList.get(0).getY()+y;
            this.boxLimits(intArr);

            //�����ж�
            if(isDead(intArr))
            {
                this.isLive=false;//������״̬
                if(SnakeJPanel.isMorePerson == false)
                {
                    System.out.println("��Ϸ����");
                    JOptionPane.showMessageDialog(null,"Game over","��Ϸ����",JOptionPane.ERROR_MESSAGE);//��ʾ��
                }
                else
                    JOptionPane.showMessageDialog(null,"sorry,������Ϸ�����Ѿ�����","��Ϸ����",JOptionPane.ERROR_MESSAGE);//��ʾ��
            }
            //��ͷ�����ƶ�
            snakeBodyLinkedList.get(0).setX(intArr[0]);
            snakeBodyLinkedList.get(0).setY(intArr[1]);
        }
    }

    //������
    public String buildSanke()
    {
        StringBuilder date = new StringBuilder();
        date.append(this.name+":");//"name:"+
        date.append(this.ip+":");//"ip:"+
        date.append(this.isLive+":");//"isLive:"+
        date.append(this.score+":");//"score:"+
        date.append(isServerSnake+":");//"isServer:"+
//        ʳ�������
        date.append(SnakeJPanel.getFood().getX()+":"+SnakeJPanel.getFood().getY()+":");//"food:"+
//        �ߵ�����ĳ���
        date.append(snakeBodyLinkedList.size()+":");//"size:"+

        for(int i = 0; i < snakeBodyLinkedList.size(); i++)
        {
            date.append(snakeBodyLinkedList.get(i).getX()+":"+snakeBodyLinkedList.get(i).getY()+":");//"x:"+  +"y:"
        }
        date.append("end");

        return date.toString();
    }


    //�ֽ���
    public void resolveSanke(String date)
    {
        if(date == null)
            return;
        String []strs = date.split(":");
        //���м򵥵�����Ч��
        if(strs[strs.length-1].equals("end"))
        {
            //�ߵ�״̬
            if(strs[2].equals("true"))
                this.isLive = true;
            else
                this.isLive =false;

            //�ߵĳɼ�
            this.score = Integer.parseInt(strs[3]);

            //�������������������Ҫ������ʳ��
            if(strs[4].equals("true"))
            {
                SnakeJPanel.getFood().setX(Integer.parseInt(strs[5]));
                SnakeJPanel.getFood().setY(Integer.parseInt(strs[6]));
            }
            int size = Integer.parseInt(strs[7]);
            //��ֹͬ�����ظ�����
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
            System.out.println("���ݲ���ȷ");
    }

    //��ȡ�ߵ�����
    public static String getDateName(String date)
    {
        String []strs = date.split(":");
        //���м򵥵�����Ч��
        if(strs[strs.length-1].equals("end"))
        {
            return strs[0];
        }
        return null;
    }
    //��ȡ�ߵ�IP
    public static String getDateIP(String date)
    {
        String []strs = date.split(":");
        //���м򵥵�����Ч��
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
