import {
	request
} from "../../utils/request.js"

// 手机号登录
export const login = (params) =>
	request({
		url: `/user/login`,
		method: 'post',
		params
	})