package model;

import javax.swing.table.AbstractTableModel;
import java.io.Serial;
import java.util.ArrayList;

public class GameTable extends AbstractTableModel {

    @Serial
    private static final long serialVersionUID = 1L;

    private static final String[] colunas = {
            "ID", "TIME 1", "GOLS", " X", "GOLS", "TIME 2", "DATA", "STATUS"
    };

    public static ArrayList<Match> match;

    public GameTable(ArrayList<Match> match) {
        super();
        this.match = match;
    }

    @Override
    public int getRowCount() {
        if (match == null) {
            match = new ArrayList<>();
        }
        return match.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        Match game = match.get(rowIndex);

        if (columnIndex == 0) {
            return game.getId();

        } else if (columnIndex == 1) {
            return game.getTeam1();

        } else if (columnIndex == 2) {
            return game.getGols1();

        } else if (columnIndex == 3) {
            return "X";

        } else if (columnIndex == 4) {
            return game.getGols2();

        } else if(columnIndex == 5 ){
            return game.getTeam2();
        } else if(columnIndex == 6) {
            return game.getDateGame();
        } else if(columnIndex == 7){
            return game.getStatus() ? "Finalizada" : "Acontecerá";
        }
        else {
            return null;
        }
    }
    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }
}