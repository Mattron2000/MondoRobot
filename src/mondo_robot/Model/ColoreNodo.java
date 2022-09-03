package mondo_robot.Model;

/**
 * Questo elenco riguarda allo stato di ricerca BFS del metodo 'allagaPavimenti'
 * nella classe Thread 'RompiElementiThread'
 * 
 */
enum ColoreNodo {
	/**
	 * il nodo è di colore bianco.
	 * il nodo non è stato visitato.
	 */
	BIANCO,

	/**
	 * il nodo è di colore grigio.
	 * il nodo è stato visitato.
	 */
	GRIGIO,

	/**
	 * il nodo è di colore nero.
	 * il nodo e i suoi adiacenti sono stati visitati.
	 */
	NERO
}
