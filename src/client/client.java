package client;
import java.net.*;
import java.io.*;
import org.json.*;

public class client 
{

	public void StartClient(String address,int port) throws IOException
	{
		Socket unSocket 		= null;
        BufferedWriter out 	= null;
        BufferedReader in 		= null;

        try 
		{
            unSocket 	= new Socket(address, port);
            out 		= new BufferedWriter(new OutputStreamWriter(unSocket.getOutputStream(),"UTF-8"));
            in 			= new BufferedReader(new InputStreamReader(unSocket.getInputStream(),"UTF-8"));
        }
        catch (IOException e) 
		{
            e.printStackTrace();
        }

        
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String fromServer;

        while ((fromServer = in.readLine()) != null) {
            System.out.println("Servidor: " + fromServer);
            
        	JSONObject data = new JSONObject();
        	JSONObject jo = new JSONObject();
        	System.out.println("Operación:");
        	int op = Integer.valueOf(stdIn.readLine());
        	jo.put("op", op);
        	
        	switch (op) {
        	case 1:
            	System.out.println("id_estacion: ");
            	data.put("id_estacion", stdIn.readLine());
            	System.out.println("ciudad: ");
            	data.put("ciudad", stdIn.readLine());
            	System.out.println("porcentaje_humedad: ");
            	data.put("porcentaje_humedad", stdIn.readLine());
            	System.out.println("temperatura (int): ");
            	data.put("temperatura", Integer.valueOf(stdIn.readLine()));
            	System.out.println("velocidad_viento: ");
            	data.put("velocidad_viento", stdIn.readLine());
            	System.out.println("fecha: ");
            	data.put("fecha", stdIn.readLine());
            	System.out.println("hora: ");
            	data.put("hora", stdIn.readLine());
                jo.put("data", data);
            	out.write(jo.toString());
            	break;
        	case 2:
            	System.out.println("ciudad: ");
        		data.put("ciudad", stdIn.readLine());
				System.out.println("temperatura: ");
				data.put("temperatura",stdIn.readLine());
                jo.put("data", Integer.valueOf(stdIn.readLine()));
            	out.write(jo.toString());
        		break;
        	default:
        		System.out.println("Unknown operation");
        	}
        }

        out.close();
        in.close();
        stdIn.close();
        unSocket.close();
    }
    public static void main(String[] args) throws IOException 
	{
		System.out.println("Sistemas distribuidos - Primera parcial");
		System.out.println("Nombre:Cesar");
		System.out.println("Apellido:Rodas");
		System.out.println("Edad:23");
		client tms = new client();
		tms.StartClient("localhost", 8088);
	}   
}
