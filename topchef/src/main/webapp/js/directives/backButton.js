angular.module('History', [])
	.directive('backButton', ['$window', function($window) {
    return {
        restrict: 'A',
        link: function (scope, elem, attrs) {
            elem.bind('click', function (evt) {
                $window.history.back();
            });
        }
    };
}]);
