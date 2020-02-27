package P3;

public class Piece {
	private String pName;
	private int px,py;
	/*
	 * pieceState
	 * 0  û�з��� ��û��px py
	 * 1 �Ѿ����� ����px py
	 * 2 ���������Ѿ����Ƴ�
	 */
	private int pieceState;
	
	public Piece(String pName) {
		this.pName = pName;
		this.px = -1;
		this.py = -1;
		this.pieceState = 0;
	}

	public Piece(String pName,int px,int py) {
		this.pName = pName;
		this.px = px;
		this.py = py;
		this.pieceState = 1;
	}
	
	public String getpName() {
		return pName;
	}
	
	public int getPieceState() {
		return pieceState;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}
	public int getPx() {
		return pieceState==1?px:-1;
	}
	public int getPy() {
		return pieceState==1?py:-1;
	}
	public void setPxy(int px,int py) {
		this.px = px;
		this.py = py;
		this.pieceState = 1;
	}
	public void rmFromBoard() {
		this.px = -1;
		this.py = -1;
		this.pieceState = 2;
	}
}
