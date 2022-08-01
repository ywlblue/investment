package com.arkpes.investment;

import com.arkpes.investment.entity.Client;
import com.arkpes.investment.entity.Investor;
import com.arkpes.investment.service.ClientService;
import com.arkpes.investment.service.InvestorService;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import javax.inject.Inject;
import javax.xml.bind.ValidationException;
import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class InvestorServiceTest {

    @Inject
    private ClientService clientService;

    @Inject
    private InvestorService investorService;

    private Client client;
    private Investor investor1;
    private Investor investor2;

    @BeforeAll
    public void init() {
        client = new Client();
        client.setName("Client Name");
        Client savedClient = clientService.saveClient(client);
        Assertions.assertNotNull(savedClient);
        Assertions.assertEquals(savedClient.getName(), client.getName());
    }

    @AfterAll
    public void teardown() {
        clientService.deleteClientById(client.getId());
    }

    @Order(1)
    @Test
    public void testCreateInvestor() throws ValidationException {
        investor1 = new Investor();
        investor1.setName("investor1");
        Investor savedInvestor1 = investorService.saveInvestor(client.getId(), investor1);
        Assertions.assertNotNull(savedInvestor1);
        Assertions.assertEquals(savedInvestor1.getName(), investor1.getName());
        Assertions.assertNotNull(savedInvestor1.getClient());

        investor2 = new Investor();
        investor2.setName("investor2");
        Investor savedInvestor2 = investorService.saveInvestor(client.getId(), investor2);
        Assertions.assertNotNull(savedInvestor2);
        Assertions.assertEquals(savedInvestor2.getName(), investor2.getName());
        Assertions.assertNotNull(savedInvestor2.getClient());
    }

    @Order(2)
    @Test
    public void testFetchAllInvestors() {
        List<Investor> allInvestors = investorService.fetchInvestorsByClientId(client.getId());
        Assertions.assertEquals(allInvestors.size(), 2);
    }

    @Order(3)
    @Test
    public void testFetchInvestor() {
        Investor investor = investorService.fetchInvestorById(client.getId(), investor1.getId());
        Assertions.assertNotNull(investor);
        Assertions.assertEquals(investor.getName(), investor1.getName());
    }

    @Order(4)
    @Test
    public void testUpdateInvestor() throws ValidationException {
        Investor investor = investorService.fetchInvestorById(client.getId(), investor2.getId());
        Assertions.assertNotNull(investor);
        Assertions.assertEquals(investor.getName(), investor2.getName());
        Assertions.assertEquals(investor.getId(), investor2.getId());
        String updatedInvestorName = "Updated Investor 2";
        investor.setName(updatedInvestorName);
        Investor updatedInvestor2 = investorService.updateInvestor(investor, client.getId(), investor2.getId());
        Assertions.assertNotNull(updatedInvestor2);
        Assertions.assertEquals(updatedInvestor2.getName(), updatedInvestorName);
    }

    @Order(5)
    @Test
    public void testDeleteInvestor() throws InterruptedException {
        investorService.deleteInvestorById(client.getId(), investor2.getId());
        Thread.sleep(1000);
        Investor investor = investorService.fetchInvestorById(client.getId(), investor2.getId());
        Assertions.assertNull(investor);
    }

}
