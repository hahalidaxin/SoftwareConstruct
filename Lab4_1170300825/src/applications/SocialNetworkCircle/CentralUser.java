package applications.SocialNetworkCircle;

import centralObject.CentralObject;

public class CentralUser extends CentralObject {
    private String gender;
    private int age;


    public static CentralUser getInstance(String obName,String gender,int age) {
        return new CentralUser(obName,gender,age);
    }
    public static CentralUser getInstance(String obName) {
        return new CentralUser(obName);
    }

    public CentralUser(String obName) {
        super(obName);
    }

    public CentralUser(String obName,String gender,int age) {
        super(obName);
        this.gender = gender;
        this.age = age;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof CentralUser) {
            CentralUser that = (CentralUser) obj;
            return super.equals(obj) && gender.equals(that.gender) && age==that.age;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode()*31*31+gender.hashCode()*31+age;
    }
}
