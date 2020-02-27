package otherDirectory.exception;

/**
 * 输入文件中哦存在不符合语法规则的语句
 */
public class GrammarException extends CheckedException{
    public GrammarException(String msg) {
        super("[GrammarException] "+msg);
    }

    //实现assertTrue的断言功能，如果cond为假则抛出异常，使用该函数进行输入合法判断
    public static void assertTrue(boolean cond,String msg) throws GrammarException {
        if(!cond) throw new GrammarException(msg);
    }
}
