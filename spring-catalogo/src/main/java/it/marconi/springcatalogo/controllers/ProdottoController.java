package it.marconi.springcatalogo.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.marconi.springcatalogo.domains.Prodotto;
//import it.marconi.springcatalogo.domains.ProdottoForm;
import it.marconi.springcatalogo.services.ProdottoService;

@Controller
@RequestMapping("/")
public class ProdottoController {
// non era necessario vista la mancanza di db, ma è comunque buona pratica
    @Autowired
    ProdottoService prodottoService;

    @GetMapping
    public ModelAndView viewHomePage() {

        return new ModelAndView("homepage");
    }

    @GetMapping("/catalogo")
    public ModelAndView viewCatalogo() {

        return new ModelAndView("prodotti-catalogo").addObject("prodotti", prodottoService.getprodotti());
    }


    //localhost:8090/home/user?type=
    @GetMapping("/prodotto")
    public ModelAndView handlerProdottoAction(@RequestParam("type") String type) {

        // in base al parametro, mostro la pagina relativa
        if(type.equals("nuovo"))
            return new ModelAndView("prodotto-registrazione").addObject("prodotto", new Prodotto());

        // se il parametro è errato, pagina non trovata
        else 
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pagina non trovata!");
    }

    


    @PostMapping("/prodotto/nuovo")
    public ModelAndView handlerNewProdotto(@ModelAttribute Prodotto prodotto) {

        // salvo il prodotto nel "database"
        prodottoService.addProdotto(prodotto);
        

        String nome = prodotto.getNome();         
        //return new ModelAndView("redirect:/prodotto" + nome);
        return new ModelAndView("redirect:/prodotto/" + nome);
    }



    @GetMapping("/prodotto/{nome}")
    public ModelAndView showProdotto(@PathVariable("nome") String nome) {

        Optional<Prodotto> prodotto = prodottoService.getProdottoByProdottoName(nome); // VEDI

        // controllo se il servizio mi ha passato un dato esistente
        if (prodotto.isPresent())
            return new ModelAndView("prodotto-dettagli").addObject("prodottoD", prodotto.get()); // VEDI
        else // se è null sollevo un errore
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Prodotto non trovato");
    }



    @GetMapping("/svuota")
    public ModelAndView viewSvuota() {

        prodottoService.deleteAll();

        return new ModelAndView("redirect:/");
    }

    @GetMapping("/delete/{codice}")
    public ModelAndView deleteProdotto(
            @PathVariable("codice") String codice,
            RedirectAttributes attr) {
        prodottoService.deleteProdottobyCodice(codice);

        // aggiunto un boolean agli attributi del redirect
        attr.addFlashAttribute("deleted", true); // VEDI
        return new ModelAndView("redirect:/catalogo");
    }

}
