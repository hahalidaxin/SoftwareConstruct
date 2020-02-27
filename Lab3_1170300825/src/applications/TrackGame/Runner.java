package applications.TrackGame;

import physicalObject.PhysicalObject;

public class Runner extends PhysicalObject{
    private int id,age;
    private double bestScore;
    private String country;

//    不考虑绝对位置，所以初始化位置随便
    public static Runner getInstance(String obName,int id,int age,double bestScore,String country) {
        return new Runner(obName, id, age, bestScore, country);
    }

    public Runner(String obName, int id, int age, double bestScore, String country) {
        super(obName);
        this.id = id;
        this.age = age;
        this.bestScore = bestScore;
        this.country = country;
    }

    public int getId() {
        return id;
    }

    public int getAge() {
        return age;
    }

    public double getBestScore() {
        return bestScore;
    }

    public String getCountry() {
        return country;
    }

    /**
     * 根据BestScore进行排序的比较器
     * @param that
     * @return
     */
    public int compareToWithScoreACS(Runner that) {
        if(this.getBestScore()<that.getBestScore()) {
            return -1;
        } else if(this.getBestScore()==that.getBestScore()) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public boolean equalsObject(Object obj) {
        Runner that = (Runner) obj;
        return super.equalsObject(obj)
                && this.age == that.age
                && this.id == that.id
                && this.country.equals(that.country)
                && this.bestScore == that.bestScore;
    }
}
