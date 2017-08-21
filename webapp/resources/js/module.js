var app = angular.module("menuApp", ["ngRoute"]);

app.config(function($routeProvider) {
	$routeProvider
		.when("/", {
			templateUrl : "views/main.html"
		})
		.when("/clientes", {
			templateUrl : "views/clientes.html",
			controller : "clienteCotnroller"
		})
		.when("/produtos", {
			templateUrl : "views/produtos.html",
			controller : "produtoController"
		});
});

//	CONTROLLER: PRODUTO

app.controller("produtoController", function($scope, $http) {
	$scope.isEdit = false;
	$scope.produto = {};

	$http.post('/Dasa/CarregarProdutos.action')
	.then(function(response) {
		$scope.produtos = response.data;
	});

	$scope.editar = function(produto) {
		$scope.produto = produto;
		$scope.isEdit = true;
	};	

	$scope.salvar = function() {
		var json = angular.toJson($scope.produto);
		var req = "";
		if($scope.isEdit) {
			req = {
				method: 'POST',
				url: '/Dasa/AlterarProduto.action',
				params: {produto:$scope.produto}
			};
		} else {
			req = {
					method: 'POST',
					url: '/Dasa/SalvarProduto.action',
					params: {produto:$scope.produto}
				};
			$scope.produtos.push($scope.produto);
		}

		$http(req);
		$scope.isEdit = false;
		$scope.produto = {};
	};

	$scope.remover = function(produto) {
		$scope.produtos.splice($scope.produtos.indexOf(produto), 1);
		$scope.isEdit = false;
		$scope.produto = {};
		var req = {
			method: 'GET',
			url: '/Dasa/DeletarProduto.action',
			params: {codigo:produto.codigo}
		};
		$http(req);
	};
});

//	CONTROLLER: CLIENTE

app.controller("clienteCotnroller", function($scope, $http) {

	var isEdit = false;
	$scope.clientes = [];

	$http.post('/Dasa/CarregarClientes.action')
		.then(function(response) {
			$scope.clientes = response.data;
		});

	$scope.novo = function() {
		$scope.cliente = {};
		isEdit = false;
	}

	$scope.salvar = function() {
		var req = '';
		
		if(isEdit) {
			req = {
					metode: 'POST',
					url: '/Dasa/AlterarCliente.action',
					params: {cliente:$scope.cliente}
			};
		} else {
			req = {
					metode: 'POST',
					url: '/Dasa/SalvarCliente.action',
					params: {cliente:$scope.cliente}
			};
			$scope.clientes.push($scope.cliente);
			console.info($scope.cliente);
		}
		
		$http(req);
		$scope.cliente = {};
		isEdit = false;
	};

	$scope.alterar = function(cliente) {
		$scope.cliente = cliente;
		isEdit = true;
	};

	$scope.remover = function(cliente) {
		$scope.clientes.splice($scope.clientes.indexOf(cliente), 1);
		$scope.cliente = {};
		isEdit = false;
		var req = {
				method: 'GET',
				url: '/Dasa/DeletarCliente.action',
				params: {cnpj:cliente.cnpj}
			};
			$http(req);
	};
});