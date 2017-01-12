package com.bzn.fundamental.excel.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Excel导出公共模块
 * 
 * @author：fengli
 * @since：2017年1月11日 下午3:21:04
 * @version:
 */
public class ExcelExporter {
	/** EXCEL_TYPE_XLS */
	public static final String EXCEL_TYPE_XLS = "xls";

	/** EXCEL_TYPE_XLSX */
	public static final String EXCEL_TYPE_XLSX = "xlsx";

	/** DATA_CELL_TYPE_INTEGER */
	public static final String DATA_CELL_TYPE_INTEGER = "Integer";

	/** DATA_CELL_TYPE_STRING */
	public static final String DATA_CELL_TYPE_STRING = "String";

	/** DATA_CELL_TYPE_FLOAT */
	public static final String DATA_CELL_TYPE_FLOAT = "Float";

	/** DATA_CELL_ALIGN_CENTER */
	public static final short DATA_CELL_ALIGN_CENTER = HorizontalAlignment.CENTER.getCode();

	/** DATA_CELL_ALIGN_LEFT */
	public static final short DATA_CELL_ALIGN_LEFT = HorizontalAlignment.LEFT.getCode();

	/** DATA_CELL_ALIGN_RIGHT */
	public static final short DATA_CELL_ALIGN_RIGHT = HorizontalAlignment.RIGHT.getCode();

	/** 标题 */
	private String title;

	/** 描述 */
	private String description;

	/** 横表头 */
	private List<String> headList;

	/** 横行数据 */
	private List<List<Object>> rowDataList;

	/** 横行数据 */
	private List<String> dataTypeList;

	/** 导出类型 xls,xlsx */
	private String excelType;

	/** sheet页名称 */
	private String sheetName;

	/** 交叉表，两个表头交叉处 文本 */
	private String crossHeadText = "";

	/** 开始填充行 */
	private Integer startRow = 0;

	/** 开始填充列 */
	private Integer startCol = 0;

	/** 记录Excel填充到哪一行了 */
	private Integer rowNumber;

	/** 默认列宽 */
	private Integer defaultColWidth = 10;

	/** 数据类型 */
	private String dataCellType = DATA_CELL_TYPE_STRING;

	/** 强制指定数据单元格水平对齐方式 */
	private HorizontalAlignment dataCellAlign = HorizontalAlignment.CENTER;

	/** 标题行高(像素) */
	private Short titleRowHeight = 0;

	/** 标题字体高度(像素) */
	private Short titleFontHeight = 2;

	/** 描述行高(像素) */
	private Short descRowHeight = 5;

	/** 描述字体高度(像素) */
	private Short descFontHeight = 10;

	/** 数据字体高度(像素) */
	private Short dataFontHeight = 10;

	/** 表头字体高度(像素) */
	private Short headFontHeight = 10;

	/** 列表头行高,设置后自动换行，将不能自动变化行高 */
	private Short colheadRowHeigh;

	/** 表头背景色 */
	private Short headForegroundColor = IndexedColors.GREY_40_PERCENT.getIndex();

	/** 是否有行表头 */
	private boolean isDataFstHead = false;

	/** 数据行行高,设置后自动换行，将不能自动变化行高 */
	private Short dataRowHeigh;

	/** 最终生成的excel */
	private Workbook destWorkbook;

	/** 标题样式 */
	private CellStyle titleStyle;;

	/** 列表头样式 */
	private CellStyle colHeadStyle;

	/** 行表头样式 */
	private CellStyle rowHeadStyle;

	/** 行表头样式 */
	private CellStyle descriptionStyle;

	/** 文本类数据样式 */
	private CellStyle stringDataCellStyle;

	/** 整数类数据样式 */
	private CellStyle integerDataCellStyle;

	/** 小数类数据样式 */
	private CellStyle floatDataCellStyle;

	/**
	 * excel导出
	 * 
	 * @param out 输出流
	 * @throws IOException IO异常
	 */
	public void export(OutputStream out) throws IOException {
		Workbook workbook = getWorkbook();
		workbook.write(out);
	}

	/**
	 * 获取excel
	 * 
	 * @return Workbook
	 */
	public Workbook getWorkbook() {

		// 创建一个空白的Workbook
		createBlankWorkbook();

		if (destWorkbook != null) {
			// 创建样式
			createCellStyles();

			// 填充标题
			fillTitle();

			// 填充注释
			fillDiscription();
			if (headList != null) {
				// 填充横表头
				fillColHead();
				if (rowDataList != null) {
					// 填充数据
					fillData();
				}
			}
		}
		return this.destWorkbook;

	}

	/**
	 * 
	 * 创建一个空白的Workbook
	 */
	protected void createBlankWorkbook() {

		// 07格式
		if (EXCEL_TYPE_XLSX.equals(excelType)) {
			destWorkbook = new XSSFWorkbook();
		}
		// 03格式
		else if (EXCEL_TYPE_XLS.equals(excelType)) {
			destWorkbook = new HSSFWorkbook();
		}
		// 其他
		else {
			destWorkbook = null;
			return;
		}

		// 创建空白sheet页
		sheetName = (sheetName == null) ? "sheet1" : sheetName;
		Sheet sheet = destWorkbook.createSheet(sheetName);

		// 设置列的默认宽度
		sheet.setDefaultColumnWidth(defaultColWidth);

		// 记录Excel填充到哪一行了(开始是初始行)
		rowNumber = startRow;

	}

	/**
	 * 创建样式 <一句话功能简述> <功能详细描述>
	 * 
	 * @see [类、类#方法、类#成员]
	 */
	protected void createCellStyles() {

		/**
		 * 标题样式
		 */
		titleStyle = destWorkbook.createCellStyle();

		// 水平居中
		titleStyle.setAlignment(HorizontalAlignment.CENTER);

		// 垂直居中
		titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		// 设置字体
		Font titleFont = destWorkbook.createFont();

		// 字体高度
		titleFont.setFontHeightInPoints(titleFontHeight);

		// 加粗
		titleFont.setBold(true);
		titleStyle.setFont(titleFont);

		/**
		 * 描述样式
		 */
		descriptionStyle = destWorkbook.createCellStyle();

		// 水平居中
		descriptionStyle.setAlignment(HorizontalAlignment.LEFT);

		// 垂直居中
		descriptionStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		// 设置字体
		Font descriptionfont = destWorkbook.createFont();

		// 字体高度
		descriptionfont.setFontHeightInPoints(descFontHeight);
		descriptionStyle.setFont(descriptionfont);

		/**
		 * 列表头样式
		 */
		colHeadStyle = destWorkbook.createCellStyle();

		// 水平居中
		colHeadStyle.setAlignment(HorizontalAlignment.CENTER);

		// 垂直居中
		colHeadStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		// 是否自动换行
		colHeadStyle.setWrapText(true);

		// 设置背景色
		colHeadStyle.setFillForegroundColor(headForegroundColor);
		colHeadStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		// 设置字体
		Font colHeadfont = destWorkbook.createFont();

		// 设置字体高度
		colHeadfont.setFontHeightInPoints(headFontHeight);

		// 加粗
		colHeadfont.setBold(true);
		colHeadStyle.setFont(colHeadfont);

		// 设置边框 --> 黑色,中等粗细
		colHeadStyle.setBorderBottom(BorderStyle.THIN);
		colHeadStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		colHeadStyle.setBorderLeft(BorderStyle.THIN);
		colHeadStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		colHeadStyle.setBorderRight(BorderStyle.THIN);
		colHeadStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		colHeadStyle.setBorderTop(BorderStyle.THIN);
		colHeadStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());

		/**
		 * 行表头样式
		 */
		rowHeadStyle = destWorkbook.createCellStyle();
		rowHeadStyle.setAlignment(HorizontalAlignment.CENTER);

		// 设置背景色
		rowHeadStyle.setFillForegroundColor(headForegroundColor);
		rowHeadStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		// 设置字体
		Font rowHeadfont = destWorkbook.createFont();

		// 设置字体高度
		rowHeadfont.setFontHeightInPoints(headFontHeight);

		// 加粗
		rowHeadfont.setBold(true);
		rowHeadStyle.setFont(rowHeadfont);

		// 垂直居中
		rowHeadStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		// 是否自动换行
		rowHeadStyle.setWrapText(true);

		// 设置边框 --> 黑色,中等粗细
		rowHeadStyle.setBorderBottom(BorderStyle.THIN);
		rowHeadStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		rowHeadStyle.setBorderLeft(BorderStyle.THIN);
		rowHeadStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		rowHeadStyle.setBorderRight(BorderStyle.THIN);
		rowHeadStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		rowHeadStyle.setBorderTop(BorderStyle.THIN);
		rowHeadStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());

		/**
		 * 文本类数据样式
		 */
		stringDataCellStyle = destWorkbook.createCellStyle();
		Font stringDatafont = destWorkbook.createFont();

		// 设置字体高度
		stringDatafont.setFontHeightInPoints(dataFontHeight);
		stringDataCellStyle.setFont(stringDatafont);

		// 垂直居中
		stringDataCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		// 是否自动换行
		stringDataCellStyle.setWrapText(true);

		// 设置边框 --> 黑色,中等粗细
		stringDataCellStyle.setBorderBottom(BorderStyle.THIN);
		stringDataCellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		stringDataCellStyle.setBorderLeft(BorderStyle.THIN);
		stringDataCellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		stringDataCellStyle.setBorderRight(BorderStyle.THIN);
		stringDataCellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		stringDataCellStyle.setBorderTop(BorderStyle.THIN);
		stringDataCellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());

		/**
		 * 整数类数据样式
		 */
		integerDataCellStyle = destWorkbook.createCellStyle();
		Font integerDatafont = destWorkbook.createFont();

		// 设置字体高度
		integerDatafont.setFontHeightInPoints(dataFontHeight);
		integerDataCellStyle.setFont(integerDatafont);

		// 垂直居中
		integerDataCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		// 是否自动换行
		integerDataCellStyle.setWrapText(true);

		// 设置边框 --> 黑色,中等粗细
		integerDataCellStyle.setBorderBottom(BorderStyle.THIN);
		integerDataCellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		integerDataCellStyle.setBorderLeft(BorderStyle.THIN);
		integerDataCellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		integerDataCellStyle.setBorderRight(BorderStyle.THIN);
		integerDataCellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		integerDataCellStyle.setBorderTop(BorderStyle.THIN);
		integerDataCellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());

		// 整数类型 （带千分位,无小数位 ）
		DataFormat integerFormat = destWorkbook.createDataFormat();
		integerDataCellStyle.setDataFormat(integerFormat.getFormat("#,##0"));

		/**
		 * 小数类数据样式
		 */
		floatDataCellStyle = destWorkbook.createCellStyle();
		Font floatDatafont = destWorkbook.createFont();

		// 设置字体高度
		floatDatafont.setFontHeightInPoints(dataFontHeight);
		floatDataCellStyle.setFont(floatDatafont);

		// 垂直居中
		floatDataCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		// 是否自动换行
		floatDataCellStyle.setWrapText(true);

		// 设置边框 --> 黑色,中等粗细
		floatDataCellStyle.setBorderBottom(BorderStyle.THIN);
		floatDataCellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		floatDataCellStyle.setBorderLeft(BorderStyle.THIN);
		floatDataCellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		floatDataCellStyle.setBorderRight(BorderStyle.THIN);
		floatDataCellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		floatDataCellStyle.setBorderTop(BorderStyle.THIN);
		floatDataCellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());

		// 整数类型 （带千分位,无小数位 ）
		DataFormat floatFormat = destWorkbook.createDataFormat();
		floatDataCellStyle.setDataFormat(floatFormat.getFormat("#,##0.0000"));

	}

	/**
	 * 填充标题 <功能详细描述>
	 * 
	 * @see [类、类#方法、类#成员]
	 */
	protected void fillTitle() {
		if (title == null) {
			return;
		}
		Sheet sheet = destWorkbook.getSheet(sheetName);
		Row row = sheet.createRow(rowNumber);
		row.setHeightInPoints(titleRowHeight);
		Cell titleCell = row.createCell(startCol, CellType.STRING);
		titleCell.setCellValue(title);
		titleCell.setCellStyle(titleStyle);

		// 合并单元格
		int mergedColCount = 8;

		// 如果有数据 合并数=数据列数
		if (rowDataList != null && rowDataList.size() > 0) {
			mergedColCount = rowDataList.get(0).size();
		} else if (headList != null && headList.size() > 0) {
			mergedColCount = headList.size();
		}

		sheet.addMergedRegion(new CellRangeAddress(rowNumber, rowNumber, startCol,
				startCol + mergedColCount - 1));

		// 记录Excel填充到哪一行了
		rowNumber += 1;
	}

	/**
	 * 数据处理 <功能详细描述>
	 * 
	 * @see [类、类#方法、类#成员]
	 */
	protected void fillDiscription() {
		if (description != null && description.trim().length() > 0) {
			Sheet sheet = destWorkbook.getSheet(sheetName);
			Row row = sheet.createRow(rowNumber);
			row.setHeightInPoints(descRowHeight);
			Cell descriptionCell = row.createCell(startCol, CellType.STRING);
			descriptionCell.setCellValue(description);
			descriptionCell.setCellStyle(descriptionStyle);

			// 合并单元格
			int mergedColCount = 8;

			// 如果有数据 合并数=数据列数
			if (rowDataList != null && rowDataList.size() > 0) {
				mergedColCount = rowDataList.get(0).size();
			} else if (headList != null && headList.size() > 0) {
				mergedColCount = headList.size();
			}
			sheet.addMergedRegion(new CellRangeAddress(rowNumber, rowNumber, startCol,
					startCol + mergedColCount - 1));

			// 记录Excel填充到哪一行了
			rowNumber += 1;
		}
	}

	/**
	 * 填充列表头 <功能详细描述>
	 * 
	 * @see [类、类#方法、类#成员]
	 */
	protected void fillColHead() {

		Sheet sheet = destWorkbook.getSheet(sheetName);
		Row row = sheet.createRow(rowNumber);

		// 设置列表头高度
		if (colheadRowHeigh != null) {
			row.setHeightInPoints(colheadRowHeigh);
		}
		// 表头起始单元格位置
		int colHeadStartColNum = startCol;

		// 如果有行表头,则列表头往后推一格
		if (isDataFstHead && rowDataList != null && rowDataList.size() > 0) {
			// 填充推一格的文本
			if (this.crossHeadText != null) {
				Cell colHeadCell = row.createCell(colHeadStartColNum, CellType.STRING);
				colHeadCell.setCellValue(this.crossHeadText);
				colHeadCell.setCellStyle(colHeadStyle);
			}

			colHeadStartColNum += 1;
		}

		for (int i = 0; i < headList.size(); i++) {
			Cell colHeadCell = row.createCell(colHeadStartColNum + i, CellType.STRING);
			colHeadCell.setCellValue(headList.get(i));
			colHeadCell.setCellStyle(colHeadStyle);
		}

		// 记录Excel填充到哪一行了
		rowNumber += 1;
	}

	/**
	 * 填充数据 <功能详细描述>
	 * 
	 * @see [类、类#方法、类#成员]
	 */
	@SuppressWarnings("rawtypes")
	protected void fillData() {

		Sheet sheet = destWorkbook.getSheet(sheetName);
		for (int i = 0; i < rowDataList.size(); i++) {

			Row row = sheet.createRow(rowNumber);

			// 设置数据列高度
			if (dataRowHeigh != null) {
				row.setHeightInPoints(dataRowHeigh);
			}
			List<Object> rowData = rowDataList.get(i);

			for (int j = 0; j < rowData.size(); j++) {

				Cell cell = row.createCell(startCol + j);
				Object data = rowData.get(j);

				// 行表头
				if (j == 0 && isDataFstHead) {
					// 单元格为文本格式
					cell.setCellType(CellType.STRING);

					// 行表头样式
					cell.setCellStyle(rowHeadStyle);

					// 设置数据
					cell.setCellValue(StringUtil.parseNull(data));

				} else {

					// 强制指定了数据类型，则智能转换
					if (this.dataCellType != null) {

						if (DATA_CELL_TYPE_INTEGER.equals(this.dataCellType)) {
							data = Integer.parseInt(StringUtil.parseNull(data));
						}

						else if (DATA_CELL_TYPE_FLOAT.equals(this.dataCellType)) {
							data = Double.valueOf(StringUtil.parseNull(data));
						}

						else if (DATA_CELL_TYPE_STRING.equals(this.dataCellType)) {
							data = StringUtil.parseNull(data);
						}

					}

					// 数字类型
					if (data instanceof Number) {

						// 单元格为数字类型
						cell.setCellType(CellType.NUMERIC);
						// Integer, Long, Short,BigInteger
						List<Class> integerClassList = new ArrayList<Class>();
						integerClassList.add(Integer.class);
						integerClassList.add(Long.class);
						integerClassList.add(Short.class);
						integerClassList.add(BigInteger.class);

						// 整数类型 （带千分位,无小数位 ）
						if (integerClassList.contains(data.getClass())) {
							// 整数类数据样式
							cell.setCellStyle(integerDataCellStyle);
						}
						// 其他当带小数（带千分位,两位小数位 ）
						else {
							// 小数类数据样式
							cell.setCellStyle(floatDataCellStyle);
						}
						cell.setCellValue(Double.parseDouble(data.toString()));
					}
					// 其他当做文本处理
					else {
						// 单元格为文本格式
						cell.setCellType(CellType.NUMERIC);

						// 文本类数据样式
						cell.setCellStyle(stringDataCellStyle);

						// 设置数据
						cell.setCellValue(StringUtil.parseNull(data));
					}

					// 如果强制指定了水平对齐方式
					if (this.dataCellAlign != null) {
						cell.getCellStyle().setAlignment(HorizontalAlignment.CENTER);
					}
				}
			}
			// 记录Excel填充到哪一行了
			rowNumber++;
		}

	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getHeadList() {
		return headList;
	}

	public void setHeadList(List<String> headList) {
		this.headList = headList;
	}

	public List<List<Object>> getRowDataList() {
		return rowDataList;
	}

	public void setRowDataList(List<List<Object>> rowDataList) {
		this.rowDataList = rowDataList;
	}

	public short getHeadForegroundColor() {
		return headForegroundColor;
	}

	public void setHeadForegroundColor(short headForegroundColor) {
		this.headForegroundColor = headForegroundColor;
	}

	public String getExcelType() {
		return excelType;
	}

	public void setExcelType(String excelType) {
		this.excelType = excelType;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public Workbook getDestWorkbook() {
		return destWorkbook;
	}

	public void setDestWorkbook(Workbook destWorkbook) {
		this.destWorkbook = destWorkbook;
	}

	public Integer getStartRow() {
		return startRow;
	}

	public void setStartRow(Integer startRow) {
		this.startRow = startRow;
	}

	public Integer getStartCol() {
		return startCol;
	}

	public void setStartCol(Integer startCol) {
		this.startCol = startCol;
	}

	public Integer getDefaultColWidth() {
		return defaultColWidth;
	}

	public void setDefaultColWidth(Integer defaultColWidth) {
		this.defaultColWidth = defaultColWidth;
	}

	public short getTitleRowHeight() {
		return titleRowHeight;
	}

	public void setTitleRowHeight(short titleRowHeight) {
		this.titleRowHeight = titleRowHeight;
	}

	public short getTitleFontHeight() {
		return titleFontHeight;
	}

	public short getColheadRowHeigh() {
		return colheadRowHeigh;
	}

	public void setColheadRowHeigh(short colheadRowHeigh) {
		this.colheadRowHeigh = colheadRowHeigh;
	}

	public void setTitleFontHeight(short titleFontHeight) {
		this.titleFontHeight = titleFontHeight;
	}

	public short getHeadFontHeight() {
		return headFontHeight;
	}

	public void setHeadFontHeight(short headFontHeight) {
		this.headFontHeight = headFontHeight;
	}

	public boolean isDataFstHead() {
		return isDataFstHead;
	}

	public void setDataFstHead(boolean isDataFstHeadTemp) {
		this.isDataFstHead = isDataFstHeadTemp;
	}

	public short getDataRowHeigh() {
		return dataRowHeigh;
	}

	public void setDataRowHeigh(Short dataRowHeigh) {
		this.dataRowHeigh = dataRowHeigh;
	}

	public List<String> getDataTypeList() {
		return dataTypeList;
	}

	public void setDataTypeList(List<String> dataTypeList) {
		this.dataTypeList = dataTypeList;
	}

	public Short getDataFontHeight() {
		return dataFontHeight;
	}

	public void setDataFontHeight(Short dataFontHeight) {
		this.dataFontHeight = dataFontHeight;
	}

	public String getCrossHeadText() {
		return crossHeadText;
	}

	public void setCrossHeadText(String crossHeadText) {
		this.crossHeadText = crossHeadText;
	}

	public String getDataCellType() {
		return dataCellType;
	}

	public void setDataCellType(String dataCellType) {
		this.dataCellType = dataCellType;
	}

	

}
