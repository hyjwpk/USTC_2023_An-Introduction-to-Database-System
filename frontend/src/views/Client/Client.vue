<template>
    <el-card shadow="never" class="index">
        <template #header>
            <div class="card_header">
                <b>客户</b>
                <div>
                    <el-button color="#056DE8" @click="addDialogFormVisible = true">增加</el-button>
                    <el-button color="#056DE8" @click="searchDialogFormVisible = true">搜索</el-button>
                </div>
            </div>
        </template>
        <!-- <el-empty description="暂无数据"></el-empty> -->

        <el-table :data="tableData" stripe style="width: 100%">
            <el-table-column prop="client_ID" label="身份证号"></el-table-column>
            <el-table-column label="真实姓名">
                <template #default="scope">
                    <el-input v-show="scope.row.showmode" v-model="scope.row.real_name"></el-input>
                    <p v-show="!scope.row.showmode">{{ scope.row.real_name }}</p>
                </template>
            </el-table-column>
            <el-table-column label="手机号">
                <template #default="scope">
                    <el-input v-show="scope.row.showmode" v-model="scope.row.client_phone"></el-input>
                    <p v-show="!scope.row.showmode">{{ scope.row.client_phone }}</p>
                </template>
            </el-table-column>
            <el-table-column label="住址">
                <template #default="scope">
                    <el-input v-show="scope.row.showmode" v-model="scope.row.client_address"></el-input>
                    <p v-show="!scope.row.showmode">{{ scope.row.client_address }}</p>
                </template>
            </el-table-column>
            <el-table-column label="邮箱">
                <template #default="scope">
                    <el-input v-show="scope.row.showmode" v-model="scope.row.client_email"></el-input>
                    <p v-show="!scope.row.showmode">{{ scope.row.client_email }}</p>
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

        <el-dialog v-model="addDialogFormVisible" title="增加">
            <el-form :model="addForm">
                <el-form-item label="身份证号" label-width=100px>
                    <el-input v-model="addForm.client_ID" autocomplete="off" />
                </el-form-item>
                <el-form-item label="真实姓名" label-width=100px>
                    <el-input v-model="addForm.real_name" autocomplete="off" />
                </el-form-item>
                <el-form-item label="手机号" label-width=100px>
                    <el-input v-model="addForm.client_phone" autocomplete="off" />
                </el-form-item>
                <el-form-item label="住址" label-width=100px>
                    <el-input v-model="addForm.client_address" autocomplete="off" />
                </el-form-item>
                <el-form-item label="邮箱" label-width=100px>
                    <el-input v-model="addForm.client_email" autocomplete="off" />
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="addDialogFormVisible = false">取消</el-button>
                    <el-button type="primary" @click="handleAdd()">确定</el-button>
                </span>
            </template>
        </el-dialog>

        <el-dialog v-model="searchDialogFormVisible" title="搜索">
            <el-form :model="searchForm">
                <el-form-item label="身份证号" label-width=100px>
                    <el-input v-model="searchForm.client_ID" autocomplete="off" />
                </el-form-item>
                <el-form-item label="真实姓名" label-width=100px>
                    <el-input v-model="searchForm.real_name" autocomplete="off" />
                </el-form-item>
                <el-form-item label="手机号" label-width=100px>
                    <el-input v-model="searchForm.client_phone" autocomplete="off" />
                </el-form-item>
                <el-form-item label="住址" label-width=100px>
                    <el-input v-model="searchForm.client_address" autocomplete="off" />
                </el-form-item>
                <el-form-item label="邮箱" label-width=100px>
                    <el-input v-model="searchForm.client_email" autocomplete="off" />
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="searchDialogFormVisible = false">取消</el-button>
                    <el-button type="primary" @click="handleSearch()">确定</el-button>
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
        const addDialogFormVisible = ref(false);
        const searchDialogFormVisible = ref(false);
        const addForm = reactive({
            client_ID: "",
            real_name: "",
            client_phone: "",
            client_address: "",
            client_email: "",
        });
        const searchForm = reactive({
            client_ID: "",
            real_name: "",
            client_phone: "",
            client_address: "",
            client_email: "",
        });
        const currentPage = ref(1);
        const pageSize = ref(2);
        const count = ref(0);
        const name = ref("");

        const load = () => {
            request.get("/client/page", { params: { page: currentPage.value, size: pageSize.value, client_ID: searchForm.client_ID, real_name: searchForm.real_name, client_phone: searchForm.client_phone, client_address: searchForm.client_address, client_email: searchForm.client_email } }).then(res => {
                tableData.value = res.data.data;
                count.value = res.data.count;
            }).catch(err => {
                ElMessage.error(err);
            });
        };

        onMounted(() => {
            load();
        });

        const handleEdit = (data) => {
            request.post("/client/edit", { client_ID: data.client_ID, real_name: data.real_name, client_phone: data.client_phone, client_address: data.client_address, client_email: data.client_email }).then(res => {
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
            request.post("/client/delete", { client_ID: data.client_ID }).then(res => {
                if (res.data.code == 0) {
                    ElMessage.success(res.data.message);
                    load();
                } else {
                    ElMessage.error(res.data.message);
                }
            }).catch(err => {
                ElMessage.error(err);
            });
        };

        const handleSizeChange = (number) => {
            pageSize.value = number;
            load();
        };

        const handleCurrentChange = (number) => {
            currentPage.value = number;
            load();
        };

        const handleAdd = () => {
            request.post("/client/add", { client_ID: addForm.client_ID, real_name: addForm.real_name, client_phone: addForm.client_phone, client_address: addForm.client_address, client_email: addForm.client_email }).then(res => {
                if (res.data.code == 0) {
                    ElMessage.success(res.data.message);
                    load();
                } else {
                    ElMessage.error(res.data.message);
                }
            }).catch(err => {
                ElMessage.error(err);
            });
            addDialogFormVisible.value = false;
            addForm.client_ID = "";
            addForm.real_name = "";
            addForm.client_phone = "";
            addForm.client_address = "";
            addForm.client_email = "";
        };

        const handleSearch = () => {
            name.value = searchForm.name;
            load();
            searchDialogFormVisible.value = false;
            searchForm.client_ID = "";
            searchForm.real_name = "";
            searchForm.client_phone = "";
            searchForm.client_address = "";
            searchForm.client_email = "";
        };
        return {
            tableData,
            addDialogFormVisible,
            searchDialogFormVisible,
            addForm,
            searchForm,
            currentPage,
            pageSize,
            count,
            handleEdit,
            handleDelete,
            handleSizeChange,
            handleCurrentChange,
            handleAdd,
            handleSearch,
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
