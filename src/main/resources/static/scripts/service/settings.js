angular.module('settingsService', ['ngResource'])
    .factory('settingsService', function($resource) {
        return $resource('../admin/api/settings',{ }, {
            getData: {method:'GET', isArray: false},
            setOfflineTrue: {method:'POST', params: { offline: true }},
            setOfflineFalse: {method:'POST', params: { offline: false }}
        });
    });