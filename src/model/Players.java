package model;

public class Players {
	protected  int id;
	protected String name;
	protected String password;
	protected String gols;
	protected String number;
	protected String position;
	protected String teamId;
	protected String userName;



	public Players(int id, String name, String password, String gols, String number, String position, String teamId, String userName) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.gols = gols;
		this.number = number;
		this.position = position;
		this.teamId = teamId;
		this.userName = userName;
	}

	public Players(int id, String name, String gols, String number, String position) {
		this.id = id;
		this.name = name;
		this.gols = gols;
		this.number = number;
		this.position = position;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getGols() {
		return gols;
	}
	public void setGols(String gols) {
		this.gols = gols;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getTeamId() {
		return teamId;
	}
	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
