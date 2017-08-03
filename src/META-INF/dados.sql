
insert into Cliente (cnpj, nome, celular, email, responsavel, telefone, bairro, cidade, logradouro, uf) values ('21.339.044/0001-10', 'ULTRA FRIOS COMERCIO DE PRODUTOS INDUSTRIALIZADOS LTDA', '(61) 9 9133-1375', 'distribuicao.ultrafrios@gmail.com', 'Wellington', '(61) 3036-7789', 'VICENTE PIRES', 'BRASILIA', 'SHVP CHACARA 134 GALPOES 02 E 03', 'DF');	
insert into Cliente (cnpj, nome, celular, email, responsavel, telefone, bairro, cidade, logradouro, uf) values ('99.999.999/9999-99', 'MEGA FRIOS TRANSPORTES E COMERCIO LTDA', '(61) 9 9133-1375', 'distribuicao.ultrafrios@gmail.com', 'Wellington', '(61) 3036-7789', 'CEILANDIA', 'BRASILIA', 'QNM 25 CONJUNTO 3', 'GO');

insert into Produto (codigo, nome) values ('0010', 'PICOLE LIMAO');
insert into Produto (codigo, nome) values ('0012', 'PICOLE MORANGO');

insert into Nota (numero, cliente_cnpj) values (16101, "21.339.044/0001-10");
insert into Nota (numero, cliente_cnpj) values (16102, "21.339.044/0001-10");

insert into ItemNota (quantidade, produto_codigo, nota_numero) values (500, "0010", "16101");
insert into ItemNota (quantidade, produto_codigo, nota_numero) values (500, "0012", "16101");
insert into ItemNota (quantidade, produto_codigo, nota_numero) values (500, "0010", "16102");
insert into ItemNota (quantidade, produto_codigo, nota_numero) values (500, "0012", "16102");
