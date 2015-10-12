/**
 * Balero CMS Project: Proyecto 100% Mexicano de código libre.
 * Página Oficial: http://www.balerocms.com
 *
 * @author      Anibal Gomez <anibalgomez@icloud.com>
 * @copyright   Copyright (C) 2015 Neblina Software. Derechos reservados.
 * @license     Licencia BSD; vea LICENSE.txt
 */

angular.module('UserApp', ['UserService', 'base64'])
.controller('UserController', function($scope, $base64, UserService){
        $scope.users = UserService.getUsers().query();
        $scope.blotoggle = function(bloEmail) {
            UserService.postUsers($base64.encode(bloEmail)).save();
            $scope.users = UserService.getUsers.query();
        }
    });