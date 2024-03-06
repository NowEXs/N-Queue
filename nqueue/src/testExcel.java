import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import java.io.*;

public class testExcel {
    public static void main(String[] args) {
        String FilePath = "C:\\Users\\prato\\Desktop\\Book1.xlsx";
        File myFile = new File(FilePath);

        Workbook wb = new XSSFWorkbook();
        Sheet st2 = wb.createSheet("Sheet2");

        Row r = null;
        Cell c = null;

        r = st2.createRow(5);
        c = r.createCell(3);
        c.setCellValue("Hi!! I'm Create Excel File");

        r = st2.createRow(6);
        c = r.createCell(3);
        c.setCellValue("Test2");

        try (FileOutputStream fout = new FileOutputStream(myFile);){
            wb.write(fout);
            System.out.println("Succesfully.");
        }
        catch (NoClassDefFoundError nc){
            nc.printStackTrace();
            System.out.println("Error");
        }
        catch (FileNotFoundException ex){
            ex.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
//        try (FileInputStream fIn = new FileInputStream(myFile);){
//            if (myFile.isFile() && myFile.exists()){
//                System.out.println("File Open Successfully.");
//            }
//            else{
//                System.out.println("Failed..");
//            }
//        }
//        catch (FileNotFoundException ex){
//            ex.printStackTrace();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }
}
