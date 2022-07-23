package eu.espeo.micronautdemo.db;

import java.util.List;
import java.util.UUID;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

@Repository
public interface OrderRepository extends CrudRepository<Order,Integer> {

	@Override
	List<Order> findAll();

	Order findByBusinessId(UUID businessId);

	void deleteByBusinessId(UUID businessId);
}
