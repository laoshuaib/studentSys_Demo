<template>
<div>
  <div  style="padding: 10px">
    <el-table
        :data="tableData"
        style="width: 100%"
        border="true"
    >
      <el-table-column prop="classid" label="课程ID"  width="200px" />
      <el-table-column prop="className" label="课程名"  />
      <el-table-column prop="scores" label="成绩"  />
      <el-table-column prop="classRank" label="班级排名"  />
      <el-table-column prop="lessonRank" label="课程排名"  />
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
  name: "Grades",
  data(){
    return{
      currentPage: 1,
      pageSize:10,
      total:0,
      tableData: [],
      form:{}
    }
  },
  created() {
    this.load()
  },
  methods:{
    load(){
      let str = sessionStorage.getItem("user")
      this.form = JSON.parse(str)
      request.get("/chooselist/getgrades",{
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