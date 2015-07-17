'use strict';

// Declare app level module which depends on views, and components
angular.module('app', [
  'ngRoute',
  'app.view1'
])
.config(['$routeProvider', function($routeProvider) {
  $routeProvider.otherwise({redirectTo: '/view1'});
}])

//make rules and lodash injectable
.factory('maxant', function(){return window.maxant;})
.factory('_', function(){return window._;})
;