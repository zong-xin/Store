package cn.rain.store.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChangeCookie {
	/**
	 * 改变cookie里面的值
	 * 用于浏览记录功能的实现
	 * @param s1
	 *            原来的cookie里面的内容
	 * @param s2
	 *            要追加的新内容
	 * @return
	 */
	public static String change(String s1, String s2) {
		String[] split = s1.split(",");
		List<String> list = Arrays.asList(split);// 将数组转换为list集合
		List<String> arrayList = new ArrayList<String>(list);// 转换为ArrayLsit调用相关的remove方法
		if (list.contains(s2)) {// 加入集合中包含这个元素
			arrayList.remove(s2);
		}
		arrayList.add(0, s2);

		if (arrayList.size() > 6) {
			arrayList.remove(6);
		}

		StringBuffer sb = new StringBuffer();// 用于接收拼接字符串
		for (int x = 0; x < arrayList.size(); x++) {
			sb.append(arrayList.get(x) + ",");
		}

		return sb.toString();
	}
	//测试
	public static void main(String[] args) {
		String s1 = "a,b,c,d,e,f,g,";
		String s2 = "c";

		String s3 = ChangeCookie.change(s1, s2);
		System.out.println(s3);
	}

}
