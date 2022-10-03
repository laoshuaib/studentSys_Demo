<template>
  <div>
    <div  style="padding: 10px">
      <el-table
          :data="tableData"
          style="width: 100%"
          border="true"
      >
        <el-table-column prop="classid" label="课程ID"  width="200px" sortable />
        <el-table-column prop="className" label="课程名"  />
        <el-table-column prop="all" label="待提交成绩学生数" sortable />
        <el-table-column prop="checkNumber" label="已录入成绩数量"  sortable />
        <el-table-column label="操作"  >
          <template #default="scope">
            <el-button size="mini" @click="checked(scope.row)">提交</el-button>
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
  </div>
</template>

<script>
import request from "../../untils/request";

export default {
  name: "GradeUp",
  data(){
    return{
      dialogVisible:false,
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
    checked(row){
      this.form=JSON.parse(JSON.stringify(row));
      request.put("/chooselist/check",this.form).then(res =>{
        console.log(res)
        if(res.code ==="0"){
          this.$message({
            type:"success",
            message:"更新成功"
          })
          this.dialogVisible=false
          this.load()
        }
        else {
          this.$message({
            type:"error",
            message:res.msg
          })
        }
      })
    },
    load(){
      let str = sessionStorage.getItem("user")
      this.form = JSON.parse(str)
      request.get("/chooselist/gradeup",{
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