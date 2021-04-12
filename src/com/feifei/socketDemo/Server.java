package com.feifei.socketDemo;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @ClassName Server
 * @date 2021-04-08 11:25
 * @Version 1.0
 * @Description 服务器端口
 */
public class Server {

    public static void main(String[] args) {
        final int DEFAULT_PORT = 8888;
        final String QUIT = "quit";
        ServerSocket serverSocket = null;

        try {
            // 绑定监听端口
            serverSocket = new ServerSocket(DEFAULT_PORT);
            System.out.println("启动服务器，监听端口" + DEFAULT_PORT);

            while (true) {
                // 等待客户端连接，阻塞式等待
                Socket socket = serverSocket.accept();
                System.out.println("客户端【" + socket.getPort() + "】已连接");
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(socket.getInputStream())
                );
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream())
                );

                // 读取客户端发送的消息
                String msg = null;
                while ((msg = reader.readLine()) != null) {
                    System.out.println("客户端【" + socket.getPort() + "】：" + msg);

                    // 回复客户端发送的消息
                    writer.write("服务器：" + msg + "\n");
                    writer.flush();

                    // 查看客户端是否退出
                    if (QUIT.equals(msg)) {
                        System.out.println("客户端【" + socket.getPort() + "】：断开连接");
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    // 关闭 serverSocket
                    serverSocket.close();
                    System.out.println("关闭serverSocket");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
