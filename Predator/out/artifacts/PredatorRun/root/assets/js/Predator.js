/**
 * Crisp Cookies 1.5
 * Copyright(C)Bean Corporation (Tyrant Group)
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Code by Tyrant Group
 * ;) Hope you enjoy this
 */
var Predator = {
    config:{
        "WizardParameter"  : "do",
        "ControlParameter" : "control",
        "ModelParameter"   : "action",
        "SystemWizard"     : "SystemCoven"
    },

    pageDate:{},

    initialize:function () {

    },

    getWizard:function(){
        return $_GET[ Predator.config.WizardParameter ]
    },

    getAction:function(){
        return $_GET[ Predator.config.ModelParameter ]
    },

    getControl:function(){
        return $_GET[ Predator.config.ControlParameter ]
    },

    spawnActionQuerySpell: function( szActionFunctionName ){
        var szQueryString = "?" + this.config.WizardParameter + "=" + this.getWizard();
        if( szActionFunctionName ){
            return szQueryString + "&" + this.config.ModelParameter + "=" + szActionFunctionName;
        }
        return szQueryString;
    },

    spawnControlQuerySpell: function( szControlFunctionName ){
        var szQueryString = "?" + this.config.WizardParameter + "=" + this.getWizard();
        if( szControlFunctionName ){
            return szQueryString + "&" + this.config.ControlParameter + "=" + szControlFunctionName;
        }
        return szQueryString;
    },

    spawnSystemActonQuerySpell: function( szActionFunctionName ){
        var szQueryString = "?" + this.config.WizardParameter + "=" + this.config["SystemWizard"];
        if( szActionFunctionName ){
            return szQueryString + "&" + this.config.ModelParameter + "=" + szActionFunctionName;
        }
        return szQueryString;
    },

    spawnSystemQuerySpell: function( szControlFunctionName ){
        var szQueryString = "?" + this.config.WizardParameter + "=" + this.config["SystemWizard"];
        if( szControlFunctionName ){
            return szQueryString + "&" + this.config.ControlParameter + "=" + szControlFunctionName;
        }
        return szQueryString;
    },



    showStaticPageIndex:function(uiID,cPageID,sumOfPage, limit){
        var pageCount = Math.ceil(sumOfPage / limit) ;
        var href = ( window.location.href.toString().indexOf('?') < 0 )? window.location.href.toString() + "?do=null":
            pPine.WebControl.urlAutoMerge({pageid:null},$_GET);
        cPageID = pPine.isThenSet(parseInt(cPageID),1);

        var pageNum = cPageID,totalPage = pageCount, startPage = 1, endPage = 5;

        if(totalPage <=5){
            endPage = totalPage;
        }else{
            startPage = pageNum - 2;
            endPage = pageNum + 2;

            if(startPage <= 0){
                startPage = 1;
                endPage = 5;
            }
            if(endPage > totalPage){
                endPage = totalPage;
                startPage = endPage - 4;
            }
        }

        var pageFirst = '', pageLast = '', pagePrevious = '',pageNext = '',indexkey = '';

        var pageInformation = '<li><a>第'+ (sumOfPage > 0 ? cPageID : 0) + '—' + pageCount + '页，共' + sumOfPage + '条</a></li>';
        if(pageNum <= 1){
            pageFirst = '<li><a href="javascript:;" style="cursor:not-allowed;">首页</a></li>';
            pagePrevious = '<li><a href="javascript:;" style="cursor:not-allowed;">«</a></li>';
            if(totalPage <= 1){
                pageLast = '<li><a href="javascript:;" style="cursor:not-allowed;">尾页</a></li>';
                pageNext = '<li><a href="javascript:;" style="cursor:not-allowed;">»</a></li>';
            } else {
                pageLast = '<li><a href="' + href + '&pageid=' + totalPage +'">尾页</a></li>';
                pageNext = '<li><a href="' + href + '&pageid=2">»</a></li>';
            }
        } else if(pageNum > 1 && pageNum < totalPage){
            pageFirst = '<li><a href="' + href + '&pageid=1">首页</a></li>';
            pagePrevious = '<li><a href="' + href + '&pageid=' + (pageNum-1) +'">«</a></li>';
            pageLast = '<li><a href="' + href + '&pageid=' + totalPage +'">尾页</a></li>';
            pageNext = '<li><a href="' + href + '&pageid=' + (pageNum+1) +'">»</a></li>';
        } else if(pageNum === totalPage){
            if(totalPage === 1){
                pageFirst = '<li><a href="javascript:;" style="cursor:not-allowed;">首页</a></li>';
                pagePrevious = '<li><a href="javascript:;" style="cursor:not-allowed;">«</a></li>';
            } else {
                pageFirst = '<li><a href="' + href + '&pageid=1">首页</a></li>';
                pagePrevious = '<li><a href="' + href + '&pageid=' + (totalPage-1) +'">«</a></li>';
            }
            pageLast = '<li><a href="javascript:;" style="cursor:not-allowed;">尾页</a></li>';
            pageNext = '<li><a href="javascript:;" style="cursor:not-allowed;">»</a></li>';
        }
        for(var i = startPage; i <= endPage; i++){
            if(i === pageNum){
                indexkey += '<li class="paginate_button active"><a href="javascript:;" style="cursor:not-allowed;">' + i + '</a></li>';
            } else {
                indexkey += '<li><a href="' + href + '&pageid=' + i + '">' + i + '</a></li>';
            }
        }
        $_PINE(uiID).append(pageInformation + pageFirst + pagePrevious + indexkey + pageNext + pageLast);
    },

    warnCommonDialog:function (uiID, title, content, url, btnTitle) {
        btnTitle = btnTitle?btnTitle:"确定";
        return '<div class="modal fade crisp-union-win" id="' + uiID + '" tabindex="-1" role="dialog" aria-labelledby="crisp-WarnCommonLabel" aria-hidden="true" style="margin-top: 6%;text-align: left">\n' +
            '    <div class="modal-dialog">\n' +
            '        <div class="modal-content">\n' +
            '            <div class="modal-header">\n' +
            '                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>\n' +
            '                <h4 class="modal-title" id="crisp-WarnCommonLabel">' + title + '</h4>\n' +
            '            </div>\n' +
            '            <div class="modal-body">' + content + '</div>\n' +
            '            <div class="modal-footer">\n' +
            '                <a href="' + url + '"><button class="btn btn-danger">'+btnTitle+'</button></a>\n' +
            '                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>\n' +
            '            </div>\n' +
            '        </div>\n' +
            '    </div>\n' +
            '</div>';
    },


    simpleCommonDialog:function (uiID, title, content,btnTitle){
        btnTitle = btnTitle?btnTitle:"关闭";
        return '<div class="modal fade crisp-union-win" id="' + uiID + '" tabindex="-1" role="dialog" aria-labelledby="crispSimpleModalLabel" aria-hidden="true" style="margin-top: 6%;">\n' +
            '    <div class="modal-dialog">\n' +
            '        <div class="modal-content">\n' +
            '            <div class="modal-header">\n' +
            '                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>\n' +
            '                <h4 class="modal-title" id="myModalLabel">' + title + '</h4>\n' +
            '            </div>\n' +
            '            <div class="modal-body">\n' + content + '</div>\n' +
            '            <div class="modal-footer">\n' +
            '                <button type="button" class="btn btn-default" data-dismiss="modal">'+btnTitle+'</button>\n' +
            '            </div>\n' +
            '        </div>\n' +
            '    </div>\n' +
            '</div>';
    },

    renderTable:function (tableStream,keyArray) {
        var table = "";
        if(tableStream){
            for(var key in keyArray){
                if(keyArray.hasOwnProperty(key) && tableStream.hasOwnProperty(keyArray[key])){
                    table += "<td>" + tableStream[keyArray[key]] + "</td>";
                }else {
                    table += "<td>&nbsp;</td>";
                }
            }
        }
        return table;
    },

    spawnDefaultPieChart:function (at,height,tuple) {
        var hCanvasHandle = $(at +" canvas");
        if (hCanvasHandle.length) {
            try{
               $(at).css("height", height + "px");
                var chartData = [], colors = [], labels = [];
                for(var i=0; i<tuple.length; i++){
                    labels.push(tuple[i][0]);
                    chartData.push(tuple[i][1]);
                    colors.push(tuple[i][2]);
                }
                new Chart(
                    hCanvasHandle[0].getContext("2d"),
                    {type: "pie", data: {datasets: [{data: chartData, backgroundColor: colors}], labels: labels},
                        options: {
                            responsive: true, maintainAspectRatio: false, layout: {padding: {left: 10, right: 10, top: 10, bottom: 10}}, legend: {position: "right"}
                        }
                    }
                );
            }catch (e) {}
        }
    },

    spawnDefaultBarChart:function (at,title,width_threshold,tuple) {
        var hHandle = $(at);
        if (hHandle.length) {
            try{
                var chartData = [], colors = [], labels = [];
                for(var i=0; i<tuple.length; i++){
                    labels.push(tuple[i][0]);
                    chartData.push(tuple[i][1]);
                    colors.push(tuple[i][2]);
                }
                new Chart(
                    hHandle[0].getContext("2d"),
                    {
                        type: "horizontalBar", data: {labels: labels, datasets: [{label: title, data: chartData, backgroundColor: colors, borderWidth: 0}]},
                        options: {
                            responsive: true, maintainAspectRatio: $(window).width() >= width_threshold,
                            scales: {
                                yAxes: [{
                                    barPercentage: 0.2, ticks: {beginAtZero: false},
                                    scaleLabel: {display: true, labelString:title}}]
                            }
                        }
                    }
                );
            }catch (e) {}
        }
    },

    spawnDefaultLineChart:function (at,labels,tuple) {
        var hCanvasHandle = $(at);
        if (hCanvasHandle.length) {
            var dataSets = [];
            if(tuple){
                for(var i=0;i<tuple.length;i++){
                    dataSets.push({
                        label: tuple[i][0],
                        data: tuple[i][1],
                        fill: false,
                        borderColor: tuple[i][2],
                        cubicInterpolationMode: "monotone",
                        pointRadius: 0
                    })
                }
            }
            new Chart(
                hCanvasHandle[0].getContext("2d"),
                {type: "line", data: {labels: labels, datasets: dataSets},
                    options: {scales: {yAxes: [{scaleLabel: {display: true, labelString: "Hits"}}]}, maintainAspectRatio : $(window).width() >= 480}
                }
            );
        }
    },

    appendTableWhileThereAreData:function (at,dataStream) {
        if(dataStream){
            $_PINE(at).myHtml(dataStream);
        }
    },

    massDeleteListener: function (at,control,data) {
        $(at).on('click', function () {
            var check = $("input[type=checkbox][class!=allcheck]:checked");
            if (check.length < 1) {
                alert('请选择要删除的记录!');
                return false;
            }
            if (confirm("您确认要删除这些记录吗?")) {
                var ids = [];
                check.each(function (i) {ids[i] = $(this).val();});
                data = data?(function (){var t = {};t[data] = ids;return t;})():{id: ids};
                $_PINE.ajax({
                    type: "POST",
                    url: control,
                    dataType: "text", data: data,
                    success: function (data) {
                        if( data === "true" ){
                            alert('删除成功！');
                            location.reload();
                        }
                        else {
                            alert('删除失败，请联系管理员！');
                        }
                    }
                });
            }
        });
    },

    smartMassDeleteListener: function ( hfnControlName, data ){
        Predator.massDeleteListener( "#" + hfnControlName, Predator.spawnControlQuerySpell( hfnControlName ), data  );
    },

    checkAll: function( that, selector ) {
        var checked = $(that).get(0).checked;
        $( selector ? selector + " ":"" + "input[type=checkbox]").prop("checked", checked);
    },

    renderLaydate:function (arr) {
        for(var row in arr){
            var k = Object.keys(arr[row])[0];
            laydate.render({elem:k , type: arr[row][k]});
        }
    },

    quickRenderLaydate:function (arr,type) {
        for(var row in arr){
            laydate.render({elem:arr[row] , type: (type?type:"datetime")});
        }
    },

    analysisWordLevels: function( wLevel , isIcon) {
    var res = "";
    if( wLevel ){
        if( !Predator.pageDate["MainWordLevel"] ){
            $.ajax({
                url:   Predator.spawnSystemActonQuerySpell("getMainWordLevels"),
                async:    false,
                type:     "GET",
                dataType: "json",
                data:     {"invoker": Predator.getWizard()},
                success: function (result) {
                    Predator.pageDate["MainWordLevel"] = result;
                },
                error: function (result) {
                    return res;
                }
            });
        }
        var hMainWordLevel = Predator.pageDate["MainWordLevel"];
        wLevel = wLevel instanceof Array ? wLevel : JSON.parse( wLevel );
        var TypeArr = ["label-primary","label-success","label-info","label-warning"];
        if(isIcon==true){
            for( var i = 0; i<wLevel.length; i++ ){
                var row = wLevel[i];
                res += '<span class="label '+TypeArr[i%4]+'">'+hMainWordLevel[row]+'</span>';
                if( i !== wLevel.length-1 ){
                    res += ' ';
                }
            }
        }
        else{
            for( var i = 0; i<wLevel.length; i++ ){
                var row = wLevel[i];
                res +=hMainWordLevel[row];
                if( i !== wLevel.length-1 ){
                    res += ',';
                }
            }
        }
    }
    return res;
},

    analysisGlossaryType: function( gType ) {
        var res = "";
        if( gType ){
            if( !Predator.pageDate["GlossaryType"] ){
                $.ajax({
                    url:   Predator.spawnSystemActonQuerySpell("getGlossaryType"),
                    async:    false,
                    type:     "GET",
                    dataType: "json",
                    data:     {"invoker": Predator.getWizard()},
                    success: function (result) {
                        Predator.pageDate["GlossaryType"] = result;
                    },
                    error: function (result) {
                        return res;
                    }
                });
            }

            var hGlossaryType = Predator.pageDate["GlossaryType"];
                res += hGlossaryType[gType];
        }
        return res;
    },
    analysisStatue:function(status){
        var res="";
        switch (status) {
            case "0":res+= "公开";break;
            case "1":res+= "私有";break;
        }
        return res;
    },
    
    parseSystemAuthority :function ( t ) {
        var r;
        return (r = { "0":"普通用户", "3":"管理员", "4":"超级管理员" }[t])? r : "普通用户";
    },
    
    Setcookie :function (key,value) {
        document.cookie = key + "=" + value + ";"
    },

    getCookie :function(cname){
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for(var i=0; i<ca.length; i++) {
        var c = ca[i].trim();
        if (c.indexOf(name)==0) return c.substring(name.length,c.length);
    }
    return null;
   },
    analysisChineseMean:function (szChinese) {
        var szChineseMean = '';
        for(var name in szChinese[0]){
            if(szChinese[0].hasOwnProperty(name)){
              var arr = name.split("_")
                szChineseMean += '<p>' + '<strong>'+arr[1]+'. </strong>'+szChinese[0][name]+'</p>';
            }
        }
        return szChineseMean;
    },

    analysisProgressbar:function (total,now) {
        total *= 1.0;
        now *= 1.0;
         var progress = (now/total)*100;
         progress = progress.toFixed(2)-0;
        return progress+"%";
    },

};

var $PD = function ( hPageData, pfnRenderer, bAutoDispatch ) {
    this.defun = function () {
        this.mhPageData   = hPageData;
        this.my           = Predator;
        this.my.pageDate  = this.mhPageData;
        this.config       = this.my.config;
        this.proto        = this;
        this.mbDispatch   = bAutoDispatch === undefined ? true : !!bAutoDispatch ;

        this.renderer   = function () {
            (pfnRenderer instanceof Function ? pfnRenderer : function () {})();
            return this.proto;
        };

        this.init       = function () {
            $_PINE("#currentTitle").myText( this.mhPageData["szMainTitle"] );
            return this.proto;
        };

        this.setConfig  = function ( config ) {
            Predator.config = config;
            return this.proto;
        };

        this.setDispatch = function (b) {
            this.mbDispatch = b;
            return this.proto;
        };

        this.beforeDispatch = function () {
            return this.proto;
        };

        this.afterDispatch = function () {
            return this.proto;
        };

        this.dispatch   = function () {
            this.beforeDispatch();
            this.init();
            this.renderer();
            this.afterDispatch();
            return this.proto;
        };

        if( this.mbDispatch ){
            this.dispatch();
        }

        return this.proto;
    };

    return new this.defun();
};

function setTagMultipleStyle(obj,css) {
    for(var attr in css){
        obj.style[attr] = css[attr];
    }
}


var PineconeInputValueCheck = {
    valueChecked:false,

    checkResult : function () {
        return this.valueChecked ;
    },

    checkInputValue : function (obj){
        if(obj){
            if(obj.value!==""){
                setTagMultipleStyle(obj,{"border-color":"rgba(75,191,15,0.87)", "box-shadow":"0 0 5px rgb(75,191,15"});
                this.valueChecked = true;
            }else {
                setTagMultipleStyle(obj,{"border-color":"rgba(255, 17, 17, 0.87)", "box-shadow":"0 0 5px #ef100a"});
                this.valueChecked =  false;
            }
        }
    }
};

$('.dropdown-menu').click(function(e) {
    e.stopPropagation();
});

$(".box-down").click(
    function (e) {
        var handle = $(e.target).parents(".crisp-my-box");
        var box_content_handle = $(handle).children(".box-content");
        if(box_content_handle.css("display") === "block"){
            box_content_handle.hide();
        }else{
            box_content_handle.show();
        }
    }
);