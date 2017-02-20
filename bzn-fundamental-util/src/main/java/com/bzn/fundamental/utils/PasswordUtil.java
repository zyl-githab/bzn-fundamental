package com.bzn.fundamental.utils;


public class PasswordUtil {
	
	private static final int SALT_SIZE = 8;
	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_ITERATIONS = 1024;

	public static String generateSalt() {
		byte[] salt = DigestUtils.generateSalt(SALT_SIZE);
		return EncodeUtils.encodeHex(salt);
	}

	public static String hashPassword(String plainPassword, String salt) {
		byte[] hashPassword = DigestUtils.sha1(plainPassword.getBytes(), EncodeUtils.decodeHex(salt), HASH_ITERATIONS);
		return EncodeUtils.encodeHex(hashPassword);
	}

	public static void main(String[] args) {
		System.out.println(hashPassword("XHSJ!2#4QWEqwe", "d0e35c15b0df867c"));
		System.out.println(hashPassword("XHSJ!2#4QWEqwe", "b27af59517895ae9"));
	}
}