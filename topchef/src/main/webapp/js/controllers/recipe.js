var recipeServices = angular.module('Topchef.Recipe', ['ngResource']);

recipeServices.factory('Recipe', ['$resource',
   function ($resource) {
      return $resource('api/users/:uid/recipes/:id', {
         uid: '@uid',
         id: '@id'
      }, {
         save: {
            method: 'POST',
            params: {
               title: '@title'
            }
         },
      });
   }
]);

recipeServices.factory('Ingredient', ['$resource',
   function ($resource) {
      return $resource('api/users/:uid/recipes/:rid/ingredients/:id', {
         rid: '@rid',
         uid: '@uid',
         id: '@id'
      }, {
         save: {
            method: 'POST',
            params: {
               description: '@description',
               quantity: '@quantity',
               unitId: '@unitId'
            }
         },
      });
   }
]);

recipeServices.factory('Step', ['$resource',
   function ($resource) {
      return $resource('api/users/:uid/recipes/:rid/steps/:number', {
         uid: '@uid',
         rid: '@rid',
         number: '@number'
      }, {
         save: {
            method: 'POST',
            params: {
               step: '@step'
            }
         },
      });
   }
]);

recipeServices.controller('RecipesController', ['$scope', '$routeParams', 'Recipe', '$location', function ($scope, $routeParams, Recipe, $location) {
   $scope.userId = $routeParams.userId;
   $scope.selectedRecipe = null;
   $scope.recipes = [];
   $scope.deleteRecipe = function (id) {
      Recipe.remove({
            id: id,
            uid: $scope.userId
         },
         function (response) {
            $scope.recipes = $scope.recipes.filter(function (val) {
               return val.id != id;
            });
         }
      );
   };
   $scope.getRecipes = function () {
      Recipe.query({
         uid: $scope.userId
      }, function (response) {
         $scope.recipes = response;
      });
   };
   $scope.addRecipe = function () {
      var recipe = Recipe.save({
            uid: $scope.userId,
            title: $scope.recipeTitle
         },
         function (recipe) {
            $location.path('users/' + $scope.userId + '/recipes/' + recipe.id);
         }
      );
   };
   $scope.getRecipes();
}]);

recipeServices.controller('RecipeDetailsController', ['$scope', '$routeParams', 'Recipe', 'Ingredient', 'Step', '$route', function ($scope, $routeParams, Recipe, Ingredient,
   Step, $route) {
   $scope.recipeId = $routeParams.rid;
   $scope.userId = $routeParams.userId;
   $scope.quantity = 1;
   $scope.ingredient = "";
   $scope.selectedUnit = "";
   $scope.edit = false;
   var clearInputs = function () {
      $scope.quantity = 1;
      $scope.ingredient = "";
      $scope.selectedUnit = "";     
   };
   
   $.ajax({
      url: 'api/static/units',
      dataType: 'json',
      success: function (result) {
         $scope.units = result;
      }
   });
   $scope.addIngredient = function () {
      var ingredient = Ingredient.save({
            rid: $scope.recipeId,
            uid: $scope.userId,
            description: $scope.ingredient,
            unitId: $scope.selectedUnit,
            quantity: $scope.quantity
         },
         function (response) {
            clearInputs();
            $scope.recipe.ingredients = response.ingredients;
            $('#ingredient')
               .focus();
         });
   };
   $scope.deleteIngredient = function (id) {
      Ingredient.remove({
            rid: $scope.recipeId,
            uid: $scope.userId,
            id: id
         },
         function (response) {
            $scope.recipe.ingredients = response.ingredients;
         });
   };
   $scope.addStep = function () {
      Step.save({
            rid: $scope.recipeId,
            uid: $scope.userId,
            step: $scope.stepText
         },
         function (response) {
            $('#steptext')
               .val('')
               .focus();
            $scope.recipe.steps = response.steps;
         });
   };
   $scope.deleteStep = function (number) {
      Step.remove({
            rid: $scope.recipeId,
            uid: $scope.userId,
            number: number
         },
         function (response) {
            $scope.recipe.steps = response.steps;
         });
   };
   $scope.getDetails = function () {
      Recipe.get({
         uid: $scope.userId,
         id: $scope.recipeId
      }, function (response) {
         $scope.recipe = response;
      });
   };
   $scope.getDetails();
}]);