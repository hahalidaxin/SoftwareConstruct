package centralObject;

public class CentralObject extends CommonObject {

    /**
     * 静态工厂方法
     * @param obName
     * @return
     */
    public static CentralObject getInstance(String obName) {
        return new CentralObject(obName);
    }

    public CentralObject(String obName) {
        super(obName);

    }

    @Override
    public boolean equals(Object obj) {
        if(obj==null) return false;
        if(obj instanceof CentralObject) {
            CentralObject that = (CentralObject) obj;
            return that.getObName().equals(this.getObName())
                    && that.getPos().equals(this.getPos());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return obName.hashCode()*31;
    }
}
