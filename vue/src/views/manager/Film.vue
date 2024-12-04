<template>
  <div>
    <div class="card" style="margin-bottom: 5px">
      <el-input v-model="data.name" placeholder="请输入名称查询" style="width: 300px; margin-right: 10px"></el-input>
      <el-button type="primary" @click="load">查询</el-button>
      <el-button type="info" @click="reset">重置</el-button>
    </div>
    <div class="card" style="margin-bottom: 5px">
      <el-button type="primary" style="margin-bottom: 10px" @click="handleAdd">新增</el-button>


      <el-table :data="data.tableData" stripe style="width: 100%">
        <el-table-column prop="name" label="名称" />
        <el-table-column prop="cover" label="封面" >
          <template #default="scope">
            <el-image :src="scope.row.cover" style="width: 40px; height: 40px; border-radius: 5px" :preview-src-list="[scope.row.cover]" preview-teleported></el-image>
          </template>
        </el-table-column>
        <el-table-column prop="descr" label="描述" show-overflow-tooltip/>
        <el-table-column prop="year" label="年份" />
        <el-table-column prop="director" label="导演" />
        <el-table-column prop="actors" label="演员" show-overflow-tooltip/>
        <el-table-column prop="categoryName" label="分类" />
        <el-table-column prop="country" label="国家" />
        <el-table-column prop="language" label="语言" />
        <el-table-column prop="date" label="上映日期" />
        <el-table-column prop="duration" label="片长" />
        <el-table-column prop="imdb" label="IMDb" />
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

    <el-dialog v-model="data.formVisible" title="电影信息" width="40%">
      <el-form :model="data.form" label-width="80px" style="padding-right: 20px">
        <el-form-item label="名称">
          <el-input v-model="data.form.name" autocomplete="off" placeholder="请输入名称"/>
        </el-form-item>
        <el-form-item label="封面" prop="cover">
          <el-upload :action="uploadUrl" list-type="picture" :on-success="handleImgSuccess">
            <el-button type="primary">上传图片</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="描述">
          <el-input :rows=5 type="textarea" v-model="data.form.descr" autocomplete="off" placeholder="请输入电影描述"/>
        </el-form-item>
        <el-form-item label="年份">
          <el-input v-model="data.form.year" autocomplete="off" placeholder="请输入年份"/>
        </el-form-item>
        <el-form-item label="导演">
          <el-input v-model="data.form.director" autocomplete="off" placeholder="请输入导演"/>
        </el-form-item>
        <el-form-item label="演员">
          <el-input :rows=4 type="textarea" v-model="data.form.actors" autocomplete="off" placeholder="请输入演员"/>
        </el-form-item>
        <el-form-item label="分类">
          <el-input v-model="data.form.categoryId" autocomplete="off" placeholder="请输入分类"/>
        </el-form-item>
        <el-form-item label="国家">
          <el-input v-model="data.form.country" autocomplete="off" placeholder="请输入国家"/>
        </el-form-item>
        <el-form-item label="语言">
          <el-input v-model="data.form.language" autocomplete="off" placeholder="请输入语言"/>
        </el-form-item>
        <el-form-item label="上映日期">
          <el-date-picker style="width: 100%" type="date" v-model="data.form.date" placeholder="请选择上映日期" format="YYYY-MM-DD" value-format="YYYY-MM-DD"></el-date-picker>
        </el-form-item>
        <el-form-item label="片长">
          <el-input v-model="data.form.duration" autocomplete="off" placeholder="请输入片长"/>
        </el-form-item>
        <el-form-item label="IMDb">
          <el-input v-model="data.form.imdb" autocomplete="off" placeholder="请输入IMDb"/>
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

// 文件上传的接口地址
const uploadUrl = import.meta.env.VITE_BASE_URL + '/files/upload'

const data = reactive({
  tableData:[],
  total: 0,
  pageNum: 1,
  pageSize: 10,
  name: null,
  formVisible: false,
  form: {}
})

const load = () => {
  request.get('/film/selectPage',{
    params:{
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      name: data.name
    }
  }).then(res =>{
    data.tableData = res.data.list
    data.total = res.data.total
  })
}
load()

const reset = () => {
  data.name = null
  load()
}

//初始化新增数据
const handleAdd = () => {
  data.form = {}
  data.formVisible = true
}

const add = () => {
  request.post('/film/add',data.form).then(res => {
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
  request.put('/film/update',data.form).then(res => {
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
  request.delete('/film/delete/' + id).then(res => {
    if(res.code === '200') {
        load()
        ElMessage.success('操作成功')
      } else {
        ElMessage.error(res.msg)
      }
    })
  }).catch(err =>{})
}

// 处理文件上传的钩子
const handleImgSuccess = (res) => {
  data.form.cover = res.data  // res.data就是文件上传返回的文件路径，获取到路径后赋值表单的属性
}

</script>