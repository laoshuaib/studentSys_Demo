<template>
<div style="height: 50px;line-height:50px;border-bottom:1px solid #ccc;display: flex;background: #008080">
  <div style="width: 200px;padding-left:30px;font-weight: bold;color:dodgerblue; ">学生信息管理系统</div>
  <div style="flex: 1"></div>
  <div style="width: 100px">
    <el-dropdown>
    <span class="el-dropdown-link">
      {{ form.username }}
      <i class="el-icon-arrow-down el-icon--right"></i>
    </span>
      <template #dropdown>
        <el-dropdown-menu>
          <el-dropdown-item @click="openDialog">个人信息</el-dropdown-item>
          <el-dropdown-item @click="$router.push('/login')" divided >退出系统</el-dropdown-item>
        </el-dropdown-menu>
      </template>
    </el-dropdown>

    <el-dialog v-model="dialogVisible" title="个人信息" width="30%"  custom-class="el-dialog">
      <el-form ref="form" :model="Tform" label-width="80px">
        <el-form-item label="学号">
          <el-input v-model="Tform.number" disabled></el-input>
        </el-form-item>
        <el-form-item label="姓名">
          <el-input v-model="Tform.name"></el-input>
        </el-form-item>
        <el-form-item label="年级">
          <el-input v-model="Tform.grade" ></el-input>
        </el-form-item>
        <el-form-item label="专业">
          <el-input v-model="Tform.major"></el-input>
        </el-form-item>
        <el-form-item label="地址">
          <el-input v-model="Tform.address"></el-input>
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="Tform.email" ></el-input>
        </el-form-item>
        <el-form-item label="手机">
          <el-input v-model="Tform.phoneNumber" ></el-input>
        </el-form-item>
        <el-form-item label="生日">
          <el-input v-model="Tform.birthday" ></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="form.password" show-password></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="save">保存</el-button>
      </span>
      </template>
    </el-dialog>

    <el-dialog v-model="TdialogVisible" title="个人信息" width="30%"  custom-class="el-dialog">
      <el-form ref="form" :model="Tform" label-width="80px">
        <el-form-item label="工号">
          <el-input v-model="Tform.number" disabled></el-input>
        </el-form-item>
        <el-form-item label="姓名">
          <el-input v-model="Tform.name"></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="form.password" show-password></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="Tsave">保存</el-button>
      </span>
      </template>
    </el-dialog>
  </div>
</div>
</template>

<script>
import request from "../../untils/request";

export default {
  name: "Header",
  components:{},
  data(){
    return{
      dialogVisible:false,//控制学生弹窗
      TdialogVisible:false,//控制老师弹窗
      form: {},//用于获取user
      tableData:[],
      Tform:{}//用于传输teacher或student
    }
  },
  created() {
    let str = sessionStorage.getItem("user");
    this.form = JSON.parse(str)
    if(this.form.identity==="老师"){
      this.loadTeacher()
    }
    if(this.form.identity==="学生"){
      this.loadStudent()
    }
  },
  methods:{
    openDialog(){
      if(this.form.identity==="老师"){
        this.TdialogVisible=true
      }
      if(this.form.identity==="学生"){
        this.dialogVisible=true
      }
    },

    loadStudent(){
      request.get("/student",{
        params:{
          id:this.form.id
        }
      }).then(res=>{
        console.log(res)
        this.Tform=JSON.parse(JSON.stringify(res.data))
      })
    },
    loadTeacher(){
      request.get("/teacher",{
        params:{
          id:this.form.id
        }
      }).then(res=>{
        console.log(res)
        this.Tform=JSON.parse(JSON.stringify(res.data))
      })
    },
    save(){//更新
      this.form.username=this.Tform.name
      request.put("/user",this.form).then(res =>{
        console.log(res)
        if(res.code ==="0"){
          sessionStorage.removeItem("user")
          sessionStorage.setItem("user",JSON.stringify(res.data))//缓存用户信息
          let str = sessionStorage.getItem("user")
          this.form = JSON.parse(str)
          this.dialogVisible=false
        }
        else {
          this.$message({
            type:"error",
            message:res.msg
          })
        }
      })
      request.put("/student",this.Tform).then(res =>{
        console.log(res)
        if(res.code ==="0"){
          this.$message({
            type:"success",
            message:"更新成功"
          })
          this.dialogVisible=false
          this.tableData=res.data
        }
        else {
          this.$message({
            type:"error",
            message:res.msg
          })
        }
      })
    },

    Tsave(){//更新
      this.form.username=this.Tform.name
      request.put("/user",this.form).then(res =>{
        console.log(res)
        if(res.code ==="0"){
          sessionStorage.removeItem("user")
          sessionStorage.setItem("user",JSON.stringify(res.data))//缓存用户信息
          let str = sessionStorage.getItem("user")
          this.form = JSON.parse(str)
        }
        else {
          this.$message({
            type:"error",
            message:res.msg
          })
        }
      })
      request.put("/teacher",this.Tform).then(res =>{
        console.log(res)
        if(res.code ==="0"){
          this.$message({
            type:"success",
            message:"更新成功"
          })
          this.TdialogVisible=false
          this.tableData=res.data
        }
        else {
          this.$message({
            type:"error",
            message:res.msg
          })
        }
      })

    }
  }
}

</script>

<style>
/* dialog自定义*/
/*绝对居中*/
.el-dialog{
  display: flex;
  flex-direction: column;
  margin:0 !important;
  position:absolute;
  top:50%;
  left:50%;
  transform:translate(-50%,-50%);
}
.el-dialog>>> .el-dialog__body{
  flex:1;
  overflow: auto;
}

</style>