package org.nico.ratel.landlords.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class StreamUtils {

	/**
	 * Convert input stream to string
	 *
	 * @param inStream Input stream
	 * @return {@link String}
	 */
	public static String convertToString(InputStream inStream) {
		BufferedReader br = new BufferedReader(new InputStreamReader(inStream));
		StringBuilder reqStr = new StringBuilder();
		char[] buf = new char[2048];
		int len;
		try {
			while ((len = br.read(buf)) != -1) {
				reqStr.append(new String(buf, 0, len));
			}
			br.close();
		} catch (IOException e) {
			return null;
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return reqStr.toString();
	}

	public static String convertToString(URL url) throws IOException {
		URLConnection con = url.openConnection();
		con.setUseCaches(false);
		con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.193 Safari/537.36");
		return convertToString(con.getInputStream());
	}


}
