insert into Estado (id, uf) values (1, 'DF');
insert into Estado (id, uf) values (2, 'GO');

insert into Endereco (id, bairro, cidade, logradouro, estado_id) values (1, 'VICENTE PIRES', 'BRASILIA', 'SHVP CHACARA 134 GALPOES 02 E 03', 1);
insert into Endereco (id, bairro, cidade, logradouro, estado_id) values (2, 'CEILANDIA', 'BRASILIA', 'QNM 25 CONJUNTO 3', 1);

insert into Contato (id, celular, email, responsavel, telefone) values (1, '(61) 9 9133-1375', 'distribuicao.ultrafrios@gmail.com', 'Wellington', '(61) 3036-7789');

insert into Cliente (id, cnpj, nome, contato_id, endereco_id) values (1, '21.339.044/0001-10', 'ULTRA FRIOS COMERCIO DE PRODUTOS INDUSTRIALIZADOS LTDA', 1, 1);
insert into Cliente (id, cnpj, nome, contato_id, endereco_id) values (2, '99.999.999/9999-99', 'MEGA FRIOS TRANSPORTES E COMERCIO LTDA', 1, 2);

insert into Produto (id, codigo, nome, quantidade) values (1, '0010', 'PICOLE LIMAO', 500);
insert into Produto (id, codigo, nome, quantidade) values (2, '0012', 'PICOLE MORANGO', 500);

insert into Nota (id, numeroNota, cliente_id) values (1, 16101, 1);

insert into Nota_Produto (Nota_id, produtos_id) values (1, 1);
insert into Nota_Produto (Nota_id, produtos_id) values (1, 2);
