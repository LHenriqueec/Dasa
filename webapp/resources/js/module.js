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

	$http.post('classes/actions/ClienteActions/CarregarClientes.php')
		.then(function(response) {
			$scope.clientes = response.data.clientes;
		});

	$scope.novo = function() {
		$scope.cliente = {};
		isEdit = false;
	}

	$scope.salvar = function() {
		var json = angular.toJson($scope.cliente);
		
		if(isEdit) {
			$http.post('classes/actions/ClienteActions/AlterarCliente.php', json);
			console.info(json);
		} else {
			$http.post('classes/actions/ClienteActions/SalvarCliente.php', json)
				.then(function(response) {
					console.info(response.data);
				});
			$scope.clientes.push($scope.cliente);
		}
		
		$scope.cliente = {};
		isEdit = false;
	};

	$scope.alterar = function(cliente) {
		$scope.cliente = cliente;
		isEdit = true;
	};

	$scope.remover = function(cliente) {
		$http.post('classes/actions/ClienteActions/DeletarCliente.php', {"cnpj":cliente.cnpj});
		$scope.clientes.splice($scope.clientes.indexOf(cliente), 1);
		$scope.cliente = {};
		isEdit = false;
	};
});