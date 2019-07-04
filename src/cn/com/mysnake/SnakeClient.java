package cn.com.mysnake;

import cn.com.mysnake.model.Snake;
import cn.com.mysnake.view.SnakeJPanel;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class SnakeClient extends Thread{
    private static final int SNAKEPORT = 60000;
    private Socket socket;
    private SnakeJPanel panel;
    private String serverIP;

    public SnakeClient(SnakeJPanel panel,String serverIP)
    {
        this.panel = panel;
        this.serverIP = serverIP;
    }

    public void stopClient()
    {
        try {
            if(socket.isConnected())
                this.socket.close();
        } catch (IOException e) {
            System.out.println("客户端关闭异常");
        }
    }

    public void run()
    {
        try {
            socket = new Socket(InetAddress.getByName(serverIP),SNAKEPORT);
            BufferedWriter writer  = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));


            //发送蛇的名字
            writer.write(SnakeJPanel.getSnake().getName()+"\r\n");
            writer.flush();

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                // TODO 自动生成的 catch 块
                e.printStackTrace();
            }

            //设置多人游戏开始标志
            panel.startMorePersonGame();

            while(socket.isConnected())
            {
                //先将本地蛇的数据进行发送
                writer.write(SnakeJPanel.getSnake().buildSanke());
                writer.newLine();
                writer.flush();

                //读取网络蛇的信息
                String [] strs = reader.readLine().split("@");

                //对号入座
                if(strs.length!=0)
                {
                    for(String str:strs)
                    {
                        Snake snake = new Snake(Snake.getDateIP(str),Snake.getDateName(str));
                        int index = SnakeJPanel.getList().indexOf(snake);
                        if(index != -1)
                        {
                            SnakeJPanel.getList().get(index).resolveSanke(str);
                        }
                        else
                        {
                            //创建新蛇
                            SnakeJPanel.getList().add(snake);
                            snake.recreat(10, 10);
                            snake.setState(true);
                        }
                    }
                }
                panel.repaint();

                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    // TODO 自动生成的 catch 块
                    e.printStackTrace();
                }
            }

        } catch (UnknownHostException e) {
            System.out.println("服务器退出");
            try {
                socket.close();
            } catch (IOException e1) {
                // TODO 自动生成的 catch 块
                e1.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println("服务器退出");
            try {
                if(socket!=null)
                    socket.close();
            } catch (IOException e1) {
                // TODO 自动生成的 catch 块
                e1.printStackTrace();
            }
            SnakeJPanel.isMorePerson = false;
            SnakeJPanel.isGameStart = false;
            //JOptionPane.showMessageDialog(null,"失去连接 请重新建立服务器","服务器异常",JOptionPane.ERROR_MESSAGE);//提示框
        }


    }
}
