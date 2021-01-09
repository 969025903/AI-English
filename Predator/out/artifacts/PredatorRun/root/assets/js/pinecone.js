/**
 * Pinecone2.6 Framework For JavaScript Plutonium Edition (Bean Nuts Pinecone Prime)
 * Copyright(C) Bean Corporation (Mr.A.R.B / WJH)
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * JS Plutonium Edition special for web-design , prime is only using for HuiMin Corporation Website !
 * Pinecone JS Family [Tritium(All in one), Plutonium(this), Uranium(node.js)] 20200821
 * Code by Mr.A.R.B / WJH
 * Pinecone is a comprehensive large-scale framework, It has multiple language versions
 * Include Almond, C/CPP, JAVA, PHP, Python, JavaScript, ActionScript, VB[Basic], E, Prototype Lisp
 * :) Hope you enjoy this
 */

/**
 * Pinecone Object-oriented Main Class
 */
var Pinecone = { // pinecone namespace & static class like C++
    config:{
        'version':'2.6.0',
        "author" : "MR.A.R.B["+undefined+"]",
        'PINECONE_RELEASE_DATE': '20210606'
    },

    init:function(pFun){
        pFun(arguments);
    },

    getQueryString: function(){
        var url = window.document.location.href.toString();
        var urls = url.split("?");
        if( typeof(urls[1]) == "string" ){
            return urls[1];
        }
        return "";
    },

    parseQueryString: function(){
        var szURL = Pinecone.getQueryString();
        if( !szURL ){
            return {};
        }
        var urls = szURL.split("&");
        var urlChips = {};

        for( var i in urls ){
            var nodes = urls[i].split("=");
            for( var j in nodes ){
                nodes[j] = nodes[j].split("#")[0];
            }
            var szKey = nodes[0], szValue = nodes[1];
            try {
                szValue = decodeURIComponent( szValue.replace(/\+/g, '%20') ) ;
            }
            catch (e) {
                console.log(e);
            }

            if( szKey.match(/\[\]/g) ){
                if( szKey.length > 2 ){
                    szKey = szKey.replace( /\[\]/g, "" );
                }
                if( urlChips[szKey] === undefined ){
                    urlChips[szKey] = [];
                }
                urlChips[szKey].push( szValue );
            }
            else {
                urlChips[szKey] = szValue;
            }
        }
        return urlChips;
    },

    elemParserStatic:{
        dir: function(elem, dir, until) {
            var matched = [],
                cur = elem[dir];
            while (cur && cur.nodeType !== 9 && (until === undefined || cur.nodeType !== 1 || !jQuery(cur).is(until))) {
                if (cur.nodeType === 1) {
                    matched.push(cur);
                }
                cur = cur[dir];
            }
            return matched;
        },
        myValueByID : function (exp,val) {
            var handle = document.getElementById(exp);
            if(handle){
                handle.value = val;
                return handle.value;
            }
            return null;
        },
        myCSSByID:function (exp,p,v) {
            var handle = document.getElementById(exp);
            if(handle){
                handle.style.setProperty(p, v);
                return handle.style;
            }
            return null;
        }
    },

    elemParser:function (selector, context) { //Tiny JQuery & JQStyle
        this.defun = this.prototype;
        this.defun = {
            init : function (selector, context) {
                var nodeList = [];
                if (typeof (selector) == 'string') {
                    nodeList = (context || document).querySelectorAll(selector);
                } else if (selector instanceof Node) {
                    nodeList[0] = selector;
                } else if (selector instanceof NodeList || selector instanceof Array) {
                    nodeList = selector;
                }
                this.length = nodeList.length;
                for (var i = 0; i < this.length; i += 1) {
                    this[i] = nodeList[i];
                }
                return this;
            },
            each : function (cb_fun, need_ret) {
                var res = [];
                for (var i = 0; i < this.length; i++) {
                    res[i] = cb_fun.call(this[i]);
                }
                if (need_ret) {
                    if (res.length === 1) {
                        res = res[0];
                    }
                    return res;
                }
                return this;
            },
            eq : function () {
                var nodeList = [];
                for (var i = 0; i < arguments.length; i++) {
                    nodeList[i] = this[arguments[i]];
                }
                return elemParser(nodeList);
            },
            first : function () {
                return this.eq(0);
            },
            last : function () {
                return this.eq(this.length - 1);
            },
            find : function (str) {
                var nodeList = [];
                var res = this.each(function () {
                    return this.querySelectorAll(str);
                }, 1);
                if (res instanceof Array) {
                    for (var i = 0; i < res.length; i++) {
                        for (var j = 0; j < res[i].length; j++) {
                            nodeList.push(res[i][j]);
                        }
                    }
                } else {
                    nodeList = res;
                }
                return elemParser(nodeList);
            },
            parent : function () {
                return elemParser(this.each(function () {
                    return this.parentNode;
                }, 1));
            },
            hide : function () {
                return this.each(function () {
                    this.style.display = "none";
                });
            },
            show : function () {
                return this.each(function () {
                    this.style.display = "";
                });
            },
            myText : function (str) {
                if (str !== undefined) {
                    return this.each(function () {
                        this.innerText = str;
                    });
                } else {
                    return this.each(function () {
                        return this.innerText;
                    }, 1);
                }
            },
            myHtml : function (str) {
                if (str !== undefined) {
                    return this.each(function () {this.innerHTML = str;});
                } else {
                    return this.each(function () {return this.innerHTML;}, 1);
                }
            },
            outHtml : function (str) {
                if (str !== undefined) {
                    return this.each(function () {
                        this.outerHTML = str;
                    });
                } else {
                    return this.each(function () {
                        return this.outerHTML;
                    }, 1);
                }
            },
            myValue : function (str) {
                if (str !== undefined) {
                    return this.each(function () {
                        this.value = str;
                    });
                } else {
                    return this.each(function () {
                        return this.value;
                    }, 1);
                }
            },
            myCSS : function (key, value, important) {
                if(key instanceof Object && arguments.length < 2){
                    for(var i in key){
                        if(key.hasOwnProperty(i)){
                            this.myCSS(i,key[i]);
                        }
                    }
                }else if (value !== undefined) {
                    return this.each(function () {
                        this.style.setProperty(key, value,(important?'important':null));
                    });
                } else {
                    return this.each(function () {
                        return this.style.getPropertyValue(key);
                    }, 1);
                }
            },
            attr : function (key, value) {
                if (value !== undefined) {
                    return this.each(function () {
                        this.setAttribute(key, value);
                    });
                } else {
                    return this.each(function () {
                        return this.getAttribute(key);
                    }, 1);
                }
            },
            removeAttr : function (key) {
                return this.each(function () {
                    this.removeAttribute(key);
                });
            },
            remove : function () {
                return this.each(function () {
                    this.remove();
                });
            },
            append : function (str) {
                return this.each(function () {
                    this.insertAdjacentHTML('beforeend', str);
                });
            },
            prepend : function (str) {
                return this.each(function () {
                    this.insertAdjacentHTML('afterbegin', str);
                });
            },
            hasClass : function (str) {
                return this.each(function () {
                    return this.classList.contains(str);
                }, 1);
            },
            addClass : function (str) {
                return this.each(function () {
                    return this.classList.add(str);
                });
            },
            removeClass : function (str) {
                return this.each(function () {
                    return this.classList.remove(str);
                });
            },
            click : function (f) {
                if (typeof (f) == "function") {
                    this.each(function () {
                        this.addEventListener("click", f);
                    });
                } else {
                    this.each(function () {
                        var event = document.createEvent('HTMLEvents');
                        event.initEvent("click", true, true);
                        this.dispatchEvent(event);
                    });
                }
            },
            tag : function (tag) {
                this[0] = document.createElement(tag);
                return this;
            },
            dom : function (str) {
                var dom = document.createElement('p');
                dom.innerHTML = str;
                this[0] = dom.childNodes[0];
                return this;
            },
            parents : function () {
                return $(this.each(function () {
                    return Pinecone.elemParserStatic.dir(this, "parentNode");
                }, 1));
            }
        };
        this.defun.init.prototype = this.defun;
        return new this.defun.init(selector, context);
    },

    Debug:{
        inlineProcess:function(anything){
            return JSON.stringify(anything);
        },
        outPutDebugString:function (anything) {
            console.log(this.inlineProcess(anything));
        },
        messageBox:function (anything) {
            alert(this.inlineProcess(anything));
        },
        spawnAtHead:function (data) {
            var h = document.createElement(arguments[1]?arguments[1]:"div");
            h.innerHTML = data;
            document.body.insertBefore(h,document.body.firstChild);
        }
    },

    Fairy: {
        JSObjectClone: function ( hObj ) {
            var newO = {};
            for ( var key in hObj ) {
                if (hObj.hasOwnProperty(key)) {
                    if (typeof hObj[key] === "object") {
                        if (hObj[key].constructor === Array) {
                            newO[key] = hObj[key].slice();
                        } else {
                            newO[key] = this.JSObjectClone(hObj[key]);
                        }
                    } else {
                        newO[key] = hObj[key];
                    }
                }
            }
            return newO;
        },
        JSObjectAt: function (hObj, i) {
            var j = 0;
            for (var key in hObj) {
                if (j++ === i && hObj.hasOwnProperty(key)) {
                    var buf = {};
                    buf[key] = hObj[key];
                    return buf;
                }
            }
        },
        JSObjectKeyAt: function (hObj, i) {
            var j = 0;
            for (var key in hObj) {
                if (j++ === i && hObj.hasOwnProperty(key)) {
                    return key;
                }
            }
        },
        JSObjectMerge: function (hObj, objSupply) {
            if (hObj && objSupply) {
                for (var key in objSupply) {
                    if (objSupply.hasOwnProperty(key)) {
                        hObj[key] = objSupply[key];
                    }
                }
            }
        },
        stripObject2Array: function(hObj) {
            var res = [];
            for (var key in hObj) {
                if ( hObj.hasOwnProperty(key) ) {
                    res.push(key);
                }
            }
            return res;
        },
        assign: function(rObj) {
            if (rObj == null) {
                throw new TypeError('Cannot convert undefined or null to object');
            }
            rObj = Object(rObj);
            for (var i = 1; i < arguments.length; i++) {
                var row = arguments[i];
                if (row != null) {
                    for (var key in row) {
                        if (Object.prototype.hasOwnProperty.call(row, key)) {
                            rObj[key] = row[key];
                        }
                    }
                }
            }
            return rObj;
        },
        tableSearch: function ( obj , matchK, matchVs, keyOfWantedCol ) {
            var res = {};
            for (var i = 0; i < obj.length; i++) {
                var r = obj[i]; var t = r[matchK];
                for (var j = 0; j < matchVs.length; j++) {
                    var m = matchVs[j];
                    if ( t === m ) {
                        res[m] = r[keyOfWantedCol];
                    }
                }
            }
            return res;
        },
        tableSearchSingle: function ( obj , matchK, matchV, keyOfWantedCol ) {
            return this.tableSearch( obj, matchK,[matchV], keyOfWantedCol )[matchV];
        }
    },

    WebControl:{
        isHTTPS:function () {
            return ('https:' === document.location.protocol);
        },
        urlHaveThenAppend:function ( url, keyValues, judgeFirst ) {
            if(keyValues){
                var i = 0;
                for(var key in keyValues){
                    if(keyValues.hasOwnProperty(key)){
                        url += (keyValues[key]?(((judgeFirst && i++ === 0)?"?":"&") + key + "=" + encodeURIComponent( keyValues[key]) ):(""));
                    }
                }
            }
            return url;
        },
        urlAutoMerge:function (mergeData,GET) {
            var GET_BUF = Pinecone.Fairy.JSObjectClone(GET);
            if(mergeData){
                for(var key in mergeData){
                    if(mergeData.hasOwnProperty(key)){
                        if(GET_BUF.hasOwnProperty(key)){
                            delete GET_BUF[key];
                        }
                        if(mergeData[key]){
                            GET_BUF[key] = mergeData[key];
                        }
                    }
                }
            }
            return (this.urlHaveThenAppend(window.location.href.toString().split("?")[0],GET_BUF,true));
        },
        back: function ( w ) {
            if( w === 1 ){
                history.back();
                return;
            }
            self.location = document.referrer;
        }
    },

    TimeTool:{
        time2IE:function (time) {
            return time.replace(/-/g,"/");
        },
        parseTimestamp:function (time) {
            return new Date(this.time2IE(time)).valueOf();
        },
        format: function( fmt, date ) {
            date = date ? date : new Date();
            var t = {
                "M+" : date.getMonth()+1,
                "d+" : date.getDate(),
                "h+" : date.getHours(),
                "m+" : date.getMinutes(),
                "s+" : date.getSeconds(),
                "q+" : Math.floor( ( date.getMonth() + 3 ) / 3 ),
                "S"  : date.getMilliseconds()
            };
            if(/(y+)/.test(fmt)) {
                fmt = fmt.replace(RegExp.$1, (date.getFullYear()+"").substr(4 - RegExp.$1.length));
            }
            for(var i in t) {
                if(new RegExp("("+ i +")").test(fmt)){
                    fmt = fmt.replace(RegExp.$1, ( RegExp.$1.length === 1 ) ? (t[i]) : (("00"+ t[i]).substr((""+ t[i]).length)));
                }
            }
            return fmt;
        }
    },

    Random:{
        nextInt:function (min,max) {
            return parseInt(Math.random() * (max-min+1) + min,10);
        },
        nextString:function (from, to, scale) {
            if(from > to){
                return false;
            }
            var randomDict = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
            var ss = "";
            for(var i=0; i < scale; i++){
                ss = ss + randomDict[this.nextInt(randomDict.indexOf(from), randomDict.indexOf(to))];
            }
            return ss;
        },
        nextStringWithScale:function (s) {
            return this.nextString('0','z',(s ? s : 10))
        }
    },

    String:{
        hypertext2Text:function (t,bNoSpace,bNoLine,bSavage) {
            if( t ){
                t = t.replace(/<[^>]+>/g, '');
                if(arguments[1] !== false){
                    t = t.replace(" ","");
                }
                if(arguments[2] !== false){
                    t = t.replace(/\t|\r|\n/g,"");
                }
                if(arguments[3]){
                    t = t.replace(/<[^>]+/g, '');
                }
            } else {
                t = "";
            }
            return t;
        }
    },

    FormChecker:function (formID) {
        this.formHandle = document.getElementById(formID);
        this.alSavedElements = [];
        this.bCheckForm = true;

        this.saveFormElements = function () {
            for(var i = 0 ; i < this.formHandle.elements.length ; i++ ) {
                if("select-one" === this.formHandle.elements[i].type) {
                    this.alSavedElements.push(this.formHandle.elements[i].selectedIndex);
                    continue;
                }
                if("radio" === this.formHandle.elements[i].type || "checkbox" === this.formHandle.elements[i].type) {
                    this.alSavedElements.push(this.formHandle.elements[i].checked);
                    continue;
                }
                this.alSavedElements.push(this.formHandle.elements[i].value);
            }
        };

        this.ignoreFormCheck = function () {
            this.bCheckForm = false;
        };

        this.forceFormCheck = function() {
            this.bCheckForm = true;
        };

        this.isFormChanged = function(form) {
            var bChanged = false;
            if(form.elements.length!== this.alSavedElements.length) {
                bChanged = true;
                return bChanged;
            }
            for(var i = 0 ; i < form.elements.length ; i++ ) {
                if("submit" !== form.elements[i].type && "button" !== form.elements[i].type && "reset" !== form.elements[i].type && "hidden" !== form.elements[i].type && "radio" !== form.elements[i].type && "checkbox" !== form.elements[i].type && "select-one" !== form.elements[i].type && form.elements[i].value!==this.alSavedElements[i]) {
                    bChanged = true;
                    break;
                }
                if("select-one" === form.elements[i].type && form.elements[i].selectedIndex!== this.alSavedElements[i]) {
                    bChanged = true;
                    break;
                }
                if(("radio" === form.elements[i].type || "checkbox" === form.elements[i].type ) && form.elements[i].checked !== this.alSavedElements[i]) {
                    bChanged = true;
                    break;
                }
            }
            return bChanged;
        };

        this.getFormStatus = function() {
            return (this.bCheckForm && this.isFormChanged(this.formHandle));
        };

        this.saveFormElements();
    },

    stateRename:function (handle,stateMap,defaultNum) {
        if(!defaultNum){
            defaultNum = handle;
        }
        if(stateMap){
            var map = {};
            if(stateMap["enum"]){
                var index = stateMap["enum"].length > 1?(function () {var temp = stateMap["enum"][0];stateMap["enum"] = stateMap["enum"][1];return temp;})():0;
                for (var key in stateMap["enum"]) {
                    if(stateMap["enum"].hasOwnProperty(key)){

                        map[index++] = stateMap["enum"][key];
                    }
                }
                delete stateMap["enum"];
            }
            Pinecone.Fairy.JSObjectMerge(stateMap,map);
            if(stateMap.hasOwnProperty(handle)){
                handle = stateMap[handle];
            }else {
                handle = defaultNum;
            }
        }
        return handle;
    },

    gotoURL : function(url){
        window.location.href = url;
    },

    isTotalTrue:function (flag) {
        return (flag !== undefined) && (flag !== []) && (flag !== {}) && (flag !== "") && flag;
    },

    isAbsoluteTrue:function(flag){
        return this.isTotalTrue(flag) && (flag !== '0' && flag !== 'false' && flag !== 'null');
    },

    isThenSet:function(val,defaultNum){
        return this.isTotalTrue(val)?val:defaultNum;
    },

    about: function () {
        alert("Pinecone Ver." + this.config.version +
            " For Javascript.\nArchitect: DR.A.R.B[undefined], Long Long Int[JoeyBada$$]\n;) Hope you enjoy this");
    }
};

Pinecone.elemParser.ajax = function (rOpt) {
    function empty() {}

    function toArray(arr,k,obj) {
        for(var i in obj){
            arr.push(encodeURI(k)+'[]='+encodeURI(obj[i]));
        }
    }

    function obj2Url(obj) {
        var arr = [];
        for (var k in obj) {
            if(obj.hasOwnProperty(k)){
                if(obj[k] instanceof Array){
                    toArray(arr,k,obj[k]);
                }else {
                    arr.push(encodeURI(k) + '=' + encodeURI(obj[k]));
                }
            }
        }
        return arr.join('&').replace(/%20/g, '+');
    }

    var opt = {
        url: '',
        sync: true,
        method: 'GET',
        data: '',
        dataType: 'json',
        username: null,
        password: null,
        success: empty,
        error: empty,
        timeout: 10000
    };

    if(!rOpt['method']){
        rOpt['method'] = rOpt['type'] ? rOpt['type'] : 'GET'
    }

    Pinecone.Fairy.assign(opt, rOpt);
    var abortTimeout = null;
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            xhr.onreadystatechange = empty;
            clearTimeout(abortTimeout);
            if ((xhr.status >= 200 && xhr.status < 300) || xhr.status === 304) {
                var result = xhr.responseText;
                try {
                    if (opt.dataType === 'json') {
                        result = result.replace(' ', '') === '' ? null : JSON.parse(result);
                    }
                } catch (e) {
                    opt.error(e, xhr);
                    xhr.abort();
                }
                opt.success(result, xhr);
            } else if (0 === xhr.status) {
                opt.error("Request Failed !", xhr);
            } else {
                opt.error(xhr.statusText, xhr);
            }
        }
    };
    var data = opt.data ? obj2Url(opt.data) : opt.data;
    opt.method = opt.method.toUpperCase();
    if (opt.method === 'GET') {
        opt.url += (opt.url.indexOf('?') === -1 ? '?' : '&') + data;
    }
    xhr.open(opt.method, opt.url, opt.sync, opt.username, opt.password);
    if (opt.method === 'POST') {
        xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    }
    if (opt.timeout > 0) {
        abortTimeout = setTimeout(function () {
            xhr.onreadystatechange = empty;
            xhr.abort();
            opt.error('Network Request Timeout !', xhr);
        }, opt.timeout);
    }
    xhr.send(data);
};

var PineconeFactory = {
    call:function(funName){
        if(!funName){
            return false;
        }
        var args = arguments[1];
        args = (args === null)?"":args;
        try{
            eval(funName+"("+args+")");
        }catch (e) {
            console.log(e);
            if(e.name === "ReferenceError"){
                return false;
            }
        }
        return true;
    },
    getFunctionName:function (functionInfo) {
        functionInfo = functionInfo.substr('function '.length);
        return functionInfo.substr(0, functionInfo.indexOf('('));
    }
};

var PineconeMVC = {
    proceduralModelDispatch:function(renderArray,invoker,defaultInvoker){
        var f = PineconeFactory.call(invoker);
        if( (f ? f : PineconeFactory.call(defaultInvoker)) ){
            $_PMVC.autoDisplay(renderArray, invoker , defaultInvoker);
            return true;
        }
        return false;
    },
    autoRender:function (selector,data) {
        var pP = Pinecone.elemParser(selector);
        switch (pP[0].localName) {
            case ("script"):case ("select"):
            case ("input"):{
                var t = pP[0].type;
                if(t === "checkbox" && Pinecone.isAbsoluteTrue(data)){
                    pP.attr("checked",true);break;
                }
                pP.myValue(data);break;
            }
            default:{
                pP.myText(data);break;
            }
        }

    },
    quickRender:function(array,selector,pfnModifyKey){
        for(var key in array){
            if(array.hasOwnProperty(key)){
                try{this.autoRender((pfnModifyKey?pfnModifyKey(key):(selector?selector+" "+key:key)),array[key]);}catch (e) {}
            }
        }
    },
    tag:function(key,value){
        var pHTML = document.getElementsByTagName("html")[0];
        pHTML.innerHTML = pHTML.innerHTML.replace("{{"+key+"}}",value);
    },
    conditionalRenderer:function (renderArray, whoNow) {
        for(var i=0; i<renderArray.length; i++){
            if(whoNow === renderArray[i]){
                pPineElem.myCSSByID(renderArray[i],"display", "block");
            }else {
                pPineElem.myCSSByID(renderArray[i],"display", "none");
            }
        }
    },
    formDynamicRenderer:function (dataMapArray,distinguishSuffix,independentMapArray) {
        for(var key in dataMapArray){
            if(dataMapArray.hasOwnProperty(key)){
                var oldKey = key;
                if(independentMapArray && independentMapArray.hasOwnProperty(key)){
                    key = independentMapArray[key];
                }else {
                    key += distinguishSuffix;
                }
                pPineElem.myValueByID(key,dataMapArray[oldKey]);
            }
        }
    },
    autoDisplay:function(renderArray,invoker,defaultInvoker){
        $_PMVC.conditionalRenderer(renderArray, (invoker && renderArray.indexOf(invoker) > -1) ? invoker : defaultInvoker);
    }
};


var $_GET = Pinecone.parseQueryString(), $_PINE = Pinecone.elemParser, pPineElem = Pinecone.elemParserStatic, pPine = Pinecone, $_PMVC = PineconeMVC;

var JSBasedModelDispatcher = function (dom) {
    this.defun = function (dom) {
        this.dom           = dom;
        this.pfnFront      = Pinecone.Fairy.JSObjectKeyAt(this.dom,0);
        if( !this.pfnFront ){
            return;
        }
        this.defaultWizard      = arguments[1] ? arguments[1] : this.pfnFront;
        this.mbAutoDisplay      = true;
        this.proto              = this;
        this.mszCurrentFun      = this.defaultWizard;

        this.setAutoDisplay = function (b) {
            this.mbAutoDisplay = !!b;
            return this.proto;
        };

        this.spawnSubSelector = function ( q ) {
            return "#" + this.mszCurrentFun  + " " + ( q?q:"");
        };

        this.summon = function ( w ) {
            w = w ? w : this.defaultWizard;

            if( w && this.dom.hasOwnProperty( w ) ){
                try{
                    this.mszCurrentFun = w;
                    this.dom[w]( this, w );
                    if( this.mbAutoDisplay ){
                        $_PMVC.autoDisplay( pPine.Fairy.stripObject2Array( this.dom ), w , this.defaultWizard );
                    }
                }
                catch (e) {
                    console.log("JSBasedModelRenderer: " + e.toString(), e);
                }
            }
            else {
                console.log("JSBasedModelRenderer: Illegal Action.");
            }

            return this.proto;
        };
    };

    return new this.defun( dom );
};

var $_PR = JSBasedModelDispatcher;

/**
 * Pinecone Process-oriented
 */

function myName() {
    var functionInfo = arguments.callee.arguments.callee.caller.toString();
    return PineconeFactory.getFunctionName(functionInfo);
}

function sizeof(pObject) {
    if(pObject instanceof Array){
        return pObject.length;
    }else {
        if(pObject instanceof Object){
            return Object.getOwnPropertyNames(pObject).length;
        }
    }
}

function trace() {
    for(var i=0;i<arguments.length;i++){
        pPine.Debug.spawnAtHead('<h1>'+pPine.Debug.inlineProcess(arguments[i])+'</h1>');
        console.log("args"+i+":",arguments[i]);
    }
}

function vomit() {
    console.log("Pinecone Debug Report (Vomit)\nCall Stack List:");
    var j = 0;
    var functionInfo = arguments.callee;
    do {
        functionInfo = functionInfo.arguments.callee.caller;
        try{
            console.log(++j + '. caller: ' + PineconeFactory.getFunctionName(functionInfo.toString()));
        }catch (e) {
            console.log(e);
        }
    } while (functionInfo);
    console.log("Trace Info:");
    for(var i=0;i<arguments.length;i++){
        console.log("args"+i+":",arguments[i]);
    }
    console.log("--------------Vomit End--------------")
}

function probe(){
    if(!arguments[0]){
        alert( 'Pinecone: Shitty Mason Is Here !');
    } else{
        if(arguments[0] === true){
            trace( 'Pinecone: pluto is a planet !');
        }
    }
    return false;
}


