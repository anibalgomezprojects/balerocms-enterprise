angular.module('settingsApp', ['settingsService'])
.controller('settingsController', function($scope, settingsService){
        settingsService.getData(function(data){
            $scope.settings = data;
            $scope.blostatus = $scope.settings.offline;
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