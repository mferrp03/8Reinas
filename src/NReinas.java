
import java.util.ArrayList;
import java.util.Scanner;

public class NReinas {
    private boolean[] fila_libre;
    private boolean[] columna_libre;
    private boolean[] diagonal_asc_libre;
    private boolean[] diagonal_desc_libre;
    private int n; 
    private int[] filas_solucion;
    private boolean SolucionFinal;

	//Definimos el arraylist por defecto. Es, por tanto un ArrayList de objects
	//Podriamos haberlo definido asi:
	//private ArrayList<int[]> lista_soluciones = new ArrayList<int []>();
    //La diferencia viene a la hora de acceder a sus contenidos. El m�todo get
	//devuelve un "object". Si no especificamos aqui el tipo del contenido del ArrayList,
	//ser� necesario hacer un casting a la hora de recuperar sus contenidos.
    private ArrayList lista_soluciones = new ArrayList();
    
   /**
   * Constructor
   * @param n. n define el tama�o del tablero. Aqui est� hardcodeado a 8.
   */
    public NReinas(int n){
        if ((n < 4)) throw new NullPointerException();
        this.n = n;
        inicializar();
    }
 
    private void inicializar(){
        this.fila_libre = new boolean[n];
        this.columna_libre = new boolean[n];
        this.filas_solucion = new int[n];
        for (int i = 0; i<n;i++){
            this.fila_libre [i] = true;
            this.columna_libre [i] = true;
            this.filas_solucion [i] = -1;
        }
        this.diagonal_asc_libre = new boolean[2*n-1];
        this.diagonal_desc_libre = new boolean[2*n-1];
        for (int i = 0; i<2*n-1;i++){
            this.diagonal_asc_libre[i] = true;
            this.diagonal_desc_libre[i] = true;
        }
        SolucionFinal = false;
    }
 
    /**
     * 
     * @param fila. Es el numero de fila para el que vamos a buscar una posici�n para la reina.
     * 
     * Este m�todo busca una posible posici�n para una reina y se llama recursivamente para buscar 
     * la siguiente posible posici�n.
     * 
     * Tambien comprueba si ha encontrado una SolucionFinal o si ha llegado a un callej�n sin salida.
     * 
     * Sale al encontrar una soluci�n y devuelve el array filas_solucion relleno con una soluci�n v�lida
     */
     private void buscarSolucion(int fila){
    	//Recorremos el tablero fila a fila. Para cada fila hacemos un while que
     	//va de 0 a 7 y que refleja la columna.
        int col = 0;
        
        //Si llegamos a la columna 8 o la variable SolucionFinal es true, salimos
        //del while y de la recursion
        while (col < n && !SolucionFinal){
        	//Si por el contrario, no hemos llegado a ninguno de las anteriores situaciones
        	//es que aun se puede colocar una reina. Vamos a probar con la fila y col actuales.
        	//Comprobamos si es una buena casilla para colocar la reina:
            if (fila_libre[fila] && columna_libre[col] && diagonal_asc_libre[col+fila] 
            		&& diagonal_desc_libre[col-fila+n-1]){
            	
             	//No ataca a ninguna otra reina, la colocamos aqui y actualizamos los arrays
            	//de la situacion del problema:
                filas_solucion[fila] = col;
                fila_libre[fila] = false;
                columna_libre[col] = false;
                diagonal_asc_libre[col+fila] = false;
                diagonal_desc_libre[col-fila+n-1] = false;
                
                //Si hemos llegado una posible solucion (8 reinas bien colocadas) y 
                //ademas estamos en la ultima fila, salimos de la recursion y ponemos
                //SolucionFinal a true.
                if (fila == n-1 && solucionNueva(this.filas_solucion)){
                    SolucionFinal = true;  //Aqui decidimos si una solucion es final
                }
                else
                {   
                	//...en caso contrario (no estamos en la ultima fila o no hemos llegado a 
                	//una solucion final), seguimos intentandolo con la siguiente fila.
                    if (fila+1 < n ){
                        buscarSolucion(fila+1); 
                    }

                    //Se llega aqui cuando se ha salido de la recursi�n. Hay dos posibles v�as 
                    //para salir de la recursion, cuando la condicion del while inicial no se 
                    //cumple (hemos llegado a la columna 8), en cuyo caso SolucionFinal=False
                    //O bien cuando llegamos a la fila 7 y tenemos una solucion nueva, en cuyo
                    //caso SolucionFinal=true.
                    
                    //Si estamos en el primer caso, debemos quitar la ultima reina que pusimos:
                    if (!SolucionFinal){                  
                        filas_solucion[fila] = -1;
                        fila_libre[fila] = true;
                        columna_libre[col] = true;
                        diagonal_asc_libre[col+fila] = true;
                        diagonal_desc_libre[col-fila+n-1] = true; 
                    }
                }
            }
            col++;
        }
    }
 
     /**
      * Este m�todo busca una soluci�n, comprueba si es v�lida, si lo es, la a�ade al
      * arrayList y si no, inicializa los arrays para buscar otra posibe soluci�n.
      */
    public void Soluciona(){
        boolean flag = true;
        while(flag){
            buscarSolucion(0);  //busca solucion para la fila cero
            if (solucionNueva(filas_solucion)){ //Si aun no teniamos esta solucion
                flag = true;
                agregarSolucion(); //A�ade esta solucion al arraylist
                 
            } else{
                flag = false;
            }
            inicializar(); //vuelve a empezar de cero
        }
    }
    
    /**
     * Este m�todo a�ade una posible soluci�n al ArrayList
     */
    private void agregarSolucion(){
        lista_soluciones.add(this.filas_solucion);  
    }
    
    /**
     * 
     * @param posibleSolucion. La posible soluci�n que vamos a comprobar si ya se habia detectado antes.
     * @return Devuelve true or false en funci�n de si la encuentra en el ArrayList
     * 
     * Este m�todo comprueba si una solucion es nueva o ya se habia agregado al arraylist.
     */
    private boolean solucionNueva(int[] nuevaSolucion){  
        if (nuevaSolucion[0] == -1) return false;
        boolean esNueva = true;
        int i = 0;
        while (i<lista_soluciones.size() && esNueva){ 
        	boolean SonIguales=true;
        	int[] unaSol = (int[]) lista_soluciones.get(i);
            if (unaSol.length!=nuevaSolucion.length) 
            	SonIguales= false;
            else
            {
            	int j=0;
            	while ((j<unaSol.length)){
            		if (unaSol[j] != nuevaSolucion[j]) SonIguales=false;
            		j++;            
            	}
            }
            esNueva=!SonIguales;
            i++;
        }
 
        return esNueva;
    }
        
    /**
     * 
     * @return devuelve el arraylist con todas las soluciones encontradas
     */
    public ArrayList getSoluciones(){
        return this.lista_soluciones;
    }
    public static void rellenar(int i,Tablero tablero, int j,int actual) {
    	tablero.setPosicion(i,j,actual);
    }
    public static void main(String[] args) {
    	int k = 0;
    	int t = 0;
    	Scanner sc = new Scanner(System.in);
    	int maxTableros = sc.nextInt();
    	Tableros tableros = new Tableros(maxTableros);
    	
    	do {
    		int i = 0;
    		int j = 0;
    		Tablero tablero = new Tablero();
    		int actual;
    		actual =sc.nextInt();
    		rellenar(i,tablero,j,actual);
    		k++;
    		if(k==8) {
    			tableros.add(t, tablero);
    			k=0;
    			t++;
    		}
    	}while(sc.hasNext());
    	sc.close();
    	//Creamos el objeto N_Reinas, en este caso para un tama�o de tablero de 8x8 (n=8)
    	//y pedimos que obtenga las soluciones (2 soluciones con n=4, 92, con n=8, 724 con n=10).
    	for(int num = 0;num<maxTableros;num++) {
    		int mayor = 0;
    		NReinas reinas= new NReinas(8);
    		reinas.Soluciona();
        
    		//Obtenemos todas las soluciones
    		ArrayList soluciones = reinas.getSoluciones();
        
    		//Las mostramos por pantalla
    		
    		for (int i = 0; i<soluciones.size();i++){
    			int[] aux  = (int[]) soluciones.get(i);
    			int[] sumas = new int[soluciones.size()];
    			for (int j = 0; j<aux.length;j++){
    			sumas[i]+=	aux[j];
    			}
    			if(sumas[i]>mayor) {
    				mayor=sumas[i];
    			}
    		}
    		System.out.println(mayor);
    		mayor = 0;
  	 }
    }
}