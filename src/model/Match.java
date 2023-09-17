package model;

public class Match {
    protected int id;
    protected String team1;
    protected int gols1;
    protected String team2;
    protected int gols2;

    public Match(int id, String team1, int gols1, String team2, int gols2, String query) {
    }

    public Match(int id, String team1, int gols1, String team2, int gols2) {
        super();
        this.id = id;
        this.team1 = team1;
        this.gols1 = gols1;
        this.team2 = team2;
        this.gols2 = gols2;
    }


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTeam1() {
        return team1;
    }
    public void setTeam1(String team1) {
        this.team1 = team1;
    }
    public int getGols1() {
        return gols1;
    }
    public void setGols1(int gols1) {
        this.gols1 = gols1;
    }
    public String getTeam2() {
        return team2;
    }
    public void setTeam2(String team2) {
        this.team2 = team2;
    }
    public int getGols2() {
        return gols2;
    }
    public void setGols2(int gols2) {
        this.gols2 = gols2;
    }
}
