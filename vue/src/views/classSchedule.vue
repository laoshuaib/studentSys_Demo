<template>
  <div>
    <div  style="padding: 10px; margin-top: 70px ; margin-left: 150px" >
      <el-table
          :data="tableData"
          style="width: 90%"
          border="true"
      >
        <el-table-column prop="time" label=""   width="80px"/>
        <el-table-column prop="one" label="星期一"   />
        <el-table-column prop="two" label="星期二"  />
        <el-table-column prop="three" label="星期三"  />
        <el-table-column prop="four" label="星期四"  />
        <el-table-column prop="five" label="星期五"  />
        <el-table-column prop="six" label="星期六"   />
        <el-table-column prop="seven" label="星期日"   />
      </el-table>
    </div>
  </div>
</template>

<script>
import request from "../../untils/request";

export default{
  name:"classSchedule",
  data(){
    return{
      form: {},
      tableData:[],
    }
  },
  created() {
    this.load()
  },
  methods:{
    load(){
      let str = sessionStorage.getItem("user")
      this.form = JSON.parse(str)
      request.get("/chooselist/schedule",{
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
  }
}
</script>

<style scoped>

</style>