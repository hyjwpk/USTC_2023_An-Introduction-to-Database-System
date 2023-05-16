<template>
    <el-card shadow="never" class="index">
        <template #header>
            <div class="card_header">
                <b>用户列表</b>
                <el-button color="#056DE8" @click="dialogFormVisible = true">增加用户</el-button>
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
                    <el-button @click="handleDelete(scope.row)" type='primary' size="small">删除</el-button>
                </template>
            </el-table-column>
        </el-table>

        <el-dialog v-model="dialogFormVisible" title="增加用户">
            <el-form :model="form">
                <el-form-item label="用户名" label-width=100px>
                    <el-input v-model="form.name" autocomplete="off" />
                </el-form-item>
                <el-form-item label="密码" label-width=100px>
                    <el-input v-model="form.password" autocomplete="off" />
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="dialogFormVisible = false">取消</el-button>
                    <el-button type="primary" @click="handleAdd()">确定</el-button>
                </span>
            </template>
        </el-dialog>
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
        const tableData = ref([])
        const dialogFormVisible = ref(false);
        const form = reactive({
            name: "",
            password: "",
        });

        onMounted(() => {
            request.get("/api/getUserList").then((res) => {
                tableData.value = res.data.data;
            });
        });

        const handleEdit = (data) => {
            request.post("/api/editUser", { name: data.name, password: data.password }).then(res => {
                if (res.data.code == 0) {
                    ElMessage.success(res.data.message);
                } else {
                    ElMessage.error(res.data.message);
                }
            });
            data.showmode = false;
        };

        const handleDelete = (data) => {
            request.post("/api/deleteUser", { name: data.name }).then(res => {
                if (res.data.code == 0) {
                    ElMessage.success(res.data.message);
                } else {
                    ElMessage.error(res.data.message);
                }
            });
            tableData.value = tableData.value.filter(item => item.name != data.name);
        };

        const handleAdd = () => {
            request.post("/api/addUser", { name: form.name, password: form.password }).then(res => {
                if (res.data.code == 0) {
                    ElMessage.success(res.data.message);
                } else {
                    ElMessage.error(res.data.message);
                }
            });
            dialogFormVisible.value = false;
            tableData.value.push({ name: form.name, password: form.password });
        };
        return {
            tableData,
            dialogFormVisible,
            form,
            handleEdit,
            handleDelete,
            handleAdd
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
