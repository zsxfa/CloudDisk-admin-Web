import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
// import Layout from '@/layout'
import Layout from '@/layout/Layout'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'/'el-icon-x' the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true,
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true,
  },

  // 首页
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    name: '全部数据',
    children: [
      {
        path: 'dashboard',
        component: () => import('@/views/dashboard/index'),
        meta: { title: '网盘管理首页', icon: 'home' },
      },
    ],
  },
]
/**
 * 动态路由
 */
export const asyncRoutes = [
  {
    path: '/core',
    component: Layout,
    redirect: '/core/file/list',
    name: '文件管理',
    meta: { title: '文件管理', icon: 'dashboard' },
    alwaysShow: true,
    children: [
      {
        path: 'file/list',
        name: '文件列表',
        component: () => import('@/views/core/file/list'),
        meta: { title: '全部', icon: 'allFile' },
      },
      {
        path: 'img/list',
        name: '图片列表',
        component: () => import('@/views/core/file/listimg'),
        meta: { title: '图片' },
      },
      {
        path: 'doc/list',
        name: '文档列表',
        component: () => import('@/views/core/file/listdoc'),
        meta: { title: '文档' },
      },
      {
        path: 'music/list',
        name: '音乐列表',
        component: () => import('@/views/core/file/listmusic'),
        meta: { title: '音乐' },
      },
      {
        path: 'video/list',
        name: '视频列表',
        component: () => import('@/views/core/file/listvideo'),
        meta: { title: '视频' },
      },
      {
        path: 'other/list',
        name: '其他列表',
        component: () => import('@/views/core/file/listother'),
        meta: { title: '其他' },
      },
    ],
  },
  {
    path: '/acl',
    component: Layout,
    redirect: '/acl/user/list',
    name: '权限管理',
    meta: { title: '权限管理', icon: 'chart' },
    children: [
      {
        path: 'user/list',
        name: '用户管理',
        component: () => import('@/views/acl/user/list'),
        meta: { title: '用户管理', icon: 'userControl' },
      },
      {
        path: 'user/add',
        name: '用户添加',
        component: () => import('@/views/acl/user/form'),
        meta: { title: '用户添加' },
        hidden: true,
      },
      {
        path: 'user/update/:id',
        name: '用户修改',
        component: () => import('@/views/acl/user/form'),
        meta: { title: '用户修改' },
        hidden: true,
      },
      {
        path: 'user/role/:id',
        name: '用户角色',
        component: () => import('@/views/acl/user/roleForm'),
        meta: { title: '用户角色', icon: 'roleCon' },
        hidden: true,
      },
      {
        path: 'role/list',
        name: '角色管理',
        component: () => import('@/views/acl/role/list'),
        meta: { title: '角色管理' },
      },
      {
        path: 'role/form',
        name: '角色添加',
        component: () => import('@/views/acl/role/form'),
        meta: { title: '角色添加' },
        hidden: true,
      },
      {
        path: 'role/update/:id',
        name: '角色修改',
        component: () => import('@/views/acl/role/form'),
        meta: { title: '角色修改' },
        hidden: true,
      },
      {
        path: 'role/distribution/:id',
        name: '角色权限',
        component: () => import('@/views/acl/role/roleForm'),
        meta: { title: '角色权限' },
        hidden: true,
      },
      {
        path: 'menu/list',
        name: '菜单管理',
        component: () => import('@/views/acl/menu/list'),
        meta: { title: '菜单管理' },
      },
    ],
  },

  {
    path: '/log',
    component: Layout,
    redirect: '/core/user/log',
    name: '日志列表',
    meta: { title: '日志' },
    children: [
      {
        path: 'log/login',
        name: '用户登录日志',
        component: () => import('@/views/core/user/log'),
        meta: { title: '用户登录日志' },
      },
      {
        path: 'log/operate',
        name: '用户操作日志',
        component: () => import('@/views/core/user/operateLog'),
        meta: { title: '用户操作日志' },
        hidden: true,
      },
    ],
  },
  // 404 放在最后
  { path: '*', redirect: '/404', hidden: true },
]

const createRouter = () =>
  new Router({
    // mode: 'history', // require service support
    scrollBehavior: () => ({ y: 0 }),
    routes: constantRoutes,
  })

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
