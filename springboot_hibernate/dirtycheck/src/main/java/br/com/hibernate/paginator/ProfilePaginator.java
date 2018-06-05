package br.com.hibernate.paginator;

import java.util.List;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.hibernate.domain.Profile;
import br.com.hibernate.service.ProfileService;

@Component
public class ProfilePaginator {

	private static final Logger logger = LoggerFactory.getLogger(ProfilePaginator.class);

	private static final int MAX_RESULTS_PER_PAGE = 5000;

	@Autowired
	private ProfileService service;

	/**
	 * Paginating without transaction declaration will cause Spring to create a new
	 * EntityManager for each call to ProfileRepository#findByPage
	 */
	public void paginateWithoutTransaction() {
		paginate(service::findProfilesWithoutTransaction, "Paginating without transaction");
	}

	/**
	 * Paginating with the same transaction will cause Spring to use the same
	 * EntityManager for each call to ProfileRepository#findByPage. Since
	 * EntityManager is not being cleared and flushed between query executions,
	 * entities (and their snapshots) from previous pages will be kept in memory by
	 * Hibernate (because of dirty checking). This will prevent entities of being
	 * garbage collected and cause future queries to perform slower over time. Check
	 * dirtycheck-with-same-transaction-required.log and take a look at the time
	 * that the commit of the transaction took to complete. More than 10 minutes had
	 * passed and the transaction commit did not finish. It was necessary to kill
	 * the process to close the EntityManager.
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void paginateWithSameTransactionRequired() {
		paginate(service::findProfilesWithoutTransaction, "Paginating with same transaction propagation REQUIRED");
	}

	/**
	 * Paginating with transaction propagation REQUIRED on
	 * ProfileService#findProfilesWithTransactionRequired if a transaction does not
	 * yet exist, will cause Spring to open a new transaction each time the method
	 * in the service class is called and, as a result, a new Entity Manager as
	 * well. Since the transaction is committed after each call, the EntityManager
	 * is closed and the entities are free to be garbage collected. This same result
	 * can be achieved with REQUIRES_NEW if a transaction was previously opened by
	 * the application.
	 */
	public void paginateWithTransactionRequired() {
		paginate(service::findProfilesWithTransactionRequired, "Paginating with transaction propagation REQUIRED");
	}

	/**
	 * Paginating with transaction propagation NOT_SUPPORTED on
	 * ProfileService#findProfilesWithTransactionNotSupported will cause the current
	 * transaction to be suspended and a new EntityManager to be created each time
	 * the method is called. When the method that opened the EntityManager finishes,
	 * the EntityManager will be closed and all the entities associated with it will
	 * be eligible to garbage collection. The behavior is similar to REQUIRES_NEW
	 * but without the overhead of the creation of a new transaction.
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void paginateWithTransactionNotSupported() {
		paginate(service::findProfilesWithTransactionNotSupported,
				"Paginating with transaction propagation NOT_SUPPORTED");
	}

	/**
	 * Paginating with transaction propagation NOT_SUPPORTED on
	 * ProfilePaginator#paginateWithSameTransactionNotSupported will cause Spring to
	 * behave as if the same method was annotated with REQUIRED, because it will
	 * open an EntityManager and stick it to the method execution until it finishes.
	 * Since the same EntityManager is used and is not cleared and flushed, the
	 * entities will be kept in memory between query executions, which will cause
	 * the queries to perform slower time after time.
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public void paginateWithSameTransactionNotSupported() {
		paginate(service::findProfilesWithTransactionNotSupported,
				"Paginating with transaction propagation NOT_SUPPORTED");
	}

	private void paginate(Function<Pageable, List<Profile>> pageableFunction, String description) {
		int page = 0;
		int pageSize = 0;
		int totalOfProfiles = 0;
		do {
			logger.info("{}...", description);
			List<Profile> profilesPage = pageableFunction.apply(PageRequest.of(page++, MAX_RESULTS_PER_PAGE));
			pageSize = profilesPage.size();
			totalOfProfiles += pageSize;
			logger.info("{} done", description);
		} while (pageSize == MAX_RESULTS_PER_PAGE);
		logger.info("Total [ pages = {}; profiles = {} ]", page, totalOfProfiles);
	}

}
