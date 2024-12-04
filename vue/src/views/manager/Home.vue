<template>
  <div style="display: flex; grid-gap: 10px">
    <div class="card" style="width: 50%; padding: 20px">
      <div style="margin-bottom: 20px; font-size: 20px; font-weight: bold">电影评分榜</div>
      <div v-for="(item, index) in data.filmList" :key="item.id" @click="goDetail(item.id)" style="cursor: pointer; margin-bottom: 10px">
        <div style="display: flex; grid-gap: 10px">
          <div style="padding-top: 10px">
            <div style="font-weight: bold; font-size: 18px; color: gold" v-if="index === 0">1</div>
            <div style="font-weight: bold; font-size: 18px; color: silver" v-else-if="index === 1">2</div>
            <div style="font-weight: bold; font-size: 18px; color: chocolate" v-else-if="index === 2">3</div>
            <div style="font-size: 18px; color: #333" v-else>{{ index + 1 }}</div>
          </div>
          <img :src="item.cover" alt="" style="width: 100px">
          <div style="flex: 1">
            <div style="font-size: 20px; margin-bottom: 10px">{{ item.name }}</div>
            <div style="color: #666; margin-bottom: 10px" class="line3">{{ item.descr }}</div>
            <div>
              <el-rate v-model="item.score" allow-half show-score disabled></el-rate>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div style="width: 50%; padding: 20px">
      <div style="margin-bottom: 20px; padding-left: 50px; font-size: 20px; font-weight: bold">系统公告</div>
      <el-timeline>
        <el-timeline-item v-for="item in data.noticeList" :key="item.id" :timestamp="item.time" placement="top">
          <div class="card">
            <div style="font-size: 16px; font-weight: bold; margin-bottom: 10px">{{ item.title }}</div>
            <div style="color: #666">{{ item.content }}</div>
          </div>
        </el-timeline-item>
      </el-timeline>
    </div>

  </div>
</template>

<script setup>
import { reactive } from "vue";
import request from "@/utils/request";

const data = reactive({
  user: JSON.parse(localStorage.getItem('system-user') || '{}'),
  noticeList: [],
  filmList: []
})

const goDetail = (id) => {
  location.href = '/filmDetail?id=' + id
}

request.get('/notice/selectAll').then(res => {
  data.noticeList = res.data
})

request.get('/film/selectRanking').then(res => {
  data.filmList = res.data
})
</script>