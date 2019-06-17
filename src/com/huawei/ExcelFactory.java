package com.huawei;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;

/**
 * @title Excel解析工场类
 * @desc  用例解析 生成map集合或者类集合
 * @author wwx193433  
 * @date 2019年6月15日
 */
public class ExcelFactory {
	
	public List<String[]> readExcel(String sourceFile){
		List<String[]> caseList = new ArrayList<String[]>();
		try {
			InputStream is = new FileInputStream(sourceFile);
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
            
            //获取首页
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
            if (hssfSheet == null) {
                return caseList;
            }
            //默认首行为标题，遍历数据行
            for (int rn = 1; rn <= hssfSheet.getLastRowNum(); rn++) {
                HSSFRow hssfRow = hssfSheet.getRow(rn);
                if (hssfRow == null) {
                    continue;
                }

            	String[] caseBean = new String[hssfRow.getLastCellNum()];
                //遍历单元格
                for(int cn=0;cn<hssfRow.getLastCellNum();cn++){
                	Cell cell = hssfRow.getCell(cn, Row.CREATE_NULL_AS_BLANK);
                	caseBean[cn] = getCellValue(cell);
                }
                
                //这里可以过滤空行，或者无效数据。可以根据特殊字段进行校验，比如用例ID
                if(caseBean.length==0 || caseBean[0]=="") {
                	continue;
                }
                caseList.add(caseBean);
            }
		} catch (Exception e) {
			// TODO: handle exception
		}
		return caseList;
	}
	
	/**
	 * 获取不同格式单元格内的value值
	 * @param cell
	 * @return
	 */
	private String getCellValue(Cell cell) {
		String cellValue = "";
		switch(cell.getCellType()){
        case Cell.CELL_TYPE_BLANK:
        	cellValue = "";
            break;
        case Cell.CELL_TYPE_BOOLEAN:
        	cellValue = Boolean.toString(cell.getBooleanCellValue());
            break;
        case Cell.CELL_TYPE_ERROR:
        	cellValue = "";
            break;
        case Cell.CELL_TYPE_FORMULA:
            cell.setCellType(Cell.CELL_TYPE_STRING);
            cellValue = cell.getStringCellValue();
            break;
        case Cell.CELL_TYPE_NUMERIC:
            if (DateUtil.isCellDateFormatted(cell)) {
            	cellValue = String.valueOf(cell.getDateCellValue());
            } else {
                cell.setCellType(Cell.CELL_TYPE_STRING);
                String temp = cell.getStringCellValue();
                // 判断是否包含小数点，如果不含小数点，则以字符串读取，如果含小数点，则转换为Double类型的字符串
                if (temp.indexOf(".") > -1) {
                	cellValue = String.valueOf(new Double(temp)).trim();
                } else {
                	cellValue = temp.trim();
                }
            }
            
            break;
        case Cell.CELL_TYPE_STRING:
        	cellValue = cell.getStringCellValue().trim();
            break;
        default:
        	cellValue = "";
            break;
		}
		return cellValue;
	}
	
}
