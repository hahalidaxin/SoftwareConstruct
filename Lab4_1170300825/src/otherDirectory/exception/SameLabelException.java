package otherDirectory.exception;

/**
 * 存在标签一样的元素
 */
public class SameLabelException extends CheckedException{
    public SameLabelException(String msg) {
        super("[SameLabelException] "+msg);
    }

    //实现assertTrue的断言功能，如果cond为假则抛出异常，使用该函数进行输入合法判断
    public static void assertTrue(boolean cond,String msg) throws SameLabelException{
        if(!cond) throw new SameLabelException(msg);
    }
}
