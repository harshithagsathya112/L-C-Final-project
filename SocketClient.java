import java.io.*;
import java.net.*;

public class SocketClient {
    public static void main(String[] args) {
        try {
            
            Socket socket = new Socket("localhost", 12345);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Send query to server
            out.println("SELECT * FROM employees");

            // Read response from server
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String response;
            while ((response = in.readLine()) != null) {
                System.out.println("Server response: " + response);
            }

            // Close connections
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
