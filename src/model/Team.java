package model;

public class Team {
    protected String id;
    protected String name;
    protected String password;
    protected String tecnico;
    protected int vitorias;

    public Team() {

    }

    public Team(String id, String name, String password, String tecnico, int vitorias) {
        super();
        this.id = id;
        this.name = name;
        this.password = password;
        this.tecnico = tecnico;
        this.vitorias = vitorias;

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

    public String getTecnico() {
        return tecnico;
    }

    public void setTecnico(String tecnico) {
        this.tecnico = tecnico;
    }

    // daqui
    public int getVitorias() {
        return vitorias;
    }

    public void setVitorias(int i) {
        this.vitorias = i;
    }

    public Team get(int rowIndex) {
        return null;
    }

}
