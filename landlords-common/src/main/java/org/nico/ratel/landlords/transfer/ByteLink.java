package org.nico.ratel.landlords.transfer;

public class ByteLink {

	private ByteNode start;

	private ByteNode current;

	private int size;

	public void append(byte b) {
		if (start == null) {
			start = new ByteNode(b);
			current = start;
		} else {
			ByteNode node = new ByteNode(b);
			current.setNext(node);
			current = node;
		}
		size++;
	}

	public void append(byte[] bs) {
		if (bs != null) {
			for (byte b : bs) {
				append(b);
			}
		}
	}

	public byte[] toArray() {
		if (size == 0) {
			return null;
		}
		byte[] bytes = new byte[size];
		int index = 0;
		ByteNode s = start.clone();
		while (s != null) {
			bytes[index++] = s.getB();
			s = s.getNext();
		}
		return bytes;
	}

	public static class ByteNode {

		private byte b;

		private ByteNode next;

		public ByteNode(byte b) {
			this.b = b;
		}

		public ByteNode(byte b, ByteNode next) {
			this.b = b;
			this.next = next;
		}

		protected ByteNode clone() {
			return new ByteNode(b, next);
		}

		public byte getB() {
			return b;
		}

		public void setB(byte b) {
			this.b = b;
		}

		public ByteNode getNext() {
			return next;
		}

		public void setNext(ByteNode next) {
			this.next = next;
		}

	}
}