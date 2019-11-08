package FileManage;
import kotlin.jvm.Throws;

import java.io.File;
import java.util.*;

public class FileManager {
    PathTree root;

    FileManager(File xml) throws Exception{
        try {
            Scanner sc = new Scanner(xml);
            LinkedList<PathTree> stack = new LinkedList<>();
            List<String> list = new LinkedList<String>();
            while(sc.hasNextLine())
                list.add(sc.nextLine());
            PathTree cur = null;
            for(String node:list){
                for(int i=0;i<node.length();i++){
                    if(node.charAt(i)=='N'){
                        String dirName = null;
                        for(int j=i,left=0,right=0;j<node.length();j++){
                            if(node.charAt(i)=='"'){
                                if(left==right)
                                    left = i;
                                else {
                                    right = i;
                                    dirName = node.substring(i + 1, j);
                                    break;
                                }
                            }
                        }
                        if(dirName==null)
                            throw new Exception("initial file format error! label(dir) name property missing");
                        if(cur==null)
                            cur = new PathTree(null,"root");
                        else{
                            stack.push(cur);
                            PathTree child = new PathTree(cur,cur.dirPath+"/"+"dirName");
                            cur.dirs.add(child);
                            cur = child;
                        }
                        break;
                    }
                    if(node.charAt(i)=='/'){
                        if(stack.isEmpty())
                            throw new Exception("initial file format error! <> not match , <Node> expected");
                        else
                            cur = stack.pop();
                        break;
                    }
                }
            }

            if(!stack.isEmpty())
                throw new Exception("initial file format error! <> not match ,</Node> expected");
            this.root = cur;
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}


class PathTree{
    PathTree parentDir;
    String dirPath;
    List<PathTree> dirs;

    public PathTree(PathTree parentDir,String dirPath){
        this.parentDir = parentDir;
        this.dirPath = dirPath;
        dirs = new LinkedList<>();

    }


    PathTree(File xml) throws Exception{
        try {
            Scanner sc = new Scanner(xml);
            Stack<PathTree> stack = new LinkedList<>();
            List<String> list = new LinkedList<String>();
            for (sc.hasNextLine())
                list.add(sc.nextLine());
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}

