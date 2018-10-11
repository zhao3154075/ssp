var imgUploader = function (target,file,successCallBack,errorCallBack) {
    commonUploader($(target),$(file),successCallBack,errorCallBack);
}

var commonUploader = function (target,file,successCallBack,errorCallBack) {
    target.on("click",function () {
        file.click();
    });
    file.on('change', function () {
        //获取token
        $.ajax({
            type: 'get',
            url: 'qnuploadkey',
            success: function (data) {
                var token = data.uptoken;
                var key = data.key;
                if (file[0].files.length > 0 && token != "") {
                    doUpload(file[0], token, key,successCallBack,errorCallBack);
                } else {
                    console && console.log("form input error");
                }
            },
            error:function () {
                alert("上传接口签名失败");
            }
        });

    })

}

//普通上传
var doUpload = function (f, token, key, successCallBack ,errorCallBack) {
    var xhr = new XMLHttpRequest();
    xhr.open('POST', "http://up.qiniu.com", true);
    var formData = new FormData();
    if (key !== null && key !== undefined) formData.append('key', key);
    formData.append('token', token);
    formData.append('file', f.files[0]);
    xhr.onreadystatechange = function (response) {
        f.value="";
        if (xhr.readyState == 4 && xhr.status == 200 && xhr.responseText != "") {
            successCallBack("https://v-img.zjol.com.cn/" + key);
        } else if (xhr.status != 200 && xhr.responseText) {
            errorCallBack(xhr.responseText);
        }
    };
    xhr.send(formData);
};