package otherdirectory.exception;

/**
 * .
 * 文件中各元素的依赖关系不确定
 */
public class DependencyException extends CheckedException {
  public DependencyException(String msg) {
    super("[DependencyException] " + msg);
  }

  //实现assertTrue的断言功能，如果cond为假则抛出异常，使用该函数进行输入合法判断

  /**
   * .
   * 断言函数
   * @param cond 判断条件
   * @param msg 错误输出信息
   * @throws DependencyException 如果异常需要抛出的错误
   */
  public static void assertTrue(boolean cond, String msg) throws DependencyException {
    if (!cond) {
      throw new DependencyException(msg);
    }
  }
}
