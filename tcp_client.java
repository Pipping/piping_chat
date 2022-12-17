import java.io.*;
import java.net.*;
import java.nio.channels.ScatteringByteChannel;
import java.util.Scanner;




class Cli_reader extends Thread {
	Socket connecting_socket;
	public Cli_reader(Socket cs){
		this.connecting_socket=cs;
	}
	@Override
    public void run(){
        while(true){
            try {
				BufferedReader in_f_server=new BufferedReader(new InputStreamReader(connecting_socket.getInputStream()));
            	String client_string=in_f_server.readLine();
            	System.out.println(client_string);
			} catch (Exception e) {
				// TODO: handle exception
			}
        }
    }
}
class Cli_sender extends Thread{
	Socket connecting_socket;
	String user_name_cli;
	public Cli_sender(Socket cs,String un){
		this.connecting_socket=cs;
		this.user_name_cli=un;
	}
	@Override
    public void run(){
        while(true){
            try {
				DataOutputStream o_t_server=new DataOutputStream(connecting_socket.getOutputStream());
            BufferedReader resp_buff=new BufferedReader(new InputStreamReader(System.in));
            String response=resp_buff.readLine()+'\n';
            o_t_server.writeBytes(user_name_cli+':'+response);
			} catch (Exception e) {
				// TODO: handle exception
			}
        }
    }
}
class tcp_client {
public static void main(String argv[])throws Exception{
	String port=argv[1];
	String ip=argv[0];
	System.out.println("enter your username ");
    BufferedReader uinp=new BufferedReader(new InputStreamReader(System.in));
	String user_name=uinp.readLine();
	Socket client_socket=new Socket(ip, Integer.parseInt(port));
		if(client_socket.isConnected()){
		System.out.print("connected to the server\n");
		}
		Cli_reader cli_read=new Cli_reader(client_socket);
		Cli_sender cli_send=new Cli_sender(client_socket,user_name);
		
		cli_read.start();
		cli_send.start();
		
	
}

}