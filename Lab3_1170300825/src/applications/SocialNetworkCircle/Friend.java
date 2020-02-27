package applications.SocialNetworkCircle;

import physicalObject.PhysicalObject;

public class Friend extends PhysicalObject {

    private String gender;
    private int age;

    public static Friend getInstance(String obName,String gender,int age) {
        return new Friend(obName,gender,age);
    }

    public static Friend getInstance(String obName) {
        return new Friend(obName);
    }
    public Friend(String obName) {
        super(obName);
    }

    public Friend(String obName,String gender,int age) {
        super(obName);
        this.gender = gender;
        this.age = age;
    }

    /**
     * 判断两个Friend是否值相等
     * @return
     */
    @Override
    public boolean equalsObject(Object obj) {
        Friend that = (Friend) obj;
        return this.getObName().equals(that.getObName())
                && this.getAge() == that.getAge()
                && this.getGender().equals(that.getGender());
    }




    public String getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }
}
