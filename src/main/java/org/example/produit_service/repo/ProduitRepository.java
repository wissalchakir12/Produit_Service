package org.example.produit_service.repo;




import org.example.produit_service.entitie.Produit;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProduitRepository extends CrudRepository<Produit, Integer> {
    List<Produit> findTop5ByOrderByQteStockDesc();
}
