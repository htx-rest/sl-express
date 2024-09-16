"use strict";
var common_vendor = require("../common/vendor.js");
var utils_env = require("./env.js");
function request({
  url = "",
  params = {},
  method = "GET"
}) {
  const token = common_vendor.index.getStorageSync("token");
  let header = {
    "Access-Control-Allow-Origin": "*",
    "Content-Type": "application/json;charset=UTF-8",
    "Authorization": token
  };
  const requestRes = new Promise((resolve, reject) => {
    common_vendor.index.request({
      url: utils_env.baseUrl + url,
      data: params,
      header,
      method,
      success: (res) => {
        const {
          data
        } = res;
        if (data.code == 0 || data.code == 200) {
          resolve(res.data);
        } else {
          resolve(res.data);
          handleError(res, resolve, url);
        }
      },
      fail: (err) => {
        const error = {
          data: {
            msg: err.data
          }
        };
        reject(error);
      }
    });
  });
  const handleError = (error, resolve, url2) => {
    var errorCode = error.statusCode;
    if (errorCode == 401) {
      common_vendor.index.removeStorageSync("token");
      common_vendor.index.removeStorageSync("nickName");
      if (!utils_env.notToLoginApiUrl.includes(url2)) {
        common_vendor.index.showToast({
          title: "\u8BF7\u5148\u767B\u5F55",
          icon: "none",
          duration: 1e3,
          success: () => {
            setTimeout(() => {
              common_vendor.index.navigateTo({
                url: "/pages/login/index"
              });
            }, 2e3);
          },
          fail: () => {
          }
        });
      }
    } else if (errorCode == 500) {
      if (common_vendor.index.getStorageSync("token") == "") {
        common_vendor.index.showToast({
          title: "\u8BF7\u5148\u767B\u5F55",
          icon: "none",
          duration: 2e3,
          success: () => {
          },
          fail: () => {
          }
        });
      } else {
        common_vendor.index.showToast({
          title: error.data.error.message + "",
          icon: "none",
          duration: 3e3
        });
      }
    } else {
      resolve(error);
    }
  };
  return requestRes;
}
exports.request = request;
