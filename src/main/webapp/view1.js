'use strict';

angular.module('app.view1', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider
  .when('/view1', {
	    templateUrl: 'view1.html',
	    controller: 'View1Ctrl'
	  });
}])

/** the view's controller */
.controller('View1Ctrl', ['$scope', '$http', '$routeParams', 'maxant',
  function($scope, $http, $routeParams, maxant) {

	//create a model
	var model = [
	             {name: 'Ant'}, 
	             {name: 'John'}
	             ];
	
	//force failure if required
	if($routeParams.scam){
		model[0].name = model[1].name;
	}
	
	//execute javascript that can also be executed on the server using Java
	$scope.clientResult = maxant.rule419(model);
	
	//now execute the same javascript on the server
	$http.post('x/services/rule419', model).success(function(data){
		$scope.serverResult = data.result;
	});
}]);