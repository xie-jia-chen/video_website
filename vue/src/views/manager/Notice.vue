<template>
  <div>
    <div class="card" style="margin-bottom: 5px">
      <el-input v-model="data.title" placeholder="请输入标题查询" style="width: 300px; margin-right: 10px"></el-input>
      <el-button type="primary" @click="load">查询</el-button>
      <el-button type="info" @click="reset">重置</el-button>
    </div>
    <div class="card" style="margin-bottom: 5px">
      <el-button type="primary" style="margin-bottom: 10px" @click="handleAdd">新增</el-button>


      <el-table :data="data.tableData" stripe style="width: 100%">
        <el-table-column prop="title" label="标题" />
        <el-table-column prop="content" label="内容" />
        <el-table-column prop="time" label="发布时间" />
        <el-table-column label="操作" width="160">
          <template #default="scope">
            <el-button  type="primary"  @click="handleEdit(scope.row)">编辑</el-button>
            <el-button  type="danger" @click="del(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="card">
    <el-pagination background layout="total, prev, pager, next" v-model:current-page="data.pageNum" v-model:page-size="data.pageSize"
                   :total="data.total" @current-change="load"/>
    </div>

    <el-dialog v-model="data.formVisible" title="系统公告" width="40%">
      <el-form :model="data.form" label-width="80px" style="padding-right: 20px">
        <el-form-item label="标题">
          <el-input v-model="data.form.title" autocomplete="off" placeholder="请输入标题"/>
        </el-form-item>
        <el-form-item label="内容">
          <el-input type="textarea" v-model="data.form.content" autocomplete="off" placeholder="请输入内容"/>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="data.formVisible = false">取消</el-button>
          <el-button type="primary" @click="save">保 存</el-button>
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
  tableData:[],
  total: 0,
  pageNum: 1,
  pageSize: 10,
  title: null,
  formVisible: false,
  form: {}
})

const load = () => {
  request.get('notice/selectPage',{
    params:{
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      title: data.title
    }
  }).then(res =>{
    data.tableData = res.data.list
    data.total = res.data.total
  })
}
load()

const reset = () => {
  data.title = null
  load()
}

//初始化新增数据
const handleAdd = () => {
  data.form = {}
  data.formVisible = true
}

const add = () => {
  request.post('/notice/add',data.form).then(res => {
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
  request.put('/notice/update',data.form).then(res => {
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
  request.delete('/notice/delete/' + id).then(res => {
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