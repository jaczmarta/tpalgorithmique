package theorie;

import java.util.ArrayList;

public class Graphe {
	
	private String label;
	private ArrayList<Sommet> listeSommets;
	private ArrayList<Arc> listeArcs;

	public Graphe(String label) {
		this.label = label;
		listeSommets = new ArrayList<Sommet>();
		listeArcs = new ArrayList<Arc>();
	}
	
	public Graphe(Graphe graphe) {
		graphe.label = this.label+"_copie";
		graphe.listeSommets = new ArrayList<Sommet>(this.listeSommets);
		graphe.listeArcs = new ArrayList<Arc>(this.listeArcs);
	}
	
	public boolean ajouterSommet(Sommet sommet) {
		if (labelSommetOccupe(sommet)) {
			System.out.println("Cr�ation du sommet \""+sommet.getLabel()+"\" ................................................................[FAILED]");
			return false;
		}
		else if (!listeSommets.contains(sommet)) {
			if (!labelSommetOccupe(sommet)) {
				if (sommet.getLabel() == -1) {
					System.out.print("Allocation d'un label au nouveau sommet ("+(listeSommets.size())+") .....................................................");
					sommet.setLabel(listeSommets.size());
					listeSommets.add(sommet);
					System.out.println("[OK]");
					return true;
				}
				else {
					System.out.print("Insertion du sommet \""+sommet.getLabel()+"\" ....................................................................");
					//TRI
					ArrayList<Sommet> tmp = new ArrayList<Sommet>(listeSommets.size()+1);
					int i = 0;
					while ((i < listeSommets.size()) && (sommet.compareTo(listeSommets.get(i)) > 0)) {
						tmp.add(listeSommets.get(i));
						i++;
					}
					tmp.add(sommet);
					while (i < listeSommets.size()) {
						tmp.add(listeSommets.get(i));
						i++;
					}
					listeSommets = new ArrayList<Sommet>(tmp);
					/*
					listeSommets.add(sommet);*/
					System.out.println("[OK]");
				}
				return true;
			}
		}
		return false;
	}
	
	public void ajouterArc(Arc arc) {
		Sommet origine = arc.getOrigine();
		Sommet destination = arc.getDestination();
		if (!listeSommets.contains(origine)) {
			ajouterSommet(origine);
		}
		if (!listeSommets.contains(destination)) {
			ajouterSommet(destination);
		}
		int index = getArc(origine, destination);
		if (index == listeArcs.size()) {
			System.out.print("Ajout de l'arc {"+arc.toString()+"} ..........................................................");
			listeArcs.add(arc);
			arc.getOrigine().ajouterSuivant(arc.getDestination());
			System.out.println("[OK]");
		}
		else {
			if (arc.egal(listeArcs.get(index))) {
				System.out.println("WARNING: L'arc {"+arc.toString()+"} est d�j� pr�sent, aucune modification ne sera apport�e.");
			}
			else {
				System.out.print("Remplacement de l'arc {"+listeArcs.get(index).toString()+"} par le nouvel arc: {"+arc.toString()+"} ..............");

				arc.getOrigine().ajouterSuivant(arc.getDestination());
				listeArcs.remove(index);
				listeArcs.add(index, arc);
				System.out.println("[OK]");
			}
		}	
	}
	
	public int getArc(Sommet origine, Sommet destination) {
		int index = 0;
		boolean trouve = false;
		while ((trouve == false) && (index < listeArcs.size())) {
			Sommet o = listeArcs.get(index).getOrigine();
			Sommet d = listeArcs.get(index).getDestination();
			if ((o.getLabel() == origine.getLabel()) && (d.getLabel() == destination.getLabel())) {	
				trouve = true;
			}
			index++;
		}
		return index;
	}
	
	public boolean existeChemin(Sommet s, Sommet t) {
		Matrice m = this.appliquerFloyd();
		int i = listeSommets.get(s.getLabel()).getLabel();
		int j = listeSommets.get(t.getLabel()).getLabel();
		return (m.get(i, j) != Matrice.infini);
	}
	
	public boolean labelSommetOccupe(Sommet sommet) {
		for (int i = 0; i < listeSommets.size(); i++) {
			if (sommet.getLabel() == listeSommets.get(i).getLabel()) {
				return true;
			}
		}
		return false;
	}
	
	public Matrice genererMatriceAdjacence() {
		Matrice delta = new Matrice(listeSommets.size());
		for (int index = 0; index < listeArcs.size(); index++) {
			Arc arcCourant = listeArcs.get(index);
			Sommet origine = arcCourant.getOrigine();
			Sommet destination = arcCourant.getDestination();
			int i = origine.getLabel();
			int j = destination.getLabel();
			delta.setCase(i, j, arcCourant.getFlot());
		}		
		return delta;
	}
	
	public Matrice appliquerFloyd() {
		int infini = Matrice.infini;
		int nbSommets = listeSommets.size();
		Matrice delta = genererMatriceAdjacence();
		delta.setDiagonale(0);
		Matrice temp = delta;
		for (int k = 0; k < nbSommets; k++) {
			for (int j = 0; j < nbSommets; j++) {
				for (int i = 0; i < nbSommets; i++) {
					int ij = temp.get(i, j);
					int ik = temp.get(i, k);
					int kj = temp.get(k, j);
					if (ij == infini) {	// s'il n'y a pas de chemin de i a j
						if ((ik != infini) && (kj != infini)) {	// alors s'il existe i->k->j
							temp.setCase(i, j, ik + kj);	// d[i,j] = d[i,k] + d[k,j]
						}
					}
					else if ((ik != infini) && (kj != infini)) { // s'il existe deja un chemin de i a j, et qu'il existe k tel que (i->k->j)
						temp.setCase(i, j, Math.min(ij, ik + kj)); //d[i,j] = min (d[i,j], d[i,k] + d[k,j])
					}
					
				}
			}
		}
		return temp;
	}
	
 
	public Matrice appliquerFloydModif() {
	 	int infini = Matrice.infini;
		int nbSommets = listeSommets.size();
		Matrice delta = genererMatriceAdjacence();
		delta.setDiagonale(0);
		Matrice temp = new Matrice(nbSommets);
		for (int k = 0; k < nbSommets; k++) {
			for (int j = 0; j < nbSommets; j++) {
				for (int i = 0; i < nbSommets; i++) {
					int ij = delta.get(i, j);
					int ik = delta.get(i, k);
					int kj = delta.get(k, j);
					if (ij == infini) {
						if ((ik != infini) && (kj != infini)) {
							temp.setCase(i, j, k);
						}
					}
					else if ((ik != infini) && (kj != infini)) {
						if (ij > (ik + kj))
							temp.setCase(i, j, k);
						else
							temp.setCase(i, j, i);
					}
				}
			}
		}
		return temp;
	}
	
	public Graphe genererGrapheEcart() {
		Graphe ge = new Graphe(this.label+"_grapheEcart");
		for (int i = 0; i < listeArcs.size(); i++) {
			Arc arcCourant = listeArcs.get(i);
			Sommet origineCourante = arcCourant.getOrigine();
			Sommet destinationCourante = arcCourant.getDestination();
			if (arcCourant.getFlot() == arcCourant.getCapacite()) {
				ge.ajouterArc(new Arc(destinationCourante, 
										origineCourante, 
										arcCourant.getCapacite(), 
										arcCourant.getCout(), 
										arcCourant.getFlot()));
			}
			else {
				ge.ajouterArc(new Arc(origineCourante, 
										destinationCourante, 
										arcCourant.getCapacite(), 
										arcCourant.getCout(),
										arcCourant.getCapacite() - arcCourant.getFlot()));
				ge.ajouterArc(new Arc(destinationCourante, 
						origineCourante, 
						arcCourant.getCapacite(), 
						arcCourant.getCout(),
						arcCourant.getFlot()));
			}
		}
		return ge;
	}
	
	public int genererNombreAleatoire(int min, int max) {
		return (int)Math.floor(Math.random()*(max-min+1))+min;
	}

	public Arc genererArcAleatoire(int flotMin, int flotMax, int minCapacite, int maxCapacite, int minCout, int maxCout) {
		int flot = genererNombreAleatoire(flotMin, flotMax);
		int capacite = genererNombreAleatoire(minCapacite, maxCapacite);
		int cout = genererNombreAleatoire(minCout, maxCout);
		int indexOrigine = genererNombreAleatoire(0, listeSommets.size());
		int indexDestination = genererNombreAleatoire(0, listeSommets.size());
		if (listeSommets == null) {
			indexOrigine = 0;
			indexDestination = 1;
		}
		Sommet origine = new Sommet(indexOrigine);
		Sommet destination = new Sommet(indexDestination);
		Arc result = new Arc(origine, destination, capacite, cout, flot);
		//ajouterArc(result);
		return result;
	}
	
	public boolean randomisation(int nbSommetsMin, int nbSommetsMax) {
		int nbSommets;
		if (nbSommetsMin > nbSommetsMax) {
			System.out.println("Erreur: le nombre de sommets minimum doit �tre < au nombre de sommets maximum");
			return false;
		}
		else if (nbSommetsMin < 0) {
			System.out.println("Erreur: l'intervalle du nombre de sommet doit �tre inclut dans N");
			return false;
		}
		else {
			if ((nbSommetsMin == nbSommetsMax) && (nbSommetsMin > 0))
				nbSommets = nbSommetsMin;
			else {
				nbSommets = genererNombreAleatoire(nbSommetsMin, nbSommetsMax);
				int nbArcs = 2*nbSommets;
				listeSommets = new ArrayList<Sommet>();
				listeArcs = new ArrayList<Arc>();
				for (int i = 0; i < nbSommets; i++) {
					ajouterSommet(new Sommet());
				}
				for (int i = 0; i < nbArcs; i++) {
					ajouterArc(genererArcAleatoire(0,10,1,10,1,10));
				}
			}
			return true;
		}
	}
	
	public void afficher() {
		System.out.println("\nGRAPHE "+label+":");
		System.out.println("\t- "+listeSommets.size()+" sommet(s): {");
		if (listeSommets != null) {
			for (int i = 0; i < listeSommets.size(); i++) {
				System.out.println("\t\t"+listeSommets.get(i).toString());
			}
		}
		System.out.println("\n\t- "+listeArcs.size()+" arc(s):");
		if (listeArcs != null) {
			for (int i = 0; i < listeArcs.size(); i++) {
				System.out.println("\t\t"+listeArcs.get(i).toString());
			}
		}
		System.out.println();
	}

}
