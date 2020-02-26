package com.zx.yunqishe.common.utils;

import com.alibaba.druid.sql.visitor.functions.Char;

import java.util.Random;
import java.util.UUID;

public class Generator {
	private static final Integer DEFAULT_LEN = 6;
	private static final String DICT = "abcdefg1234567890";

	/**
	 * 获取UUID
	 * @return
	 */
	public static String UUID() {
		UUID uuid = null;
		synchronized(""){
			uuid = UUID.randomUUID();
		}
		String uid = uuid.toString().replaceAll("-", "");
		return uid;
	}

	/**
	 * 获取随机字符串
	 * @param len 字符串长度
	 * @return 随机字符串
	 */
	public static String getRandomStr(Integer len) {
		if (len == null) len = DEFAULT_LEN;
		int dlen = DICT.length();
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < len; i++) {
			int index = random.nextInt(dlen);
			char c = DICT.charAt(index);
			sb.append(c);
		}
		return sb.toString();
	}

	/**
	 * 根据指定长度获取随机数字字符串
	 * @param len 长度
	 * @return 返回指定长度的数字字符串
	 */
	public static String getRandomNum(Integer len) {
		if (len == null) len = 6;
		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < len; i++) {
			int num = random.nextInt(10);
			sb.append(num);
		}
		return sb.toString();
	}
}
