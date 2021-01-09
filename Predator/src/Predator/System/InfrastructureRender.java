package Predator.System;

import Pinecone.Framework.Util.Net.Illumination.prototype.Wizard;
import Predator.Wizard.Public.UserLogin.UserLogin;
import Predator.Wizard.Public.UserLogin.UserLoginControl;
import com.mysql.fabric.Response;
import org.omg.CORBA.Request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InfrastructureRender{
    private PredatorWizardSoul mhSoul  = null;

    public InfrastructureRender( PredatorWizardSoul hSoul ) {
        this.mhSoul  = hSoul;
    }


    public String includeMainHeadPart(String szLoginWho){
        String szMenu = this.mhSoul.parent().menu().spawn();

        String szLoginHTML = "                <div class=\"right-div\">\n" ;

        String LoginWho = szLoginWho;

        if(LoginWho == null){
            szLoginHTML += "<a href=\"?do=UserLogin\" class=\"btn btn-primary pull-right\" style=\"padding: 7px 25px 7px\">登&nbsp;录</a>";
        }
        else {
            szLoginHTML += "<div class=\"btn-group\">\n" +
                    "                            <button data-toggle=\"dropdown\" class=\"btn btn-primary dropdown-toggle pull-right\" style=\"padding: 7px 22px 7px\" id=\"U_id_name\" value=\""+LoginWho+"\">"+
                    LoginWho
                    + "&nbsp;&nbsp;<span class=\"caret\"></span></button>\n" +
                    "                            <ul class=\"dropdown-menu\">\n" +
                    "                                <li><a href=\"/userIndex\">个人中心</a></li>\n" +
                    "                                <li><a href=\"/studentNotice\">通知中心</a></li>\n" +
                    "                                <li class=\"divider\"></li>\n" +
                    "                                <li><a href=\"?do=UserLogin&control=userSignOut\">注销</a></li>\n" +
                    "                            </ul>\n" +
                    "                        </div>\n" ;
        }
        szLoginHTML += "</div>";

        return "        <div class=\"navbar navbar-inverse set-radius-zero\" >\n" +
                "            <div class=\"container\">\n" +
                "                <div class=\"navbar-header\">\n" +
                "                    <button type=\"button\" class=\"navbar-toggle\" data-toggle=\"collapse\" data-target=\".navbar-collapse\">\n" +
                "                        <span class=\"icon-bar\"></span>\n" +
                "                        <span class=\"icon-bar\"></span>\n" +
                "                        <span class=\"icon-bar\"></span>\n" +
                "                    </button>\n" +
                "                    <a class=\"navbar-brand\" href=\"#\">\n" +
                "                        <img src=\"/root/assets/img/logo.png\" />\n" +
                "                    </a>\n" +
                "                </div>\n" +
                "\n" +
                "                <div class = \"form-group search_wrap\" id = \"search_wrap\">\n" +
                "                    <form method=\"get\">\n" +
                "                        <input type=\"hidden\" name=\"do\" value=\"SearchWord\"/>\n" +
                "                        <input type=\"text\" name=\"KeyWord\" class=\"form-control\" placeholder=\"请输入搜索关键字\" />\n" +
                "                        <button class=\"btn btn-primary search\" style=\"float:right\" type=\"submit\">\n" +
                "                            <i class = \"fa fa-search\"></i>\n" +
                "                        </button>\n" +
                "                    </form>\n" +
                "                </div>\n" +
                szLoginHTML+
                "            </div>\n" +
                "        </div>\n" +
                "        <!--class=\"menu-top-active\"-->\n" +
                "        <section class=\"menu-section\">\n" +
                "            <div class=\"container\">\n" +
                "                <div class=\"row \">\n" +
                "                    <div class=\"col-md-12\">\n" +
                "                        <div class=\"navbar-collapse collapse\" id=\"mobileMenu\">\n" +
                "                            <ul id=\"menu-top\" class=\"nav navbar-nav navbar-left\" style=\"margin-left: -18px\">\n" +
                szMenu  +
                "                            </ul>\n" +
                "                        </div>\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "        </section>";
    }

    public String includeStaticHead( Wizard hWizard , String szLoginWho ){
        return "<!doctype html>\n" +
                "        <html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "        <head>\n" +
                "            <!-- Public Global Static Include -->\n" +
                "            <meta charset=\"utf-8\" />\n" +
                "            <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=1\" />\n" +
                "            <meta name=\"description\" content=\"\" />\n" +
                "            <meta name=\"author\" content=\"\" />\n" +
                "            <!--[if IE]>\n" +
                "            <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge,chrome=1\">\n" +
                "            <![endif]-->\n" +
                "            <title id=\"staticTitle\">" + hWizard.getTitle() + "</title>\n" +
                "            <link href=\"/root/assets/css/bootstrap.css\" rel=\"stylesheet\" />\n" +
                "            <link href=\"/root/assets/css/font-awesome.css\" rel=\"stylesheet\" />\n" +
                "            <link href=\"/root/assets/css/Predator.css\" rel=\"stylesheet\" />\n" +
                "            <link href=\"/root/assets/css/tooltip.css\" rel=\"stylesheet\" />\n"+
                "        </head>\n" +
                "        <body>"
                + this.includeMainHeadPart(szLoginWho)
                ;
    }

    public String includeFooter(){
        return "<section class=\"footer-section\">\n" +
                "            <div class=\"container\">\n" +
                "                <div class=\"row\">\n" +
                "                    <div class=\"col-md-12\">\n" +
                "                        <p>版权所有 © 2008 - 2021 <a href=\"http://www.rednest.cn/\" target=\"_blank\">袁佳逸</a> | Copyright © 2008 - 2021 YJY (Genius!) All rights reserved.</p>\n" +
                "                        <p>技术支持 : <a href=\"http://www.nutgit.com/\" target=\"_blank\">CSDN个人博客</a> | Powered by : <a href=\"http://www.csdn.cn/\" target=\"_blank\">Genius Blog</a></p>\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "        </section>\n" +
                "        <!-- FOOTER SECTION END-->";
    }
}
