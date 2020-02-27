package P1.graph;

public class GraphException extends RuntimeException {
	private String expMsg="MyException";
	
	public GraphException(String msg) {
		this.expMsg = msg;
	}
	
	public String getExpMsg() {
		return expMsg;
	}

	public void setExpMsg(String expMsg) {
		this.expMsg = expMsg;
	}
	
	@Override public String toString() {
		return this.expMsg+"\t «Î÷ÿ–¬ ‰»Î";
	}
	
	public static void assertTrue(boolean cond,String msg) throws GraphException {
		if(!cond) throw new GraphException(msg);
	}
}
