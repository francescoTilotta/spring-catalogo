package it.marconi.springcatalogo.domains;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdottoForm {
    
    private String codice;
    private String nome;
    private String categoria;
    private String annoProduzione;
    private String quantita;
}
