package com.arkpes.investment;

import com.arkpes.investment.entity.Client;
import com.arkpes.investment.service.ClientService;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import javax.inject.Inject;
import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ClientServiceTest {

	@Inject
	private ClientService clientService;

	private Client client1;
	private Client client2;

	@Order(1)
	@Test
	public void testCreateClient() {
		client1 = new Client();
		client1.setName("Client 1");
		Client savedClient1 = clientService.saveClient(client1);
		Assertions.assertNotNull(savedClient1);
		Assertions.assertEquals(savedClient1.getName(), client1.getName());

		client2 = new Client();
		client2.setName("Client 2");
		Client savedClient2 = clientService.saveClient(client2);
		Assertions.assertNotNull(savedClient2);
		Assertions.assertEquals(savedClient2.getName(), client2.getName());
	}

	@Order(2)
	@Test
	public void testFetchAllClients() {
		List<Client> allClients = clientService.fetchClients();
		Assertions.assertEquals(allClients.size(), 2);
	}

	@Order(3)
	@Test
	public void testFetchClient() {
		Client client = clientService.fetchClient(client1.getId());
		Assertions.assertNotNull(client);
		Assertions.assertEquals(client.getName(), client1.getName());
	}

	@Order(4)
	@Test
	public void testUpdateClient() {
		Client client = clientService.fetchClient(client2.getId());
		Assertions.assertNotNull(client);
		Assertions.assertEquals(client.getName(), client2.getName());

		String newName = "updated client 2";
		client.setName(newName);
		Client updatedClient = clientService.updateClient(client, client2.getId());
		Assertions.assertNotNull(updatedClient);
		Assertions.assertEquals(updatedClient.getName(), newName);
	}

	@AfterAll
	public void teardown() {
		if (client1 != null) {
			clientService.deleteClientById(client1.getId());
		}
		if (client2 != null) {
			clientService.deleteClientById(client2.getId());
		}
	}

}
