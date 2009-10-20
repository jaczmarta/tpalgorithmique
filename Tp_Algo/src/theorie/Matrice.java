package theorie;

public class Matrice {
	private int lignes;
	private int colonnes;	
	private int [][] matrice;
	protected static int infini = 999;
	
	public Matrice() {
		lignes 		= 0;
		colonnes 	= 0;
		matrice		= null;
	}
	
	public Matrice(int lignes, int colonnes) {
		this.lignes		= lignes;
		this.colonnes	= colonnes;
		this.matrice	= new int[lignes][colonnes];
		for (int i = 0; i < lignes; i++) {
			for (int j = 0; j < colonnes; j++)
				this.setCase(i, j, infini);
		}
	}
	
	public Matrice(int n) {
		this.lignes		= n;
		this.colonnes	= n;
		this.matrice	= new int[lignes][colonnes];
		for (int i = 0; i < lignes; i++) {
			for (int j = 0; j < colonnes; j++)
				this.setCase(i, j, infini);
		}
	}
	
	public Matrice(Matrice m) {
		this.lignes 	= m.lignes;
		this.colonnes 	= m.colonnes;
		this.matrice 	= m.matrice;
	}
	
	public void setDiagonale(int val) {
		for (int i = 0; i < lignes; i++) 
				matrice[i][i] = val;
	}
	
	public void remplir(int val) {
		for (int i = 0; i < lignes; i++)
			for (int j = 0; j < colonnes; j++) {
				matrice[i][j] = val;
			}
	}
		
// GETTERS & SETTERS
	public int getColonnes() {
		return colonnes;
	}

	public void setColonnes(int colonnes) {
		this.colonnes = colonnes;
	}

	public int[][] getMatrice() {
		return matrice;
	}

	public void setMatrice(int[][] matrice) {
		this.matrice = matrice;
	}

	public int getLignes() {
		return lignes;
	}

	public void setLignes(int lignes) {
		this.lignes = lignes;
	}
	
	public Integer get(int i, int j) {
		return matrice[i][j];
	}
	
	public void setCase(int i, int j, int val) {
		matrice[i][j] = val;
	}

	public void afficher() {
		for (int i = 0; i < lignes; i++) {
			for (int j = 0; j < colonnes; j++) {
				System.out.print(matrice[i][j]+"\t");
			}
			System.out.println();
		}
	}
	
	public String toString() {
		String result = "Matrice de taille "+colonnes+"x"+lignes+":\n";
		for (int i = 0; i < lignes; i++) {
			result += "[\t";
			for (int j = 0; j < colonnes; j++) {
				if (matrice[i][j] == infini)
					result += "--\t";
				else
					result += matrice[i][j]+"\t";
			}
			result += "]\n";
		}
		return result;
	}
}
