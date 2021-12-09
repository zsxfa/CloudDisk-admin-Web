// 文件模块相关接口
import { get, post } from './http'
/**
 * 以登录接口为例
 * export const login = p => get('/user/login', p);
 *
 * login ---------- 接口名称
 * p -------------- 传参，若需要在url中拼接其他信息，传参可以改为(p, other)
 * get ------------ 接口调用的方法，来自 http.js 中封装好的四个axios方法 get/post/put/axiosDelete
 * '/user/login' -- 接口url，若需要在url中拼接其他信息：
 *                  首先需要在传参处改为(p, other1, other2)
 *                  然后将url改为`/user/${other1}/login/${other2}`
 * p -------------- 传递给 get/post/put/axiosDelete 中的查询参数/请求体
 *
 *
 *
 * 除此之外，POST 请求支持请求体格式为 FormData，那么就需要多传递一个参数，true，如下示例：
 * export const example = p => post('/test/example', p, true);
 */

/**
 * 获取文件列表相关接口
 */
// 获取文件列表（区分文件路径）✔
export const getFileListByPath = (p) =>
	post('/api/core/file/list/getfilelist', p)
// 获取文件列表（区分文件类型）✔
export const getFileListByType = (p) =>
	post('/api/core/file/list/selectfilebyfiletype', p)
// 获取存储占用✔
export const getStorage = (p) => get('/api/core/hdfs/getstorage', p)
// 获取回收站文件列表✔
export const getRecoveryFile = (p) => post('/api/core/recoveryfile/list', p)
// 获取文件目录树✔
export const getFoldTree = (p) => get('api/core/file/getfiletree', p)

// 获取我已分享的文件列表
export const getMyShareFileList = (p) => get('/share/shareList', p)

/**
 * 单文件操作相关接口
 */
// 删除文件✔
export const deleteFile = (p) => post('/api/core/file/deletefile', p)
// 创建文件夹✔
export const createFold = (p) => post('/api/core/file/createfile', p)
// 移动文件✔
export const moveFile = (p) => post('/api/core/file/movefile', p)
// 重命名文件✔
export const renameFile = (p) => post('/api/core/file/renamefile', p)

// 全局搜索文件✔
export const searchFile = (p) => get('/api/core/file/search', p)
// 分享文件
export const shareFile = (p) => post('/share/sharefile', p)
// 校验分享链接过期时间
export const checkShareLinkEndtime = (p) => get('/share/checkendtime', p)
// 校验分享链接是否需要提取码
export const checkShareLinkType = (p) => get('/share/sharetype', p)
// 校验分享链接提取码是否正确
export const checkShareLinkCode = (p) => get('/share/checkextractioncode', p)
// 获取分享文件列表
export const getShareFileList = (p) => get('/share/sharefileList', p)
// 保存分享文件
export const saveShareFile = (p) => post('/share/savesharefile', p)

/**
 * 文件批量操作相关接口
 */
// 批量删除文件✔
export const batchDeleteFile = (p) => post('/api/core/file/batchdeletefile', p)
// 批量移动文件✔
export const batchMoveFile = (p) => post('api/core/file/batchmovefile', p)

/**
 * 回收站文件操作相关接口
 */
// 回收站文件还原✔
export const restoreRecoveryFile = (p) =>
	post('/api/core/recoveryfile/restorefile', p)
// 回收站文件删除✔
export const deleteRecoveryFile = (p) =>
	post('/api/core/recoveryfile/deleterecoveryfile', p)
// 回收站文件批量删除✔
export const batchDeleteRecoveryFile = (p) =>
	post('/api/core/recoveryfile/batchdelete', p)
