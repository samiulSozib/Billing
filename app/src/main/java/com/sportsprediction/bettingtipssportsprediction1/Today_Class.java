package com.sportsprediction.bettingtipssportsprediction1;

public class Today_Class {
    String legue,team,score1,score2;

    public Today_Class() {

    }

    public Today_Class(String legue, String team, String score1, String score2) {
        this.legue = legue;
        this.team = team;
        this.score1 = score1;
        this.score2 = score2;
    }

    public String getLegue() {
        return legue;
    }

    public void setLegue(String legue) {
        this.legue = legue;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getScore1() {
        return score1;
    }

    public void setScore1(String score1) {
        this.score1 = score1;
    }

    public String getScore2() {
        return score2;
    }

    public void setScore2(String score2) {
        this.score2 = score2;
    }
}
