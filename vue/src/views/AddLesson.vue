<template>
  <div class="add-lesson-box">
    <el-form class="add-lesson-form" ref="form" :model="form" label-width="120px" :rules="rules">
      <el-form-item label="课程名称" prop="name" >
        <el-input v-model="form.name"></el-input>
      </el-form-item>
      <el-form-item label="上课地点" prop="address" style="width: 40%">
        <el-input v-model="form.address"></el-input>
      </el-form-item>
      <el-form-item label="上课时间" prop="day">
        <el-row>
          <el-select v-model="extra.day" style="width: 25%;margin-right: 5px">
            <el-option
                v-for="item in dayOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
            >
            </el-option>
          </el-select>
          <el-select v-model="extra.dayTime" style="width: 25%;">
            <el-option
                v-for="item in options"
                :key="item.value1"
                :label="item.label"
                :value="item.value1"
            >
            </el-option>
          </el-select>
        </el-row>
      </el-form-item>
      <el-row>
        <el-form-item label="学分" style="width: 40%" prop="point">
          <el-input v-model="form.point"></el-input>
        </el-form-item>
      </el-row>
      <el-row>
        <el-form-item label="课容量" style="width: 40%" prop="capacity">
          <el-input v-model="form.capacity"></el-input>
        </el-form-item>
      </el-row>
      <el-form-item label="选课时间" prop="picktime">
        <el-col :span="11">
          <el-date-picker
              v-model="form.startTime"
              type="date"
              placeholder="选择开始时间"
              style="width: 100%"
          ></el-date-picker>
        </el-col>
        <el-col class="line" :span="2">-</el-col>
        <el-col :span="11">
          <el-date-picker
              v-model="form.endTime"
              type="date"
              placeholder="选择结束时间"
              style="width: 100%"
          ></el-date-picker>
        </el-col>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="onSubmit">上传</el-button>
        <el-button @click="clear">清空</el-button>
      </el-form-item>
    </el-form>

  </div>
</template>

<script>
import {ref, defineComponent} from 'vue'
import request from "../../untils/request";

export default {
  name: "AddLesson",
  data() {
    return {
      form: {},
      extra: {},
      rules: {
        name: [
          {
            required: true,
            message: '请输入课程名字',
            trigger: 'blur',
          },
        ],
        address: [
          {
            required: true,
            message: '请输入上课地点',
            trigger: 'blur',
          },
        ],
        point: [
          {
            required: true,
            message: '请输入学分',
            trigger: 'blur',
          },
        ],
        capacity: [
          {
            required: true,
            message: '请输入课容量',
            trigger: 'blur',
          },
        ],
      }
    }
  },
  setup() {

    return {

      dayOptions: ref([
        {
          value: '周一',
          label: '周一',
        },
        {
          value: '周二',
          label: '周二',
        },
        {
          value: '周三',
          label: '周三',
        },
        {
          value: '周四',
          label: '周四',
        },
        {
          value: '周五',
          label: '周五',
        },
        {
          value: '周六',
          label: '周六',
        },
        {
          value: '周日',
          label: '周日',
        },
      ]),
      value: ref(''),
      options: ref([
        {
          value1: '第一节',
          label: '第一节',
        },
        {
          value1: '第二节',
          label: '第二节',
        },
        {
          value1: '第三节',
          label: '第三节',
        },
        {
          value1: '第四节',
          label: '第四节',
        },
        {
          value1: '第五节',
          label: '第五节',
        },
      ]),
      value1: ref(''),
    }

  },
  methods: {
    onSubmit() {
      this.$refs['form'].validate((valid)=> {
        if (valid){
          let str = this.extra.day + this.extra.dayTime
          this.form.time = str
          this.form.margin = this.form.capacity
          str = sessionStorage.getItem("user")
          this.extra = JSON.parse(str)
          this.form.teacherid = this.extra.id;
          this.form.teacher = this.extra.username;
          request.post("/lessonlist", this.form).then(res => {
            console.log(res)
            if (res.code === "0") {
              this.$message({
                type: "success",
                message: "新增成功"
              })
              this.form = {}
              this.extra = {}
            } else {
              this.$message({
                type: "error",
                message: res.msg
              })
            }
          })
        }})


    },
    clear(){
      this.extra={}
      this.form={}
    }
  },
}
</script>

<style scoped>
.add-lesson-box {
  margin: 90px 30px 30px;
}

.add-lesson-form {
  width: 80%;
  min-width: 500px;
}
</style>