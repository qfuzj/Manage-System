/**
 * 简洁、标准的前端 Token（身份令牌）管理器工具文件
 * 提供获取、设置和删除 Token 的函数，使用 js-cookie 库来操作浏览器的 Cookie 存储
 * 适用于 Vue.js 项目中的用户认证和会话管理
 */
import Cookies from 'js-cookie'

const TokenKey = 'Admin-Token'

export function getToken() {
  return Cookies.get(TokenKey)
}

export function setToken(token) {
  return Cookies.set(TokenKey, token)
}

export function removeToken() {
  return Cookies.remove(TokenKey)
}
