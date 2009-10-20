package theorie;

import java.util.ArrayList;

public class Sommet implements Comparable<Sommet> {
	
	private int label;
	private ArrayList<Sommet> suivants;
	
	public Sommet() {
		label = -1;
	}
	public Sommet(int label) {
		this.label = label;
	}

	public void ajouterSuivant(Sommet s) {
		if (suivants == null)
			suivants = new ArrayList<Sommet> ();
		if (!suivants.contains(s)) {
			suivants.add(s);
		}
		else {
			System.out.println("Transformation de l'arc "+label+"->"+s.label);
		}
	}
	
	public ArrayList<Sommet> getSuivants() {
		return suivants;
	}

	public void setSuivants(ArrayList<Sommet> suivants) {
		this.suivants = suivants;
	}


	public int getLabel() {
		return label;
	}

	public void setLabel(int label) {
		this.label = label;
	}

	public String toString() {
		String result = label+" -> {";
		if (suivants == null)
			result += "}";
		else {
			for (int i = 0; i < suivants.size(); i++) {
				result += suivants.get(i).label;
				if (i == suivants.size() - 1)
					result += "}";
				else
					result += ", ";
			}
		}
		return result;
	}

	public int compareTo(Sommet sommet) {
		if (label < sommet.label)
			return -1;
		else if (label == sommet.label)
			return 0;
		else 
			return 1;
	}
}
