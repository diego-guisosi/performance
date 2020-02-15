package br.com.hibernate.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.hibernate.domain.Profile;
import br.com.hibernate.repository.ProfileRepository;

@Service
public class ProfileService {

	private static final Logger logger = LoggerFactory.getLogger(ProfileService.class);

	@Autowired
	private ProfileRepository repository;

	public List<Profile> findProfilesWithoutTransaction(Pageable pageable) {
		return findProfilesPage(pageable, "Finding profiles without transaction");
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Profile> findProfilesWithTransactionRequired(Pageable pageable) {
		return findProfilesPage(pageable, "Finding profiles with transaction REQUIRED");
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<Profile> findProfilesWithTransactionNotSupported(Pageable pageable) {
		return findProfilesPage(pageable, "Finding profiles with transaction NOT_SUPPORTED");
	}

	private List<Profile> findProfilesPage(Pageable pageable, String description) {
		logger.info("{} by {}", description, pageable);
		List<Profile> pageOfProfiles = repository.findByPage(pageable);
		logger.info("Profiles found");
		return pageOfProfiles;
	}

}
