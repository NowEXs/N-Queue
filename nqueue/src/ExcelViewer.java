import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelViewer extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private File selectedFile;

    public ExcelViewer() {
        setTitle("Excel Viewer and Editor");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 400);

        JButton openButton = new JButton("Open Excel File");
        openButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                selectedFile = fileChooser.getSelectedFile();
                displayExcel(selectedFile);
            }
        });

        JButton saveButton = new JButton("Save Changes");
        saveButton.addActionListener(e -> {
            if (selectedFile != null && model != null) {
                saveExcel(selectedFile);
            } else {
                JOptionPane.showMessageDialog(null, "No Excel file is opened.");
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(openButton);
        buttonPanel.add(saveButton);

        table = new JTable();
        table.setDefaultEditor(Object.class, new DefaultCellEditor(new JTextField()));
        JScrollPane scrollPane = new JScrollPane(table);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(buttonPanel, BorderLayout.NORTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }

    private void displayExcel(File file) {
        try (FileInputStream inputStream = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);
            DataFormatter dataFormatter = new DataFormatter();
            model = new DefaultTableModel();

            Row headerRow = sheet.getRow(0);
            if (headerRow != null) {
                for (Cell cell : headerRow) {
                    model.addColumn(dataFormatter.formatCellValue(cell));
                }

                for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                    Row row = sheet.getRow(rowIndex);
                    if (row != null) {
                        Object[] rowData = new Object[headerRow.getLastCellNum()];
                        for (int columnIndex = 0; columnIndex < headerRow.getLastCellNum(); columnIndex++) {
                            Cell cell = row.getCell(columnIndex);
                            rowData[columnIndex] = dataFormatter.formatCellValue(cell);
                        }
                        model.addRow(rowData);
                    }
                }
            }

            table.setModel(model);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveExcel(File file) {
        try (FileInputStream inputStream = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);
            for (int rowIndex = 0; rowIndex < model.getRowCount(); rowIndex++) {
                Row row = sheet.getRow(rowIndex + 1);
                if (row == null) {
                    row = sheet.createRow(rowIndex + 1);
                }
                for (int columnIndex = 0; columnIndex < model.getColumnCount(); columnIndex++) {
                    Object value = model.getValueAt(rowIndex, columnIndex);
                    Cell cell = row.getCell(columnIndex);
                    if (cell == null) {
                        cell = row.createCell(columnIndex);
                    }
                    if (value != null) {
                        cell.setCellValue(value.toString());
                    }
                }
            }

            try (FileOutputStream outputStream = new FileOutputStream(file)) {
                workbook.write(outputStream);
                JOptionPane.showMessageDialog(null, "Changes saved successfully.");
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ExcelViewer excelViewer = new ExcelViewer();
            excelViewer.setVisible(true);
        });
    }
}