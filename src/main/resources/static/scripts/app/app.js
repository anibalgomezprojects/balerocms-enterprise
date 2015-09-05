var app = angular.module('myApp', []);
app.controller('customersCtrl', function($scope, $http) {
    $http.get("../test/info.json")
        .success(function(response) {$scope.names = response.records;});
});