/**
 * api for RESTful operation
 */

/**
 * use case: <a href="/user/12" onclick="doRestDelete(this,'confirm delete?');return false;">delete</a>
 */
function doRestDelete(anchor,confirmMsg) {
	if (confirm(confirmMsg)) {
        var f = document.createElement("form");
        f.style.display = "none";
        anchor.parentNode.appendChild(f);
        f.method = "POST";
        f.action = anchor.href;
        var m = document.createElement("input");
        m.setAttribute("type", "hidden");
        m.setAttribute("name", "_method");
        m.setAttribute("value", "delete");
        f.appendChild(m);
        f.submit();
	}
}

function doRestUpdate(anchor) {
        var f = document.createElement("form");
        f.style.display = "none";
        anchor.parentNode.appendChild(f);
        f.method = "post";
        f.action = anchor.href;
        var m = document.createElement("input");
        m.setAttribute("type", "hidden");
        m.setAttribute("name", "_method");
        m.setAttribute("value", "put");
        f.appendChild(m);
        f.submit();
}

function doRestBatchDelete(action,checkboxName,form) {
	if (!hasOneChecked(checkboxName)) {
		alert("请选择你要删除的对象!");
		return;
	}
    if(confirm("你确认要删除?")){
        form.action = action;
        form.method = 'POST';

        var m = document.createElement("input");
        m.setAttribute("type", "hidden");
        m.setAttribute("name", "_method");
        m.setAttribute("value", "delete");
        form.appendChild(m);

        form.submit();
    }
}
function disableSubmit(finalResult,submitButtonId) {
    if(finalResult) {
        document.getElementById(submitButtonId).disabled = true;
        return finalResult;
    }else {
        return finalResult;
    }
}
function batchOperate(opname,action,checkboxName,form){
    if (!hasOneChecked(checkboxName)){
        modelshow('请选择要操作的对象!','注意');
        return;
    }
    modelshow('确定执行['+opname+']操作?','注意',null,{confirm:function(){
        form.action = action;
        form.submit();
    }});
}
function batchDelete(action,checkboxName,form){
    if (!hasOneChecked(checkboxName)){
        modelshow('请选择要操作的对象!','注意');
        return;
    }
    modelshow('确定执行[删除]操作?','注意',null,{confirm:function(){
        form.action = action;
        form.submit();
    }});
}
function batchBack(action,checkboxName,form){
    if (!hasOneChecked(checkboxName)){
        alert('请选择要操作的对象!');
        return;
    }
    if (confirm('确定执行[还原]操作?')){
        form.action = action;
        form.submit();
    }
}

function hasOneChecked(name){
    var items = document.getElementsByName(name);
    if (items.length > 0) {
        for (var i = 0; i < items.length; i++){
            if (items[i].checked == true){
                return true;
            }
        }
    } else {
        if (items.checked == true) {
            return true;
        }
    }
    return false;
}

function setAllCheckboxState(name,state) {
    var elms = document.getElementsByName(name);
    for(var i = 0; i < elms.length; i++) {
        elms[i].checked = state;
    }
}

function getReferenceForm(elm) {
    while(elm && elm.tagName != 'BODY') {
        if(elm.tagName == 'FORM') return elm;
        elm = elm.parentNode;
    }
    return null;
}
$(function(){
    $(".rest-delete").click(function(){
        _defaultMsg="确定要删除吗？";
        if($(this).attr("confirm-msg")!=null){
            _defaultMsg=$(this).attr("confirm-msg");
        }
        doRestDelete($(this)[0],_defaultMsg);
        return false;
    });
    $(".rest-batch-delete").click(function(){
        doRestBatchDelete($(this).attr("href"),"items",document.forms.queryForm);
        return false;
    });
});