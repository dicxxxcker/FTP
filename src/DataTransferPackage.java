public class DataTransferPackage {

    private int dataLength; // 数据包中数据长度，两个字节
    private byte[] databuff; // 数据包中数据，meici最大不超过2048字节

    public final static byte[] EOF = new byte[]{'E', 'O','F'};

    public DataTransferPackage() {
        dataLength = 0;
        databuff = new byte[2048];
    }

    public byte[] getPackageData() {
        byte[] pData = new byte[dataLength];
        // end comment
        System.arraycopy(databuff, 0, pData, 0, dataLength);
        return pData;
    }

    public void setData(byte[] data, int bsize) {
        dataLength = bsize;
        for(int i=0; i<databuff.length; i++) {
            if(i<bsize) {
                databuff[i] = data[i];
            } else {
                databuff[i] = ' ';
            }
        }
    }
}
