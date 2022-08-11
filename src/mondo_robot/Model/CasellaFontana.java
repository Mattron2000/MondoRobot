package mondo_robot.Model;

import javax.swing.JOptionPane;

public class CasellaFontana extends CasellaStato {
	private static Boolean[][] pozzanghera = null;

	protected CasellaFontana(Integer x, Integer y, CasellaTipo tipo) {
		super(x, y, tipo);
	}
	
	public static void inizializzaPozzanghera(Casella[][] mappa) {
		if (CasellaFontana.getPozzanghera() == null) {
			CasellaFontana.pozzanghera = new Boolean[mappa.length][mappa.length];

			for (int x = 0; x < mappa.length; x++)
				for (int y = 0; y < mappa.length; y++)
					switch (mappa[x][y].getTipo()) {
						case ANIMALE:
						case PAVIMENTO:
						case ROBOT:
							CasellaFontana.pozzanghera[x][y] = false;
							break;

						case FORNELLO:
						case LAVATRICE:
						case MURO:
						case RUBINETTO:
							CasellaFontana.pozzanghera[x][y] = true;
							break;
					}
		}
	}

	static Boolean[][] getPozzanghera() {
		return CasellaFontana.pozzanghera;
	}

	public static void setPozzanghera(int x, int y, boolean value) {
		if (x >= 0 && y >= 0 && x < getPozzanghera().length && y < getPozzanghera().length)
			CasellaFontana.pozzanghera[x][y] = value;
		else {
			JOptionPane.showMessageDialog(null, "I valori x, y e value non rientrano nei valori consentiti!", "ERRORE",
					JOptionPane.ERROR_MESSAGE);
			throw new IllegalArgumentException("ERRORE: I valori x, y e value non rientrano nei valori consentiti!");
		}
	}
}
