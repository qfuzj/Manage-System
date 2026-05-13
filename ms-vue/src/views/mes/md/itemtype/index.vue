<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="产品物料类型名称" prop="itemTypeName">
        <el-input v-model="queryParams.itemTypeName" placeholder="请输入产品物料类型名称" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="是否启用" prop="enableFlag">
        <el-select v-model="queryParams.enableFlag" placeholder="请选择是否启用" clearable>
          <el-option v-for="dict in common_yes_no" :key="dict.value" :label="dict.label" :value="dict.value" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="Plus" @click="handleAdd"
          v-hasPermi="['mes:md:itemtype:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="Edit" :disabled="single" @click="handleUpdate"
          v-hasPermi="['mes:md:itemtype:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['mes:md:itemtype:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="Download" @click="handleExport"
          v-hasPermi="['mes:md:itemtype:export']">导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="itemtypeList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="产品物料类型编码" align="center" prop="itemTypeCode">
        <template #default="scope">
          <el-button link type="primary" @click="handleView(scope.row)">{{ scope.row.itemTypeCode }}</el-button>
        </template>
      </el-table-column>
      <el-table-column label="产品物料类型名称" align="center" prop="itemTypeName" />
      <el-table-column label="父类型ID" align="center" prop="parentTypeId" />
      <el-table-column label="所有层级父节点" align="center" prop="ancestors" />
      <el-table-column label="产品物料标识" align="center" prop="itemOrProduct">
        <template #default="scope">
          <dict-tag :options="item_or_product" :value="scope.row.itemOrProduct" />
        </template>
      </el-table-column>
      <el-table-column label="排列顺序" align="center" prop="orderNum" />
      <el-table-column label="是否启用" align="center" prop="enableFlag">
        <template #default="scope">
          <dict-tag :options="common_yes_no" :value="scope.row.enableFlag" />
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['mes:md:itemtype:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)"
            v-hasPermi="['mes:md:itemtype:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改物料产品分类对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="itemtypeRef" :model="form" :rules="rules" label-width="80px" :disabled="dialogMode === 'view'">
        <el-form-item label="产品物料类型编码" prop="itemTypeCode">
          <el-input v-model="form.itemTypeCode" placeholder="请输入产品物料类型编码" />
        </el-form-item>
        <el-form-item label="产品物料类型名称" prop="itemTypeName">
          <el-input v-model="form.itemTypeName" placeholder="请输入产品物料类型名称" />
        </el-form-item>
        <el-form-item label="父类型ID" prop="parentTypeId">
          <el-input v-model="form.parentTypeId" placeholder="请输入父类型ID" />
        </el-form-item>
        <el-form-item label="产品物料标识" prop="itemOrProduct">
          <el-radio-group v-model="form.itemOrProduct">
            <el-radio v-for="dict in item_or_product" :key="dict.value" :label="dict.value">{{ dict.label }}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="排列顺序" prop="orderNum">
          <el-input v-model="form.orderNum" placeholder="请输入排列顺序" />
        </el-form-item>
        <el-form-item label="是否启用" prop="enableFlag">
          <el-radio-group v-model="form.enableFlag">
            <el-radio v-for="dict in common_yes_no" :key="dict.value" :label="dict.value">{{ dict.label }}</el-radio>
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

<script setup name="Itemtype">
import { listItemtype, getItemtype, delItemtype, addItemtype, updateItemtype } from "@/api/mes/md/itemtype";

const { proxy } = getCurrentInstance();
const { common_yes_no, item_or_product } = proxy.useDict('common_yes_no', 'item_or_product');

const itemtypeList = ref([]);
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
    itemTypeName: null,
    enableFlag: null,
  },
  rules: {
    itemTypeCode: [
      { required: true, message: "产品物料类型编码不能为空", trigger: "blur" }
    ],
    itemTypeName: [
      { required: true, message: "产品物料类型名称不能为空", trigger: "blur" }
    ],
    parentTypeId: [
      { required: true, message: "父类型ID不能为空", trigger: "blur" }
    ],
    ancestors: [
      { required: true, message: "所有层级父节点不能为空", trigger: "blur" }
    ],
    itemOrProduct: [
      { required: true, message: "产品物料标识不能为空", trigger: "change" }
    ],
    enableFlag: [
      { required: true, message: "是否启用不能为空", trigger: "change" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询物料产品分类列表 */
function getList() {
  loading.value = true;
  listItemtype(queryParams.value).then(response => {
    itemtypeList.value = response.rows;
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
    itemTypeId: null,
    itemTypeCode: null,
    itemTypeName: null,
    parentTypeId: null,
    ancestors: null,
    itemOrProduct: null,
    orderNum: null,
    enableFlag: null,
    remark: null,
    attr1: null,
    attr2: null,
    attr3: null,
    attr4: null,
    createBy: null,
    createTime: null,
    updateBy: null,
    updateTime: null
  };
  proxy.resetForm("itemtypeRef");
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
  ids.value = selection.map(item => item.itemTypeId);
  single.value = selection.length !== 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
function handleAdd() {
  reset();
  dialogMode.value = "add";
  open.value = true;
  title.value = "添加物料产品分类";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  dialogMode.value = "edit";
  const _itemTypeId = row.itemTypeId || ids.value
  getItemtype(_itemTypeId).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改物料产品分类";
  });
}

/** 查看按钮操作 */
function handleView(row) {
  reset();
  dialogMode.value = "view";
  const _itemTypeId = row.itemTypeId || ids.value;
  getItemtype(_itemTypeId).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "查看物料产品分类";
  });
}

/** 提交按钮 */
function submitForm() {
  if (dialogMode.value === "view") return;
  proxy.$refs["itemtypeRef"].validate(valid => {
    if (valid) {
      if (form.value.itemTypeId != null) {
        updateItemtype(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addItemtype(form.value).then(response => {
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
  const _itemTypeIds = row.itemTypeId || ids.value;
  proxy.$modal.confirm('是否确认删除物料产品分类编号为"' + _itemTypeIds + '"的数据项？').then(function () {
    return delItemtype(_itemTypeIds);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => { });
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('mes:md/itemtype/export', {
    ...queryParams.value
  }, `itemtype_${new Date().getTime()}.xlsx`)
}

getList();
</script>
