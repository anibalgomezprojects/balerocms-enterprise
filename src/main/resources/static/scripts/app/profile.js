/**
 * Balero CMS Project: Proyecto 100% Mexicano de código libre.
 * Página Oficial: http://www.balerocms.com
 *
 * @author      Anibal Gomez <anibalgomez@icloud.com>
 * @copyright   Copyright (C) 2015 Neblina Software. Derechos reservados.
 * @license     Licencia BSD; vea LICENSE.txt
 */

angular.module('ProfileApp', ['ProfileService'])
.controller('ProfileController', function($scope, ProfileService){
        $scope.blotoggle = function(blotype) {
            ProfileService.postUser(blotype).save();
        }
        $scope.bloload = function (blotype) {
            $scope.user = ProfileService.getUser(blotype).query();
        }
    });