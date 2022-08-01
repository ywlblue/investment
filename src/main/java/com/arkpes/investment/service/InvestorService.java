package com.arkpes.investment.service;

import com.arkpes.investment.entity.Investor;

import javax.xml.bind.ValidationException;
import java.util.List;

public interface InvestorService {
    List<Investor> fetchInvestorsByClientId(Long clientId);
    Investor fetchInvestorById(Long clientId, Long investorId);
    Investor saveInvestor(Long clientId, Investor investor) throws ValidationException;
    Investor updateInvestor(Investor investor, Long clientId, Long investorId) throws ValidationException;
    void deleteInvestorById(Long clientId, Long investorId);
}
