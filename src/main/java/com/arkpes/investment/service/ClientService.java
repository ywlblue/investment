package com.arkpes.investment.service;

import com.arkpes.investment.entity.Client;

import java.util.List;

public interface ClientService {
    Client saveClient(Client client);
    List<Client> fetchClients();

    Client fetchClient(Long clientId);
    Client updateClient(Client client, Long clientId);
    void deleteClientById(Long clientId);
}
