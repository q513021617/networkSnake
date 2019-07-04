package cn.com.mysnake.model;

import cn.com.mysnake.view.SnakeJPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class SnakeFood {
    private int x;
    private int y;
    public static final int FOODSIZE = SnakeBody.SIZE;
    public static final ImageIcon snakeFoodImg=new ImageIcon("F:\\code\\java\\snake2\\src\\images\\food.png");
    public Color foodColor;
    Random random;

    public SnakeFood() {
        foodColor = new Color(0,255,0);
        random = new Random();
    }

    public void crateFood()
    {
        //通过随机数生成食物的X Y位置
        this.x = random.nextInt(SnakeJPanel.SIZE_X/this.FOODSIZE);
        this.y = random.nextInt(SnakeJPanel.SIZE_Y/this.FOODSIZE);
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
