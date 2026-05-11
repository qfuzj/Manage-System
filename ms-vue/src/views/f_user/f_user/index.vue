<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="用户名" prop="username">
        <el-input
          v-model="queryParams.username"
          placeholder="请输入用户名"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Plus"
          @click="handleAdd"
          v-hasPermi="['system:f_user:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:f_user:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:f_user:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['system:f_user:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="f_userList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="用户编号" align="center" prop="userId" />
      <el-table-column label="用户名" align="center" prop="username" />
      <el-table-column label="密码" align="center" prop="password" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['system:f_user:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['system:f_user:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改前台用户对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="f_userRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="form.username" placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="form.password" placeholder="请输入密码" />
        </el-form-item>
<!--        <el-divider content-position="center">${subTable.functionName}信息</el-divider>-->
        <el-divider content-position="center">用户详情信息</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" icon="Plus" @click="handleAddUserDetail">添加</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" icon="Delete" @click="handleDeleteUserDetail">删除</el-button>
          </el-col>
        </el-row>
        <el-table :data="userDetailList" :row-class-name="rowUserDetailIndex" @selection-change="handleUserDetailSelectionChange" ref="userDetail">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="序号" align="center" prop="index" width="50"/>
          <el-table-column label="邮箱" prop="email" width="150">
            <template #default="scope">
              <el-input v-model="scope.row.email" placeholder="请输入邮箱" />
            </template>
          </el-table-column>
          <el-table-column label="电话" prop="phone" width="150">
            <template #default="scope">
              <el-input v-model="scope.row.phone" placeholder="请输入电话" />
            </template>
          </el-table-column>
        </el-table>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="F_user">
import { listF_user, getF_user, delF_user, addF_user, updateF_user } from "@/api/system/f_user";

const { proxy } = getCurrentInstance();

const f_userList = ref([]);
const userDetailList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const checkedUserDetail = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    username: null,
  },
  rules: {
    username: [
      { required: true, message: "用户名不能为空", trigger: "blur" }
    ],
    password: [
      { required: true, message: "密码不能为空", trigger: "blur" }
    ]
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询前台用户列表 */
function getList() {
  loading.value = true;
  listF_user(queryParams.value).then(response => {
    f_userList.value = response.rows;
    total.value = response.total;
    loading.value = false;
  });
}

// 取消按钮
function cancel() {
  open.value = false;
  reset();
}

// 表单重置
function reset() {
  form.value = {
    userId: null,
    username: null,
    password: null
  };
  userDetailList.value = [];
  proxy.resetForm("f_userRef");
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1;
  getList();
}

/** 重置按钮操作 */
function resetQuery() {
  proxy.resetForm("queryRef");
  handleQuery();
}

// 多选框选中数据
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.userId);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
function handleAdd() {
  reset();
  open.value = true;
  title.value = "添加前台用户";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _userId = row.userId || ids.value
  getF_user(_userId).then(response => {
    form.value = response.data;
    userDetailList.value = response.data.userDetailList;
    open.value = true;
    title.value = "修改前台用户";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["f_userRef"].validate(valid => {
    if (valid) {
      form.value.userDetailList = userDetailList.value;
      if (form.value.userId != null) {
        updateF_user(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addF_user(form.value).then(response => {
          proxy.$modal.msgSuccess("新增成功");
          open.value = false;
          getList();
        });
      }
    }
  });
}

/** 删除按钮操作 */
function handleDelete(row) {
  const _userIds = row.userId || ids.value;
  proxy.$modal.confirm('是否确认删除前台用户编号为"' + _userIds + '"的数据项？').then(function() {
    return delF_user(_userIds);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** ${subTable.functionName}序号 */
function rowUserDetailIndex({ row, rowIndex }) {
  row.index = rowIndex + 1;
}

/** ${subTable.functionName}添加按钮操作 */
function handleAddUserDetail() {
  let obj = {};
  obj.email = "";
  obj.phone = "";
  userDetailList.value.push(obj);
}

/** ${subTable.functionName}删除按钮操作 */
function handleDeleteUserDetail() {
  if (checkedUserDetail.value.length == 0) {
    proxy.$modal.msgError("请先选择要删除的${subTable.functionName}数据");
  } else {
    const userDetails = userDetailList.value;
    const checkedUserDetails = checkedUserDetail.value;
    userDetailList.value = userDetails.filter(function(item) {
      return checkedUserDetails.indexOf(item.index) == -1
    });
  }
}

/** 复选框选中数据 */
function handleUserDetailSelectionChange(selection) {
  checkedUserDetail.value = selection.map(item => item.index)
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('system/f_user/export', {
    ...queryParams.value
  }, `f_user_${new Date().getTime()}.xlsx`)
}

getList();
</script>
