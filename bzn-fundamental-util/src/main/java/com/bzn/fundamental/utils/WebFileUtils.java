package com.bzn.fundamental.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;

public class WebFileUtils {

	public static void download(HttpServletRequest request, HttpServletResponse response,
			String dlname, String filePath) {
		try {
			response.setCharacterEncoding("utf-8");
			response.setContentType("multipart/form-data");
			response.setHeader("Content-Disposition",
					"attachment;fileName=" + processFileName(request, dlname));
			OutputStream out = null;
			File file = new File(filePath);
			out = response.getOutputStream();
			FileCopyUtils.copy(new FileInputStream(file), out);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 文件下载
	 * 
	 * @throws IOException
	 */
	public static ResponseEntity<byte[]> downloadFile(HttpServletRequest request, File file,
			String dlName) throws IOException {
		// 解决中文名称乱码问题
		String fileName = processFileName(request, dlName);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData("attachment", fileName);
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		return new ResponseEntity<byte[]>(org.apache.commons.io.FileUtils.readFileToByteArray(file),
				headers, HttpStatus.OK);
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
	 * 使用时间戳作为文件名称
	 */
	public static String getTimeMillisFileName() {
		// 使用时间戳作为文件名称
		Random random = new Random();
		return (System.currentTimeMillis() + String.valueOf(random.nextInt(10000)));
	}

	/**
	 * 解决IE, Chrome, Firfox下文件名乱码问题
	 *
	 * @param fileName
	 * @throws UnsupportedEncodingException
	 */
	public static String processFileName(HttpServletRequest request, String fileName)
			throws UnsupportedEncodingException {
		// 获取浏览器类型
		boolean isIE = isMSIE(request);
		if (isIE) {
			fileName = URLEncoder.encode(fileName, "UTF-8");
		} else {
			fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
		}
		return fileName;
	}

	/**
	 * 判断客户端浏览器类型是否为IE
	 */
	public static boolean isMSIE(HttpServletRequest request) {
		String agent = request.getHeader("USER-AGENT");
		// IE
		if ((null != agent && -1 != agent.indexOf("MSIE"))
				|| (null != agent && -1 != agent.indexOf("Trident"))) {
			return true;
		} else { // Firefox, Chrome等
			return false;
		}
	}

}