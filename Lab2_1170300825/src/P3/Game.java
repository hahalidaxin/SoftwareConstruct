package P3;

import static org.junit.Assert.assertNotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


/**
 * 	�����������������ļ���˵����
 * 	��һ��Ϊ�����������ֱ�Ϊ��������boardType�����̴�СboardSize
 * 	�ڶ���Ϊһ������na������playerA��ӵ�е�������Ŀ
 * 	����na�У�ÿ�а����������ͣ������Ҫ��ʼ������λ�ã������������ӳ�ʼ��������
 *	������һ��һ������nb������playerB��ӵ�е����ӵ������������Ŀ
 *	����nb�У�ÿ�а����������ͣ������Ҫ��ʼ������λ�ã������������ӳ�ʼ��������
 * 
 * @author DaxinLi
 *
 */
public class Game {
	/**
	 * gameType
	 * chess ��������
	 * go Χ��
	 */
	private String gameType;
	private Board gameBoard;
	private Action gameAction;
	
	private Player playerA,playerB;
	
	public Game(String gameType) {
		this.gameType = gameType;
	}
	
	public void iniGameWithPlayerName(String paName,String pbName) {
		this.playerA = new Player(paName);
		this.playerB = new Player(pbName);
		
		BufferedReader reader = null;
		try {
			File configFile=new File(String.format("src/P3/%s_config.txt",this.gameType));
			reader = new BufferedReader(new FileReader(configFile));
			String[] splitItems=reader.readLine().split("\\s");
			gameBoard = new Board(Integer.valueOf(splitItems[0]), Integer.valueOf(splitItems[1]));
			int totalTypeLen = Integer.valueOf(reader.readLine());
			for(int i=0;i<totalTypeLen;i++) {
				splitItems = reader.readLine().split("\\s");
				if(splitItems.length==1) {
//					��ʱû�г�ʼ��λ��
					playerA.addPiece(new Piece(splitItems[0]));
				} else if(splitItems.length==3) {
//					��ʱ��Ҫ��ʼ�����ӵ�λ��
					int px = Integer.valueOf(splitItems[1]),py = Integer.valueOf(splitItems[2]);
					Piece thisPiece = new Piece(splitItems[0],px,py);
					playerA.addPiece(thisPiece);
					gameBoard.setPieceAtCord(px, py, thisPiece);
				}
			}
			
			totalTypeLen = Integer.valueOf(reader.readLine());
			for(int i=0;i<totalTypeLen;i++) {
				splitItems = reader.readLine().split("\\s");
				if(splitItems.length==1) {
//					��ʱû�г�ʼ��λ��
					playerB.addPiece(new Piece(splitItems[0]));
				} else if(splitItems.length==3) {
//					��ʱ��Ҫ��ʼ�����ӵ�λ��
					int px = Integer.valueOf(splitItems[1]),py = Integer.valueOf(splitItems[2]);
					Piece thisPiece = new Piece(splitItems[0],px,py);
					playerB.addPiece(thisPiece);
					gameBoard.setPieceAtCord(px, py, thisPiece);
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				reader.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		gameAction = new Action(gameBoard, playerA, playerB);
	}
	
	/**
	 * 	����
	 * @param player
	 * @param piece
	 * @param px
	 * @param py
	 */
	public void putPiece(Player player,Piece piece,Position pos) throws MyExp{
		try {
			gameAction.putPiece(player, piece, pos);
		} catch (MyExp e) {
			// TODO: handle exception
				throw e;
		}
	}
	
	
	/**
	 * 	�ƶ�����
	 * @param player
	 * @param piece
	 * @param stX
	 * @param stY
	 * @param edX
	 * @param edY
	 */
	public void movePiece(Player player,Position st,Position ed) throws MyExp{
		try {
			gameAction.movePiece(player, st, ed);
		} catch (MyExp e) {
			// TODO: handle exception
				throw e;
		}
	}
	
	/**
	 * 	����
	 * 	��ָ��λ�õĶ��ֵ������Ƴ�
	 * @param player
	 * @param px
	 * @param py
	 */
	public void removePiece(Player player,Position pos) throws MyExp{
		try {
			gameAction.removePiece(player, pos);
		} catch (MyExp e) {
			// TODO: handle exception
				throw e;
		}
	}
	
	/**
	 * 	����
	 * 	��Ŀ��λ���϶��ֵ������Ƴ�����player��ʼλ�õ������ƶ���Ŀ��λ����
	 * @param player
	 * @param px
	 * @param py
	 */
	public void eatPiece(Player player,Position st,Position ed) throws MyExp{
		try {
			gameAction.eatPiece(player,st, ed);
		} catch (MyExp e) {
			// TODO: handle exception
				throw e;
		}
	}

	/**
	 *	�����ָ��λ�õ����ӵ�player
	 * @param pos
	 * @return
	 */
	public Player getOwnerAtCord(Position pos) throws MyExp{
		try {
			MyExp.assertTrue(gameBoard.isCordAvailable(pos.x(), pos.y()), "����λ����Ϸ�");
		}catch (MyExp e) {
			// TODO: handle exception
			throw e;
		}
		
		Piece piece = gameBoard.getPieceAtCord(pos.x(), pos.y());
		if(piece==null) {
			return null;
		} else {
			if(playerA.isContainPiece(piece)) {
				return playerA;
			} else {
				return playerB;
			}
		}
	}
	/**
	 *	�����ָ��λ�õ�����
	 * @param pos
	 * @return
	 */
	public Piece getPieceAtCord(Position pos) throws MyExp{
		try {
			MyExp.assertTrue(gameBoard.isCordAvailable(pos.x(), pos.y()), "����λ����Ϸ�");
		}catch (MyExp e) {
			// TODO: handle exception
			throw e;
		}
		
		if(!gameBoard.isCordAvailable(pos.x(), pos.y())) return null;
		
		return gameBoard.getPieceAtCord(pos.x(), pos.y());
	}
	/**
	 * 	��ȡ�û��������ϵ�����������Ŀ������gameBoard. 
	 * 	getNumOfPlayerPiecesInBoard
	 * @param player
	 * @return
	 */
	
	public int getNumOfPlayerPiecesInBoard(Player player) {
		return gameBoard.getNumOfPlayerPiecesInBoard(player);
	}
	
	/*
	 * getter and setter
	 */
	
	public Board getGameBoard() {
		return gameBoard;
	}


	public Player getPlayerA() {
		return playerA;
	}


	public Player getPlayerB() {
		return playerB;
	}

	 
}
