/**
 * Balero CMS Project: Proyecto 100% Mexicano de código libre.
 * Página Oficial: http://www.balerocms.com
 *
 * @author      Anibal Gomez <anibalgomez@icloud.com>
 * @copyright   Copyright (C) 2015 Neblina Software. Derechos reservados.
 * @license     Licencia BSD; vea LICENSE.txt
 */

angular.module('SettingService', ['ngResource'])
    .factory('SettingService', function($resource) {
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