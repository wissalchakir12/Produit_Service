package org.example.produit_service.controller;
import java.util.List;
import java.util.Optional;


import org.example.produit_service.entitie.Produit;
import org.example.produit_service.repo.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("ProduitsService/api/produits")

public class ProduitController {

    @Autowired
    private ProduitRepository produitRepository;

    @PostMapping("/add")
    public ResponseEntity<Produit> ajouter(@RequestBody Produit produit) {
        try {
            // Sauvegarder le produit dans la base de données
            Produit savedProduit = produitRepository.save(produit);

            // Retourner la réponse avec le produit ajouté et le status HTTP 201
            return new ResponseEntity<>(savedProduit, HttpStatus.CREATED);
        } catch (Exception e) {
            // En cas d'erreur, retourner un statut 500 avec un message d'erreur
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<Produit>> produits() {
        List<Produit> listeProduits = (List<Produit>) produitRepository.findAll();
        return new ResponseEntity<>(listeProduits, HttpStatus.OK);
    }

    //Details
    @GetMapping("/{id}")

    public ResponseEntity<Produit> detail(@PathVariable("id") int id) {

        Optional<Produit> pp = produitRepository.findById(id);

        if (pp.isPresent()) {
            Produit p = pp.get();
            return new ResponseEntity<>(p, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //Supprimer
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> supprimer(@PathVariable("id") int id) {
        try {

            produitRepository.deleteById(id);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    //vider la table Produit
    @DeleteMapping("/vider")
    public ResponseEntity<HttpStatus> vider() {
        try {

            produitRepository.deleteAll();

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    //modifier
    @PutMapping("/{id}")
    public ResponseEntity<Produit> modifier(@PathVariable("id") int id, @RequestBody Produit produit) {

        if (produitRepository.save(produit) != null) {
            /*si le produit est modifié avec succés*/
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            /*si il y a erreur de modification*/
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
    @GetMapping("/top-stocked")
    public ResponseEntity<List<Produit>> getTopStockedProducts() {
        try {
            // Utiliser une méthode dérivée pour récupérer les produits les plus stockés
            List<Produit> topStockedProducts = produitRepository.findTop5ByOrderByQteStockDesc();

            return new ResponseEntity<>(topStockedProducts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}/")
    public float getPrixProduit(@PathVariable int id) {
        Produit produit = produitRepository.findById(id).orElseThrow(() -> new RuntimeException("Produit non trouvé"));
        return produit.getPrix();
    }
}





