package org.nico.ratel.landlords.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListUtils {

	public static <T> List<T> getList(T[] array) {
		List<T> list = new ArrayList<>(array.length);
		Collections.addAll(list, array);
		return list;
	}

	public static <T> List<T> getList(List<T>[] array) {
		List<T> list = new ArrayList<>(array.length);
		for (List<T> t : array) {
			list.addAll(t);
		}
		return list;
	}

	public static <T> List<T> getList(List<T> source) {
		List<T> list = new ArrayList<>(source.size());
		list.addAll(source);
		return list;
	}
}
