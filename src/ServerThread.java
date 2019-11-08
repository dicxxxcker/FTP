import java.io.*;
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
            //以后应加入日志功能
            System.out.println("a new connection from"+ remoteSocket.getRemoteSocketAddress() +" established!");

            OutputStream out = remoteSocket.getOutputStream();
            OutputStreamWriter ouw = new OutputStreamWriter(out);
            Writer wr = new BufferedWriter(ouw);

            //login
            wr.write("Enter Your UserName: (it's okay to be empty)\n");
            wr.flush();
            String userName;
            while(true) {
                if(br.ready()) {
                    userName = br.readLine();
                    break;
                }
            }

            wr.write("Enter Your Password: (it's okay to be empty)\n");
            wr.flush();
            String password;
            while(true) {
                if(br.ready()) {
                    password = br.readLine();
                    break;
                }
            }


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
