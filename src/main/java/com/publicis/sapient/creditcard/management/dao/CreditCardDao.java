package com.publicis.sapient.creditcard.management.dao;

import com.publicis.sapient.creditcard.management.entity.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditCardDao extends JpaRepository<CreditCard, Long> {

}
