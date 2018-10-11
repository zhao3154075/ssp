
/**
 * author: badqiu
 * depend on JQuery
 */
var __bind=function(a,b){return function(){return a.apply(b,arguments)}},__slice=[].slice;!function(a,b){var c;return c=function(){function c(c,d){this.mousedown=__bind(this.mousedown,this);var e=this;this.options=a.extend({},this.defaults,d),this.$table=c,this.tableId=this.$table.data("resizable-columns-id"),this.createHandles(),this.restoreColumnWidths(),this.syncHandleWidths(),a(b).on("resize.rc",function(){return e.syncHandleWidths()})}return c.prototype.defaults={store:b.store,rigidSizing:!1},c.prototype.destroy=function(){return this.$handleContainer.remove(),this.$table.removeData("resizableColumns"),a(b).off(".rc")},c.prototype.createHandles=function(){var b=this;return this.$table.before(this.$handleContainer=a("<div class='rc-handle-container' />")),this.$table.find("tr th").each(function(c,d){var e;if(0!==b.$table.find("tr th").eq(c+1).length&&null==b.$table.find("tr th").eq(c).attr("data-noresize")&&null==b.$table.find("tr th").eq(c+1).attr("data-noresize"))return e=a("<div class='rc-handle' />"),e.data("th",a(d)),e.appendTo(b.$handleContainer)}),this.$handleContainer.on("mousedown",".rc-handle",this.mousedown)},c.prototype.syncHandleWidths=function(){var b=this;return this.$handleContainer.width(this.$table.width()),this.$handleContainer.find(".rc-handle").each(function(c,d){return a(d).css({left:a(d).data("th").outerWidth()+(a(d).data("th").offset().left-b.$handleContainer.offset().left),height:b.$table.height()})})},c.prototype.saveColumnWidths=function(){var b=this;return this.$table.find("tr th").each(function(c,d){var e;return null==a(d).attr("data-noresize")&&(e=b.tableId+"-"+a(d).data("resizable-column-id"),null!=b.options.store)?store.set(e,a(d).width()):void 0})},c.prototype.restoreColumnWidths=function(){var b=this;return this.$table.find("tr th").each(function(c,d){var e,f;return e=b.tableId+"-"+a(d).data("resizable-column-id"),null!=b.options.store&&(f=store.get(e))?a(d).width(f):void 0})},c.prototype.mousedown=function(b){var c,d,e,f,g,h,i=this;return b.preventDefault(),this.startPosition=b.pageX,c=a(b.currentTarget),d=c.data("th"),g=d.width(),f=this.$table.find("tr th").index(c.data("th")),e=this.$table.find("tr th").eq(f+1),h=e.width(),a(document).on("mousemove.rc",function(a){var b,c,f;return b=a.pageX-i.startPosition,f=h-b,c=g+b,i.options.rigidSizing&&parseInt(e[0].style.width)<e.width()&&f<e.width()||parseInt(d[0].style.width)<d.width()&&c<d.width()?void 0:(d.width(c),e.width(f),i.syncHandleWidths())}),a(document).one("mouseup",function(){return a(document).off("mousemove.rc"),i.saveColumnWidths()})},c}(),a.fn.extend({resizableColumns:function(){var b,d;return d=arguments[0],b=2<=arguments.length?__slice.call(arguments,1):[],this.each(function(){var e,f;return e=a(this),f=e.data("resizableColumns"),f||e.data("resizableColumns",f=new c(e,d)),"string"==typeof d?f[d].apply(f,b):void 0})}})}(window.jQuery,window);
var SimpleTable = function(formId,pageNumber,pageSize,sortColumns,pageNumberKey,pageSizeKey,sortColumnsKey) {
	this.form = formId;
	this.pageNumber = pageNumber;
	this.pageSize = pageSize;
	this.sortColumns = sortColumns;
	this.pageNumberKey = pageNumberKey || 'pageNumber';
	this.pageSizeKey = pageSizeKey || 'pageSize';
	this.sortColumnsKey = sortColumnsKey || 'sortColumns';
	
	//handle sort
	_this = this;
	$("th[sortColumn]").click(function() {
		//handle click sort header
		var column = $(this).attr('sortColumn');
		if(SimpleTableUtils.getSortDirection(sortColumns,column) == 'asc') {
			_this.toggleSort("");
		}else if(SimpleTableUtils.getSortDirection(sortColumns,column) == 'desc') {
			_this.toggleSort(column + " asc");
		}else {
			_this.toggleSort(column + " desc");
		}
	}).mouseover(function() {
		$(this).toggleClass('sorting',true);
	}).mouseout(function() {
		$(this).toggleClass('sorting',false);
	});
	
	// add 'desc' or 'asc' class to sorted tableHeader
	var sortInfos = SimpleTableUtils.getSortInfos(sortColumns);
	for(var i = 0; i < sortInfos.length; i++) {
		var info = sortInfos[i];
		var selector ='th[sortColumn="'+info.column+'"]';
		var order = info.order ? info.order : 'asc';
		if($(selector+" .newsarrBox").length>0){
            $(selector+" .newsarrBox").html("<span class='newsarr "+(order=="asc"? "arrUp" : "arrDown")+"'></span>");
		}else{
            $(selector).addClass("row-hover");
            $(selector).toggleClass("sorting_"+(order=="asc"? "asc" : "desc"),true);
		}
	}
	
	//handle highlight on mouseover
	$("#"+formId+" .gridBody tbody tr").mouseover(function() {
		$(this).toggleClass('highlight',true);
	}).mouseout(function() {
		$(this).toggleClass('highlight',false);
	});
	$("#"+formId+" .gridBody tbody tr").click(function() {
		$("#"+formId+" .gridBody tbody tr").toggleClass('selectedhighlight',false);
		$(this).toggleClass('selectedhighlight');
	});
	$("#"+formId+" .gridBody").resizableColumns({ });
};
SimpleTable.prototype = {
	doJump : function(pageNumber,pageSize,sortColumns) {
		//alert("pageNumber:"+pageNumber+" pageSize:"+pageSize+" sortColumns:"+sortColumns+" this.form:"+this.form);
		var pair = function(k,v) {return ' <input type="hidden" name="'+k+'" value="'+v+'" />'};
		var params = pair(this.pageNumberKey,this.pageNumber)+pair(this.pageSizeKey,this.pageSize)+pair(this.sortColumnsKey,this.sortColumns)
		$('#'+this.form).append(params);
		SimpleTableUtils.fireSubmit(this.form);
	},
	togglePage : function(pageNumber) {
		this.pageNumber = (pageNumber==''?1:pageNumber);
		this.doJump(pageNumber,null,null);
	},
	togglePageSize : function(pageSize) {
		this.pageSize = pageSize;
		this.doJump(null,pageSize,null);
	},
	toggleSort : function(sortColumns) {
		this.sortColumns = sortColumns;
		this.doJump(null,null,sortColumns);
	}
};

// static methods
var SimpleTableUtils = {
	getSortInfos : function(sortColumns) {
		if(!sortColumns) return []; 
		var results = [];
		var sorts = sortColumns.split(",");
		for(var i = 0; i < sorts.length; i++) {
			var columnAndOrder = sorts[i].split(/\s+/);
			var column = columnAndOrder[0];
			var order = columnAndOrder.length > 1 ? columnAndOrder[1] : null;
			
			var sortInfo = new Object();
			sortInfo.column = $.trim(column);
			sortInfo.order = $.trim(order);
			
			results.push(sortInfo);
		}
		return results;
	},
	getSortDirection : function(defaultSortColumns,currentColumn) {
		var infos = SimpleTableUtils.getSortInfos(defaultSortColumns);
		for(var i = 0; i < infos.length; i++) {
			var info = infos[i];
			var order = info.order ? info.order : 'asc';
			if(info.column == currentColumn) {
				return order;
			}
		}
		return null;
	},
	fireSubmit : function(form) {
		var form = document.getElementById(form);
	    if (form.fireEvent) { //for ie
	    	if(form.fireEvent('onsubmit')){
	    		form.submit();
	    	}
	    } else if (document.createEvent) { // for dom level 2
			var evt = document.createEvent("HTMLEvents");
	      	//true for can bubble, true for cancelable
	      	evt.initEvent('submit', false, true); 
	      	form.dispatchEvent(evt);
	      	if(navigator.userAgent.indexOf('Chrome') >= 0) {
	      		form.submit();
	      	}
	    }
	}
}
$(function() {
	//详细数据相关操作
	var tdIndex;
	$("#table-list thead").delegate("th", "mouseover", function(){
		if($(this).find("i").hasClass("")) {
			$("#table-list thead th").each(function() {
				if($(this).find("i").hasClass("fa-sort")) $(this).find("i").attr("class", "");
			});
			$("#table-list thead th").eq($(this).index()).find("i").addClass("icon-sort");
		}
	});
	$("#table-list thead th").click(function() {
		if($(this).find("i").length>0) {
			var a = $(this).find("i");
			if(a.hasClass("icon-sort") || a.hasClass("icon-caret-up")) { //递减排序
				/*
					数据处理代码位置
				*/
				$("#table-list thead th i").attr("class", "");
				a.addClass("icon-caret-down");
			} else if(a.hasClass("icon-caret-down")) { //递增排序
				/*
					数据处理代码位置
				*/
				$("#table-list thead th i").attr("class", "");
				a.addClass("icon-caret-up");
			}
			$("#table-list thead th,#table-list tbody:eq(0) td").removeClass("row-hover");
			$(this).addClass("row-hover");
			tdIndex = $(this).index();
			$("#table-list tbody:eq(0) tr").each(function() {
				$(this).find("td").eq(tdIndex).addClass("row-hover");
			});
		}
	});
});