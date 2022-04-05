import request from '@/utils/request'

export default {
  list() {
    //调用axios的初始化模块，发送远程ajax请求
    return request({
      url: '/admin/core/user/list',
      method: 'get',
    })
  },
  //获取分页列表
  getPageList(page, limit, searchObj) {
    return request({
      url: `/admin/core/user/list/${page}/${limit}`,
      method: 'get',
      params: searchObj,
    })
  },

  getuserLoginRecord(page, limit, searchObj) {
    return request({
      url: `/admin/core/userLoginRecord/list/${page}/${limit}`,
      method: 'get',
      params: searchObj,
    })
  },

  getuserOperateRecord(page, limit, searchObj) {
    return request({
      url: `/admin/core/userOperateRecord/list/${page}/${limit}`,
      method: 'get',
      params: searchObj,
    })
  },
}
