
// 测试环境
export const baseUrl = 'http://slwl-geteway-t.itheima.net/customer'
// 本地环境
// export const baseUrl = 'http://172.16.17.63:9527/customer'
// export const baseUrl ='http://172.16.43.24:9527/courier'
// 线上环境
// export const baseUrl = 'https://registakeaway.itheima.net'

//不需要跳转到登录页面的接口
export const notToLoginApiUrl = [
	'/order-manager/order/page',
	'/user/profile',
	'/order-manager/order/count'
]