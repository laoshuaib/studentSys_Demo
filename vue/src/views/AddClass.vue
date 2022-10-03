<template>
  <div  style="padding: 10px">
<!--    搜索区域-->
    <div style="margin: 10px 0">
      <el-input v-model="search" placeholder="请输入关键字" style="width: 20%" clearable/>
      <el-button type="primary" style="margin-left: 10px" @click="load">搜索</el-button>
    </div>
    <el-table
        :data="tableData"
        style="width: 100%"
        border="true"
        :row-class-name="tableRowClassName"
    >
      <el-table-column prop="lessonID" label="课程ID"  width="120px" />
      <el-table-column prop="name" label="课程名"  />
      <el-table-column prop="time" label="时间"
      sortable/>
      <el-table-column prop="teacher" label="老师"  />
      <el-table-column prop="point" label="学分"  width="50px"/>
      <el-table-column prop="address" label="地点" />
      <el-table-column label="操作" width="80px">
        <template #default="scope">
          <el-popconfirm title="确定选择该课程？" @confirm="addLesson(scope.row)">
            <template #reference>
              <el-button size="mini" >选择</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <div style="margin: 10px 0">
      <el-pagination
          v-model:currentPage="currentPage"
          :page-sizes="[5, 10, 20]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
      >
      </el-pagination>
  </div>
  </div>
</template>

<script>

import request from "../../untils/request";

export default {
  name: 'Home',
  components: {

  },

  data() {
    return {
      search:'',
      currentPage: 1,
      pageSize:10,
      total:0,
      tableData: [],
      form:{},
      Tform:{}
      }
    },

  created() {
    this.load()
  },

  methods: {
      tableRowClassName({ row, rowIndex }) {
        if (rowIndex === 1) {
          return 'warning-row'
        } else if (rowIndex === 3) {
          return 'success-row'
        }
        return ''
      },

      load(){
        request.get("/lessonlist",{
          params:{
            pageNum:this.currentPage,
            pageSize:this.pageSize,
            search:this.search
          }
        }).then(res=>{
          console.log(res)
          this.tableData=res.data.records
          this.total=res.data.total
        })
      },

      addLesson(row){
        this.form=JSON.parse(JSON.stringify(row))
        let str = sessionStorage.getItem("user")
        this.Tform = JSON.parse(str)
        this.form.classid=this.form.lessonID
        this.form.className=this.form.name
        this.form.teacherid=this.form.teacherid
        this.form.studentName=this.Tform.username
        this.form.studentid=this.Tform.id
        this.form.studentNumber=this.Tform.number
        this.form.checker=0
        this.form.scores=-1
        request.post("/chooselist",this.form).then(res =>{
        console.log(res)
          if(res.code ==="0"){
            this.$message({
              type:"success",
              message:"选课成功"
            })
          }
          else {
            this.$message({
              type:"error",
              message:res.msg
            })
          }
      })
      },

      handleSizeChange(pageSize){
        //改变当前每页的个数触发
        this.pageSize=pageSize
        this.load()
      },
      handleCurrentChange(pageNum){  //改变当前页码触发
        this.currentPage=pageNum
        this.load()
      }

    },

}
</script>

<style>

</style>
