import java.net.*;
import java.io.*;

public class DemoServer {
    public DemoServer() {
        new Thread(new Runnable() {
            public void run() {
                try( ServerSocket welcomeSocket = new ServerSocket(1111);) {
                    System.out.println("Please Wait...");
                    Socket soc = welcomeSocket.accept();
                    System.out.println("Connection Complete");
                    BufferedReader in = new BufferedReader(new InputStreamReader(soc.getInputStream()));
                    String txt = in.readLine();
                    PrintWriter output = new PrintWriter(soc.getOutputStream(), true);
                    output.println("Server says: " + txt);
                }
                catch (BindException e){
                    throw new RuntimeException(e);
                }
                catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
    public static void main(String[] args) {
        new DemoServer();
    }
}
