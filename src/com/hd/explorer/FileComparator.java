package com.hd.explorer;

import java.io.File;
import java.util.Comparator;


/** 排序 **/
public class FileComparator implements Comparator<File> {

	public int compare(File file1, File file2) {
		// 文件夹排在前面
		if (file1.isDirectory() && !file2.isDirectory()) {
			return -1000;
		} else if (!file1.isDirectory() && file2.isDirectory()) {
			return 1000;
		}
		// 相同类型按名称排序
		return (file1.getName().toLowerCase()).compareTo(file2.getName().toLowerCase());
	}
}