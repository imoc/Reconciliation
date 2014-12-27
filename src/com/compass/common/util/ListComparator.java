package com.compass.common.util;

import java.util.Comparator;

public class ListComparator implements Comparator<String> {

	@Override
	public int compare(String a, String b) {
		if (a.compareTo(b) > 0) {
			return 1;
		} else {
			return -1;
		}
	}

}
