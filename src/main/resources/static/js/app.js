 $(function() {
        $('.summernote').summernote({
            height: 200,
            onImageUpload: function(files, editor, welEditable) {
                sendFile(files[0], editor, welEditable);
            }
        });
    });
function sendFile(file, editor, welEditable) {
    //alert('funciona!');
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
            $(".summernote").summernote(
                "insertImage", "/images/uploads/" + file.name, "Image Title: " + file.name
            );
        }
    });
}