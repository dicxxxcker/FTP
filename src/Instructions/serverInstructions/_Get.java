package Instructions.serverInstructions;

import Backup.FileDataPackage;
import Instructions.Instruction;

import java.io.*;
import java.util.concurrent.TimeUnit;

public class _Get extends Instruction {
    @Override
    public void run() {
        try {
            File transFile = new File("E:\\IDEAFile\\JAVA\\FTP\\FileTransformDir\\ServerFile\\UserTable.txt");
            long length = transFile.length();
            if (length > Integer.MAX_VALUE) {
                throw new IOException("Could not completely read file " + transFile.getName() + " as it is too long (" + length + " bytes, max supported " + Integer.MAX_VALUE + ")");
            }

            // Create the byte array to hold the file data
            dataSocket.setSoLinger(true, 60);
            ObjectOutputStream dout = new ObjectOutputStream(dataSocket.getOutputStream());
            TimeUnit.SECONDS.sleep(3);
            // now we start to send the file meta info.
            dout.writeUTF(transFile.getName());
            dout.writeLong(length);
            dout.flush();
            // end comment
            System.out.println("Transfer start!");
            FileDataPackage pData = new FileDataPackage();
            DataInputStream is = new DataInputStream(new FileInputStream(transFile));
            byte[] bytes = new byte[2048];

            // Read in the bytes
            int offset = 0;
            int numRead = 0;
            int fsize = (int)length;
            while (offset < fsize && (numRead=is.read(bytes, 0, bytes.length)) >= 0) {
                pData.setData(bytes, numRead);
                dout.write(pData.getPackageData(), 0, pData.getPackageData().length);
                dout.flush();
                offset += numRead;
                //图形化操作 可忽略
                //float percent = 100.0f * ((float)offset)/((float)fsize);
                //setProgress((int)percent);
            }
            System.out.println("total send bytes = " + offset);
            // Ensure all the bytes have been read in
            if (offset < fsize) {
                throw new IOException("Could not completely transfer file " + transFile.getName());
            }
            dataSocket.shutdownOutput();
            BufferedInputStream streamReader = new BufferedInputStream(dataSocket.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(streamReader));
            String doneMsg = bufferedReader.readLine();
            if("DONE".equals(doneMsg)) {
                System.out.println("File Transfer Success!");
            }
            // Close the file input stream
            // dout.close();
            dataSocket.close();
            is.close();
            System.out.println("close it now......");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
