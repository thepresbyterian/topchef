var navigationServices = angular.module('Topchef.Navigation', []);


navigationServices.controller('ProfileController', function($scope, $modalInstance, user) {
	$scope.user = user;
	$scope.validate = function() {
		return $scope.user && $scope.user.firstName && $scope.user.lastName && $scope.user.email && $scope.user.userName;
	}

	$scope.ok = function(user) {
		$modalInstance.close(user);
	}

	$scope.cancel = function() {
		$modalInstance.dismiss();
	}		
})

navigationServices.controller('NavigationController', function($scope, $location, $route, AuthService, User, $modal) {
	$scope.authService = AuthService;
	$scope.path = function() {
		return $location.path();
	}
	
    $scope.updateProfile = function() {            	
    	var modal = $modal.open({
    		templateUrl : 'views/navbar/profileModal.html',
    		controller : 'ProfileController',  
    		resolve : {
    			user : function() { return $scope.authService.getCurrentUser(); }
    		}
    	});
    	
    	modal.result.then(function(user) {
    		User.update(user).$promise.then(
    				function(response) {
    					$route.reload();
    				}, 
    				function(v) {            		
    				});          		
    	});            	          
    }
	
	$scope.startsWith = function(s1, s2) {
		return s1.indexOf(s2) !== -1;
	}
	
    $scope.isActive = function(route) {
        return (route === $location.path() || $scope.startsWith($location.path(), route));        
    }	
    
    $scope.bgClass = function() {
    	var path = $location.path();
    	var options = {
    			"/" : "splash",
    			"/admin" : "admin",
    			"/home" : "home",
    			"/about" : "about",
    			"/contact" : "contact"    			
    	}
    	
    	return options[path] || "home";
    }
});