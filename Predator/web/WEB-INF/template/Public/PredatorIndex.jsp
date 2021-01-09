<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page contentType="text/html;" pageEncoding="utf-8"%>
${StaticHead}

<script src="/root/assets/js/jquery.js"></script>
<script src="/root/assets/js/jqthumb.js"></script>
<script>
    var uHeight = $(window).width();
    if(uHeight<500){
        uHeight = 250;
    }else {
        uHeight = 450;
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
        <div class="row">
            <div class="col-md-12 col-sm-12 col-xs-12" style="margin-top:-2%;margin-bottom: 2%;">
                <div  id="carousel-main-body" class="carousel slide slide-bdr-index-main" data-ride="carousel">
                    <div class="carousel-inner" id="carousel-main">
                    </div>
                    <a class="left carousel-control" href="#carousel-main-body" data-slide="prev">
                        <span class="glyphicon glyphicon-chevron-left"></span>
                    </a>
                    <a class="right carousel-control" href="#carousel-main-body" data-slide="next">
                        <span class="glyphicon glyphicon-chevron-right"></span>
                    </a>
                </div>
            </div>

        </div>

        <div class="row">
            <div class="pad-botm" style="margin-bottom: -2%;">
                <div class="col-md-12">
                    <h4 class="header-line">
                        <i class="fa fa-cube"></i>
                        <span>常用功能</span>
                    </h4>
                </div>
            </div>
            <a href="/PredatorIndex">
                <div class="col-md-3 col-sm-3 col-xs-6">
                    <div class="alert alert-success back-widget-set text-center">
                        <i class="fa fa-user-circle-o fa-5x"></i>
                        <h3>首页</h3>
                    </div>
                </div>
            </a>
            <a href="?do=NewsBrowse">
                <div class="col-md-3 col-sm-3 col-xs-6">
                    <div class="alert alert-info back-widget-set text-center">
                        <i class="fa fa-bookmark fa-5x"></i>
                        <h3>咨询文章</h3>
                    </div>
                </div>
            </a>
            <a href="">
                <div class="col-md-3 col-sm-3 col-xs-6">
                    <div class="alert alert-danger back-widget-set text-center">
                        <i class="fa fa-book fa-5x"></i>
                        <h3>个人单词本</h3>
                    </div>
                </div>
            </a>
            <a href="">
                <div class="col-md-3 col-sm-3 col-xs-6">
                    <div class="alert alert-warning back-widget-set text-center">
                        <i class="fa fa-space-shuttle fa-5x"></i>
                        <h3>待开发...</h3>
                    </div>
                </div>
            </a>
        </div>

        <div class="row">
            <div class="pad-botm" style="margin-bottom: -2%;">
                <div class="col-md-12">
                    <h4 class="header-line">
                        <i class="fa fa-newspaper-o"></i>
                        <span>平台新闻</span>
                    </h4>
                </div>
            </div>
            <div class="crisp-my-box" id = "news" style="margin-top: 6%">
            </div>
            <ul class="pagination" id = "newsPageList"></ul>
        </div>


    </div>
</div>
${StaticFooter}
<script src="/root/assets/js/bootstrap.js"></script>
<script src="/root/assets/js/pinecone.js"></script>
<script src="/root/assets/js/Predator.js"></script>
<script>
    var pageData = ${szPageData};

    (function carouselMainShow() {
        var carouselMain = '<div class="item active">' +
            '<img src="'+pageData['carouselMain'][0]+'" alt="" onload="drawImage(this)"/>' +
            '</div>';

        if(pageData['carouselMain'].length > 1){
            for(var i=1; i<pageData['carouselMain'].length; i++){
                carouselMain += '<div class="item">' +
                    '<img src="'+pageData['carouselMain'][i]+'" alt="" onload="drawImage(this)"/>' +
                    '</div>';
            }
        }

        $_PINE('#carousel-main').append(carouselMain);
    })();

    (function showNewsList() {
        var dataStream = pageData['tableData'];
        var tableZone = '';

        for (var i = 0; i < dataStream.length; i++) {
            tableZone += '<div class="panel panel-hr">\n' +
                '                        <div class="panel-body">\n' +
                '                        <div class="crisp-news-box">\n' +
                '                        <div class="col-md-2">\n' +
                '                        <a href="/lambdaPageView'+'?classid='+dataStream[i]['classid']+'&classname='+pageData['SelfClassName']+'">' +
                '                        <img class="img-responsive" src="' + dataStream[i]['mainimage'] + '" alt="" onerror="this.src=\'./root/root/System/img/nopic.jpg\'; this.title=\'图片未找到 !\'"></a>\n' +
                '                        </div>\n' +
                '                        <div class="col-md-10 crisp-my-profile">\n' +
                '                        <p style="margin-left: 0;font-size: 22px;margin-bottom: -1%"><a href="/lambdaPageView'+'?classid='+dataStream[i]['classid']+'&classname='+pageData['SelfClassName']+'"><strong>' + dataStream[i]['title'] + '</strong></a></p>\n' +
                '                    <hr/>\n' +
                '                    <p style="margin-top: -1%"><strong> 发布日期：</strong><span >' + dataStream[i]['posttime'] + '</span></p>\n' +
                '                    <p style="margin-top: -1%">' + pPine.String.hypertext2Text(dataStream[i]['content'],true,true).substr(0,150)  + '...</p>\n' +
                '                    </div>\n' +
                '                    </div>\n' +
                '                    </div>\n' +
                '                    </div> ';
        }
        $_PINE('#news').append(tableZone);
    })();


    Predator.showStaticPageIndex('#newsPageList',pPine.isThenSet($_GET["pageid"],1),
        pageData['sumOfPage'],pageData['pageLimit']);

</script>
${StaticPageEnd}