var authenticationServices = angular.module('Topchef.Authentication', ['ngResource']);

authenticationServices.service('AuthService', function ($injector, $window, $location) {
   var authService = {
      currentUser: null
   };
   var userSessionName = 'currentUser';
   authService.login = function (credentials) {
      var payload = 'username=' + credentials.username + '&password=' + credentials.password;
      var $http = $injector.get('$http');
      $http({
            method: 'POST',
            data: payload,
            url: 'login',
            headers: {
               "Content-Type": 'application/x-www-form-urlencoded'
            }
         })
         .success(function (user, status, headers) {
        	 // somehow this function is called with 401 errors
    		 $location.url($location.path()); // clear search params        	 
        	 if(!user || status == 0) {	 
        		 $location.url('#/').search( { 'error' : 'login' });        		 
        		 return;
        	 } else {
        		 authService.currentUser = user;
        		 $window.sessionStorage.setItem(userSessionName, JSON.stringify(user));
        		 $location.path('/users/' + user.id);
         	};
         })
         .error(function (data) {
            $window.sessionStorage.removeItem(userSessionName);
            authService.currentUser = null;
            $location.url($location.path()); // clear search params
            $location.url('#/').search( { 'error' : 'login' });        		 
         });
   };
   authService.clear = function () {
      authService.currentUser = null;
      $window.sessionStorage.removeItem(userSessionName);
   }
   authService.logout = function () {
      authService.clear();
      var $http = $injector.get('$http');
      $http({
            method: 'POST',
            url: 'logout',
         })
         .success(function (data) {
            $location.path("/")
               .search('error', 'logout');
         })
         .error(function (data) {
            $location.path("/");
         });
   };
   authService.userInSession = function () {
      return JSON.parse($window.sessionStorage.getItem(userSessionName));
   }
   authService.getUserId = function () {
      var user = authService.getCurrentUser();
      return user ? user.id : null;
   }
   authService.getUsername = function () {
      var user = authService.getCurrentUser();
      return user && user.userName;
   }
   authService.getCurrentUser = function () {
      var sessionUser = authService.userInSession();
      if (authService.currentUser) {
         $window.sessionStorage.setItem(userSessionName, JSON.stringify(authService.currentUser));
      } else if (sessionUser) {
         authService.currentUser = sessionUser;
      }
      return authService.currentUser;
   }
   authService.isAuthenticated = function () {
      return authService.getCurrentUser();
   };
   authService.hasRole = function (role) {
      var user = authService.getCurrentUser();
      if (user) {
         var roles = user.roles;
         return roles ? roles.indexOf(role) >= 0 : false;
      } else {
         return false;
      }
   };
   return authService;
});

authenticationServices.controller('AuthController',
	      function ($scope, $routeParams, AuthService, ErrorTypes, $location) {
	         $scope.error = ErrorTypes[$routeParams.error];
	         $scope.authService = AuthService;
	      });