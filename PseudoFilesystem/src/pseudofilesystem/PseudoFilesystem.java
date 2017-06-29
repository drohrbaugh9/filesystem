package pseudofilesystem;

import java.util.Arrays;

public class PseudoFilesystem {
    
    static String[][] drive = new String[256][3];
    static String[][] wdir;
    
    static final String NORMAL_FILE = "0";
    static final String DIRECTORY = "1";
    
    static String wd = "/";
    
    static boolean showPrompt = true;

    public static void main(String[] args) {
        initDrive();
        
        ls();
        
        cat("file2");
        
        cat("dir1");
        
        cat("emptyFile");
        
        rm("file1");
        
        ls();
        
        cd("dir1");
        
        pwd();
        
        cd("../");
        
        pwd();
        
        cd("..");
        
        pwd();
        
        ls("file2");
        
        ls("file5");
        
        ls("dir1");
        
        mkdir("dir2");
        
        rm("dir2");
        
        rm("thisFileShouldntExist");
        
        /**/
        System.out.println("");
        for (int i = 0; i < drive.length; i++) {
            if (drive[i][0] != null) System.out.println(i + ": " + Arrays.toString(drive[i]));
        }
        /**/
    }
    
    public static void cat(String filename) {
        if (showPrompt) System.out.println("$ cat " + filename);
        for (int i = 0; i < drive.length; i++) {
            if (drive[i][0].equals(filename)) {
                if (drive[i][1].equals(DIRECTORY)) { 
                    System.out.println("cat: " + filename + ": Is a directory");
                    return;
                }
                if (drive[i][2] == null || drive[i][2].equals("")) return;
                System.out.println(drive[i][2]);
                i = drive.length;
            }
        }
    }
    
    public static void cd(String dirName) {
        boolean found = false;
        
        if (showPrompt) System.out.println("$ cd " + dirName);
        
        if (dirName.charAt(dirName.length() -1) == '/') {
            dirName = dirName.substring(0, dirName.length() - 1);
        }
        
        if (dirName.equals(".")) return;
        if (dirName.equals("..")) {
            if (wd.equals("/")) return;
            wd = wd.substring(0, wd.length() - 1); // remove "/" at end of wd
            wd = wd.substring(0, wd.lastIndexOf('/') + 1);
            return;
        }
        
        for (int i = 0; i < drive.length; i++) {
            if (drive[i][0] != null && drive[i][0].equals(dirName)) {
                wd = wd + dirName;
                i = drive.length;
                found = true;
            }
        }
        if (wd.charAt(wd.length() - 1) != '/') wd = wd + "/";
        if (!found) System.out.println("cd: " + dirName + ": No such file or directory");
    }
    
    public static void write(int index, String data) {
        drive[index][2] = data;
    }
    
    public static void ls() {
        if (showPrompt) System.out.println("$ ls");
        
        for (String[] i : drive) {
            if (i[0] != null && i[0].length() > 0) System.out.println(i[0]);
        }
    }
    
    public static void ls(String filename) {
        boolean found = false;
        
        if (showPrompt) System.out.println("$ ls " + filename);
        
        for (int i = 0; i < drive.length; i++) {
            if (drive[i][0] != null && drive[i][0].equals(filename)) {
                if (drive[i][1] == NORMAL_FILE) {
                    System.out.println(drive[i][0]);
                    i = drive.length;
                    found = true;
                } else if (drive[i][1] == DIRECTORY) {
                    System.out.println("ls: cannot access '" + filename + "': Directories not supported yet");
                    return;
                }
            }
        }
        
        if (!found) System.out.println("ls: cannot access '" + filename + "': No such file or directory");
    }
    
    public static void mkdir(String dirName) {
        if (showPrompt) System.out.println("$ mkdir " + dirName);
        
        for (int i = 0; i < drive.length; i++) {
            if (drive[i][0] == null) {
                drive[i][0] = dirName;
                drive[i][1] = DIRECTORY;
                i = drive.length;
            }
        }
    }
    
    public static void pwd() {
        if (showPrompt) System.out.println("$ pwd");
        
        if (wd.equals("/")) System.out.println(wd);
        else System.out.println(wd.substring(0, wd.length() - 1));
    }
    
    public static void rm(String filename) {
        boolean found = false;
        
        if (showPrompt) System.out.println("$ rm " + filename);
        
        for (int i = 0; i < drive.length; i++) {
            if (drive[i][0] != null && drive[i][0].equals(filename)) {
                if (drive[i][1] == DIRECTORY) {
                    System.out.println("rm: cannot remove '" + filename + "': Is a directory");
                    return;
                }
                drive[i][0] = null;
                drive[i][1] = null;
                drive[i][2] = null;
                
                i = drive.length;
                found = true;
            }
        }
        
        if (!found) System.out.println("rm: cannot remove '" + filename + "': No such file or directory");
    }
    
    public static void touch(String filename) {
        if (showPrompt) System.out.println("$ touch " + filename);
        
        for (int i = 0; i < drive.length; i++) {
            if (drive[i][0] == null) {
                drive[i][0] = filename;
                drive[i][1] = NORMAL_FILE;
                i = drive.length;
            }
        }
    }
    
    private static void initDrive() {
        boolean old = showPrompt;
        showPrompt = false;
        
        touch("file1");
        touch("file2");
        touch("emptyFile");
        
        mkdir("dir1");
        
        write(1, "text in file2");
        
        showPrompt = old;
    }
}
