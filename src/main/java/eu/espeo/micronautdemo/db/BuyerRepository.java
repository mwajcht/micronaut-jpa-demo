package eu.espeo.micronautdemo.db;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

@Repository
public interface BuyerRepository extends CrudRepository<Buyer,Integer> {
	@Override
	List<Buyer> findAll();

	Optional<Buyer> findByBusinessId(UUID businessId);

	void deleteByBusinessId(UUID businessId);
}
