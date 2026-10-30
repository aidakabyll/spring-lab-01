package kz.iitu.spring_lab_01.service;

import kz.iitu.spring_lab_01.domain.Account;
import kz.iitu.spring_lab_01.domain.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class AccountService {

    private final AccountRepository repository;
    private final AuditService auditService;

    public AccountService(AccountRepository repository, AuditService auditService) {
        this.repository = repository;
        this.auditService = auditService;
    }

    @Transactional
    public void transfer(Long fromId, Long toId, BigDecimal amount) {
        Account from = repository.findById(fromId).orElseThrow();
        Account to = repository.findById(toId).orElseThrow();

        from.debit(amount);
        to.credit(amount);

        repository.save(from);
        repository.save(to);
    }

    @Transactional
    public void transferWithAuditAndFail(Long fromId, Long toId, BigDecimal amount) {
        Account from = repository.findById(fromId).orElseThrow();
        Account to = repository.findById(toId).orElseThrow();

        from.debit(amount);
        to.credit(amount);
        repository.save(from);
        repository.save(to);

        auditService.logTransfer("Transfer " + amount + " from " + fromId + " to " + toId);

        throw new RuntimeException("Simulated error after audit!");
    }
}