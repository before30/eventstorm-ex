package cc.before30.home.ex.eventstorm.products.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * ProductRepository
 *
 * @author before30
 * @since 2019-08-04
 */
public interface ProductRepository extends CrudRepository<Product, Long> {
    List<Product> findByName(@Param("name") String anme);
}
