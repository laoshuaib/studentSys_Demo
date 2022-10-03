<template>
  <div
      :style="{
      width: '100%',
      height: '100vh',
      overflow: 'hidden',
      background: 'url(' + bg + ')',
      backgroundSize: 'cover',
    }"
  >
    <div style="width: 400px; margin: 150px auto">
      <div
          style="
          color: #cccccc;
          font-size: 30px;
          text-align: center;
          padding: 30px 0;
        "
      >
        登录
      </div>
      <el-form ref="form" :model="form" size="default" :rules="rules">
        <el-form-item prop="number">
          <el-input
              prefix-icon="el-icon-user-solid"
              v-model="form.number"
          ></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
              prefix-icon="el-icon-lock"
              v-model="form.password"
              show-password
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button style="width: 100%" type="primary" @click="login"
          >登录</el-button
          >
        </el-form-item>
        <el-button type="text" @click="toRegister"
        >没有账号？注册一个！</el-button
        >
      </el-form>
    </div>
  </div>
</template>
<script>
import request from "../../untils/request";

export default {
  name: "Login",
  data(){
    return{
      bg: require("../assets/basealt.jpg"),
      form:{},
      rules: {
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
      }
    }
  },

  created() {
    sessionStorage.removeItem("user")
  },
  methods:{
    toRegister(){
      this.$router.push("/register")
    },
    login(){
      this.$refs['form'].validate((valid) => {
        if (valid) {
          console.log(this.form)
          request.post("/user/login",this.form).then(res=>{
            if(res.code ==="0"){
              this.$message({
                type:"success",
                message:"登录成功"
              })
              sessionStorage.setItem("user",JSON.stringify(res.data))//缓存用户信息
              if(res.data.identity==="学生"){
                this.$router.push("/addclass")//学生登录成功页面跳转
              }
              else {
                this.$router.push("/addlesson")//老师登录成功页面跳转
              }
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