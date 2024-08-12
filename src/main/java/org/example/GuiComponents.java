package org.example;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class GuiComponents implements ActionListener {
    // Components
    private JFrame frame;
    private JPanel panel;
    private JTable fileTable;
    private DefaultTableModel tableModel;
    private JTextField pathField;
    private JTextField searchField;
    private JButton refreshButton, copyButton, moveButton, deleteButton, createDirButton, deleteDirButton, searchButton, pathExplorer;
    private String selectedFile;

    private File currentDir;

    public GuiComponents() {
        // Initialize frame and panel
        frame = new JFrame();
        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Path field and buttons
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(1, 2));
        // Initialize the starting directory in our home directory
        pathField = new JTextField("" + FileSystemView.getFileSystemView().getHomeDirectory());
        refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(this);
        topPanel.add(pathField);
        topPanel.add(refreshButton);

        // File table
        tableModel = new DefaultTableModel(new Object[]{"Name", "Size (in bytes)", "Last Modified"}, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };
        fileTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(fileTable);

        // Action buttons
        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new GridLayout(1, 6));
        copyButton = new JButton("Copy");
        moveButton = new JButton("Move");
        deleteButton = new JButton("Delete");
        createDirButton = new JButton("Create Dir");
        deleteDirButton = new JButton("Delete Dir");
        searchField = new JTextField("Search...");
        searchButton = new JButton("Search");
        // Action Listeners for Each Button
        copyButton.addActionListener(this);
        moveButton.addActionListener(this);
        deleteButton.addActionListener(this);
        createDirButton.addActionListener(this);
        deleteDirButton.addActionListener(this);
        searchButton.addActionListener(this);
        fileTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) return;
                int row = fileTable.getSelectedRow();
                if (row >= 0) {
                    // Debugging print statement to show selected file in explorer
                    // System.out.println(fileTable.getValueAt(row, 0).toString());
                    selectedFile = fileTable.getValueAt(row, 0).toString();
                }
            }
        });

        // Add buttons to action panel
        actionPanel.add(copyButton);
        actionPanel.add(moveButton);
        actionPanel.add(deleteButton);
        actionPanel.add(createDirButton);
        actionPanel.add(deleteDirButton);
        actionPanel.add(searchField);
        actionPanel.add(searchButton);

        // Add panels to frame
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(actionPanel, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("File Explorer");
        frame.setSize(800, 600);
        frame.setVisible(true);

        // Refresh panel to display files in Main drive
        refreshDirectory();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == refreshButton) {
            refreshDirectory();
        } else if (e.getSource() == copyButton) {
            // Implement file copy logic
            try {
                new FileManagerMethods().CopyFile(frame, new File(pathField.getText()+"\\"+selectedFile));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } else if (e.getSource() == moveButton) {
            // Implement file move logic
            try {
                new FileManagerMethods().MoveFile(frame, new File(pathField.getText()+"\\"+selectedFile));
                refreshDirectory();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        } else if (e.getSource() == deleteButton) {
            // Implement file delete logic
            try {
                new FileManagerMethods().DeleteFile(frame, new File(pathField.getText()+"\\"+selectedFile));
                refreshDirectory();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } else if (e.getSource() == createDirButton) {
            new DirectoryManagementMethods().AddDirectory(pathField.getText(), frame);
            refreshDirectory();
        } else if (e.getSource() == deleteDirButton) {
            // Directory deletion logic
            new DirectoryManagementMethods().DeleteDirectory(pathField.getText(), selectedFile, frame);
            refreshDirectory();
        } else if (e.getSource() == searchButton) {
            // File search logic
            new DirectorySearching().SearchDirectory(pathField.getText(), tableModel, searchField.getText(), frame);
        }
    }

    private void refreshDirectory() {
        String path = pathField.getText();
        currentDir = new File(path);

        if (currentDir.isDirectory()) {
            File[] files = currentDir.listFiles();
            tableModel.setRowCount(0); // Clear existing rows
            if (files != null) {
                for (File file : files) {
                    tableModel.addRow(new Object[]{
                            file.getName(),
                            file.isFile() ? file.length() : "",
                            file.lastModified()
                    });
                }
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid directory path.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
