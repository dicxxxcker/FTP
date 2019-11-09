import FileManage.FileManager;

import java.io.File;


public class CodeTest {
    public static void main(String[] args){
        try {
            FileManager manager = new FileManager(new File("PathTree.xml"));

        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
