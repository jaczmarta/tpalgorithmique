package theorie;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	
		Graphe graphe = new Graphe("G");
		
		Sommet a = new Sommet(0);
		Sommet b = new Sommet(1);
		Sommet c = new Sommet(2);
		Sommet d = new Sommet(3);
		Sommet e = new Sommet(4);
				
		Arc bd = new Arc(b, d, 99, 99 , 3);
		Arc cb = new Arc(c, b, 99, 99 , -1);
		Arc ce = new Arc(c, e, 99, 99 , -7);
		Arc dc = new Arc(d, c, 99, 99 , 8);
		Arc de = new Arc(d, e, 99, 99 , 1);
		Arc da = new Arc(d, a, 99, 99 , 1);
		Arc ea = new Arc(e, a, 99, 99 , 2);

		graphe.ajouterArc(bd);
		graphe.ajouterArc(cb);
		graphe.ajouterArc(ce);
		graphe.ajouterArc(dc);
		graphe.ajouterArc(de);
		graphe.ajouterArc(da);
		graphe.ajouterArc(ea);
		//graphe.ajouterSommet(new Sommet());
		graphe.afficher();
		
		graphe.genererGrapheEcart().afficher();
		//graphe.randomisation(10, 100);
		graphe.afficher();
		//graphe.generationAleatoire();
		System.out.println(graphe.genererMatriceAdjacence().toString());
		System.out.println(graphe.appliquerFloyd().toString());
		System.out.println(graphe.appliquerFloydModif().toString());
//		
//		System.out.println(graphe.existeChemin(d, b));
//		Sommet p = new Sommet();
//		System.out.println(p.getLabel());
		//System.out.println(graphe.genererArcAleatoire(1, 10, 5, 500).toString());
	}

}
