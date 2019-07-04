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
            System.out.println("�ͻ��˹ر��쳣");
        }
    }

    public void run()
    {
        try {
            socket = new Socket(InetAddress.getByName(serverIP),SNAKEPORT);
            BufferedWriter writer  = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));


            //�����ߵ�����
            writer.write(SnakeJPanel.getSnake().getName()+"\r\n");
            writer.flush();

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                // TODO �Զ����ɵ� catch ��
                e.printStackTrace();
            }

            //���ö�����Ϸ��ʼ��־
            panel.startMorePersonGame();

            while(socket.isConnected())
            {
                //�Ƚ������ߵ����ݽ��з���
                writer.write(SnakeJPanel.getSnake().buildSanke());
                writer.newLine();
                writer.flush();

                //��ȡ�����ߵ���Ϣ
                String [] strs = reader.readLine().split("@");

                //�Ժ�����
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
                            //��������
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
                    // TODO �Զ����ɵ� catch ��
                    e.printStackTrace();
                }
            }

        } catch (UnknownHostException e) {
            System.out.println("�������˳�");
            try {
                socket.close();
            } catch (IOException e1) {
                // TODO �Զ����ɵ� catch ��
                e1.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println("�������˳�");
            try {
                if(socket!=null)
                    socket.close();
            } catch (IOException e1) {
                // TODO �Զ����ɵ� catch ��
                e1.printStackTrace();
            }
            SnakeJPanel.isMorePerson = false;
            SnakeJPanel.isGameStart = false;
            //JOptionPane.showMessageDialog(null,"ʧȥ���� �����½���������","�������쳣",JOptionPane.ERROR_MESSAGE);//��ʾ��
        }


    }
}
