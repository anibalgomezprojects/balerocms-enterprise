/**
 * Summernote User Settings
 * Balero CMS Enterprise (balerocms.com)
 * @author Anibal Gomez <anibalgomez@icloud.com>
 * Based on: http://stackoverflow.com/questions/28812825/custom-toolbar-with-summernote
 */

$(function() {
    $('.summernote').each(function(i, obj) {
        $(obj).summernote({
            height: 200,
            onImageUpload: function (files, editor, welEditable) {
                console.log($(obj).attr('id'));
                var id = $(obj).attr('id');
                sendFile(files[0], id, welEditable);
            },
            toolbar: [
                // TODO: Commented Lines, Need To Be Fixed With Java Functions
                //['style', ['style']],
                ['font', ['bold', 'italic', 'underline', 'clear']],
                ['fontname', ['fontname']],
                ['color', ['color']],
                //['para', ['ul', 'ol', 'paragraph']],
                //['height', ['height']],
                //['table', ['table']],
                ['insert', ['link', 'picture']],
                ['view', ['fullscreen', 'codeview']],
                //['help', ['help']]
            ]
        });
    });
});
function sendFile(file, editor, welEditable) {
    data = new FormData();
    data.append("file", file);
    $.ajax({
        data: data,
        type: "POST",
        xhr: function() {
            var myXhr = $.ajaxSettings.xhr();
            if (myXhr.upload) myXhr.upload.addEventListener('progress',progressHandlingFunction, false);
            return myXhr;
        },
        url: "/upload",
        cache: false,
        contentType: false,
        processData: false,
        success: function(url) {
            console.log('sucess upload: ' + file.name);
            //editor.insertImage(welEditable, 'http://localhost:8080/images/uploads/test.png');
            $('#' + editor).summernote(
                "insertImage", "/images/uploads/" + file.name, "Image Title: " + file.name
            );
        }
    });
}
// update progress bar
function progressHandlingFunction(e){
    if(e.lengthComputable){
        $('progress').attr({value:e.loaded, max:e.total});
        // reset progress on complete
        if (e.loaded == e.total) {
            $('progress').attr('value','0.0');
        }
    }
}