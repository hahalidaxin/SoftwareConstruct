package P3;

import static org.junit.jupiter.api.Assertions.assertAll;

public class Action {
	
	private Board gameBoard;
	private Player playerA,playerB;
	
//	Action 接收gameBoard playerA,playerB的引用 进行初始化
	public Action(Board gameBoard,Player playerA,Player playerB) {
		this.gameBoard = gameBoard;
		this.playerA = playerA;
		this.playerB = playerB;
	}
	
	/**
	 * 	落子
	 * 	将玩家player的未处于棋盘的piece棋子落到pos处。
	 * 	修改棋子piece位置，修改棋盘gameBoard上pos位置的棋子为piece，
	 * 	添加用户历史。
	 * @param player
	 * @param piece
	 * @param px
	 * @param py
	 */
	public void putPiece(Player player,Piece piece,Position pos) throws MyExp {
		int px = pos.x(),py = pos.y();
		
		try {
			MyExp.assertTrue(gameBoard.isCordAvailable(pos.x(), pos.y()), "输入位置须合法");
			MyExp.assertTrue(player.isContainPiece(piece), "player应该拥有该piece");
			MyExp.assertTrue(gameBoard.getPieceAtCord(px, py)==null, "目标位置应该为空");
			MyExp.assertTrue(!gameBoard.isPieceInBoard(piece), "落子不能已经在棋盘中");
		}catch (MyExp e) {
			// TODO: handle exception
			throw e;
		}
		
		piece.setPxy(px, py);
		gameBoard.setPieceAtCord(px, py, piece);
		player.addHistorty(String.format("落子在  (%d,%d)", px,py));
	}
	
	
	/**
	 * 	移动棋子
	 * 	将玩家player的已经处于棋盘上的位于st的棋子移动到空地址ed。
	 * 	将棋盘上st位置设置为null，ed位置设置为piece，修改piece的位置为ed。
	 * 	添加玩家历史
	 * @param player
	 * @param piece
	 * @param stX
	 * @param stY
	 * @param edX
	 * @param edY
	 */
	public void movePiece(Player player,Position st,Position ed) throws MyExp{
		int stX = st.x(),stY = st.y();
		int edX = ed.x(),edY = ed.y();
		Piece piece;
		try {
			MyExp.assertTrue(gameBoard.isCordAvailable(stX, stY),"输入初始位置须合法");
			MyExp.assertTrue(gameBoard.isCordAvailable(edX, edY), "输入目标位置须合法");
			MyExp.assertTrue(gameBoard.getPieceAtCord(edX, edY)==null, "目标位置应该为空");
			MyExp.assertTrue(gameBoard.getPieceAtCord(stX, stY)!=null, "初始位置应该有可移动的棋子");
			MyExp.assertTrue(!(stX==edX && stY==edY), "初始位置和目标位置不应该相同");
			piece = gameBoard.getPieceAtCord(stX, stY);
			MyExp.assertTrue(player.isContainPiece(piece), "player应该拥有初始棋子");
		}catch (MyExp e) {
			// TODO: handle exception
			throw e;
		}

		
//		移除初始位置的棋子
		gameBoard.setPieceAtCord(stX, stY, null);
//		将该棋子移动到目标位置
		gameBoard.setPieceAtCord(edX, edY, piece);
		
		piece.setPxy(edX, edY);
		
		player.addHistorty(String.format("移动棋子 自 (%d,%d) 至 (%d,%d)", stX,stY,edX,edY));
	}
	
	/**
	 * 	提子
	 * 	将用户player的位于棋盘上pos的棋子移出棋盘。
	 * 	调用piece.rmFromBoard，将棋盘上pos位置设置为null。
	 * 	添加玩家历史。
	 * @param player
	 * @param px
	 * @param py
	 */
	public void removePiece(Player player,Position pos) throws MyExp{
		int px = pos.x(),py = pos.y();
		Piece piece = gameBoard.getPieceAtCord(px, py);
		Player targetPlayer=null;
		if(player==playerA) {
			targetPlayer = playerB;
//			assert playerB.isContainPiece(piece):"移除的棋子应该为对手棋子";
		} else {
//			assert playerA.isContainPiece(piece):"移除的棋子应该为对手棋子";
			targetPlayer = playerA;
		}
		
		try {
			MyExp.assertTrue(gameBoard.isCordAvailable(px, py),"输入位置须合法");
			
			MyExp.assertTrue(gameBoard.getPieceAtCord(px, py)!=null, "该位置应该有子可提");
			MyExp.assertTrue(targetPlayer.isContainPiece(piece),"移除的棋子应该为对手棋子");
		}catch (MyExp e) {
			// TODO: handle exception
			throw e;
		}
		
		piece.rmFromBoard();
		gameBoard.setPieceAtCord(px, py, null);
		
		player.addHistorty(String.format("提子（移除对手棋子）在  (%d,%d)", px,py));
	}
	
	/**
	 * 	吃子
	 * 	将目标位置上对手的棋子移除，将player初始位置的棋子移动到目标位置上
	 * 	使用用户player的位于棋盘st位置的棋子吃掉到对手的ed位置的棋子。
	 * 	调用edPiece.rmFromBoard，将棋盘ed位置设置为stPiece，
	 * 	将棋盘st位置设置为null，将stPiece坐标设置为ed。
	 * 	添加用户历史。
	 * @param player
	 * @param px
	 * @param py
	 */
	public void eatPiece(Player player,Position st,Position ed) throws MyExp {
		int stX = st.x(),stY = st.y();
		int edX = ed.x(),edY = ed.y();
		Player targetPlayer;
		if(player==playerA) {
			targetPlayer = playerB;
		} else {
			targetPlayer = playerA;
		}
		Piece stPiece=gameBoard.getPieceAtCord(stX, stY),
				edPiece = gameBoard.getPieceAtCord(edX, edY);
		try {
			MyExp.assertTrue(gameBoard.isCordAvailable(stX, stY),"输入初始位置须合法");
			MyExp.assertTrue(gameBoard.isCordAvailable(edX, edY), "输入目标位置须合法");
			
			MyExp.assertTrue(stPiece!=null, "初始位置不能没有棋子");
			MyExp.assertTrue(edPiece!=null, "目标位置不能没有棋子");
			MyExp.assertTrue(!st.equals(ed), "初始位置和目标位置必须不同");
			MyExp.assertTrue(player.isContainPiece(stPiece), "初始位置棋子应该属于player");
			MyExp.assertTrue(targetPlayer.isContainPiece(edPiece), "目标位置棋子应该属于targetPlayer(敌人)");
		} catch(MyExp e) {
			throw e;
		}
		
//		remove ed piece
		edPiece.rmFromBoard();
		gameBoard.setPieceAtCord(edX, edY, null);
		
//		move st piece
		gameBoard.setPieceAtCord(stX, stY, null);
//		将该棋子移动到目标位置
		gameBoard.setPieceAtCord(edX, edY, stPiece);
		
		stPiece.setPxy(edX, edY);
		
		player.addHistorty(String.format("吃子 用己方 ( %d,%d) 棋子吃掉对手 (%d,%d) 棋子",
				stX,stY,edX,edY));
	}
}
