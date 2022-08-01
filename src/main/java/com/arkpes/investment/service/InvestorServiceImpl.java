package com.arkpes.investment.service;

import com.arkpes.investment.entity.Client;
import com.arkpes.investment.entity.Investor;
import com.arkpes.investment.repository.InvestorRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.xml.bind.ValidationException;
import java.util.List;

@Component
public class InvestorServiceImpl implements InvestorService{
    @Inject
    private ClientService clientService;

    @Inject
    private InvestorRepository investorRepository;

    @Override
    public List<Investor> fetchInvestorsByClientId(Long clientId) {
        return investorRepository.findInvestorsByClientId(clientId);
    }

    @Override
    public Investor fetchInvestorById(Long clientId, Long investorId) {
        return investorRepository.findInvestorByClientIdAndId(clientId, investorId);
    }

    @Override
    @Transactional
    public Investor saveInvestor(Long clientId, Investor investor) throws ValidationException {
        Client client = clientService.fetchClient(clientId);
        if (client == null) {
            throw new ValidationException("Client not exists");
        }
        investor.setClient(client);
        return investorRepository.save(investor);
    }

    @Override
    @Transactional
    public Investor updateInvestor(Investor investor, Long clientId, Long investorId) throws ValidationException {
        Investor existingInvestor = investorRepository.findInvestorByClientIdAndId(clientId, investorId);
        if (existingInvestor == null) {
            throw new ValidationException("No investor exists");
        }
        return investorRepository.save(copyInvestor(existingInvestor, investor));
    }

    private Investor copyInvestor(Investor existingInvestor, Investor updatedInvestor) {
        if (updatedInvestor.getName() != null) {
            existingInvestor.setName(updatedInvestor.getName());
        }
        return existingInvestor;
    }

    @Override
    @Transactional
    public void deleteInvestorById(Long clientId, Long investorId) {
        investorRepository.deleteInvestorByClientIdAndId(clientId, investorId);
    }
}
