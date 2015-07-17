/* Copyright 2015 Ant Kutschera

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License. */
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
