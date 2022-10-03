<template>
  <div  style="padding: 10px">
    <el-table
        :data="tableData"
        style="width: 100%"
        border="true"
    >
      <el-table-column prop="classid" label="课程ID"  width="300px" />
      <el-table-column prop="className" label="课程名"  />
      <el-table-column label="操作" width="80px">
        <template #default="scope">
          <el-popconfirm title="确定退选该课程？" @confirm="deleteLesson(scope.row.id)">
            <template #reference>
              <el-button size="mini" type="danger">退选</el-button>
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
  name: "DeleteClass",
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
      request.get("/chooselist",{
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
    deleteLesson(id) {
      request.delete("/chooselist/" + id).then(res => {
        if (res.code === '0') {
          this.$message({
            type: "success",
            message: "删除成功"
          })
        } else {
          this.$message({
            type: "error",
            message: res.msg
          })
        }
        this.load()  // 删除之后重新加载表格的数据
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