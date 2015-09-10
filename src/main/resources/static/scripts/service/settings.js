angular.module('settingsService', ['ngResource'])
    .factory('settingsService', function($resource) {
        return {
            getSettings: function() {
                return $resource('../admin/api/settings', {}, {
                    query: { method: 'GET', params: {}, isArray: false }
                })
            },
            postSettings: function(bloStatus) {
                return $resource('../admin/api/settings', {}, {
                    save: { method: 'POST', params: { offline: bloStatus } }
                })
            }
        };
    });