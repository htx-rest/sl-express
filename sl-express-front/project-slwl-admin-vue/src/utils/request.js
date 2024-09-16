import axios from 'axios'
import Cookies from 'js-cookie'
import router from '@/router'
import { MessageBox } from 'element-ui'
import { Message } from 'element-ui'
import { blackList } from '@/utils/static-data'
// create an axios instance
const service = axios.create({
  baseURL: process.env.VUE_APP_BASE_URL,
  timeout: 5000 // request timeout
})

service.interceptors.request.use(
  config => {
    config.headers['Authorization'] = Cookies.get('TOKEN')
    config.headers['Content-type'] = 'application/json'
    return config
  },
  error => {
    return Promise.reject(error)
  }
)
service.interceptors.response.use(response => {
  if (Number(response.status) === 200) {
    // 判断如果code为-1 权限不足提示 40001 认证失效
    if (response.data && Object.prototype.toString.call(response.data) === '[object Object]' && response.data.isError) {
      if (response.data.code === -1) {
        Message({
          // message: '很抱歉，您暂无该操作权限' || 'Error',
          message: '很抱歉，演示系统，不允许修改',
          // message: response.data.msg,
          type: 'error',
          duration: 5 * 1000
        })
      } else if (response.data.code === 40001) {
        // 向父通讯认证失败 退出到登录页
        window.parent.postMessage({
          data: 'tokenFailed'
        }, '*')
      }
    }
    // TODO
    return response.data
  } else if (Number(response.status) === 40001) {
    return MessageBox.confirm(
      '长时间无操作，系统超时，需重新登录',
      '确定登出',
      {
        confirmButtonText: '重新登录',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )
      .then(() => {
        return router.push('/login')
      })
      .finally(() => {})
  }
}, error => {
  const response = error.response
  const status = response && response.status
  // 和后端约定的3种状态码会跳转登录
  if ([400, 401, 403].includes(status)) {
    if (status === 400) {
      Message({
        message: '权限不足',
        type: 'error',
        duration: 2000
      })
    } else if (status === 401) {
      Message({
        message: '登录状态过期',
        type: 'error',
        duration: 2000
      })
    }
    return router.push('/login')
  }
})

export const createAPI = (url, method, data, type) => {
  const config = {}
  config.headers = {
    'Content-Type': 'application/json'
  }
  if (method === 'get') {
    config.params = data
  } else {
    config.data = data
  }
  config.responseType = type
  // 判断是否不可进行操作
  // const blackIndex = blackList.findIndex(item => {
  //   if (!item.isReg) {
  //     return item.method === method && item.url === url
  //   } else {
  //     return item.method === method && new RegExp(`^${item.url}`).test(url)
  //   }
  // })
  // if (blackIndex >= 0) {
  //   Message({
  //     message: '演示系统，不支持此操作！',
  //     type: 'warning',
  //     duration: 3 * 1000,
  //     showClose: true
  //   })
  //   return Promise.reject('演示系统，不支持此操作！')
  // }

  return service({
    url,
    method,
    ...config
  })
}
export default createAPI
