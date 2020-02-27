package P3;

public class Board {
	public static final int BOARDSIZELIMIT = 100;
	/**
	 * boardType
	 * 0  放置在格子里
	 * 1 放置在交点上
	 */
	private int boardType;
//	棋盘大小
	private int boardSize;
//	记录 棋盘上放置的棋子
	private Piece boardPieces[][] = new Piece[BOARDSIZELIMIT][BOARDSIZELIMIT];
	
	private int bdLT() {
		return boardSize+boardType;
	}
	
	public Board(int boardType,int boardSize) {
		this.boardType = boardType;
		this.boardSize = boardSize;
	}
	
	/**
	 * 	获取处于(px,py)位置的棋子，如果位置不合法抛出MyExp
	 * @param px
	 * @param py
	 * @return
	 * @throws MyExp
	 */
	public Piece getPieceAtCord(int px,int py) throws MyExp{
		try {
			MyExp.assertTrue(this.isCordAvailable(px, py), "棋子位置须合法");
		} catch(MyExp e) {
			throw e;
		}
		return boardPieces[px][py];
	}
	
	/**
	 * 	将棋子piece放置在棋盘的(px,py)位置处。如果位置不合法则抛出MyExp
	 * @param px
	 * @param py
	 * @param piece
	 * @throws MyExp
	 */
	public void setPieceAtCord(int px,int py,Piece piece) throws MyExp{
		try {
			MyExp.assertTrue(this.isCordAvailable(px, py), "棋子位置须合法");
		} catch(MyExp e) {
			throw e;
		}
		this.boardPieces[px][py]=piece;
	}
	
	/**
	 * 	判断坐标(cx,cy)是否是一个合法坐标。棋盘最终的横纵可以放置的棋子数目为boardSize+boardType
	 * @param cx
	 * @param cy
	 * @return
	 */
	public boolean isCordAvailable(int cx,int cy) {
		return cx>=0 && cx<bdLT() 
				&& cy>=0 && cy<bdLT();
	}
	/**
	 * 	判断棋子piece是否处于棋盘之内。
	 * @param piece
	 * @return
	 */
	public boolean isPieceInBoard(Piece piece) {
		for(int i=0;i<bdLT();i++) {
			for(int j=0;j<bdLT();j++) {
				if(boardPieces[i][j]!=null && boardPieces[i][j]==piece) {
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * 	获得player在棋盘中的棋子数目（也可以将接口交给Player实现）
	 * @param player
	 * @return
	 */
	public int getNumOfPlayerPiecesInBoard(Player player) {
		int ans=0;
		for(int i=0;i<bdLT();i++) {
			for(int j=0;j<bdLT();j++) {
				if(player.isContainPiece(boardPieces[i][j])) ans++;
			}
		}
		return ans;
	}
}
