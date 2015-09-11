angular.module('UserApp', ['UserService'])
.controller('UserController', function($scope, UserService){
        $scope.users = UserService.getUsers().query();
        $scope.blotoggle = function(bloid) {
            UserService.postUsers(bloid).save();
            $scope.users = UserService.getUsers.query();
        }
    });