package com.arkpes.investment;

import com.arkpes.investment.entity.Client;
import com.arkpes.investment.entity.Fund;
import com.arkpes.investment.entity.Investor;
import com.arkpes.investment.service.ClientService;
import com.arkpes.investment.service.FundService;
import com.arkpes.investment.service.InvestorService;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import javax.inject.Inject;
import javax.xml.bind.ValidationException;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FundServiceTest {
    @Inject
    private ClientService clientService;

    @Inject
    private InvestorService investorService;

    @Inject
    private FundService fundService;

    private Client client;
    private Investor investor1;
    private Investor investor2;
    private Fund fundId1;
    private Fund fundId2;

    @BeforeAll
    public void init() throws ValidationException {
        client = new Client();
        client.setName("Sara T. Muhama");
        Client savedClient = clientService.saveClient(client);
        Assertions.assertNotNull(savedClient);
        Assertions.assertEquals(savedClient.getName(), client.getName());

        investor1 = new Investor();
        investor1.setName("investor1");
        Investor savedInvestor1 = investorService.saveInvestor(client.getId(), investor1);
        Assertions.assertNotNull(savedInvestor1);
        Assertions.assertEquals(savedInvestor1.getName(), investor1.getName());

        investor2 = new Investor();
        investor2.setName("investor2");
        Investor savedInvestor2 = investorService.saveInvestor(client.getId(), investor2);
        Assertions.assertNotNull(savedInvestor2);
        Assertions.assertEquals(savedInvestor2.getName(), investor2.getName());

    }

    @AfterAll
    public void teardown() {
        clientService.deleteClientById(client.getId());
    }

    @Order(1)
    @Test
    public void testCreateFund() throws ValidationException {
        Fund fund1 = new Fund();
        fund1.setInvestors(Collections.singletonList(investor1));
        fund1.setAmount(new BigInteger("2000000342"));
        Fund savedFund1 = fundService.saveFund(client.getId(), fund1);
        Assertions.assertNotNull(savedFund1);
        Assertions.assertEquals(savedFund1.getAmount(), fund1.getAmount());
        List<Investor> currentInvestors = savedFund1.getInvestors();
        Assertions.assertEquals(currentInvestors.size(), 1);
        Assertions.assertEquals(currentInvestors.get(0).getId(), investor1.getId());
        fundId1 = savedFund1;

        Fund fund2 = new Fund();
        List<Investor> investors = new ArrayList<>();
        investors.add(investor1);
        investors.add(investor2);
        fund2.setInvestors(investors);
        fund2.setAmount(new BigInteger("10456836300"));
        Fund savedFund2 = fundService.saveFund(client.getId(), fund2);
        Assertions.assertNotNull(savedFund2);
        Assertions.assertEquals(savedFund2.getAmount(), fund2.getAmount());
        currentInvestors = savedFund2.getInvestors();
        Assertions.assertEquals(currentInvestors.size(), 2);
        Assertions.assertTrue(currentInvestors.stream().allMatch(investor ->
                Objects.equals(investor.getId(), investor1.getId()) || Objects.equals(investor.getId(), investor2.getId())));
        fundId2 = savedFund2;
    }

    @Order(2)
    @Test
    public void testFetchAllFunds() {
        List<Fund> allFundsFromInvestor1 = fundService.fetchFundsByInvestorId(client.getId(), investor1.getId());
        Assertions.assertEquals(allFundsFromInvestor1.size(), 2);
        Assertions.assertTrue(allFundsFromInvestor1.stream()
                .allMatch(f -> f.getInvestors().stream().map(Investor::getId).collect(Collectors.toList()).contains(investor1.getId())));

        List<Fund> allFundsFromInvestor2 = fundService.fetchFundsByInvestorId(client.getId(), investor2.getId());
        Assertions.assertEquals(allFundsFromInvestor2.size(), 1);
        Assertions.assertTrue(allFundsFromInvestor2.stream()
                .allMatch(f -> f.getInvestors().stream().map(Investor::getId).collect(Collectors.toList()).contains(investor2.getId())));
    }

    @Order(3)
    @Test
    public void testFetchFund() {
        Fund fund = fundService.fetchFundById(client.getId(), fundId1.getId());
        Assertions.assertNotNull(fund);
        Assertions.assertEquals(fund.getAmount(), fundId1.getAmount());
    }

    @Order(4)
    @Test
    public void testUpdateFund() throws ValidationException {
        Fund fund = fundService.fetchFundById(client.getId(), fundId2.getId());
        Assertions.assertNotNull(fund);
        Assertions.assertEquals(fund.getAmount(), fundId2.getAmount());
        Assertions.assertEquals(fund.getInvestors().size(), 2);
        fund.setAmount(new BigInteger("100009700"));
        fund.setInvestors(Collections.singletonList(investor1));
        Fund updatedFund = fundService.updateFund(fund, fundId2.getId(), client.getId());
        Assertions.assertNotNull(updatedFund);
        Assertions.assertEquals(updatedFund.getAmount(), new BigInteger("100009700"));
        Assertions.assertEquals(updatedFund.getInvestors().size(), 1);
    }

    @Order(5)
    @Test
    public void testDeleteFund() throws InterruptedException {
        fundService.deleteFundById(client.getId(), fundId2.getId());
        Thread.sleep(1000);
        Fund fund = fundService.fetchFundById(client.getId(), fundId2.getId());
        Assertions.assertNull(fund);
    }

}
