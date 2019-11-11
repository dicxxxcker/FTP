package Instructions;

import java.net.Socket;
import java.util.HashMap;
import java.util.function.Consumer;

public class InstructionFactory {
    //服务端指令映射
    public static final HashMap<String,Class<Instruction>> serverMapping = new HashMap<>();
    //客户端指令映射
    public static final HashMap<String,Class<Instruction>> clientMapping = new HashMap<>();

    static{}
    static{}
    public static Instruction generateInstruction(String s, Socket ctrlSocket, Socket dataSocket,boolean isServer) throws Exception{
        String[] params = s.trim().split("( )+");
        if(isServer){
            if(serverMapping.containsKey(params[0])){
                Instruction operation = serverMapping.get(params[0]).newInstance();
                operation.setCtrlSocket(ctrlSocket);
                operation.setDataSocket(dataSocket);
                operation.setParams(params);
                return operation;
            }
            else
                throw new Exception("Instruction Not Found!");
        }
        else{
            if(clientMapping.containsKey(params[0])){
                Instruction operation = clientMapping.get(params[0]).newInstance();
                operation.setCtrlSocket(ctrlSocket);
                operation.setDataSocket(dataSocket);
                operation.setParams(params);
                return operation;
            }
            else
                throw new Exception("Instruction Not Found!");
        }
    }
}
