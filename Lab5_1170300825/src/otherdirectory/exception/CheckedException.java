package otherdirectory.exception;

public class CheckedException extends Exception {
  //错误信息
  private String expMsg = "InputException";

  public CheckedException(String msg) {
    this.expMsg = msg;
  }

  //实现assertTrue的断言功能，如果cond为假则抛出异常，使用该函数进行输入合法判断

  /**
   * .
   * 断言函数
   * @param cond 判断条件值
   * @param msg 异常打印信息
   * @throws CheckedException 需要抛出的Checked异常类型
   */
  public static void assertTrue(boolean cond, String msg) throws CheckedException {
    if (!cond) {
      throw new CheckedException(msg);
    }
  }

  public String getExpMsg() {
    return expMsg;
  }

  @Override
  public String toString() {
    return this.expMsg;
  }
}
