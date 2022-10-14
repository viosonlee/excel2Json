package org.example;

import com.google.gson.Gson;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.example.bean.ServiceBean;
import org.example.exception.ReadExcelException;
import org.example.util.ExcelReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        List<ServiceBean> beans = readExcel("C:\\Users\\viosonlee\\Downloads\\服务供应商入驻优化.xlsx");
        Gson gson = new Gson();
        String json = gson.toJson(beans);
        System.out.println("beans= " + json);
    }

    private static List<ServiceBean> readExcel(String filePath) throws ReadExcelException {
        ExcelReader reader = new ExcelReader();
        Workbook workbook = reader.getReadWorkBookType(filePath);
        List<ServiceBean> serviceList = new ArrayList<>();

        Sheet sheet = workbook.getSheet("品类");
        //第一行是标题
        for (int rowNum = 1; rowNum < sheet.getLastRowNum(); rowNum++) {
            Row row = sheet.getRow(rowNum);
            Cell cell = row.getCell(0);
            //供应商类型
            String typeCode = reader.getCellStringVal(cell);
            // read 三级分类
            String typeName = reader.getCellStringVal(row.getCell(1));
            String thirdCode = reader.getCellStringVal(row.getCell(2));
            String fourCode = reader.getCellStringVal(row.getCell(3));
            String productName = reader.getCellStringVal(row.getCell(4));
            if (serviceList.isEmpty() || serviceList.stream().noneMatch(b -> b.getCode().equals(typeCode))) {
                ServiceBean serviceBean = new ServiceBean();
                serviceBean.setCode(typeCode);
                serviceBean.setName(typeName);
                serviceBean.setSubList(new ArrayList<>());
                serviceList.add(serviceBean);

                if (thirdCode != null && !thirdCode.isEmpty()) {
                    ServiceBean.ThirdSub thirdSub = new ServiceBean.ThirdSub();
                    thirdSub.setCode(thirdCode);
                    thirdSub.setName(productName);
                    serviceBean.addSub(thirdSub);
                } else if (fourCode != null && !fourCode.isEmpty()) {
                    //商品信息
                    ServiceBean.FourthSub fourthSub = new ServiceBean.FourthSub(fourCode, productName);
                    serviceBean.addFourSub(fourthSub);
                }
            } else {
                Optional<ServiceBean> optional = serviceList.stream().filter(b -> b.getCode().equals(typeCode)).findFirst();
                if (optional.isPresent()) {
                    ServiceBean serviceBean = optional.get();
                    serviceBean.setCode(typeCode);
                    serviceBean.setName(reader.getCellStringVal(row.getCell(1)));
                    if (thirdCode != null && !thirdCode.isEmpty()) {
                        ServiceBean.ThirdSub thirdSub = new ServiceBean.ThirdSub();
                        thirdSub.setCode(thirdCode);
                        thirdSub.setName(productName);
                        serviceBean.addSub(thirdSub);
                    } else if (fourCode != null && !fourCode.isEmpty()) {
                        //商品信息
                        ServiceBean.FourthSub fourthSub = new ServiceBean.FourthSub(fourCode, productName);
                        serviceBean.addFourSub(fourthSub);
                    }
                }
            }
        }
        return serviceList;
    }
}