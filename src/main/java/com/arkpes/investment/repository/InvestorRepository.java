package com.arkpes.investment.repository;

import com.arkpes.investment.entity.Investor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface InvestorRepository extends CrudRepository<Investor, Long> {
    @Query("FROM Investor i WHERE i.client.id = :clientId")
    List<Investor> findInvestorsByClientId(@Param("clientId") Long clientId);

    @Query("FROM Investor i WHERE i.client.id = :clientId AND i.id = :investorId")
    Investor findInvestorByClientIdAndId(@Param("clientId") Long clientId,
                                         @Param("investorId") Long investorId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Investor i WHERE i.client.id = :clientId AND i.id = :investorId")
    void deleteInvestorByClientIdAndId(@Param("clientId") Long clientId,
                                       @Param("investorId") Long investorId);
}
