angular.module('Topchef.User', ['ngResource'])
   .factory('Users', ['$resource', function ($resource) {
      return $resource('api/users/', {}, {
         get: {
            method: 'GET',
            params: {
               username: '@username'
            },
            isArray: true
         },
         update: {
            method: 'PUT',
            data: {},
            isArray: false
         },
      });
   }])
   
   .factory('User', ['$resource', function ($resource) {
      return $resource('api/users/:userid', {
         userid: '@id'
      }, {
         get: {
            method: 'GET',
            isArray: false
         },
         update: {
            method: 'PUT',
            data: {},
            isArray: false
         },
      });
   }])
   
   .controller('NewUserController', function($scope, $modalInstance) {
	   $scope.newUser = {};
	   $scope.validate = function() {
		   return $scope.newUser && $scope.newUser.firstName &&
		   		$scope.newUser.lastName && $scope.newUser.email &&
		   		$scope.newUser.userName;
	   }
	   
       $scope.ok = function() {    	 
    	   $modalInstance.close($scope.newUser);
       }
       
       $scope.cancel = function() {
    	   $modalInstance.dismiss();
       }
   })	
   
   .controller('ConfirmController', function($scope, $modalInstance, user) {
	   $scope.modal = {};
	   $scope.modal.body = "Do you really want to delete user " + user.userName + "?  All of their data will be " +
	   		"permanently removed from the system.";
	   $scope.modal.header = "Confirm Delete";
	   
       $scope.ok = function() {    	 
    	   $modalInstance.close(true);
       }
       
       $scope.cancel = function() {
    	   $modalInstance.dismiss();
       }	   
   })
   
   .controller('AdminController', ['$scope', '$route', '$routeParams', 'User', 'Users', '$location', '$modal',
         function ($scope, $route, $routeParams, User, Users, $location, $modal) {
            $scope.users = [];
            
            $scope.deleteUser = function (user) {
            	var modal = $modal.open({
            		templateUrl : 'views/admin/confirmModal.html',
            		controller : 'ConfirmController',
            		resolve : { user : function() { return user; } }
            	});
            	
            	modal.result.then(function(result) {
                    User.remove({
                        userid: user.id,
                     }, $scope.getUsers);            		
            	});
            };
            
            $scope.adminInRoles = function(user) {
            	return user.roles.indexOf('ROLE_ADMIN') >= 0;
            }
            
            $scope.setAdmin = function(user, isAdmin) {
            	if(isAdmin &&  !$scope.adminInRoles(user)) {
            		user.roles.push('ROLE_ADMIN');            		
            		$scope.update(user);            		
            	} else if(!isAdmin && $scope.adminInRoles(user)) {
            		user.roles = ['ROLE_USER'];
            		$scope.update(user);
            	}
            };
            
            $scope.update = function (user) {
               User.update(user);
            };
            
            $scope.getUsers = function () {
               Users.get({
                  userid: $scope.userId
               }, function (response) {
                  $scope.users = response;
               });
            };
          
            $scope.addUser = function() {            	
            	var modal = $modal.open({
            		templateUrl : 'views/admin/newUserModal.html',
            		controller : 'NewUserController',            		
            	});
            	
            	modal.result.then(function(newUser) {
            		User.save(newUser).$promise.then(
            				function(response) {
            					$route.reload();
            				}, 
            				function(v) {            		
            				});            		
            	});            	          
            }
            
            $scope.getUsers();
         }                           
      ]);
      
