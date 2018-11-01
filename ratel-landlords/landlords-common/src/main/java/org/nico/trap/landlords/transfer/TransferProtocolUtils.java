package org.nico.trap.landlords.transfer;

import org.nico.noson.Noson;

/**
 * Protocol transport related tools
 * 
 * @author nico
 * @time 2018-11-01 20:43
 */
public class TransferProtocolUtils {
	
	/**
	 * A protocol header that represents the beginning of an available stream of data
	 */
	public static final byte[] PROTOCOL_HAED = "#".getBytes();
	
	/**
	 * The end of the protocol used to represent the end of an available stream of data
	 */
	public static final byte[] PROTOCOL_TAIL = "$".getBytes();
	
	/**
	 * Serialize the poker list to transportable bytes
	 * 
	 * @param pokers Poker list
	 * @return Transportable byte array
	 */
	public static <T> byte[] serialize(T object) {
		ByteLink bl = new ByteLink();
		bl.append(PROTOCOL_HAED);
		bl.append(Noson.reversal(object).getBytes());
		bl.append(PROTOCOL_TAIL);
		return bl.toArray();
	}
	
	public static <T> T unserialize(byte[] bytes) {
		ByteLink bl = new ByteLink();
		
		return null;
	}
	
}
