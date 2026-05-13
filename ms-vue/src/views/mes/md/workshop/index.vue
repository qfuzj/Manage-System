<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="车间编码" prop="workshopCode">
        <el-input
          v-model="queryParams.workshopCode"
          placeholder="请输入车间编码"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="车间名称" prop="workshopName">
        <el-input
          v-model="queryParams.workshopName"
          placeholder="请输入车间名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否启用" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择是否启用" clearable style="width: 150px">
          <el-option
            v-for="dict in common_yes_no"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
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
          v-hasPermi="['mes:md:workshop:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['mes:md:workshop:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['mes:md:workshop:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['mes:md:workshop:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="workshopList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="车间编码" align="center" prop="workshopCode">
        <template #default="scope">
          <el-button link type="primary" @click="handleView(scope.row)">{{ scope.row.workshopCode }}</el-button>
        </template>
      </el-table-column>
      <el-table-column label="车间名称" align="center" prop="workshopName" />
      <el-table-column label="车间面积(㎡)" align="center" prop="area" />
      <el-table-column label="负责人" align="center" prop="managerId">
        <template #default="scope">
          {{ managerMap[scope.row.managerId] || scope.row.managerNickname || scope.row.managerName || scope.row.managerId }}
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status">
        <template #default="scope">
          <dict-tag :options="common_yes_no" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['mes:md:workshop:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['mes:md:workshop:remove']">删除</el-button>
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

    <el-dialog v-model="employeePickerOpen" title="选择员工" width="900px" top="5vh" append-to-body>
      <el-form :model="employeeQueryParams" ref="employeeQueryRef" :inline="true" label-width="68px">
        <el-form-item label="姓名" prop="empName">
          <el-input v-model="employeeQueryParams.empName" placeholder="请输入姓名" clearable @keyup.enter="handleEmployeeQuery" />
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="employeeQueryParams.phone" placeholder="请输入手机号" clearable @keyup.enter="handleEmployeeQuery" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="Search" @click="handleEmployeeQuery">搜索</el-button>
          <el-button icon="Refresh" @click="resetEmployeeQuery">重置</el-button>
        </el-form-item>
      </el-form>
      <el-table
        v-loading="employeeLoading"
        :data="employeeList"
        height="360"
        @current-change="handleEmployeeCurrentChange"
        :row-key="getEmployeeRowKey"
        highlight-current-row
      >
        <el-table-column label="选择" width="70" align="center">
          <template #default="scope">
            <el-radio :model-value="selectedEmployeeId" :label="getEmployeeRowKey(scope.row)">{{ '' }}</el-radio>
          </template>
        </el-table-column>
        <el-table-column label="姓名" prop="empName" :show-overflow-tooltip="true" />
        <el-table-column label="昵称" prop="nickname" :show-overflow-tooltip="true" />
        <el-table-column label="手机号" prop="phone" :show-overflow-tooltip="true" />
        <el-table-column label="邮箱" prop="email" :show-overflow-tooltip="true" />
        <el-table-column label="在职状态" align="center" prop="status">
          <template #default="scope">
            <dict-tag :options="common_yes_no" :value="scope.row.status" />
          </template>
        </el-table-column>
      </el-table>
      <pagination v-show="employeeTotal > 0" :total="employeeTotal" v-model:page="employeeQueryParams.pageNum" v-model:limit="employeeQueryParams.pageSize" @pagination="getEmployeeList" />
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="confirmEmployeeSelect">确 定</el-button>
          <el-button @click="employeePickerOpen = false">取 消</el-button>
        </div>
      </template>
    </el-dialog>

    <!-- 添加或修改车间对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="workshopRef" :model="form" :rules="rules" label-width="80px" :disabled="dialogMode === 'view'">
        <el-form-item label="车间编码" prop="workshopCode">
          <el-input v-model="form.workshopCode" placeholder="请输入车间编码" />
        </el-form-item>
        <el-form-item label="车间名称" prop="workshopName">
          <el-input v-model="form.workshopName" placeholder="请输入车间名称"/>
        </el-form-item>
        <el-form-item label="车间面积(㎡)" prop="area">
          <el-input v-model="form.area" placeholder="请输入车间面积(㎡)" />
        </el-form-item>
        <el-form-item label="负责人" prop="managerId">
          <el-input v-model="form.managerNickname" placeholder="请选择负责人" readonly>
            <template #append>
              <el-button :disabled="dialogMode === 'view'" @click="openEmployeePicker">选择员工</el-button>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in common_yes_no"
              :key="dict.value"
              :label="parseInt(dict.value)"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button v-if="dialogMode !== 'view'" type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="Workshop">
import { listWorkshop, getWorkshop, delWorkshop, addWorkshop, updateWorkshop } from "@/api/mes/md/workshop";
import { listEmployee, getEmployee } from "@/api/system/employee";

const { proxy } = getCurrentInstance();
const { common_yes_no } = proxy.useDict('common_yes_no');

const workshopList = ref([]);
const employeeList = ref([]);
const employeePickerOpen = ref(false);
const employeeLoading = ref(false);
const employeeTotal = ref(0);
const employeeIds = ref([]);
const selectedEmployee = ref(null);
const selectedEmployeeId = ref(null);
const managerMap = ref({});
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");
const dialogMode = ref("edit");

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    workshopCode: null,
    workshopName: null,
    status: null,
  },
  employeeQueryParams: {
    pageNum: 1,
    pageSize: 10,
    empName: null,
    phone: null,
  },
  rules: {
    workshopCode: [
      { required: true, message: "车间编码不能为空", trigger: "blur" }
    ],
    workshopName: [
      { required: true, message: "车间名称不能为空", trigger: "blur" }
    ],
    status: [
      { required: true, message: "状态不能为空", trigger: "change" }
    ],
  }
});

const { queryParams, form, rules, employeeQueryParams } = toRefs(data);

/** 查询车间列表 */
function getList() {
  loading.value = true;
  listWorkshop(queryParams.value).then(response => {
    workshopList.value = response.rows;
    total.value = response.total;
    loading.value = false;
  });
}

function getEmployeeList() {
  employeeLoading.value = true;
  listEmployee(employeeQueryParams.value).then(response => {
    employeeList.value = response.rows;
    employeeTotal.value = response.total;
    const tempMap = { ...managerMap.value };
    response.rows.forEach(item => {
      const id = item.userId || item.id;
      if (id != null && item.nickname) {
        tempMap[id] = item.nickname;
      }
    });
    managerMap.value = tempMap;
    employeeLoading.value = false;
  });
}

function getEmployeeRowKey(row) {
  return row.userId || row.id;
}

async function fillManagerNickname(targetForm) {
  const managerId = targetForm.managerId;
  if (!managerId) {
    targetForm.managerNickname = null;
    return;
  }
  if (managerMap.value[managerId]) {
    targetForm.managerNickname = managerMap.value[managerId];
    return;
  }
  try {
    const res = await getEmployee(managerId);
    const nickname = res?.data?.nickname || res?.data?.empName || managerId;
    managerMap.value[managerId] = nickname;
    targetForm.managerNickname = nickname;
  } catch (e) {
    targetForm.managerNickname = managerId;
  }
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
  ids.value = selection.map(item => item.id);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
function handleAdd() {
  reset();
  dialogMode.value = "add";
  open.value = true;
  title.value = "添加车间";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  dialogMode.value = "edit";
  const _id = row.id || ids.value;
  getWorkshop(_id).then(async response => {
    form.value = response.data;
    await fillManagerNickname(form.value);
    open.value = true;
    title.value = "修改车间";
  });
}

function handleView(row) {
  reset();
  dialogMode.value = "view";
  const _id = row.id;
  getWorkshop(_id).then(async response => {
    form.value = response.data;
    await fillManagerNickname(form.value);
    open.value = true;
    title.value = "查看车间";
  });
}

/** 提交按钮 */
function submitForm() {
  if (dialogMode.value === "view") return;
  proxy.$refs["workshopRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateWorkshop(form.value).then(() => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addWorkshop(form.value).then(() => {
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
  const _ids = row.id || ids.value;
  proxy.$modal.confirm('是否确认删除车间编号为"' + _ids + '"的数据项？').then(function() {
    return delWorkshop(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('mes:md/workshop/export', {
    ...queryParams.value
  }, `workshop_${new Date().getTime()}.xlsx`)
}

function openEmployeePicker() {
  selectedEmployeeId.value = form.value.managerId || null;
  employeePickerOpen.value = true;
  getEmployeeList();
}

function handleEmployeeQuery() {
  employeeQueryParams.value.pageNum = 1;
  getEmployeeList();
}

function resetEmployeeQuery() {
  proxy.resetForm("employeeQueryRef");
  handleEmployeeQuery();
}

function handleEmployeeCurrentChange(currentRow) {
  if (!currentRow) {
    employeeIds.value = [];
    selectedEmployee.value = null;
    selectedEmployeeId.value = null;
    return;
  }
  const id = currentRow.userId || currentRow.id;
  employeeIds.value = [id];
  selectedEmployee.value = currentRow;
  selectedEmployeeId.value = id;
}

function confirmEmployeeSelect() {
  if (!selectedEmployee.value) {
    proxy.$modal.msgError("请选择一名员工");
    return;
  }
  const managerId = selectedEmployee.value.userId || selectedEmployee.value.id;
  form.value.managerId = managerId;
  form.value.managerNickname = selectedEmployee.value.nickname || selectedEmployee.value.empName || managerId;
  managerMap.value[managerId] = form.value.managerNickname;
  employeePickerOpen.value = false;
}

// 取消按钮
function cancel() {
  open.value = false;
  reset();
  dialogMode.value = "edit";
}

// 表单重置
function reset() {
  form.value = {
    id: null,
    workshopCode: null,
    workshopName: null,
    area: null,
    managerId: null,
    managerNickname: null,
    status: null,
    remark: null,
    createTime: null,
    updateTime: null,
    isDeleted: null
  };
  selectedEmployee.value = null;
  employeeIds.value = [];
  proxy.resetForm("workshopRef");
}

getList();
</script>
