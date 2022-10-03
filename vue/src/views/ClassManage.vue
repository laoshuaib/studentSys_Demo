<template>
  <div  style="padding: 10px">
    <el-dialog v-model="dialogVisible" title="课程信息" width="30%"  custom-class="el-dialog">
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item label="课程ID">
          <el-input v-model="form.lessonID" disabled></el-input>
        </el-form-item>
        <el-form-item label="课程名称">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="上课地点">
          <el-input v-model="form.address" ></el-input>
        </el-form-item>
        <el-form-item label="课容量">
          <el-input v-model="form.capacity"></el-input>
        </el-form-item>
        <el-form-item label="学分">
          <el-input v-model="form.point"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="save">保存</el-button>
      </span>
      </template>
    </el-dialog>

    <el-table
        :data="tableData"
        style="width: 100%"
        border="true"
    >
      <el-table-column prop="lessonID" label="课程ID"  width="120px" />
      <el-table-column prop="name" label="课程名"  />
      <el-table-column prop="time" label="时间"/>
      <el-table-column prop="capacity" label="课容量"  />
      <el-table-column prop="point" label="学分"  width="50px"/>
      <el-table-column prop="address" label="地点" />
      <el-table-column label="操作" width="145px">
          <template #default="scope">
            <el-button size="mini" @click="handleEdit(scope.row)">编辑</el-button>
            <el-popconfirm title="确定删除该课程？" @confirm="handleDelete(scope.row.lessonID)">
              <template #reference>
                <el-button size="mini" type="danger">删除</el-button>
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
  name: "ClassManage",

  data(){
    return{
      dialogVisible:false,
      currentPage: 1,
      pageSize:10,
      total:0,
      tableData: [],
      form:{},
    }
  },
  created() {
    this.load()
  },
  methods:{
    save(){
      request.put("/lessonlist",this.form).then(res =>{
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
      request.get("/lessonlist/manage",{
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
    handleEdit(row){
      this.form=JSON.parse(JSON.stringify(row));
      this.dialogVisible=true
    },
    handleDelete(lessonID) {
      console.log(lessonID)
      request.delete("/lessonlist/" + lessonID).then(res => {
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