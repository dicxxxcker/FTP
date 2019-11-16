package Instructions.ClientInstructions;

import Instructions.Instruction;

import java.io.*;

public class Get extends Instruction {
    @Override
    public void run() {
        try {
            System.out.println("instruction Get(Client) start!");
            System.out.println("1");
            //太神秘了
            //ObjectInputStream ois = new ObjectInputStream(ctrlSocket.getInputStream());
            //ObjectOutputStream oos = new ObjectOutputStream(ctrlSocket.getOutputStream());
            //须先进行命令是否合法的判断
            //oos.writeObject(this.params);
            //File file = (File)ois.readObject();


            System.out.println("1");
            // get file meta information
            ObjectInputStream input = new ObjectInputStream(dataSocket.getInputStream());
            System.out.println("Read FileName");
            String fileName = input.readUTF();
            System.out.println("FileName: " + fileName);
            int fileLength = (int)input.readLong(); // number of total bytes
            System.out.println("Length: " + fileLength);
            File file = new File("E:\\IDEAFile\\JAVA\\FTP\\FileTransformDir\\ClientFile"  + File.separator + fileName);
            BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(file));
            System.out.println("Received File Name = " + fileName);
            System.out.println("Received File size = " + fileLength/1024 + "KB");

            System.out.println("File Transfer(Client) Start!");
            // start to receive the content of the file and write them
            byte[] content = new byte[2048];
            int offset = 0;
            int numReadBytes = 0;
            while(offset < fileLength && (numReadBytes = input.read(content)) > 0) {
                output.write(content, 0, numReadBytes);
                //图形化需求 暂时忽略
                //float precent = 100.0f * ((float)offset)/((float)fileLength);
                //setProgress((int)precent);
                offset += numReadBytes;
            }
            System.out.println("numReadBytes = " + numReadBytes);
            if(offset < fileLength) {
                numReadBytes = input.read(content);
                System.out.println("numReadBytes = " + numReadBytes);
                System.out.println("File content error at server side");
            } else {
                System.out.println("File Receive Task has done correctly");
            }
            //setProgress(100);

            // tell client to close the socket now, we already receive the file successfully!!
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(dataSocket.getOutputStream()));
            bufferedWriter.write("DONE\r\n");
            bufferedWriter.flush();

            // close the file and socket
            output.close();
            dataSocket.close();


        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
