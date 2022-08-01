package com.arkpes.investment.service;

import com.arkpes.investment.entity.Fund;

import javax.xml.bind.ValidationException;
import java.util.List;

public interface FundService {
    List<Fund> fetchFundsByInvestorId(Long clientId, Long investorId);

    List<Fund> fetchFundByClientId(Long clientId);
    Fund fetchFundById(Long clientId, Long fundId);
    Fund saveFund(Long clientId, Fund fund) throws ValidationException;
    Fund updateFund(Fund fund, Long fundId, Long clientId) throws ValidationException;
    void deleteFundById(Long clientId, Long fundId);
}
