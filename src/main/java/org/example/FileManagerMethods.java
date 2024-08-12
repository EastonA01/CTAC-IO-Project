package org.example;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileManagerMethods extends Component {
    /*
    Description: Performs file operations such as displaying, copying, moving, and deleting files.
    Responsibilities:
    Implement methods for file and directory operations.
    Provide results back to the GUIComponents.
     */

    final JFileChooser chooser = new JFileChooser() {
        public void approveSelection() {
            super.approveSelection();
        }
    };

    // Copy File Method
    public void CopyFile(JFrame frame, File source) throws IOException {
        int reply = JOptionPane.showConfirmDialog(frame, "Are you sure you want to copy the selected file?");
        if (reply == JOptionPane.YES_OPTION) {

            chooser.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );
            chooser.showOpenDialog(this);
            System.out.println("Selected directory: " + chooser.getSelectedFile());
            File dest = chooser.getSelectedFile();
            if(dest.isDirectory()) {
                try {
                    Path destPath = Paths.get(dest.getAbsolutePath() + "\\" + source.getName());
                    Files.copy(source.toPath(), destPath, StandardCopyOption.REPLACE_EXISTING);
                }catch(Exception e) {
                    JOptionPane.showMessageDialog(frame, "Error Copying File.", "Error", JOptionPane.ERROR_MESSAGE);
                    System.out.println("Error copying file: " + e.getMessage());
                }
            }

        }

    }

    // Add Move File Method
    public void MoveFile(JFrame frame, File source) throws IOException {
        int reply = JOptionPane.showConfirmDialog(frame, "Are you sure you want to move the selected file?");
        if (reply == JOptionPane.YES_OPTION) {
            chooser.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );
            chooser.showOpenDialog(this);
            System.out.println("Selected directory: " + chooser.getSelectedFile());
            File dest = chooser.getSelectedFile();
            if(dest.isDirectory()) {
                try {
                    Path destPath = Paths.get(dest.getAbsolutePath() + "\\" + source.getName());
                    Files.move(source.toPath(), destPath, StandardCopyOption.REPLACE_EXISTING);
                }catch(Exception e) {
                    JOptionPane.showMessageDialog(frame, "Error moving file.", "Error", JOptionPane.ERROR_MESSAGE);
                    System.out.println("Error moving file: " + e.getMessage());
                }
            }

        }

    }
    // Delete File method
    public void DeleteFile(JFrame frame, File source) throws IOException {
        int reply = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete the selected file?");
        if (reply == JOptionPane.YES_OPTION) {
           if(!source.delete()) {
               JOptionPane.showMessageDialog(frame, "Failed to delete the selected file.", "Error", JOptionPane.ERROR_MESSAGE);
           }
           else{
               JOptionPane.showMessageDialog(frame, "Successfully deleted the selected file.");
           }
        }
    }
}
