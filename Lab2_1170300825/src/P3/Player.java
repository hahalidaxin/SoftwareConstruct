package P3;

import java.awt.List;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Player {
	private String playerName;
	private StringBuilder gameHistorySB = new StringBuilder();
	
//	玩家所拥有的棋子的集合
	private Set<Piece> playerPieces = new HashSet<Piece>();
	
	public Player(String playerName) {
		this.playerName = playerName;
	}
	
	/**
	 * 	玩家添加棋子，如果玩家已有该棋子，则返回false。否则向playerPieces添加后返回true
	 * @param piece
	 * @return 如果玩家已有该棋子，则返回false。否则向playerPieces添加后返回true
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
	 * 	获取任意一个满足predicate的Piece
	 * @param predicate
	 * @return 获取该玩家所有满足功能函数predicate要求的棋子之中的任意一个。如果没有，则返回null
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
	 * 判断该用户是否包含给定的棋子
	 * @param piece 给定的棋子
	 * @return 如果包含则返回true 否则返回false
	 */
	public boolean isContainPiece(Piece piece) {
		return playerPieces.contains(piece);
	}
	/**
	 * 向gameHistorySB中添加一步的操作
	 * @param gameStep 一步操作
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
