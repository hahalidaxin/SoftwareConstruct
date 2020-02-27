package otherdirectory.exception;

public class UncheckedException extends RuntimeException {
  //错误信息
  private String expMsg = "MyException";

  public UncheckedException(String msg) {
    this.expMsg = msg;
  }

  //实现assertTrue的断言功能，如果cond为假则抛出异常，使用该函数进行输入合法判断

  /**
   * .
   * 断言函数
   * @param cond 条件值
   * @param msg 错误输出信息
   * @throws UncheckedException 如果错误需要抛出的异常
   */
  public static void assertTrue(boolean cond, String msg) throws UncheckedException {
    if (!cond) {
      throw new UncheckedException(msg);
    }
  }

  public String getExpMsg() {
    return expMsg;
  }

  public void setExpMsg(String expMsg) {
    this.expMsg = expMsg;
  }

  @Override
  public String toString() {
    return "[UncheckedException] " + this.expMsg;
  }
}