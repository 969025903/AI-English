<%@ page import="Predator.Util.PredatorHelper" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ page isELIgnored="false" %>
<style>
    strong
    {
        font-size: 25px;
        color: black;
        letter-spacing: 0px;
    }
    #yd-word-meaning
    {
        font-size: 15px;
        letter-spacing: 3px;
        color: dimgray;
        font-family:Georgia;
    }
    #yd-word
    {
        font-size: 40px;
    }
</style>
${StaticHead}


<div>
    <div class="content-wrapper" id="Search" style="width: 70%;margin: auto">
        <div class="col-sm-8" id="yd-content">
            <div class="row">
                <div class="col-md-7" id="yd-word-info">
                    <div class="row"><h3 id="yd-word" ></h3></div>
                    <div class="row" id="yd-word-pron" ></div>
                    <div class="row" id="yd-word-meaning"></div>
                    <div class="row" style="margin-bottom:6px;" id="WordLevel"></div>
                    <hr/>
                    <div class="row">
                        <h5><label>使用频率：</label>
                            <span class="glyphicon glyphicon-star"></span>
                            <span class="glyphicon glyphicon-star"></span>
                        </h5>

                        <h5><label>星级词汇：</label>
                            <span class="glyphicon glyphicon-star"></span>
                            <span class="glyphicon glyphicon-star"></span>
                            <span class="glyphicon glyphicon-star"></span>
                            <span class="glyphicon glyphicon-star"></span>
                        </h5>
                    </div>
                </div>
                <div class="col-md-5" id="yd-word-img">
                    <div class="thumbnail">
                        <img style="width: 300px; height: 200px;" src="../../../pinecone/assets/avatar.png" alt="soar" class="center-block" id="word-image">
                        <div id="yd-img-nav">
                            <div class="yd-img-left">&laquo;</div>
                            <span><span id="yd-img-current">1</span> / 10</span>
                            <div class="yd-img-right">&raquo;</div>
                        </div>
                    </div>
                </div>

            </div>
            <div class="page-header"><h3>记忆方法</h3></div>
            <div class="row" style="font-family:SimSun,serif;"></div>
            <div class="page-header"><h3>中文词源</h3></div>
            <div class="row" id="yd-ciyuan" style="font-family:SimSun,serif;"></div>
            <div class="page-header"><h3>英语词源</h3></div>
            <div class="row"><div class="thumbnail" id="yd-etym"></div></div>
            <div class="page-header"><h3>权威例句</h3></div>
            <div class="row" id="yd-liju">
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
    var pageData = ${szPageData};
    var szWord = pageData['SearchWord'];
    var szChinese = pageData['SearchChinese'];
    // console.log(Predator.getAction());
    // $_PINE("#yd-word").myText(szWord[0]['en_word']);
    $PD(pageData, function () {
        $_PR({
            "Search": function () {
                $_PINE("#yd-word").myText(szWord[0]['en_word']);
                $_PINE("#yd-word-pron").myHtml( '英: '+'<span class="en-uk">['+szWord[0]['us_phonetic_symbol'] +']  '+'<span class="glyphicon glyphicon-volume-up" id="en-us"></span>'+'</span>');
                var mean=Predator.analysisChineseMean(szChinese);
                $_PINE("#yd-word-meaning").myHtml(mean);
                $_PINE('#en-us').click(function () {
                    var audio_url = 'https://dict.youdao.com/dictvoice?audio=' + szWord[0]['en_word'] + '&type=' + 1;
                    var audio = new Audio(audio_url);
                    audio.play();
                });
                $_PINE("#WordLevel").myHtml(Predator.analysisWordLevels(szWord[0]['w_level'],true));
            }


        }).summon(Predator.getAction());
    });



</script>
