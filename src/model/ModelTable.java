package model;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class ModelTable extends AbstractTableModel {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private static String[] colunas = {
			"ID", "NOME", "NUMERO", "GOLS", "POSI\u00C7\u00C3O"
	};

	private ArrayList<Players> players;

	public ModelTable(ArrayList<Players> players) {
		super();
		this.players = players;
	}

	@Override
	public int getRowCount() {
		return players.size();
	}

	@Override
	public int getColumnCount() {
		return colunas.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Players player = players.get(rowIndex);

		if (columnIndex == 0) {
			return player.getId();

		} else if (columnIndex == 1) {
			return player.getName();

		} else if (columnIndex == 2) {
			return player.getNumber();

		} else if (columnIndex == 3) {
			return player.getGols();

		} else if (columnIndex == 4) {
			return player.getPosition();

		} else {
			return null;
		}

	}

	@Override
	public String getColumnName(int column) {
		return colunas[column];
	}
}
