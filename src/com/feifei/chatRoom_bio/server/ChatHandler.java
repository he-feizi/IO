package com.feifei.chatRoom_bio.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * @ClassName ChatHandler
 * @date 2021-04-12 21:24
 * @Version 1.0
 * @Description TODO
 */
public class ChatHandler implements Runnable{

    private ChatServer server;
    private Socket socket;

    public ChatHandler(ChatServer server, Socket socket) {
        this.server = server;
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            // 存储新上线用户
            server.addClient(socket);
            // 读取用户发送的消息
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String msg = null;
            while ((msg = reader.readLine()) != null) {
                String fwdMsg = "客户端【" + socket.getPort() + "】：" + msg;
                System.out.println(fwdMsg);

                // 将消息转发给聊天室在线的其他用户
                server.fowardMessage(socket, fwdMsg + "\n");

                // 检查用户是否准备退出
                if (server.readyToQuit(msg)) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                server.removeClient(socket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
