var bacthOperate=function (add_url,remove_url) {
    $("td .i-checks").on("ifClicked",function(){
        if(!$(this).is(":checked")){
            $.post(add_url,{items:$(this).val()});
        }else{
            $.post(remove_url,{items:$(this).val()});
        }
    });
    $("th .i-checks").on("ifClicked",function(){
        items="";
        if(!$(this).is(":checked")){
            $("td .i-checks").each(function(i){
                if(!$(this).is(":checked")){
                    if(items!=""){
                        items=items+",";
                    }
                    items=items+$(this).val();
                }
            });
            $.post(add_url,{items:items});
        }else{
            $("td .i-checks").each(function(i){
                if($(this).is(":checked")){
                    if(items!=""){
                        items=items+",";
                    }
                    items=items+$(this).val();
                }
            });
            $.post(remove_url,{items:items});
        }
    });
}