import java.io.*;
import java.net.*;
import java.util.ArrayList;

class User{
	public Socket socket;
	public String user_name;
    public String data;
	public User(Socket sk, String un){
		this.socket=sk;
		this.user_name=un;
	}
}




class Server_reader extends Thread {
	ArrayList<Socket> clients;
    Socket client;
    
	public Server_reader(Socket cs,ArrayList<Socket>cls){
		this.client=cs;
        
        this.clients=cls;
	}
    public void run(){
        while(true){
            try {
				BufferedReader in_f_server=new BufferedReader(new InputStreamReader(client.getInputStream()));
            	String client_string=in_f_server.readLine();
                
            	System.out.println(client_string);
                //to prevent echoing the users' own input back
                for(int i=0;i<clients.size();++i){
                    if(client.getInetAddress()!=clients.get(i).getInetAddress()){
                        DataOutputStream o_t_server=new DataOutputStream(clients.get(i).getOutputStream());
                        o_t_server.writeBytes(client_string+'\n');
                    }
                }
			} catch (Exception e) {
				// TODO: handle exception
			}
        }
    }
}
//below is depricated
class Server_sender extends Thread{
	ArrayList<Socket> clients;
    Socket client;
	String user_name_cli;
    String msg=null;
	public Server_sender(Socket cs,String un,String message){
		this.client=cs;
        //clients.add(cs);
		this.user_name_cli=un;
        this.msg=message;
	}
	//@Override
    public void run(){
       try {
        
        while(true){
           
            DataOutputStream o_t_server=new DataOutputStream(client.getOutputStream());
            o_t_server.writeBytes(user_name_cli+":"+msg+'\n');
            //BufferedReader resp_buff=new BufferedReader(new InputStreamReader(System.in));
            //String response=resp_buff.readLine()+'\n';
            
            
       }
       
      
    }
         catch (Exception e) {
        // TODO: handle exception
       }
    }
}

class tcp_server {
public static void main(String argv[])throws Exception{
    String user_name_server;
    BufferedReader uinp=new BufferedReader(new InputStreamReader(System.in));
    user_name_server=uinp.readLine();
    //String ip_str=argv[0];
    //InetAddress ip=InetAddress.getByName(ip_str);
	ServerSocket server_socket=new ServerSocket(6969);
    ArrayList<Socket> users=new ArrayList<Socket>();
    //System.out.println("server ip:"+server_socket.getInetAddress());
    while(true){
        Socket connecting_socket=server_socket.accept();
        users.add(connecting_socket);
        //System.out.println("user size::"+users.size());
        
        Server_reader sr_thread=new Server_reader(connecting_socket,users);
        //Server_sender ss_thread=new Server_sender(connecting_socket,user_name_server,message);
        //ss_thread.start();
        sr_thread.start();
        
    }
  
   
}

}