import sun.plugin2.util.SystemUtil;

import java.io.*;
import java.net.Socket;

public class ServerThread implements Runnable{
    //控制端口
    Socket remoteSocket;
    //数据端口
    Socket dataSocket;
    public void setRemoteSocket(Socket remoteSocket) {
        this.remoteSocket= remoteSocket;
    }

    public void setDataSocket(Socket dataSocket) {
        this.dataSocket = dataSocket;
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
                    System.out.println("UserName: "+userName);
                    break;
                }
            }

            wr.write("Enter Your Password: (it's okay to be empty)\n");
            wr.flush();
            String password;
            while(true) {
                if(br.ready()) {
                    password = br.readLine();
                    System.out.println("Password: "+password);
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


            ObjectInputStream ois = new ObjectInputStream(in);
            System.out.println(ois.readUTF());
            ois.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
