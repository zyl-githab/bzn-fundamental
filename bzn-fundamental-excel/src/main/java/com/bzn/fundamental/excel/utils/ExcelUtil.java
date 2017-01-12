package com.bzn.fundamental.excel.utils;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

/**
 * Excel工具类
 * 
 * @author：fengli
 * @since：2017年1月11日 下午3:12:57
 * @version:
 */
public class ExcelUtil {

	private final static Logger LOGGER = LoggerFactory.getLogger(ExcelUtil.class);

	/**
	 * 创建一个新的实例 ExcelUtil.
	 * 
	 */
	private ExcelUtil() {

	}

	/**
	 * 导出Excel统一方法 1、提供03和07转换 2、Excel导出标题统一
	 * 
	 * @param excelExporter Excel导出对象
	 * @param filename 封装Excel文件名格式
	 * @param res Response
	 * @throws IOException 异常信息
	 * @see [类、类#方法、类#成员]
	 */
	public static void exportExcel(ExcelExporter excelExporter, String filename,
			HttpServletResponse res) throws IOException {
		// 设置日期格式
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		// 03格式
		if (ExcelExporter.EXCEL_TYPE_XLS.equals(excelExporter.getExcelType())) {
			res.setContentType("application/vnd.ms-excel;charset=UTF-8");
			res.addHeader("Content-Disposition",
					"attachment;filename=" + java.net.URLEncoder.encode(filename, "utf-8") + "_"
							+ df.format(new Date()) + ".xls");
		}
		// 07格式
		else if (ExcelExporter.EXCEL_TYPE_XLSX.equals(excelExporter.getExcelType())) {

			res.setContentType(
					"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8");
			res.addHeader("Content-Disposition",
					"attachment;filename=" + java.net.URLEncoder.encode(filename, "utf-8") + "_"
							+ df.format(new Date()) + ".xlsx");
		}
		excelExporter.export(res.getOutputStream());
	}

	public static void exportExcelWithSheets(String filename, String title, String[] headList,
			Map<String, List<List<Object>>> dataList, HttpServletResponse res) throws IOException {
		// 设置日期格式
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		// 07格式
		res.setContentType(
				"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8");
		// res.addHeader(
		// "Content-Disposition",
		// "attachment;filename=" + java.net.URLEncoder.encode(filename,
		// "utf-8") + "_"
		// + df.format(new Date()) + ".xlsx");
		res.addHeader("Content-Disposition",
				"attachment;filename=" + new String(filename.getBytes("gbk"), "iso8859-1") + "_"
						+ df.format(new Date()) + ".xlsx");
		Workbook workbook = getWorkbookWithSheets(dataList, headList, title);
		workbook.write(res.getOutputStream());
		res.getOutputStream().close();
	}

	private static Workbook getWorkbookWithSheets(Map<String, List<List<Object>>> datas,
			String[] headList, String title) {
		XSSFWorkbook workBook = new XSSFWorkbook();
		for (String sheetName : datas.keySet()) {
			List<List<Object>> sheetData = datas.get(sheetName);
			XSSFSheet sheet = workBook.createSheet(sheetName);
			// 标题
			XSSFRow titleRow = sheet.createRow(0);
			XSSFCell titleCell = titleRow.createCell(0, CellType.STRING);
			titleCell.setCellValue(title);
			// 居中
			CellStyle cs = workBook.createCellStyle();
			cs.setAlignment(HorizontalAlignment.CENTER);
			
			titleCell.setCellStyle(cs);
			// 合并单元格
			int mergedColCount = 9;
			if (ArrayUtils.isNotEmpty(headList)) {
				mergedColCount = headList.length;
			}
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 0 + mergedColCount - 1));
			// 表头
			XSSFRow headRow = sheet.createRow(1);
			for (int i = 0; i < headList.length; i++) {
				XSSFCell headCell = headRow.createCell(i, CellType.STRING);
				headCell.setCellValue(headList[i]);
			}
			int rowNumber = 2;
			for (int j = 0; j < sheetData.size(); j++) {
				XSSFRow row = sheet.createRow(rowNumber);
				List<Object> rowData = sheetData.get(j);
				for (int k = 0; k < rowData.size(); k++) {
					XSSFCell cell = row.createCell(k);
					Object data = rowData.get(k);
					cell.setCellValue(StringUtil.parseNull(data));
				}
				rowNumber++;
			}
		}
		return workBook;
	}

	/**
	 * 读取excel的原始内容
	 * 
	 * @param file
	 * @return
	 */
	public static List<List<Object>> readExcel(MultipartFile file) {
		InputStream inputStream = null;
		try {
			inputStream = file.getInputStream();
		} catch (IOException e) {

		}
		return readExcel(inputStream, file.getOriginalFilename().split("\\.")[1]);
	}

	/**
	 * 导入excel
	 * 
	 * @param input
	 * @return
	 */
	public static List<List<Object>> readExcel(InputStream input, String excelType) {
		List<List<Object>> dataList = new ArrayList<List<Object>>();
		// 新建WorkBook
		Workbook wb = null;
		try {
			if (ExcelExporter.EXCEL_TYPE_XLS.equals(excelType)) {
				wb = new HSSFWorkbook(input);
			} else {
				wb = new XSSFWorkbook(input);
			}
			//int sheetNumber = wb.getNumberOfSheets();
			for (int i = 0; i < 1; i++) {
				// 获取Sheet（工作薄）
				Sheet sheet = wb.getSheetAt(i);

				// 开始行数
				int firstRow = sheet.getFirstRowNum();
				// 结束行数
				int lastRow = sheet.getLastRowNum();
				// 判断该Sheet（工作薄)是否为空
				boolean isEmpty = false;
				if (firstRow == lastRow) {
					isEmpty = true;
				}
				if (!isEmpty) {
					for (int j = firstRow; j < lastRow; j++) {
						// 获取一行
						Row row = sheet.getRow(j);

						if (row == null) {
							continue;
						}
						// 开始列数
						int firstCell = row.getFirstCellNum();
						// 结束列数
						int lastCell = row.getLastCellNum();
						// 判断该行是否为空
						List<Object> rowData = new ArrayList<Object>();
						if (firstCell != lastCell) {
							for (int k = firstCell; k < lastCell; k++) {
								// 获取一个单元格
								Cell cell = row.getCell(k);

								if (cell == null) {
									rowData.add("");
									continue;
								}
								Object value = null;

								@SuppressWarnings("deprecation")
								int cellType = cell.getCellType();

								if (cellType == 0) {
									if (HSSFDateUtil.isCellDateFormatted(cell)) {
										SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
										Date date = cell.getDateCellValue();
										value = sdf.format(date);

									} else {
										value = StringUtil.doubleTrans(cell.getNumericCellValue());
									}
								} else if (cellType == 1) {
									value = cell.getStringCellValue();
								} else if (cellType == 4) {
									value = cell.getBooleanCellValue();
								} else if (HSSFDateUtil.isCellDateFormatted(cell)) {
									SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
									Date theDate = cell.getDateCellValue();
									value = sdf.format(theDate);
								}
								rowData.add(value);
							}
						}
						dataList.add(rowData);
					}
				}
			}
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		} finally {
			if (wb != null) {
				try {
					wb.close();
				} catch (IOException e) {
					LOGGER.error(e.getMessage());
				}
			}
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					LOGGER.error(e.getMessage());
				}
			}
		}

		return dataList;
	}
}
