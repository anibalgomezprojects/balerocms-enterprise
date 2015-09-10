angular.module('settingsApp', ['settingsService'])
.controller('settingsController', function($scope, settingsService){
        $scope.settings = settingsService.getSettings().query();
        $scope.togglechange = function() {
            settingsService.postSettings($scope.settings.offline).save();
            $scope.settings = settingsService.getSettings.query();
        }
    });