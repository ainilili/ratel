package org.nico.ratel.landlords.helper;

/** 
 * 
 * @author nico
 * @version createTime：2018年11月4日 下午6:29:32
 */

public class TimeHelper {

	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException ignored) {
		}
	}
}
