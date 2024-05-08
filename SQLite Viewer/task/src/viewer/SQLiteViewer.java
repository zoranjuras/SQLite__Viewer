package viewer;

import java.awt.event.*;
import java.io.File;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class SQLiteViewer extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private DBConnection dbConnection;
    private JButton executeButton;
    private JTextArea queryTextArea;

    public SQLiteViewer() {
        super("SQLite Viewer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 900);
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);

        initComponents();

        initTable();

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 270, 645, 500);
        add(scrollPane);

        setVisible(true);
    }

    private void initTable() {
        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        table.setFillsViewportHeight(true);
        table.setName("Table");
    }

    private void initComponents() {
        JPanel firstPanel = new JPanel();
        firstPanel.setLayout(null);
        firstPanel.setBounds(0, 0, 700, 50);
        add(firstPanel);

        JLabel nameLabel = new JLabel("Database:");
        nameLabel.setBounds(20, 20, 60, 30);
        firstPanel.add(nameLabel);

        JTextField jTextField = new JTextField();
        jTextField.setName("FileNameTextField");
        jTextField.setBounds(100, 20, 445, 30);
        firstPanel.add(jTextField);

        JButton button = new JButton("Open");
        button.setName("OpenFileButton");
        button.setBounds(565, 20, 100, 30);
        firstPanel.add(button);

        JPanel secondPanel = new JPanel();
        secondPanel.setLayout(null);
        secondPanel.setBounds(0, 50, 700, 50);
        add(secondPanel);

        JLabel tblNameLabel = new JLabel("Table:");
        tblNameLabel.setBounds(20, 20, 60, 30);
        secondPanel.add(tblNameLabel);

        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.setName("TablesComboBox");
        comboBox.setBounds(100, 20, 565, 30);
        secondPanel.add(comboBox);

        JPanel thirdPanel = new JPanel();
        thirdPanel.setLayout(null);
        thirdPanel.setBounds(0, 100, 700, 100);
        add(thirdPanel);

        JLabel queryLabel = new JLabel("Query:");
        queryLabel.setBounds(20, 20, 60, 30);
        thirdPanel.add(queryLabel);

        queryTextArea = new JTextArea();
        queryTextArea.setName("QueryTextArea");
        queryTextArea.setRows(5);
        queryTextArea.setLineWrap(true);
        queryTextArea.setBounds(100, 20, 565, 80);
        thirdPanel.add(queryTextArea);

        JPanel fourthPanel = new JPanel();
        fourthPanel.setLayout(null);
        fourthPanel.setBounds(0, 200, 700, 50);
        add(fourthPanel);

        executeButton = new JButton("Execute");
        executeButton.setName("ExecuteQueryButton");
        executeButton.setBounds(565, 20, 100, 30);
        fourthPanel.add(executeButton);

//        setQueryUIEnabled(false);
        executeButton.setEnabled(false);
        queryTextArea.setEnabled(false);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fileName = jTextField.getText();
                File dbFile = new File(fileName);
                if (!dbFile.exists()) {
//                    setQueryUIEnabled(false);
                    executeButton.setEnabled(false);
                    queryTextArea.setEnabled(false);
                    JOptionPane.showMessageDialog(new JFrame(), "File doesn't exist!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    dbConnection = new DBConnection(fileName);
                    if (!updateComboBox(comboBox, dbConnection.getUrl())) {
//                        setQueryUIEnabled(false);
                        executeButton.setEnabled(false);
                        queryTextArea.setEnabled(false);
                        JOptionPane.showMessageDialog(new JFrame(), "Database is empty or no tables found!", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
//                        setQueryUIEnabled(true);
                        executeButton.setEnabled(true);
                        queryTextArea.setEnabled(true);
                    }
                }
            }
        });

        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateQueryTextArea(queryTextArea, (String) comboBox.getSelectedItem());
            }
        });

        executeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                executeQuery(queryTextArea.getText());
            }
        });
    }

    private boolean updateComboBox(JComboBox<String> comboBox, String url) {
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT name FROM sqlite_master WHERE type ='table' AND name NOT LIKE 'sqlite_%';")) {
            comboBox.removeAllItems();
            boolean hasTables = false;
            while (rs.next()) {
                comboBox.addItem(rs.getString("name"));
                hasTables = true;
            }
            return hasTables;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private void updateQueryTextArea(JTextArea queryTextArea, String selectedTable) {
        String query = "SELECT * FROM " + selectedTable + ";";
        queryTextArea.setText(query);
    }

    private void executeQuery(String query) {
        try (Connection conn = DriverManager.getConnection(dbConnection.getUrl());
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            String[] columnNames = new String[columnCount];
            for (int i = 0; i < columnCount; i++) {
                columnNames[i] = metaData.getColumnName(i + 1);
            }
            tableModel.setColumnIdentifiers(columnNames);
            tableModel.setRowCount(0);
            while (rs.next()) {
                Object[] row = new Object[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    row[i] = rs.getObject(i + 1);
                }
                tableModel.addRow(row);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(new JFrame(), "SQL Error: " + ex.getMessage(), "Query Execution Error", JOptionPane.ERROR_MESSAGE);
            setQueryUIEnabled(false);
        }
    }

    private void setQueryUIEnabled(boolean enabled) {
        executeButton.setEnabled(enabled);
        queryTextArea.setEditable(enabled);
    }

}
