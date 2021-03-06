/**
 * Balero CMS Project: Proyecto 100% Mexicano de código libre.
 * Página Oficial: http://www.balerocms.com
 *
 * @author      Anibal Gomez <anibalgomez@icloud.com>
 * @copyright   Copyright (C) 2015 Neblina Software. Derechos reservados.
 * @license     Licencia BSD; vea LICENSE.txt
 */

'use strict';


angular


    .module('MediaApp', ['angularFileUpload', 'MediaService'])


    .controller('MediaController', ['$scope', '$http', 'FileUploader', function($scope, $http, FileUploader) {

        $('#spinner').show();

        $scope.deleteQueue = [];
        $scope.chk = [];
        $scope.status = {
            selectedAll: false,
            icon: 'check'
        };

        $scope.load = function() {
            // Force Clean / Reset
            $scope.chk = [];
            $scope.deleteQueue = [];
            $scope.status = {
                selectedAll: false,
                icon: 'check'
            };
            $http.get('../admin/api/uploads').success(function(data) {
                $scope.uploads = data;
                $('#spinner').hide();
            });
        };

        $scope.addRow = function(index, fileName) {
            console.log('val: ' + $scope.chk[index] + ' ' + fileName);
            $scope.chk[index] ? $scope.deleteQueue.push({
                'fileName': fileName,
                'width': 0,
                'height': 0
                })  : $scope.deleteQueue.splice(0, 1);
        };

        $scope.removeFiles = function() {
            $('#spinner').show();
            $('.gallery').hide();
            var res = $http.post('../admin/api/uploads', $scope.deleteQueue );
            res.success(function(data, status, headers, config) {
                $scope.load();
            });
            res.error(function(data, status, headers, config) {
                alert( "failure message: " + JSON.stringify({data: data}));
            });
        };

        $scope.checkAll = function() {
            if($scope.status.selectedAll) {
                console.debug('false');
                $scope.status.selectedAll = false;
                $scope.status.icon = 'check';
                $scope.deleteQueue = [];
            } else {
                console.debug('true');
                $scope.status.selectedAll = true;
                $scope.status.icon = 'ok-sign';
                $scope.deleteQueue = $scope.uploads;
            }
            for(var i = 0; i < $scope.uploads.length; i++) {
                $scope.chk[i] = $scope.status.selectedAll;
            }
        };

        var uploader = $scope.uploader = new FileUploader({
            url: '/upload'
        });

        // FILTERS

        uploader.filters.push({
            name: 'imageFilter',
            fn: function(item /*{File|FileLikeObject}*/, options) {
                var type = '|' + item.type.slice(item.type.lastIndexOf('/') + 1) + '|';
                return '|jpg|png|jpeg|bmp|gif|'.indexOf(type) !== -1;
            }
        });

        // CALLBACKS

        uploader.onWhenAddingFileFailed = function(item /*{File|FileLikeObject}*/, filter, options) {
            console.info('onWhenAddingFileFailed', item, filter, options);
        };
        uploader.onAfterAddingFile = function(fileItem) {
            console.info('onAfterAddingFile', fileItem);
        };
        uploader.onAfterAddingAll = function(addedFileItems) {
            console.info('onAfterAddingAll', addedFileItems);
        };
        uploader.onBeforeUploadItem = function(item) {
            console.info('onBeforeUploadItem', item);
        };
        uploader.onProgressItem = function(fileItem, progress) {
            console.info('onProgressItem', fileItem, progress);
        };
        uploader.onProgressAll = function(progress) {
            console.info('onProgressAll', progress);
        };
        uploader.onSuccessItem = function(fileItem, response, status, headers) {
            console.info('onSuccessItem', fileItem, response, status, headers);
        };
        uploader.onErrorItem = function(fileItem, response, status, headers) {
            console.info('onErrorItem', fileItem, response, status, headers);
        };
        uploader.onCancelItem = function(fileItem, response, status, headers) {
            console.info('onCancelItem', fileItem, response, status, headers);
        };
        uploader.onCompleteItem = function(fileItem, response, status, headers) {
            console.log('update gallery');
            console.info('onCompleteItem', fileItem, response, status, headers);
        };
        uploader.onCompleteAll = function() {
            console.info('onCompleteAll');
            $scope.load();
        };

        console.info('uploader', uploader);
    }]);