/**
 * Balero CMS Project: Proyecto 100% Mexicano de código libre.
 * Página Oficial: http://www.balerocms.com
 *
 * @author      Anibal Gomez <anibalgomez@icloud.com>
 * @copyright   Copyright (C) 2015 Neblina Software. Derechos reservados.
 * @license     Licencia BSD; vea LICENSE.txt
 */

angular.module('DashboardApp', ['DashboardService'])
.controller('DashboardController', function($scope, DashboardService){
        console.log('load');
        $scope.posts = DashboardService.getPosts().query();
        $scope.blotoggle = function(bloid, bloindex) {
            DashboardService.postPosts(bloid).save();
            $scope.posts[bloindex].likes = $scope.posts[bloindex].likes + 1;
        };
    });