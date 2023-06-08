<template>
    <el-card shadow="never" class="index">
        <template #header>
            <div class="card_header">
                <b>储蓄账户</b>
                <div>
                    <el-button color="#056DE8" @click="addDialogFormVisible = true">增加</el-button>
                    <el-button color="#056DE8" @click="searchDialogFormVisible = true">搜索</el-button>
                    <el-button color="#87CEEB" @click="savingDialogFormVisible = true" >存钱</el-button>
                    <el-button color="#87CEEB" @click="withdrawDialogFormVisible = true" >取钱</el-button>
                </div>
            </div>
        </template>
        <!-- <el-empty description="暂无数据"></el-empty> -->

        <el-table :data="tableData" stripe style="width: 100%">
            <el-table-column prop="account_id" label="账户号"></el-table-column>
            <el-table-column prop="client_id" label="客户身份证号" width="200"></el-table-column>
            <el-table-column prop="bank_name" label="支行名称"></el-table-column>
            <el-table-column label="密码">
                <template #default="scope">
                    <el-input v-show="scope.row.showmode" v-model="scope.row.password"></el-input>
                    <p v-show="!scope.row.showmode">{{ scope.row.password }}</p>
                </template>
            </el-table-column>
            <el-table-column prop="remaining" label="余额"></el-table-column>
            <el-table-column prop="open_date" label="开户日期"></el-table-column>
            <el-table-column prop="interest_rate" label="利率"></el-table-column>
            <el-table-column fixed="right" label="操作" width="250">
                <template #default="scope">
                    
                    <el-button @click="scope.row.showmode = true" type='primary' size="small">修改密码</el-button>
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
                <el-form-item label="客户身份证号" label-width=100px>
                    <el-input v-model="addForm.client_id" autocomplete="off" />
                </el-form-item>
                <el-form-item label="支行名称" label-width=100px>
                    <el-input v-model="addForm.bank_name" autocomplete="off" />
                </el-form-item>
                <el-form-item label="密码" label-width=100px>
                    <el-input v-model="addForm.password" autocomplete="off" />
                </el-form-item>
                <el-form-item label="余额" label-width=100px>
                    <el-input v-model="addForm.remaining" autocomplete="off" />
                </el-form-item>
                <el-form-item label="利率" label-width=100px>
                    <el-input v-model="addForm.interest_rate" autocomplete="off" />
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
                <el-form-item label="账户号" label-width=100px>
                    <el-input v-model="searchForm.account_id" autocomplete="off" />
                </el-form-item>
                <el-form-item label="客户身份证号" label-width=100px>
                    <el-input v-model="searchForm.client_id" autocomplete="off" />
                </el-form-item>
                <el-form-item label="支行名称" label-width=100px>
                    <el-input v-model="searchForm.bank_name" autocomplete="off" />
                </el-form-item>
                <el-form-item label="密码" label-width=100px>
                    <el-input v-model="searchForm.password" autocomplete="off" />
                </el-form-item>
                <el-form-item label="余额" label-width=100px>
                    <el-input v-model="searchForm.remaining" autocomplete="off" />
                </el-form-item>
                <el-form-item label="开户日期" label-width=100px>
                    <el-input v-model="searchForm.open_date" autocomplete="off" />
                </el-form-item>
                <el-form-item label="利率" label-width=100px>
                    <el-input v-model="searchForm.interest_rate" autocomplete="off" />
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="searchDialogFormVisible = false">取消</el-button>
                    <el-button type="primary" @click="handleSearch()">确定</el-button>
                </span>
            </template>
        </el-dialog>

        <el-dialog v-model="savingDialogFormVisible" title="存钱">
            <el-form :model="interactForm">
                <el-form-item label="账户号" label-width=100px>
                    <el-input v-model="interactForm.account_id" autocomplete="off" />
                </el-form-item>
                <el-form-item label="存入金额" label-width=100px>
                    <el-input v-model="interactForm.money" autocomplete="off" />
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="savingDialogFormVisible = false">取消</el-button>
                    <el-button type="primary" @click="handleSaving()">确定</el-button>
                </span>
            </template>
        </el-dialog>

        <el-dialog v-model="withdrawDialogFormVisible" title="取钱">
            <el-form :model="interactForm">
                <el-form-item label="账户号" label-width=100px>
                    <el-input v-model="interactForm.account_id" autocomplete="off" />
                </el-form-item>
                <el-form-item label="取出金额" label-width=100px>
                    <el-input v-model="interactForm.money" autocomplete="off" />
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="withdrawDialogFormVisible = false">取消</el-button>
                    <el-button type="primary" @click="handleWithdraw()">确定</el-button>
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
        const savingDialogFormVisible = ref(false);
        const withdrawDialogFormVisible = ref(false);
        const addForm = reactive({
            account_id: "",
            client_id: "",
            bank_name: "",
            password: "",
            remaining: "",
            open_date: "",
            interest_rate: "",
        });
        const searchForm = reactive({
            account_id: "",
            client_id: "",
            bank_name: "",
            password: "",
            remaining: "",
            open_date: "",
            interest_rate: "",
        });
        const interactForm = reactive({
            account_id: "",
            money: "",
        });
        const currentPage = ref(1);
        const pageSize = ref(2);
        const count = ref(0);
        const baseurl = "/savingAccount";

        const load = () => {
            request({ url: baseurl + "/page", method: "post", params: { page: currentPage.value, size: pageSize.value }, data: searchForm }).then(res => {
                if (res.data.code == 200) {
                    tableData.value = res.data.data.data;
                    count.value = res.data.data.count;
                } else {
                    ElMessage.error(res.data.code + "：" + res.data.message);
                }
            }).catch(err => {
                ElMessage.error(err);
            });
        };

        onMounted(() => {
            load();
        });

        const handleEdit = (data) => {
            request.post(baseurl + "/edit", data).then(res => {
                load();
                if (res.data.code == 200) {
                    ElMessage.success(res.data.message);
                } else {
                    ElMessage.error(res.data.code + "：" + res.data.message);
                }
            }).catch(err => {
                ElMessage.error(err);
            });
            data.showmode = false;
        };

        const handleDelete = (data) => {
            request.post(baseurl + "/delete", data).then(res => {
                load();
                if (res.data.code == 200) {
                    ElMessage.success(res.data.message);
                } else {
                    ElMessage.error(res.data.code + "：" + res.data.message);
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
                load();
                if (res.data.code == 200) {
                    ElMessage.success(res.data.message);
                } else {
                    ElMessage.error(res.data.code + "：" + res.data.message);
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

        const handleSaving = () => {
            request.post(baseurl + "/saving", interactForm).then(res => {
                load();
                if (res.data.code == 200) {
                    ElMessage.success(res.data.message);
                } else {
                    ElMessage.error(res.data.code + "：" + res.data.message);
                }
            }).catch(err => {
                ElMessage.error(err);
            });
            savingDialogFormVisible.value = false;
            Object.keys(interactForm).forEach(key => {
                interactForm[key] = "";
            });
        };

        const handleWithdraw = () => {
            request.post(baseurl + "/withdraw", interactForm).then(res => {
                load();
                if (res.data.code == 200) {
                    ElMessage.success(res.data.message);
                } else {
                    ElMessage.error(res.data.code + "：" + res.data.message);
                }
            }).catch(err => {
                ElMessage.error(err);
            });
            withdrawDialogFormVisible.value = false;
            Object.keys(interactForm).forEach(key => {
                interactForm[key] = "";
            });
        };

        return {
            tableData,
            addDialogFormVisible,
            searchDialogFormVisible,
            savingDialogFormVisible,
            withdrawDialogFormVisible,
            addForm,
            searchForm,
            interactForm,
            currentPage,
            pageSize,
            count,
            handleEdit,
            handleDelete,
            handleSizeChange,
            handleCurrentChange,
            handleAdd,
            handleSearch,
            handleSaving,
            handleWithdraw,
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
