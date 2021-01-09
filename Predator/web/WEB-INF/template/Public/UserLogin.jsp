<%@ page import="Predator.Util.PredatorHelper" %>
<%@ page import="Predator.System.Predator" %>
<%@ page import="Predator.Wizard.Public.UserLogin.UserLogin" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ page isELIgnored="false" %>

${StaticHead}
<link href="/assets/js/plugins/dataTables/dataTables.bootstrap.css" rel="stylesheet"/>

<style>
    .headBar{
        width: 100%;
        height: 50px;
        line-height: 50px;
        border-bottom: 1px solid #e6e6e6;
    }
    .headBar ul li {
        width: 33%;
        /*min-width: 270px;*/
        height: 50px;
        font-size: 19px;
        float: left;
        font-family:\5FAE\8F6F\96C5\9ED1;
        text-align: center;
        color: #b4b4b4;
        margin-right: -2%;
        list-style-type: decimal;
        list-style-position: inside;
    }
    .curr{
        color: #428bca !important;
        border-bottom: 2px solid #428bca;
    }
    .con {
        font-family:\5FAE\8F6F\96C5\9ED1;
        /*width: 100%;*/
        min-width: 250px;
        padding: 20px;
        line-height: 30px;
        color: #979797;
        font-size: 19px;
    }

    @media screen and (max-device-width:480px) {
        .headBar ul li {
            width: 85%;
        }
        img.vcode {
            width: 60px;
            height: 40px;
            margin-top: 7px;
            margin-right: 13%;
            margin-left: 0;
        }
        input.input_box_box{
            margin-left: 5px;
            width: 85%;
        }
        input.input_box_box#vcode{
            width: 58%;
        }
        td{
            font-size: 14px;
        }
        td.left_td{
            font-size: 12px;
            width: 100px;
            padding: 0;
        }

    }
    .vcode{
        width: 20%;  height: 80%;float: right;margin-right: 26%;margin-left: -10%; margin-top:1.10%;
    }
    input.input_box_box#vcode{
        width: 49%;
    }
    .input_box_box {
        margin: 10px 0 10px 20px  ;
        width: 70%;
        /*width: 100px;*/
        /*min-width: 200px;*/
        /*height: 35px;*/
        line-height: 35px;
        color: #666;
        font-size: 14px;
        padding: 0px 10px;
        font-family: "microsoft yahei";
        border: 1px solid #ccc;
    }
    .sub_button{
        color: white;
        background: #428bca;
        margin-top: 10px;
    }

</style>

<div class="content-wrapper" >
    <div class="container" >
        <div class="row pad-botm">
            <div class="col-md-12">
                <h4 class="header-line">
                    <a href="/"><i class="fa fa-home"></i> 首页</a> >> <label id="currentTitle"></label>
                </h4>
            </div>
        </div>

        <div class="row">
            <div class="col-md-12">
                <div id="VomitZone">
                    <div class="headBar">
                        <ul>
                            <li class="curr" style="margin-left: 15%">用户登录</li>
                            <li><a href="?do=UserLogin&action=UserRegister">用户注册</a></li>
                        </ul>
                    </div>
                    <div class="con">
                        <form id="reg_form" action="?do=UserLogin&control=formCheck" method="post" onsubmit="return PineconeInputValueCheck.checkResult()">
                            <table width="65%" border="0" cellspacing="0" cellpadding="10" align="center" style="min-width: 320px">
                                <tbody>
                                <tr>
                                    <td class="left_td" width="25%"  align="right">用户名：</td>
                                    <td width="75%"><i class="glyphicon glyphicon-user"></i><input type="text" class="input_box_box" id="username" name="username" onblur="PineconeInputValueCheck.checkInputValue(this)" required></td>
                                </tr>
                                <tr>
                                    <td class="left_td" width="25%" align="right">密码：</td>
                                    <td width="75%"><i class="glyphicon glyphicon-lock"></i><input type="text" class="input_box_box" id="password" name="password" onblur="PineconeInputValueCheck.checkInputValue(this)" required></td>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                            <div style="text-align: center">
                                <input style="width: 32% ;min-width: 270px;margin-left: 13px" type="submit" class="sub_button" value="登录">
                            </div>
                            <div style="text-align: center">
                                <span class="help-block" style="color:#fc5531;font-size:80%;" id="warningText"></span>
                            </div>
                        </form>
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
    var pageData = ${szPageData};
    $_PINE("#warningText").myText(pageData['WaringText']);

    $PD( pageData, function(){
        $_PR({
            "UserLogin":function () {
                $_PINE('#currentTitle').myHtml("登录");
            },
            "UserRegister":function () {
                $_PINE('#currentTitle').myHtml("注册");
                $_PINE('#VomitZone').myHtml('<div class="headBar">\n' +
                    '                        <ul >\n' +
                    '                            <li style="margin-left: 18%"><a href="?do=UserLogin&action=UserLogin">用户登录<a></li>\n' +
                    '                            <li class="curr">用户注册</li> \n' +
                    '                        </ul>\n' +
                    '                    </div>\n' +
                    '                    <div class="con">\n' +
                    '                        <form id="reg_form" action="?do=UserLogin&control=UserRegister" method="post" onsubmit="return PineconeInputValueCheck.checkResult()">\n' +
                    '                             <table width="65%" border="0" cellspacing="0" cellpadding="10" align="center" style="min-width: 320px">\n' +
                    '                                <tbody>\n' +
                    '                                <tr>\n' +
                    '                                    <td class="left_td" width="25%" align="right"> 昵称：</td>\n' +
                    '                                    <td width="75%"><input type="text" class="input_box_box" id="nickname" name="nickname" onblur="PineconeInputValueCheck.checkInputValue(this)" required></td>\n' +
                    '                                </tr>\n' +
                    '                                <tr>\n' +
                    '                                    <td class="left_td" width="25%" align="right"> 账号：</td>\n' +
                    '                                    <td width="75%"><input type="text" class="input_box_box" id="username" name="username" onblur="PineconeInputValueCheck.checkInputValue(this)" required></td>\n' +
                    '                                </tr>\n' +
                    '                                <tr>\n' +
                    '                                    <td class="left_td" width="25%" align="right"> 设置密码：</td>\n' +
                    '                                    <td width="75%"><input type="text" class="input_box_box" id="first_password" name="first_password"  required ></td>\n' +
                    '                                </tr>\n' +
                    '                                <tr>\n' +
                    '                                    <td class="left_td" width="25%" align="right"> 确认密码：</td>\n' +
                    '                                    <td width="75%"><input type="text" class="input_box_box"  id="second_password" name="new_password"  required></td>\n' +
                    '                                </tr>\n' +
                    '                                <tr>\n' +
                    '                                </tbody></table>\n' +
                    ' <div style="text-align: center"> <input style="width: 32% ;min-width: 270px;margin-left: 13px" type="submit" class="sub_button" value="注册"></div>' +
                    '<div style="text-align: center">\n'+
                    '<span class="help-block" style="color:#fc5531;font-size:80%;" id="warningText"></span>\n'+
                    '</div>\n'+
                    '                        </form>\n' +
                    '                    </div>');

                document.getElementById('first_password').onblur = function (){
                    PineconeInputValueCheck.checkInputValue(document.getElementById('first_password'));
                    if($_PINE('#first_password').myValue() && $_PINE('#second_password').myValue()){
                        if($_PINE('#first_password').myValue() !== $_PINE('#second_password').myValue()) {
                            $_PINE('#warningText').myText("两次密码不一致！");
                            PineconeInputValueCheck.valueChecked = false;
                        } else if(!(new RegExp(/^(?=.*?[a-z)(?=.*>[A-Z])(?=.*?[0-9])[a-zA_Z0-9]{6,15}$/)).test($_PINE("#second_password").myValue())){
                            $_PINE('#warningText').myText("密码应包含字母和数字！且密码不能少于6位！");
                            PineconeInputValueCheck.valueChecked = false;
                        } else {
                            $_PINE('#warningText').myText("");
                            PineconeInputValueCheck.valueChecked = true;
                        }
                    }
                };

                document.getElementById('second_password').onblur = function (){
                    PineconeInputValueCheck.checkInputValue(document.getElementById('second_password'));
                    if($_PINE('#first_password').myValue() && $_PINE('#second_password').myValue()){
                        if($_PINE('#first_password').myValue() !== $_PINE('#second_password').myValue()) {
                            $_PINE('#warningText').myText("两次密码不一致！")
                            PineconeInputValueCheck.valueChecked =  false;
                        } else if(!(new RegExp(/^(?=.*?[a-z)(?=.*>[A-Z])(?=.*?[0-9])[a-zA_Z0-9]{6,15}$/)).test($_PINE("#first_password").myValue())){
                            $_PINE('#warningText').myText("密码应包含字母和数字！且密码不能少于6位！");
                            PineconeInputValueCheck.valueChecked = false;
                        } else {
                            $_PINE('#warningText').myText("");
                            PineconeInputValueCheck.valueChecked = true;
                        }
                    }
                };
            }
        }).summon(Predator.getAction());
       });

</script>
</script>
${StaticPageEnd}