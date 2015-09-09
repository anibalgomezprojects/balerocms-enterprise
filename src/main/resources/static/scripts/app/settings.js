angular.module('settingsApp', ['settingsService'])
.controller('settingsController', function($scope, settingsService){
        settingsService.getData(function(data){
            $scope.settings = data;
            if($scope.settings.offline == 1) {
                $scope.blostatus = true;
            }
            if($scope.settings.offline == 0) {
                $scope.blostatus = false;
            }
        });
        $scope.togglechange = function() {
            if($scope.blostatus == true) {
                settingsService.setOfflineTrue(function(data){
                    console.log($scope.blostatus);
                    $scope.settings = data;
                });
            }
            if($scope.blostatus == false) {
                settingsService.setOfflineFalse(function(data){
                    console.log($scope.blostatus);
                    $scope.settings = data;
                });
            }
        }
    });