package kz.iitu.spring_lab_01;

import kz.iitu.spring_lab_01.domain.Account;
import kz.iitu.spring_lab_01.domain.AccountRepository;
import kz.iitu.spring_lab_01.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountServiceTest {

    @Autowired
    private AccountService service;

    @Autowired
    private AccountRepository repository;

    @Test
    void testSuccessfulTransfer() {
        service.transfer(1L, 2L, new BigDecimal("100.00"));
        System.out.println("Transfer successful!");
    }

    @Test
    void testPropagationAndRollback() {
        try {
            service.transferWithAuditAndFail(1L, 2L, new BigDecimal("50.00"));
        } catch (RuntimeException e) {
            System.out.println("Caught expected error: " + e.getMessage());
        }
    }

    @Test
    void testOptimisticLocking() {
        Account acc1 = repository.findById(1L).orElseThrow();
        Account acc2 = repository.findById(1L).orElseThrow();

        acc1.debit(new BigDecimal("10.00"));
        repository.saveAndFlush(acc1);

        acc2.debit(new BigDecimal("20.00"));

        assertThrows(ObjectOptimisticLockingFailureException.class, () -> {
            repository.saveAndFlush(acc2);
        });
        System.out.println("Optimistic locking worked!");
    }
}