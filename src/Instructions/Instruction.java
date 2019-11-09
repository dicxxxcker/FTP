package Instructions;

import java.util.HashMap;
import java.util.function.Consumer;

public class Instruction {
    public final static HashMap<String, Consumer<String[]>> instructionMapping = new HashMap<>();
    static {
        instructionMapping.put("get", new Consumer<String[]>() {
            @Override
            public void accept(String[] strings) {

            }
        });
    }

    String opcode;
    String[] params;

    public Instruction(String instruction) {

    }
}
