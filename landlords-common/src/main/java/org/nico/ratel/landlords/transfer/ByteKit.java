package org.nico.ratel.landlords.transfer;

/**
 * Byte manipulation tool
 *
 * @author nico
 */
public class ByteKit {

	/**
	 * Target byte array
	 */
	private byte[] bytes;

	public ByteKit(byte[] bytes) {
		this.bytes = bytes;
	}

	/**
	 * Gets the index of the incoming array in the target array, not matched to return -1
	 *
	 * @param bs    Incoming array
	 * @param start Matching start index
	 * @return Match index, not match to return -1
	 */
	public int indexOf(byte[] bs, int start) {
		int targetIndex = -1;
		if (bs == null) {
			return targetIndex;
		}
		for (int index = start; index < bytes.length; index++) {
			byte cbyte = bytes[index];
			if (bs[0] == cbyte) {
				boolean isEquals = true;
				for (int sindex = 1; sindex < bs.length; sindex++) {
					if (index + sindex >= bytes.length || bs[sindex] != bytes[index + sindex]) {
						isEquals = false;
						break;
					}
				}
				if (isEquals) {
					targetIndex = index;
					break;
				}
			}
		}
		return targetIndex;
	}

	/**
	 * Gets the position of the byte byte in the byte array
	 *
	 * @param b     Byte
	 * @param start Matching start index
	 * @return Match index, not match to return -1
	 */
	public int indexOf(byte b, int start) {
		return indexOf(new byte[]{b}, start);
	}
}
