package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.File;

public class DirectorySearching {
    /*
    Description: Implements search functionality.
    Responsibilities:
    Search for files based on name or extension within a directory.
    Return search results.
     */

    public void SearchDirectory(String path, DefaultTableModel tableModel, String search, JFrame frame){
        File directory = new File(path);
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            tableModel.setRowCount(0); // Clear existing rows
            if (files != null) {
                for (File file : files) {
                    if(file.getName().toLowerCase().contains(search)){
                        tableModel.addRow(new Object[]{
                                file.getName(),
                                file.isFile() ? file.length() : "",
                                file.lastModified()
                        });
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid directory path.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
