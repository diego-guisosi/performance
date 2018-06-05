package br.com.hibernate.repository;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.hibernate.domain.Profile;

@SpringBootTest
public class ProfileRepositoryTest {

	private static final Logger logger = LoggerFactory.getLogger(ProfileRepositoryTest.class);

	@Autowired
	private ProfileRepository repository;

	@Test
	public void testFindAll() {
		List<Profile> profiles = repository.findAll();
		profiles.forEach(p -> logger.info("{}", p));
	}

}
