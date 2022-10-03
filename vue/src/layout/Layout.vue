<template>
<div>
  <!--头部-->
  <Header />
  <!--主体-->

  <div style="display: flex">
    <!--      侧边栏-->
    <Aside v-if="identity==='学生'" />
    <TeacherAside v-else />
    <!--      内容区域-->
    <router-view  style="flex: 1"/>
  </div>
</div>
</template>

<script>
import Header from "@/components/Header";
import Aside from "@/components/Aside";
import TeacherAside from "@/components/TeacherAside";
export default {
  name: "Layout",
  components:{
    Header,
    Aside,
    TeacherAside,
  },
  data(){
    let str = sessionStorage.getItem("user");
    let identity = JSON.parse(str).identity
    return{
      identity
    }
  }


}
</script>

<style scoped>

</style>