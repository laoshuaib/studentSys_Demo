<template>
<div>
  <el-menu
      style="width: 200px;min-height:calc(100vh - 1000px) "
      active-text-color="#1E90FF"
      background-color="#008080"
      default-active="addclass"
      router
      :default-openeds="[1]"
      class="el-menu-vertical-demo"
  >
      <el-sub-menu index="1">
        <template #title>选课</template>
        <el-menu-item index="addclass">新增课程</el-menu-item>
        <el-menu-item index="classschedule">已选课程</el-menu-item>
        <el-menu-item index="deleteclass">退选课程</el-menu-item>
      </el-sub-menu>
    <el-menu-item index="grades">
      <el-icon><icon-menu /></el-icon>
      <span>成绩查看</span>
    </el-menu-item>
  </el-menu>
</div>
</template>

<script>
export default {
  name: "Aside"
}
</script>

<style scoped>

</style>