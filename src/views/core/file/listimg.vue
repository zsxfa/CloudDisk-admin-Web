<template>
  <div class="app-container">
    <!-- 表格 在el-table元素中定义了height属性，即可实现固定表头的表格-->
    <el-table
      :data="list"
      style="width: 100%"
      highlight-current-row
      border
      fit
      :default-sort="{ prop: 'uploadTime', order: 'descending' }"
    >
      <!-- <el-table-column type="index" width="50" /> -->
      <el-table-column
        prop="userFileId"
        width="50"
        label="ID"
      ></el-table-column>
      <el-table-column prop="fileName" label="文件名称" align="center" />
      <el-table-column
        prop="fileSize"
        :formatter="formatData"
        label="文件大小"
        sortable
        align="center"
      />
      <el-table-column
        prop="extendName"
        label="文件类型"
        sortable
        align="center"
      />
      <el-table-column
        prop="uploadTime"
        label="上传时间"
        sortable
        align="center"
      />
      <el-table-column prop="filePath" label="保存路径" align="center" />
      <el-table-column prop="userName" label="所属用户" align="center" />
      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <el-button
            type="primary"
            size="mini"
            icon="el-icon-edit"
            @click="updateById(scope.row.userFileId)"
          >
            修改
          </el-button>
          <el-button
            type="danger"
            size="mini"
            icon="el-icon-delete"
            @click="removeById(scope.row.userFileId)"
          >
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <el-pagination
      :current-page="page"
      :total="total"
      :page-size="limit"
      :page-sizes="[10, 20]"
      style="padding: 30px 0"
      layout="total, sizes, prev, pager, next, jumper"
      @size-change="changePageSize"
      @current-change="changeCurrentPage"
    />
  </div>
</template>

<script>
import fileApi from '@/api/core/file'
import Cookies from 'js-cookie'
export default {
  // 定义数据模型
  data() {
    return {
      list: null, // 数据列表
      total: 0, // 数据库中的总记录数
      page: 1, // 默认页码
      limit: 10, // 每页记录数
      searchObj: { fileType: 1 }, // 查询条件
      loginRecordList: [], //会员登录日志
      dialogTableVisible: false, //对话框是否显示
    }
  },

  // 页面渲染成功后获取数据
  created() {
    this.fetchData()
  },

  // 定义方法
  methods: {
    fetchData() {
      // 调用api
      let username = Cookies.get('username')
      fileApi
        .getPageListByType(this.page, this.limit, this.searchObj, username)
        .then((response) => {
          this.list = response.data.list
          this.total = response.data.total
        })
    },

    // 每页记录数改变，size：回调参数，表示当前选中的“每页条数”
    changePageSize(size) {
      this.limit = size
      this.fetchData()
    },

    // 改变页码，page：回调参数，表示当前选中的“页码”
    changeCurrentPage(page) {
      this.page = page
      this.fetchData()
    },

    // 重置表单
    resetData() {
      this.searchObj = {}
      this.fetchData()
    },

    updateById(userFileId) {
      console.log('更新的userFileId', userFileId)
      this.$prompt('请输入文件名', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
      })
        .then(({ value }) => {
          console.log('验证了吗')
          return fileApi.updateById(userFileId, value)
        })
        .then((response) => {
          this.$message.success(response.message)
          this.fetchData()
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消输入',
          })
        })
    },

    removeById(userFileId) {
      console.log('删除的userFileId', userFileId)
      this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          return fileApi.removeById(userFileId)
        })
        .then((response) => {
          this.$message.success(response.message)
          this.fetchData()
        })
        .catch((error) => {
          if (error === 'cancel') {
            this.$message.info({
              type: 'info',
              message: '已取消删除',
            })
          }
        })
    },

    formatData(row, column, cellValue) {
      var bytes = row.fileSize
      if (bytes < 1024) {
        // 字节
        bytes = Math.round(bytes, 2) + ' B'
      } else if (bytes >= 1024 && bytes < 1024 * 1024) {
        // 千字节
        bytes = Math.round(bytes / 1024, 2) + ' KB'
      } else if (bytes >= 1024 * 1024 && bytes < 1024 * 1024 * 1024) {
        // 兆字节
        bytes = Math.round(bytes / 1024 / 1024, 2) + ' MB'
      } else if (
        bytes >= 1024 * 1024 * 1024 &&
        bytes < 1024 * 1024 * 1024 * 1024
      ) {
        // 吉字节
        bytes = Math.round(bytes / 1024 / 1024 / 1024, 2) + ' GB'
      } else if (
        bytes >= 1024 * 1024 * 1024 * 1024 &&
        bytes < 1024 * 1024 * 1024 * 1024 * 1024
      ) {
        // 太字节
        bytes = Math.round(bytes / 1024 / 1024 / 1024 / 1024, 2) + ' TB'
      }
      return bytes
    },
  },
}
</script>
