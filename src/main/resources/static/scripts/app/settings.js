var app = angular.module('myApp', []);
app.controller('settingsCtrl', function($scope, $http) {
    $http.get("../admin/api/settings")
        .success(function(response) {
            $scope.settings = response.records;
        }
    );
});