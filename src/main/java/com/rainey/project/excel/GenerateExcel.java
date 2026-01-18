package com.rainey.project.excel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.rainey.project.model.WorkDiary;
import com.rainey.project.repository.WorkDiaryRepo;
import com.rainey.project.service.UnifiedService;

@Component
public class GenerateExcel {
	
    @Autowired
	private UnifiedService unifiedService;

    public Workbook workDiaryExcel()  {
    	List<WorkDiary> workDiaries = unifiedService.findAllWorkDiaries(); 
    	Workbook workbook = new XSSFWorkbook();
    	
    	Map<String, CellStyle> styles = createStyle(workbook);
    	
    	Sheet sheet = workbook.createSheet("工作日誌");
    	setColumnWidths(sheet);
    	int setRowNum = 0;
    	
    	for(WorkDiary item : workDiaries) {
    		
    		//算出基偶數列
            boolean isEvenRow = (setRowNum + 1) % 2 != 0;
            
           
            Row row = sheet.createRow(setRowNum++);
            
            Cell yearCell = row.createCell(0);
            //yearCell.setCellValue(Integer.parseInt(item.getYear()));
            yearCell.setCellFormula("YEAR(D"+setRowNum+")");
            if(isEvenRow) {
                yearCell.setCellStyle(styles.get("whiteBGStyle"));
            }else {
            	yearCell.setCellStyle(styles.get("blueBGStyle"));
            }
            
            
        	Cell weekCell = row.createCell(1);
        	//weekCell.setCellValue(Integer.parseInt(item.getWeek()));
        	weekCell.setCellFormula("WEEKNUM(D"+setRowNum+")");
            if(isEvenRow) {
            	weekCell.setCellStyle(styles.get("whiteBGStyle"));
            }else {
            	weekCell.setCellStyle(styles.get("blueBGStyle"));
            }


        	Cell monthCell = row.createCell(2);
        	//monthCell.setCellValue(Integer.parseInt(item.getMonth()));
        	monthCell.setCellFormula("MONTH(D"+setRowNum+")");
        	if(isEvenRow) {
            	monthCell.setCellStyle(styles.get("whiteBGStyle"));
            }else {
            	monthCell.setCellStyle(styles.get("blueBGStyle"));
            }


        	Cell dateCell = row.createCell(3);
        	dateCell.setCellValue(String.valueOf(item.getDate()));
        	
            if(isEvenRow) {
            	dateCell.setCellStyle(styles.get("whiteBGStyle"));
            }else {
            	dateCell.setCellStyle(styles.get("blueBGStyle"));
            }
        	

        	Cell initDeptCell = row.createCell(4);
        	initDeptCell.setCellValue(String.valueOf(item.getInitDept()));
        	initDeptCell.setCellFormula("IF(ISNA(F6VLOOKUP(F"+setRowNum+",部門員工!$A:$E,3,0)),\"\",VLOOKUP(F"+setRowNum+",部門員工!$A:$E,3,0))");
        	 if(isEvenRow) {
        		 initDeptCell.setCellStyle(styles.get("whiteBGStyle"));
             }else {
            	 initDeptCell.setCellStyle(styles.get("blueBGStyle"));
             }
        	
        	
        	Cell initByCell = row.createCell(5);
        	initByCell.setCellValue(String.valueOf(item.getInitBy()));
        	
         	 if(isEvenRow) {
         		initByCell.setCellStyle(styles.get("whiteBGStyle"));
             }else {
            	initByCell.setCellStyle(styles.get("blueBGStyle"));
             }
        	
        	
        	Cell workitemCell = row.createCell(6);
        	workitemCell.setCellValue(String.valueOf(item.getWorkitem()));
        	
        	
        	if(isEvenRow) {
        		workitemCell.setCellStyle(styles.get("whiteBGStyle"));
            }else {
            	workitemCell.setCellStyle(styles.get("blueBGStyle"));
            }
        	
        	
        	
        	Cell itemDescribeCell = row.createCell(7);
        	itemDescribeCell.setCellValue(String.valueOf(item.getItemDescribe()));
        	
        	
        	if(isEvenRow) {
        		itemDescribeCell.setCellStyle(styles.get("itemDescribeWhiteBGStyle"));
            }else {
            	itemDescribeCell.setCellStyle(styles.get("itemDescribeBlueBGStyle"));
            }
        	
        	
        	
        	Cell devMinuteCell = row.createCell(8);
        	devMinuteCell.setCellValue(String.valueOf(item.getDevMinute()));
        	
          	if(isEvenRow) {
          		devMinuteCell.setCellStyle(styles.get("whiteBGStyle"));
            }else {
            	devMinuteCell.setCellStyle(styles.get("blueBGStyle"));
            }
        	
        	
        	
        	Cell supportMinuteCell = row.createCell(9);
        	supportMinuteCell.setCellValue(String.valueOf(item.getSupportMinute()));
        	
          	if(isEvenRow) {
          		supportMinuteCell.setCellStyle(styles.get("whiteBGStyle"));
            }else {
            	supportMinuteCell.setCellStyle(styles.get("blueBGStyle"));
            }

        	Cell systemMinuteCell = row.createCell(10);
        	systemMinuteCell.setCellValue(String.valueOf(item.getSystemMinute()));
        	
        	
          	if(isEvenRow) {
          		systemMinuteCell.setCellStyle(styles.get("whiteBGStyle"));
            }else {
            	systemMinuteCell.setCellStyle(styles.get("blueBGStyle"));
            }
        	
    	}

    	return workbook;
    }   	
   
    
    
    //
    
    private static Map<String, CellStyle> createStyle(Workbook workbook){
    	Map<String , CellStyle> styles = new HashMap<String, CellStyle>();  
    	CellStyle style;
    	
    	//基數，白底色、邊框、除itemDescribe的所有欄位
    	
    	Font whiteBGfont = workbook.createFont();
    	//字形樣式
    	whiteBGfont.setFontHeightInPoints((short)11);
    	
    	whiteBGfont.setFontName("微軟正黑體");

    	style = workbook.createCellStyle();
    	//對齊樣式
    	style.setAlignment(HorizontalAlignment.CENTER);
    	style.setVerticalAlignment(VerticalAlignment.CENTER);
    	//邊框樣式
    	style.setBorderRight(BorderStyle.THIN);
    	style.setRightBorderColor(IndexedColors.BLACK.getIndex());

    	style.setBorderLeft(BorderStyle.THIN);
    	style.setLeftBorderColor(IndexedColors.BLACK.getIndex());

    	style.setBorderTop(BorderStyle.THIN);
    	style.setTopBorderColor(IndexedColors.BLACK.getIndex());

    	style.setBorderBottom(BorderStyle.THIN);
    	style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
    	style.setFont(whiteBGfont);
    	style.setWrapText(true);
    	styles.put("whiteBGStyle", style);
    	
    	
    	//偶數，淺藍底色、邊框、除itemDescribe的所有欄位
    	
    	XSSFWorkbook xssfWorkbook = (XSSFWorkbook) workbook;
    	// 字型
    	XSSFFont blueBGfont = (XSSFFont) xssfWorkbook.createFont();
    	
    	blueBGfont.setFontHeightInPoints((short) 11);
    	blueBGfont.setFontName("微軟正黑體");



    	// CellStyle
    	XSSFCellStyle xssfCellStyle = (XSSFCellStyle) xssfWorkbook.createCellStyle();
    	
    	XSSFColor themeColor = new XSSFColor(new java.awt.Color(221, 235, 247), null);

    	
    	xssfCellStyle.setFillForegroundColor(themeColor);
    	xssfCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

    	
    	
    	     	
    	// 對齊
    	xssfCellStyle.setAlignment(HorizontalAlignment.CENTER);
    	xssfCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

    	// 邊框
    	xssfCellStyle.setBorderRight(BorderStyle.THIN);
    	xssfCellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());

    	xssfCellStyle.setBorderLeft(BorderStyle.THIN);
    	xssfCellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());

    	xssfCellStyle.setBorderTop(BorderStyle.THIN);
    	xssfCellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());

    	xssfCellStyle.setBorderBottom(BorderStyle.THIN);
    	xssfCellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());

    	// 套用字型
    	xssfCellStyle.setFont(blueBGfont);
    	xssfCellStyle.setWrapText(true);

    	styles.put("blueBGStyle", xssfCellStyle);
    	
    	//基數，白底色、邊框、itemDescribe所有欄位
    	
    	Font itemDescribeWhiteBGfont = workbook.createFont();
    	//字形樣式
    	itemDescribeWhiteBGfont .setFontHeightInPoints((short)11);
    	itemDescribeWhiteBGfont.setFontName("微軟正黑體");

    	style = workbook.createCellStyle();
    	//對齊樣式
    	style.setAlignment(HorizontalAlignment.LEFT);
    	style.setVerticalAlignment(VerticalAlignment.CENTER);
    	//邊框樣式
    	style.setBorderRight(BorderStyle.THIN);
    	style.setRightBorderColor(IndexedColors.BLACK.getIndex());

    	style.setBorderLeft(BorderStyle.THIN);
    	style.setLeftBorderColor(IndexedColors.BLACK.getIndex());

    	style.setBorderTop(BorderStyle.THIN);
    	style.setTopBorderColor(IndexedColors.BLACK.getIndex());

    	style.setBorderBottom(BorderStyle.THIN);
    	style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
    	style.setFont(itemDescribeWhiteBGfont );
        style.setWrapText(true);

    	styles.put("itemDescribeWhiteBGStyle", style);
    	
    	
    	//偶數，淺藍底色、邊框、itemDescribe所有欄位
    	
    	//XSSFWorkbook xssfWorkbook = (XSSFWorkbook) workbook;

    	// 字型
    	XSSFFont itemDescribeXSSFFont = (XSSFFont) xssfWorkbook.createFont();
    	
    	itemDescribeXSSFFont.setFontHeightInPoints((short) 11);
    	itemDescribeXSSFFont.setFontName("微軟正黑體");


    	// CellStyle
    	XSSFCellStyle itemDescribeBlueBGStyle = (XSSFCellStyle) xssfWorkbook.createCellStyle();
    	
    	XSSFColor itemDescribeThemeColor = new XSSFColor(new java.awt.Color(221, 235, 247), null);

    	
    	itemDescribeBlueBGStyle.setFillForegroundColor(itemDescribeThemeColor);
    	itemDescribeBlueBGStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

    	// 對齊
    	itemDescribeBlueBGStyle.setAlignment(HorizontalAlignment.LEFT);
    	itemDescribeBlueBGStyle.setVerticalAlignment(VerticalAlignment.CENTER);

    	// 邊框
    	itemDescribeBlueBGStyle.setBorderRight(BorderStyle.THIN);
    	itemDescribeBlueBGStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());

    	itemDescribeBlueBGStyle.setBorderLeft(BorderStyle.THIN);
    	itemDescribeBlueBGStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());

    	itemDescribeBlueBGStyle.setBorderTop(BorderStyle.THIN);
    	itemDescribeBlueBGStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());

    	itemDescribeBlueBGStyle.setBorderBottom(BorderStyle.THIN);
    	itemDescribeBlueBGStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());

    	// 套用字型
    	itemDescribeBlueBGStyle.setFont(itemDescribeXSSFFont);
    	itemDescribeBlueBGStyle.setWrapText(true);

    	styles.put("itemDescribeBlueBGStyle", itemDescribeBlueBGStyle);


    	return styles;
    }
    
	private static void setColumnWidths(Sheet sheet) {
	    double[] widths = new double[]{
	    	8,8,8,16,11,11,8,55,9,9,9
	    };

	    for (int i = 0; i < widths.length; i++) {
	        sheet.setColumnWidth(i, (int) (widths[i] * 256));
	    }
	}
    
	
	public byte[] workbookToByte() throws IOException {
		Workbook workbook = workDiaryExcel();
		  try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
		        workbook.write(byteArrayOutputStream);
		        workbook.close(); 
		        return byteArrayOutputStream.toByteArray();
		    }
	}
    
}
