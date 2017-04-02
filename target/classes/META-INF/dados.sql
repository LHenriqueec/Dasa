insert into Estado (id, uf) values (1, 'DF');
insert into Estado (id, uf) values (2, 'GO');

insert into Endereco (id, bairro, cidade, logradouro, estado_id) values (1, 'VICENTE PIRES', 'BRASILIA', 'SHVP CHACARA 134 GALPOES 02 E 03', 1);
insert into Endereco (id, bairro, cidade, logradouro, estado_id) values (2, 'CEILANDIA', 'BRASILIA', 'QNM 25 CONJUNTO 3', 1);

insert into Contato (id, celular, email, responsavel, telefone) values (1, '(61) 9 9133-1375', 'distribuicao.ultrafrios@gmail.com', 'Wellington', '(61) 3036-7789');

insert into Cliente (id, cnpj, nome, contato_id, endereco_id) values (1, '21.339.044/0001-10', 'ULTRA FRIOS COMERCIO DE PRODUTOS INDUSTRIALIZADOS LTDA', 1, 1);
insert into Cliente (id, cnpj, nome, contato_id, endereco_id) values (2, '99.999.999/9999-99', 'MEGA FRIOS TRANSPORTES E COMERCIO LTDA', 1, 2);

insert into Produto (codigo, nome) values ('0010', 'PICOLE LIMAO');
insert into Produto (codigo, nome) values ('0012', 'PICOLE MORANGO');

insert into Nota (id, numeroNota, cliente_id) values (1, 16101, 1);

insert into Item (id, quantidade, produto_codigo) values (1, 500, "0010");
insert into Item (id, quantidade, produto_codigo) values (2, 500, "0012");

insert into Nota_Item (Nota_id, itens_id) values (1, 1);
insert into Nota_Item (Nota_id, itens_id) values (1, 2);
