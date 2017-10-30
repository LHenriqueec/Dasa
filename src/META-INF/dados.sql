INSERT INTO Produto (codigo, nome) VALUES ('0010', 'PICOLE LIMAO'), ('0012', 'PICOLE MORANGO');

INSERT INTO Cliente (cnpj, celular, email, responsavel, telefone, bairro, cidade, logradouro, uf, nome) VALUES ('21339044000110', '(61) 30367789', 'wellington@ultrafrios.com.br', 'Wellington', '(61) 9923775241', 'Vicente Pires', 'Brasilia', 'SHVP Chácara 134 Galpões 02 e 03', 'DF', 'Ultra Frios');

INSERT INTO Nota (numero, data, cliente_cnpj) VALUES ('17123', '2018-10-09', '21339044000110');

INSERT INTO ItemNota (id, nota_numero, produto_codigo, quantidade) VALUES (1, '17123', '0010', 100), (2, '17123', '0012', 100);

INSERT INTO Recibo (numero, data, printer, cliente_cnpj) VALUES ('17000', '2018-10-09', 0, '21339044000110');

INSERT INTO ItemRecibo (id, recibo_numero, produto_codigo, quantidade, nota_numero) VALUES (1, '17000', '0010', 10, '17123'), (2, '17000', '0012', 10, '17123');
