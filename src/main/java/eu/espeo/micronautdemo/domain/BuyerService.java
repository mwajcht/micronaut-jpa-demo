package eu.espeo.micronautdemo.domain;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import eu.espeo.micronautdemo.db.BuyerRepository;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor
public class BuyerService {
	private final BuyerRepository buyerRepository;

	public List<Buyer> findAll() {
		return buyerRepository.findAll().stream()
				.map(eu.espeo.micronautdemo.db.Buyer::toBuyer)
				.collect(toList());
	}


	public Buyer findByBusinessId(UUID businessId) {
		return buyerRepository.findByBusinessId(businessId)
				.map(eu.espeo.micronautdemo.db.Buyer::toBuyer)
				.orElseThrow();
	}

	@Transactional
	public Buyer create(Buyer buyer) {
		return buyerRepository
				.save(eu.espeo.micronautdemo.db.Buyer.fromBuyer(buyer))
				.toBuyer();
	}

	@Transactional
	public void delete(UUID businessId) {
		buyerRepository.deleteByBusinessId(businessId);
	}
}
