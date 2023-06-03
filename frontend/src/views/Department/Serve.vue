<template>
    <el-card shadow="never" class="index">
        <template #header>
            <div class="card_header">
                <b>服务</b>
                <div>
                    <el-button color="#056DE8" @click="addDialogFormVisible = true">增加</el-button>
                    <el-button color="#056DE8" @click="searchDialogFormVisible = true">搜索</el-button>
                </div>
            </div>
        </template>
        <!-- <el-empty description="暂无数据"></el-empty> -->

        <el-table :data="tableData" stripe style="width: 100%">
            <el-table-column prop="id" label="员工号"></el-table-column>
            <el-table-column prop="client_id" label="客户身份证号"></el-table-column>
            <el-table-column fixed="right" label="操作" width="200">
                <template #default="scope">
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
                <el-form-item label="员工号" label-width=100px>
                    <el-input v-model="addForm.id" autocomplete="off" />
                </el-form-item>
                <el-form-item label="客户身份证号" label-width=100px>
                    <el-input v-model="addForm.client_id" autocomplete="off" />
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
                <el-form-item label="员工号" label-width=100px>
                    <el-input v-model="searchForm.id" autocomplete="off" />
                </el-form-item>
                <el-form-item label="客户身份证号" label-width=100px>
                    <el-input v-model="searchForm.client_id" autocomplete="off" />
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
            id: "",
            client_id: "",
        });
        const searchForm = reactive({
            id: "",
            client_id: "",
        });
        const currentPage = ref(1);
        const pageSize = ref(2);
        const count = ref(0);
        const baseurl = "/serve";

        const load = () => {
            request({ url: baseurl + "/page", method: "post", params: { page: currentPage.value, size: pageSize.value }, data: searchForm }).then(res => {
                tableData.value = res.data.data;
                count.value = res.data.count;
            }).catch(err => {
                ElMessage.error(err);
            });
        };

        onMounted(() => {
            load();
        });

        const handleDelete = (data) => {
            request.post(baseurl + "/delete", data).then(res => {
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
            request.post(baseurl + "/add", addForm).then(res => {
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
            Object.keys(addForm).forEach(key => {
                addForm[key] = "";
            });
        };

        const handleSearch = () => {
            load();
            searchDialogFormVisible.value = false;
            Object.keys(searchForm).forEach(key => {
                searchForm[key] = "";
            });
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
