angular.module('UserService', ['ngResource'])
    .factory('UserService', function($resource) {
        return {
            getUsers: function() {
                return $resource('../admin/api/users', {}, {
                    query: { method: 'GET', params: {}, isArray: true }
                })
            },
            postUsers: function(bloId) {
                return $resource('../admin/api/subscribe/'+bloId, {}, {
                    save: { method: 'POST', params: { id: bloId } }
                })
            }
        };
    });