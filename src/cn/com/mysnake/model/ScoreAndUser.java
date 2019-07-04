package cn.com.mysnake.model;

public class ScoreAndUser {
    String username;
    String score;

    public ScoreAndUser() {
    }

    public ScoreAndUser(String username, String score) {
        this.username = username;
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }


}
