package test;

import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {
	public static void main(String argv[]) throws Exception
    {
        // 서버소켓을 생성한다. 웹서버는 기본적으로 80번 포트를 사용한다.
        ServerSocket listenSocket = new ServerSocket(80);
        System.out.println("WebServer Socket Created");
 
        Socket connectionSocket;
        ServerThread serverThread;
 
        // 순환을 돌면서 클라이언트의 접속을 받는다.
        // accept()는 Blocking 메서드이다.
        while((connectionSocket = listenSocket.accept()) != null)
        {
            // 서버 쓰레드를 생성하여 실행한다.
            serverThread = new ServerThread(connectionSocket);
            serverThread.start();
        }
    }
}
