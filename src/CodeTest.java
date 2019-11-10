import FileManage.FileManager;

import java.io.File;


public class CodeTest {
    public static void main(String[] args){

        String strs[] = "1  22  33 3 4    4 ".split("( )+");
        for(String s:strs)
            System.out.println(s);
        System.out.println(strs[0]);
        try {
            FileManager manager = new FileManager(new File("PathTree.xml"));

        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
