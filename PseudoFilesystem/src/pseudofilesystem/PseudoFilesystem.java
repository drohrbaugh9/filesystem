package pseudofilesystem;

import java.util.Arrays;

public class PseudoFilesystem {
    
    static String[][] drive = new String[256][3];
    
    static final String NORMAL_FILE = "0";
    static final String DIRECTORY = "1";

    public static void main(String[] args) {
        initDrive();
        
        System.out.println("$ ls");
        ls();
        
        System.out.println("$ cat file2");
        cat("file2");
        
        System.out.println("$ cat dir1");
        cat("dir1");
        
        /*for (int i = 0; i < drive.length; i++) {
            System.out.println(i + ": " + Arrays.toString(drive[i]));
        }*/
    }
    
    public static void cat(String filename) {
        for (int i = 0; i < drive.length; i++) {
            if (drive[i][0].equals(filename)) {
                if (drive[i][1].equals(DIRECTORY)) { 
                    System.out.println("cat: " + filename + ": Is a directory");
                    return;
                }
                System.out.println(drive[i][2]);
                i = drive.length;
            }
        }
    }
    
    public static void write(int index, String data) {
        drive[index][2] = data;
    }
    
    public static void ls() { 
        for (String[] i : drive) {
            if (i[0] != null && i[0].length() > 0) System.out.println(i[0]);
        }
    }
    
    public static void mkdir(String dirName) {
        for (int i = 0; i < drive.length; i++) {
            if (drive[i][0] == null) {
                drive[i][0] = dirName;
                drive[i][1] = DIRECTORY;
                i = drive.length;
            }
        }
    }
    
    public static void touch(String filename) {
        for (int i = 0; i < drive.length; i++) {
            if (drive[i][0] == null) {
                drive[i][0] = filename;
                drive[i][1] = NORMAL_FILE;
                i = drive.length;
            }
        }
    }
    
    private static void initDrive() {
        touch("file1");
        touch("file2");
        touch("file3");
        
        mkdir("dir1");
        
        write(1, "text in file2");
    }
}
