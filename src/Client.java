import Instructions.Instruction;
import Instructions.InstructionFactory;
import org.apache.commons.net.ftp.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class Client {

    public static void main(String[] args) {
        FTPClient client = new FTPClient();
        try {
            //控制端口
            Socket remoteSc = new Socket("localhost", 9102);
            Socket dataSocket = new Socket("localhost",8102);
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

            final ObjectOutputStream oos = new ObjectOutputStream(out);
            final ObjectOutputStream dataOs = new ObjectOutputStream(dataSocket.getOutputStream());

            //操作过程 传输指令
            while(true){
                //String instruction = sc.nextLine();
                String str = sc.nextLine();
                //退出检查
                if(str.equals("exit"))
                    break;
                String[] params = str.trim().split("( )+");
                //传输控制信息给服务端做好准备
                oos.writeUTF(str);
                oos.flush();
                //本地执行操作
                Thread local = new Thread(InstructionFactory.generateInstruction(str,remoteSc,dataSocket,false));
                local.start();
                //手动阻塞 因为IO不是线程安全的 结束一个任务后再开始一个任务
                while(true){
                if(!local.isAlive())
                    break;
                }
            }

            //文件IO实验
            oos.writeUTF(sc.nextLine());
            oos.flush();

            remoteSc.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
