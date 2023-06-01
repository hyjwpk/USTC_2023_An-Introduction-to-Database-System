<template>
    <el-card shadow="never" class="index">
        <template #header>
            <div class="card_header">
                <b>用户列表</b>
                <div>
                    <el-button color="#056DE8" @click="dialogFormVisible = true">增加用户</el-button>
                    <el-button color="#056DE8" @click="dialogFormVisible = true">搜索</el-button>
                </div>
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
                    <el-button @click="handleEdit(scope.row)" type='success' size="small">保存</el-button>
                    <el-button @click="handleDelete(scope.row)" type='danger' size="small">删除</el-button>
                </template>
            </el-table-column>
        </el-table>

        <div style="padding: 10px 0">
            <el-pagination v-model:current-page="currentPage" v-model:page-size="pageSize" :page-sizes="[2, 4, 10, 20]"
                layout="total, sizes, prev, pager, next, jumper" :total="count" @size-change="handleSizeChange"
                @current-change="handleCurrentChange" />
        </div>

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
import request from "@/utils/axios";
import { reactive } from "vue";
import { ElMessage } from "element-plus";

export default {
    setup() {
        const tableData = ref([])
        const dialogFormVisible = ref(false);
        const form = reactive({
            name: "",
            password: "",
        });
        const currentPage = ref(1);
        const pageSize = ref(2);
        const count = ref(0);

        onMounted(() => {
            request.get("/api/page", { params: { page: currentPage.value, size: pageSize.value } }).then(res => {
                tableData.value = res.data.data;
                count.value = res.data.count;
            }).catch(err => {
                ElMessage.error(err);
            });
        });

        const handleEdit = (data) => {
            request.post("/api/editUser", { name: data.name, password: data.password }).then(res => {
                if (res.data.code == 0) {
                    ElMessage.success(res.data.message);
                } else {
                    ElMessage.error(res.data.message);
                }
            }).catch(err => {
                ElMessage.error(err);
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
            }).catch(err => {
                ElMessage.error(err);
            });
            tableData.value = tableData.value.filter(item => item.name != data.name);
        };

        const handleSizeChange = (number) => {
            pageSize.value = number;
            request.get("/api/page", { params: { page: currentPage.value, size: pageSize.value } }).then(res => {
                tableData.value = res.data.data;
                count.value = res.data.count;
            }).catch(err => {
                ElMessage.error(err);
            });
        };

        const handleCurrentChange = (number) => {
            currentPage.value = number;
            request.get("/api/page", { params: { page: currentPage.value, size: pageSize.value } }).then(res => {
                tableData.value = res.data.data;
                count.value = res.data.count;
            }).catch(err => {
                ElMessage.error(err);
            });
        };

        const handleAdd = () => {
            request.post("/api/addUser", { name: form.name, password: form.password }).then(res => {
                if (res.data.code == 0) {
                    ElMessage.success(res.data.message);
                } else {
                    ElMessage.error(res.data.message);
                }
            }).catch(err => {
                ElMessage.error(err);
            });
            dialogFormVisible.value = false;
            tableData.value.push({ name: form.name, password: form.password });
        };
        return {
            tableData,
            dialogFormVisible,
            form,
            currentPage,
            pageSize,
            count,
            handleEdit,
            handleDelete,
            handleSizeChange,
            handleCurrentChange,
            handleAdd,
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
