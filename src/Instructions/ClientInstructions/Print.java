package Instructions.ClientInstructions;
import Instructions.*;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Print extends Instruction{

    @Override
    public void run(){
        try {
            InputStream printStream = dataSocket.getInputStream();
            ObjectInputStream oos = new ObjectInputStream(printStream);
            for(int i=1;i<params.length;i++)
                System.out.print(params[i]+" ");
            System.out.println(" ");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
