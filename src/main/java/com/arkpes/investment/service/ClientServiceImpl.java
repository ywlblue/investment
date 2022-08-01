package com.arkpes.investment.service;

import com.arkpes.investment.entity.Client;
import com.arkpes.investment.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;

@Component
public class ClientServiceImpl implements ClientService{
    @Inject
    private ClientRepository clientRepository;

    @Override
    public Client saveClient(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public List<Client> fetchClients() {
        return (List<Client>) clientRepository.findAll();
    }

    @Override
    public Client fetchClient(Long clientId) {
        return clientRepository.findById(clientId).orElse(null);
    }

    @Override
    public Client updateClient(Client client, Long clientId) {
        return clientRepository.findById(clientId)
                .map(value -> clientRepository.save(copyClient(value, client)))
                .orElse(null);
    }

    private Client copyClient(Client existingClient, Client updatedClient) {
        if (updatedClient.getName() != null) {
            existingClient.setName(updatedClient.getName());
        }
        return existingClient;
    }

    @Override
    public void deleteClientById(Long clientId) {
        Client client = fetchClient(clientId);
        clientRepository.delete(client);
    }
}
