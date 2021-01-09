<%@ page import="Predator.Util.PredatorHelper" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ page isELIgnored="false" %>

${StaticHead}
<script>
    var uHeight = $(window).width();
    if(uHeight<500){
        uHeight = 250;
    }else {
        uHeight = 380;
    }
    function drawImage(hotimg) {
        $(hotimg).jqthumb({
            classname      : 'jqthumb',
            width          : '100%',
            height         : uHeight,
            position       : { y: '50%', x: '50%'},
            zoom           : '1',
            method         : 'auto',
        });
    }
</script>
<div class="content-wrapper">
    <div class="container">
        <div class="row pad-botm">
            <div class="col-md-6">
                <h4 class="header-line">
                    <a href="/"><i class="fa fa-home"></i> 首页</a> >> <label id="currentTitle">个人中心</label> >> <label id="pageNodeTitle">单词本</label>
                </h4>
            </div>
            <div class="col-md-6" style="text-align: right">
                <h4 class="header-line">
                    <i class="fa fa-book"></i>最近访问单词本： <a id="recentlyWordBuildHref" href="" style="text-align: right"><label id="recentlyWordBuild"></label></a>
                </h4>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-2">
                <div class="row pad-botm">
                    <div class="col-md-12">
                        <h4 class="header-line" style="font-size: 110%"><i class="fa fa-list"></i><a id="showButtonControl">单词本中心菜单</a></h4>
                    </div>
                </div>
                <div id="ButtonsControl" class="crisp-union-box">
                    <div class="row">
                        <div class="col-sm-12">
                            <div class="crisp-super-btn red-background super-btn-add-gap">
                                <a href="<%=PredatorHelper.mySoul(request).spawnActionQuerySpell()%>">
                                    <div class="header">
                                        <i class="fa fa-book"></i>
                                    </div>
                                    <div id="WordBuildMainCss" class="content" style="font-size: 140%">单词本</div>
                                </a>
                            </div>
                        </div>
                        <div class="col-sm-12">
                            <div class="crisp-super-btn orange-background super-btn-add-gap">
                                <a href="#">
                                    <div class="header">
                                        <i class="fa fa-database"></i>
                                    </div>
                                    <div id="addApplyCss" class="content" style="font-size: 140%">单词本分析</div>
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-sm-10" id="mutualUsersWordBuildList" style="display:block;">
                <div>
                    <div class="row pad-botm">
                        <div class="col-md-6">
                            <h4 class="header-line" style="font-size: 110%"><i class="fa fa-trophy"></i><span> 我的单词本</span></h4>
                        </div>
                            <a href="<%=PredatorHelper.mySoul(request).spawnActionQuerySpell("appendNewWordBuild")%>">
                                <div class="col-md-6" style="text-align: right">
                                    <h4 class="header-line" style="font-size: 100%" ><i class="fa fa-plus"></i><span> 添加单词本</span>
                                    </h4>
                                </div>
                            </a>
                    </div>

                    <div>
                        <div class="panel panel-default" style="border-radius: 0;">
                            <div class="panel-body">
                                <div class="row">
                                    <div class="col-sm-9">
                                        <div class="form-group com-group-control-search">
                                            <label style="width: 25%">单词本本名称</label>
                                            <input id="g_nameSearch" class="form-control"  type='text' style="width: 52%;display:inline;" onchange="screeningGlossaryName(this)"/>
                                            <a style="float: right;width: 21%;border-radius: 0;" href="#" class="btn btn-default" onclick="screeningGlossaryName(this)">查找</a>
                                        </div>
                                    </div>
                                    <div class="col-sm-3">
                                        <div class="form-group com-group-control-search">
                                            <label style="width: 39%">状态</label>
                                            <select id="stateMainSelect" class="form-control" style="width: 60%;display:inline;" onchange="screeningState(this)">
                                                <option value="">未选择</option>
                                                <option value="0">公开</option>
                                                <option value="1">私有</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-9">
                                        <div class='form-group com-group-control-date'>
                                            <label class="main-label" style="width: 25%">创建时间: </label>
                                            <input id='create_dataSelect' type='text' value="" class="form-control"
                                                   placeholder="请输入时间" style="width: 52%;margin-left: 7px"
                                                   onchange="getCreateDateSearch()" required/>
                                            <a style="float: right;width: 21%;border-radius: 0;" href="#" class="btn btn-default" onclick="getCreateDateSearch()">查找</a>
                                        </div>
                                    </div>
                                </div>
                                <div class="row" style="text-align: center;">
                                    <a  class="btn btn-primary btn-sharp" onclick="clearAll()">清除筛选</a>
                                </div>
                            </div>
                        </div>

                        <div id="GlossaryTable">
                        </div>



                        <div class="col-sm-12 crisp-ui-fault-tolerant-A">
                            <ul class="pagination" id="paginationApplyManageMain"></ul>
                        </div>
                    </div>

                    <div class="crisp-my-box" id="TargetTable" style="display: none">
                        <div class="panel panel-default" style="border-radius: 0;margin-bottom: 0">
                            <div class="panel-body">
                                <div class="crisp-my-profile">
                                    <div class="col-md-12" style="text-align: center">
                                        <label style="color: red;font-size: 20px" id="state">快来创建一个单词本吧！</label>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-sm-10" id="appendNewWordBuild" style="display:none;">
                <div class="col-md-12">
                    <div class="panel panel-default" style="border-radius:0">
                        <form name="appendNewWordBuild" id="#" method="post" action="<%=PredatorHelper.mySoul(request).spawnControlQuerySpell("appendNewWordBuild")%>">
                            <div class="panel-body">
                                <div class="tab-content">
                                    <div class="form-group com-group-control-relate">
                                        <label style="width: 20%">单词本名称: </label>
                                        <input class="form-control" name="g_name" id="g_name" type="text" placeholder="请输入单词本名称" style="width: 80%;display:inline" required>
                                    </div>
                                    <div class="form-group com-group-control-relate">
                                        <label style="height: 150px;width:20%;text-align: center">备注: </label>
                                        <textarea id="g_note" name="g_note" class="form-control" style="width: 80%;height: 150px;"></textarea>
                                    </div>

                                    <div class="row">
                                        <div class="col-md-12">
                                            <div class="form-group com-group-control-relate">
                                                <label style="width: 20%">状态: </label>
                                                <select id="g_authority" name="g_authority" class="form-control" style="width: 80%">
                                                    <option value="0">公开</option>
                                                    <option value="1">私有</option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-footer" style="text-align: right">
                                <button id="sendbtn" class='btn btn-primary' type='submit'>
                                    <i class='fa fa-save'></i> 添加
                                </button>
                                <a href="<%=PredatorHelper.mySoul(request).spawnActionQuerySpell()%>">
                                    <button class='btn btn-default' type="button"><i class='fa fa-reply'></i>返回</button>
                                </a>
                            </div>
                        </form>
                    </div>
                </div>
            </div>

            <div id="showWordBuildContent" class="col-sm-10">
                <div class="row pad-botm">
                    <div class="col-md-6">
                        <h4 class="header-line" style="font-size: 110%"><i class="fa fa-trophy"></i><span id="WordBuildName"></span></h4>
                    </div>
                    <a href="?do=PersonWordBuild&action=appendNewWord#appendNewWord" data-toggle="modal">
                        <div class="col-md-6" style="text-align: right">
                            <h4 class="header-line" style="font-size: 100%" ><i class="fa fa-plus"></i><span> 添加单词</span>
                            </h4>
                        </div>
                    </a>
                </div>

              <div class="modal fade" id="appendNewWord" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="margin-top: 6%">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 class="modal-title" id="myModalLabel">添加单词</h4>
                            </div>
                        <form name="appendNewWord" id="appendNewWordFrom" method="POST" action="?do=PersonWordBuild&control=appendNewWord">
                        </form>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-sm-5">
                        <div class="form-group com-group-control-search">
                            <label for="filterSortType">排序: </label>
                            <select id="filterSortType" class="form-control" name="authority" onchange="screeningSortType( this )">
                                <option value="">无</option>
                                <option value="0">随机排序</option>
                                <option value="1">字典排序</option>
                                <option value="2">单词长度排序</option>
                            </select>
                            <a href="?do=PersonWordBuild#saveSortType" class="btn btn-primary btn btn-default" data-toggle="modal"> 应用 </a>
                        </div>
                        <div id="SaveWarning"></div>
                    </div>
                    <div class="col-sm-6">
                        <div class="form-group com-group-control-search">
                            <label >单词搜索: </label>
                            <input class="form-control" id="searchText" name="searchText" type="text"
                                   placeholder="输入英文单词" maxlength="25" onchange="searchEnWord( this )" required/>
                            <a onclick="searchEnWord( this )" class="btn btn-default">查找</a>
                        </div>
                    </div>
                </div>

                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover crisp-picture-table">
                        <thead>
                        <tr>
                            <th>
                                <input type="checkbox" onclick="Predator.checkAll(this)" style="width: 20%">全选
                            </th>
                            <th>英文单词</th>
                            <th>单词释义</th>
                            <th>单词等级</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody><tr><td colspan="8">没有添加单词！</td></tr></tbody>
                    </table>
                </div>

                <div class="col-sm-12 crisp-margin-ui-fault-tolerant-2">
                    <div class="crisp-paginate">
                        <ul class="pagination"></ul>
                    </div>
                </div>
            </div>

            <div class="col-md-10" id="WordBuildAnalysis">
                <div id="learnIndex" style="display: none">
                    <div class="row pad-botm">
                        <div class="col-md-12">
                            <h4 class="header-line" style="font-size: 110%"><i class="fa fa-pie-chart"></i> <span>升学概况</span></h4>
                        </div>
                    </div>

                    <div class="row box-content">
                        <div class="col-sm-6">
                            <div id="pieChartContainerGraduateSum" style="border: 1px solid #428bca;text-align: center">
                                <canvas class="chartjs-render-monitor" width="200" height="200">
                                </canvas>
                            </div>
                        </div>
                        <div class="col-sm-6">
                            <div class="table-responsive">
                                <table class="table table-hover table-bordered">
                                    <tbody>
                                    <tr>
                                        <td>毕业生总数：</td>
                                        <td>
                                            <span id="collageSum">1278</span>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>就业人数：</td>
                                        <td>
                                            <span id="registerSum">1121</span>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>创业人数：</td>
                                        <td>
                                            <span id="inReadSum">21</span>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>考研人数：</td>
                                        <td>
                                            <span id="inReadSum">53</span>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>就业率：</td>
                                        <td>
                                            <span id="inReadSum">88.3%</span>
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <div class="col-sm-6" style="text-align: center">
                            <span>考研率变化</span>
                            <div style="border: 1px solid #428bca;text-align: center">
                                <canvas id="employRate"></canvas>
                            </div>
                        </div>
                        <div class="col-sm-6" style="text-align: center">
                            <span>学校类型</span>
                            <div id="pieChartContainerJobType" style="border: 1px solid #428bca;text-align: center">
                                <canvas class="chartjs-render-monitor" width="200" height="200">
                                </canvas>
                            </div>
                        </div>
                        <div class="col-md-12" style="margin-top: 2%;text-align: center">
                            <span>就升学情况分析</span>
                            <div class="" style="border: 1px solid #428bca;">
                                <canvas id="learnSituationBarChart"></canvas>
                            </div>
                        </div>
                    </div>
                </div>
        </div>
    </div>
</div>
${StaticFooter}
    <script src="/root/assets/js/jquery.js"></script>
    <script src="/root/assets/js/bootstrap.js"></script>
    <script src="/root/assets/js/pinecone.js"></script>
    <script src="/root/assets/js/Predator.js"></script>
    <script>
         if($("#U_id_name").text()==""){
             window.location.href = "?do=UserLogin";
         }
        data:{
            var pageData = ${szPageData};
            var UserWordBuildList = pageData['UserWordBuildList'];
        }
        utl:{
            var mainURL = '?do=PersonWordBuild';
            var szDeleteGlossaryURL = Predator.spawnControlQuerySpell( "deleteOneWordBuild" );
            var szModifyGlossaryURL = Predator.spawnControlQuerySpell( "modifyOneWordBuild" );
            var szShowWordBuildContentURL = Predator.spawnActionQuerySpell("showWordBuildContent");
            var szDeleteOneWordURL = Predator.spawnControlQuerySpell( "deleteOneWord" );
            var szAppendOneWordURL = Predator.spawnControlQuerySpell( "appendNewWord" );
            var szSaveSortTypeURL    = Predator.spawnControlQuerySpell( "saveSortId" );
        }
        $PD( pageData, function(){
            $_PR({
                "mutualUsersWordBuildList": function ( parent ) {
                    $_PINE('#WordBuildMainCss').myCSS({"text-decoration":"underline","font-weight":"bold"});

                    if(!pageData['UserWordBuildList']){
                        $_PINE('#TargetTable').myCSS('display','block');
                        return 0;
                    }
                    var UserWordBuildListZone = '';

                    for (var i = 0; i < UserWordBuildList.length; i++) {
                        var row = UserWordBuildList[i];

                        UserWordBuildListZone += '<div class="panel panel-hr">\n' +
                            '                            <div class="row">\n' +
                            '                                <div class="col-md-12 crisp-job-list-box">\n' +
                            '                                    <div class="head-info">\n' +
                            '                                        <a class="fa fa-sign-in" href="'+szShowWordBuildContentURL+'&class_id='+row['class_id']+'"><strong>'+row['g_name']+'</strong></a>\n' +
                            '                                        <span>创建时间：'+row['create_data']+'</span>\n' +
                            '                                    </div>\n' +
                            '                                    <hr><ul>\n' +
                            '                                        <li class="fa fa-facebook-official">备注：'+row['g_note']+' </li>\n' +
                            '                                        <li class="fa fa-street-view" style="float: right;font-size: 15px">状态:'+Predator.analysisStatue(row['g_authority'])+'</li>\n' +
                            '                                    </ul>\n' +
                            '                                    <p>\n'+
                            '                                        <a class="btn btn-primary btn-sharp" style="float: right;margin-left: 1%" href="'+mainURL+"&action=applyModifyPage&modifyId="+row['id']+'"><i class="fa fa-edit"></i></a>\n' +
                            '                                        <a class="btn btn-primary btn-sharp" style="float: right;margin-left: 1%" data-toggle="modal" href="' + Predator.spawnActionQuerySpell() + '#modalDeleteOne' + row['class_id'] + '"><i class="fa fa-trash-o"></i></a>\n'+
                            Predator.warnCommonDialog(
                                'modalDeleteOne' + row['class_id'], "Warning",
                                '<h4>Are you sure to delete the word build "'+ row['g_name'] + '"?</h4>',
                                szDeleteGlossaryURL + '&class_id=' + row['class_id'],"Confirm"
                            ) +
                            '                                    </p>\n' +
                            '                                </div>\n' +
                            '                            </div>\n' +
                            '                        </div>' ;
                    }
                    Predator.appendTableWhileThereAreData( "#GlossaryTable",UserWordBuildListZone );
                },

                "appendNewWordBuild": function () {
                },

                "appendNewWord": function () {
                    $_PINE("#en_word").myValue(pageData["WordBuildName"][0]['class_id'])
                },

                "showWordBuildContent": function ( parent ) {
                    var hWordsList = pageData["WordBuildContent"];
                    var hWordBuildInformation = pageData["WordBuildName"];
                    var hSortType = pageData["szSortType"];
                    var hSortEnWord = pageData["szSortEnWord"];
                    var szTableZone = '';
                    var szAppendZone= '';
                    $_PINE("#filterSortType").myValue(hSortType);
                    $_PINE("#searchText").myValue(hSortEnWord);
                    Predator.Setcookie("g_name",hWordBuildInformation[0]['g_name']);
                    Predator.Setcookie("class_id",hWordBuildInformation[0]['class_id']);
                    for( var i=0; i < hWordsList.length; i++ ){
                        var row = hWordsList[i];
                        var uid = "modalDeleteOne"+row['id'];
                        szTableZone += '<tr>'+
                            '<tr>\n' +
                            '<td><input type="checkbox" name="massDelete" value="'+row['id']+'"  style="width: 30%"><span>选择</span></td>' +
                            '<td class="wrapper"><a href="?do=SearchWord&KeyWord='+row['en_word']+'">'+row['en_word']+'<div class="tooltip"><img src="/root/assets/img/userImg.jpg"></br>'+
                            '英文：'+row['en_word']+'</br>中文：'+row['cn_simple_mean']+'</br>' +
                            '</div></a></td>'+
                            Predator.renderTable( row,['cn_simple_mean'] ) +"<td>"+ Predator.analysisWordLevels( row["w_level"] )+"</td>"+ '<td>' +
                            '   <a class="" data-toggle="modal" href="'+ Predator.spawnActionQuerySpell() + '#modalDeleteOne'+row['id']+'" style="">\n' +
                            '       <i class="fa fa-trash-o fa-lg" style="margin-left: 2px;margin-right: 2px;"></i>\n' +
                            '   </a>\n' +
                            Predator.warnCommonDialog(
                                'modalDeleteOne' + row['id'], "Warning",
                                '<h4>你确定要删除单词: "'+ row['en_word'] + '"?</h4>',
                                szDeleteOneWordURL + '&en_word=' + row['en_word']+'&class_id='+hWordBuildInformation[0]['class_id'],"Confirm"
                            ) +
                            '</td></tr>'
                    }
                    {
                        szAppendZone += '<input id="class_id" name="class_id" type="text" value="' + hWordBuildInformation[0]['class_id'] + '" hidden/>\n' +
                            '<div class="modal-body">\n' +
                            '<div class="form-group com-group-control">\n' +
                            '<label>英语单词: </label>\n' +
                            '<input id="en_word" name="en_word" class="form-control" type="text" value="" placeholder="添加的单词" required>\n' +
                            '</div>\n</div>\n' +
                            '<div class="modal-footer">\n' +
                            '<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>\n' +
                            '<input type="submit" class="btn btn-primary" style="width: 15%" value="添加">\n'
                    }

                    Predator.appendTableWhileThereAreData("#appendNewWordFrom",szAppendZone);
                    Predator.appendTableWhileThereAreData( "#SaveWarning",Predator.warnCommonDialog('saveSortType',"Warning",'<h4>确 定 要 应 用 该 排 序 吗?<h4>',szSaveSortTypeURL+"&action=showWordBuildContent&class_id="+hWordBuildInformation[0]['class_id']+ '&sort_type='+hSortType,"Confirm"));
                    $_PINE("#WordBuildName").myText(hWordBuildInformation[0]['g_name']);
                    Predator.appendTableWhileThereAreData( parent.spawnSubSelector("tbody"),szTableZone );
                }

            }).summon(Predator.getAction());
            $("#recentlyWordBuildHref").attr("href",szShowWordBuildContentURL+"&class_id="+Predator.getCookie("class_id"));
            $_PINE("#recentlyWordBuild").myText(Predator.getCookie("g_name"));
        });
        filter:{
            function getCreateDateSearch() {
                var mergeData = {"create_data":null};
                if ($('#create_dataSelect').val()) {
                    mergeData["create_data"] = $('#create_dataSelect').val();
                }
                window.location = Pinecone.WebControl.urlAutoMerge(mergeData,$_GET);
            }

            function screeningGlossaryName(handle) {
                var vs = $(handle).val(), mergeData = {"g_name":null};
                if (vs) {
                    mergeData["g_name"] = vs;
                }
                window.location.href = Pinecone.WebControl.urlAutoMerge(mergeData,$_GET);
            }

            function screeningState(handle) {
                var vs = $(handle).val(), mergeData = {"g_authority":null};
                if (vs) {
                    mergeData["g_authority"] = vs;
                }
                window.location.href = Pinecone.WebControl.urlAutoMerge(mergeData,$_GET);
            }

            function screeningSortType( handle ) {
                var vs = $(handle).val(), mergeData = {"sort_type":null};
                if (vs) {
                    mergeData["sort_type"] = vs;
                }
                window.location.href = Pinecone.WebControl.urlAutoMerge(mergeData,$_GET);
            }

            function clearAll() {
                window.location.href = mainURL;
            }
            function searchEnWord( handle ) {
                var vs = $(handle).val(),mergeData = {"en_word":null};
                if(vs) {
                    mergeData["en_word"] = vs;
                }
                window.location.href = Pinecone.WebControl.urlAutoMerge(mergeData,$_GET);
            }

        }

                $(document).ready(function(){
                    $("#showButtonControl").click(function () {
                        $("#ButtonsControl").slideToggle("slow");
                    });
                });
    </script>
${StaticPageEnd}