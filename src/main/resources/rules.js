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
;
(function() {
	var _global = this;

	///////////////////////////////////////////
	// some javascript that we want to be able
	// to run on the server, but also on the
	// client
	///////////////////////////////////////////
	function rule419(input) {
		return _(input)
		.filter(function(e){ 
			return e.name === "John"; 
		})
		.value().length == 1 ? "OK" : "Scam";
	};

	///////////////////////////////////////////
	//create and assemble object for exporting
	///////////////////////////////////////////
	var maxant = {};
	maxant.rule419 = rule419;

	///////////////////////////////////////////
	//export module depending upon environment
	///////////////////////////////////////////
	if (typeof (module) != 'undefined' && module.exports) {
		// Publish as node.js module
		module.exports = maxant;
	} else if (typeof define === 'function' && define.amd) {
		// Publish as AMD module
		define(function() {
			return maxant;
		});
	} else {
		// Publish as global (in browsers and rhino/nashorn)
		var _previousRoot = _global.maxant;

		// **`noConflict()` - (browser only) to reset global 'maxant' var**
		maxant.noConflict = function() {
			_global.maxant = _previousRoot;
			return maxant;
		};

		_global.maxant = maxant;
	}
}).call(this);
