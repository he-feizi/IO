package com.feifei.socketDemo;

import java.io.*;
import java.net.Socket;

/**
 * @ClassName Client
 * @date 2021-04-08 11:39
 * @Version 1.0
 * @Description 客户端端口
 */
public class Client {
    public static void main(String[] args) {
        final String DEFAULT_SERVER_HOST = "127.0.0.1";
        final int DEFAULT_SERVER_PORT = 8888;
        final String QUIT = "quit";
        Socket socket = null;
        BufferedWriter writer = null;

        try {
            // 创建socket
            socket = new Socket(DEFAULT_SERVER_HOST, DEFAULT_SERVER_PORT);

            // 创建IO流
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );
             writer = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream())
            );

            // 等待用户输入信息
            BufferedReader consoleReader = new BufferedReader(
                    new InputStreamReader(System.in)
            );
            while (true) {
                String input = consoleReader.readLine();

                // 发送消息给服务器
                writer.write(input + "\n");
                writer.flush();

                // 读取服务器返回的消息
                String msg = reader.readLine();
                System.out.println(msg);

                // 查看用户是否退出
                if (QUIT.equals(input)) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                    System.out.println("关闭socket");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
