package microservice.demo.resolver;

import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.extern.slf4j.Slf4j;
import microservice.demo.domain.BankAccount;
import microservice.demo.domain.Currency;
import microservice.demo.repository.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class BankAccountResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    private List<BankAccount> accounts = new ArrayList<>();
    private BankAccountRepository repo;


    @Autowired
    public BankAccountResolver(BankAccountRepository repo) {
       this.repo = repo;
        accounts.add(BankAccount.builder().id(UUID.randomUUID()).currency(Currency.USD).name("Harry").build());
        accounts.add(BankAccount.builder().id(UUID.randomUUID()).currency(Currency.CHF).name("Samuel").build());

        accounts.forEach(repo:: save);
    }

    public BankAccount bankAccount(UUID id) {
        log.info("Retrieving Bank account id: {}", id);

        repo.save(BankAccount.builder().id(id).currency(Currency.USD).name("Harry").build());

        return repo.findById(id).orElse(null);
    }

    public List<BankAccount> bankAccounts() {
        log.info("Retrieving all the Bank accounts");

        return (List<BankAccount>) repo.findAll();
    }

    public List<BankAccount> bankAccountByName(String name) {
        log.info("Retrieving Bank account name: {}", name);

        List<BankAccount> acc = (List<BankAccount>) repo.findAll();
        List<BankAccount> accounts = new ArrayList<>();
        acc.stream().filter(a -> a.getName().equals(name)).forEach(accounts :: add);
        return accounts;
    }

    public BankAccount createBankAccount(String name, Currency currency) {
        log.info("Saving Bank account name:{}", name);
        BankAccount account = BankAccount.builder().id(UUID.randomUUID()).name(name).currency(currency).build();
        repo.save(account);

        return account;
    }
}
