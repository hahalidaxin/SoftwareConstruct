package P3;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Collectors;

import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;

class testGame {

//	����game��ʼ��
	@Test
	void testLoadConfigChess() {
		System.out.println("testLoadConfigChess");
		Game game = new Game("chess");
		game.iniGameWithPlayerName("ME", "Enemy");
		Player playerA = game.getPlayerA();
		Player playerB = game.getPlayerB();
//		for(Piece piece:playerA.getPlayerPieces()) {
//			System.out.println(String.format("%s(%d,%d)",piece.getpName(),piece.getPx(),piece.getPy()));
//		}
//		System.out.println();
//		for(Piece piece:playerB.getPlayerPieces()) {
//			System.out.println(String.format("%s(%d,%d)",piece.getpName(),piece.getPx(),piece.getPy()));
//		}
		System.out.println();
	}
	@Test
	void testLoadConfigGo() {
		System.out.println("testLoadConfigGo");
		Game game = new Game("go");
		game.iniGameWithPlayerName("ME", "Enemy");
		Player playerA = game.getPlayerA();
		Player playerB = game.getPlayerB();
//		System.out.println(playerA.getPlayerPieces().size());
////		System.out.println();
//		System.out.println(playerB.getPlayerPieces().size());
		System.out.println();
	}
	
	/*
	 * 	�������壺
	 * 	��8*8�����ӣ���������Ϊ����
	 *  Rook(0,7)	Knight(1,7)	Bishop(2,7)	Queen(3,7)	King(4,7)	Bishop(5,7)	Knight(6,7)	Rook(7,7)
	 *  Pawn(0,6)	Pawn(1,6)	Pawn(2,6)	Pawn(3,6)	Pawn(4,6)	Pawn(5,6)	Pawn(6,6)	Pawn(7,6)
	 *  --
	 *  --
	 *  --
	 *  --
	 *  Pawn(0,1)	Pawn(1,1)	Pawn(2,1)	Pawn(3,1)	Pawn(4,1)	Pawn(5,1)	Pawn(6,1)	Pawn(7,1)	
	 *  Rook(0,0)	Knight(1,0)	Bishop(2,0)	Queen(3,0)	King(4,0)	Bishop(5,0)	Knight(6,0)	Rook(7,0)
	 *  
	 */
	
	/**
	 * 	Χ��
	 * 	��18*18�����ӣ���������Ϊ����
	 * 	���Ӱ�������ռ 185 ��
	 */

//------- TEST put 
	@Test
	void testPutPiece() {
		System.out.println("testPutPiece");
		try {
			Game game = new Game("go");
			game.iniGameWithPlayerName("ME", "Enemy");
			Player playerA = game.getPlayerA();
			Player playerB = game.getPlayerB();
			Piece pa = playerA.getPlayerPieces().stream()
					.filter(p->p.getPieceState()==0)
					.collect(Collectors.toList()).get(0);
			game.putPiece(playerA, pa, new Position(0, 0));
			
			pa = playerA.getPlayerPieces().stream()
					.filter(p->p.getPieceState()==0)
					.collect(Collectors.toList()).get(0);
			game.putPiece(playerA, pa, new Position(0, 1));
			
			pa = playerA.getPlayerPieces().stream()
					.filter(p->p.getPieceState()==0)
					.collect(Collectors.toList()).get(0);
			game.putPiece(playerA, pa, new Position(18, 18));
		} catch (MyExp e) {
			System.out.println(e.toString());
			// TODO: handle exception
		}
		System.out.println();
	}
//	���Գ���
	@Test
	void testPutPieceOutOfBoard() {
		System.out.println("testPutPieceOutOfBoard");
		try {
			Game game = new Game("go");
			game.iniGameWithPlayerName("ME", "Enemy");
			Player playerA = game.getPlayerA();
			Player playerB = game.getPlayerB();
			Piece pa = playerA.getPlayerPieces().stream()
					.filter(p->p.getPieceState()==0)
					.collect(Collectors.toList()).get(0);
			game.putPiece(playerA, pa, new Position(19, 19));
		}catch(MyExp e) {
			System.out.println(e.toString());
		}
		System.out.println();
	}
//	�������Ӵ�������
	@Test
	void testPutHavePiece() {
		System.out.println("testPutHavePiece");
		try {
		Game game = new Game("go");
		game.iniGameWithPlayerName("ME", "Enemy");
		Player playerA = game.getPlayerA();
		Player playerB = game.getPlayerB();
		Piece pa = playerA.getPlayerPieces().stream()
				.filter(p->p.getPieceState()==0)
				.collect(Collectors.toList()).get(0);
		game.putPiece(playerA, pa, new Position(0, 0));
		pa = playerA.getPlayerPieces().stream()
				.filter(p->p.getPieceState()==0)
				.collect(Collectors.toList()).get(0);
		game.putPiece(playerA, pa, new Position(0,0));
		}catch(MyExp e) {
			System.out.println(e.toString());
		}
		System.out.println();
	}
	
//	���������Ѿ���������
	@Test
	void testPutOldPiece() {
		System.out.println("testPutOldPiece");
		try {
		Game game = new Game("go");
		game.iniGameWithPlayerName("ME", "Enemy");
		Player playerA = game.getPlayerA();
		Player playerB = game.getPlayerB();
		Piece pa = playerA.getPlayerPieces().stream()
				.filter(p->p.getPieceState()==0)
				.collect(Collectors.toList()).get(0);
		game.putPiece(playerA, pa, new Position(0, 0));
		game.putPiece(playerA, pa, new Position(1,0));
		}catch(MyExp e) {
			System.out.println(e.toString());
		}
		System.out.println();
	}
	
//	TEST MOVE
	@Test
	void testMove() {
		System.out.println("testMove");
		try {
		Game game = new Game("chess");
		game.iniGameWithPlayerName("ME", "Enemy");
		Player playerA = game.getPlayerA();
		Player playerB = game.getPlayerB();
//		for(Piece piece:playerA.getPlayerPieces()) {
//			System.out.println(String.format("%s(%d,%d)",piece.getpName(),piece.getPx(),piece.getPy()));
//		}
//		System.out.println();

		game.movePiece(playerA, new Position(0,0), new Position(0,2));
		

		game.movePiece(playerA, new Position(0,2), new Position(0,3));
		}catch(MyExp e) {
			System.out.println(e.toString());
		}
		System.out.println();
	}
	
//	���Գ�ʼλ��û������
	@Test
	void testMoveStartUnAv() {
		System.out.println("testMoveStartUnAv");
		try {
		Game game = new Game("chess");
		game.iniGameWithPlayerName("ME", "Enemy");
		Player playerA = game.getPlayerA();
		Player playerB = game.getPlayerB();
		game.movePiece(playerA, new Position(2,0), new Position(2,0));
		} catch(MyExp e) {
			System.out.println(e.toString());
		}
		System.out.println();
	}
//	����Ŀ��λ��������
	@Test
	void testMoveEndUnAv() {
		System.out.println("testMoveEndUnAv");
		try {
		Game game = new Game("chess");
		game.iniGameWithPlayerName("ME", "Enemy");
		Player playerA = game.getPlayerA();
		Player playerB = game.getPlayerB();
		game.movePiece(playerA, new Position(0,0), new Position(1,0));
		}catch(MyExp e) {
			System.out.println(e.toString());
		}
		System.out.println();
	}
	
//	TEST REMOE
	@Test 
	void testRemove() {
		System.out.println("testRemove");
		try {
		Game game = new Game("chess");
		game.iniGameWithPlayerName("ME", "Enemy");
		Player playerA = game.getPlayerA();
		Player playerB = game.getPlayerB();
		game.removePiece(playerA,new Position(1, 7));
		}catch (MyExp e) {
			System.out.println(e.toString());
			// TODO: handle exception
		}
		System.out.println();
	}
//	�����Ƴ��Լ�������
	@Test 
	void testRemoveMySelfP() {
		System.out.println("testRemoveMySelfP");
		try {
		Game game = new Game("chess");
		game.iniGameWithPlayerName("ME", "Enemy");
		Player playerA = game.getPlayerA();
		Player playerB = game.getPlayerB();
		game.removePiece(playerA,new Position(7, 1));
		}catch(MyExp e) {
			System.out.println(e.toString());
		}
		System.out.println();
	}
	
//	TEST eat
	@Test 
	void testEat() {
		System.out.println("testEat");
		try {
		Game game = new Game("chess");
		game.iniGameWithPlayerName("ME", "Enemy");
		Player playerA = game.getPlayerA();
		Player playerB = game.getPlayerB();
		game.eatPiece(playerA, new Position(0,1),new Position(1,6));
		}catch(MyExp e) {
			System.out.println(e.toString());
		}
		System.out.println();
	}
	
//	���ԳԵ��Լ�������
	@Test 
	void testEatMine() {
		System.out.println("testEatMine");
		try {
		Game game = new Game("chess");
		game.iniGameWithPlayerName("ME", "Enemy");
		Player playerA = game.getPlayerA();
		Player playerB = game.getPlayerB();
		game.eatPiece(playerA, new Position(0,1),new Position(1,0));
		}catch (MyExp e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}
		System.out.println();
	}
//	����ʹ�ö��ֵ�����
	@Test 
	void testEatByEne() {
		System.out.println("testEatByEne");
		try {
		Game game = new Game("chess");
		game.iniGameWithPlayerName("ME", "Enemy");
		Player playerA = game.getPlayerA();
		Player playerB = game.getPlayerB();
		game.eatPiece(playerA, new Position(0,7),new Position(0,0));
		}catch(MyExp e) {
			System.out.println(e.toString());
		}
		System.out.println();
	}
//	���Գ�ʼλ�ú�Ŀ��λ����ͬ
	@Test 
	void testCordEq() {
		System.out.println("testCordEq");
		try {
		Game game = new Game("chess");
		game.iniGameWithPlayerName("ME", "Enemy");
		Player playerA = game.getPlayerA();
		Player playerB = game.getPlayerB();
		game.eatPiece(playerA, new Position(0,0),new Position(0,0));
		}catch(MyExp e) {
			System.out.println(e.toString());
		}
		System.out.println();
	}
	
//	TEST OTHERS
//	���Գ�ʼλ�ú�Ŀ��λ����ͬ
	@Test 
	void testIsAvailable() {
		System.out.println("testIsAvailable");
		Game game = new Game("chess");
		game.iniGameWithPlayerName("ME", "Enemy");
		Player playerA = game.getPlayerA();
		Player playerB = game.getPlayerB();
		try {
			game.getPieceAtCord(new Position(-1,1));
		} catch (MyExp e) {
			System.out.println(e.toString());
			// TODO: handle exception
		}
		System.out.println();
		
//		assertTrue(game.getGameBoard().isCordAvailable(-1, -1));
	}
	
	@Test 
	void testgetAnyPieceByFilter() {
		Game game = new Game("chess");
		game.iniGameWithPlayerName("ME", "Enemy");
		Player playerA = game.getPlayerA();
		Player playerB = game.getPlayerB();
		Piece piece=playerA.getAnyPieceByFilter((p)->p.getPieceState()==0);
	}
}
