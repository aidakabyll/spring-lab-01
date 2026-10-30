package kz.iitu.spring_lab_01.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuditService {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void logTransfer(String message) {
        System.out.println("====== AUDIT LOG (REQUIRES_NEW): " + message + " ======");
    }
}