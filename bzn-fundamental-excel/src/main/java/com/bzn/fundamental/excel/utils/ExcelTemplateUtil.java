package com.bzn.fundamental.excel.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

/**
 * Excel工具类
 * 
 * @author：fengli
 * @since：2017年1月11日 下午3:13:17
 * @version:
 */
public class ExcelTemplateUtil {

	private final static Logger LOGGER = LoggerFactory.getLogger(ExcelTemplateUtil.class);

	private static final int EXPORTER_COL_WIDTH = 30;

	private static final short EXPORTER_COL_HEAD_ROW_HEIGH = 30;

	private static final short EXPORTER_DATA_ROW_HEIGH = 30;

	private static final short EXPORTER_TITLE_FONT_HEIGHT = 30;

	private static final short EXPORTER_TITLE_ROWHEIGHT_HEIGHT = 50;

	/**
	 * Excel导出模版
	 * 
	 * @param fileName
	 * @param title
	 * @param headList
	 * @param dataList
	 * @param response
	 */
	public static void exportExcel(String fileName, String title, String[] headList,
			List<List<Object>> dataList, HttpServletResponse response) {
		ExcelExporter excelExporter = new ExcelExporter();
		excelExporter.setDefaultColWidth(EXPORTER_COL_WIDTH);

		// 造标题
		excelExporter.setTitleFontHeight(EXPORTER_TITLE_FONT_HEIGHT);
		excelExporter.setTitleRowHeight(EXPORTER_TITLE_ROWHEIGHT_HEIGHT);
		excelExporter.setTitle(title);
		excelExporter.setColheadRowHeigh(EXPORTER_COL_HEAD_ROW_HEIGH);
		excelExporter.setDataRowHeigh(EXPORTER_DATA_ROW_HEIGH);

		excelExporter.setHeadList(Arrays.asList(headList));

		excelExporter.setDataCellType(null);
		excelExporter.setRowDataList(dataList);
		excelExporter.setExcelType(ExcelExporter.EXCEL_TYPE_XLS);
		try {
			ExcelUtil.exportExcel(excelExporter, fileName, response);
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
	}

	/**
	 * Excel导出模版
	 * 
	 * @param fileName
	 * @param title
	 * @param headList
	 * @param dataList
	 * @param response
	 */
	public static void exportExcelWithSheets(String fileName, String title, String[] headList,
			Map<String, List<List<Object>>> dataList, HttpServletResponse response) {
		try {
			ExcelUtil.exportExcelWithSheets(fileName, title, headList, dataList, response);
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
	}

	/**
	 * 获取Excel中的数据
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static List<List<Object>> readExcel(MultipartFile file, int sheetIndex,
			int beginReadRow, int beginReadCol) {
		return ExcelUtil.readExcel(file, sheetIndex, beginReadRow, beginReadCol);
	}

	/**
	 * 获取Excel中的数据
	 * 
	 * @param input
	 * @return
	 * @throws IOException
	 */
	public static List<List<Object>> readExcel(InputStream input, int sheetIndex,
			int beginReadRow, int beginReadCol) {
		return ExcelUtil.readExcel(input, "xls", sheetIndex, beginReadRow, beginReadCol);
	}
}
