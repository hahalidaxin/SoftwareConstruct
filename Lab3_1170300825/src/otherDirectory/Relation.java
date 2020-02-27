package otherDirectory;

import applications.SocialNetworkCircle.CentralUser;
import applications.SocialNetworkCircle.Friend;

/**
 *  两个物体之间的关系
 *  单向边，但是有节点顺序
 *  相等判断使用值相等
 */
public class Relation<L,E> {
    L obju;
    E objv;
    double weight;

    public static Relation<Friend,Friend> getFriendsTie(Friend obju, Friend objv, double weight) {
        return new Relation<>(obju,objv,weight);
    }

    public static Relation<CentralUser,Friend> getCentralUserTie(CentralUser obju, Friend objv, double weight) {
        return new Relation<>(obju,objv,weight);
    }

    public Relation(L obju, E objv,double weight) {
        this.obju = obju;
        this.objv = objv;
        this.weight = weight;
    }

    public L getObju() {
        return obju;
    }

    public E getObjv() {
        return objv;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public boolean equals(Object obj) {
        Relation<L,E> that = (Relation<L,E>) obj;
        return this.obju.equals(that.obju) && this.objv.equals(that.objv) && this.weight==that.weight;
    }

    @Override
    public int hashCode() {
        return this.obju.hashCode()*31*31+this.objv.hashCode()*31+(int)this.weight;
    }
}
