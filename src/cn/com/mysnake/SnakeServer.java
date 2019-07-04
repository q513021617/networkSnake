package cn.com.mysnake;

import cn.com.mysnake.model.Snake;
import cn.com.mysnake.view.SnakeJPanel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;


/**
 *
 * @author ccq
 * 蛇的服务器接收到的连接
 *
 */
class ServerSockeTestThread extends Thread
{
    private Socket socket;
    private SnakeJPanel panel;
    private Snake sanke;
    public ServerSockeTestThread(Socket socket,SnakeJPanel panel,Snake snake)
    {
        super();
        this.socket = socket;
        this.panel = panel;
        this.sanke = snake;
    }

    public void run()
    {
        LinkedList<Snake> list =SnakeJPanel.getList();
        int index = list.indexOf(this.sanke);

        if(index == -1)
            return;
        Snake findSnake = list.get(index);
        BufferedWriter writer = null;
        BufferedReader reader = null;
        //建立发送数据缓冲区
        StringBuilder sb = new StringBuilder();
        try {
            //建立流通道
            writer  = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while(socket.isConnected())//如果连接建立
            {
                sb.delete(0, sb.length());//清空缓冲区
                //读取客户端蛇的数据
                findSnake.resolveSanke(reader.readLine());

                //将本地蛇与非findSnake里的其他蛇数据进行发送到客户端
                //1.添加除了该类指向的蛇之外的网络蛇
                for(Snake temp:list)
                {
                    if(temp != findSnake)
                    {
                        sb.append(temp.buildSanke()+"@");
                    }
                }
                //2.添加本地蛇的数据
                sb.append(SnakeJPanel.getSnake().buildSanke());

                writer.write(sb.toString());
                writer.newLine();
                writer.flush();

                panel.repaint();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    // TODO 自动生成的 catch 块
                    e.printStackTrace();
                }
            }
            socket.close();
        } catch (IOException e) {
            //如果客户端退出
            sanke.setState(false);
            try {
                socket.close();
            } catch (IOException e1) {
                // TODO 自动生成的 catch 块
                e1.printStackTrace();
            }
        }
    }
}
/**
 *
 * @author ccq
 * 蛇的服务器
 *
 */
public class SnakeServer extends Thread
{
    private static final int SNAKEPORT = 60000;
    private ServerSocket serverSocket;
    private SnakeJPanel panel;
    private boolean isLive = false;

    public SnakeServer(SnakeJPanel panel)
    {
        this.panel = panel;
    }

    public void stopServer()
    {
        try {
            serverSocket.close();
        } catch (IOException e) {
            System.out.println("服务器关闭异常");
        }
    }

    public void setState(boolean isLive)
    {
        this.isLive = isLive;
    }

    public void run()
    {
        try {
            serverSocket = new  ServerSocket(SNAKEPORT);
            this.isLive = true;

            //设置多人游戏开始标志
            panel.startMorePersonGame();

            //设置本地的蛇为服务器蛇
            SnakeJPanel.getSnake().setIsServerSnake(true);

            while(isLive)
            {
                Socket socket = serverSocket.accept();
                //获取蛇的名字
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String snakeName = reader.readLine();

                LinkedList<Snake> list = SnakeJPanel.getList();

                Snake snake = new Snake(socket.getInetAddress().toString(),snakeName);

                if(!list.contains(snake))//判断是否存在该蛇  如果存在则不往下执行
                {
                    //接收到连接 在本地添加网络蛇
                    list.add(snake);
                    snake.recreat(10, 10);
                    snake.setState(true);

                    //启动连接线程
                    new ServerSockeTestThread(socket,panel,snake).start();
                }
                else
                {
                    System.out.println("该蛇已经存在");
                    Snake s = list.get(list.indexOf(snake));
                    if(!s.getState())
                        new ServerSockeTestThread(socket,panel,s).start();
                }
            }
        } catch (IOException e) {
            //关闭资源
            try {
                if(serverSocket!=null)
                    serverSocket.close();
            } catch (IOException e1) {
                System.out.println("服务器关闭异常");
                this.isLive = false;
            }
            System.out.println("服务器关闭");

            this.isLive = false;
        }
    }
}
