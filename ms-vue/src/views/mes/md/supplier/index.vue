<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="88px">
      <el-form-item label="供应商编码" prop="code">
        <el-input
          v-model="queryParams.code"
          placeholder="请输入供应商编码"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="供应商名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入供应商名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="供应商地址" prop="address">
        <el-input
          v-model="queryParams.address"
          placeholder="请输入供应商地址"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否启用" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择" clearable style="width: 100px">
          <el-option
            v-for="dict in common_yes_no"
            :key="dict.value"
            :label="dict.label"
            :value="parseInt(dict.value)"
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
          v-hasPermi="['system:supplier:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:supplier:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:supplier:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['system:supplier:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="supplierList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="供应商编码" align="center" prop="code">
        <template #default="scope">
          <el-button link type="primary" @click="handleView(scope.row)">{{ scope.row.code }}</el-button>
        </template>
      </el-table-column>
      <el-table-column label="供应商名称" align="center" prop="name" />
      <el-table-column label="供应商地址" align="center" prop="address" />
      <el-table-column label="供应商联系人" align="center" prop="contactPerson" />
      <el-table-column label="供应商电话" align="center" prop="phone" />
      <el-table-column label="供应商传真" align="center" prop="fax" />
      <el-table-column label="供应商邮箱" align="center" prop="email" />
      <el-table-column label="供应商描述" align="center" prop="description" />
      <el-table-column label="是否启用" align="center" prop="status">
        <template #default="scope">
          <dict-tag :options="common_yes_no" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['system:supplier:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['system:supplier:remove']">删除</el-button>
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

    <!-- 添加或修改供应商对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="supplierRef" :model="form" :rules="rules" label-width="80px" :disabled="dialogMode === 'view'">
        <el-form-item label="供应商编码" prop="code">
          <el-input v-model="form.code" placeholder="请输入供应商编码" />
        </el-form-item>
        <el-form-item label="供应商名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入供应商名称" />
        </el-form-item>
        <el-form-item label="供应商地址" prop="address">
          <el-input v-model="form.address" placeholder="请输入供应商地址" />
        </el-form-item>
        <el-form-item label="供应商联系人" prop="contactPerson">
          <el-input v-model="form.contactPerson" placeholder="请输入供应商联系人" />
        </el-form-item>
        <el-form-item label="供应商电话" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入供应商电话" />
        </el-form-item>
        <el-form-item label="供应商传真" prop="fax">
          <el-input v-model="form.fax" placeholder="请输入供应商传真" />
        </el-form-item>
        <el-form-item label="供应商邮箱" prop="email">
          <el-input v-model="form.email" placeholder="请输入供应商邮箱" />
        </el-form-item>
        <el-form-item label="供应商描述" prop="description">
          <el-input v-model="form.description" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="是否启用" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in common_yes_no"
              :key="dict.value"
              :label="parseInt(dict.value)"
            >{{dict.label}}</el-radio>
          </el-radio-group>
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

<script setup name="Supplier">
import { listSupplier, getSupplier, delSupplier, addSupplier, updateSupplier } from "@/api/mes/md/supplier";

const { proxy } = getCurrentInstance();
const { common_yes_no } = proxy.useDict('common_yes_no');

const supplierList = ref([]);
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
  form: {
    status: 0,
  },
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    code: null,
    name: null,
    address: null,
    status: null,
  },
  rules: {
    code: [
      { required: true, message: "供应商编码不能为空", trigger: "blur" }
    ],
    name: [
      { required: true, message: "供应商名称不能为空", trigger: "blur" }
    ],
    status: [
      { required: true, message: "是否启用不能为空", trigger: "change" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询供应商列表 */
function getList() {
  loading.value = true;
  listSupplier(queryParams.value).then(response => {
    supplierList.value = response.rows;
    total.value = response.total;
    loading.value = false;
  });
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
    code: null,
    name: null,
    address: null,
    contactPerson: null,
    phone: null,
    fax: null,
    email: null,
    description: null,
    status: 0,
    createTime: null,
    updateTime: null,
    deleteFlag: null
  };
  proxy.resetForm("supplierRef");
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
  single.value = selection.length !== 1;
  multiple.value = selection.length === 0;
}

/** 新增按钮操作 */
function handleAdd() {
  reset();
  dialogMode.value = "add";
  open.value = true;
  title.value = "添加供应商";
}

function handleUpdate(row) {
  reset();
  dialogMode.value = "edit";
  const _id = row.id || ids.value
  getSupplier(_id).then(response => {
    form.value = response.data;
    form.value.status = form.value.status != null ? Number(form.value.status) : 0;
    open.value = true;
    title.value = "修改供应商";
  });
}

function handleView(row) {
  reset();
  dialogMode.value = "view";
  const _id = row.id;
  getSupplier(_id).then(response => {
    form.value = response.data;
    form.value.status = form.value.status != null ? Number(form.value.status) : 0;
    open.value = true;
    title.value = "查看供应商";
  });
}

/** 提交按钮 */
function submitForm() {
  if (dialogMode.value === "view") return;
  proxy.$refs["supplierRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateSupplier(form.value).then(() => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addSupplier(form.value).then(() => {
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
  proxy.$modal.confirm('是否确认删除供应商编号为"' + _ids + '"的数据项？').then(function() {
    return delSupplier(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('system/supplier/export', {
    ...queryParams.value
  }, `supplier_${new Date().getTime()}.xlsx`)
}

getList();
</script>