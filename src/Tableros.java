
public class Tableros {
	Tablero [] tableros;
	Tableros(int k) {
		tableros = new Tablero[k];
	}
	void add(int i, Tablero tablero) {
		tableros [i] = tablero;
	}
	Tablero getTablero(int i) {
		return tableros[i];
	}
}
