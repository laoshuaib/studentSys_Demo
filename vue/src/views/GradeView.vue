<template>
  <div>
    <div  style="padding: 10px">
      <!--    搜索区域-->
      <div style="margin: 10px 0">
        <el-input v-model="search" placeholder="请输入课程ID" style="width: 20%" clearable/>
        <el-button type="primary" style="margin-left: 10px" @click="findByID">查询</el-button>
      </div>
      <el-table
          :data="tableData"
          style="width: 100%"
          border="true"
      >
        <el-table-column prop="classid" label="课程ID"  width="200px" sortable />
        <el-table-column prop="className" label="课程名"  />
        <el-table-column prop="studentNumber" label="学号"  />
        <el-table-column prop="studentName" label="学生姓名"  />
        <el-table-column prop="scores" label="成绩"  />
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
  </div>
</template>

<script>
import request from "../../untils/request";

export default {
  name: "GradeView",
  data(){
    return{
      form: {},
      tableData:[],
      currentPage: 1,
      pageSize:10,
      total:0,
      search:''
    }
  },
  created() {
    this.load()
  },
  methods:{
    findByID(){
      let str = sessionStorage.getItem("user")
      this.form = JSON.parse(str)
      console.log(this.search)
      request.get("/chooselist/viewByID",{
        params:{
          pageNum:this.currentPage,
          pageSize:this.pageSize,
          id:this.form.id,
          search:this.search
        }
      }).then(res=>{
        console.log(res)
        this.tableData=res.data.records
        this.total=res.data.total
      })
    },
    load(){
      let str = sessionStorage.getItem("user")
      this.form = JSON.parse(str)
      request.get("/chooselist/view",{
        params:{
          pageNum:this.currentPage,
          pageSize:this.pageSize,
          id:this.form.id
        }
      }).then(res=>{
        console.log(res)
        this.tableData=res.data.records
        this.total=res.data.total
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
  }
}
</script>

<style scoped>

</style>