package mondo_robot.Model;

/**
 * Interfaccia per implementare le funzioni utili alla gestione di stato
 * 
 */
interface Statable {
	/**
	 * Ottieni lo stato della casella
	 * 
	 * @return stato della casella
	 */
	boolean getStato();

	/**
	 * cambia lo stato della casella
	 * 
	 * @param stato il nuovo stato della casella
	 */
	void setStato(boolean stato);

}
