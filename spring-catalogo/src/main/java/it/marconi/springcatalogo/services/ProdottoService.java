package it.marconi.springcatalogo.services;

import java.util.ArrayList;

import java.util.Optional;

import org.springframework.stereotype.Service;

import it.marconi.springcatalogo.domains.Prodotto;
//import it.marconi.springcatalogo.domains.ProdottoForm;

@Service
public class ProdottoService {
    
    // creo un finto "database" dove salvare gli utenti registrati
    private ArrayList<Prodotto> prodotti = new ArrayList<>();

    public ArrayList<Prodotto> getprodotti() {
        return prodotti;
    }

    public void addProdotto(Prodotto newProdotto) {
        prodotti.add(newProdotto);
    }

    public Optional<Prodotto> getProdottoByProdottoName(String prodottoName) {

        for(Prodotto p : prodotti) {
            if(p.getNome().equals(prodottoName)) {
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }

    public void deleteAll() {
        prodotti.clear(); // VEDI
    }

    public Optional<Prodotto> deleteProdotto(String prodottoName) {
        for(Prodotto p : prodotti) {
            if(p.getNome().equals(prodottoName)) {
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }

    public Optional<Prodotto> deleteProdottobyCodice(String prodottoCodice) {
        for(Prodotto p : prodotti) {
            if(p.getCodice().equals(prodottoCodice)) {
                prodotti.remove(p);
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }

    public Optional<Prodotto> getProdottobyCodice(String prodottoCodice) {
        for(Prodotto p : prodotti) {
            if(p.getCodice().equals(prodottoCodice)) {
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }

}
