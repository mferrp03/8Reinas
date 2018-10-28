
public class Tablero {
	int[][] tablero = new int [8] [8];
	void setPosicion(int i, int j, int n) {
		tablero [i][j]=n;
	}
	int getNum(int i, int j) {
		return tablero[i][j];
	}
}
