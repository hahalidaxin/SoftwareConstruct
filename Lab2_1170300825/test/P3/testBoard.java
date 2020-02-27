package P3;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class testBoard {

	@Test
	void testIsAvailable() {
		Board board =new Board(1, 8);
		assertTrue("处于范围内",board.isCordAvailable(8, 8));
		assertTrue("处于棋盘范围内", board.isCordAvailable(9, 9));
//		fail("Not yet implemented");
	}
	
	@Test
	void testGetNum() {
		Board board =new Board(1, 8);
		
		try {
			Player player =new Player("me");
			Piece piece = new Piece("black");
			piece.setPxy(0, 0);
			board.setPieceAtCord(0, 0, piece);
			player.addPiece(piece);
			
			piece = new Piece("black");
			piece.setPxy(1, 1);
			board.setPieceAtCord(1, 1, piece);
			player.addPiece(piece);
				
			System.out.println(board.getNumOfPlayerPiecesInBoard(player));
		}catch (MyExp e) {
			System.out.println(e.toString());
			// TODO: handle exception
		}
	}

}
