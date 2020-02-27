package P3;

import static org.junit.Assert.assertNotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


/**
 * 	关于棋类配置类型文件的说明：
 * 	第一行为两个整数，分别为棋盘类型boardType和棋盘大小boardSize
 * 	第二行为一个整数na，代表playerA所拥有的棋子数目
 * 	以下na行，每行包含棋子类型，如果需要初始化棋子位置，则后面跟有棋子初始横纵坐标
 *	接下来一行一个整数nb，代表playerB所拥有的棋子的所有种类和数目
 *	以下nb行，每行包含棋子类型，如果需要初始化棋子位置，则后面跟有棋子初始横纵坐标
 * 
 * @author DaxinLi
 *
 */
public class Game {
	/**
	 * gameType
	 * chess 国际象棋
	 * go 围棋
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
//					此时没有初始化位置
					playerA.addPiece(new Piece(splitItems[0]));
				} else if(splitItems.length==3) {
//					此时需要初始化棋子的位置
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
//					此时没有初始化位置
					playerB.addPiece(new Piece(splitItems[0]));
				} else if(splitItems.length==3) {
//					此时需要初始化棋子的位置
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
	 * 	落子
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
	 * 	移动棋子
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
	 * 	提子
	 * 	将指定位置的对手的棋子移除
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
	 * 	吃子
	 * 	将目标位置上对手的棋子移除，将player初始位置的棋子移动到目标位置上
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
	 *	获得在指定位置的棋子的player
	 * @param pos
	 * @return
	 */
	public Player getOwnerAtCord(Position pos) throws MyExp{
		try {
			MyExp.assertTrue(gameBoard.isCordAvailable(pos.x(), pos.y()), "输入位置须合法");
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
	 *	获得在指定位置的棋子
	 * @param pos
	 * @return
	 */
	public Piece getPieceAtCord(Position pos) throws MyExp{
		try {
			MyExp.assertTrue(gameBoard.isCordAvailable(pos.x(), pos.y()), "输入位置须合法");
		}catch (MyExp e) {
			// TODO: handle exception
			throw e;
		}
		
		if(!gameBoard.isCordAvailable(pos.x(), pos.y())) return null;
		
		return gameBoard.getPieceAtCord(pos.x(), pos.y());
	}
	/**
	 * 	获取用户在棋盘上的所有棋子数目，调用gameBoard. 
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
