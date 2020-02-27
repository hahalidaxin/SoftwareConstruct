package P3;

public class Position {
	private int x,y;
	public Position(int x,int y) {
		this.x = x;
		this.y = y;
	}
	public int x() {
		return this.x;
	}
	public int y() {
		return this.y;
	}

	@Override public boolean equals(Object that) {
		Position thatPosition = (Position) that;
		return this.x()==thatPosition.x() && this.y()==thatPosition.y();
	}
}
