var chefApp = angular.module('TopchefApp', ['ngSanitize', 'ngRoute', 'Topchef.Recipe', 'Topchef.Navigation', 'Topchef.Authentication', 'Topchef.User', 'spring-security-csrf-token-interceptor', 'History', 'ui.bootstrap', 'contenteditable']);

String.prototype.endsWith = function(suffix) {
    return this.indexOf(suffix, this.length - suffix.length) !== -1;
};

chefApp.factory('ErrorTypes', function() {
	var errors = {};
	errors['login'] = 'The combination of username and password is incorrect.  Please try again.';
	errors['timeout'] = 'You have been logged out due to inactivity.  Please log in.';
	errors['logout'] = 'You have successfully logged out';	
	return errors;
});

chefApp
.config(['$routeProvider', function($routeProvider, $location, AuthService) {
	$routeProvider.
	when('/', {
		templateUrl : 'views/splash.html',
		controller : 'AuthController'
	}).
	when('/admin', {
		templateUrl : 'views/admin/admin.html',
		controller : 'AdminController'
	}).	
	when('/users/:userId', {
		templateUrl : 'views/home/recipes.html',
		controller : 'RecipesController'
	}).
	when('/users/:userId/recipes', { 
		templateUrl: 'views/home/recipes.html',
		controller : 'RecipesController'
	}).
	when('/about', {
		templateUrl : 'views/about/about.html',
		controller : null
	}).
	when('/contact', {
		templateUrl : 'views/contact/contact.html',
		controller : null
	}).
	when('/users/:userId/recipes/:rid', {
		templateUrl: 'views/home/recipeDetails.html',
		controller: 'RecipeDetailsController'
	}).
	otherwise({ redirectTo : '/'});
}])
.run( function($rootScope, $location, AuthService) {
//	$rootScope.$on('$locationChangeStart', function(event, next, current) {
//		if(AuthService && AuthService.isAuthenticated() && (next.endsWith('#/') || next.endsWith('#'))) {
//			event.preventDefault();
//			$location.path('/users/' + AuthService.getCurrentUser().id + '/recipes');			
//		}
//	});
});

