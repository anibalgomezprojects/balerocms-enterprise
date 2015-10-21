/**
 * Balero CMS Project: Proyecto 100% Mexicano de código libre.
 * Página Oficial: http://www.balerocms.com
 *
 * @author      Anibal Gomez <anibalgomez@icloud.com>
 * @copyright   Copyright (C) 2015 Neblina Software. Derechos reservados.
 * @license     Licencia BSD; vea LICENSE.txt
 */

angular.module('MediaService', ['ngResource'])
    .factory('MediaService', function($resource) {
        return {
            getUploads: function() {
                return $resource('../admin/api/uploads', {}, {
                    query: { method: 'GET', params: {}, isArray: true}
                })
            }
        };
    });