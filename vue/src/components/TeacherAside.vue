<template>
  <div>
  <el-menu
      style="width: 200px;min-height:calc(100vh - 1000px) "
      active-text-color="#1E90FF"
      background-color="#008080"
      default-active="addlesson"
      router
      :default-openeds="[1]"
      class="el-menu-vertical-demo"
  >
    <el-sub-menu index="1">
      <template #title>课程管理</template>
      <el-menu-item index="addlesson">新增课程</el-menu-item>
      <el-menu-item index="classmanage">课程管理</el-menu-item>
    </el-sub-menu>
    <el-sub-menu index="2">
      <template #title>成绩管理</template>
      <el-menu-item index="gradeinput">成绩录入</el-menu-item>
      <el-menu-item index="gradeup">成绩提交</el-menu-item>
      <el-menu-item index="gradeview">成绩查看</el-menu-item>
    </el-sub-menu>
    <el-menu-item index="studentlist">查看学生</el-menu-item>
  </el-menu>
  </div>
</template>

<script>
export default {
  name: "TeacherAside"
}
</script>

<style scoped>

</style>