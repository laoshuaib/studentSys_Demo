<template>
  <el-config-provider :locale="locale">
  <div>
    <router-view />
  </div>
  </el-config-provider>
</template>

<script >

import { ElConfigProvider } from 'element-plus'
import zhCn from 'element-plus/lib/locale/lang/zh-cn'

export default {
  name:"App",
  components:{
    [ElConfigProvider.name]: ElConfigProvider
  },
  setup() {

    // 切换为中文
    let locale = zhCn

    return {
      locale
    }
  }
}
</script>
