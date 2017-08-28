var app = angular.module("menuApp", [ "ngRoute" ]);

app.config(function($routeProvider) {
	$routeProvider.when("/", {
		templateUrl : "views/main.html",
		controller : "mainController as main"
	}).when("/clientes", {
		templateUrl : "views/clientes.html",
		controller : "clienteCotnroller as cliente"
	}).when("/produtos", {
		templateUrl : "views/produtos.html",
		controller : "produtoController"
	}).when("/notas", {
		templateUrl : "views/notas.html",
		controller : "notaController"
	}).when("/itensNota", {
		templateUrl: "views/itensNota.html",
		controller: "itensNotaController"
	});
});


app.service("container", function() {
	this.notas = undefined;
	this.nota = undefined;

	this.inserir = function(nota) {
		if (!this.notas) this.notas = [];
		this.notas.push(nota);
	}
	
	this.remover = function(index) {
		this.notas.split(indes, 1);
		if(this.notas.length == 0) this.notas = undefined;
	}
});

// CONTROLLER: MAIN
app.controller("mainController", function($http) {
	var ctrl = this;
	ctrl.total = 0;
	ctrl.itens = [];
	ctrl.isNew = false;
	ctrl.clientes = undefined;
	ctrl.recibo = undefined;
	ctrl.recibos = undefined;
	ctrl.item = {};

	// Carrega o total de itens disponívels lançados pelas Notas
	$http.post("/Dasa/CarregarItensNotas.action").then(function(response) {
		ctrl.itens = response.data;

		if(ctrl.itens.length == 0) {
			ctrl.itens = undefined;
			return;
		}
		
		for (var i = 0; i < ctrl.itens.length; i++) {
			ctrl.total += ctrl.itens[i].quantidade;
			console.info(ctrl.total);
		}
	});

	ctrl.novoRecibo = function() {
		ctrl.recibo = {};
		ctrl.recibo.data = new Date();
		
		$http.post('/Dasa/CarregarClientes.action').then(function(response) {
			ctrl.clientes = response.data;

			if(ctrl.clientes.length == 0) ctrl.clientes = undefined;

		});

		if(!ctrl.recibos || ctrl.recibos.length == 0) {
			ctrl.recibo.numero = '17001';
		} else {
			var index = ctrl.recibos.length;
			console.info(Number.parseLong(ctrl.recibos[index].numero) + 1);
		}

	};

	ctrl.cancelar = function() {
		ctrl.isNew = false;
	};

	ctrl.selecionarCliente = function(cliente) {
		ctrl.cliente = cliente;
		ctrl.isNew = true;
	};

	ctrl.buscarProduto = function() {
		if (!ctrl.searchProduto)
			return;

		var req = {
			method : 'GET',
			url : '/Dasa/BuscarItem.action',
			params : {
				search : ctrl.searchProduto
			}
		};
		$http(req).then(
			function(response) {
				var item = response.data;
				ctrl.item.produto = item.produto;
				ctrl.disponivel = item.quantidade;

				if (!ctrl.item.produto) {
					ctrl.searchProduto = 'Não encontrado';
				} else {
					ctrl.searchProduto = ctrl.item.produto.codigo
					+ " - " + ctrl.item.produto.nome;
				}
			});
	};

});

// CONTROLLER: NOTA
app.controller("notaController", function($scope, $http, $rootScope, container) {
	$scope.isNew = false;
	$scope.nota = undefined;
	$scope.notas = undefined;
	
	$http.post('/Dasa/CarregarNotas.action').then(function(response) {
		console.info('carregado');
		console.info(response.data);
		$scope.notas = response.data;
		if($scope.notas.length == 0) $scope.notas = undefined;
	});

	$scope.novo = function() {
		$scope.isNew = true;
		$scope.nota = {};
		$scope.nota.data = new Date();
		var req = {
			method: 'POST',
			url: '/Dasa/CarregarClientes.action',
		};
		$http(req).then(function(response) {
			$scope.clientes = response.data;
		});
	};

	$scope.cancelar = function() {
		$scope.isNew = false;
	};

	$scope.confirmar = function() {
		container.nota = $scope.nota;
	};

	$scope.deletar = function(index) {
		var req = {
			method: 'GET',
			url: '/Dasa/DeletarNota.action',
			params: {nota:$scope.notas[index].numero}
		};

		$http(req);
		$scope.notas.splice(index, 1);
		if($scope.notas.length == 0) $scope.notas = undefined;
		
	};

	$scope.selecionarCliente = function(cliente) {
		$scope.nota.cliente = cliente;
	};

});

// CONTROLLER: ITENS NOTA
app.controller("itensNotaController", function($rootScope, $scope, $http, container) {
	$scope.item = {};
	$scope.itens = undefined;
	$scope.total = 0;
	$scope.searchProduto = undefined;
	$scope.nota = container.nota;
	
	var isEdit = false;
	
	console.info($rootScope.nota);

	$scope.buscarProduto = function() {
		if (!$scope.searchProduto)
			return;
		var req = {
			method : 'GET',
			url : '/Dasa/BuscarProduto.action',
			params : {
				search : $scope.searchProduto
			}
		};
		$http(req).then(
			function(response) {
				var produto = response.data;
				$scope.item.produto = produto;

				if (!$scope.item.produto) {
					$scope.searchProduto = 'Não encontrado';
				} else {
					$scope.searchProduto = $scope.item.produto.codigo
					+ " - " + $scope.item.produto.nome;
				}
			});
	};

	$scope.inserir = function() {
		if (!$scope.itens)
			$scope.itens = [];
		$scope.itens.push($scope.item);
		$scope.total = Number.parseInt($scope.total)
		+ Number.parseInt($scope.item.quantidade);
		limpa();

	};

	$scope.remover = function(item) {
		$scope.total = Number.parseInt($scope.total)
		- Number.parseInt($scope.itens[item].quantidade);
		$scope.itens.splice(item, 1);
		limpa();
		if ($scope.itens.length == 0)
			$scope.itens = undefined;
	};
	
	$scope.salvar = function() {
		$scope.nota.itens = $scope.itens;
		var req = {
			method: 'POST',
			url: '/Dasa/SalvarNota.action',
			params: {nota: $scope.nota}
		};
		
		$http(req);
		
		container.inserir($scope.nota);
		container.nota = undefined;
		$scope.nota = {};
		$scope.itens = undefined;
	}
	
	function limpa() {
		$scope.searchProduto = undefined;
		$scope.item = {};
	};
});

// CONTROLLER: PRODUTO

app.controller("produtoController", function($scope, $http) {
	$scope.isEdit = false;
	$scope.produto = {};

	$http.post('/Dasa/CarregarProdutos.action').then(function(response) {
		$scope.produtos = response.data;
	});

	$scope.editar = function(produto) {
		$scope.produto = produto;
		$scope.isEdit = true;
	};

	$scope.salvar = function() {
		var req = "";
		if ($scope.isEdit) {
			req = {
				method : 'POST',
				url : '/Dasa/AlterarProduto.action',
				params : {
					produto : $scope.produto
				}
			};
		} else {
			req = {
				method : 'POST',
				url : '/Dasa/SalvarProduto.action',
				params : {
					produto : $scope.produto
				}
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
			method : 'GET',
			url : '/Dasa/DeletarProduto.action',
			params : {
				codigo : produto.codigo
			}
		};
		$http(req);
	};
});

// CONTROLLER: CLIENTE

app.controller("clienteCotnroller", function($scope, $http) {

	var isEdit = false;
	$scope.clientes = [];

	$http.post('/Dasa/CarregarClientes.action').then(function(response) {
		$scope.clientes = response.data;
	});

	$scope.novo = function() {
		$scope.cliente = {};
		isEdit = false;
	}

	$scope.salvar = function() {
		var req = '';

		if (isEdit) {
			req = {
				metode : 'POST',
				url : '/Dasa/AlterarCliente.action',
				params : {
					cliente : $scope.cliente
				}
			};
		} else {
			req = {
				metode : 'POST',
				url : '/Dasa/SalvarCliente.action',
				params : {
					cliente : $scope.cliente
				}
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
			method : 'GET',
			url : '/Dasa/DeletarCliente.action',
			params : {
				cnpj : cliente.cnpj
			}
		};
		$http(req);
	};
});