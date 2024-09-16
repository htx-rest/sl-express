"use strict";
var common_vendor = require("../../common/vendor.js");
var pages_api_login = require("../api/login.js");
require("../../utils/request.js");
require("../../utils/env.js");
if (!Array) {
  const _easycom_uni_popup2 = common_vendor.resolveComponent("uni-popup");
  _easycom_uni_popup2();
}
const _easycom_uni_popup = () => "../../uni_modules/uni-popup/components/uni-popup/uni-popup.js";
if (!Math) {
  (NavBar + _easycom_uni_popup)();
}
const NavBar = () => "../../components/Navbar/index.js";
const _sfc_main = {
  __name: "index",
  setup(__props) {
    common_vendor.useStore();
    const popup = common_vendor.ref();
    const handleOpen = () => {
      popup.value.open();
    };
    const decryptPhoneNumber = (e) => {
      handleClose();
      wx.login({
        success(res) {
          if (e.detail.errMsg === "getPhoneNumber:ok" && e.target.errMsg === "getPhoneNumber:ok") {
            pages_api_login.login({
              code: res.code,
              phoneCode: e.detail.code
            }).then((res2) => {
              common_vendor.index.setStorageSync("token", res2.data.token);
              common_vendor.index.switchTab({
                url: "/pages/index/index"
              });
            }).catch((err) => {
              common_vendor.index.showToast({
                title: "\u7F51\u7EDC\u5F02\u5E38",
                duration: 2e3,
                icon: "none"
              });
            });
          } else {
            common_vendor.index.redirectTo({
              url: "/pages/login/index"
            });
          }
        }
      });
    };
    const handleClose = () => {
      popup.value.close();
    };
    return (_ctx, _cache) => {
      return {
        a: common_vendor.p({
          title: "\u767B\u5F55"
        }),
        b: common_vendor.o(handleOpen),
        c: common_vendor.o(handleClose),
        d: common_vendor.o(handleClose),
        e: common_vendor.o(decryptPhoneNumber),
        f: common_vendor.sr(popup, "4586967a-1", {
          "k": "popup"
        }),
        g: common_vendor.p({
          type: "bottom",
          ["safe-area"]: false
        })
      };
    };
  }
};
var MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__scopeId", "data-v-4586967a"], ["__file", "E:/project/project-wl-yonghuduan-uniapp-vue3/pages/login/index.vue"]]);
wx.createPage(MiniProgramPage);
