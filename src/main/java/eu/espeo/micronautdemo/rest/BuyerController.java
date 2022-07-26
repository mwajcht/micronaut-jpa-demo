package eu.espeo.micronautdemo.rest;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.UUID;

import eu.espeo.micronautdemo.domain.BuyerService;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import lombok.RequiredArgsConstructor;

@Controller(value = "/buyers", produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class BuyerController {

	private final BuyerService buyerService;

	@Get
	public List<BuyerDto> listBuyers() {
		return buyerService.findAll().stream()
				.map(BuyerDto::fromBuyer)
				.collect(toList());
	}

	@Get(value = "/{buyerId}")
	public BuyerDto getBuyer(@PathVariable("buyerId") String buyerId) {
		return BuyerDto.fromBuyer(buyerService.findByBusinessId(UUID.fromString(buyerId)));
	}

	@Post
	public BuyerDto create(@Body BuyerDto buyerDto) {
		return BuyerDto.fromBuyer(buyerService.create(buyerDto.toBuyer()));
	}

	@Delete("/{buyerId}")
	public void delete(@PathVariable("buyerId") String buyerId) {
		buyerService.delete(UUID.fromString(buyerId));
	}
}
