package org.nico.ratel.landlords.print;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SimpleWriter {

	private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	
	public static String write(String message) {
		System.out.println();
		System.out.print("[ratel@" + message + "]$ ");
		try {
			return write();
		}finally {
			System.out.println();
		}
	}

	public static String write() {
		try {
			return reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
