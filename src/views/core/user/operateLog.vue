<template>
  <div class="app-container">
    <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item label="通过用户名查询">
        <el-input v-model="searchObj.userName" placeholder="姓名" />
      </el-form-item>

      <el-button type="primary" icon="el-icon-search" @click="fetchData()">
        查询
      </el-button>
      <el-button type="default" @click="resetData()">清空</el-button>
    </el-form>

    <!-- 用户登录日志 -->
    <el-table
      :data="list"
      border
      stripe
      fit
      height="550"
      highlight-current-row
      style="width: 80%"
    >
      <el-table-column
        prop="username"
        label="用户姓名"
        width="200"
        align="center"
      />
      <el-table-column
        prop="operation"
        label="操作"
        width="150"
        align="center"
      />
      <el-table-column
        prop="detail"
        label="操作细节"
        width="150"
        align="center"
      />
      <el-table-column
        prop="result"
        label="操作结果"
        width="150"
        align="center"
      />
      <el-table-column
        prop="source"
        label="操作来源"
        width="150"
        align="center"
      />

      <el-table-column prop="time" label="登录时间" sortable align="center" />
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
import userInfoApi from '@/api/core/user'

export default {
  data() {
    return {
      list: null, // 数据列表
      total: 0, // 数据库中的总记录数
      page: 1, // 默认页码
      limit: 10, // 每页记录数
      searchObj: {}, // 查询条件
      loginRecordList: [], //会员登录日志
      dialogTableVisible: false, //对话框是否显示
    }
  },

  created() {
    // 当页面加载时获取数据
    this.fetchData()
  },

  methods: {
    fetchData() {
      userInfoApi
        .getuserOperateRecord(this.page, this.limit, this.searchObj)
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

    resetData() {
      //还原表单
      this.searchObj = {}
      this.fetchData()
    },

    // 锁定和解锁
    lock(id, status) {
      userInfoApi.lock(id, status).then((response) => {
        this.$message.success(response.message)
        this.fetchData()
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
  },
}
</script>
