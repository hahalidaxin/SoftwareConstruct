package P3;

import java.awt.List;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Player {
	private String playerName;
	private StringBuilder gameHistorySB = new StringBuilder();
	
//	�����ӵ�е����ӵļ���
	private Set<Piece> playerPieces = new HashSet<Piece>();
	
	public Player(String playerName) {
		this.playerName = playerName;
	}
	
	/**
	 * 	���������ӣ����������и����ӣ��򷵻�false��������playerPieces��Ӻ󷵻�true
	 * @param piece
	 * @return ���������и����ӣ��򷵻�false��������playerPieces��Ӻ󷵻�true
	 */

	public boolean addPiece(Piece piece) {
		if(playerPieces.contains(piece)) {
			 return false;
		}
		playerPieces.add(piece);
		return true;
	}
//	public boolean removePiece(Piece piece) {
//		if(playerPieces.contains(piece)) {
//			playerPieces.remove(piece);
//			return true;
//		} else {
//			return false;
//		}
//	}
	
	/**
	 * 	��ȡ����һ������predicate��Piece
	 * @param predicate
	 * @return ��ȡ������������㹦�ܺ���predicateҪ�������֮�е�����һ�������û�У��򷵻�null
	 */
	public Piece getAnyPieceByFilter(Predicate<? super Piece> predicate) {
		try {
			return  playerPieces.stream()
				.filter(predicate)
				.collect(Collectors.toList()).get(0);
		} catch(IndexOutOfBoundsException e) {
//			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * �жϸ��û��Ƿ��������������
	 * @param piece ����������
	 * @return ��������򷵻�true ���򷵻�false
	 */
	public boolean isContainPiece(Piece piece) {
		return playerPieces.contains(piece);
	}
	/**
	 * ��gameHistorySB�����һ���Ĳ���
	 * @param gameStep һ������
	 */
	public void addHistorty(String gameStep) {
		gameHistorySB.append(gameStep+"\n");
	}
	
	
	/*
	 * getter and setter
	 */
	
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public Set<Piece> getPlayerPieces() {
		return playerPieces;
	}
	public String getGameHistoryString() {
		return gameHistorySB.toString();
	}
	

	
}
