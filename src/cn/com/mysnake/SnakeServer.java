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
 * �ߵķ��������յ�������
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
        //�����������ݻ�����
        StringBuilder sb = new StringBuilder();
        try {
            //������ͨ��
            writer  = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while(socket.isConnected())//������ӽ���
            {
                sb.delete(0, sb.length());//��ջ�����
                //��ȡ�ͻ����ߵ�����
                findSnake.resolveSanke(reader.readLine());

                //�����������findSnake������������ݽ��з��͵��ͻ���
                //1.��ӳ��˸���ָ�����֮���������
                for(Snake temp:list)
                {
                    if(temp != findSnake)
                    {
                        sb.append(temp.buildSanke()+"@");
                    }
                }
                //2.��ӱ����ߵ�����
                sb.append(SnakeJPanel.getSnake().buildSanke());

                writer.write(sb.toString());
                writer.newLine();
                writer.flush();

                panel.repaint();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    // TODO �Զ����ɵ� catch ��
                    e.printStackTrace();
                }
            }
            socket.close();
        } catch (IOException e) {
            //����ͻ����˳�
            sanke.setState(false);
            try {
                socket.close();
            } catch (IOException e1) {
                // TODO �Զ����ɵ� catch ��
                e1.printStackTrace();
            }
        }
    }
}
/**
 *
 * @author ccq
 * �ߵķ�����
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
            System.out.println("�������ر��쳣");
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

            //���ö�����Ϸ��ʼ��־
            panel.startMorePersonGame();

            //���ñ��ص���Ϊ��������
            SnakeJPanel.getSnake().setIsServerSnake(true);

            while(isLive)
            {
                Socket socket = serverSocket.accept();
                //��ȡ�ߵ�����
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String snakeName = reader.readLine();

                LinkedList<Snake> list = SnakeJPanel.getList();

                Snake snake = new Snake(socket.getInetAddress().toString(),snakeName);

                if(!list.contains(snake))//�ж��Ƿ���ڸ���  �������������ִ��
                {
                    //���յ����� �ڱ������������
                    list.add(snake);
                    snake.recreat(10, 10);
                    snake.setState(true);

                    //���������߳�
                    new ServerSockeTestThread(socket,panel,snake).start();
                }
                else
                {
                    System.out.println("�����Ѿ�����");
                    Snake s = list.get(list.indexOf(snake));
                    if(!s.getState())
                        new ServerSockeTestThread(socket,panel,s).start();
                }
            }
        } catch (IOException e) {
            //�ر���Դ
            try {
                if(serverSocket!=null)
                    serverSocket.close();
            } catch (IOException e1) {
                System.out.println("�������ر��쳣");
                this.isLive = false;
            }
            System.out.println("�������ر�");

            this.isLive = false;
        }
    }
}
