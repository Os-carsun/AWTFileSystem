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
    public static void main(String[] args) {
        // TODO code application logic here
        FileTree fileTree = new FileTree(new File("/"));
        fileTree.Layout();
    }
    
}
