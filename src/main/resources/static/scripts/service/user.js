/**
 * Balero CMS Project: Proyecto 100% Mexicano de código libre.
 * Página Oficial: http://www.balerocms.com
 *
 * @author      Anibal Gomez <anibalgomez@icloud.com>
 * @copyright   Copyright (C) 2015 Neblina Software. Derechos reservados.
 * @license     Licencia BSD; vea LICENSE.txt
 */

angular.module('UserService', ['ngResource'])
    .factory('UserService', function($resource) {
        return {
            getUsers: function() {
                return $resource('../admin/api/users', {}, {
                    query: { method: 'GET', params: {}, isArray: true }
                })
            },
            postUsers: function(bloEmail) {
                return $resource('../user/api/subscribe/'+bloEmail, {}, {
                    save: { method: 'POST', params: { id: bloEmail } }
                })
            }
        };
    });