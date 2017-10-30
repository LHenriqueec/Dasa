var app = angular.module("menuApp", ["ngRoute"]);

app.config(function($routeProvider) {
	$routeProvider.when("/", {
		templateUrl: "main.html",
		controller: "controller:main as main"
	})
	.when("/novo_recibo", {
		templateUrl: "novo_recibo.html",
		controller: "main:recibos as controlRecibo"
	});
});

app.controller("controller:main", function($scope) {
	var ctrl = this;

	$scope.$on('isNew', function(event, data) { ctrl.isNew = data; });

});

app.controller("main:produtos", function($scope) {
	var ctrl = this;

	ctrl.itens = [
	{produto:{codigo:'10', nome:'PICOLE LIMAO'}, quantidade:500},
	{produto:{codigo:'12', nome:'PICOLE MORANGO'}, quantidade:500}
	];

	if(ctrl.itens.length > 0) {
		if(!ctrl.total) ctrl.total = 0;
		for (var i = 0; i < ctrl.itens.length; i++) {
			ctrl.total += Number.parseInt(ctrl.itens[i].quantidade);
		}
	}

});

app.controller("main:clientes", function($scope, $http) {
	var ctrl = this;
	
	ctrl.clientesSemCompra = [
	{nome:'ultra frios', endereco:{bairro:'vicente pires'}, contato:{telefone:'30367789', responsavel:'wellington'}},
	{nome:'mega frios', endereco:{bairro:'ceilandia'}, contato:{telefone:'35507721', responsavel:'andre'}}
	];
});

app.controller("main:recibos", function($scope) {
	var ctrl = this;

	if(!ctrl.recibos) {
		ctrl.recibos = [
		{numero:'17000', cliente:{nome:'ultra frios'}, data:new Date('2017-08-21')}
		];
	}

	ctrl.novo = function() {
		ctrl.recibo = {};
		if(!ctrl.clientes) {
			ctrl.clientes = [
			{nome:'ultra frios', endereco:{bairro:'vicente pires', logradouro:'chacara 134 galpoes 02 e 03'}}
			];
		}
	}

	ctrl.selecionarCliente = function(cliente) {
		ctrl.recibo.cliente = cliente;
		$scope.$emit('isNew', true);
	}
});