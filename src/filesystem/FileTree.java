/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package filesystem;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 * 
 *
 * @author oscar
 */
public class FileTree extends JFrame{
    
    private DefaultTreeModel model;
    private JTree fileTree;
    private File directyFile;
    private JTextArea fileDetailsTextArea = new JTextArea();
    private DefaultMutableTreeNode root;
    private DefaultMutableTreeNode InVisibleRoot;
    private DefaultMutableTreeNode node;
    private JPopupMenu popupMenu = new JPopupMenu();
    
    public FileTree(File Dir){
        directyFile = Dir;
        InVisibleRoot = new DefaultMutableTreeNode();
        
        root  =   new  DefaultMutableTreeNode ( directyFile );
        InVisibleRoot.add(root);
        findAllChild(root);
        
        model = new DefaultTreeModel(InVisibleRoot);
        fileTree = new JTree(model);
        fileTree.addMouseListener(new mouseEventListener());
        fileTree.addTreeSelectionListener(new FileTreeSelectionListener());
        fileTree.setRootVisible(false);
        buildPopMenu();
        
    }
    public FileTree(File[] Dir){
        InVisibleRoot = new DefaultMutableTreeNode();
        for(File f : Dir){
         node = new DefaultMutableTreeNode();
         InVisibleRoot.add(node);
         findAllChild(node);
        }
        model = new DefaultTreeModel(InVisibleRoot);
        fileTree = new JTree(model);
        fileTree.addMouseListener(new mouseEventListener());
        fileTree.addTreeSelectionListener(new FileTreeSelectionListener());
        fileTree.setRootVisible(false);
        buildPopMenu();
    }
    
    private void findAllNode(DefaultMutableTreeNode parent){
        
        File parentFile = (File)parent.getUserObject();
        File f = null;
        File [] childList = parentFile.listFiles();
        DefaultMutableTreeNode newNode = null;
        if(parentFile.listFiles() != null)
        try {
            for(int i=0;i< childList.length;i++){
//            System.out.println(f.toString());
            f = childList[i];
            System.out.println(f.toString());
            newNode = new DefaultMutableTreeNode(f);
            parent.add(newNode);
            if(f!=null&&f.isDirectory())
            findAllNode((DefaultMutableTreeNode) parent.getChildAt(i));
            
            
        }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("f= "+f.toString());
            System.out.println("TreeNode = " + newNode.toString());
        }
        
          
    }
    private void findAllChild(DefaultMutableTreeNode parent){
        File parentFile = (File)parent.getUserObject();
        File f = null;
        File [] childList = parentFile.listFiles();
        DefaultMutableTreeNode newNode = null;
        if(parentFile.listFiles() != null)
        try {
            for(int i=0;i< childList.length;i++){
//                System.out.println(f.toString());
                f = childList[i];
                System.out.println(f.toString());
                newNode = new DefaultMutableTreeNode(f);
                parent.add(newNode);
//                model.insertNodeInto(newNode, parent, i);
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("f= "+f.toString());
            System.out.println("TreeNode = " + newNode.toString());
        }    
    }
    private class mouseEventListener implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {
            if(e.getClickCount() == 2 && SwingUtilities.isLeftMouseButton(e)){
                node = (DefaultMutableTreeNode) fileTree.
                        getLastSelectedPathComponent();
                File innerFile = (File) node.getUserObject();
                if(innerFile.isDirectory())
                    findAllChild(node);
                else if(innerFile.isFile()){
                    try {
                        Desktop.getDesktop().open(innerFile);
                    } catch (IOException ex) {
                        Logger.getLogger(FileTree.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }else if(SwingUtilities.isRightMouseButton(e)){
                popupMenu.show(fileTree, e.getX(), e.getY());
            }
            
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }
    private void buildPopMenu(){
        ActionListener actionListener = (ActionListener) new PopupActionListener();
        JMenuItem mkdirItem = new JMenuItem("newDir");
        mkdirItem.addActionListener(actionListener);
        JMenuItem deleteItem = new JMenuItem("delete");
        deleteItem.addActionListener(actionListener);
        JMenuItem copyItem = new JMenuItem("copy");
        copyItem.addActionListener(actionListener);
        JMenuItem pasteItem = new JMenuItem("paste");
        pasteItem.addActionListener(actionListener);
        popupMenu.add(mkdirItem);
        popupMenu.add(deleteItem);
        popupMenu.add(copyItem);
        popupMenu.add(pasteItem);
        fileTree.add(popupMenu);
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
    private class PopupActionListener implements ActionListener {


        @Override
            public void actionPerformed(ActionEvent e) {
                switch (e.getActionCommand()){

                    case "newDir":
                        mkDir();

                        break;
                    case "delete":
                        delete();
                        break;
                    case "copy":
                        break;
                    case "paste":
                        break;

                }
            }

    }
    private void mkDir(){
        node = (DefaultMutableTreeNode) fileTree.
                        getLastSelectedPathComponent();
        File file = (File) node.getUserObject();
        File newDir = new File(file.getPath()+"/newDir");
        File parentFile = new File(file.getParent());
        newDir.mkdir();
        
        model.insertNodeInto(new DefaultMutableTreeNode(newDir), node, node.getChildCount());
        
    }
    private void delete(){
    
        node = (DefaultMutableTreeNode) fileTree.
                        getLastSelectedPathComponent();
        File file = (File) node.getUserObject();
        file.delete();
        model.removeNodeFromParent(node);
    }
    private String getFileDetails(File file) {
        if (file == null)
          return "";
        StringBuffer buffer = new StringBuffer();
        buffer.append("Name: " + file.getName() + "\n");
        buffer.append("Path: " + file.getPath() + "\n");
        buffer.append("Size: " + file.length() + "\n");
        buffer.append(file.isDirectory()?"Directory":"File"+"\n");
        
        return buffer.toString();
    }
    private class FileTreeSelectionListener implements TreeSelectionListener{

        @Override
        public void valueChanged(TreeSelectionEvent e) {
            node = (DefaultMutableTreeNode) fileTree.
                            getLastSelectedPathComponent();
            File file = (File) node.getUserObject();
            fileDetailsTextArea.setText(getFileDetails(file));
        }
    }
}
