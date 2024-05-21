import java.io.*;
import java.net.*;
import java.sql.*;

public class SocketServer {
    public static void main(String[] args) {
        try {
            
            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("Server listening on port 12345...");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected: " + clientSocket.getInetAddress());

            // Establish MySQL connection
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/employees", "root@localhost", "Harshitha@555");
            Statement statement = connection.createStatement();

            // Read input from client
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String query = in.readLine();
            System.out.println("Received query from client: " + query);

            // Execute query
            ResultSet resultSet = statement.executeQuery(query);

            // Send results back to client
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            while (resultSet.next()) {
                String result = resultSet.getString(1); // Assuming first column contains strings
                out.println(result);
            }

            // Close connections
            resultSet.close();
            statement.close();
            connection.close();
            serverSocket.close();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}
