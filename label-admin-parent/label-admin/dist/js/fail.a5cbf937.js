(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["fail"],{"01f4":function(t,e,s){"use strict";var c=s("1fe3"),a=s.n(c);a.a},"0673":function(t,e,s){"use strict";s.r(e);var c=function(){var t=this,e=t.$createElement,s=t._self._c||e;return s("div",{staticClass:"exception"},[s("div",{staticClass:"img"},[s("img",{attrs:{src:t.config[t.type].img}})]),s("div",{staticClass:"content"},[s("h1",[t._v(t._s(t.config[t.type].title))]),s("div",{staticClass:"desc"},[t._v(t._s(t.config[t.type].desc))]),s("div",{staticClass:"action"},[s("a-button",{attrs:{type:"primary"},on:{click:t.handleToHome}},[t._v("返回首页")])],1)])])},a=[],i={403:{img:"https://gw.alipayobjects.com/zos/rmsportal/wZcnGqRDyhPOEYFcZDnb.svg",title:"403",desc:"抱歉，你无权访问该页面"},404:{img:"https://gw.alipayobjects.com/zos/rmsportal/KpnpchXsobRgLElEozzI.svg",title:"404",desc:"抱歉，你访问的页面不存在或仍在开发中"},500:{img:"https://gw.alipayobjects.com/zos/rmsportal/RVRUAYdCGeYNBWoKiIwB.svg",title:"500",desc:"抱歉，服务器出错了"}},n=i,o={name:"Exception",props:{type:{type:String,default:"404"}},data:function(){return{config:n}},methods:{handleToHome:function(){this.$router.push({name:"dashboard"})}}},r=o,l=(s("01f4"),s("2877")),p=Object(l["a"])(r,c,a,!1,null,"f38602ac",null);e["default"]=p.exports},"1fe3":function(t,e,s){},cc89:function(t,e,s){"use strict";s.r(e);var c=function(){var t=this,e=t.$createElement,s=t._self._c||e;return s("exception-page",{attrs:{type:"404"}})},a=[],i=s("0673"),n={components:{ExceptionPage:i["default"]}},o=n,r=s("2877"),l=Object(r["a"])(o,c,a,!1,null,"b5dd89e6",null);e["default"]=l.exports}}]);