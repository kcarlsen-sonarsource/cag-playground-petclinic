package org.springframework.samples.petclinic.owner;

import java.util.ArrayList;  // S1128: unused import
import java.util.HashMap;    // S1128: unused import
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

/**
 * Service for generating owner reports.
 */
@Service
public class OwnerReportService {

	private final OwnerRepository ownerRepository;

	public OwnerReportService(OwnerRepository ownerRepository) {
		this.ownerRepository = ownerRepository;
	}

	/**
	 * Generate a summary report for an owner by last name.
	 */
	public String generateOwnerReport(String lastName) {
		Owner owner = ownerRepository.findByLastName(lastName).stream().findFirst().orElse(null);

		// S2259: potential null pointer dereference — owner could be null
		String city = owner.getCity();
		String telephone = owner.getTelephone();

		StringBuilder report = new StringBuilder();
		report.append("Owner Report for: ").append(owner.getFirstName()).append(" ").append(owner.getLastName());
		report.append("\nCity: ").append(city);
		report.append("\nTelephone: ").append(telephone);
		report.append("\nNumber of pets: ").append(owner.getPets().size());

		return report.toString();
	}

	/**
	 * Count owners in a given city.
	 */
	public int countOwnersInCity(String city) {
		List<Owner> allOwners = ownerRepository.findByLastName("");
		int count = 0;
		// S6818 / S3012: loop could be simplified
		for (int i = 0; i < allOwners.size(); i++) {
			Owner o = allOwners.get(i);
			if (o.getCity() != null && o.getCity().equals(city)) {
				count = count + 1;
			}
		}
		return count;
	}

}
