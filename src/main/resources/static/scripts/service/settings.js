angular.module('settingsService', ['ngResource'])
    .factory('settingsService', function($resource) {
        return $resource('../admin/api/settings',{ }, {
            getData: {method:'GET', isArray: false},
            setOfflineTrue: {method:'POST', params: { offline: 1 }},
            setOfflineFalse: {method:'POST', params: { offline: 0 }}
        });
    });