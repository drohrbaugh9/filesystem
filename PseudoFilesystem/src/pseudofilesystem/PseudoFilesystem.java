package pseudofilesystem;

import java.util.Arrays;

public class PseudoFilesystem {
    
    static String[][] drive = new String[256][3];
    
    static final String NORMAL_FILE = "0";
    static final String DIRECTORY = "1";
    
    static String wd = "/";
    static int wDir = 0;
    
    static boolean showPrompt = true;

    public static void main(String[] args) {
        initDrive();
        
        ls();
        
        /*cat("file2");
        
        cat("dir1");
        
        cat("emptyFile");
        
        rm("file1");*/
        
        ls();
        
        cd("dir1");
        
        pwd();
        
        cd("../");
        
        pwd();
        
        cd("..");
        
        pwd();
        
        /*ls("file2");
        
        ls("file5");
        
        ls("dir1");*/
        
        mkdir("dir2");
        
        /*rm("dir2");
        
        rm("thisFileShouldntExist");*/
        
        /**/
        System.out.println("\ndrive:");
        for (int i = 0; i < drive.length; i++) {
            if (drive[i][1] != null) System.out.println(i + ": " + Arrays.toString(drive[i]));
        }
        System.out.print("\ntable:" + drive[0][2]);
        /**/
    }
    
    public static void cat(String filename) {
        if (showPrompt) System.out.println("$ cat " + filename);
        
        String tempTable = drive[wDir][2];
        
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
            wDir = Integer.parseInt("" + drive[wDir][2].charAt(drive[wDir][2].indexOf("..") + 3));
            return;
        }
        
        String tempTable = drive[0][2];
        String fileNameToCheck;
        while (tempTable.length() > 0) {
            fileNameToCheck = tempTable.substring(0, tempTable.indexOf(";"));
            if (fileNameToCheck.equals(dirName)) {
                if (drive[Integer.parseInt("" + tempTable.charAt(tempTable.indexOf(";") + 2))][1].equals(DIRECTORY)) {
                    
                } else {
                    //error
                }
                tempTable = "";
            } else {
                tempTable = tempTable.substring(tempTable.indexOf(';') + 1, tempTable.length());
            }
        }
        
        /*for (int i = 0; i < drive.length; i++) {
            if (drive[i][0] != null && drive[i][0].equals(dirName)) {
                wd = wd + dirName;
                i = drive.length;
                found = true;
            }
        }*/
        
        if (wd.charAt(wd.length() - 1) != '/') wd = wd + "/";
        if (!found) System.out.println("cd: " + dirName + ": No such file or directory");
    }
    
    private static void write(int index, String data) {
        drive[index][2] = data;
    }
    
    public static void ls() {
        if (showPrompt) System.out.println("$ ls");
        
        
    }
    
    /*public static void ls(String filename) {
        boolean found = false;
        
        if (showPrompt) System.out.println("$ ls " + filename);
        
        
        if (wd.equals("/")) {
            for (int i = 0; i < table.length; i++) {
                if (table[i][0] != null && table[i][0].equals(filename)) {
                    if (drive[Integer.parseInt(table[i][1])][1] == NORMAL_FILE) {
                        System.out.println(table[i][0]);
                        i = table.length;
                        found = true;
                    } else if (drive[Integer.parseInt(table[i][1])][1] == DIRECTORY) {
                        System.out.println("ls: cannot access '" + filename + "': Directories not supported yet");
                        return;
                    }
                }
            }
        } else {
            
            for (int i = 0; i < table.length; i++) {
                if (table[i][0] != null && table[i][0].equals(filename)) {
                    if (drive[Integer.parseInt(table[i][1])][1] == NORMAL_FILE) {
                        System.out.println(table[i][0]);
                        i = table.length;
                        found = true;
                    } else if (drive[Integer.parseInt(table[i][1])][1] == DIRECTORY) {
                        System.out.println("ls: cannot access '" + filename + "': Directories not supported yet");
                        return;
                    }
                }
            }
        }
        
        if (!found) System.out.println("ls: cannot access '" + filename + "': No such file or directory");
    }*/
    
    public static void mkdir(String dirName) {
        if (showPrompt) System.out.println("$ mkdir " + dirName);
        
        drive[0][2] = drive[0][2] + dirName + ";";
        for (int j = 0; j < drive.length; j++) {
            if (drive[j][0] == null) {
                drive[j][0] = dirName;
                drive[j][1] = DIRECTORY;
                drive[0][2] = drive[0][2] + " " + j + ";\n";
                j = drive.length;
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
        
        drive[0][2] = drive[0][2] + filename + ";";
        for (int i = 0; i < drive.length; i++) {
            if (drive[i][0] == null) {
                drive[i][0] = filename;
                drive[i][1] = NORMAL_FILE;
                drive[0][2] = drive[0][2] + " " + i + ";\n";
                i = drive.length;
            }
        }
    }
    
    private static void initDrive() {
        boolean old = showPrompt;
        showPrompt = false;
        
        drive[0][0] = ".table";
        drive[0][2] = "\n.; 0;\n.. 0;\n";
        
        touch("file1");
        touch("file2");
        touch("emptyFile");
        
        mkdir("dir1");
        
        write(2, "text in file2");
        
        drive[5][0] = "fake_file1"; drive[5][1] = NORMAL_FILE;
        drive[6][0] = "fake_file2"; drive[6][1] = NORMAL_FILE;
        
        write(4, "\n.; 4;\n.. 0;\nfake_file1; 5;\nfake_file2; 6;\n");
        
        showPrompt = old;
    }
}
