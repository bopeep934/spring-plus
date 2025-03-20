package org.example.expert.domain.common.log;


import org.example.expert.domain.manager.entity.Manager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LogService {

    @Autowired
    private LogRepository logRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveLog(Long managerId, String action, String message) {
        Log log = new Log(managerId, action, message);
        logRepository.save(log);
    }

//    @Transactional(propagation = Propagation.REQUIRES_NEW)
//    public void saveFailLog(Long failId, String action, String message) {
//        Log log = new Log( failId , action, message);
//        logRepository.save(log);
//    }
}
