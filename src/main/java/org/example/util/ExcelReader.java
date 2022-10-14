package org.example.util;

import com.sun.istack.internal.Nullable;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.exception.ReadExcelException;

import java.io.FileInputStream;
import java.io.IOException;

public class ExcelReader {

    /**
     * read Excel file
     *
     * @param filePath
     * @return
     * @throws ReadExcelException
     */
    public Workbook getReadWorkBookType(String filePath) throws ReadExcelException {
        //xls-2003, xlsx-2007
        FileInputStream is = null;

        try {
            is = new FileInputStream(filePath);
            if (filePath.toLowerCase().endsWith("xlsx")) {
                return new XSSFWorkbook(is);
            } else if (filePath.toLowerCase().endsWith("xls")) {
                return new HSSFWorkbook(is);
            } else {
                //  抛出自定义的业务异常
                throw new ReadExcelException("excel格式文件错误");
            }
        } catch (IOException e) {
            //  抛出自定义的业务异常
            throw new ReadExcelException(e.getMessage());
        } finally {
            IOUtils.closeQuietly(is);
        }
    }

    /**
     * cell to String
     *
     * @param cell
     * @return
     */
    public String getCellStringVal(@Nullable Cell cell) {
        if (cell==null) return "";
        CellType cellType = cell.getCellTypeEnum();
        switch (cellType) {
            case NUMERIC:
                return String.valueOf((int)cell.getNumericCellValue());
            case STRING:
                return cell.getStringCellValue();
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            case ERROR:
                return String.valueOf(cell.getErrorCellValue());
            default:
                return "";
        }
    }
}
