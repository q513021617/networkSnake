package cn.com.mysnake.model;

public class SnakeBody {
//    Ì°³ÔÉß×ø±ê
    private int x;
    private int y;
//    Ì°³ÔÉßµÄ³¤¶È
    public static final int SIZE = 20;

    public SnakeBody(int x, int y) {
        this.x = x;
        this.y = y;
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

    @Override
    public boolean equals(Object obj) {

        if(((SnakeBody)obj).x == this.x && ((SnakeBody)obj).y == this.y)
            return true;
        else
            return false;
    }
}
