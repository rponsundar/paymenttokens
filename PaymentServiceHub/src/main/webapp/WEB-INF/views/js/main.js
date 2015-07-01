'use strict';
var myBank = angular.module("myBank", [ 'ngResource' ]);
myBank.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/login', {
		templateUrl : '/PaymentServiceHub/views/pages/login.html',
		controller : 'LoginCtrl'
	});
	$routeProvider.when('/urn', {
		templateUrl : '/PaymentServiceHub/views/pages/generate_urn.html',
		controller : 'UserURNCtrl'
	});
	$routeProvider.when('/urnresult', {
		templateUrl : '/PaymentServiceHub/views/pages/result_urn.html',
		controller : 'UserURNCtrl'
	});
	$routeProvider.otherwise({
		redirectTo : '/login'
	});
} ]);

myBank.factory('PaymentHubService', [ '$resource', '$q',
		function($resource, $q) {
			return new PaymentHubService($resource, $q);
		} ]);

myBank.factory('User', function() {
	 var savedData = {}
	 function set(data) {
	   savedData = data;
	 }
	 function get() {
	  return savedData;
	 }

	 return {
	  set: set,
	  get: get
	 }

	});

myBank.controller('LoginCtrl', [ '$scope', '$location', 'PaymentHubService', '$http', 'User',
		function($scope, $location, PaymentHubService, $http, User) {
			$scope.loginUser = function(user) {
			      $http({
				    url: "/PaymentServiceHub/psh/login", 
				    method: "POST",
				    contentType : 'application/json',
				    data : JSON.stringify({
						"userId" : user.userId,
						"password" : user.password
					})
			      }).success(function(response) { 
			    	   if (response == 'true'){
		            	myBank.user = user;
		            	User.set(user);
						$location.path('/urn');
			    	   } else {
			    		   alert('Invalid Credentials');
			    		   $location.path('/login'); 
			    		   
			    	   }
			       }) ;
			};
		} ]);

myBank.controller('UserURNCtrl', [ '$scope',  '$rootScope', '$location', 'PaymentHubService', '$http', 'User',
		function($scope, $rootScope, $location, PaymentHubService, $http, User) {
	        $http.get("/PaymentServiceHub/psh/cards/"+User.get().userId)
	            .success(function(response) {
	            	$scope.custCards = response;
	            	});
			$scope.generateUrn = function(card) {
				PaymentHubService.generateUrn(card).then(function(data) {
					var urn = {};
					urn = data.urn;
					$rootScope.urn = urn;
					if ($rootScope.urn != null) {
						$location.path('/urnresult');
					} else {
						alert("Unable to generate Unique Reference Number");
					}
				});
			};
			$scope.login = function() {
				$location.path('/login');
			};
			$scope.back = function() {
				$location.path('/urn');
			};
		} ]);

function PaymentHubService(resource, q) {
	return {
		generateUrn : function(card) {
			var Card = resource('/PaymentServiceHub/psh/urn');
			var deferred = q.defer();
			Card.save(card, function(response) {
				deferred.resolve(response);
			});
			return deferred.promise;
		},
		validateUser : function (user) {
			if(!myBank.user) {
				return true;
			} else {
				return false;
			}
		}
	}
};