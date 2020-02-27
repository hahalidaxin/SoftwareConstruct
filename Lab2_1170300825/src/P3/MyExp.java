package P3;

public class MyExp extends Exception {
	private String expMsg="MyException";
	
	public MyExp(String msg) {
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
	
	public static void assertTrue(boolean cond,String msg) throws MyExp {
		if(!cond) throw new MyExp(msg);
	}
}
