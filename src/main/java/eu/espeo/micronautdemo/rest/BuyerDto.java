package eu.espeo.micronautdemo.rest;

import java.util.UUID;

import eu.espeo.micronautdemo.domain.Buyer;
import io.micronaut.core.annotation.Introspected;

@Introspected
public record BuyerDto(
		UUID businessId,
		String firstName,
		String lastName,
		String street,
		String city,
		String postalCode,
		String country
) {
	public static BuyerDto fromBuyer(Buyer buyer) {
		return new BuyerDto(
				buyer.getBusinessId(),
				buyer.getFirstName(),
				buyer.getLastName(),
				buyer.getStreet(),
				buyer.getCity(),
				buyer.getPostalCode(),
				buyer.getCountry());
	}

	public Buyer toBuyer() {
		return Buyer.builder()
				.businessId(businessId)
				.firstName(firstName)
				.lastName(lastName)
				.street(street)
				.city(city)
				.postalCode(postalCode)
				.country(country)
				.build();
	}
}
