angular.module('myApp', [])
.controller('customersCtrl', function($scope, $http) {
    alert('test');
    $http.get("../test/info.json")
        .success(function(response) {$scope.names = response.records;});
});