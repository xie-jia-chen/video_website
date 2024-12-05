<template>
  <div>
    <div class="card" style="margin-bottom: 5px">
      <el-input v-model="data.name" placeholder="请输入电影名称查询" style="width: 300px; margin-right: 10px"></el-input>
      <el-button type="primary" @click="load">查询</el-button>
      <el-button type="info" @click="reset">重置</el-button>
    </div>

    <div class="card" style="margin-bottom: 5px">
      <el-button :class="{ 'film-active' : data.categoryId === null }" @click="loadFilmByCategory(null)">全部</el-button>
      <el-button :class="{ 'film-active' : data.categoryId === item.id }" v-for="item in data.categoryList" :key="item.id" @click="loadFilmByCategory(item.id)">{{ item.name }}</el-button>
    </div>

    <div class="card" style="margin-bottom: 5px">
      <el-row :gutter="20">
        <el-col :span="6" v-for="item in data.tableData" :key="item.id" style="margin-bottom: 20px; cursor: pointer" @click="goDetail(item.id)">
          <img :src="item.cover" alt="" style="width: 100%; height: 400px; border-radius: 5px">
          <div style="margin: 5px 0; font-size: 18px" class="line1">{{ item.name }}</div>
          <div style="margin: 5px 0; display: flex; align-items: center">
            <el-rate v-model="item.score" disabled allow-half show-score></el-rate>
            <div style="flex: 1; text-align: right; color: #1967e3">{{ item.commentNum }}人评论</div>
          </div>
          <div style="font-size: 13px; color: #666" class="line3">{{ item.descr }}</div>
        </el-col>
      </el-row>
    </div>

    <div class="card">
      <el-pagination background layout="total, prev, pager, next" v-model:current-page="data.pageNum" v-model:page-size="data.pageSize"
                     :total="data.total" @current-change="load" />
    </div>
  </div>
</template>

<script setup>
import { reactive } from "vue";
import request from "@/utils/request";

const data = reactive({
  name: null,
  pageNum: 1,
  pageSize: 8,
  tableData: [],
  total: 0,
  categoryList: [],
  categoryId: null
})

const goDetail = (id) => {
  location.href = '/#/filmDetail?id=' + id
}

// 查询所有电影分类信息
const loadCategory = () => {
  request.get('/category/selectAll').then(res => {
    data.categoryList = res.data
  })
}
loadCategory()

// 根据分类查询电影列表
const loadFilmByCategory = (categoryId) => {
  data.categoryId = categoryId
  load()
}

const load = () => {
  request.get('/film/selectPage', {
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      name: data.name,
      categoryId: data.categoryId
    }
  }).then(res => {
    data.tableData = res.data.list
    data.total = res.data.total
  })
}
load()

const reset = () => {
  data.name = null
  load()
}
</script>

<style scoped>
.film-active {
  background-color: #1967e3;
  color: white;
}
</style>