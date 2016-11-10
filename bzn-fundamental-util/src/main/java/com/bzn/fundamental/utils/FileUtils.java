package com.bzn.fundamental.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

/**
 * 描述: 文件工具类</br>
 */
public class FileUtils {

	/**
	 * Java文件操作, 获取不带扩展名的文件名
	 * 
	 * @param fileName 原始文件名
	 * @return 不带扩展名的文件名
	 */
	public static String getFileName(String fileName) {
		if ((fileName != null) && (fileName.length() > 0)) {
			int dot = fileName.lastIndexOf('.');
			if ((dot > -1) && (dot < (fileName.length()))) {
				return fileName.substring(0, dot);
			}
		}
		return fileName;
	}

	/**
	 * 获取自定义文件名
	 * 
	 * @param prefix 前缀名
	 * @param suffix 后缀名
	 * @param ext 扩展名
	 * 
	 * @return 自定义格式文件名(prefix + suffix + ext)
	 */
	public static String getCustomFileName(String prefix, String suffix, String ext) {
		return prefix + suffix + ext;
	}

	/**
	 * 获取文件全限定名
	 * 
	 * @param rootPath 文件所在目录路径
	 * @param fileName 文件名
	 * @param ext 扩展名
	 * 
	 * @return 文件全限定名
	 */
	public static String getFullyFileName(String rootPath, String fileName, String ext) {
		return rootPath + File.separator + fileName + ext;
	}

	/**
	 * 连接获取新文件路径
	 *
	 * @param rootPath 文件所在目录路径
	 * @param tempDir 临时目录
	 * @param fileName 文件名称
	 * @return
	 */
	public static String geConcatFileName(String rootPath, String tempDir, String fileName) {
		// 获取模板文件全限定名
		return rootPath + File.separator + tempDir + File.separator + fileName;
	}

	/**
	 * 连接获取新文件路径
	 *
	 * @param rootPath 文件所在目录路径
	 * @param fileName 文件名称
	 * @param ext 扩展名
	 * @return
	 */
	public static String geConcatFileNameWithExt(String rootPath, String fileName, String ext) {
		// 获取模板文件全限定名
		return rootPath + File.separator + fileName + ext;
	}

	/**
	 * 获取PDF模板文件名
	 *
	 * @param rootPath 文件所在目录路径
	 * @param fileName PDF文件名
	 * @return
	 */
	public static String geConcatFileName(String rootPath, String tempDir, String fileName,
			String ext) {
		// 获取模板文件全限定名
		return rootPath + File.separator + tempDir + File.separator + fileName + ext;
	}

	/**
	 * 输出pdf文件流
	 *
	 * @param file
	 * @param response
	 * @throws IOException
	 */
	public static void outputPDFStream(File file, HttpServletResponse response) throws IOException {
		response.setContentType("application/pdf");
		response.addHeader("Content-Disposition", "attachment;filename=" + file.getName());
		FileInputStream inputStream = new FileInputStream(file);
		OutputStream outputStream = response.getOutputStream();
		int len = 0;
		byte[] buffer = new byte[1024];
		while ((len = inputStream.read(buffer)) > 0) {
			outputStream.write(buffer, 0, len);
		}
		outputStream.flush();
		outputStream.close();
		inputStream.close();
	}

	public static void outputPicStream(File file, HttpServletResponse response) throws IOException {
		String fileName = file.getName();
		String imageType = fileName.substring(fileName.indexOf(".") + 1);
		response.setContentType("image/" + imageType);
		// 设置禁止客户端缓存的Header.
		// Http 1.0 header
		response.setDateHeader("Expires", 1L);
		response.addHeader("Pragma", "no-cache");
		// Http 1.1 header
		response.setHeader("Cache-Control", "no-cache, no-store, max-age=0");
		ImageIO.write(ImageIO.read(file), imageType, response.getOutputStream());
	}

	/**
	 * 生成ZIP文件
	 *
	 * @throws IOException
	 */
	public static File generateZipFile(String[] fileNameList, String baseDir, String zipName)
			throws IOException {
		// 设置文件保存路径
		if (!baseDir.endsWith("/")) {
			baseDir += "/";
		}

		// 目录不存在则创建
		File dir = new File(baseDir);
		if (!dir.exists()) {
			dir.mkdirs();
		}

		// 创建新文件, 如果存在则删除
		File file = new File(baseDir + zipName);
		if (file.exists()) {
			file.delete();
		}

		byte[] buffer = new byte[1024];
		// 生成的ZIP文件
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(file));
		// 需要同时下载的文件
		File entryFile = null;
		for (int i = 0; i < fileNameList.length; i++) {
			entryFile = new File(fileNameList[i]);
			FileInputStream fis = new FileInputStream(entryFile);
			out.putNextEntry(new ZipEntry(entryFile.getName()));
			int len;
			// 读入需要下载的文件的内容, 打包到zip文件
			while ((len = fis.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}
			out.closeEntry();
			fis.close();
		}
		out.close();
		return file;
	}

	/**
	 * 移动文件
	 * 
	 * @param sourceFile 源文件（路径+文件）
	 * @param target 目标路径
	 */
	public static void moveFile(String sourceFile, String target) {
		File s = new File(sourceFile);
		File t = new File(target);
		if (!t.exists())
			t.mkdirs();
		File tf = new File(target + s.getName());
		s.renameTo(tf);
	}

}