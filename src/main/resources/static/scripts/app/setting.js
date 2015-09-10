angular.module('SettingApp', ['SettingService'])
.controller('SettingController', function($scope, SettingService){
        $scope.settings = SettingService.getSettings().query();
        $scope.blotoggle = function() {
            SettingService.postSettings($scope.settings.offline).save();
            $scope.settings = SettingService.getSettings.query();
        }
    });