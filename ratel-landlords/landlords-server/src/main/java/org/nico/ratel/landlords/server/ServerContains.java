package org.nico.ratel.landlords.server;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

import org.nico.ratel.landlords.entity.ClientSide;
import org.nico.ratel.landlords.entity.ServerSide;

public class ServerContains {
	
	/**
	 * Server port
	 */
	public final static int PORT = 1024;
	
	/**
	 * The map of server side
	 */
	public final static Map<Integer, ServerSide> SERVER_SIDE_MAP = new ConcurrentHashMap<>();
	
	/**
	 * The list of client side
	 */
	public final static Queue<ClientSide> CLIENT_SIDE_LIST = new ConcurrentLinkedQueue<ClientSide>();
	
	private final static AtomicInteger CLIENT_ATOMIC_ID = new AtomicInteger(0);
	
	private final static AtomicInteger SERVER_ATOMIC_ID = new AtomicInteger(0);
	
	public final static int getClientId() {
		return CLIENT_ATOMIC_ID.getAndIncrement();
	}
	
	public final static int getServerId() {
		return SERVER_ATOMIC_ID.getAndIncrement();
	}
}
