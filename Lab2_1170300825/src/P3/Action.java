package P3;

import static org.junit.jupiter.api.Assertions.assertAll;

public class Action {
	
	private Board gameBoard;
	private Player playerA,playerB;
	
//	Action ����gameBoard playerA,playerB������ ���г�ʼ��
	public Action(Board gameBoard,Player playerA,Player playerB) {
		this.gameBoard = gameBoard;
		this.playerA = playerA;
		this.playerB = playerB;
	}
	
	/**
	 * 	����
	 * 	�����player��δ�������̵�piece�����䵽pos����
	 * 	�޸�����pieceλ�ã��޸�����gameBoard��posλ�õ�����Ϊpiece��
	 * 	����û���ʷ��
	 * @param player
	 * @param piece
	 * @param px
	 * @param py
	 */
	public void putPiece(Player player,Piece piece,Position pos) throws MyExp {
		int px = pos.x(),py = pos.y();
		
		try {
			MyExp.assertTrue(gameBoard.isCordAvailable(pos.x(), pos.y()), "����λ����Ϸ�");
			MyExp.assertTrue(player.isContainPiece(piece), "playerӦ��ӵ�и�piece");
			MyExp.assertTrue(gameBoard.getPieceAtCord(px, py)==null, "Ŀ��λ��Ӧ��Ϊ��");
			MyExp.assertTrue(!gameBoard.isPieceInBoard(piece), "���Ӳ����Ѿ���������");
		}catch (MyExp e) {
			// TODO: handle exception
			throw e;
		}
		
		piece.setPxy(px, py);
		gameBoard.setPieceAtCord(px, py, piece);
		player.addHistorty(String.format("������  (%d,%d)", px,py));
	}
	
	
	/**
	 * 	�ƶ�����
	 * 	�����player���Ѿ����������ϵ�λ��st�������ƶ����յ�ַed��
	 * 	��������stλ������Ϊnull��edλ������Ϊpiece���޸�piece��λ��Ϊed��
	 * 	��������ʷ
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
			MyExp.assertTrue(gameBoard.isCordAvailable(stX, stY),"�����ʼλ����Ϸ�");
			MyExp.assertTrue(gameBoard.isCordAvailable(edX, edY), "����Ŀ��λ����Ϸ�");
			MyExp.assertTrue(gameBoard.getPieceAtCord(edX, edY)==null, "Ŀ��λ��Ӧ��Ϊ��");
			MyExp.assertTrue(gameBoard.getPieceAtCord(stX, stY)!=null, "��ʼλ��Ӧ���п��ƶ�������");
			MyExp.assertTrue(!(stX==edX && stY==edY), "��ʼλ�ú�Ŀ��λ�ò�Ӧ����ͬ");
			piece = gameBoard.getPieceAtCord(stX, stY);
			MyExp.assertTrue(player.isContainPiece(piece), "playerӦ��ӵ�г�ʼ����");
		}catch (MyExp e) {
			// TODO: handle exception
			throw e;
		}

		
//		�Ƴ���ʼλ�õ�����
		gameBoard.setPieceAtCord(stX, stY, null);
//		���������ƶ���Ŀ��λ��
		gameBoard.setPieceAtCord(edX, edY, piece);
		
		piece.setPxy(edX, edY);
		
		player.addHistorty(String.format("�ƶ����� �� (%d,%d) �� (%d,%d)", stX,stY,edX,edY));
	}
	
	/**
	 * 	����
	 * 	���û�player��λ��������pos�������Ƴ����̡�
	 * 	����piece.rmFromBoard����������posλ������Ϊnull��
	 * 	��������ʷ��
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
//			assert playerB.isContainPiece(piece):"�Ƴ�������Ӧ��Ϊ��������";
		} else {
//			assert playerA.isContainPiece(piece):"�Ƴ�������Ӧ��Ϊ��������";
			targetPlayer = playerA;
		}
		
		try {
			MyExp.assertTrue(gameBoard.isCordAvailable(px, py),"����λ����Ϸ�");
			
			MyExp.assertTrue(gameBoard.getPieceAtCord(px, py)!=null, "��λ��Ӧ�����ӿ���");
			MyExp.assertTrue(targetPlayer.isContainPiece(piece),"�Ƴ�������Ӧ��Ϊ��������");
		}catch (MyExp e) {
			// TODO: handle exception
			throw e;
		}
		
		piece.rmFromBoard();
		gameBoard.setPieceAtCord(px, py, null);
		
		player.addHistorty(String.format("���ӣ��Ƴ��������ӣ���  (%d,%d)", px,py));
	}
	
	/**
	 * 	����
	 * 	��Ŀ��λ���϶��ֵ������Ƴ�����player��ʼλ�õ������ƶ���Ŀ��λ����
	 * 	ʹ���û�player��λ������stλ�õ����ӳԵ������ֵ�edλ�õ����ӡ�
	 * 	����edPiece.rmFromBoard��������edλ������ΪstPiece��
	 * 	������stλ������Ϊnull����stPiece��������Ϊed��
	 * 	����û���ʷ��
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
			MyExp.assertTrue(gameBoard.isCordAvailable(stX, stY),"�����ʼλ����Ϸ�");
			MyExp.assertTrue(gameBoard.isCordAvailable(edX, edY), "����Ŀ��λ����Ϸ�");
			
			MyExp.assertTrue(stPiece!=null, "��ʼλ�ò���û������");
			MyExp.assertTrue(edPiece!=null, "Ŀ��λ�ò���û������");
			MyExp.assertTrue(!st.equals(ed), "��ʼλ�ú�Ŀ��λ�ñ��벻ͬ");
			MyExp.assertTrue(player.isContainPiece(stPiece), "��ʼλ������Ӧ������player");
			MyExp.assertTrue(targetPlayer.isContainPiece(edPiece), "Ŀ��λ������Ӧ������targetPlayer(����)");
		} catch(MyExp e) {
			throw e;
		}
		
//		remove ed piece
		edPiece.rmFromBoard();
		gameBoard.setPieceAtCord(edX, edY, null);
		
//		move st piece
		gameBoard.setPieceAtCord(stX, stY, null);
//		���������ƶ���Ŀ��λ��
		gameBoard.setPieceAtCord(edX, edY, stPiece);
		
		stPiece.setPxy(edX, edY);
		
		player.addHistorty(String.format("���� �ü��� ( %d,%d) ���ӳԵ����� (%d,%d) ����",
				stX,stY,edX,edY));
	}
}
