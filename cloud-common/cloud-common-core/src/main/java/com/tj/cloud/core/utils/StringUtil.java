package com.tj.cloud.core.utils;

import cn.hutool.core.util.ArrayUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.helpers.MessageFormatter;

import java.util.Collections;

/**
 * * @Author codingMan_tj * @Date 2024/3/29 17:31 * @version v1.0.0 * @desc
 **/
public class StringUtil {

	/**
	 * 逗号分隔
	 */
	public static String join(Iterable<String> strings) {
		return join(strings, ",");
	}

	public static String join(Iterable<String> iterable, String split) {
		StringBuilder sb = new StringBuilder();
		for (String str : iterable) {
			sb.append(str);
			sb.append(split);
		}
		return sb.substring(0, sb.length() - split.length());
	}

	/**
	 * 把字符串的第一个字母转为大写
	 */
	public static String upperFirst(String str) {
		return toFirst(str, true);
	}

	/**
	 * 判断字符串非空
	 */
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	/**
	 * 判断字符串非空
	 */
	public static boolean isNotEmpty(Object str) {
		if (str == null) {
			return false;
		}
		return !isEmpty(str.toString());
	}

	/**
	 * 判断字符串是否为空
	 */
	public static boolean isEmpty(String str) {

		if (str == null)
			return true;

		if (str.trim().equals(""))
			return true;

		return false;
	}

	public static boolean isEmpty(Object str) {
		if (str instanceof String) {
			return isEmpty(str.toString());
		}
		return str == null;
	}

	/**
	 * 为空判断,0做空处理。 这里判断： 1.字符串为NULL 2.字符串为空串。 3.字符串为0。
	 */
	public static boolean isZeroEmpty(String tmp) {
		boolean isEmpty = StringUtil.isEmpty(tmp);
		if (isEmpty)
			return true;

		return "0".equals(tmp);
	}

	/**
	 * 非空判断
	 */
	public static boolean isNotZeroEmpty(String tmp) {
		return !isZeroEmpty(tmp);
	}

	/**
	 * 把字符串的第一个字母转为小写
	 */
	public static String lowerFirst(String str) {
		return toFirst(str, false);
	}

	/**
	 * 把字符串的第一个字母转为大写或者小写
	 */
	public static String toFirst(String str, boolean isUpper) {
		if (StringUtils.isEmpty(str))
			return "";
		char first = str.charAt(0);
		String firstChar = new String(new char[] { first });
		firstChar = isUpper ? firstChar.toUpperCase() : firstChar.toLowerCase();
		return firstChar + str.substring(1);
	}

	/**
	 * 将数据库字段名转为DataGrid字段名 isIgnoreFirst:是否忽略第一个字段不转大写
	 */
	public static String convertDbFieldToField(String dbField) {
		return convertDbFieldToField(dbField, "_", true);
	}

	/**
	 * 将数据库字段名转为DataGrid字段名,如 sys_data_ 转为sysData symbol:间隔符号
	 */
	public static String convertDbFieldToField(String dbField, String symbol, boolean isIgnoreFirst) {
		StringBuilder result = new StringBuilder();
		if (dbField.startsWith(symbol)) {
			dbField = dbField.substring(1);
		}
		if (dbField.endsWith(symbol)) {
			dbField = dbField.substring(0, dbField.length() - 1);
		}
		String[] arr = dbField.split(symbol);
		for (int i = 0; i < arr.length; i++) {
			String str = arr[i];
			if (isIgnoreFirst && i != 0) {
				char oldChar = str.charAt(0);
				char newChar = (oldChar + "").toUpperCase().charAt(0);
				str = newChar + str.substring(1);
			}
			result.append(str);
		}
		return result.toString();
	}

	public static String[] getStringAryByStr(String str) {
		if (StringUtil.isEmpty(str)) {
			Collections.emptyList();
		}

		return str.split(",");
	}

	/**
	 * 数组合并
	 */
	public static String join(String[] values, String separator) {
		if (ArrayUtil.isEmpty(values))
			return "";
		String val = "";
		for (int i = 0; i < values.length; i++) {
			if (i == 0) {
				val += values[i];
			}
			else {
				val += separator + values[i];
			}
		}
		return val;
	}

	/**
	 * 对字符串去掉前面的指定字符
	 */
	public static String trimSuffix(String content, String suffix) {
		String resultStr = content;
		while (resultStr.endsWith(suffix)) {
			resultStr = resultStr.substring(0, resultStr.length() - suffix.length());
		}
		return resultStr;
	}

	/**
	 * 对字符串去掉前面的指定字符
	 */
	public static String trimPrefix(String content, String prefix) {
		String resultStr = content;
		while (resultStr.startsWith(prefix)) {
			resultStr = resultStr.substring(prefix.length());
		}
		return resultStr;
	}

	/**
	 * 截取字符串
	 */
	public static String truncateStr(String src, int length) {
		assert src != null;

		if (src.length() >= length) {
			return src.substring(0, length);
		}

		return src;
	}

	/**
	 * 重置json对象
	 */
	public static String resetJsonStr(String json) {
		if (StringUtils.isEmpty(json)) {
			return "";
		}
		json = json.replaceAll("\"\\{", "\\{").replaceAll("\\}\"", "\\}").replaceAll("\"\\[", "\\[").replaceAll("\\]\"",
				"\\]");
		json = json.replaceAll("\\\\n", "#n#n#n#").replaceAll("\\\\t", "#t#t#t#").replaceAll("\\\\", "");
		json = json.replaceAll("#n#n#n#", "\\\\n").replaceAll("#t#t#t#", "\\\\t");
		return json;
	}

	/**
	 * 将 &quot 变为 "
	 */
	public static String quotToStr(String str) {
		if (StringUtils.isEmpty(str)) {
			return "";
		}
		str = str.replaceAll("\\\\&quot;", "\"").replaceAll("&quot;", "\"");// .replaceAll(QutoConstants.SINGLE_QUOTE,
																			// "'");//.replaceAll("&#39;",
																			// "\"")
		if (str.startsWith("\"{") || str.startsWith("\"[")) {
			str = str.substring(1, str.length());
		}
		if (str.endsWith("}\"") || str.endsWith("]\"")) {
			str = str.substring(0, str.length() - 1);
		}
		return str;
	}

	public static String ifNullReturnEmpty(Object obj) {
		if (obj == null) {
			return "";
		}
		return obj.toString();
	}

	public static String underlineToCamel(String param) {
		if (param == null || "".equals(param.trim())) {
			return "";
		}
		String[] split = param.split("_");

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < split.length; i++) {
			if (i == 0) {
				sb.append(split[i].toLowerCase());
			}
			else {
				sb.append(capitalFirstLetter(split[i].toLowerCase()));
			}
		}

		return sb.toString();
	}

	/**
	 * 获取首字母大写
	 **/
	public static String capitalFirstLetter(String s) {
		int len = length(s);
		if (len == 0) {
			return s;
		}
		if (len == 1) {
			return String.valueOf(Character.toUpperCase(s.charAt(0)));
		}
		return Character.toUpperCase(s.charAt(0)) + s.substring(1);
	}

	/**
	 * 长度
	 **/
	public static int length(String s) {
		return s != null ? s.length() : 0;
	}

	public static String format(String msg, Object... params) {
		if (isEmpty(msg) || params == null || params.length == 0) {
			return msg;
		}
		return MessageFormatter.arrayFormat(msg, params).getMessage();
	}

}
