package com.bzn.fundamental.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomUtils;

/**
 * 随机码生成器
 * 
 * @author：fengli
 * @since：2016年9月2日 下午2:00:04
 * @version:
 */
public class RandCodeUtils {
	private final static String[] arr = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "0" };

	private final static String[] arr2 = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "A",
			"B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
			"S", "T", "U", "V", "W", "X", "Y", "Z", "a", "b", "c", "d", "e", "f", "g", "h", "i",
			"j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z" };

	private final static String[] arr3 = { "A", "B", "C", "D", "E", "F", "G", "H", "J", "K", "M",
			"N", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };

	/**
	 * 生产6位随机码
	 * 
	 * @return
	 */
	public static String getRandCode() {
		return getRandCode(6);
	}

	/**
	 * 生产length位随机码
	 * 
	 * @param length
	 * @return
	 */
	public static String getRandCode(int length) {
		StringBuilder sb = new StringBuilder("");
		for (int i = 0; i < length; i++) {
			sb.append(arr[(int) (Math.random() * arr.length)]);
		}
		return sb.toString();
	}

	/**
	 * 生成指定类型的字符串
	 * 
	 * @param length
	 * @param args
	 * @return
	 */
	public static String getRandCode(int length, String[] args) {
		StringBuilder sb = new StringBuilder("");
		for (int i = 0; i < length; i++) {
			sb.append(args[(int) (Math.random() * args.length)]);
		}
		return sb.toString();
	}

	public static String getRandomCode(int length, int strLength, String[] args) {
		String randomCode = RandCodeUtils.getRandCode(strLength, args);
		int strIndex = 0;
		StringBuilder sb = new StringBuilder("");
		for (int i = 0; i < length; i++) {
			int random = RandomUtils.nextInt(0, 2);
			if (random == 0 && strIndex < strLength) {
				sb.append(randomCode.charAt(strIndex++));
			} else {
				sb.append(RandomUtils.nextInt(0, 10));
			}
		}
		return sb.toString();
	}

	/**
	 * 生成唯一的随机字符串
	 * 
	 * @param length 总长度
	 * @param strLength 字符长度
	 * @param args
	 * @param sourceList 不能重复的原始串
	 * @param size 生成多少个
	 */
	public static List<String> uniqueRandomCode(int length, int strLength, List<String> sourceList,
			int size) {
		List<String> targetList = new ArrayList<>();
		if (sourceList == null) {
			sourceList = new ArrayList<>();
		}
		while (true) {
			String randomCode = getRandomCode(length, strLength, arr3);
			if (!sourceList.contains(randomCode) && !targetList.contains(randomCode)) {
				targetList.add(randomCode);
			}
			if (targetList.size() >= size) {
				break;
			}
		}
		return targetList;
	}

	/**
	 * 生成length验证码
	 * 
	 * @param length
	 * @return
	 */
	public static String getRandValidCode(int length) {
		StringBuilder sb = new StringBuilder("");
		for (int i = 0; i < length; i++) {
			sb.append(arr2[(int) (Math.random() * arr2.length)]);
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			System.out.println(uniqueRandomCode(12, 4, null, 500));
		}
	}
}
