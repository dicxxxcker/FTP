import org.apache.commons.net.ftp.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.io.*;

public class Main {

    public static void main(String[] args) {




        FTPClient client = new FTPClient();
        try {
            ServerSocket server = new ServerSocket(9102);
            ServerSocket dataTransform = new ServerSocket(8102);
            while(true){
                Socket accept = server.accept();
                Socket dataAccept = dataTransform.accept();
                ServerThread service = new ServerThread();
                service.setRemoteSocket(accept);
                service.setDataSocket(dataAccept);
                new Thread(service).start();
            }
            /*
            Socket accept = server.accept();
            InputStream in = accept.getInputStream();
            InputStreamReader isr = new InputStreamReader(in);
            BufferedReader br = new BufferedReader(isr);
            String s = null;

            while((s=br.readLine())!=null){
                System.out.println("get:"+s);
            }
            //System.out.println("Hello World!");

             */
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
