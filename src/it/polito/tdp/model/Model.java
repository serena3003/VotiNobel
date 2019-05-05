package it.polito.tdp.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Model {
	
	private List<Esame> esami; //letti da db -TODO!!!
	private List<Esame> best; //gestione della ricorsione 
	private double media_best;

	
	/**
	 * Trova la combinazione di corsi avente la somma dei crediti richiesta
	 * che abbia media massima
	 * @param numeroCrediti
	 * @return elenco dei corsi ottimale oppure {@code null} se non esiste alcuna 
	 * combinazione di corsi che assomma al numero esatto di crediti. 
	 */
	public List<Esame> calcolaSottoinsiemeEsami(int numeroCrediti) {
		
		best = new ArrayList<Esame>();
		media_best = 0.0;
		
		Set<Esame> parziale = new HashSet<>();
		
		cerca(parziale, 0, numeroCrediti);
		return best;
	}
	
	private void cerca(Set<Esame> parziale, int L, int m) {
		
		//caso terminale
		int crediti = sommaCrediti(parziale);
		if(crediti > m)
			return;
		if(crediti == m) {
			double media = calcolaMedia(parziale);
			if(media > media_best) {
				best = new ArrayList<Esame>(parziale);
				media_best = media;
				return;
			}
		}
		
		if(L == esami.size()) {
			return;
		}
			
		//generiamo sotto-problemi
		// esami[L] è da aggiungere?
		
		//provo a non aggiungerlo
		cerca(parziale, L+1, m);
		
		//provo ad aggiungerlo
		parziale.add(esami.get(L));
		cerca(parziale, L+1, m);
		parziale.remove(esami.get(L));
	}

	private double calcolaMedia(Set<Esame> parziale) {
		double media = 0.0;
		int crediti = 0;
		for(Esame e : parziale) {
			media = media + e.getVoto()*e.getCrediti();
			crediti += e.getCrediti();
		}
		return media/crediti;
	}

	private int sommaCrediti(Set<Esame> parziale) {
		int somma = 0;
		for(Esame e : parziale) {
			somma += e.getCrediti();
		}
		return somma;
	}

}
