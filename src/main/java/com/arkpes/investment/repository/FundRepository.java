package com.arkpes.investment.repository;

import com.arkpes.investment.entity.Fund;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface FundRepository extends CrudRepository<Fund, Long> {

    @Query("FROM Fund f WHERE f.clientId = :clientId")
    List<Fund> findFundsByClientId(@Param("clientId") Long clientId);

    @Query("FROM Fund f WHERE f.clientId = :clientId and f.id = :fundId")
    Fund findFundByClientIdAndId(@Param("clientId") Long clientId, @Param("fundId") Long fundId);

    @Query("FROM Fund f WHERE :investorId in f.investors")
    List<Fund> findAllByInvestorId(@Param("investorId") Long investorId);
    @Modifying
    @Transactional
    @Query("DELETE FROM Fund f WHERE f.clientId = :clientId AND f.id = :fundId")
    void deleteFundByClientIdAndId(@Param("clientId") Long clientId, @Param("fundId") Long fundId);
}
