import Instructions.InstructionFactory;
import sun.plugin2.util.SystemUtil;

import java.io.*;
import java.net.Socket;

public class ServerThread implements Runnable{
    //控制端口
    protected Socket ctrlSocket;
    //数据端口
    protected Socket dataSocket;
    //当前文件路径
    protected String currentPath ="root";
    //日志
    BufferedWriter logging;

    public void setCtrlSocket(Socket ctrlSocket) {
        this.ctrlSocket= ctrlSocket;
    }

    public void setDataSocket(Socket dataSocket) {
        this.dataSocket = dataSocket;
    }

    @Override
    public void run() {
        try {
            InputStream in = ctrlSocket.getInputStream();
            InputStreamReader isr = new InputStreamReader(in);
            ObjectInputStream read = new ObjectInputStream(in);
            String s = null;
            //以后应加入日志功能
            System.out.println("a new connection from"+ ctrlSocket.getRemoteSocketAddress() +" established!");

            OutputStream out = ctrlSocket.getOutputStream();
            OutputStreamWriter ouw = new OutputStreamWriter(out);
            Writer wr = new BufferedWriter(ouw);

            //login
            //wr.write("Enter Your UserName: (it's okay to be empty)\n");
            //读取用户名
            wr.flush();
            String userName = read.readUTF();
            System.out.println("UserName: "+userName);

            //wr.write("Enter Your Password: (it's okay to be empty)\n");
            //读取密码
            wr.flush();
            String password = read.readUTF();
            System.out.println("Password: "+password);


            while (true) {
                String instruction = read.readUTF();
                //退出检查
                System.out.println("instruction "+instruction+" get!");
                if(instruction.equals("exit"))
                    break;
                Thread serverThread = new Thread(InstructionFactory.generateInstruction(instruction,ctrlSocket,dataSocket,true));
                serverThread.start();
                //手动阻塞 因为IO不是线程安全的 结束一个任务后再开始一个任务
                while(true){
                    if(!serverThread.isAlive())
                        break;
                }
            }

            //测试代码
            ObjectInputStream ois = new ObjectInputStream(in);
            System.out.println(ois.readUTF());
            ois.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
