import {
	baseUrl,
	notToLoginApiUrl
} from './env'
// 参数： url:请求地址  param：请求参数  method：请求方式 callBack：回调函数
export function request({
	url = '',
	params = {},
	method = 'GET'
}) {
	// uni.getStorage({
	// 	key: ''
	// })
	// 获取token
	const token = uni.getStorageSync('token')
	let header = {
		// 'Accept': 'application/json',
		'Access-Control-Allow-Origin': '*',
		'Content-Type': 'application/json;charset=UTF-8',
		'Authorization': token
	}
	const requestRes = new Promise((resolve, reject) => {
		uni.request({
			url: baseUrl + url,
			data: params,
			header: header,
			method: method,
			success: (res) => {
				const {
					data
				} = res
				if (data.code == 0 || data.code == 200) {
					resolve(res.data)
				} else {
					resolve(res.data)
					handleError(res, resolve, url)
				}
			},
			fail: (err) => {
				const error = {
					data: {
						msg: err.data
					}
				}
				reject(error)
			}

		})
	})
	const handleError = (error, resolve, url) => {
		var errorCode = error.statusCode;
		if (errorCode == 401) {
			uni.removeStorageSync('token');
			uni.removeStorageSync('nickName')
			if (!notToLoginApiUrl.includes(url)) {
				uni.showToast({
					title: "请先登录",
					icon: "none",
					duration: 1000,
					success: () => {
						setTimeout(() => {
							uni.navigateTo({
								url: '/pages/login/index'
							});
						}, 2000)

					},
					fail: () => {
					}
				});
			}

		} else if (errorCode == 500) {
			if (uni.getStorageSync("token") == "") {
				uni.showToast({
					title: "请先登录",
					icon: "none",
					duration: 2000,
					success: () => {

					},
					fail: () => {

					}
				});
			} else {
				uni.showToast({
					title: error.data.error.message + "",
					icon: "none",
					duration: 3000
				});
			}
		} else {
			resolve(error)
		}
	}
	return requestRes
}
