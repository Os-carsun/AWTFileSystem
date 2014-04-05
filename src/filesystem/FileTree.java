/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package filesystem;

import java.io.File;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author oscar
 */
public class FileTree extends JFrame{
    
    private DefaultTreeModel model;
    private JTree fileTree;
    private File directyFile;
    private JTextArea fileDetailsTextArea = new JTextArea();
    private DefaultMutableTreeNode root;
    
    public FileTree(File Dir){
        directyFile = Dir;
        root  =   new  DefaultMutableTreeNode ( directyFile );
        findAllNode();
        model = new DefaultTreeModel(root);
        fileTree = new JTree(model);
    }
    
    private void findAllNode(){
          
          
    }
    public void Layout(){
        
        
        fileDetailsTextArea.setEditable(false);
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, new JScrollPane(
            fileTree), new JScrollPane(fileDetailsTextArea));

        getContentPane().add(splitPane);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(640, 480);
        setVisible(true);
    }
    
}
