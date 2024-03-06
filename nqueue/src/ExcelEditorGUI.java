import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;
import java.awt.*;

public class ExcelEditorGUI extends JFrame {
    private JTable table;
    private DefaultTableModel model;

    public ExcelEditorGUI() {
        setTitle("Excel Editor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);

        model = new DefaultTableModel();
        table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);

        JButton openButton = new JButton("Open Excel File");
        openButton.addActionListener(e -> openExcelFile());

        JButton saveButton = new JButton("Save Excel File");
        saveButton.addActionListener(e -> saveExcelFile());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(openButton);
        buttonPanel.add(saveButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void openExcelFile() {
        JFileChooser fileChooser = new JFileChooser();
        int returnVal = fileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            Workbook workbook = null;
            try {
                workbook = WorkbookFactory.create(fileChooser.getSelectedFile());
                Sheet sheet = workbook.getSheetAt(0);
                model.setRowCount(0);
                for (Row row : sheet) {
                    Object[] rowData = new Object[row.getLastCellNum()];
                    for (int i = 0; i < row.getLastCellNum(); i++) {
                        Cell cell = row.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                        rowData[i] = cell.toString();
                    }
                    model.addRow(rowData);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                if (workbook != null) {
                    try {
                        workbook.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }

    private void saveExcelFile() {
        JFileChooser fileChooser = new JFileChooser();
        int returnVal = fileChooser.showSaveDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            Workbook workbook = new XSSFWorkbook(); // or new HSSFWorkbook() for .xls files
            Sheet sheet = workbook.createSheet("Sheet1");
            try (FileOutputStream fileOut = new FileOutputStream(fileChooser.getSelectedFile())) {
                for (int i = 0; i < table.getRowCount(); i++) {
                    Row row = sheet.createRow(i);
                    for (int j = 0; j < table.getColumnCount(); j++) {
                        Cell cell = row.createCell(j);
                        cell.setCellValue(table.getValueAt(i, j).toString());
                    }
                }
                workbook.write(fileOut);
                JOptionPane.showMessageDialog(this, "Excel file saved successfully.");
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error saving Excel file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                if (workbook != null) {
                    try {
                        workbook.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ExcelEditorGUI gui = new ExcelEditorGUI();
            gui.setVisible(true);
        });
    }
}