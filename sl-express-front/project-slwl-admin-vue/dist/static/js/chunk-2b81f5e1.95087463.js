(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-2b81f5e1"],{"0a86":function(t,e,a){"use strict";a.d(e,"c",(function(){return r})),a.d(e,"d",(function(){return i})),a.d(e,"a",(function(){return s})),a.d(e,"b",(function(){return o})),a.d(e,"e",(function(){return c})),a.d(e,"f",(function(){return l}));var n=a("b775"),r=function(t){return Object(n["a"])("/transport-task-manager/page","post",t)},i=function(t){return Object(n["a"])("/transport-task-manager/count","get",t)},s=function(t){return Object(n["a"])("/web-manager/driver-job-manager/page","post",t)},o=function(t){return Object(n["a"])("/transport-task-manager/".concat(t),"get",t)},c=function(t){return Object(n["a"])("/apache-druid/query/selectOne","get",t)},l=function(t){return Object(n["a"])("/web-manager/transport-task-manager/point/".concat(t),"get",t)}},"0d55":function(t,e,a){},"12d8":function(t,e,a){"use strict";a("325e")},"21d8":function(t,e,a){"use strict";a("d614")},2298:function(t,e){t.exports="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABwAAAAcCAYAAAByDd+UAAADQ0lEQVRIS8WWT2gcZRjGf8/stlR6kCgIHpqCGsE/WA/WNjVNMhtPihexin/Z2bbowYMeSqlaXf8kh6CCFy+6O7EQkPQgPRS06Oxk1dRKEcFDrRpELx5s8V+LxTLzyjc2280mm410Vz+YwzDv9zzf+37PO+8jOi0z+SHDiLsFWw0GMPoQKcYvBic98XmScmh2p452gtNKAYXQdhi8gnF9I078iXHajJxEH7CuCeOkxHj0A9OUlS6HvSxhYcqutZRpYAvCMOom3iPP4fgR5pEsAzPT2AEGLGUkNR4UjAJCfJpAUA/0bSvpEsKxivmpOAhcCcyZsTfeqU86lcp9Hwlts4zXBNsxfpO4Nyopat67iNCRJeIDQV4wEQXsb2SzGkYXUzZvtJ99wMuC83jcVSvqo8LbtlEe6xuEF8p4DLhCohQFmlotx3JxhSkL0pSK4Hcvx+1JwruCay4QmvwqTmFbBONRSc9dCtnCXr9qLwLPIz7DuBrYmBFmajRm3J3VAob+dRnbna5snt+Pq9ptCyFySvOn+BoytQ2vViCdKjD8lt2Q83gScZPTU4PQr5p7iYHZWklO1l1ZfmgvYexvATslP7RJjD0mnooDvdEVNuDGGVt71Vm2YeQXMPM5vpFftY+BIUsZiHfpu24RtsNxGf4EXF4rsr5rYlnh1C7Dc8DPtZI29Do7h/8/EP4XJTXTaMgBwRcXRbOGgfjR3ojGr9gmxJeIes/aolkPftWeBl4HJl2GPWn8RYShzWIMA1t79mtbIByp2KAn5gy+iku6ZdHP2+BoHHBHt/pxx4zlTp2hDmyTuD8KdHDJeEJM1AI9242eLFSsbOIFZzlqRba7RJYdwOaxOy6qcimkhdCeMONN4I8k4db6bn2fNX4zaMNiiLyMyehHnmnnvtodZrRsefUzDuxBnEPc4yzGxXnYsrPFRB032BeX9OFqsh17x+5ME14FNgG/yuOBqKgjzXs728R/oo8LDqXG+2vXcOLIYzrrBvdghb7L4GbLMQQ8hGXD1q1jOs/D0eOabz3oCkbYVAi5b4kRdvcg/nJGGLKnsQQnUpiIA6bbKX1F550hNVl9jEHEdZnVhwQ4bTAvY07icFTSXKfS/w0Eo2XRqFNxOgAAAABJRU5ErkJggg=="},"325e":function(t,e,a){},3283:function(t,e,a){"use strict";a.r(e);var n=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("el-container",{staticClass:"customer-details-box"},[a("el-card",{staticStyle:{width:"100%"},attrs:{shadow:"never"}},[a("div",{staticClass:"block"},[a("div",{staticClass:"car-base delivery-pic"},[t._v("交付货品照片")]),t._v(" "),a("div",{staticClass:"demo-input-suffix",staticStyle:{color:"#2a2929","font-size":"14px",padding:"15px 26px"}},[a("el-image",{staticStyle:{width:"424px",height:"282px","border-radius":"10px"},attrs:{src:t.deliverPicture||t.defaultPic}},[a("div",{staticClass:"image-slot",staticStyle:{"text-align":"center","line-height":"282px"},attrs:{slot:"error"},slot:"error"},[t._v("\n            暂无照片\n          ")])])],1),t._v(" "),a("div",{staticClass:"car-base car-img-base delivery-pic"},[t._v("支付回单照片")]),t._v(" "),a("div",{staticClass:"demo-input-suffix",staticStyle:{color:"#2a2929","font-size":"14px",padding:"15px 26px"}},[a("el-image",{staticStyle:{width:"424px",height:"282px","border-radius":"10px"},attrs:{src:t.deliverPicture||t.defaultPic}},[a("div",{staticClass:"image-slot",staticStyle:{"text-align":"center","line-height":"282px"},attrs:{slot:"error"},slot:"error"},[t._v("\n            暂无照片\n          ")])])],1),t._v(" "),a("div",{staticClass:"footer-box"},[a("el-button",{staticClass:"cancel-btn",on:{click:function(e){return t.handleTaskList()}}},[t._v("返回")])],1)])])],1)},r=[],i=(a("e186"),a("c3ae")),s=a("0a86"),o={data:function(){return{deliverPicture:"",defaultPic:"https://yjy-slwl-oss.oss-cn-hangzhou.aliyuncs.com/282b9ee4-9edc-40e9-b365-84dec2cce429.png"}},created:function(){this.id=this.$route.query.id,this.getList(this.id)},methods:{goBack:function(){},getList:function(){var t=Object(i["a"])(regeneratorRuntime.mark((function t(e){var a,n;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return t.next=2,Object(s["b"])(e);case 2:a=t.sent,n=a.data,this.deliverPicture=n.deliverPicture;case 5:case"end":return t.stop()}}),t,this)})));function e(e){return t.apply(this,arguments)}return e}(),handleTaskList:function(){this.$router.push({path:"/transport/transport-task"})}}},c=o,l=(a("d23d"),a("cba8")),u=Object(l["a"])(c,n,r,!1,null,"7198667d",null);e["default"]=u.exports},3602:function(t,e,a){"use strict";a("cd79")},4461:function(t,e,a){"use strict";a.r(e);var n=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("el-container",{staticClass:"customer-details-box"},[a("el-card",{staticStyle:{width:"100%"},attrs:{shadow:"never"}},[a("div",{staticClass:"block"},[a("div",{staticClass:"car-base task-info"},[t._v("基本信息")]),t._v(" "),a("el-card",{staticClass:"form-box",attrs:{shadow:"never"}},[a("div",{staticClass:"form-item-box"},[a("div",{staticClass:"form-item"},[a("span",{staticClass:"span-title"},[t._v("运输任务编号：")]),t._v(" "),a("el-input",{attrs:{disabled:t.disabled,placeholder:"请输入运输任务编号"},model:{value:t.taskInfo.taskNo,callback:function(e){t.$set(t.taskInfo,"taskNo",e)},expression:"taskInfo.taskNo"}})],1),t._v(" "),a("div",{staticClass:"form-item"},[a("span",{staticClass:"span-title"},[t._v("车牌号码：")]),t._v(" "),a("el-input",{attrs:{disabled:t.disabled,placeholder:"请输入车牌号码"},model:{value:t.taskInfo.carNum,callback:function(e){t.$set(t.taskInfo,"carNum",e)},expression:"taskInfo.carNum"}})],1),t._v(" "),a("div",{staticClass:"form-item"},[a("span",{staticClass:"span-title"},[t._v("调度机构：")]),t._v(" "),a("el-input",{attrs:{disabled:t.disabled,placeholder:"请输入调度机构"},model:{value:t.taskInfo.startAgencyName,callback:function(e){t.$set(t.taskInfo,"startAgencyName",e)},expression:"taskInfo.startAgencyName"}})],1),t._v(" "),a("div",{staticClass:"form-item"},[a("span",{staticClass:"span-title"},[t._v("起始地：")]),t._v(" "),a("el-input",{attrs:{disabled:t.disabled,placeholder:"请输入起始地"},model:{value:t.taskInfo.startAgencyName,callback:function(e){t.$set(t.taskInfo,"startAgencyName",e)},expression:"taskInfo.startAgencyName"}})],1),t._v(" "),a("div",{staticClass:"form-item"},[a("span",{staticClass:"span-title"},[t._v("目的地：")]),t._v(" "),a("el-input",{attrs:{disabled:t.disabled,placeholder:"请输入目的地"},model:{value:t.taskInfo.endAgencyName,callback:function(e){t.$set(t.taskInfo,"endAgencyName",e)},expression:"taskInfo.endAgencyName"}})],1)])]),t._v(" "),a("div",{staticClass:"footer-box"},[a("el-button",{staticClass:"cancel-btn",on:{click:function(e){return t.handleTaskList()}}},[t._v("返回")])],1)],1)])],1)},r=[],i=(a("a450"),a("e186"),a("c3ae")),s=a("0a86"),o={data:function(){return{disabled:!0,taskInfo:{taskNo:"",carNum:"",startAgencyName:"",endAgencyName:""}}},created:function(){this.id=this.$route.query.id,this.getList(this.id)},methods:{goBack:function(){},getList:function(){var t=Object(i["a"])(regeneratorRuntime.mark((function t(e){var a,n;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return t.next=2,Object(s["b"])(e);case 2:a=t.sent,n=a.data,this.taskInfo.taskNo=n.id,null!=n.truck&&(this.taskInfo.carNum=n.truck.licensePlate),null!=n.startAgency&&(this.taskInfo.startAgencyName=n.startAgency.name),null!=n.endAgency&&(this.taskInfo.endAgencyName=n.endAgency.name);case 8:case"end":return t.stop()}}),t,this)})));function e(e){return t.apply(this,arguments)}return e}(),handleTaskList:function(){this.$router.push({path:"/transport/transport-task"})}}},c=o,l=(a("a5e0"),a("cba8")),u=Object(l["a"])(c,n,r,!1,null,"796eebbf",null);e["default"]=u.exports},"44a9":function(t,e,a){"use strict";a("0d55")},"4ce6":function(t,e,a){},9399:function(t,e,a){"use strict";a.r(e);var n=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"dashboard-container"},[a("div",{staticClass:"app-container"},[a("el-container",[a("div",{staticClass:"aside-box"},[a("el-aside",t._l(t.menu,(function(e,n){return a("div",{key:n,staticClass:"aside-item",class:{active:t.isActive==e.value},on:{click:function(a){t.isActive=e.value,t.currentComp=e.comp}}},[t._v(t._s(e.name))])})),0)],1),t._v(" "),a("el-container",[a(t.currentComp,{tag:"test1"})],1)],1)],1)])},r=[],i=a("4461"),s=a("e29b"),o=a("ae36"),c=a("3283"),l=a("9b70"),u={components:{taskInfo:i["default"],missionLocus:s["default"],waybillDetail:o["default"],deliveryPic:c["default"],pickPic:l["default"]},data:function(){return{currentComp:"taskInfo",isActive:"1",id:"",menu:[{name:"基本信息",value:"1",comp:"taskInfo"},{name:"任务轨迹",value:"2",comp:"missionLocus"},{name:"运单详情",value:"3",comp:"waybillDetail"},{name:"交付照片",value:"4",comp:"deliveryPic"},{name:"提货照片",value:"5",comp:"pickPic"}]}},created:function(){},methods:{goBack:function(){}}},d=u,p=(a("44a9"),a("cba8")),h=Object(p["a"])(d,n,r,!1,null,"fa58a2c6",null);e["default"]=h.exports},"9b70":function(t,e,a){"use strict";a.r(e);var n=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("el-container",{staticClass:"customer-details-box"},[a("el-card",{staticStyle:{width:"100%"},attrs:{shadow:"never"}},[a("div",{staticClass:"block"},[a("div",{staticClass:"car-base pick-pic"},[t._v("提货货品照片")]),t._v(" "),a("div",{staticClass:"demo-input-suffix",staticStyle:{color:"#2a2929","font-size":"14px",padding:"15px 26px"}},[a("el-image",{staticStyle:{width:"424px",height:"282px","border-radius":"10px"},attrs:{src:t.cargoPicture||t.defaultPic}},[a("div",{staticClass:"image-slot",staticStyle:{"text-align":"center","line-height":"282px"},attrs:{slot:"error"},slot:"error"},[t._v("\n            暂无照片\n          ")])])],1),t._v(" "),a("div",{staticClass:"car-base pick-pic"},[t._v("提货凭证照片")]),t._v(" "),a("div",{staticClass:"demo-input-suffix",staticStyle:{color:"#2a2929","font-size":"14px",padding:"15px 26px"}},[a("el-image",{staticStyle:{width:"424px",height:"282px","border-radius":"10px"},attrs:{src:t.cargoPickUpPicture||t.defaultPic}},[a("div",{staticClass:"image-slot",staticStyle:{"text-align":"center","line-height":"282px"},attrs:{slot:"error"},slot:"error"},[t._v("\n            暂无照片\n          ")])])],1),t._v(" "),a("div",{staticClass:"footer-box"},[a("el-button",{staticClass:"cancel-btn",on:{click:function(e){return t.handleTaskList()}}},[t._v("返回")])],1)])])],1)},r=[],i=(a("e186"),a("c3ae")),s=a("0a86"),o={data:function(){return{cargoPicture:"",cargoPickUpPicture:"",defaultPic:"https://yjy-slwl-oss.oss-cn-hangzhou.aliyuncs.com/282b9ee4-9edc-40e9-b365-84dec2cce429.png"}},created:function(){this.id=this.$route.query.id,this.getList(this.id)},methods:{getList:function(){var t=Object(i["a"])(regeneratorRuntime.mark((function t(e){var a,n;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return t.next=2,Object(s["b"])(e);case 2:a=t.sent,n=a.data,this.cargoPicture=n.cargoPicture,this.cargoPickUpPicture=n.cargoPickUpPicture;case 6:case"end":return t.stop()}}),t,this)})));function e(e){return t.apply(this,arguments)}return e}(),handleTaskList:function(){this.$router.push({path:"/transport/transport-task"})}}},c=o,l=(a("3602"),a("cba8")),u=Object(l["a"])(c,n,r,!1,null,"674bdce7",null);e["default"]=u.exports},"9c02":function(t,e,a){},a5e0:function(t,e,a){"use strict";a("4ce6")},ae36:function(t,e,a){"use strict";a.r(e);var n=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("el-container",{staticClass:"customer-details-box waybill-detail"},[a("el-card",{staticStyle:{width:"100%"},attrs:{shadow:"never"}},[a("div",{staticClass:"block"},[a("div",{staticClass:"car-base"},[t._v("共计"+t._s(t.transportOrderCount)+"票运单")]),t._v(" "),a("el-card",{directives:[{name:"loading",rawName:"v-loading",value:t.listLoading,expression:"listLoading"}],staticClass:"form-box",class:{"loading-box":t.listLoading},attrs:{"element-loading-text":"加载中",shadow:"never"}},[a("el-table",{directives:[{name:"show",rawName:"v-show",value:t.dataList&&t.dataList.length>0,expression:"dataList && dataList.length > 0"}],key:t.tableKey,ref:"multipleTable",staticStyle:{width:"100%","text-align":"center",overflow:"auto"},attrs:{data:t.dataList,fit:"",stripe:"","header-cell-style":{background:"rgba(250,252,255,1)"}},on:{"selection-change":t.handleSelectionChange}},[a("el-table-column",{attrs:{type:"index",label:"序号",width:"60"}}),t._v(" "),a("el-table-column",{attrs:{align:"left",label:"运单编号",width:"200"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("span",[t._v(t._s(e.row.id))])]}}])}),t._v(" "),a("el-table-column",{attrs:{align:"left",label:"下单时间",width:"200"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("span",[t._v(t._s(null===e.row.order?"":e.row.order.createTime))])]}}])}),t._v(" "),a("el-table-column",{attrs:{align:"left",label:"发件人姓名",width:"100"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("span",[t._v(t._s(null===e.row.order?"":e.row.order.senderName))])]}}])}),t._v(" "),a("el-table-column",{attrs:{align:"left",label:"发件人电话",width:"140"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("span",[t._v(t._s(null===e.row.order?"":e.row.order.senderPhone))])]}}])}),t._v(" "),a("el-table-column",{attrs:{align:"left",label:"发件人地址",width:"180","show-overflow-tooltip":!0},scopedSlots:t._u([{key:"default",fn:function(e){return[a("span",[t._v(t._s(null===e.row.order?"":e.row.order.senderAddress))])]}}])}),t._v(" "),a("el-table-column",{attrs:{align:"left",label:"货品数量"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("span",[t._v(t._s(e.row.order&&e.row.order.orderCargoDtos&&e.row.order.orderCargoDtos.length))])]}}])}),t._v(" "),a("el-table-column",{attrs:{align:"left",fixed:"right",label:"操作",width:"86","class-name":"small-padding fixed-width"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("el-link",{attrs:{type:"primary",underline:!1},on:{click:function(a){return t.handleTransportDetail(e.row.id,e.row)}}},[t._v("查看详情")])]}}])})],1),t._v(" "),a("empty",{directives:[{name:"show",rawName:"v-show",value:(!t.dataList||t.dataList.length<=0)&&!t.listLoading,expression:"(!dataList || dataList.length <= 0) && !listLoading"}]})],1),t._v(" "),a("div",{staticClass:"footer-box"},[a("el-button",{staticClass:"cancel-btn",on:{click:function(e){return t.handleTaskList()}}},[t._v("返回")])],1)],1)])],1)},r=[],i=(a("e186"),a("c3ae")),s=(a("4057"),a("0a86")),o={name:"Courses",components:{},data:function(){return{transportOrderCount:"",value:"",multipleSelection:[],tableKey:0,dataList:[],total:null,listLoading:!0,alertText:""}},computed:{status:function(t){function e(){return t.apply(this,arguments)}return e.toString=function(){return t.toString()},e}((function(){return status}))},mounted:function(){},created:function(){var t=this;this.id=this.$route.query.id,this.getList(this.id),document.onkeydown=function(e){var a=window.event.keyCode;13===a&&t.handleFilter(this.requestParameters)}},updated:function(){},methods:{handleSelectionChange:function(t){this.multipleSelection=t},handleTransportDetail:function(t,e){this.$router.push({path:"/business/waybill-detail",query:{id:t,orderId:e.order&&e.order.id,type:this.$route.query.type}})},getList:function(){var t=Object(i["a"])(regeneratorRuntime.mark((function t(e){var a,n;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return this.listLoading=!0,t.next=3,Object(s["b"])(e);case 3:a=t.sent,n=a.data,this.listLoading=!1,null!=n.transportOrders&&(this.dataList=n.transportOrders,this.transportOrderCount=n.transportOrderCount,this.total=n.counts);case 7:case"end":return t.stop()}}),t,this)})));function e(e){return t.apply(this,arguments)}return e}(),handleTaskList:function(){this.$router.push({path:"/transport/transport-task"})}}},c=o,l=(a("12d8"),a("cba8")),u=Object(l["a"])(c,n,r,!1,null,"c74af998",null);e["default"]=u.exports},c3ae:function(t,e,a){"use strict";function n(t,e,a,n,r,i,s){try{var o=t[i](s),c=o.value}catch(l){return void a(l)}o.done?e(c):Promise.resolve(c).then(n,r)}function r(t){return function(){var e=this,a=arguments;return new Promise((function(r,i){var s=t.apply(e,a);function o(t){n(s,r,i,o,c,"next",t)}function c(t){n(s,r,i,o,c,"throw",t)}o(void 0)}))}}a.d(e,"a",(function(){return r}))},cd79:function(t,e,a){},d23d:function(t,e,a){"use strict";a("9c02")},d614:function(t,e,a){},e186:function(t,e,a){var n=function(t){"use strict";var e,a=Object.prototype,n=a.hasOwnProperty,r="function"===typeof Symbol?Symbol:{},i=r.iterator||"@@iterator",s=r.asyncIterator||"@@asyncIterator",o=r.toStringTag||"@@toStringTag";function c(t,e,a){return Object.defineProperty(t,e,{value:a,enumerable:!0,configurable:!0,writable:!0}),t[e]}try{c({},"")}catch(j){c=function(t,e,a){return t[e]=a}}function l(t,e,a,n){var r=e&&e.prototype instanceof m?e:m,i=Object.create(r.prototype),s=new T(n||[]);return i._invoke=C(t,a,s),i}function u(t,e,a){try{return{type:"normal",arg:t.call(e,a)}}catch(j){return{type:"throw",arg:j}}}t.wrap=l;var d="suspendedStart",p="suspendedYield",h="executing",f="completed",v={};function m(){}function g(){}function b(){}var y={};c(y,i,(function(){return this}));var w=Object.getPrototypeOf,x=w&&w(w(S([])));x&&x!==a&&n.call(x,i)&&(y=x);var k=b.prototype=m.prototype=Object.create(y);function _(t){["next","throw","return"].forEach((function(e){c(t,e,(function(t){return this._invoke(e,t)}))}))}function L(t,e){function a(r,i,s,o){var c=u(t[r],t,i);if("throw"!==c.type){var l=c.arg,d=l.value;return d&&"object"===typeof d&&n.call(d,"__await")?e.resolve(d.__await).then((function(t){a("next",t,s,o)}),(function(t){a("throw",t,s,o)})):e.resolve(d).then((function(t){l.value=t,s(l)}),(function(t){return a("throw",t,s,o)}))}o(c.arg)}var r;function i(t,n){function i(){return new e((function(e,r){a(t,n,e,r)}))}return r=r?r.then(i,i):i()}this._invoke=i}function C(t,e,a){var n=d;return function(r,i){if(n===h)throw new Error("Generator is already running");if(n===f){if("throw"===r)throw i;return N()}a.method=r,a.arg=i;while(1){var s=a.delegate;if(s){var o=P(s,a);if(o){if(o===v)continue;return o}}if("next"===a.method)a.sent=a._sent=a.arg;else if("throw"===a.method){if(n===d)throw n=f,a.arg;a.dispatchException(a.arg)}else"return"===a.method&&a.abrupt("return",a.arg);n=h;var c=u(t,e,a);if("normal"===c.type){if(n=a.done?f:p,c.arg===v)continue;return{value:c.arg,done:a.done}}"throw"===c.type&&(n=f,a.method="throw",a.arg=c.arg)}}}function P(t,a){var n=t.iterator[a.method];if(n===e){if(a.delegate=null,"throw"===a.method){if(t.iterator["return"]&&(a.method="return",a.arg=e,P(t,a),"throw"===a.method))return v;a.method="throw",a.arg=new TypeError("The iterator does not provide a 'throw' method")}return v}var r=u(n,t.iterator,a.arg);if("throw"===r.type)return a.method="throw",a.arg=r.arg,a.delegate=null,v;var i=r.arg;return i?i.done?(a[t.resultName]=i.value,a.next=t.nextLoc,"return"!==a.method&&(a.method="next",a.arg=e),a.delegate=null,v):i:(a.method="throw",a.arg=new TypeError("iterator result is not an object"),a.delegate=null,v)}function A(t){var e={tryLoc:t[0]};1 in t&&(e.catchLoc=t[1]),2 in t&&(e.finallyLoc=t[2],e.afterLoc=t[3]),this.tryEntries.push(e)}function O(t){var e=t.completion||{};e.type="normal",delete e.arg,t.completion=e}function T(t){this.tryEntries=[{tryLoc:"root"}],t.forEach(A,this),this.reset(!0)}function S(t){if(t){var a=t[i];if(a)return a.call(t);if("function"===typeof t.next)return t;if(!isNaN(t.length)){var r=-1,s=function a(){while(++r<t.length)if(n.call(t,r))return a.value=t[r],a.done=!1,a;return a.value=e,a.done=!0,a};return s.next=s}}return{next:N}}function N(){return{value:e,done:!0}}return g.prototype=b,c(k,"constructor",b),c(b,"constructor",g),g.displayName=c(b,o,"GeneratorFunction"),t.isGeneratorFunction=function(t){var e="function"===typeof t&&t.constructor;return!!e&&(e===g||"GeneratorFunction"===(e.displayName||e.name))},t.mark=function(t){return Object.setPrototypeOf?Object.setPrototypeOf(t,b):(t.__proto__=b,c(t,o,"GeneratorFunction")),t.prototype=Object.create(k),t},t.awrap=function(t){return{__await:t}},_(L.prototype),c(L.prototype,s,(function(){return this})),t.AsyncIterator=L,t.async=function(e,a,n,r,i){void 0===i&&(i=Promise);var s=new L(l(e,a,n,r),i);return t.isGeneratorFunction(a)?s:s.next().then((function(t){return t.done?t.value:s.next()}))},_(k),c(k,o,"Generator"),c(k,i,(function(){return this})),c(k,"toString",(function(){return"[object Generator]"})),t.keys=function(t){var e=[];for(var a in t)e.push(a);return e.reverse(),function a(){while(e.length){var n=e.pop();if(n in t)return a.value=n,a.done=!1,a}return a.done=!0,a}},t.values=S,T.prototype={constructor:T,reset:function(t){if(this.prev=0,this.next=0,this.sent=this._sent=e,this.done=!1,this.delegate=null,this.method="next",this.arg=e,this.tryEntries.forEach(O),!t)for(var a in this)"t"===a.charAt(0)&&n.call(this,a)&&!isNaN(+a.slice(1))&&(this[a]=e)},stop:function(){this.done=!0;var t=this.tryEntries[0],e=t.completion;if("throw"===e.type)throw e.arg;return this.rval},dispatchException:function(t){if(this.done)throw t;var a=this;function r(n,r){return o.type="throw",o.arg=t,a.next=n,r&&(a.method="next",a.arg=e),!!r}for(var i=this.tryEntries.length-1;i>=0;--i){var s=this.tryEntries[i],o=s.completion;if("root"===s.tryLoc)return r("end");if(s.tryLoc<=this.prev){var c=n.call(s,"catchLoc"),l=n.call(s,"finallyLoc");if(c&&l){if(this.prev<s.catchLoc)return r(s.catchLoc,!0);if(this.prev<s.finallyLoc)return r(s.finallyLoc)}else if(c){if(this.prev<s.catchLoc)return r(s.catchLoc,!0)}else{if(!l)throw new Error("try statement without catch or finally");if(this.prev<s.finallyLoc)return r(s.finallyLoc)}}}},abrupt:function(t,e){for(var a=this.tryEntries.length-1;a>=0;--a){var r=this.tryEntries[a];if(r.tryLoc<=this.prev&&n.call(r,"finallyLoc")&&this.prev<r.finallyLoc){var i=r;break}}i&&("break"===t||"continue"===t)&&i.tryLoc<=e&&e<=i.finallyLoc&&(i=null);var s=i?i.completion:{};return s.type=t,s.arg=e,i?(this.method="next",this.next=i.finallyLoc,v):this.complete(s)},complete:function(t,e){if("throw"===t.type)throw t.arg;return"break"===t.type||"continue"===t.type?this.next=t.arg:"return"===t.type?(this.rval=this.arg=t.arg,this.method="return",this.next="end"):"normal"===t.type&&e&&(this.next=e),v},finish:function(t){for(var e=this.tryEntries.length-1;e>=0;--e){var a=this.tryEntries[e];if(a.finallyLoc===t)return this.complete(a.completion,a.afterLoc),O(a),v}},catch:function(t){for(var e=this.tryEntries.length-1;e>=0;--e){var a=this.tryEntries[e];if(a.tryLoc===t){var n=a.completion;if("throw"===n.type){var r=n.arg;O(a)}return r}}throw new Error("illegal catch attempt")},delegateYield:function(t,a,n){return this.delegate={iterator:S(t),resultName:a,nextLoc:n},"next"===this.method&&(this.arg=e),v}},t}(t.exports);try{regeneratorRuntime=n}catch(r){"object"===typeof globalThis?globalThis.regeneratorRuntime=n:Function("r","regeneratorRuntime = r")(n)}},e29b:function(t,e,a){"use strict";a.r(e);var n=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("el-container",{staticClass:"customer-details-box"},[n("el-card",{staticStyle:{width:"100%"},attrs:{shadow:"never"}},[n("div",{staticClass:"block"},[n("div",{staticClass:"car-base mission-locus"},[t._v("任务轨迹")]),t._v(" "),n("el-card",{staticClass:"form-box",attrs:{shadow:"never"}},[n("div",{staticClass:"form-item-box"},[n("div",{staticClass:"form-item"},[n("span",{staticClass:"span-title"},[t._v("车牌号：")]),t._v(" "),n("el-input",{attrs:{disabled:t.disabled,placeholder:"请输入车牌号"},model:{value:t.missionLocus.licensePlate,callback:function(e){t.$set(t.missionLocus,"licensePlate",e)},expression:"missionLocus.licensePlate"}})],1),t._v(" "),n("div",{staticClass:"form-item"},[n("span",{staticClass:"span-title"},[t._v("司机：")]),t._v(" "),n("el-input",{attrs:{disabled:t.disabled,placeholder:"请输入司机"},model:{value:t.missionLocus.driverName,callback:function(e){t.$set(t.missionLocus,"driverName",e)},expression:"missionLocus.driverName"}})],1),t._v(" "),n("div",{staticClass:"form-item"},[n("span",{staticClass:"span-title"},[t._v("计划发车时间：")]),t._v(" "),n("el-input",{attrs:{disabled:t.disabled,placeholder:"请输入计划发车时间"},model:{value:t.missionLocus.planDepartureTime,callback:function(e){t.$set(t.missionLocus,"planDepartureTime",e)},expression:"missionLocus.planDepartureTime"}})],1),t._v(" "),n("div",{staticClass:"form-item"},[n("span",{staticClass:"span-title"},[t._v("实际发车时间：")]),t._v(" "),n("el-input",{attrs:{disabled:t.disabled,placeholder:"请输入实际发车时间"},model:{value:t.missionLocus.actualDepartureTime,callback:function(e){t.$set(t.missionLocus,"actualDepartureTime",e)},expression:"missionLocus.actualDepartureTime"}})],1),t._v(" "),n("div",{staticClass:"form-item"},[n("span",{staticClass:"span-title"},[t._v("计划到达时间：")]),t._v(" "),n("el-input",{attrs:{disabled:t.disabled,placeholder:"请输入计划到达时间"},model:{value:t.missionLocus.planArrivalTime,callback:function(e){t.$set(t.missionLocus,"planArrivalTime",e)},expression:"missionLocus.planArrivalTime"}})],1),t._v(" "),n("div",{staticClass:"form-item"},[n("span",{staticClass:"span-title"},[t._v("实际到达时间：")]),t._v(" "),n("el-input",{attrs:{disabled:t.disabled,placeholder:"请输入实际到达时间"},model:{value:t.missionLocus.actualArrivalTime,callback:function(e){t.$set(t.missionLocus,"actualArrivalTime",e)},expression:"missionLocus.actualArrivalTime"}})],1)])]),t._v(" "),n("div",{staticClass:"car-base car-img-base mission-locus map-btn-box"},[t._v("\n        轨迹查看\n        "),n("img",{staticClass:"refresh",attrs:{src:a("2298"),alt:""},on:{click:t.handleLocusPlace}})]),t._v(" "),n("baidu-map",{staticClass:"map",attrs:{center:{lng:116.404,lat:39.915},zoom:t.zoom,"scroll-wheel-zoom":t.scrollWheelZoom}},[n("bm-navigation",{attrs:{anchor:"BMAP_ANCHOR_TOP_RIGHT"}}),t._v(" "),t._l(t.pointsArr,(function(e,a){return n("bm-marker",{key:a,attrs:{position:e.markerPoint}},[n("bm-label",{attrs:{content:e.name,position:e.markerPoint,"label-style":t.labelStyle,title:e.licensePlate,offset:{width:-20,height:-28}}})],1)})),t._v(" "),n("bm-polyline",{attrs:{path:t.polylinePath,"stroke-color":"#0099CC","stroke-opacity":.5,"stroke-style":"soild","stroke-weight":6},on:{lineupdate:t.updatePolylinePath}}),t._v(" "),n("bml-lushu",{attrs:{path:t.polylinePath,icon:t.icon,play:t.play,speed:t.speed,"auto-view":!0}})],2),t._v(" "),n("div",{staticClass:"footer-box"},[n("el-button",{staticClass:"cancel-btn",on:{click:function(e){return t.handleTaskList()}}},[t._v("返回")])],1)],1)])],1)},r=[],i=(a("e186"),a("c3ae")),s=a("4878"),o=a("0a86"),c={components:{BmlLushu:s["BmlLushu"],BmPolyline:s["BmPolyline"]},data:function(){return{scrollWheelZoom:!0,polylinePath:[],pointsArr:[],zoom:6,speed:1e4,disabled:!0,play:!0,infoWindow:!0,queryParams:{},content1:"京A6666611",labelStyle:{padding:"5px",border:"1px solid #ccc",borderRadius:"10px",fontSize:"12px",backgroundColor:"#7BA6FF",color:"#fff"},rotation:!0,icon:{url:"https://developer.baidu.com/map/jsdemo/img/car.png",size:{width:52,height:26},opts:{anchor:{width:27,height:13}}},missionLocus:{licensePlate:"",driverName:"",planDepartureTime:"",actualDepartureTime:"",planArrivalTime:"",actualArrivalTime:""}}},created:function(){this.id=this.$route.query.id,this.getList(this.id)},methods:{handleTaskList:function(){this.$router.push({path:"/transport/transport-task"})},reset:function(){this.play=!1},handleLocusPlace:function(){this.getList(this.id)},handleFilter:function(){},updatePolylinePath:function(t){this.polylinePath=t.target.getPath()},addPolylinePoint:function(){this.polylinePath.push({lng:116.404,lat:39.915})},handleSearchComplete:function(t){this.polylinePath=t.getPlan(0).getRoute(0).getPath()},goBack:function(){},getList:function(){var t=Object(i["a"])(regeneratorRuntime.mark((function t(e){var a,n,r,i,s,c;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return t.next=2,Object(o["b"])(e);case 2:return a=t.sent,n=a.data,null!=n.truck&&(this.missionLocus.licensePlate=n.truck.licensePlate),this.missionLocus.driverName=n.driverName,this.missionLocus.planDepartureTime=n.planDepartureTime,this.missionLocus.actualDepartureTime=n.actualDepartureTime,this.missionLocus.planArrivalTime=n.planArrivalTime,this.missionLocus.actualArrivalTime=n.actualArrivalTime,this.queryParams.transportTaskId=e,t.next=13,Object(o["e"])(this.queryParams);case 13:return r=t.sent,i=r.data,this.polylinePath=i.data.polyLinePath,t.next=18,Object(o["f"])(e);case 18:s=t.sent,c=s.data,this.pointsArr=c;case 21:case"end":return t.stop()}}),t,this)})));function e(e){return t.apply(this,arguments)}return e}()}},l=c,u=(a("21d8"),a("cba8")),d=Object(u["a"])(l,n,r,!1,null,"0fca34c5",null);e["default"]=d.exports}}]);