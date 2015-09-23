/**
 * Balero CMS Project: Proyecto 100% Mexicano de código libre.
 * Página Oficial: http://www.balerocms.com
 *
 * @author      Anibal Gomez <anibalgomez@icloud.com>
 * @copyright   Copyright (C) 2015 Neblina Software. Derechos reservados.
 * @license     Licencia BSD; vea LICENSE.txt
 */

angular.module('BlogApp', ['BlogService'])
.controller('BlogController', function($scope, BlogService){
        $scope.posts = BlogService.getPosts().query();
        $scope.blotoggle = function(bloid, bloindex) {
            BlogService.postPosts(bloid).save();
            //$scope.posts = BlogService.getPosts().query();
            $scope.posts[bloindex].likes = $scope.posts[bloindex].likes + 1;
        };
    });