/**
 * Balero CMS Project: Proyecto 100% Mexicano de código libre.
 * Página Oficial: http://www.balerocms.com
 *
 * @author      Anibal Gomez <anibalgomez@icloud.com>
 * @copyright   Copyright (C) 2015 Neblina Software. Derechos reservados.
 * @license     Licencia BSD; vea LICENSE.txt
 */

angular.module('PropertyService', ['ngResource'])
    .factory('PropertyService', function($resource) {
        return {
            getProperties: function() {
                return $resource('../admin/api/properties', {}, {
                    query: { method: 'GET', params: {}, isArray: false }
                })
            },
            postProperties: function(properties) {
                return $resource('../admin/api/properties', {}, {
                    save: {
                        method: 'POST',
                        params: {
                            offline: properties.offline,
                            multiLanguage: properties.multiLanguage
                        }
                    }
                })
            }
        };
    });