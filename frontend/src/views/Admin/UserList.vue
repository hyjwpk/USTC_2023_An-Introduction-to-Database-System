<template>
    <el-card shadow="never" class="index">
        <template #header>
            <div class="card_header">
                <b>用户列表</b>
            </div>
        </template>
        <!-- <el-empty description="暂无数据"></el-empty> -->
        <el-table :data="tableData" stripe style="width: 100%">
            <el-table-column prop="name" label="用户名"></el-table-column>
            <el-table-column label="密码">
                <template #default="scope">
                    <el-input v-show="scope.row.showmode" v-model="scope.row.password"></el-input>
                    <p v-show="!scope.row.showmode">{{ scope.row.password }}</p>
                </template>
            </el-table-column>
            <el-table-column label="操作">
                <template #default="scope">
                    <el-button @click="scope.row.showmode = true" type='primary' size="small">编辑</el-button>
                    <el-button @click="handleEdit(scope.row)" type='primary' size="small">保存</el-button>
                </template>
            </el-table-column>
        </el-table>
    </el-card>
</template>

<script>
import { ref, onMounted } from "vue";
import axios from "axios";
import { reactive } from "vue";
import { ElMessage } from "element-plus";

export default {
    setup() {
        const request = axios.create({
            baseURL: "",
            timeout: 5000,
        });
        const tableData = reactive([])

        onMounted(() => {
            request.get("/api/getUserList").then((res) => {
                tableData.push(...res.data.data);
            });
            console.log("test");
        });

        const handleEdit = (data) => {
            request.post("/api/editUser", { name: data.name, password: data.password }).then(res => {
                if (res.data.code == 0) {
                    ElMessage.success("修改成功");
                } else {
                    ElMessage.error(res.data.msg);
                }
            });
            data.showmode = false;
        };
        return {
            tableData,
            handleEdit
        };
    }
}
</script>

<style lang="scss" scoped>
.card_header {
    display: flex;
    align-items: center;
    justify-content: space-between;
}
</style>
