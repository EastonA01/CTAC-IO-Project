package org.example;

import javax.swing.*;
import java.io.File;

public class DirectoryManagementMethods {
    /*
    Description: Handles directory-specific operations.
    Responsibilities:
    Create and delete directories.
     */

    public void AddDirectory(String pathName, JFrame frame){
        try{
            String dirName = JOptionPane.showInputDialog("Enter your Directory Name:");
            if(new File(pathName+"\\"+dirName).exists()){
                JOptionPane.showMessageDialog(frame, "Directory already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                throw new Error("Directory already exists");
            }
            if (dirName != null) {
                new File(pathName+"\\"+dirName).mkdirs();
                JOptionPane.showMessageDialog(frame, "Directory successfully created!", "Success", JOptionPane.INFORMATION_MESSAGE);
                System.out.println("Making Directory: " + pathName+"\\"+dirName);
            }
            else{
                JOptionPane.showMessageDialog(frame, "Invalid Directory Name!.", "Error", JOptionPane.ERROR_MESSAGE);
                throw new Exception("Invalid Directory Name");
            }
        }catch(Exception exception){
            System.out.println("Error: " + exception.getMessage());
        }
    }

    public void DeleteDirectory(String pathName, String fileName, JFrame frame){
        try{
            if(!new File(pathName + "\\" + fileName).exists()){
                JOptionPane.showMessageDialog(frame, "Directory does not exist!", "Error", JOptionPane.ERROR_MESSAGE);
                throw new Error("Directory does not exist");
            } else if (!new File(pathName + "\\" + fileName).isDirectory()) {
                JOptionPane.showMessageDialog(frame, "File is not a directory", "Error", JOptionPane.ERROR_MESSAGE);
                throw new Error("File is not a directory");
            }
            else{
                int reply = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete this directory?");
                if(reply == JOptionPane.YES_OPTION){
                    new File(pathName + "\\" + fileName).delete();
                    JOptionPane.showMessageDialog(frame, "File successfully deleted!.", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
                else{
                    JOptionPane.showMessageDialog(frame, "Failed to delete the selected directory.", "Error", JOptionPane.ERROR_MESSAGE);
                    System.out.println("Directory Was Not deleted: " + pathName + "\\" + fileName);
                }
            }
        }catch(Exception exception){
            exception.printStackTrace();
        }
    }
}
