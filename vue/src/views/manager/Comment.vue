<template>
  <div>
    <div class="card" style="margin-bottom: 5px">
      <el-input v-model="data.filmName" placeholder="请输入电影名称查询" style="width: 300px; margin-right: 10px"></el-input>
      <el-button type="primary" @click="load">查询</el-button>
      <el-button type="info" @click="reset">重置</el-button>
    </div>
    <div class="card" style="margin-bottom: 5px">

      <el-table :data="data.tableData" stripe>
        <el-table-column prop="filmName" label="电影名称" />
        <el-table-column prop="score" label="评分" >
          <template #default="scope">
            <el-rate disabled v-model="scope.row.score" allow-half />
          </template>
        </el-table-column>
        <el-table-column prop="comment" label="评论" >
          <template #default="scope">
            <el-button @click="preview(scope.row.comment)">查看内容</el-button>
          </template>
        </el-table-column>
        <el-table-column prop="userName" label="用户名称" />
        <el-table-column prop="time" label="评论时间" />
        <el-table-column prop="type" label="类型" >
          <template #default="scope">
            <el-tag type="primary" v-if="scope.row.type === '短评'">短评</el-tag>
            <el-tag type="success" v-if="scope.row.type === '长评'">长评</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="100">
          <template #default="scope">
            <el-button  type="danger" @click="del(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="card">
    <el-pagination background layout="total, prev, pager, next" v-model:current-page="data.pageNum" v-model:page-size="data.pageSize"
                   :total="data.total" @current-change="load"/>
    </div>

    <el-dialog v-model="data.formVisibleComment" title="评论内容" width="40%">
      <div v-html="data.commentContent"></div>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="data.formVisibleComment = false">关 闭</el-button>
        </div>
      </template>
    </el-dialog>

  </div>
</template>

<script setup>
import { reactive } from "vue";
import request from "@/utils/request";
import {ElMessage, ElMessageBox} from "element-plus";

const data = reactive({
  user: JSON.parse(localStorage.getItem('system-user') || '{}'),
  tableData:[],
  total: 0,
  pageNum: 1,
  pageSize: 10,
  filmName: null,
  formVisible: false,
  form: {},
  formVisibleComment: false,
  commentContent: null
})

const preview = (comment) =>{
  data.commentContent = comment
  data.formVisibleComment = true
}

const load = () => {
  request.get('/comment/selectPage',{
    params:{
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      filmName: data.filmName,
      userId: data.user.role === 'ADMIN' ? null : data.user.id
    }
  }).then(res =>{
    data.tableData = res.data.list
    data.total = res.data.total
  })
}
load()

const reset = () => {
  data.filmName = null
  load()
}

//初始化新增数据
const handleAdd = () => {
  data.form = {}
  data.formVisible = true
}

const add = () => {
  request.post('/comment/add',data.form).then(res => {
    if(res.code === '200'){
      load()
      data.formVisible = false
      ElMessage.success('操作成功')
    }else{
      ElMessage.error(res.msg)
    }
  })
}

//编辑按钮触发
const handleEdit = (row) => {
  data.form = JSON.parse(JSON.stringify(row)) //给表单赋值当前行对象的数据,深度拷贝
  data.formVisible = true
}

const update = () => {
  request.put('/comment/update',data.form).then(res => {
    if(res.code === '200'){
      load()
      data.formVisible = false
      ElMessage.success('操作成功')
    }else{
      ElMessage.error(res.msg)
    }
  })
}

const save = () => {
  data.form.id ? update() : add()
}

const del = (id) => {
  ElMessageBox.confirm('删除数据后无法恢复，您确认吗？', '确认删除', {type: 'warning'}).then(res =>{
  request.delete('/comment/delete/' + id).then(res => {
    if(res.code === '200') {
        load()
        ElMessage.success('操作成功')
      } else {
        ElMessage.error(res.msg)
      }
    })
  }).catch(err =>{})
}
</script>