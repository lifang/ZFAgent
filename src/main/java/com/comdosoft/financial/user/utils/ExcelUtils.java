package com.comdosoft.financial.user.utils;

import java.text.SimpleDateFormat;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;

/**
 * Excel工具类
 * 
 * @author DELL
 *
 */
public class ExcelUtils {
	/**
	 * EXCEL2003格式
	 */
	public static final String XLS_2003 = ".xls";

	/**
	 * EXCEL2007格式
	 */
	public static final String XLSX_2007 = ".xlsx";

	/**
	 * 由于Excel当中的单元格Cell存在类型,若获取类型错误就会产生异常, 所以通过此方法将Cell内容全部转换为String类型
	 * 
	 * @param cell
	 * @return
	 */
	public static String getCellValue(Cell cell) {
		String str = null;
		if (cell != null) {
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_BLANK:
				str = "";
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				str = String.valueOf(cell.getBooleanCellValue());
				break;
			case Cell.CELL_TYPE_FORMULA:
				str = String.valueOf(cell.getCellFormula());
				break;
			case Cell.CELL_TYPE_NUMERIC:
				if (DateUtil.isCellDateFormatted(cell)) {
					str = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(cell.getDateCellValue());
				} else {
					str = String.valueOf((long) cell.getNumericCellValue());
				}
				break;
			case Cell.CELL_TYPE_STRING:
				str = String.valueOf(cell.getStringCellValue());
				break;
			default:
				str = null;
				break;
			}
		}
		return str;
	}

}
