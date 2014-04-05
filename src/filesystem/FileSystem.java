/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package filesystem;

import java.io.File;

/**
 *
 * @author oscar
 */
public class FileSystem {

    /**
     * @param args the command line arguments
     */
    private static String OS = null;
    public static String getOsName(){
        
      if(OS == null) { OS = System.getProperty("os.name"); }
      return OS;
    }
    public static boolean isWindows(){
        
      return getOsName().startsWith("Windows");
    }
    public static void main(String[] args) {
        // TODO code application logic here
        FileTree fileTree = null;
        if(isWindows()){
            fileTree = new FileTree(File.listRoots());
        }            
        else
            fileTree = new FileTree(new File("/"));
        fileTree.Layout();
    }
    
}
