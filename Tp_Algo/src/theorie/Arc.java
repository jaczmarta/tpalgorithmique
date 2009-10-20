package theorie;

public class Arc {
	
	private Sommet origine;
	private Sommet destination;
	private int capacite;
	private int cout;
	private int flot;
	
	public Arc() {
		
	}
	
	public Arc(	Sommet origine, 
				Sommet destination,
				int capacite,
				int cout,
				int flot) {
		this.origine = origine;
		this.destination = destination;
		this.capacite = capacite;
		this.cout = cout;
		setFlot(flot);
	}

	public Sommet getOrigine() {
		return origine;
	}

	public void setOrigine(Sommet origine) {
		this.origine = origine;
	}

	public Sommet getDestination() {
		return destination;
	}

	public void setDestination(Sommet destination) {
		this.destination = destination;
	}

	public int getCapacite() {
		return capacite;
	}

	public void setCapacite(int capacite) {
		this.capacite = capacite;
	}

	public int getCout() {
		return cout;
	}

	public void setCout(int cout) {
		this.cout = cout;
	}

	public int getFlot() {
		return flot;
	}
//
//	public void renverser() {
//		
//	}
	public boolean setFlot(int flot) {
		System.out.print("Vérification flot inférieur à capacité ................................................");
		if (flot > capacite) {
			System.out.println("[FAILED]");
			System.out.println("\t->Création de l'arc {"+origine.getLabel()+" -> "+destination.getLabel()+"} ..................................................[FAILED]");
			return false;
		}		
		else {
			this.flot = flot;
			System.out.println("....[OK]");
			System.out.println("\t->Création de l'arc {"+origine.getLabel()+" -> "+destination.getLabel()+"} ......................................................[OK]");
			return true;
		} 			
	}
//
//	public String getLabelOrigine() {
//		return labelOrigine;
//	}
//
//	public String getLabelDestination() {
//		return labelDestination;
//	}
//	
	public String toString() {
		if (origine == null)
			return "Pas d'origine";
		else if (destination == null)
			return "Pas de destination";
		else
			return 	origine.getLabel()+" -> "+destination.getLabel()+": "+
					this.flot+"/"+this.capacite+" ("+this.cout+")";
	}
	
	public boolean egal(Arc arc) {
		return ((origine.getLabel() == arc.origine.getLabel()) &&
				(destination.getLabel() == arc.destination.getLabel()) &&
				(capacite == arc.capacite) &&
				(cout == arc.cout) &&
				(flot == arc.flot));
	}

}
