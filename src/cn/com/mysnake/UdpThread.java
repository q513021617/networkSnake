package cn.com.mysnake;

import cn.com.mysnake.view.JoinGameDialog;
import cn.com.mysnake.view.SnakeJPanel;

import java.io.IOException;
import java.net.*;
import java.util.TreeSet;

public class UdpThread extends Thread{

    private DatagramSocket socketSend;
    private DatagramSocket socketRecv;
    private static final int SNAKEPORT = 50000;
    private static TreeSet<String> list;
    private boolean isLocalOpen = false;

    public static void clearList()
    {
        list.clear();
    }

    public UdpThread()
    {
        list = new TreeSet<String>();
        try {
            this.socketSend = new DatagramSocket();
        } catch (SocketException e) {
            System.out.println("����UDP��������ʧ��");
            if(socketSend!=null)
                socketSend.close();
        }


        try {
            socketRecv = new DatagramSocket(SNAKEPORT);
        } catch (SocketException e) {
            System.out.println("����ͬһ̨�����ϴ򿪶��Ӧ��,����UDP��������ʧ��");
            isLocalOpen = true; //��ͬһ̨̨�����ϴ�
            if(socketRecv != null)
                socketRecv.close();
        }

    }

    public void run()
    {
        //ʹ�������ڲ��࿪��udp�����߳�
        new Thread()
        {
            public void run()
            {
                if(!isLocalOpen)
                {
                    byte[] buf = new byte[1024];
                    DatagramPacket packet = new DatagramPacket(buf, buf.length);
                    while(true)
                    {
                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                        try {
                            socketRecv.receive(packet);
                        } catch (IOException e) {
                            System.out.println("udp�����쳣");
                        }

                        //������װ�뼯����
                        list.add(new String(buf,0,packet.getLength()));
                        JoinGameDialog.updateList(list);
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
                else//����Ǳ��ش򿪶��������ʹ�ñ���
                {
                    while(true)
                    {
                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }

                        String data = null;
                        try {
                            data = InetAddress.getLocalHost().toString();
                        } catch (UnknownHostException e) {
                            e.printStackTrace();
                        }
                        String [] strs = data.split("/");
                        if(strs.length > 0)
                        {
                            list.add(strs[1]);
                        }
                        JoinGameDialog.updateList(list);
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }.start();

        while(true)
        {
            if(SnakeJPanel.isMorePerson)
            {
                try {
                    //����Ƿ������� ���͹㲥��  �����ͨ��
                    if(SnakeJPanel.getSnake().getIsServerSnake())
                    {
                        String data = InetAddress.getLocalHost().toString();
                        String [] strs = data.split("/");
                        if(strs.length > 0)
                        {
                            byte[] datas  = strs[1].getBytes();

                            String [] strArr = strs[1].split("\\.");
                            String addr = strArr[0]+"."+strArr[1]+"."+strArr[2]+".255";
                            DatagramPacket datagramPacket = new DatagramPacket(datas, datas.length,InetAddress.getByName(addr),SNAKEPORT);
                            socketSend.send(datagramPacket);
                        }
                    }
                } catch (IOException e) {
                    System.out.println("UDP�������");
                    if(socketSend!=null)
                        socketSend.close();
                }
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e)
            {
                if(socketSend!=null)
                    socketSend.close();
                e.printStackTrace();
            }
        }
    }


}
