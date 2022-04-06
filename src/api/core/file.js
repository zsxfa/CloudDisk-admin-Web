// @ 符号在vue.config.js 中配置， 表示 'src' 路径的别名
import request from '@/utils/request'

//导出默认模块
export default {
  //定义模块成员
  //成员方法：获取文件列表
  list() {
    //调用axios的初始化模块，发送远程ajax请求
    return request({
      url: '/admin/core/file/list',
      method: 'get',
    })
  },
  //获取分页列表
  getPageList(page, limit, searchObj, username) {
    return request({
      url: `/admin/core/file/list/${page}/${limit}/${username}`,
      method: 'get',
      params: searchObj,
    })
  },

  getPageListByType(page, limit, searchObj, username) {
    return request({
      url: `/admin/core/file/listByType/${page}/${limit}/${username}`,
      method: 'get',
      params: searchObj,
    })
  },

  removeById(userFileId) {
    return request({
      url: `/admin/core/file/remove/${userFileId}`,
      method: 'get',
    })
  },

  getById(id) {
    return request({
      url: '/admin/core/file/get/' + id,
      method: 'get',
    })
  },

  updateById(userFileId, userFileName) {
    return request({
      url: `/admin/core/file/update/${userFileId}/${userFileName}`,
      method: 'get',
    })
  },

  showData(username) {
    return request({
      url: `/admin/core/file/show/${username}`,
      method: 'get',
    })
  },
}
