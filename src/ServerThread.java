import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerThread implements Runnable{
    Socket remoteSocket;

    public void setRemoteSocket(Socket remoteSocket) {
        this.remoteSocket= remoteSocket;
    }

    @Override
    public void run() {
        try {
            InputStream in = remoteSocket.getInputStream();
            InputStreamReader isr = new InputStreamReader(in);
            BufferedReader br = new BufferedReader(isr);
            String s = null;
            System.out.println("communication start");
            while (true) {

                if ((s = br.readLine()) != null) {
                    System.out.println("get:" + s);
                }
                //if(s.equals("exit"))
                //   break;
                if (s != null && s.equals("exit"))
                    break;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
