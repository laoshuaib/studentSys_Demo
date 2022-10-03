<template>
<div :style="{width: '100%', height: '100vh', overflow: 'hidden', background: 'url(' +bg+ ')', backgroundSize: 'cover'}">
  <div style="width: 400px;margin: 100px auto">
    <div style="color: #cccccc;font-size: 30px;text-align: center; padding: 30px 0">
      注册
    </div>
    <el-form ref="form" :model="form" size="normal" :rules="rules">
      <el-form-item prop="username">
        <el-input placeholder="请输入姓名"  v-model="form.username"></el-input>
      </el-form-item>
      <el-form-item prop="number">
        <el-input placeholder="请输入学号或工号"  v-model="form.number"></el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input placeholder="请输入密码" v-model="form.password" show-password></el-input>
      </el-form-item>
      <el-form-item prop="confirm">
        <el-input placeholder="请再次输入密码" v-model="form.confirm" show-password></el-input>
      </el-form-item>
      <el-form-item prop="identity" >
        <el-radio v-model="form.identity" label="老师" style="width: 40%" >老师</el-radio>
        <el-radio v-model="form.identity" label="学生" style="width: 40%">学生</el-radio>
      </el-form-item>
      <el-form-item>
        <el-button style="width: 100% " type="primary" @click="register">注册</el-button>
      </el-form-item>
      <el-button type="text" @click="toLogin">已有账号？立即登录！</el-button>
    </el-form>
  </div>
</div>
</template>

<script>
import request from "../../untils/request";

export default {
  name: "Register",
  data(){
    return{
      bg: require("../assets/basealt.jpg"),
      form:{},
      rules: {
        username: [
          {
            required: true,
            message: '请输入姓名',
            trigger: 'blur',
          },
        ],
        number: [
          {
            required: true,
            message: '请输入学号或工号',
            trigger: 'blur',
          },
        ],
        password: [
          {
            required: true,
            message: '请输入密码',
            trigger: 'blur',
          },
        ],
        confirm: [
          {
            required: true,
            message: '请再次输入密码',
            trigger: 'blur',
          },
        ],
        identity:[
          {
            required: true,
            message: '请确认身份',
            trigger: 'blur',
          }
        ]
      }
    }
  },

  methods:{
    toLogin(){
      this.$router.push("/login")
    },
    register(){
      this.$refs['form'].validate((valid) => {
        if (valid) {
          if(this.form.password !==this.form.confirm){
            this.$message({
              type:"error",
              message:'两次密码输入不一致'
            })
            return
          }
          if(this.form.password.length<6){
            this.$message({
              type:"error",
              message:'密码过短（应大于6位）'
            })
            return
          }
          request.post("/user/register",this.form).then(res=>{
            if(res.code ==="0"){
              this.$message({
                type:"success",
                message:"注册成功"
              })
              this.$router.push("/login")//登录成功页面跳转
            }
            else {
              this.$message({
                type:"error",
                message:res.msg
              })
            }
          })
        }
      })
    }
  }

}
</script>

<style scoped>
/deep/ .el-form-item {
  background-color: unset !important;
}
/deep/ .el-input input {
  color: #fff !important;
}
/deep/ .el-input__inner {
     height: 47px;
     background-color: rgba(255, 255, 255, 0.247);
}
</style>
<style>
.el-radio__input + .el-radio__label {
  color: #ffffff !important;
}

</style>