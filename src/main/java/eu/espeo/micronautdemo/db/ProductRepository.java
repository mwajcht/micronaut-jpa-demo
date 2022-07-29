package eu.espeo.micronautdemo.db;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

@Repository
public interface ProductRepository extends CrudRepository<Product,Integer> {
	@Override
	List<Product> findAll();

	Optional<Product> findByBusinessId(UUID businessId);

	void deleteByBusinessId(UUID businessId);

	//String findByName(String name);
}
