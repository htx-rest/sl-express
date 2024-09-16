"use strict";
var utils_request = require("../../utils/request.js");
const login = (params) => utils_request.request({
  url: `/user/login`,
  method: "post",
  params
});
exports.login = login;
