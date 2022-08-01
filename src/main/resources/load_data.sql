INSERT INTO Client (id, name) VALUES (1, 'client 1'), (2, 'client 2');
INSERT INTO Investor (id, name, client_id)
VALUES (1, 'investor 1', 1),
       (2, 'investor 2', 1),
       (3, 'investor 3', 2);
INSERT INTO Fund (id, amount, digit, client_id)
VALUES (1, 1000000, 2, 2),
       (2, 2000000, 2, 1),
       (3, 3000000, 2, 1),
       (4, 4000000, 2, 2);

INSERT INTO Fund_Investors(funds_id, investors_id)
VALUES (1, 3),
       (2, 1),
       (3, 1),
       (3, 2),
       (4, 3)