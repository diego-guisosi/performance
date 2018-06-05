package br.com.hibernate.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.hibernate.domain.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Serializable> {

	/**
	 * This method returns pages of Profiles according to the Pageable parameter
	 * provided
	 * 
	 * @param pageable
	 *            Pageable representing the page to return
	 * @return List<Profile> containing the page content
	 */
	@Query("SELECT p FROM Profile p")
	List<Profile> findByPage(Pageable pageable);

}
