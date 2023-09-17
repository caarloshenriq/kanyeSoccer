package model;

public class Players {
	protected String id;
	protected String name;
	protected String password;
	protected String gols;
	protected String number;
	protected String position;
	protected String teamId;




	public Players(String id, String name,String password, String gols, String number, String position, String teamId) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.gols = gols;
		this.number = number;
		this.position = position;
		this.teamId = teamId;
	}

	public Players(String id, String name, String gols, String number, String position) {
		this.id = id;
		this.name = name;
		this.gols = gols;
		this.number = number;
		this.position = position;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
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

}
