package P3;

public class Board {
	public static final int BOARDSIZELIMIT = 100;
	/**
	 * boardType
	 * 0  �����ڸ�����
	 * 1 �����ڽ�����
	 */
	private int boardType;
//	���̴�С
	private int boardSize;
//	��¼ �����Ϸ��õ�����
	private Piece boardPieces[][] = new Piece[BOARDSIZELIMIT][BOARDSIZELIMIT];
	
	private int bdLT() {
		return boardSize+boardType;
	}
	
	public Board(int boardType,int boardSize) {
		this.boardType = boardType;
		this.boardSize = boardSize;
	}
	
	/**
	 * 	��ȡ����(px,py)λ�õ����ӣ����λ�ò��Ϸ��׳�MyExp
	 * @param px
	 * @param py
	 * @return
	 * @throws MyExp
	 */
	public Piece getPieceAtCord(int px,int py) throws MyExp{
		try {
			MyExp.assertTrue(this.isCordAvailable(px, py), "����λ����Ϸ�");
		} catch(MyExp e) {
			throw e;
		}
		return boardPieces[px][py];
	}
	
	/**
	 * 	������piece���������̵�(px,py)λ�ô������λ�ò��Ϸ����׳�MyExp
	 * @param px
	 * @param py
	 * @param piece
	 * @throws MyExp
	 */
	public void setPieceAtCord(int px,int py,Piece piece) throws MyExp{
		try {
			MyExp.assertTrue(this.isCordAvailable(px, py), "����λ����Ϸ�");
		} catch(MyExp e) {
			throw e;
		}
		this.boardPieces[px][py]=piece;
	}
	
	/**
	 * 	�ж�����(cx,cy)�Ƿ���һ���Ϸ����ꡣ�������յĺ��ݿ��Է��õ�������ĿΪboardSize+boardType
	 * @param cx
	 * @param cy
	 * @return
	 */
	public boolean isCordAvailable(int cx,int cy) {
		return cx>=0 && cx<bdLT() 
				&& cy>=0 && cy<bdLT();
	}
	/**
	 * 	�ж�����piece�Ƿ�������֮�ڡ�
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
	 * 	���player�������е�������Ŀ��Ҳ���Խ��ӿڽ���Playerʵ�֣�
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
