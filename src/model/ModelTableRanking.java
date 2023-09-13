package model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class ModelTableRanking extends AbstractTableModel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String[] colunas = {
        "TIME", "VITORIAS"
    };

    private final ArrayList<Team> team;

    public ModelTableRanking(ArrayList<Team> team) {
        this.team = team;
    }

    @Override
    public int getRowCount() {
        return team.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Team selectedTeam = team.get(rowIndex);

        if (columnIndex == 0) {
            return selectedTeam.getName();
        } else if (columnIndex == 1) {
            return selectedTeam.getVitorias();
        } else {
            return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }
}