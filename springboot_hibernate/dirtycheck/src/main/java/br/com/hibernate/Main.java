package br.com.hibernate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import br.com.hibernate.paginator.ProfilePaginator;

@SpringBootApplication
public class Main {

	public static void main(String[] args) throws Exception {
		ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);
		ProfilePaginator workflow = context.getBean(ProfilePaginator.class);
		paginateWithoutTransaction(workflow);
	}

	public static void paginateWithSameTransactionNotSupported(ProfilePaginator workflow) {
		workflow.paginateWithSameTransactionNotSupported();
	}

	public static void paginateWithTransactionNotSupported(ProfilePaginator workflow) {
		workflow.paginateWithTransactionNotSupported();
	}

	public static void paginateWithTransactionRequired(ProfilePaginator workflow) {
		workflow.paginateWithTransactionRequired();
	}

	public static void paginateWithSameTransactionRequired(ProfilePaginator workflow) {
		workflow.paginateWithSameTransactionRequired();
	}

	public static void paginateWithoutTransaction(ProfilePaginator workflow) {
		workflow.paginateWithoutTransaction();
	}

}
