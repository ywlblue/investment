package com.arkpes.investment.service;

import com.arkpes.investment.entity.Client;
import com.arkpes.investment.entity.Fund;
import com.arkpes.investment.entity.Investor;
import com.arkpes.investment.repository.FundRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.xml.bind.ValidationException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FundServiceImpl implements FundService{

    @Inject
    private FundRepository fundRepository;

    @Inject
    private ClientService clientService;

    @Override
    public List<Fund> fetchFundsByInvestorId(Long clientId, Long investorId) {
        List<Fund> allFunds = fundRepository.findFundsByClientId(clientId);
        return allFunds.stream().filter(fund -> {
            List<Long> ids = fund.getInvestors().stream().map(Investor::getId).collect(Collectors.toList());
            return ids.contains(investorId);
        }).collect(Collectors.toList());
    }

    @Override
    public List<Fund> fetchFundByClientId(Long clientId) {
        return fundRepository.findFundsByClientId(clientId);
    }

    @Override
    public Fund fetchFundById(Long clientId, Long fundId) {

        return fundRepository.findById(fundId).orElse(null);
    }

    @Override
    @Transactional
    public Fund saveFund(Long clientId, Fund fund) throws ValidationException {
        Client client = clientService.fetchClient(clientId);
        if (client == null) {
            throw new ValidationException("Client not exists");
        }
        fund.setClientId(clientId);
        return fundRepository.save(fund);
    }

    @Override
    public Fund updateFund(Fund fund, Long fundId, Long clientId) throws ValidationException {
        Fund existingFund = fundRepository.findFundByClientIdAndId(clientId, fundId);
        if (existingFund == null) {
            throw new ValidationException("Fund not exists");
        }
        return fundRepository.save(copyFund(existingFund, fund));
    }

    private Fund copyFund(Fund existingFund, Fund updatedFund) {
        if (updatedFund.getAmount() != null) {
            existingFund.setAmount(updatedFund.getAmount());
        }
        if (updatedFund.getDigit() != null) {
            existingFund.setDigit(updatedFund.getDigit());
        }
        if (updatedFund.getInvestors() != null) {
            existingFund.setInvestors(updatedFund.getInvestors());
        }
        return existingFund;
    }

    @Override
    public void deleteFundById(Long clientId, Long fundId) {
        fundRepository.deleteFundByClientIdAndId(clientId, fundId);
    }
}
