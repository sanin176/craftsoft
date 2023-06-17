package com.craftsoft.main.db.repository;

import com.craftsoft.main.db.model.TransactionHistory;
import org.springframework.data.repository.CrudRepository;

public interface TransactionHistoryRepository extends CrudRepository<TransactionHistory, Long> {

}
