import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Date;

public class Application {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        while (true){
            System.out.println("Waiting for connection...\n");
            Socket socket = serverSocket.accept();
            System.out.println("Get connection.\n");
            try(InputStream input = socket.getInputStream(); OutputStream out = socket.getOutputStream()){
                System.out.println("Reading request...\n");
                byte[] request = Utils.readRequest(input);
                System.out.println(new String(request, "US-ASCII") + "\n");
                System.out.println("Sending response...\n");
                System.out.println(new String(Utils.makeResponse("<b>" + new Date().toString() + "</b>"), "US-ASCII"));
                out.write(Utils.makeResponse("<b>" + new Date().toString() + "</b>"));
                System.out.println("Response sent.\n");
            } finally {
               socket.close();
            }
        }
    }
}
