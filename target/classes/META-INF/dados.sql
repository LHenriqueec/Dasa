insert into Estado (id, uf) values (1, 'DF');
insert into Estado (id, uf) values (2, 'GO');

insert into Endereco (id, bairro, cidade, logradouro, estado_id) values (1, 'VICENTE PIRES', 'BRASILIA', 'SHVP CHACARA 134 GALPOES 02 E 03', 1);
insert into Endereco (id, bairro, cidade, logradouro, estado_id) values (2, 'CEILANDIA', 'BRASILIA', 'QNM 25 CONJUNTO 3', 1);

insert into Contato (id, celular, email, responsavel, telefone) values (1, '(61) 9 9133-1375', 'distribuicao.ultrafrios@gmail.com', 'Wellington', '(61) 3036-7789');

insert into Cliente (cnpj, nome, contato_id, endereco_id) values ('21.339.044/0001-10', 'ULTRA FRIOS COMERCIO DE PRODUTOS INDUSTRIALIZADOS LTDA', 1, 1);
insert into Cliente (cnpj, nome, contato_id, endereco_id) values ('99.999.999/9999-99', 'MEGA FRIOS TRANSPORTES E COMERCIO LTDA', 1, 2);

insert into Produto (codigo, nome) values ('0010', 'PICOLE LIMAO');
insert into Produto (codigo, nome) values ('0012', 'PICOLE MORANGO');

insert into Nota (numeroNota, cliente_cnpj) values (16101, "21.339.044/0001-10");
insert into Nota (numeroNota, cliente_cnpj) values (16102, "21.339.044/0001-10");

insert into Item (quantidade, produto_codigo) values (500, "0010");
insert into Item (quantidade, produto_codigo) values (500, "0012");
insert into Item (quantidade, produto_codigo) values (500, "0010");
insert into Item (quantidade, produto_codigo) values (500, "0012");

insert into Nota_Item (Nota_numeroNota, itens_id) values ("16101", 1);
insert into Nota_Item (Nota_numeroNota, itens_id) values ("16101", 2);
insert into Nota_Item (Nota_numeroNota, itens_id) values ("16102", 3);
insert into Nota_Item (Nota_numeroNota, itens_id) values ("16102", 4);
