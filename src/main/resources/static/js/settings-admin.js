/**
 * Summernote Multiple Editors / Image Uploader
 * Balero CMS Enterprise (balerocms.com)
 * @author Anibal Gomez <anibalgomez@icloud.com>
 * Based on : http://stackoverflow.com/questions/22619110/multiple-summer-note-divs-on-one-page
 * Based on: http://stackoverflow.com/questions/3239598/how-can-i-get-the-id-of-an-element-using-jquery
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
                 // Full Editor
                 ['style', ['style']],
                 ['font', ['bold', 'italic', 'underline', 'clear']],
                 ['fontname', ['fontname']],
                 ['color', ['color']],
                 ['para', ['ul', 'ol', 'paragraph']],
                 ['height', ['height']],
                 ['table', ['table']],
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