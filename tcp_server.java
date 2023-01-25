import java.io.*;
import java.net.*;
import java.util.ArrayList;

class User {
    public Socket socket;
    public String user_name;
    public String data;

    public User(Socket sk, String un) {
        this.socket = sk;
        this.user_name = un;
    }
}

class Server_reader extends Thread {
    static ArrayList<Socket> clients=new ArrayList<Socket>();
    public Socket client;

    public Server_reader() {
        
    }

    public void run() {
        while (true) {
            try {
                BufferedReader in_f_server = new BufferedReader(new InputStreamReader(client.getInputStream()));
                String client_string = in_f_server.readLine();

                System.out.println(client_string);
                if (client_string.substring(client_string.length() - 4, client_string.length()).equals("null")) {
                    System.out.println("someone disconnected ,erasing it's existense");
                    client.close();
                    // cleaning the clients array
                    for (int i = 0; i < clients.size(); ++i) {
                        if (clients.get(i).isClosed()) {
                            clients.remove(i);
                        }
                    }
                    // dis part is questionable atm
                    continue;
                }

                System.out.println("clients size is:" + clients.size());
                for (int i = 0; i < clients.size(); ++i) {
                    // to prevent echoing of the users' own input
                    if (client.getInetAddress() != clients.get(i).getInetAddress()) {
                        System.out.println("forwarding messages");
                        DataOutputStream o_t_server = new DataOutputStream(clients.get(i).getOutputStream());
                        o_t_server.writeBytes(client_string + '\n');
                    }
                }
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }
}

class tcp_server {
    public static void main(String argv[]) throws Exception {

        ServerSocket server_socket = new ServerSocket(6969);
        //ArrayList<Socket> users = new ArrayList<Socket>();
        
        while (true) {
            Server_reader sr_thread = new Server_reader();
            Socket connecting_socket = server_socket.accept();
            
            Server_reader.clients.add(connecting_socket);
            sr_thread.client = connecting_socket;
            //sr_thread.join();
            //users.add(connecting_socket);
            // System.out.println("user size::"+users.size());

            // Server_sender ss_thread=new
            // Server_sender(connecting_socket,user_name_server,message);
            // ss_thread.start();
            sr_thread.start();

        }

    }

}