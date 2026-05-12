<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="工装唯一编码" prop="toolCode">
        <el-input
          v-model="queryParams.toolCode"
          placeholder="请输入工装唯一编码"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="工装名称" prop="toolName">
        <el-input
          v-model="queryParams.toolName"
          placeholder="请输入工装名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="品牌" prop="brand">
        <el-input
          v-model="queryParams.brand"
          placeholder="请输入品牌"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="下次保养日期" prop="nextMaintenDate">
        <el-date-picker clearable
          v-model="queryParams.nextMaintenDate"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择下次保养日期">
        </el-date-picker>
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
          v-hasPermi="['system:tool:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:tool:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:tool:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['system:tool:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="toolList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="工装ID" align="center" prop="id" />
      <el-table-column label="工装唯一编码" align="center" prop="toolCode" />
      <el-table-column label="工装名称" align="center" prop="toolName" />
      <el-table-column label="所属类型ID" align="center" prop="toolTypeId" />
      <el-table-column label="品牌" align="center" prop="brand" />
      <el-table-column label="规格型号" align="center" prop="spec" />
      <el-table-column label="总量" align="center" prop="qty" />
      <el-table-column label="可用数量" align="center" prop="qtyAvail" />
      <el-table-column label="状态" align="center" prop="status" />
      <el-table-column label="下次保养日期" align="center" prop="nextMaintenDate" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.nextMaintenDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['system:tool:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['system:tool:remove']">删除</el-button>
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

    <!-- 添加或修改工装夹具清单对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="toolRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="工装唯一编码" prop="toolCode">
          <el-input v-model="form.toolCode" placeholder="请输入工装唯一编码" />
        </el-form-item>
        <el-form-item label="工装名称" prop="toolName">
          <el-input v-model="form.toolName" placeholder="请输入工装名称" />
        </el-form-item>
        <el-form-item label="所属类型ID" prop="toolTypeId">
          <el-input v-model="form.toolTypeId" placeholder="请输入所属类型ID" />
        </el-form-item>
        <el-form-item label="品牌" prop="brand">
          <el-input v-model="form.brand" placeholder="请输入品牌" />
        </el-form-item>
        <el-form-item label="规格型号" prop="spec">
          <el-input v-model="form.spec" placeholder="请输入规格型号" />
        </el-form-item>
        <el-form-item label="总量" prop="qty">
          <el-input v-model="form.qty" placeholder="请输入总量" />
        </el-form-item>
        <el-form-item label="可用数量" prop="qtyAvail">
          <el-input v-model="form.qtyAvail" placeholder="请输入可用数量" />
        </el-form-item>
        <el-form-item label="下次保养日期" prop="nextMaintenDate">
          <el-date-picker clearable
            v-model="form.nextMaintenDate"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择下次保养日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
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

<script setup name="Tool">
import { listTool, getTool, delTool, addTool, updateTool } from "@/api/system/tool";

const { proxy } = getCurrentInstance();

const toolList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    toolCode: null,
    toolName: null,
    brand: null,
    nextMaintenDate: null,
  },
  rules: {
    toolCode: [
      { required: true, message: "工装唯一编码不能为空", trigger: "blur" }
    ],
    toolName: [
      { required: true, message: "工装名称不能为空", trigger: "blur" }
    ],
    toolTypeId: [
      { required: true, message: "所属类型ID不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询工装夹具清单列表 */
function getList() {
  loading.value = true;
  listTool(queryParams.value).then(response => {
    toolList.value = response.rows;
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
    id: null,
    toolCode: null,
    toolName: null,
    toolTypeId: null,
    brand: null,
    spec: null,
    qty: null,
    qtyAvail: null,
    status: null,
    nextMaintenDate: null,
    remark: null,
    createTime: null,
    updateTime: null,
    isDeleted: null
  };
  proxy.resetForm("toolRef");
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
  open.value = true;
  title.value = "添加工装夹具清单";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getTool(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改工装夹具清单";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["toolRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateTool(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addTool(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除工装夹具清单编号为"' + _ids + '"的数据项？').then(function() {
    return delTool(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('system/tool/export', {
    ...queryParams.value
  }, `tool_${new Date().getTime()}.xlsx`)
}

getList();
</script>
