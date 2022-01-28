package br.com.meliw4.projetointegrador.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.meliw4.projetointegrador.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // public Optional<Product> findById(Long productId);
    //
    // List<Product> findProductByType(Type productType);
    //
    // Optional<List<Product>> findProductsBySellerId(Integer sellerId);
}