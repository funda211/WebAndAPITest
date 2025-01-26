import org.apache.poi.ss.usermodel.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ExcelUtils {

    public static String readCell(String filePath, int rowNum, int colNum) throws Exception {
        try (FileInputStream file = new FileInputStream(new File(filePath))) {
            Workbook workbook = WorkbookFactory.create(file);
            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(rowNum);
            Cell cell = row.getCell(colNum);
            return cell.getStringCellValue();
        } catch (IOException e) {
            throw new Exception("Error reading Excel file", e);
        }
    }
}
