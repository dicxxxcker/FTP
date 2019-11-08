package FileManage;

public class VirtualFile {

    private String fileName;
    private String virtualPath;
    private String realPath;

    public VirtualFile(String fileName, String virtualPath, String realPath) {
        this.fileName = fileName;
        this.virtualPath = virtualPath;
        this.realPath = realPath;
    }


}
