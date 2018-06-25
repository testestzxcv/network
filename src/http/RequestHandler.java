package http;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;

public class RequestHandler extends Thread {
	private static final String DOCUMENT_ROOT = "./webapp";

	private Socket socket;

	public RequestHandler(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			// logging Remote Host IP Address & Port
			InetSocketAddress inetSocketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
			System.out.println("inetsocket === " + inetSocketAddress);
			consoleLog("connected from " + inetSocketAddress.getAddress().getHostAddress() + ":"
					+ inetSocketAddress.getPort());

			// get IOStream
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));	// 소켓에서, 바이트 단위로 읽어서, 버퍼로 한줄씩 읽는다.
			System.out.println("br === "+br);
			OutputStream os = socket.getOutputStream();	// 바이트 단위 쓰기
			System.out.println("os === "+os);
			
			
			
			//// 데이터는 라인단위로 온다.
			
			String request = null;
			while(true) {
				String line = br.readLine();
				System.out.println("line === "+line);
				if(line == null || "".equals(line)) {
					break;
				}
				
				if(request == null) {
					request = line;
					System.out.println("이거 항상 null 아냐?");
					break;
				}
				
			}
			
			consoleLog(request);
			
			//
			String[] tokens = request.split(" ");
			responseStaticResource(os, tokens[1], tokens[2]);
			
			File file = new File( "./webapp" + tokens[1] );
			System.out.println("file === " + file);
			Path path = file.toPath();
			System.out.println("path === " + path);
			byte[] body = Files.readAllBytes( path );
			System.out.println("body === " + body);
			//
			
			// 예제 응답입니다.
			// 서버 시작과 테스트를 마친 후, 주석 처리 합니다.
			os.write( "HTTP/1.1 200 OK\r\n".getBytes( "UTF-8" ) );
			os.write( "Content-Type:text/html; charset=utf-8\r\n".getBytes( "UTF-8" ) );
			os.write( "\r\n".getBytes() );
//			os.write( "<h1>이 페이지가 잘 보이면 실습과제 SimpleHttpServer를 시작할 준비가 된 것입니다.</h1>".getBytes( "UTF-8" ) );
			//
			os.write(body);
			//
		} catch ( Exception ex ) {
			consoleLog( "error:" + ex );
		} finally {
			// clean-up
			try {
				if ( socket != null && socket.isClosed() == false ) {
					socket.close();
				}
			} catch ( IOException ex)  {
				consoleLog( "error:" + ex );
			}
		}
	}

	private void consoleLog(String message) {
		System.out.println("[RequestHandler#" + getId() + "] " + message);
	}
	
	//
	private void responseStaticResource(OutputStream outputStream, String url, String protocol) throws IOException{
		try {
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	//
}