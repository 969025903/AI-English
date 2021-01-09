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
<style>
    #StartStudy{
        -webkit-border-radius: 50px;

        -moz-border-radius: 50px;

        border-radius: 50px;
    }
</style>
<div  class="container" style="position: relative" id="window">
    <div>
        <div>
            <div>
                <div id="ReciteWordCenter">
                    <div>
                        <ul id="CenterMenu" class="nav nav-tabs">
                            <li class="active">
                                <a href="#WordStudy" data-toggle="tab"><label>单词学习</label></a>
                            </li>
                            <li>
                                <a href="#WordBookSelect" data-toggle="tab"><label>单词书</label></a>
                            </li>
                            <li>
                                <a href="#Setting" data-toggle="tab"><label>设置</label></a>
                            </li>
                        </ul>
                    </div>
                    <div id="myTabContent" class="tab-content">
                        <div class="tab-pane fade in active" id = "WordStudy">
                            <div class="jumbotron" style="height: 80%;">
                                <div class="panel panel-default">
                                    <div class="panel-body" style="height: 600px">
                                        <div>
                                            <h3 style="float: left;color: #333333">
                                                <label>我已经学习单词&nbsp;</label><label id="SumDays" style="font-size: 150%">0</label><label>&nbsp;天</label>
                                            </h3>
                                        </div>
                                        <div style="margin-top: 10%">
                                            <div class="well" style="background-color: #e1edf7;width: 100%;height: 300px" id="PlanTableShow">

                                            </div>
                                            <div style="position: relative;margin-top: 10%;font-style:italic;font-weight: lighter ">
                                                <blockquote class="pull-right">
                                                    Life isn't always what one likes.
                                                    <small style="font-weight: lighter">奥黛丽.赫本 <cite title="Source Title">《罗马假日》</cite></small>
                                                </blockquote>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="tab-pane fade" id="WordBookSelect">
                            <div class="panel panel-default">
                                <div class="panel-body">
                                    <div class="row pad-botm">
                                        <div class="col-md-12">
                                            <h4 class="header-line">
                                                <i class="fa fa-book" style="margin-left: 2%"></i><label id="username" style="color: coral;font-weight: lighter"></label><label id="pageNodeTitle" style="color: coral;font-weight: lighter"> 的词汇本</label>
                                            </h4>
                                        </div>
                                    </div>
                                    <div class="row" id="MyBookShow">

                                    </div>
                                    <hr/>
                                    <div class="row pad-botm">
                                        <div class="col-md-12">
                                            <h4 class="header-line">
                                                <i class="fa fa-book"></i><label style="color: coral;font-weight: lighter">所有词汇书</label>
                                            </h4>
                                        </div>
                                    </div>
                                    <div class="row" id="WordBookShow">

                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="tab-pane fade" id = "Setting">
                            设置!!!!!!!!!!!!!!!!!!!!!!!!
                        </div>
                    </div>
                </div>

                <div class="panel panel-info" style="width: 60%;margin: auto; position: relative" id="ReciteWord">
                    <div class="panel-heading">
                        <li role="presentation" class="active"><a class="glyphicon glyphicon-home" href="?do=ReciteWord"></a></li>
                        <p>今日已学单词:<span id="studied"></span></p>
                        <p>今日需学单词:<span id="studying"></span></p>
                    </div>
                    <div class="panel-body" style="height: 700px;">
                        <div style="position: relative;top:10%;">
                            <div id="WordTable" style="text-align: center;">
                                <p><h3 class="fa fa-3x" id="WordShow"></h3></p>
                                <p style="color: #5e5e5e" id="WordPhonetic"></p>
                            </div>

                            <div class="row" style="position: relative;margin-top: 45%;" id="SelectShow" >
                            </div>
                        </div>
                    </div>
                    <div class="panel-heading" style="height: 100px">
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


url:{
var szMainURL = "?do=ReciteWord";
var szAppendNewBookURL = Predator.spawnControlQuerySpell("appendNewBook");
var szDeleteOneBookURL = Predator.spawnControlQuerySpell("deleteOneBook");
var szStudyThisBookURL = Predator.spawnControlQuerySpell("studyThisBook");
var szPlanThisBookURL = Predator.spawnControlQuerySpell("PlanThisBook");
var szReciteWordURL = Predator.spawnActionQuerySpell("ReciteWord");
}

data:{
var pageData = ${szPageData};
var hWordReciteList = pageData['ReciteWordList'];
var hWordBookSelect = pageData['WordBookSelect'];
var hUserPlan = pageData['UserPlan'];
var hTotalGlossaryWord = pageData['totalGlossaryWord'];
var hMyBookList = pageData['MyBookList'];
}

ReciteWord_Data:{
    var SelectArray = ['A','B','C','D'];
    var FakeChineseList = pageData['FakeReciteWordList'];
    var studied = 0;
    var studying = hUserPlan[0]['plan_word'];
    var index = 0;
    var worngTime = 0;
    var szRealChinese = '';
    var szRealEnglish = '';
}

Function :{
function juageBookState( szD_id , szPlan_data) {
if(hUserPlan !=""){
if(szD_id == hUserPlan[0]['d_id']){
return  '<p><a href="#" class="btn" style="background-color: coral;color: white" role="button">正在学习</a>\n';
}
if(szPlan_data != undefined){
return  '<p><a href="'+szStudyThisBookURL+"&d_id="+szD_id+'" class="btn btn-primary" role="button">背书</a>\n';
}
}
return  '<p><a href="'+szMainURL+"#BookPlan"+szD_id+'" class="btn btn-primary" role="button" data-toggle="modal">背书</a>\n';
}

function juageBookPlanDays( WordNum ,Word) {
return ((WordNum/Word)+1).toFixed(0);
}

function theOption(szD_Word) {
var szOptionZone = '';
var x=0,y=5;
for(var i=0;i<5;i++){
var feibo = x+y;
szOptionZone+= '<option value="'+feibo+'"><strong>'+feibo+' 单词 &nbsp;预计天数 "'+juageBookPlanDays(szD_Word,feibo)+'"<strong> </option>\n';
x=y;
y=feibo;
}
return szOptionZone;
}

function juageWordChinese( szButtonId ) {
    if ($(szButtonId).text() == szRealChinese) {
        NextWord();
    }
    else {
        $(szButtonId).css("color", "lightcoral");
        ++worngTime;
        if (worngTime >=2) {
            window.location.href ="?do=SearchWord&KeyWord=" + szRealEnglish;
        }
        winShake();
    }

}

function winShake(u){
        var a=['top','left'],b=0;
        u=setInterval(function(){
            document.getElementById('window').style[a[b%2]]=(b++)%4<2?0:4+"px";
            if(b>15){clearInterval(u);b=0}
        },32)
    }

function  NextWord() {
    worngTime = 0;
   ++index;
   --studying;
    ++studied;

    var row = hWordReciteList[index];
    $_PINE('#studied').myText(studied);
    $_PINE('#studying').myText(studying);
    $_PINE('#WordShow').myText(row['en_word']);
    $_PINE('#WordPhonetic').myHtml('英: '+'<span class="en-uk">['+row['us_phonetic_symbol'] +']  '+'<span class="glyphicon glyphicon-volume-up" id="en-us"></span>'+'</span>');
    $_PINE('#en-us').click(function () {
        var audio_url = 'https://dict.youdao.com/dictvoice?audio=' + row['en_word'] + '&type=' + 1;
        var audio = new Audio(audio_url);
        audio.play();
    });
    szRealChinese =  row['cn_simple_mean'];
    szRealEnglish = row['en_word'];
    var szButtonZone = massChineseButton();
    Predator.appendTableWhileThereAreData("#SelectShow",szButtonZone)
}

function massChineseButton(  ) {
    var arr1 = [];
    var ChineseList = [];
    var szMassButtonZone = '';
   for(var i=0;i<10;i++){
       var RandomNum = Math.floor(Math.random()*FakeChineseList.length);
       if(arr1.indexOf(RandomNum)==-1){
           ChineseList.push(FakeChineseList[RandomNum]['cn_simple_mean']);
           arr1.push(RandomNum);
       }
           if(ChineseList.length>2){
               break;
           }
   }
   ChineseList.push(szRealChinese);
   ChineseList.sort();
    for(var j=0;j<ChineseList.length;j++){
        var SectionId = "Section"+SelectArray[j];
        szMassButtonZone +=' <a>\n' +
            '                    <div class="col-md-6 col-sm-3 col-xs-6" >\n' +
            '                        <div class="alert alert-info back-widget-set text-center" style="solid:5px">\n' +
            '                            <h3 class="fa fa-2px" id="'+SectionId+'" onclick="juageWordChinese(this)">'+ChineseList[j]+'</h3>\n' +
            '                        </div>\n' +
            '                    </div>\n' +
            '                </a>\n'
    }
   return szMassButtonZone;
}

}
$PD( pageData, function(){
$_PR({
"ReciteWordCenter":function ( parent ) {

$_PINE("#username").myText($("#U_id_name").text());

WordBookSelect:{
var szWordBookSelectZone = '';
for(var i=0;i < hWordBookSelect.length ;i++){
    var row = hWordBookSelect[i];
    szWordBookSelectZone +=  '<div class="col-sm-6 col-md-3">\n'+
        '<div class="thumbnail">\n'+
        '<img src="'+row['imageurl']+'" alt="通用的占位符缩略图">\n'+
        '<div class="caption">\n'+
        '<h3>'+row['d_name']+'</h3>\n'+
        '<p>词汇总量：<span id="CET4_WordSum">'+row['d_word_sum']+'</span></p>\n'+
        '<p><a href="'+szAppendNewBookURL+"&d_id="+row['d_id']+'" class="btn btn-primary" role="button">添加</a>\n'+
        '<a href="#" class="btn btn-default" role="button">按钮 </a>\n'+
        '</p>\n'+
        '</div> </div> </div>\n'
}
}

MyBookShow:{
var szMyBookShow = '';
if(hMyBookList == ""){
    szMyBookShow +='<div class="col-sm-6 col-md-3">\n'+
        '<div class="thumbnail">\n'+
        '<img src="/root/assets/img/nonebook.jpg" alt="通用的占位符缩略图">\n'+
        '<div class="caption">\n'+
        '<h3>没有添加书籍!</h3>\n'+
        '</div> </div> </div>\n'
}
else{
    for(var i=0;i<hMyBookList.length;i++ ){
        var bow = hMyBookList[i];
        szMyBookShow +='<div class="col-sm-6 col-md-3">\n'+
            '<div class="thumbnail">\n'+
            '<img src="'+bow['imageurl']+'" alt="通用的占位符缩略图">\n'+
            '<div class="caption">\n'+
            '<h3>'+bow['d_name']+'</h3>\n'+
            juageBookState(bow['d_id'],bow['plan_word'])+
            '<a href="'+Predator.spawnActionQuerySpell()+'#deleteOneBook'+bow['d_id']+'" data-toggle="modal" class="btn btn-default" role="button">删除</a>\n'+
            '</p>\n'+
            '</div> </div> </div>\n'+
            Predator.warnCommonDialog(
                'deleteOneBook' + bow['d_id'], "Warning",
                '<h4>你确定你要删除该词汇书吗"'+ bow['d_name'] + '"?</h4>',
                szDeleteOneBookURL + '&d_id=' + bow['d_id'],"确定"
            )+
            '<div class="modal fade" id="BookPlan'+bow['d_id']+'" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">\n' +
            '<div class="modal-dialog">\n' +
            '<div class="modal-content">\n' +
            '<div class="modal-header">\n' +
            '<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>\n' +
            '<h4 class="modal-title" id="myModalLabel">计划制定</h4>\n' +
            '</div>\n' +
            '<div class="modal-body">\n' +
            '<h3 style="font-weight: lighter"><i class="fa fa-book"></i>词汇书总词量：<strong> '+bow['d_word_sum']+'</strong><\h3> \n'+
            '<form method="POST" name="BookStudyPlan" action="'+szPlanThisBookURL+'&d_id='+bow['d_id']+'" onsubmit="return PineconeInputValueCheck.checkResult()">\n' +
            '<label for="name">选择每日任务</label>\n' +
            '    <select multiple class="form-control" name="Day_Word" id="Day_Word" onblur="PineconeInputValueCheck.checkInputValue(this)">\n' +
            theOption(bow['d_word_sum']) +
            '    </select>' +
            '<div class="modal-footer">\n' +
            '<button type="submit" class="btn btn-info" style="width:100px;" ><i class="fa fa-bookmark"></i> 开始背书</button>\n' +
            '</form>' +
            '</div></div></div></div></div>';
    }
}
}

WordStudy:{
var szWordStudyZone='';
if(hUserPlan == ""){
    szWordStudyZone+= '<div style="float: right;margin-right: 58%">\n'+
        '<h3><label>暂无书籍</label></h3>\n'+
        '<h3><span class="label label-success">预定完成时间：</span></h3><h3>无</h3>\n'+
        '</div>\n'+
        '<div class="col-sm-6 col-md-3">\n'+
        '<div class="thumbnail">\n'+
        '<img src="/root/assets/img/nonebook.jpg" alt="通用的占位符缩略图">\n'+
        '<div class="caption" style="text-align: center">\n'+
        '<p></p>\n'+
        '<p> <a href="#WordBookSelect" data-toggle="tab" class="btn btn-primary" role="button">添加书籍</a></p>\n'+
        '</div></div></div><div>\n' +
        '<div style="margin-top: 25%;">\n'+
        '<label style="margin-left:30%;color:coral"><span >已完成</span><span>&nbsp;0%</span></label>\n'+
        '<div class="progress progress-striped active" style="width: 40%">\n'+
        '<div class="progress-bar progress-bar-warning" role="progressbar"aria-valuenow="60" aria-valuemin="0" aria-valuemax="100"style="width:0%;" id="StudyProgress">\n'+
        '</div></div> </div> </div> </div> \n' +
        '<div>\n' +
        '<br/>'+
        '<a href="#" class="btn" id="StartStudy" style="margin-left: 38%;background-color: #63cb95;width: 270px;text-align: center;" disabled="disabled">\n'+
        '<h2 style="color: whitesmoke">开始学习</h2>\n'+
        '</a> </div>\n'
}
else{
    for(var i =0 ;i<hWordBookSelect.length;i++){
        if(hWordBookSelect[i]['d_id'] == hUserPlan[0]['d_id']){
            var row = hWordBookSelect[i];
        }
    }
    szWordStudyZone+= '<div style="float: right;margin-right: 54%">\n'+
        '<h3><label>'+row['d_name']+'</label></h3>\n'+
        '<h3><span class="label label-success">预定完成时间：</span></h3><h3>'+juageBookPlanDays(row['d_word_sum'],hUserPlan[0]['plan_word'])+'天</h3>\n' +
        '<div style="float:left">\n' +
        '<h5><span style="color:#EEEEEE">--今日任务--</span></h5>\n' +
        '<h3><strong>'+row['plan_word']+ '<strong><h3>\n'  +
        '</div>'+
        '</div>\n'+
        '<div class="col-sm-6 col-md-3" style="margin-bottom: 80%">\n'+
        '<div class="thumbnail">\n'+
        '<img src="'+row['imageurl']+'" alt="通用的占位符缩略图">\n'+
        '<div class="caption" style="text-align: center">\n'+
        '<p><a href="#" data-toggle="tab" class="btn" role="button" style="background-color: coral;color: whitesmoke;">单词列表</a></p>\n'+
        '</div></div></div></div>\n' +
        '<div style="margin-top: 23%;">\n ' +
        '<span style="color:coral">'+hUserPlan['0']['total_recited']+"/"+row['d_word_sum']+'词<span >'+
        '<label style="margin-left:27%;color:coral">已完成</span><span>&nbsp;'+Predator.analysisProgressbar(row['d_word_sum'],hUserPlan[0]['total_recited'])+'</span></label>\n'+
        '<div class="progress progress-striped active" style="width: 40%">\n'+
        '<div class="progress-bar progress-bar-warning" role="progressbar"aria-valuenow="60" aria-valuemin="0" aria-valuemax="100"' +
        'style="width:'+Predator.analysisProgressbar(row['d_word_sum'],hUserPlan[0]['total_recited'])+';" id="StudyProgress">\n'+
        '</div></div> </div> </div> </div> \n'+
        '<div>\n' +
        '<br/>'+
        '<a href="?do=ReciteWord&action=ReciteWord&d_id='+row['d_id']+'" class="btn" id="StartStudy" style="margin-left: 10%;background-color: #63cb95;width: 270px;text-align: center;">\n'+
        '<h2 style="color: white">开始学习</h2>\n'+
        '</a> </div>\n'
}
}

Predator.appendTableWhileThereAreData("#MyBookShow",szMyBookShow);
Predator.appendTableWhileThereAreData("#PlanTableShow",szWordStudyZone);
Predator.appendTableWhileThereAreData("#WordBookShow",szWordBookSelectZone);

},

"ReciteWord":function ( parent ) {
    var szButtonZone = '';
        var row = hWordReciteList[index];
        $_PINE('#studied').myText(studied);
        $_PINE('#studying').myText(studying);
        $_PINE('#WordShow').myText(row['en_word']);
        $_PINE('#WordPhonetic').myHtml('英: '+'<span class="en-uk">['+row['us_phonetic_symbol'] +']  '+'<span class="glyphicon glyphicon-volume-up" id="en-us"></span>'+'</span>');
        $_PINE('#en-us').click(function () {
            var audio_url = 'https://dict.youdao.com/dictvoice?audio=' + row['en_word'] + '&type=' + 1;
            var audio = new Audio(audio_url);
            audio.play();
        });
        szRealEnglish = row['en_word'];
        szRealChinese = row['cn_simple_mean'];
        var szButtonZone = massChineseButton();
    Predator.appendTableWhileThereAreData("#SelectShow",szButtonZone);
}

}).summon(Predator.getAction());
});

</script>
${StaticPageEnd}