package com.craftsoft.main.service.scheduler;

import com.craftsoft.main.db.repository.WalletRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WalletJob {

    static Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    WalletRepository walletRepository;

    /*
     * TODO
     * add @SchedulerLock
     * */
    @Scheduled(cron = "0 5 0 * * ?") //every day at 0.01 am
//    @SchedulerLock(name = "createAssessmentForEmployees", lockAtLeastFor = "PT5M", lockAtMostFor = "PT10M")
    public void unblockWallets() {
        LOGGER.info("Start job: unblockedWallets");

        walletRepository.unblockWallets();

        LOGGER.info("Finish job: unblockedWallets");
    }

}
