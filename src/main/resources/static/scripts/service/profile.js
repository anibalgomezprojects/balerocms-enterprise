/**
 * Balero CMS Project: Proyecto 100% Mexicano de código libre.
 * Página Oficial: http://www.balerocms.com
 *
 * @author      Anibal Gomez <anibalgomez@icloud.com>
 * @copyright   Copyright (C) 2015 Neblina Software. Derechos reservados.
 * @license     Licencia BSD; vea LICENSE.txt
 */

angular.module('ProfileService', ['ngResource'])
    .factory('ProfileService', function($resource) {
        return {
            getUser: function(bloid) {
                return $resource('../admin/api/user/'+bloid, {}, {
                    query: { method: 'GET', params: {}, isArray: false }
                })
            },
            postUser: function(bloId) {
                return $resource('../admin/api/subscribe/'+bloId, {}, {
                    save: { method: 'POST', params: { id: bloId } }
                })
            }
        };
    });