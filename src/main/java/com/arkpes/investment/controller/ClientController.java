package com.arkpes.investment.controller;

import com.arkpes.investment.entity.Client;
import com.arkpes.investment.entity.Fund;
import com.arkpes.investment.entity.Investor;
import com.arkpes.investment.service.ClientService;
import com.arkpes.investment.service.FundService;
import com.arkpes.investment.service.InvestorService;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.xml.bind.ValidationException;
import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {
    @Inject
    private ClientService clientService;

    @Inject
    private InvestorService investorService;

    @Inject
    private FundService fundService;

    // client

    @PostMapping("")
    public Client createClient(@RequestBody Client client) {
        return clientService.saveClient(client);
    }

    @GetMapping("/{id}")
    public Client getClient(@PathVariable(name = "id") Long clientId) {
        return clientService.fetchClient(clientId);
    }

    @PutMapping("/{id}")
    public Client updateClient(@PathVariable(name = "id") Long clientId,
                               @RequestBody Client updatedClient) {
        return clientService.updateClient(updatedClient, clientId);
    }

    @DeleteMapping("/{id}")
    public boolean deleteClient(@PathVariable(name = "id") Long clientId) {
        clientService.deleteClientById(clientId);
        return true;
    }



    // Investors
    @PostMapping("/{clientId}/investor")
    public Investor createInvestor(@PathVariable(name = "clientId") Long clientId,
                                   @RequestBody Investor investor) throws ValidationException {
        return investorService.saveInvestor(clientId, investor);
    }

    @GetMapping("/{clientId}/investor")
    public List<Investor> getInvestors(@PathVariable(name = "clientId") Long clientId) {
        return investorService.fetchInvestorsByClientId(clientId);
    }

    @GetMapping("/{clientId}/investor/{investorId}")
    public Investor getInvestor(@PathVariable(name = "clientId") Long clientId,
                                @PathVariable(name = "investorId") Long investorId) {
        return investorService.fetchInvestorById(clientId, investorId);
    }

    @PutMapping("/{clientId}/investor/{investorId}")
    public Investor updateInvestor(@PathVariable(name = "clientId") Long clientId,
                                   @PathVariable(name = "investorId") Long investorId,
                                   @RequestBody Investor updatedInvestor) throws ValidationException {
        return investorService.updateInvestor(updatedInvestor, clientId, investorId);
    }

    @DeleteMapping("/{clientId}/investor/{investorId}")
    public boolean deleteInvestor(@PathVariable(name = "clientId") Long clientId,
                                  @PathVariable(name = "investorId") Long investorId) {
        investorService.deleteInvestorById(clientId, investorId);
        return true;
    }

    // Fund
    @GetMapping("/{clientId}/fund")
    public List<Fund> getFund(@PathVariable(name = "clientId") Long clientId) {
        return fundService.fetchFundByClientId(clientId);
    }

    @PostMapping("/{clientId}/fund")
    public Fund createFund(@PathVariable(name = "clientId") Long clientId,
                           @RequestBody Fund fund) throws ValidationException {
        return fundService.saveFund(clientId, fund);
    }

    @GetMapping("/{clientId}/fund/{fundId}")
    public Fund getFund(@PathVariable(name = "clientId") Long clientId,
                        @PathVariable(name = "fundId") Long fundId) {
        return fundService.fetchFundById(clientId, fundId);
    }

    @PutMapping("/{clientId}/fund/{fundId}")
    public Fund updateFund(@PathVariable(name = "clientId") Long clientId,
                           @PathVariable(name = "fundId") Long fundId,
                           @RequestBody Fund updatedFund) throws ValidationException {
        return fundService.updateFund(updatedFund, fundId, clientId);
    }

    @DeleteMapping("/{clientId}/fund/{fundId}")
    public boolean deleteFund(@PathVariable(name = "clientId") Long clientId,
                                  @PathVariable(name = "fundId") Long fundId) {
        fundService.deleteFundById(clientId, fundId);
        return true;
    }

}
