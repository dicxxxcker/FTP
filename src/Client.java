import org.apache.commons.net.ftp.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class Client {

    public static void main(String[] args) {
        FTPClient client = new FTPClient();
        try {
            Socket remoteSc = new Socket("localhost", 9102);
            InputStream in = remoteSc.getInputStream();
            OutputStream out = remoteSc.getOutputStream();
            OutputStreamWriter ouw = new OutputStreamWriter(out);
            Writer wr = new BufferedWriter(ouw);
            wr.write("hello server!");
            wr.flush();
            Scanner sc = new Scanner(System.in);
            while(true){
                String message = sc.nextLine();
                wr.write(message);
                wr.write("\n");
                wr.flush();
                ouw.flush();
                out.flush();

                if(message.equals("exit"))
                    break;
            }

            remoteSc.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
