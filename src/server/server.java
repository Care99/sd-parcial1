package server;
import java.net.*;
import java.util.ArrayList;
import java.io.*;
import org.json.*;

public class server 
{
	ServerSocket serverSocket   = null;
    Socket communicationSocket  = null;
    BufferedReader in          = null;
    BufferedWriter out        = null;
    ArrayList<JSONObject> data  = new ArrayList<>();
    public void StartServer(int port) throws IOException 
    {
        try
        {
            serverSocket        = new ServerSocket(port);
            System.out.println("Server started. Waiting for client...");
            communicationSocket = serverSocket.accept();
            in                  = new BufferedReader(new InputStreamReader(communicationSocket.getInputStream(),"UTF-8"));
            out                 = new BufferedWriter(new OutputStreamWriter(communicationSocket.getOutputStream(),"UTF-8"));
            String inputLine, outputLine = "";

            while ((inputLine = in.readLine())!= null) 
            {
            	System.out.println(inputLine);
                JSONObject object = new JSONObject(inputLine);
                
                int temp = 0;
                switch (object.getInt("op")) 
                {
                case 1:
                	data.add(object.getJSONObject("data"));
                	outputLine = "{\"result\": \"added\"}";
                	break;
                case 2:
                	boolean exist = false;
                	for (JSONObject datapoint : data) 
                    {
                		if(datapoint.getString("ciudad").compareTo(object.getJSONObject("data").getString("ciudad")) == 0) 
                        {
                			temp = datapoint.getInt("temperatura");
                			exist = true;
                		}
                	}
                	outputLine = exist ? "{\"result\": "+temp+"}" : "{\"error\": \"ciudad no existe\"}";
                	break;
                case 3:
                	int count = 0;
                	for (JSONObject datapoint : data) {
                		if(datapoint.getString("fecha").compareTo(object.getJSONObject("data").getString("fecha")) == 0) 
                        {
                			temp += datapoint.getInt("temperatura");
                			count++;
                		}
                	}
                	outputLine = "{\"result\": "+temp/count+"}";
                	break;
                default:
                	outputLine = "{\"error\": \"unknown op type\"}";
                }
                out.write(outputLine);
            }
            out.close();
            in.close();
            communicationSocket.close();
            System.out.println("Finalizando Hilo");
        }
        catch(IOException ioException)
        {
            ioException.printStackTrace();
        }
    }
    
    public static void main(String[] args) throws IOException 
    {
    	System.out.println("Sistemas distribuidos - Primera parcial");
		System.out.println("Nombre:Cesar");
		System.out.println("Apellido:Rodas");
		System.out.println("Edad:23");
    	server tms = new server();
    	tms.StartServer(8088);
    }
}