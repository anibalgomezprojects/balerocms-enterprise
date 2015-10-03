/**
 * Balero CMS Project: Proyecto 100% Mexicano de código libre.
 * Página Oficial: http://www.balerocms.com
 *
 * @author      Anibal Gomez <anibalgomez@icloud.com>
 * @copyright   Copyright (C) 2015 Neblina Software. Derechos reservados.
 * @license     Licencia BSD; vea LICENSE.txt
 */

angular.module('DashboardService', ['ngResource'])
    .factory('DashboardService', function($resource) {
        return {
            getPosts: function() {
                return $resource('/user/api/posts', {}, {
                    query: { method: 'GET', params: {}, isArray: true}
                })
            },
            postPosts: function(bloId) {
                return $resource('../api/blog/'+bloId, {}, {
                    save: { method: 'POST', params: { id: bloId }, isArray: false }
                })
            }
        };
    });