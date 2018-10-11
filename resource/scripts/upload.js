(function($) {
    $.extend($.fn, {
        fileUpload: function(opts) {
            this.each(function() {
                var $self = $(this);
                var doms = {
                    "fileToUpload": $(opts.fileInput)
                };
                var funs = {
                    //选择文件，获取文件大小，也可以在这里获取文件格式，限制用户上传非要求格式的文件
                    "fileSelected": function() {
                        var files = (doms.fileToUpload)[0].files;
                        if(opts.fileSelected(files)){
                            funs.uploadFile();
                        }else{
                            doms.fileToUpload.val("");
                        }
                    },
                    //异步上传文件
                    uploadFile: function() {
                        var fd = new FormData();//创建表单数据对象
                        var files = (doms.fileToUpload)[0].files;
                        var count = files.length;
                        for (var index = 0; index < count; index++) {
                            var file = files[index];
                            fd.append(opts.file, file);//将文件添加到表单数据中
                        }
                        var xhr = new XMLHttpRequest();
                        xhr.upload.addEventListener("progress", funs.uploadProgress, false);//监听上传进度
                        xhr.addEventListener("load", funs.uploadComplete, false);
                        xhr.addEventListener("error", opts.uploadFailed, false);
                        xhr.open("POST", opts.url);
                        xhr.send(fd);
                    },
                    uploadProgress: function(evt) {
                        if (evt.lengthComputable) {
                            var percentComplete = Math.round(evt.loaded * 100 / evt.total);
                            opts.uploadProgress(percentComplete);
                        }
                    },
                    "uploadComplete": function(evt) {
                        opts.uploadComplete(evt.target.responseText);
                    }
                };
                doms.fileToUpload.on("change", function() {
                    funs.fileSelected();
                });
            });
        }
    });
})(Zepto);