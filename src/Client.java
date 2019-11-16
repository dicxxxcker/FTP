import Instructions.Instruction;
import Instructions.InstructionFactory;
import org.apache.commons.net.ftp.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class Client {

    public static void main(String[] args) {
        try {
            //控制端口
            Socket ctrlSocket = new Socket("localhost", 9102);
            Socket dataSocket = new Socket("localhost",8102);
            OutputStream out = ctrlSocket.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(out);
            //OutputStreamWriter ouw = new OutputStreamWriter(out);
            //Writer wr = new BufferedWriter(ouw);
            //wr.write("hello server!");
            //wr.flush();


            Scanner sc = new Scanner(System.in);
            int i = 2;
            while(i>0){
                String message = sc.nextLine();
                oos.writeUTF(message);
                //wr.write("\n");
                oos.flush();
                //ouw.flush();
                //out.flush();

                if(message.equals("exit"))
                    break;
                i--;
            }



            //final ObjectOutputStream oos = new ObjectOutputStream(out);
            //final ObjectOutputStream dataOs = new ObjectOutputStream(dataSocket.getOutputStream());

            //操作过程 传输指令
            while(true){
                //String instruction = sc.nextLine();
                String instruction = sc.nextLine();
                //退出检查
                if(instruction.equals("exit"))
                    break;
                oos.writeUTF(instruction);
                oos.flush();
                //String[] params = str.trim().split("( )+");
                //传输控制信息给服务端做好准备 （转到lambada中进行通信）
                //oos.writeUTF(instruction);
                //oos.flush();
                //本地执行操作
                Thread clientThread = new Thread(InstructionFactory.generateInstruction(instruction,ctrlSocket,dataSocket,false));
                clientThread.start();
                //手动阻塞 因为IO不是线程安全的 结束一个任务后再开始一个任务
                while(true){
                if(!clientThread.isAlive())
                    break;
                }
            }


            ctrlSocket.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
