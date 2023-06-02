<template>
  <el-card shadow="never" class="index">
    <template #header>
      <div class="card_header">
        <b>支付情况</b>
      </div>
    </template>
    <el-empty description="暂无数据"></el-empty>
  </el-card>
</template>

<style lang="scss" scoped>
.card_header {
  display: flex;
  align-items: center;
  justify-content: space-between;
}
</style>
