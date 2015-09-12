/**
 * Balero CMS Project: Proyecto 100% Mexicano de código libre.
 * Página Oficial: http://www.balerocms.com
 *
 * @author      Anibal Gomez <anibalgomez@icloud.com>
 * @copyright   Copyright (C) 2015 Neblina Software. Derechos reservados.
 * @license     Licencia BSD; vea LICENSE.txt
 */

angular.module('SettingApp', ['SettingService'])
.controller('SettingController', function($scope, SettingService){
        $scope.settings = SettingService.getSettings().query();
        $scope.blotoggle = function() {
            SettingService.postSettings($scope.settings.offline).save();
            $scope.settings = SettingService.getSettings.query();
        }
    });