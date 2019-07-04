package cn.com.mysnake.model;

public class score {
    private int id;
    private int userid;
    private int score;

    public score() {
    }

    public score(int id, int userid, int score) {
        this.id = id;
        this.userid = userid;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "score{" +
                "id=" + id +
                ", userid=" + userid +
                ", score=" + score +
                '}';
    }
}
