(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-6578aa0c"],{2934:function(e,t,r){"use strict";r.d(t,"e",(function(){return a})),r.d(t,"g",(function(){return o})),r.d(t,"f",(function(){return i})),r.d(t,"c",(function(){return s})),r.d(t,"d",(function(){return c})),r.d(t,"a",(function(){return l}));var n=r("b775"),a=function(e){return Object(n["a"])("/web-manager/common/transportLineType/simple","get",e)},o=function(e){return Object(n["a"])("/truckType/simple","get",e)},i=function(e){return Object(n["a"])("/common/user/simple","get",e)},s=function(e){return Object(n["a"])("/web-manager/common/fleet/simple","get",e)},c=function(e){return Object(n["a"])("/goodsType/simple","get",e)},l=function(e){return Object(n["a"])("/areas/children","get",e)}},"2a95":function(e,t,r){"use strict";r.r(t);var n=function(){var e=this,t=e.$createElement,r=e._self._c||t;return r("div",[r("el-dialog",{attrs:{title:e.titleInfo.text,visible:e.dialogFormVisible,"close-on-click-modal":!1,"before-close":e.dialogFormH,width:"600px"},on:{"update:visible":function(t){e.dialogFormVisible=t}}},[r("el-form",{ref:"dataForm",staticStyle:{width:"100%"},attrs:{rules:e.ruleInline,model:e.formBase,"label-position":"right","label-width":"130px"}},[r("el-form-item",{attrs:{label:"车辆类型",prop:"name"}},[r("el-input",{attrs:{placeholder:"请输入车辆类型",maxlength:"15"},model:{value:e.formBase.name,callback:function(t){e.$set(e.formBase,"name",t)},expression:"formBase.name"}})],1),e._v(" "),r("el-form-item",{attrs:{label:"实载重量",prop:"allowableLoad"}},[r("el-input",{attrs:{placeholder:"请输入车型载重",maxlength:"10"},model:{value:e.formBase.allowableLoad,callback:function(t){e.$set(e.formBase,"allowableLoad",t)},expression:"formBase.allowableLoad"}},[r("span",{staticStyle:{color:"#20232a","font-weight":"400","font-size":"14px","font-family":"PingFangSC, PingFangSC-Regular","margin-right":"14px"},attrs:{slot:"suffix"},slot:"suffix"},[e._v("吨")])])],1),e._v(" "),r("el-form-item",{attrs:{label:"实载体积",prop:"allowableVolume"}},[r("el-input",{attrs:{placeholder:"请输入车型体积",maxlength:"10"},model:{value:e.formBase.allowableVolume,callback:function(t){e.$set(e.formBase,"allowableVolume",t)},expression:"formBase.allowableVolume"}},[r("span",{staticStyle:{color:"#20232a","font-weight":"400","font-size":"14px","font-family":"PingFangSC, PingFangSC-Regular","margin-right":"14px"},attrs:{slot:"suffix"},slot:"suffix"},[e._v("立方")])])],1),e._v(" "),r("el-form-item",{attrs:{label:"长",prop:"measureLong"}},[r("el-input",{attrs:{placeholder:"请输入长",maxlength:"10"},model:{value:e.formBase.measureLong,callback:function(t){e.$set(e.formBase,"measureLong",t)},expression:"formBase.measureLong"}},[r("span",{staticStyle:{color:"#20232a","font-weight":"400","font-size":"14px","font-family":"PingFangSC, PingFangSC-Regular","margin-right":"14px"},attrs:{slot:"suffix"},slot:"suffix"},[e._v("米")])])],1),e._v(" "),r("el-form-item",{attrs:{label:"宽",prop:"measureWidth"}},[r("el-input",{attrs:{placeholder:"请输入宽",maxlength:"10"},model:{value:e.formBase.measureWidth,callback:function(t){e.$set(e.formBase,"measureWidth",t)},expression:"formBase.measureWidth"}},[r("span",{staticStyle:{color:"#20232a","font-weight":"400","font-size":"14px","font-family":"PingFangSC, PingFangSC-Regular","margin-right":"14px"},attrs:{slot:"suffix"},slot:"suffix"},[e._v("米")])])],1),e._v(" "),r("el-form-item",{attrs:{label:"高",prop:"measureHigh"}},[r("el-input",{attrs:{placeholder:"请输入高",maxlength:"10",max:100},model:{value:e.formBase.measureHigh,callback:function(t){e.$set(e.formBase,"measureHigh",t)},expression:"formBase.measureHigh"}},[r("span",{staticStyle:{color:"#20232a","font-weight":"400","font-size":"14px","font-family":"PingFangSC, PingFangSC-Regular","margin-right":"14px"},attrs:{slot:"suffix"},slot:"suffix"},[e._v("米")])])],1),e._v(" "),r("el-form-item",{attrs:{label:"适用货品类型",prop:"goodsTypes"}},["新增车型"===e.titleInfo.text?[r("el-select",{ref:"selectInput",staticStyle:{width:"100%"},attrs:{"value-key":"id",placeholder:"请选择适用货品类型",clearable:"",multiple:""},model:{value:e.formBase.goodsTypes,callback:function(t){e.$set(e.formBase,"goodsTypes",t)},expression:"formBase.goodsTypes"}},e._l(e.options,(function(e){return r("el-option",{key:e.id,attrs:{label:e.name,value:e}})})),1)]:e._e(),e._v(" "),"编辑车型"===e.titleInfo.text?[e.selectShow?e._e():r("el-input",{attrs:{"suffix-icon":"el-icon-arrow-down"},on:{focus:e.getSelectShowFn},model:{value:e.getallGoodsTypes,callback:function(t){e.getallGoodsTypes=t},expression:"getallGoodsTypes"}}),e._v(" "),e.selectShow?r("el-select",{ref:"selectInput",staticStyle:{width:"100%"},attrs:{"value-key":"id",placeholder:"请选择适用货品类型",clearable:"",multiple:""},model:{value:e.formBase.goodsTypes,callback:function(t){e.$set(e.formBase,"goodsTypes",t)},expression:"formBase.goodsTypes"}},e._l(e.options,(function(e){return r("el-option",{key:e.id,attrs:{label:e.name,value:e}})})),1):e._e()]:e._e()],2)],1),e._v(" "),r("div",{staticClass:"dialog-footer",staticStyle:{"text-align":"center"},attrs:{slot:"footer"},slot:"footer"},[r("el-button",{staticClass:"save-btn",on:{click:e.createData}},[e._v("确认")]),e._v(" "),r("el-button",{staticClass:"cancel-btn",on:{click:e.dialogFormH}},[e._v("取消")])],1)],1)],1)},a=[],o=(r("e186"),r("c3ae")),i=(r("a450"),r("e680"),r("7b11")),s=r("2934"),c={name:"CarModelsAdd",props:{titleInfo:{type:Object,required:!0},formBase:{type:Object,required:!0}},data:function(){return{selectShow:!1,options:[],requestParameters:{page:1,pageSize:10},dialogFormVisible:!1,ruleInline:{name:[{required:!0,message:"车型名称不能为空",trigger:"blur"}],goodsTypes:[{required:!0,message:"货品类型不能为空",trigger:"blur"}],allowableLoad:[{required:!0,validator:function(e,t,r){if(t){var n=/^[1-9]\d*$/;n.test(Number(t))?t>100||t<1?r(new Error("只可输入1-100之间的整数")):r():r(new Error("只能输入1-100之间的正整数，请重新输入"))}else r(new Error("实载重量不能为空"))},trigger:"blur"}],allowableVolume:[{required:!0,validator:function(e,t,r){if(t){var n=/^[1-9]\d*$/;n.test(t)?t>100||t<1?r(new Error("只可输入1-100之间的整数")):r():r(new Error("只能输入1-100之间的正整数，请重新输入"))}else r(new Error("实载体积不能为空"))},trigger:"blur"}],measureLong:[{required:!1,validator:function(e,t,r){if(t){var n=/^[0-9]+(.[0-9]{1,2})?$/;n.test(t)?t>100?r(new Error("长最多只能输入100，请重新输入")):r():r(new Error("只能输入数字类型，最多保留两位小数，请重新输入"))}else r()},trigger:"blur"}],measureWidth:[{required:!1,validator:function(e,t,r){if(t){var n=/^[0-9]+(.[0-9]{1,2})?$/;n.test(t)?t>100?r(new Error("宽最多只能输入100，请重新输入")):r():r(new Error("只能输入数字类型，最多保留两位小数，请重新输入"))}else r()},trigger:"blur"}],measureHigh:[{required:!1,validator:function(e,t,r){if(t){var n=/^[0-9]+(.[0-9]{1,2})?$/;n.test(t)?t>100?r(new Error("高最多只能输入100，请重新输入")):r():r(new Error("只能输入数字类型，最多保留两位小数，请重新输入"))}else r()},trigger:"blur"}]}}},computed:{getallGoodsTypes:function(){return this.formBase.goodsTypes.map((function(e){return e.name})).join("、")}},created:function(){this.initialData(),console.log(this.formBase.measureHigh,"this")},updated:function(){},methods:{getSelectShowFn:function(){this.selectShow=!0},initialData:function(){this.getGoodsTypeList()},getGoodsTypeList:function(){var e=Object(o["a"])(regeneratorRuntime.mark((function e(){var t,r;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:return e.next=2,Object(s["d"])();case 2:t=e.sent,r=t.data,this.options=r.filter((function(e){return 0!==e.status}));case 5:case"end":return e.stop()}}),e,this)})));function t(){return e.apply(this,arguments)}return t}(),dialogFormV:function(){this.dialogFormVisible=!0},dialogFormH:function(){this.dialogFormVisible=!1,this.selectShow=!1,this.$refs["dataForm"].resetFields()},createData:function(){var e=this;this.$refs["dataForm"].validate(function(){var t=Object(o["a"])(regeneratorRuntime.mark((function t(r){var n;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:if(!r){t.next=13;break}if(n={name:e.formBase.name,allowableLoad:e.formBase.allowableLoad,allowableVolume:e.formBase.allowableVolume,measureHigh:e.formBase.measureHigh,measureLong:e.formBase.measureLong,measureWidth:e.formBase.measureWidth,goodsTypes:e.formBase.goodsTypes},console.log(1),!e.formBase.id){t.next=8;break}return t.next=6,Object(i["hb"])(e.formBase.id,n).then((function(t){console.log(t,"update"),"200"===String(t.code)&&(e.$message({message:"操作成功！",type:"success"}),e.$emit("newDataes",e.formBase),e.dialogFormH())}));case 6:t.next=10;break;case 8:return t.next=10,Object(i["a"])(n).then((function(t){"200"===String(t.code)?(e.$message({message:"操作成功！",type:"success"}),e.$emit("newDataes",e.newDataes),e.dialogFormH()):e.$message({message:t.msg,type:"error"})}));case 10:e.selectShow=!1,t.next=15;break;case 13:console.log(2),e.$message.error("*号为必填项!");case 15:case"end":return t.stop()}}),t)})));return function(e){return t.apply(this,arguments)}}())}}},l=c,u=r("cba8"),f=Object(u["a"])(l,n,a,!1,null,null,null);t["default"]=f.exports},"7b11":function(e,t,r){"use strict";r.d(t,"A",(function(){return a})),r.d(t,"z",(function(){return o})),r.d(t,"M",(function(){return i})),r.d(t,"h",(function(){return s})),r.d(t,"a",(function(){return c})),r.d(t,"hb",(function(){return l})),r.d(t,"f",(function(){return u})),r.d(t,"u",(function(){return f})),r.d(t,"r",(function(){return d})),r.d(t,"s",(function(){return h})),r.d(t,"t",(function(){return m})),r.d(t,"v",(function(){return p})),r.d(t,"B",(function(){return g})),r.d(t,"p",(function(){return b})),r.d(t,"gb",(function(){return v})),r.d(t,"l",(function(){return w})),r.d(t,"k",(function(){return y})),r.d(t,"m",(function(){return x})),r.d(t,"n",(function(){return L})),r.d(t,"o",(function(){return S})),r.d(t,"q",(function(){return j})),r.d(t,"c",(function(){return _})),r.d(t,"j",(function(){return O})),r.d(t,"e",(function(){return k})),r.d(t,"V",(function(){return F})),r.d(t,"bb",(function(){return D})),r.d(t,"cb",(function(){return T})),r.d(t,"fb",(function(){return M})),r.d(t,"eb",(function(){return $})),r.d(t,"db",(function(){return B})),r.d(t,"ab",(function(){return E})),r.d(t,"U",(function(){return C})),r.d(t,"Z",(function(){return V})),r.d(t,"Y",(function(){return R})),r.d(t,"J",(function(){return N})),r.d(t,"I",(function(){return P})),r.d(t,"K",(function(){return I})),r.d(t,"G",(function(){return H})),r.d(t,"H",(function(){return z})),r.d(t,"F",(function(){return G})),r.d(t,"C",(function(){return q})),r.d(t,"D",(function(){return W})),r.d(t,"E",(function(){return A})),r.d(t,"L",(function(){return J})),r.d(t,"S",(function(){return K})),r.d(t,"P",(function(){return U})),r.d(t,"T",(function(){return Y})),r.d(t,"R",(function(){return Q})),r.d(t,"Q",(function(){return X})),r.d(t,"W",(function(){return Z})),r.d(t,"X",(function(){return ee})),r.d(t,"x",(function(){return te})),r.d(t,"y",(function(){return re})),r.d(t,"w",(function(){return ne})),r.d(t,"b",(function(){return ae})),r.d(t,"ib",(function(){return oe})),r.d(t,"kb",(function(){return ie})),r.d(t,"O",(function(){return se})),r.d(t,"jb",(function(){return ce})),r.d(t,"g",(function(){return le})),r.d(t,"lb",(function(){return ue})),r.d(t,"N",(function(){return fe})),r.d(t,"i",(function(){return de})),r.d(t,"d",(function(){return he}));var n=r("b775"),a=function(e){return Object(n["a"])("/count","get",e)},o=function(e){return Object(n["a"])("/truckType/simple","get",e)},i=function(e){return Object(n["a"])("/truckType/page","get",e)},s=function(e){return Object(n["a"])("/truckType/".concat(e),"get",e)},c=function(e){return Object(n["a"])("/truckType","post",e)},l=function(e,t){return Object(n["a"])("/truckType/".concat(e),"put",t)},u=function(e){return Object(n["a"])("/truckType/".concat(e),"delete",e)},f=function(e){return Object(n["a"])("/web-manager/transfor-center/bussiness/fleet/page","get",e)},d=function(e){return Object(n["a"])("/web-manager/transfor-center/bussiness/fleet","post",e)},h=function(e){return Object(n["a"])("/web-manager/transfor-center/bussiness/fleet/".concat(e),"delete",e)},m=function(e){return Object(n["a"])("/web-manager/transfor-center/bussiness/fleet/".concat(e),"get",e)},p=function(e,t){return Object(n["a"])("/web-manager/transfor-center/bussiness/fleet/".concat(e),"put",t)},g=function(e){return Object(n["a"])("/bindingDrivers/".concat(e),"get",e)},b=function(e){return Object(n["a"])("/driver/page","get",e)},v=function(e){return Object(n["a"])("/unBindingDrivers/","get",e)},w=function(e){return Object(n["a"])("/driver/".concat(e),"get",e)},y=function(e,t){return Object(n["a"])("/web-manager/transfor-center/bussiness/driver/".concat(e),"put",t)},x=function(e,t){return Object(n["a"])("/driver/".concat(e),"put",t)},L=function(e){return Object(n["a"])("/driverLicense/".concat(e),"get",e)},S=function(e){return Object(n["a"])("/driverLicense","post",e)},j=function(e){return Object(n["a"])("/web-manager/transfor-center/bussiness/driver/".concat(e,"/truck"),"get",e)},_=function(e){return Object(n["a"])("/transportLine/trips/".concat(e.transportTripsId,"/truckDrivers"),"post",e)},O=function(e){return Object(n["a"])("/driver/truckDrivers","post",e)},k=function(e){return Object(n["a"])("/truck/truckDrivers","post",e)},F=function(e){return Object(n["a"])("/truck/page","get",e)},D=function(e){return Object(n["a"])("/truck","post",e)},T=function(e){return Object(n["a"])("/truck/".concat(e),"get",e)},M=function(e,t){return Object(n["a"])("/truck/".concat(e),"put",t)},$=function(e){return Object(n["a"])("/truck/".concat(e,"/license"),"get",e)},B=function(e,t){return Object(n["a"])("/truck/".concat(e,"/license"),"post",t)},E=function(e){return Object(n["a"])("/truck/".concat(e,"/transportTrips"),"get",e)},C=function(e){return Object(n["a"])("/del/".concat(e),"delete",e)},V=function(e){return Object(n["a"])("/enable/".concat(e),"PUT",e)},R=function(e){return Object(n["a"])("/disable/".concat(e),"PUT",e)},N=function(e){return Object(n["a"])("/web-manager/transfor-center/bussiness/transportLineType/page","get",e)},P=function(e){return Object(n["a"])("/web-manager/transfor-center/bussiness/transportLineType/".concat(e),"get",e)},I=function(e,t){return Object(n["a"])("/web-manager/transfor-center/bussiness/transportLineType/".concat(e),"put",t)},H=function(e){return Object(n["a"])("/web-manager/transfor-center/bussiness/transportLineType","post",e)},z=function(e){return Object(n["a"])("/web-manager/transfor-center/bussiness/transportLineType/".concat(e),"delete",e)},G=function(e){return Object(n["a"])("/transportLine/page","post",e)},q=function(e){return Object(n["a"])("/transportLine","post",e)},W=function(e){return Object(n["a"])("/transportLine/".concat(e),"delete",e)},A=function(e){return Object(n["a"])("/transportLine/".concat(e),"get",e)},J=function(e,t){return Object(n["a"])("/transportLine/".concat(e),"put",t)},K=function(e){return Object(n["a"])("/transportLine/trips","get",e)},U=function(e){return Object(n["a"])("/transportLine/trips","post",e)},Y=function(e,t){return Object(n["a"])("/transportLine/trips/".concat(e),"put",t)},Q=function(e){return Object(n["a"])("/transportLine/trips/".concat(e),"get",e)},X=function(e){return Object(n["a"])("/transportLine/trips/".concat(e),"delete",e)},Z=function(e){return Object(n["a"])("/web-druid/apache-druid/query/select","get",e)},ee=function(e){return Object(n["a"])("/web-manager/transfor-center/truck-place-info/".concat(e),"get",e)},te=function(e){return Object(n["a"])("/carriages","get",e)},re=function(e){return Object(n["a"])("/carriages","post",e)},ne=function(e){return Object(n["a"])("/carriages/".concat(e),"delete",e)},ae=function(e){return Object(n["a"])("/work-patterns","post",e)},oe=function(e){return Object(n["a"])("/work-patterns","put",e)},ie=function(e){return Object(n["a"])("/work-patterns/page","get",e)},se=function(e){return Object(n["a"])("/work-patterns/all","get",e)},ce=function(e){return Object(n["a"])("/work-patterns/".concat(e),"get",e)},le=function(e){return Object(n["a"])("/work-patterns/".concat(e),"delete",e)},ue=function(e){return Object(n["a"])("/work-schedulings","get",e)},fe=function(e){return Object(n["a"])("/work-schedulings","put",e)},de=function(e){return Object(n["a"])("/work-schedulings/downExcelTemplate","get",e,"blob")},he=function(e){return Object(n["a"])("/work-schedulings/uploadExcel","post",e)}},9038:function(e,t,r){"use strict";r("c9ba")},c3ae:function(e,t,r){"use strict";function n(e,t,r,n,a,o,i){try{var s=e[o](i),c=s.value}catch(l){return void r(l)}s.done?t(c):Promise.resolve(c).then(n,a)}function a(e){return function(){var t=this,r=arguments;return new Promise((function(a,o){var i=e.apply(t,r);function s(e){n(i,a,o,s,c,"next",e)}function c(e){n(i,a,o,s,c,"throw",e)}s(void 0)}))}}r.d(t,"a",(function(){return a}))},c9ba:function(e,t,r){},ce36:function(e,t,r){"use strict";r.r(t);var n=function(){var e=this,t=e.$createElement,r=e._self._c||t;return r("div",{staticClass:"dashboard-container car-models customer-list-box"},[r("div",{staticClass:"app-container"},[r("el-card",{staticClass:"search-card-box",attrs:{shadow:"never"}},[r("el-form",{ref:"carModelsSearchFormData",attrs:{model:e.carModelsSearchFormData,"label-width":"120px"}},[r("el-row",{attrs:{gutter:60}},[r("el-col",{attrs:{span:8}},[r("el-form-item",{attrs:{label:"车型编号:",prop:"id"}},[r("el-input",{attrs:{placeholder:"请输入车型编号",clearable:""},model:{value:e.carModelsSearchFormData.id,callback:function(t){e.$set(e.carModelsSearchFormData,"id",t)},expression:"carModelsSearchFormData.id"}})],1)],1),e._v(" "),r("el-col",{attrs:{span:8}},[r("el-form-item",{attrs:{label:"车型载重:",prop:"allowableLoad"}},[r("el-input",{attrs:{placeholder:"请输入车型载重",clearable:""},model:{value:e.carModelsSearchFormData.allowableLoad,callback:function(t){e.$set(e.carModelsSearchFormData,"allowableLoad",t)},expression:"carModelsSearchFormData.allowableLoad"}})],1)],1),e._v(" "),r("el-col",{attrs:{span:8}},[r("el-form-item",{attrs:{label:"车型体积:",prop:"allowableVolume"}},[r("el-input",{attrs:{placeholder:"请输入车型体积",clearable:""},model:{value:e.carModelsSearchFormData.allowableVolume,callback:function(t){e.$set(e.carModelsSearchFormData,"allowableVolume",t)},expression:"carModelsSearchFormData.allowableVolume"}})],1)],1),e._v(" "),r("el-col",{attrs:{span:8}},[r("el-form-item",{staticStyle:{"margin-bottom":"0px"},attrs:{label:"车辆类型:",prop:"name"}},[r("el-select",{attrs:{placeholder:"请选择车辆类型"},model:{value:e.carModelsSearchFormData.name,callback:function(t){e.$set(e.carModelsSearchFormData,"name",t)},expression:"carModelsSearchFormData.name"}},e._l(e.carTypeList,(function(e){return r("el-option",{key:e.id,attrs:{label:e.name,value:e.name}})})),1)],1)],1),e._v(" "),r("el-col",{attrs:{span:8}},[r("el-button",{attrs:{type:"warning"},on:{click:e.handleFilter}},[e._v("搜索")]),e._v(" "),r("el-button",{staticClass:"reset-btn",attrs:{plain:""},on:{click:function(t){return e.resetForm("carModelsSearchFormData")}}},[e._v("重置")])],1)],1)],1)],1),e._v(" "),r("el-card",{directives:[{name:"loading",rawName:"v-loading",value:e.listLoading,expression:"listLoading"}],staticClass:"table-card-box",class:{"table-empty-box":!e.dataList||e.dataList.length<=0,"loading-box":e.listLoading},staticStyle:{"margin-top":"20px"},attrs:{"element-loading-text":"加载中",shadow:"never"}},[r("el-button",{staticClass:"customer-add-btn",attrs:{type:"warning"},on:{click:function(t){return e.handleAddModels()}}},[e._v("+新增车型")]),e._v(" "),r("el-table",{directives:[{name:"show",rawName:"v-show",value:e.dataList&&e.dataList.length>0,expression:"dataList && dataList.length > 0"}],key:e.tableKey,ref:"multipleTable",staticStyle:{width:"100%"},attrs:{data:e.dataList,fit:"",stripe:"","header-cell-style":{background:"rgba(250,252,255,1)"}},on:{"selection-change":e.handleSelectionChange}},[r("el-table-column",{attrs:{align:"left",type:"index",label:"序号",width:"60"},scopedSlots:e._u([{key:"default",fn:function(t){return[r("span",[e._v(e._s(t.$index+(e.carModelsSearchFormData.page-1)*e.carModelsSearchFormData.pageSize+1))])]}}])}),e._v(" "),r("el-table-column",{attrs:{align:"left",label:"车型编号","min-width":"180"},scopedSlots:e._u([{key:"default",fn:function(t){return[r("span",[e._v(e._s(t.row.id))])]}}])}),e._v(" "),r("el-table-column",{attrs:{align:"left",label:"车辆类型","min-width":"100"},scopedSlots:e._u([{key:"default",fn:function(t){return[r("span",[e._v(e._s(t.row.name))])]}}])}),e._v(" "),r("el-table-column",{attrs:{align:"left",label:"车辆数量","min-width":"100"},scopedSlots:e._u([{key:"default",fn:function(t){return[r("span",[e._v(e._s(t.row.num))])]}}])}),e._v(" "),r("el-table-column",{attrs:{align:"left",label:"实载重量（T）","min-width":"150"},scopedSlots:e._u([{key:"default",fn:function(t){return[r("span",[e._v(e._s(t.row.allowableLoad))])]}}])}),e._v(" "),r("el-table-column",{attrs:{align:"left",label:"实载体积（立方）","min-width":"150"},scopedSlots:e._u([{key:"default",fn:function(t){return[r("span",[e._v(e._s(t.row.allowableVolume))])]}}])}),e._v(" "),r("el-table-column",{attrs:{align:"left",label:"长（米）","min-width":"100"},scopedSlots:e._u([{key:"default",fn:function(t){return[r("span",[e._v(e._s(t.row.measureLong))])]}}])}),e._v(" "),r("el-table-column",{attrs:{align:"left",label:"宽（米）","min-width":"100"},scopedSlots:e._u([{key:"default",fn:function(t){return[r("span",[e._v(e._s(t.row.measureWidth))])]}}])}),e._v(" "),r("el-table-column",{attrs:{align:"left",label:"高（米）","min-width":"100"},scopedSlots:e._u([{key:"default",fn:function(t){return[r("span",[e._v(e._s(t.row.measureHigh))])]}}])}),e._v(" "),r("el-table-column",{attrs:{align:"left",label:"适用货品类型","min-width":"150"},scopedSlots:e._u([{key:"default",fn:function(t){return e._l(t.row.goodsTypes,(function(t,n){return r("span",{key:n},[e._v("\n              "+e._s(null===t?"":t.name)+"\n              ")])}))}}])}),e._v(" "),r("el-table-column",{attrs:{align:"center",label:"操作",fixed:"right",width:"110","class-name":"small-padding fixed-width"},scopedSlots:e._u([{key:"default",fn:function(t){return[r("el-link",{attrs:{type:"primary",underline:!1},on:{click:function(r){return e.handleAddModels(t.row.id)}}},[e._v("编辑")]),e._v(" "),r("el-divider",{attrs:{direction:"vertical"}}),e._v(" "),r("el-link",{attrs:{type:"danger",underline:!1},on:{click:function(r){return e.handleDelete(t.row.id,t.row.num)}}},[e._v("删除")])]}}])})],1),e._v(" "),r("div",{directives:[{name:"show",rawName:"v-show",value:e.dataList&&e.dataList.length>0,expression:"dataList && dataList.length > 0"}],staticClass:"pagination"},[r("div",{staticClass:"pages"},[r("el-pagination",{attrs:{"current-page":Number(e.carModelsSearchFormData.page),total:Number(e.total),"page-size":Number(e.carModelsSearchFormData.pageSize),"page-sizes":[10,20,30,50],layout:"total, sizes, prev, pager, next, jumper"},on:{"size-change":e.handleSizeChange,"current-change":e.handleCurrentChange}})],1)]),e._v(" "),r("carModelsDialog",{ref:"addCarModels",staticClass:"add-form-dialog add-form-dialog-car",attrs:{"title-info":e.titleInfo,"form-base":e.formData},on:{newDataes:e.getList}}),e._v(" "),r("empty",{directives:[{name:"show",rawName:"v-show",value:(!e.dataList||e.dataList.length<=0)&&!e.listLoading,expression:"(!dataList || dataList.length <= 0) && !listLoading"}]})],1)],1)])},a=[],o=(r("e680"),r("a450"),r("e186"),r("c3ae")),i=(r("4057"),r("7b11")),s=r("2a95"),c={components:{carModelsDialog:s["default"]},data:function(){return{carTypeList:[],dialogVisible:!1,handleDeleteId:"",value:"",multipleSelection:[],tableKey:0,dataList:[],total:null,listLoading:!0,alertText:"",carModelsSearchFormData:{page:1,pageSize:10},titleInfo:{pageTitle:"",text:""},formData:{name:"",allowableLoad:"",allowableVolume:"",measureHigh:"",measureLong:"",measureWidth:"",goodsTypes:[]}}},computed:{status:function(e){function t(){return e.apply(this,arguments)}return t.toString=function(){return e.toString()},t}((function(){return status}))},mounted:function(){},created:function(){var e=this;this.initialData(),document.onkeydown=function(t){var r=window.event.keyCode;13===r&&e.handleFilter(this.carModelsSearchFormData)}},updated:function(){},methods:{handleClose:function(){this.dialogVisible=!1},initialData:function(){this.clearField(),this.getList(),this.getCarTypeList()},getCarTypeList:function(){var e=Object(o["a"])(regeneratorRuntime.mark((function e(){var t,r;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:return e.next=2,Object(i["z"])();case 2:t=e.sent,r=t.data,this.carTypeList=r,console.log(r,"res");case 6:case"end":return e.stop()}}),e,this)})));function t(){return e.apply(this,arguments)}return t}(),handleSelectionChange:function(e){this.multipleSelection=e},clearField:function(){""===this.carModelsSearchFormData.allowableLoad&&this.$delete(this.carModelsSearchFormData,"allowableLoad"),""===this.carModelsSearchFormData.allowableVolume&&this.$delete(this.carModelsSearchFormData,"allowableVolume"),""===this.carModelsSearchFormData.name&&this.$delete(this.carModelsSearchFormData,"name"),""===this.carModelsSearchFormData.id&&this.$delete(this.carModelsSearchFormData,"id")},setField:function(){this.$set(this.carModelsSearchFormData,"allowableLoad"),this.$set(this.carModelsSearchFormData,"allowableVolume"),this.$set(this.carModelsSearchFormData,"name"),this.$set(this.carModelsSearchFormData,"id")},getList:function(){var e=Object(o["a"])(regeneratorRuntime.mark((function e(){var t,r;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:return this.listLoading=!0,e.next=3,Object(i["M"])(this.carModelsSearchFormData);case 3:t=e.sent,r=t.data,this.listLoading=!1,this.dataList=r.items,this.total=r.counts;case 8:case"end":return e.stop()}}),e,this)})));function t(){return e.apply(this,arguments)}return t}(),resetForm:function(e){this.setField(),this.$refs[e].resetFields(),this.getList()},handleFilter:function(){this.clearField(),this.carModelsSearchFormData.page=1,this.getList(this.carModelsSearchFormData)},handleSizeChange:function(e){this.carModelsSearchFormData.pageSize=e,1===this.carModelsSearchFormData.page&&this.getList(this.carModelsSearchFormData)},handleCurrentChange:function(e){this.carModelsSearchFormData.page=e,this.getList()},query:function(){this.formData={}},handleAddModels:function(e){e?(this.$refs.addCarModels.dialogFormV(),this.titleInfo.text="编辑车型",this.hanldeEditForm(e)):(this.$refs.addCarModels.dialogFormV(),this.titleInfo.text="新增车型",this.formData.id="",this.formData.name="",this.formData.allowableLoad="",this.formData.allowableVolume="",this.formData.measureHigh="0.0",this.formData.measureLong="0.0",this.formData.measureWidth="0.0",this.formData.goodsTypes=[])},hanldeEditForm:function(){var e=Object(o["a"])(regeneratorRuntime.mark((function e(t){var r,n;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:return e.next=2,Object(i["h"])(t);case 2:r=e.sent,n=r.data,this.formData.id=n.id,this.formData.name=n.name,this.formData.allowableLoad=Number(n.allowableLoad),this.formData.allowableVolume=Number(n.allowableVolume),this.formData.measureLong=Number(n.measureLong),this.formData.measureWidth=Number(n.measureWidth),this.formData.measureHigh=Number(n.measureHigh),null!=n.goodsTypes?this.formData.goodsTypes=n.goodsTypes:this.formData.goodsTypes=[];case 12:case"end":return e.stop()}}),e,this)})));function t(t){return e.apply(this,arguments)}return t}(),handleDelete:function(){var e=Object(o["a"])(regeneratorRuntime.mark((function e(t,r){var n=this;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:if(!r){e.next=2;break}return e.abrupt("return",this.$message({type:"error",message:"绑定车辆的车型无法删除"}));case 2:this.handleDeleteId="",this.handleDeleteId=t,this.$confirm("确定要删除车型吗?","删除确认",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then((function(){n.change(t)})).catch((function(){n.$message({type:"info",message:"已取消"})}));case 5:case"end":return e.stop()}}),e,this)})));function t(t,r){return e.apply(this,arguments)}return t}(),change:function(){var e=Object(o["a"])(regeneratorRuntime.mark((function e(t){var r=this;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:return e.next=2,Object(i["f"])(t).then((function(e){console.log(e,"删除"),r.dialogVisible=!1,"200"===String(e.code)?(r.$message.success("删除成功"),r.getList()):r.$message.error(e.msg||"删除失败")})).catch((function(){r.$message.error("已绑定车辆的车型无法删除")}));case 2:case"end":return e.stop()}}),e)})));function t(t){return e.apply(this,arguments)}return t}()}},l=c,u=(r("9038"),r("cba8")),f=Object(u["a"])(l,n,a,!1,null,"43bfb464",null);t["default"]=f.exports},e186:function(e,t,r){var n=function(e){"use strict";var t,r=Object.prototype,n=r.hasOwnProperty,a="function"===typeof Symbol?Symbol:{},o=a.iterator||"@@iterator",i=a.asyncIterator||"@@asyncIterator",s=a.toStringTag||"@@toStringTag";function c(e,t,r){return Object.defineProperty(e,t,{value:r,enumerable:!0,configurable:!0,writable:!0}),e[t]}try{c({},"")}catch($){c=function(e,t,r){return e[t]=r}}function l(e,t,r,n){var a=t&&t.prototype instanceof g?t:g,o=Object.create(a.prototype),i=new D(n||[]);return o._invoke=_(e,r,i),o}function u(e,t,r){try{return{type:"normal",arg:e.call(t,r)}}catch($){return{type:"throw",arg:$}}}e.wrap=l;var f="suspendedStart",d="suspendedYield",h="executing",m="completed",p={};function g(){}function b(){}function v(){}var w={};c(w,o,(function(){return this}));var y=Object.getPrototypeOf,x=y&&y(y(T([])));x&&x!==r&&n.call(x,o)&&(w=x);var L=v.prototype=g.prototype=Object.create(w);function S(e){["next","throw","return"].forEach((function(t){c(e,t,(function(e){return this._invoke(t,e)}))}))}function j(e,t){function r(a,o,i,s){var c=u(e[a],e,o);if("throw"!==c.type){var l=c.arg,f=l.value;return f&&"object"===typeof f&&n.call(f,"__await")?t.resolve(f.__await).then((function(e){r("next",e,i,s)}),(function(e){r("throw",e,i,s)})):t.resolve(f).then((function(e){l.value=e,i(l)}),(function(e){return r("throw",e,i,s)}))}s(c.arg)}var a;function o(e,n){function o(){return new t((function(t,a){r(e,n,t,a)}))}return a=a?a.then(o,o):o()}this._invoke=o}function _(e,t,r){var n=f;return function(a,o){if(n===h)throw new Error("Generator is already running");if(n===m){if("throw"===a)throw o;return M()}r.method=a,r.arg=o;while(1){var i=r.delegate;if(i){var s=O(i,r);if(s){if(s===p)continue;return s}}if("next"===r.method)r.sent=r._sent=r.arg;else if("throw"===r.method){if(n===f)throw n=m,r.arg;r.dispatchException(r.arg)}else"return"===r.method&&r.abrupt("return",r.arg);n=h;var c=u(e,t,r);if("normal"===c.type){if(n=r.done?m:d,c.arg===p)continue;return{value:c.arg,done:r.done}}"throw"===c.type&&(n=m,r.method="throw",r.arg=c.arg)}}}function O(e,r){var n=e.iterator[r.method];if(n===t){if(r.delegate=null,"throw"===r.method){if(e.iterator["return"]&&(r.method="return",r.arg=t,O(e,r),"throw"===r.method))return p;r.method="throw",r.arg=new TypeError("The iterator does not provide a 'throw' method")}return p}var a=u(n,e.iterator,r.arg);if("throw"===a.type)return r.method="throw",r.arg=a.arg,r.delegate=null,p;var o=a.arg;return o?o.done?(r[e.resultName]=o.value,r.next=e.nextLoc,"return"!==r.method&&(r.method="next",r.arg=t),r.delegate=null,p):o:(r.method="throw",r.arg=new TypeError("iterator result is not an object"),r.delegate=null,p)}function k(e){var t={tryLoc:e[0]};1 in e&&(t.catchLoc=e[1]),2 in e&&(t.finallyLoc=e[2],t.afterLoc=e[3]),this.tryEntries.push(t)}function F(e){var t=e.completion||{};t.type="normal",delete t.arg,e.completion=t}function D(e){this.tryEntries=[{tryLoc:"root"}],e.forEach(k,this),this.reset(!0)}function T(e){if(e){var r=e[o];if(r)return r.call(e);if("function"===typeof e.next)return e;if(!isNaN(e.length)){var a=-1,i=function r(){while(++a<e.length)if(n.call(e,a))return r.value=e[a],r.done=!1,r;return r.value=t,r.done=!0,r};return i.next=i}}return{next:M}}function M(){return{value:t,done:!0}}return b.prototype=v,c(L,"constructor",v),c(v,"constructor",b),b.displayName=c(v,s,"GeneratorFunction"),e.isGeneratorFunction=function(e){var t="function"===typeof e&&e.constructor;return!!t&&(t===b||"GeneratorFunction"===(t.displayName||t.name))},e.mark=function(e){return Object.setPrototypeOf?Object.setPrototypeOf(e,v):(e.__proto__=v,c(e,s,"GeneratorFunction")),e.prototype=Object.create(L),e},e.awrap=function(e){return{__await:e}},S(j.prototype),c(j.prototype,i,(function(){return this})),e.AsyncIterator=j,e.async=function(t,r,n,a,o){void 0===o&&(o=Promise);var i=new j(l(t,r,n,a),o);return e.isGeneratorFunction(r)?i:i.next().then((function(e){return e.done?e.value:i.next()}))},S(L),c(L,s,"Generator"),c(L,o,(function(){return this})),c(L,"toString",(function(){return"[object Generator]"})),e.keys=function(e){var t=[];for(var r in e)t.push(r);return t.reverse(),function r(){while(t.length){var n=t.pop();if(n in e)return r.value=n,r.done=!1,r}return r.done=!0,r}},e.values=T,D.prototype={constructor:D,reset:function(e){if(this.prev=0,this.next=0,this.sent=this._sent=t,this.done=!1,this.delegate=null,this.method="next",this.arg=t,this.tryEntries.forEach(F),!e)for(var r in this)"t"===r.charAt(0)&&n.call(this,r)&&!isNaN(+r.slice(1))&&(this[r]=t)},stop:function(){this.done=!0;var e=this.tryEntries[0],t=e.completion;if("throw"===t.type)throw t.arg;return this.rval},dispatchException:function(e){if(this.done)throw e;var r=this;function a(n,a){return s.type="throw",s.arg=e,r.next=n,a&&(r.method="next",r.arg=t),!!a}for(var o=this.tryEntries.length-1;o>=0;--o){var i=this.tryEntries[o],s=i.completion;if("root"===i.tryLoc)return a("end");if(i.tryLoc<=this.prev){var c=n.call(i,"catchLoc"),l=n.call(i,"finallyLoc");if(c&&l){if(this.prev<i.catchLoc)return a(i.catchLoc,!0);if(this.prev<i.finallyLoc)return a(i.finallyLoc)}else if(c){if(this.prev<i.catchLoc)return a(i.catchLoc,!0)}else{if(!l)throw new Error("try statement without catch or finally");if(this.prev<i.finallyLoc)return a(i.finallyLoc)}}}},abrupt:function(e,t){for(var r=this.tryEntries.length-1;r>=0;--r){var a=this.tryEntries[r];if(a.tryLoc<=this.prev&&n.call(a,"finallyLoc")&&this.prev<a.finallyLoc){var o=a;break}}o&&("break"===e||"continue"===e)&&o.tryLoc<=t&&t<=o.finallyLoc&&(o=null);var i=o?o.completion:{};return i.type=e,i.arg=t,o?(this.method="next",this.next=o.finallyLoc,p):this.complete(i)},complete:function(e,t){if("throw"===e.type)throw e.arg;return"break"===e.type||"continue"===e.type?this.next=e.arg:"return"===e.type?(this.rval=this.arg=e.arg,this.method="return",this.next="end"):"normal"===e.type&&t&&(this.next=t),p},finish:function(e){for(var t=this.tryEntries.length-1;t>=0;--t){var r=this.tryEntries[t];if(r.finallyLoc===e)return this.complete(r.completion,r.afterLoc),F(r),p}},catch:function(e){for(var t=this.tryEntries.length-1;t>=0;--t){var r=this.tryEntries[t];if(r.tryLoc===e){var n=r.completion;if("throw"===n.type){var a=n.arg;F(r)}return a}}throw new Error("illegal catch attempt")},delegateYield:function(e,r,n){return this.delegate={iterator:T(e),resultName:r,nextLoc:n},"next"===this.method&&(this.arg=t),p}},e}(e.exports);try{regeneratorRuntime=n}catch(a){"object"===typeof globalThis?globalThis.regeneratorRuntime=n:Function("r","regeneratorRuntime = r")(n)}}}]);