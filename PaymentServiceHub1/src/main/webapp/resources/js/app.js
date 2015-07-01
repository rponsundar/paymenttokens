'use strict';

var paymentApp = angular.module("ngNewPay", [ 'ngRoute', 'ngResource' ]);

paymentApp.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/getUrn', {
		templateUrl : 'pages/generate_urn.html',
		controller : 'UserURNCtrl'
	});
	$routeProvider.when('/getToken', {
		templateUrl : 'pages/redeem_token.html',
		controller : 'PaymentTokenCtrl'
	});
	$routeProvider.otherwise({
		redirectTo : '/login'
	});
} ]);

paymentApp.factory('PaymentHubService', [ '$resource', function($resource) {

	return new PaymentHubService($resource);
} ]);

function PaymentHubService(resource) {

	var urnmessage = '';
	var tokenmessage = '';

	this.resource = resource;

	return {

		generateUrn : function(card) {

			var Card = resource('/PaymentServiceHub/psh/urn');
			Card.save(card, function(response) {
				console.log('URN from server:' + response.message);
				urnmessage = response.message;
			});

			return urnmessage;
		},

		getToken : function(urn) {
			var URN = resource('/PaymentServiceHub/psh/token');
			URN.save(urn, function(response) {
				console.log('Token from Server:' + response.message);
				tokenmessage = response.message;
			});

			return tokenmessage;
		}

	}

}

paymentApp.controller('UserURNCtrl', [ '$scope', 'PaymentHubService',
		function($scope, PaymentHubService) {
			$scope.card = {};
			var message = 'test';
			$scope.generateUrn = function(card) {
				message = PaymentHubService.generateUrn(card);
			};
			$scope.message = message;
		} ]);

paymentApp.controller('PaymentTokenCtrl', [ '$scope', 'PaymentHubService',
		function($scope, PaymentHubService) {
			$scope.user = {};
			var message = '';
			$scope.getToken = function(user) {
				message = PaymentHubService.getToken(user)
			};

			$scope.message = message;

		} ]);
