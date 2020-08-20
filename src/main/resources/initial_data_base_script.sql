-- metodos de pagamento
insert into apipayment.payment_method(credit_card_type , description , name , `type`)
values("MASTER", "cartão master", "CARTÃO MASTER", "CARD");
insert into apipayment.payment_method(credit_card_type , description , name , `type`)
values("VISA", "cartão visa", "CARTÃO VISA", "CARD");
insert into apipayment.payment_method(description , name , `type`)
values("dinheiro vivo", "dinheiro", "MONEY");
insert into apipayment.payment_method(description , name , `type`)
values("maquina", "maquina", "MACHINE");
insert into apipayment.payment_method(description , name , `type`)
values("cheque pre datado", "cheque", "CHECK");

-- usuarios
insert into apipayment.`user`(email) values ("vinicius@mock.com");
insert into apipayment.`user`(email) values ("bernardo@mock.com");
insert into apipayment.`user`(email) values ("joao@mock.com");
insert into apipayment.`user`(email) values ("jose@mock.com");
-- metodos de pagamento para cada usuario
insert into apipayment.user_payment_methods (user_id, payment_methods_id) values(1,1);
insert into apipayment.user_payment_methods (user_id, payment_methods_id) values(1,2);
insert into apipayment.user_payment_methods (user_id, payment_methods_id) values(1,3);
insert into apipayment.user_payment_methods (user_id, payment_methods_id) values(1,4);
insert into apipayment.user_payment_methods (user_id, payment_methods_id) values(1,5);

insert into apipayment.user_payment_methods (user_id, payment_methods_id) values(2,1);
insert into apipayment.user_payment_methods (user_id, payment_methods_id) values(2,2);
insert into apipayment.user_payment_methods (user_id, payment_methods_id) values(2,3);

insert into apipayment.user_payment_methods (user_id, payment_methods_id) values(3,1);
insert into apipayment.user_payment_methods (user_id, payment_methods_id) values(3,3);
insert into apipayment.user_payment_methods (user_id, payment_methods_id) values(3,5);

insert into apipayment.user_payment_methods (user_id, payment_methods_id) values(4,2);
insert into apipayment.user_payment_methods (user_id, payment_methods_id) values(4,4);

-- restaurantes
insert into apipayment.restaurant (name) values ("japones 01");
insert into apipayment.restaurant (name) values ("churrascaria 01");
insert into apipayment.restaurant (name) values ("barzinho 01");

-- metodos de pagamento para cada usuario
insert into apipayment.restaurant_payment_methods (restaurant_id , payment_methods_id) values(1,1);
insert into apipayment.restaurant_payment_methods (restaurant_id, payment_methods_id) values(1,2);
insert into apipayment.restaurant_payment_methods (restaurant_id, payment_methods_id) values(1,3);
insert into apipayment.restaurant_payment_methods (restaurant_id, payment_methods_id) values(1,4);
insert into apipayment.restaurant_payment_methods (restaurant_id, payment_methods_id) values(1,5);

insert into apipayment.restaurant_payment_methods (restaurant_id, payment_methods_id) values(2,1);
insert into apipayment.restaurant_payment_methods (restaurant_id, payment_methods_id) values(2,2);
insert into apipayment.restaurant_payment_methods (restaurant_id, payment_methods_id) values(2,3);

insert into apipayment.restaurant_payment_methods (restaurant_id, payment_methods_id) values(3,1);
insert into apipayment.restaurant_payment_methods (restaurant_id, payment_methods_id) values(3,3);
insert into apipayment.restaurant_payment_methods (restaurant_id, payment_methods_id) values(3,5);