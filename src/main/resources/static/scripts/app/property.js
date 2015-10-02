/**
 * Balero CMS Project: Proyecto 100% Mexicano de código libre.
 * Página Oficial: http://www.balerocms.com
 *
 * @author      Anibal Gomez <anibalgomez@icloud.com>
 * @copyright   Copyright (C) 2015 Neblina Software. Derechos reservados.
 * @license     Licencia BSD; vea LICENSE.txt
 */

angular.module('PropertyApp', ['PropertyService'])
.controller('PropertyController', function($scope, PropertyService){
        $scope.properties = PropertyService.getProperties().query();
        $scope.blotoggle = function() {
            PropertyService.postProperties($scope.properties.offline).save();
            $scope.properties = PropertyService.getProperties.query();
        }
    });