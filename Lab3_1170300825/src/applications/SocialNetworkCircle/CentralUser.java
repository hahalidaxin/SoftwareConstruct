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

    public String getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }
}
