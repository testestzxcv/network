package http;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleHttpServer {
	
	private static final int PORT = 8088;

	public static void main(String[] args) {

		ServerSocket serverSocket = null;

		try {
			// 1. Create Server Socket
			serverSocket = new ServerSocket();	// 서버 소켓 만들기
			   
			// 2. Bind
			String localhost = InetAddress.getLocalHost().getHostAddress();	// 호스트에 ip 주소저장후 소켓에 붙인다.
//			System.out.println("localhost === " + localhost);
			serverSocket.bind( new InetSocketAddress( localhost, PORT ) );	// bind는 서버소켓에다 붙이는 것이다.
			consoleLog("bind " + localhost + ":" + PORT);

			while (true) {
				// 3. Wait for connecting ( accept )
				Socket socket = serverSocket.accept();	// 접속 시도가 있을때까지 대기, cpu를 안쓰고 있으니 무한루프로 돌려도 된다, (블락킹)
				System.out.println("socket === "+socket);
				// 4. Delegate Processing Request
				new RequestHandler(socket).start();	// 생성된 소켓을 스레드에 넘겨서 통신을 한다.
			}

		} catch (IOException ex) {
			consoleLog("error:" + ex);
		} finally {
			// 5. clean-up
			try {
				if (serverSocket != null && serverSocket.isClosed() == false) {
					serverSocket.close();
				}
			} catch (IOException ex) {
				consoleLog("error:" + ex);
			}
		}
	}

	public static void consoleLog(String message) {
		System.out.println("[HttpServer#" + Thread.currentThread().getId()  + "] " + message);
	}
}