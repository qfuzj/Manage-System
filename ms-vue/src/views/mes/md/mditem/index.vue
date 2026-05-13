<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="产品物料编码" prop="itemCode">
        <el-input v-model="queryParams.itemCode" placeholder="请输入产品物料编码" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="产品物料名称" prop="itemName">
        <el-input v-model="queryParams.itemName" placeholder="请输入产品物料名称" clearable @keyup.enter="handleQuery" />
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
        <el-button type="primary" plain icon="Plus" @click="handleAdd" v-hasPermi="['mes:md:mditem:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="Edit" :disabled="single" @click="handleUpdate"
          v-hasPermi="['mes:md:mditem:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['mes:md:mditem:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="Download" @click="handleExport"
          v-hasPermi="['mes:md:mditem:export']">导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="mditemList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="产品物料编码" align="center" prop="itemCode">
        <template #default="scope">
          <el-button link type="primary" @click="handleView(scope.row)">{{ scope.row.itemCode }}</el-button>
        </template>
      </el-table-column>
      <el-table-column label="产品物料名称" align="center" prop="itemName" />
      <el-table-column label="规格型号" align="center" prop="specification" />
      <el-table-column label="单位编码" align="center" prop="unitOfMeasure" />
      <el-table-column label="单位名称" align="center" prop="unitName" />
      <el-table-column label="产品物料标识" align="center" prop="itemOrProduct" />
      <el-table-column label="物料类型ID" align="center" prop="itemTypeId" />
      <el-table-column label="物料类型编码" align="center" prop="itemTypeCode" />
      <el-table-column label="物料类型名称" align="center" prop="itemTypeName" />
      <el-table-column label="是否启用" align="center" prop="enableFlag">
        <template #default="scope">
          <dict-tag :options="common_yes_no" :value="scope.row.enableFlag" />
        </template>
      </el-table-column>
      <el-table-column label="是否设置安全库存" align="center" prop="safeStockFlag">
        <template #default="scope">
          <dict-tag :options="common_yes_no" :value="scope.row.safeStockFlag" />
        </template>
      </el-table-column>
      <el-table-column label="最低库存量" align="center" prop="minStock" />
      <el-table-column label="最大库存量" align="center" prop="maxStock" />
      <el-table-column label="高价值物资" align="center" prop="highValue">
        <template #default="scope">
          <dict-tag :options="common_yes_no" :value="scope.row.highValue" />
        </template>
      </el-table-column>
      <el-table-column label="批次管理" align="center" prop="batchFlag">
        <template #default="scope">
          <dict-tag :options="common_yes_no" :value="scope.row.batchFlag" />
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['mes:md:mditem:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)"
            v-hasPermi="['mes:md:mditem:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize" @pagination="getList" />

    <!-- 添加或修改物料产品对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="mditemRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="产品物料编码" prop="itemCode">
          <el-input v-model="form.itemCode" placeholder="请输入产品物料编码" />
        </el-form-item>
        <el-form-item label="产品物料名称" prop="itemName">
          <el-input v-model="form.itemName" placeholder="请输入产品物料名称" />
        </el-form-item>
        <el-form-item label="规格型号" prop="specification">
          <el-input v-model="form.specification" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="单位编码" prop="unitOfMeasure">
          <el-input v-model="form.unitOfMeasure" placeholder="请输入单位编码" />
        </el-form-item>
        <el-form-item label="单位名称" prop="unitName">
          <el-input v-model="form.unitName" placeholder="请输入单位名称" />
        </el-form-item>
        <el-form-item label="产品物料标识" prop="itemOrProduct">
          <el-input v-model="form.itemOrProduct" placeholder="请输入产品物料标识" />
        </el-form-item>
        <el-form-item label="物料类型ID" prop="itemTypeId">
          <el-input v-model="form.itemTypeId" placeholder="请输入物料类型ID" />
        </el-form-item>
        <el-form-item label="物料类型编码" prop="itemTypeCode">
          <el-input v-model="form.itemTypeCode" placeholder="请输入物料类型编码" />
        </el-form-item>
        <el-form-item label="物料类型名称" prop="itemTypeName">
          <el-input v-model="form.itemTypeName" placeholder="请输入物料类型名称" />
        </el-form-item>
        <el-form-item label="是否启用" prop="enableFlag">
          <el-radio-group v-model="form.enableFlag">
            <el-radio v-for="dict in common_yes_no" :key="dict.value" :label="dict.value">{{ dict.label }}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="是否设置安全库存" prop="safeStockFlag">
          <el-radio-group v-model="form.safeStockFlag">
            <el-radio v-for="dict in common_yes_no" :key="dict.value" :label="dict.value">{{ dict.label }}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="最低库存量" prop="minStock">
          <el-input v-model="form.minStock" placeholder="请输入最低库存量" />
        </el-form-item>
        <el-form-item label="最大库存量" prop="maxStock">
          <el-input v-model="form.maxStock" placeholder="请输入最大库存量" />
        </el-form-item>
        <el-form-item label="高价值物资" prop="highValue">
          <el-radio-group v-model="form.highValue">
            <el-radio v-for="dict in common_yes_no" :key="dict.value" :label="dict.value">{{ dict.label }}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="批次管理" prop="batchFlag">
          <el-radio-group v-model="form.batchFlag">
            <el-radio v-for="dict in common_yes_no" :key="dict.value" :label="dict.value">{{ dict.label }}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-divider content-position="center">产品SOP信息</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" icon="Plus" @click="handleAddProductSop">添加</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" icon="Delete" @click="handleDeleteProductSop">删除</el-button>
          </el-col>
        </el-row>
        <el-table :data="productSopList" :row-class-name="rowProductSopIndex"
          @selection-change="handleProductSopSelectionChange" ref="productSop">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="序号" align="center" prop="index" width="50" />
          <el-table-column label="排列顺序" prop="orderNum" width="150">
            <template #default="scope">
              <el-input v-model="scope.row.orderNum" placeholder="请输入排列顺序" />
            </template>
          </el-table-column>
          <el-table-column label="对应的工序" prop="processId" width="150">
            <template #default="scope">
              <el-input v-model="scope.row.processId" placeholder="请输入对应的工序" />
            </template>
          </el-table-column>
          <el-table-column label="工序编号" prop="processCode" width="150">
            <template #default="scope">
              <el-input v-model="scope.row.processCode" placeholder="请输入工序编号" />
            </template>
          </el-table-column>
          <el-table-column label="工序名称" prop="processName" width="150">
            <template #default="scope">
              <el-input v-model="scope.row.processName" placeholder="请输入工序名称" />
            </template>
          </el-table-column>
          <el-table-column label="标题" prop="sopTitle" width="150">
            <template #default="scope">
              <el-input v-model="scope.row.sopTitle" placeholder="请输入标题" />
            </template>
          </el-table-column>
          <el-table-column label="图片地址" prop="sopUrl" width="150">
            <template #default="scope">
              <el-input v-model="scope.row.sopUrl" placeholder="请输入图片地址" />
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

<script setup name="Mditem">
import { listMditem, getMditem, delMditem, addMditem, updateMditem } from "@/api/mes/md/mditem";

const { proxy } = getCurrentInstance();
const { common_yes_no } = proxy.useDict('common_yes_no');

const mditemList = ref([]);
const productSopList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const checkedProductSop = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    itemCode: null,
    itemName: null,
    enableFlag: null,
  },
  rules: {
    itemCode: [
      { required: true, message: "产品物料编码不能为空", trigger: "blur" }
    ],
    itemName: [
      { required: true, message: "产品物料名称不能为空", trigger: "blur" }
    ],
    unitOfMeasure: [
      { required: true, message: "单位编码不能为空", trigger: "blur" }
    ],
    itemOrProduct: [
      { required: true, message: "产品物料标识不能为空", trigger: "blur" }
    ],
    enableFlag: [
      { required: true, message: "是否启用不能为空", trigger: "change" }
    ],
    safeStockFlag: [
      { required: true, message: "是否设置安全库存不能为空", trigger: "change" }
    ],
    highValue: [
      { required: true, message: "高价值物资不能为空", trigger: "change" }
    ],
    batchFlag: [
      { required: true, message: "批次管理不能为空", trigger: "change" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询物料产品列表 */
function getList() {
  loading.value = true;
  listMditem(queryParams.value).then(response => {
    mditemList.value = response.rows;
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
    itemId: null,
    itemCode: null,
    itemName: null,
    specification: null,
    unitOfMeasure: null,
    unitName: null,
    itemOrProduct: null,
    itemTypeId: null,
    itemTypeCode: null,
    itemTypeName: null,
    enableFlag: null,
    safeStockFlag: null,
    minStock: null,
    maxStock: null,
    highValue: null,
    batchFlag: null,
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
  productSopList.value = [];
  proxy.resetForm("mditemRef");
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
  ids.value = selection.map(item => item.itemId);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
function handleAdd() {
  reset();
  open.value = true;
  title.value = "添加物料产品";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _itemId = row.itemId || ids.value
  getMditem(_itemId).then(response => {
    form.value = response.data;
    productSopList.value = response.data.productSopList;
    open.value = true;
    title.value = "修改物料产品";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["mditemRef"].validate(valid => {
    if (valid) {
      form.value.productSopList = productSopList.value;
      if (form.value.itemId != null) {
        updateMditem(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addMditem(form.value).then(response => {
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
  const _itemIds = row.itemId || ids.value;
  proxy.$modal.confirm('是否确认删除物料产品编号为"' + _itemIds + '"的数据项？').then(function () {
    return delMditem(_itemIds);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => { });
}

/** 产品SOP序号 */
function rowProductSopIndex({ row, rowIndex }) {
  row.index = rowIndex + 1;
}

/** 产品SOP添加按钮操作 */
function handleAddProductSop() {
  let obj = {};
  obj.orderNum = "";
  obj.processId = "";
  obj.processCode = "";
  obj.processName = "";
  obj.sopTitle = "";
  obj.sopDescription = "";
  obj.sopUrl = "";
  obj.remark = "";
  productSopList.value.push(obj);
}

/** 产品SOP删除按钮操作 */
function handleDeleteProductSop() {
  if (checkedProductSop.value.length == 0) {
    proxy.$modal.msgError("请先选择要删除的产品SOP数据");
  } else {
    const productSops = productSopList.value;
    const checkedProductSops = checkedProductSop.value;
    productSopList.value = productSops.filter(function (item) {
      return checkedProductSops.indexOf(item.index) == -1
    });
  }
}

/** 复选框选中数据 */
function handleProductSopSelectionChange(selection) {
  checkedProductSop.value = selection.map(item => item.index)
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('mes:md/mditem/export', {
    ...queryParams.value
  }, `mditem_${new Date().getTime()}.xlsx`)
}

getList();
</script>
