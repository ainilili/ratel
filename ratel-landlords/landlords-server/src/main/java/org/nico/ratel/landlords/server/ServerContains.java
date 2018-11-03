package org.nico.ratel.landlords.server;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.nico.ratel.landlords.entity.ClientSide;
import org.nico.ratel.landlords.entity.Room;

public class ServerContains {
	
	/**
	 * Server port
	 */
	public final static int PORT = 1024;
	
	/**
	 * The map of server side
	 */
	public final static Map<Integer, Room> ROOM_MAP = new ConcurrentSkipListMap<>();
	
	/**
	 * The list of client side
	 */
	public final static Map<Integer, ClientSide> CLIENT_SIDE_MAP = new ConcurrentSkipListMap<>();
	
	private final static AtomicInteger CLIENT_ATOMIC_ID = new AtomicInteger(1);
	
	private final static AtomicInteger SERVER_ATOMIC_ID = new AtomicInteger(1);
	
	public final static int getClientId() {
		return CLIENT_ATOMIC_ID.getAndIncrement();
	}
	
	public final static int getServerId() {
		return SERVER_ATOMIC_ID.getAndIncrement();
	}
}
