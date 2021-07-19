package org.nico.ratel.landlords.entity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.locks.ReentrantLock;

import org.nico.noson.annotations.JsonIgnore;
import org.nico.ratel.landlords.enums.RoomStatus;
import org.nico.ratel.landlords.enums.RoomType;

public class Room {

	private int id;

	private String roomOwner;

	private RoomStatus status;

	private RoomType type;

	private Map<Integer, ClientSide> clientSideMap;

	private LinkedList<ClientSide> clientSideList;

	private int landlordId = -1;

	private List<Poker> landlordPokers;

	private PokerSell lastPokerShell;

	private int lastSellClient = -1;

	private int currentSellClient = -1;

	private int difficultyCoefficient;

	private long lastFlushTime;

	private long createTime;

	private int firstSellClient;

	/** 观战者列表 */
	private List<ClientSide> watcherList = new ArrayList<>(5);

	private int scoreRate = 1;

	private int baseScore = 3;

	public Room() {
	}

	public Room(int id) {
		this.id = id;
		this.clientSideMap = new ConcurrentSkipListMap<>();
		this.clientSideList = new LinkedList<>();
		this.status = RoomStatus.WAIT;
		this.createTime = System.currentTimeMillis();
	}

	public int getScore() {
		return this.baseScore * this.scoreRate;
	}

	public int getBaseScore() {
		return this.baseScore;
	}

	public void setBaseScore(int baseScore) {
		this.baseScore = baseScore;
	}

	public int getScoreRate() {
		return this.scoreRate;
	}

	public void setScoreRate(int scoreRate) {
		this.scoreRate = scoreRate;
	}

	public void initScoreRate() {
		this.scoreRate = 1;
	}

	public void increaseRate() {
		this.scoreRate *= 2;
	}

	public final long getCreateTime() {
		return createTime;
	}

	public final void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public final int getDifficultyCoefficient() {
		return difficultyCoefficient;
	}

	public final void setDifficultyCoefficient(int difficultyCoefficient) {
		this.difficultyCoefficient = difficultyCoefficient;
	}

	public final RoomType getType() {
		return type;
	}

	public final void setType(RoomType type) {
		this.type = type;
	}

	public final PokerSell getLastPokerShell() {
		return lastPokerShell;
	}

	public final void setLastPokerShell(PokerSell lastPokerShell) {
		this.lastPokerShell = lastPokerShell;
	}

	public final int getCurrentSellClient() {
		return currentSellClient;
	}

	public final void setCurrentSellClient(int currentSellClient) {
		this.currentSellClient = currentSellClient;
	}

	public long getLastFlushTime() {
		return lastFlushTime;
	}

	public void setLastFlushTime(long lastFlushTime) {
		this.lastFlushTime = lastFlushTime;
	}

	public int getLastSellClient() {
		return lastSellClient;
	}

	public void setLastSellClient(int lastSellClient) {
		this.lastSellClient = lastSellClient;
	}

	public int getLandlordId() {
		return landlordId;
	}

	public void setLandlordId(int landlordId) {
		this.landlordId = landlordId;
	}

	public LinkedList<ClientSide> getClientSideList() {
		return clientSideList;
	}

	public void setClientSideList(LinkedList<ClientSide> clientSideList) {
		this.clientSideList = clientSideList;
	}

	public List<Poker> getLandlordPokers() {
		return landlordPokers;
	}

	public void setLandlordPokers(List<Poker> landlordPokers) {
		this.landlordPokers = landlordPokers;
	}

	public final String getRoomOwner() {
		return roomOwner;
	}

	public final void setRoomOwner(String roomOwner) {
		this.roomOwner = roomOwner;
	}

	public final int getId() {
		return id;
	}

	public final void setId(int id) {
		this.id = id;
	}

	public final RoomStatus getStatus() {
		return status;
	}

	public final void setStatus(RoomStatus status) {
		this.status = status;
	}

	public final Map<Integer, ClientSide> getClientSideMap() {
		return clientSideMap;
	}

	public final void setClientSideMap(Map<Integer, ClientSide> clientSideMap) {
		this.clientSideMap = clientSideMap;
	}

	public int getFirstSellClient() {
		return firstSellClient;
	}

	public void setFirstSellClient(int firstSellClient) {
		this.firstSellClient = firstSellClient;
	}

	public List<ClientSide> getWatcherList() {
		return watcherList;
	}
}
