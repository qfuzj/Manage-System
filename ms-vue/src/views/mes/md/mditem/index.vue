<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="98px">
      <el-form-item label="产品物料编码" prop="itemCode">
        <el-input v-model="queryParams.itemCode" placeholder="请输入产品物料编码" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="产品物料名称" prop="itemName">
        <el-input v-model="queryParams.itemName" placeholder="请输入产品物料名称" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="是否启用" prop="enableFlag">
        <el-select v-model="queryParams.enableFlag" placeholder="请选择是否启用" clearable style="width: 160px">
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
      <el-table-column label="产品物料标识" align="center" prop="itemOrProduct">
        <template #default="scope">
          <dict-tag :options="item_or_product" :value="scope.row.itemOrProduct" />
        </template>
      </el-table-column>
      <el-table-column label="物料类型名称" align="center" prop="itemTypeName" />
      <el-table-column label="是否启用" align="center" prop="enableFlag">
        <template #default="scope">
          <dict-tag :options="common_yes_no" :value="scope.row.enableFlag" />
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

    <el-dialog :title="title" v-model="open" width="1000px" top="4vh" append-to-body class="mditem-dialog">
      <el-form ref="mditemRef" :model="form" :rules="rules" label-width="118px" class="mditem-form"
        :disabled="dialogMode === 'view'">
        <div class="mditem-form-layout">
          <section class="mditem-hero">
            <div class="mditem-hero-main">
              <el-form-item label="物料编码" prop="itemCode" class="mditem-form-item">
                <div class="mditem-code-row">
                  <el-input v-model="form.itemCode" placeholder="请输入物料编码" />
                  <div class="mditem-inline-switch">
                    <el-switch v-if="dialogMode !== 'view'" v-model="autoGenerateCode" />
                    <span class="mditem-inline-switch__label">自动生成</span>
                  </div>
                </div>
              </el-form-item>

              <el-form-item label="物料名称" prop="itemName" class="mditem-form-item">
                <el-input v-model="form.itemName" placeholder="请输入物料名称" />
              </el-form-item>

              <el-form-item label="规格型号" prop="specification" class="mditem-form-item">
                <el-input v-model="form.specification" type="textarea" :rows="2" resize="none"
                  placeholder="请输入规格型号" />
              </el-form-item>

              <div class="mditem-grid-row">
                <el-form-item label="单位" prop="unitOfMeasure" class="mditem-form-item">
                  <el-select v-model="form.unitOfMeasure" placeholder="请选择单位" filterable clearable
                    @change="handleUnitChange">
                    <el-option v-for="item in unitOptions" :key="item.id" :label="item.name" :value="item.code" />
                  </el-select>
                </el-form-item>

                <el-form-item label="物料/产品标识" prop="itemOrProduct" class="mditem-form-item">
                  <el-radio-group v-model="form.itemOrProduct" @change="handleItemOrProductChange">
                    <el-radio v-for="dict in item_or_product" :key="dict.value" :label="dict.value">
                      {{ dict.label }}
                    </el-radio>
                  </el-radio-group>
                </el-form-item>
              </div>

              <div class="mditem-grid-row mditem-grid-row--align-end">
                <el-form-item label="物料/产品分类" prop="itemTypeId" class="mditem-form-item mditem-form-item--grow">
                  <el-select v-model="form.itemTypeId" placeholder="请选择分类" filterable clearable
                    @change="handleItemTypeChange">
                    <el-option v-for="item in filteredItemTypeOptions" :key="item.itemTypeId"
                      :label="item.itemTypeName" :value="item.itemTypeId" />
                  </el-select>
                </el-form-item>

                <div class="mditem-flag-box">
                  <span class="mditem-flag-box__label">高价值/易被盗物品</span>
                  <el-checkbox v-model="highValueChecked" />
                </div>
              </div>
            </div>

            <div class="mditem-preview">
              <div class="mditem-preview__frame">
                <template v-if="previewImage">
                  <img :src="previewImage" alt="物料图片" class="mditem-preview__image" />
                </template>
                <template v-else>
                  <el-icon class="mditem-preview__icon"><Picture /></el-icon>
                  <span>图片预览区</span>
                </template>
              </div>
            </div>
          </section>

          <section class="mditem-status-strip">
            <el-form-item label="是否启用" prop="enableFlag" class="mditem-form-item">
              <el-radio-group v-model="form.enableFlag">
                <el-radio v-for="dict in common_yes_no" :key="dict.value" :label="dict.value">
                  {{ dict.label }}
                </el-radio>
              </el-radio-group>
            </el-form-item>

            <el-form-item label="批次管理" prop="batchFlag" class="mditem-form-item">
              <div class="mditem-toggle-row">
                <span :class="{ 'is-active': form.batchFlag === '1' }">否</span>
                <el-switch v-model="form.batchFlag" active-value="0" inactive-value="1" />
                <span :class="{ 'is-active': form.batchFlag === '0' }">是</span>
              </div>
            </el-form-item>

            <el-form-item label="安全库存" prop="safeStockFlag" class="mditem-form-item">
              <el-radio-group v-model="form.safeStockFlag">
                <el-radio v-for="dict in common_yes_no" :key="dict.value" :label="dict.value">
                  {{ dict.label }}
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </section>

          <section v-if="form.safeStockFlag === '0'" class="mditem-grid-row">
            <el-form-item label="最低库存量" prop="minStock" class="mditem-form-item">
              <el-input-number v-model="form.minStock" :min="0" :step="1" controls-position="right" />
            </el-form-item>
            <el-form-item label="最大库存量" prop="maxStock" class="mditem-form-item">
              <el-input-number v-model="form.maxStock" :min="0" :step="1" controls-position="right" />
            </el-form-item>
          </section>

          <section>
            <el-form-item label="备注" prop="remark" class="mditem-form-item">
              <el-input v-model="form.remark" type="textarea" :rows="2" resize="none" placeholder="请输入备注" />
            </el-form-item>
          </section>

          <section class="mditem-detail-panel">
            <el-tabs v-model="detailTab" class="mditem-tabs">
              <el-tab-pane label="BOM组成" name="bom">
                <div class="mditem-detail-content">
                  <div class="mditem-detail-toolbar">
                    <div class="mditem-detail-actions">
                      <el-button v-if="dialogMode !== 'view'" type="primary" plain icon="Plus" @click="handleAddMdItemBom">
                        新增
                      </el-button>
                      <el-button v-if="dialogMode !== 'view'" type="danger" plain icon="Delete"
                        @click="handleDeleteMdItemBom">
                        删除
                      </el-button>
                    </div>
                    <div class="mditem-detail-tools">
                      <el-button circle icon="Search" disabled />
                      <el-button circle icon="Refresh" disabled />
                    </div>
                  </div>

                  <el-table :data="mdItemBomList" @selection-change="handleMdItemBomSelectionChange" class="mditem-detail-table">
                    <el-table-column type="selection" width="50" align="center" />
                    <el-table-column label="物料编码" prop="bomItemCode" min-width="150" />
                    <el-table-column label="物料名称" prop="bomItemName" min-width="140" />
                    <el-table-column label="规格" prop="bomItemSpec" min-width="120" show-overflow-tooltip />
                    <el-table-column label="单位" prop="unitName" min-width="80" />
                    <el-table-column label="使用比例" prop="quantity" min-width="110" />
                    <el-table-column label="备注" prop="remark" min-width="120" show-overflow-tooltip />
                    <el-table-column v-if="dialogMode !== 'view'" label="操作" width="140" align="center">
                      <template #default="scope">
                        <el-button link type="primary" @click="handleEditMdItemBom(scope.row, scope.$index)">修改</el-button>
                        <el-button link type="danger" @click="removeMdItemBom(scope.$index)">删除</el-button>
                      </template>
                    </el-table-column>
                  </el-table>
                </div>
              </el-tab-pane>

              <el-tab-pane label="SIP" name="sip">
                <div class="mditem-detail-content">
                  <div class="mditem-detail-toolbar">
                    <div class="mditem-detail-actions">
                      <el-button v-if="dialogMode !== 'view'" type="primary" plain icon="Plus" @click="handleAddMdItemSip">
                        新增
                      </el-button>
                      <el-button v-if="dialogMode !== 'view'" type="danger" plain icon="Delete"
                        @click="handleDeleteMdItemSip">
                        删除
                      </el-button>
                    </div>
                  </div>

                  <el-table :data="mdItemSipList" @selection-change="handleMdItemSipSelectionChange" class="mditem-detail-table">
                    <el-table-column type="selection" width="50" align="center" />
                    <el-table-column label="排序" prop="orderNum" width="80" align="center" />
                    <el-table-column label="工序编号" prop="processCode" min-width="120" />
                    <el-table-column label="工序名称" prop="processName" min-width="140" />
                    <el-table-column label="标题" prop="sipTitle" min-width="160" />
                    <el-table-column label="图片地址" prop="sipUrl" min-width="180" show-overflow-tooltip />
                    <el-table-column v-if="dialogMode !== 'view'" label="操作" width="140" align="center">
                      <template #default="scope">
                        <el-button link type="primary" @click="handleEditMdItemSip(scope.row, scope.$index)">修改</el-button>
                        <el-button link type="danger" @click="removeMdItemSip(scope.$index)">删除</el-button>
                      </template>
                    </el-table-column>
                  </el-table>
                </div>
              </el-tab-pane>

              <el-tab-pane label="SOP" name="sop">
                <div class="mditem-detail-content">
                  <div class="mditem-detail-toolbar">
                    <div class="mditem-detail-actions">
                      <el-button v-if="dialogMode !== 'view'" type="primary" plain icon="Plus" @click="handleAddProductSop">
                        新增
                      </el-button>
                      <el-button v-if="dialogMode !== 'view'" type="danger" plain icon="Delete"
                        @click="handleDeleteProductSop">
                        删除
                      </el-button>
                    </div>
                  </div>

                  <el-table :data="productSopList" @selection-change="handleProductSopSelectionChange" class="mditem-detail-table">
                    <el-table-column type="selection" width="50" align="center" />
                    <el-table-column label="排序" prop="orderNum" width="80" align="center" />
                    <el-table-column label="工序编号" prop="processCode" min-width="120" />
                    <el-table-column label="工序名称" prop="processName" min-width="140" />
                    <el-table-column label="标题" prop="sopTitle" min-width="160" />
                    <el-table-column label="图片地址" prop="sopUrl" min-width="180" show-overflow-tooltip />
                    <el-table-column v-if="dialogMode !== 'view'" label="操作" width="140" align="center">
                      <template #default="scope">
                        <el-button link type="primary" @click="handleEditProductSop(scope.row, scope.$index)">修改</el-button>
                        <el-button link type="danger" @click="removeProductSop(scope.$index)">删除</el-button>
                      </template>
                    </el-table-column>
                  </el-table>
                </div>
              </el-tab-pane>
            </el-tabs>
          </section>
        </div>
      </el-form>

      <template #footer>
        <div class="dialog-footer">
          <el-button v-if="dialogMode !== 'view'" type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>

    <el-dialog v-model="bomDialog.open" :title="bomDialog.title" width="760px" append-to-body class="sub-dialog">
      <el-form ref="bomRef" :model="bomDialog.form" :rules="bomRules" label-width="140px">
        <el-form-item label="BOM物料编码" prop="bomItemCode">
          <el-input v-model="bomDialog.form.bomItemCode" placeholder="请输入BOM物料编码" />
        </el-form-item>
        <el-form-item label="BOM物料名称" prop="bomItemName">
          <el-input v-model="bomDialog.form.bomItemName" placeholder="请输入BOM物料名称" />
        </el-form-item>
        <el-form-item label="BOM物料规格" prop="bomItemSpec">
          <el-input v-model="bomDialog.form.bomItemSpec" type="textarea" :rows="2" placeholder="请输入BOM物料规格" />
        </el-form-item>
        <el-form-item label="BOM物料单位" prop="unitOfMeasure">
          <el-select v-model="bomDialog.form.unitOfMeasure" placeholder="请选择单位" filterable clearable
            @change="handleBomUnitChange">
            <el-option v-for="item in unitOptions" :key="item.id" :label="item.name" :value="item.code" />
          </el-select>
        </el-form-item>
        <el-form-item label="物料使用比例" prop="quantity">
          <el-input-number v-model="bomDialog.form.quantity" :min="0.0001" :step="1" :precision="4" controls-position="right" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="bomDialog.form.remark" type="textarea" :rows="3" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitBomDialog">确 定</el-button>
          <el-button @click="bomDialog.open = false">取 消</el-button>
        </div>
      </template>
    </el-dialog>

    <el-dialog v-model="sipDialog.open" :title="sipDialog.title" width="760px" append-to-body class="sub-dialog">
      <el-form ref="sipRef" :model="sipDialog.form" :rules="sipRules" label-width="120px">
        <el-form-item label="排列顺序" prop="orderNum">
          <el-input-number v-model="sipDialog.form.orderNum" :min="1" controls-position="right" />
        </el-form-item>
        <el-form-item label="工序编号" prop="processCode">
          <el-input v-model="sipDialog.form.processCode" placeholder="请输入工序编号" />
        </el-form-item>
        <el-form-item label="工序名称" prop="processName">
          <el-input v-model="sipDialog.form.processName" placeholder="请输入工序名称" />
        </el-form-item>
        <el-form-item label="标题" prop="sipTitle">
          <el-input v-model="sipDialog.form.sipTitle" placeholder="请输入标题" />
        </el-form-item>
        <el-form-item label="详细描述" prop="sipDescription">
          <el-input v-model="sipDialog.form.sipDescription" type="textarea" :rows="3" placeholder="请输入详细描述" />
        </el-form-item>
        <el-form-item label="图片地址" prop="sipUrl">
          <el-input v-model="sipDialog.form.sipUrl" placeholder="请输入图片地址" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitSipDialog">确 定</el-button>
          <el-button @click="sipDialog.open = false">取 消</el-button>
        </div>
      </template>
    </el-dialog>

    <el-dialog v-model="sopDialog.open" :title="sopDialog.title" width="760px" append-to-body class="sub-dialog">
      <el-form ref="sopRef" :model="sopDialog.form" :rules="sopRules" label-width="120px">
        <el-form-item label="排列顺序" prop="orderNum">
          <el-input-number v-model="sopDialog.form.orderNum" :min="1" controls-position="right" />
        </el-form-item>
        <el-form-item label="工序编号" prop="processCode">
          <el-input v-model="sopDialog.form.processCode" placeholder="请输入工序编号" />
        </el-form-item>
        <el-form-item label="工序名称" prop="processName">
          <el-input v-model="sopDialog.form.processName" placeholder="请输入工序名称" />
        </el-form-item>
        <el-form-item label="标题" prop="sopTitle">
          <el-input v-model="sopDialog.form.sopTitle" placeholder="请输入标题" />
        </el-form-item>
        <el-form-item label="详细描述" prop="sopDescription">
          <el-input v-model="sopDialog.form.sopDescription" type="textarea" :rows="3" placeholder="请输入详细描述" />
        </el-form-item>
        <el-form-item label="图片地址" prop="sopUrl">
          <el-input v-model="sopDialog.form.sopUrl" placeholder="请输入图片地址" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitSopDialog">确 定</el-button>
          <el-button @click="sopDialog.open = false">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="Mditem">
import { Picture } from "@element-plus/icons-vue";
import { listMditem, getMditem, delMditem, addMditem, updateMditem } from "@/api/mes/md/mditem";
import { listMdproductbom } from "@/api/mes/md/mdproductbom";
import { listProductsip } from "@/api/mes/md/productsip";
import { listProductsop } from "@/api/mes/md/productsop";
import { listUnit } from "@/api/mes/md/unit";
import { listItemtype } from "@/api/mes/md/itemtype";

const { proxy } = getCurrentInstance();
const { common_yes_no, item_or_product } = proxy.useDict("common_yes_no", "item_or_product");

const mditemList = ref([]);
const productSopList = ref([]);
const mdItemBomList = ref([]);
const mdItemSipList = ref([]);
const checkedProductSop = ref([]);
const checkedMdItemBom = ref([]);
const checkedMdItemSip = ref([]);
const unitOptions = ref([]);
const itemTypeOptions = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");
const dialogMode = ref("edit");
const autoGenerateCode = ref(false);
const detailTab = ref("bom");

const bomRef = ref();
const sipRef = ref();
const sopRef = ref();

const bomDialog = reactive({
  open: false,
  title: "新增产品BOM关系",
  index: -1,
  form: {}
});

const sipDialog = reactive({
  open: false,
  title: "新增产品SIP",
  index: -1,
  form: {}
});

const sopDialog = reactive({
  open: false,
  title: "新增产品SOP",
  index: -1,
  form: {}
});

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
    itemCode: [{ required: true, message: "物料编码不能为空", trigger: "blur" }],
    itemName: [{ required: true, message: "物料名称不能为空", trigger: "blur" }],
    unitOfMeasure: [{ required: true, message: "单位不能为空", trigger: "change" }],
    itemTypeId: [{ required: true, message: "物料/产品分类不能为空", trigger: "change" }],
    itemOrProduct: [{ required: true, message: "物料/产品标识不能为空", trigger: "change" }],
    enableFlag: [{ required: true, message: "是否启用不能为空", trigger: "change" }],
    safeStockFlag: [{ required: true, message: "安全库存设置不能为空", trigger: "change" }],
    highValue: [{ required: true, message: "高价值物资不能为空", trigger: "change" }],
    batchFlag: [{ required: true, message: "批次管理不能为空", trigger: "change" }],
  }
});

const bomRules = {
  bomItemCode: [{ required: true, message: "BOM物料编码不能为空", trigger: "blur" }],
  bomItemName: [{ required: true, message: "BOM物料名称不能为空", trigger: "blur" }],
  unitOfMeasure: [{ required: true, message: "BOM物料单位不能为空", trigger: "change" }],
  quantity: [{ required: true, message: "物料使用比例不能为空", trigger: "change" }]
};

const sipRules = {
  orderNum: [{ required: true, message: "排列顺序不能为空", trigger: "change" }],
  sipTitle: [{ required: true, message: "标题不能为空", trigger: "blur" }]
};

const sopRules = {
  orderNum: [{ required: true, message: "排列顺序不能为空", trigger: "change" }],
  sopTitle: [{ required: true, message: "标题不能为空", trigger: "blur" }]
};

const { queryParams, form, rules } = toRefs(data);

const highValueChecked = computed({
  get: () => form.value.highValue === "0",
  set: (value) => {
    form.value.highValue = value ? "0" : "1";
  }
});

const filteredItemTypeOptions = computed(() => {
  if (!form.value.itemOrProduct) {
    return itemTypeOptions.value;
  }
  return itemTypeOptions.value.filter((item) => !item.itemOrProduct || item.itemOrProduct === form.value.itemOrProduct);
});

const previewImage = computed(() => form.value.attr1 || "");

function getList() {
  loading.value = true;
  listMditem(queryParams.value).then(response => {
    mditemList.value = response.rows;
    total.value = response.total;
    loading.value = false;
  }).catch(() => {
    loading.value = false;
  });
}

function loadUnitOptions() {
  return listUnit({
    pageNum: 1,
    pageSize: 999,
    status: "0"
  }).then(response => {
    unitOptions.value = response.rows || [];
  });
}

function loadItemTypeOptions() {
  return listItemtype({
    pageNum: 1,
    pageSize: 999,
    enableFlag: "0"
  }).then(response => {
    itemTypeOptions.value = response.rows || [];
  });
}

function getUnitName(code) {
  const current = unitOptions.value.find(item => item.code === code);
  return current ? current.name : "";
}

function resetBomDialog() {
  bomDialog.form = {
    bomId: null,
    bomItemId: null,
    bomItemCode: "",
    bomItemName: "",
    bomItemSpec: "",
    unitOfMeasure: "",
    unitName: "",
    itemOrProduct: form.value.itemOrProduct || "",
    enableFlag: "0",
    quantity: 1,
    remark: ""
  };
  bomDialog.index = -1;
}

function resetSipDialog() {
  sipDialog.form = {
    sipId: null,
    orderNum: mdItemSipList.value.length + 1,
    processId: null,
    processCode: "",
    processName: "",
    sipTitle: "",
    sipDescription: "",
    sipUrl: "",
    remark: ""
  };
  sipDialog.index = -1;
}

function resetSopDialog() {
  sopDialog.form = {
    sopId: null,
    orderNum: productSopList.value.length + 1,
    processId: null,
    processCode: "",
    processName: "",
    sopTitle: "",
    sopDescription: "",
    sopUrl: "",
    remark: ""
  };
  sopDialog.index = -1;
}

function cancel() {
  open.value = false;
  reset();
}

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
    enableFlag: "0",
    safeStockFlag: "1",
    minStock: null,
    maxStock: null,
    highValue: "1",
    batchFlag: "1",
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
  mdItemBomList.value = [];
  mdItemSipList.value = [];
  checkedProductSop.value = [];
  checkedMdItemBom.value = [];
  checkedMdItemSip.value = [];
  autoGenerateCode.value = false;
  detailTab.value = "bom";
  resetBomDialog();
  resetSipDialog();
  resetSopDialog();
  proxy.resetForm("mditemRef");
}

function handleQuery() {
  queryParams.value.pageNum = 1;
  getList();
}

function resetQuery() {
  proxy.resetForm("queryRef");
  handleQuery();
}

function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.itemId);
  single.value = selection.length !== 1;
  multiple.value = !selection.length;
}

function handleUnitChange(value) {
  form.value.unitName = getUnitName(value);
}

function handleBomUnitChange(value) {
  bomDialog.form.unitName = getUnitName(value);
}

function handleItemOrProductChange(value) {
  if (!form.value.itemTypeId) return;
  const current = itemTypeOptions.value.find(item => item.itemTypeId === form.value.itemTypeId);
  if (current && current.itemOrProduct && current.itemOrProduct !== value) {
    form.value.itemTypeId = null;
    form.value.itemTypeCode = null;
    form.value.itemTypeName = null;
  }
}

function handleItemTypeChange(value) {
  const current = itemTypeOptions.value.find(item => item.itemTypeId === value);
  if (!current) {
    form.value.itemTypeId = null;
    form.value.itemTypeCode = null;
    form.value.itemTypeName = null;
    return;
  }
  form.value.itemTypeId = current.itemTypeId;
  form.value.itemTypeCode = current.itemTypeCode;
  form.value.itemTypeName = current.itemTypeName;
  if (current.itemOrProduct) {
    form.value.itemOrProduct = current.itemOrProduct;
  }
}

function handleAdd() {
  reset();
  dialogMode.value = "add";
  open.value = true;
  title.value = "添加物料/产品";
}

function normalizeRows(response) {
  if (Array.isArray(response?.rows)) return response.rows;
  if (Array.isArray(response?.data)) return response.data;
  return [];
}

function normalizeBomRows(rows) {
  return rows.map(item => ({
    ...item,
    unitName: item.unitName || getUnitName(item.unitOfMeasure)
  }));
}

function loadSubTables(itemId) {
  return Promise.all([
    listMdproductbom({ itemId }),
    listProductsip({ itemId }),
    listProductsop({ itemId })
  ]).then(([bomRes, sipRes, sopRes]) => {
    mdItemBomList.value = normalizeBomRows(normalizeRows(bomRes));
    mdItemSipList.value = normalizeRows(sipRes);
    productSopList.value = normalizeRows(sopRes);
  }).catch(() => {
    mdItemBomList.value = [];
    mdItemSipList.value = [];
    productSopList.value = [];
  });
}

function handleUpdate(row) {
  reset();
  dialogMode.value = "edit";
  const _itemId = row?.itemId ?? ids.value[0];
  if (!_itemId) {
    proxy.$modal.msgError("请选择一条物料/产品数据");
    return;
  }
  Promise.all([
    getMditem(_itemId),
    loadSubTables(_itemId)
  ]).then(([response]) => {
    form.value = { ...form.value, ...response.data };
    open.value = true;
    title.value = "修改物料/产品";
  });
}

function handleView(row) {
  reset();
  dialogMode.value = "view";
  const _itemId = row?.itemId ?? ids.value[0];
  if (!_itemId) {
    proxy.$modal.msgError("请选择一条物料/产品数据");
    return;
  }
  Promise.all([
    getMditem(_itemId),
    loadSubTables(_itemId)
  ]).then(([response]) => {
    form.value = { ...form.value, ...response.data };
    open.value = true;
    title.value = "查看物料/产品";
  });
}

function normalizeBeforeSubmit() {
  form.value.productSopList = productSopList.value.map(item => ({
    sopId: item.sopId || null,
    itemId: form.value.itemId,
    orderNum: item.orderNum,
    processId: item.processId || null,
    processCode: item.processCode || "",
    processName: item.processName || "",
    sopTitle: item.sopTitle || "",
    sopDescription: item.sopDescription || "",
    sopUrl: item.sopUrl || "",
    remark: item.remark || ""
  }));

  form.value.mdItemBomList = mdItemBomList.value.map(item => ({
    bomId: item.bomId || null,
    itemId: form.value.itemId,
    bomItemId: item.bomItemId || null,
    bomItemCode: item.bomItemCode || "",
    bomItemName: item.bomItemName || "",
    bomItemSpec: item.bomItemSpec || "",
    unitOfMeasure: item.unitOfMeasure || "",
    itemOrProduct: item.itemOrProduct || form.value.itemOrProduct || "",
    enableFlag: item.enableFlag || "0",
    quantity: item.quantity,
    remark: item.remark || ""
  }));

  form.value.mdItemSipList = mdItemSipList.value.map(item => ({
    sipId: item.sipId || null,
    itemId: form.value.itemId,
    orderNum: item.orderNum,
    processId: item.processId || null,
    processCode: item.processCode || "",
    processName: item.processName || "",
    sipTitle: item.sipTitle || "",
    sipDescription: item.sipDescription || "",
    sipUrl: item.sipUrl || "",
    remark: item.remark || ""
  }));
}

function submitForm() {
  if (dialogMode.value === "view") return;
  proxy.$refs["mditemRef"].validate(valid => {
    if (!valid) return;
    normalizeBeforeSubmit();
    if (form.value.itemId != null) {
      updateMditem(form.value).then(() => {
        proxy.$modal.msgSuccess("修改成功");
        open.value = false;
        getList();
      });
    } else {
      addMditem(form.value).then(() => {
        proxy.$modal.msgSuccess("新增成功");
        open.value = false;
        getList();
      });
    }
  });
}

function handleDelete(row) {
  const _itemIds = row?.itemId ? [row.itemId] : ids.value;
  if (!_itemIds || _itemIds.length === 0) {
    proxy.$modal.msgError("请选择要删除的物料/产品数据");
    return;
  }
  proxy.$modal.confirm('是否确认删除物料产品编号为"' + _itemIds + '"的数据项？').then(function () {
    return delMditem(_itemIds);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => { });
}

function handleAddMdItemBom() {
  resetBomDialog();
  bomDialog.title = "新增产品BOM关系";
  bomDialog.open = true;
}

function handleEditMdItemBom(row, index) {
  resetBomDialog();
  bomDialog.title = "修改产品BOM关系";
  bomDialog.index = index;
  bomDialog.form = { ...row };
  bomDialog.open = true;
}

function submitBomDialog() {
  bomRef.value.validate(valid => {
    if (!valid) return;
    const payload = {
      ...bomDialog.form,
      unitName: getUnitName(bomDialog.form.unitOfMeasure),
      itemOrProduct: bomDialog.form.itemOrProduct || form.value.itemOrProduct || "",
      enableFlag: bomDialog.form.enableFlag || "0"
    };
    if (bomDialog.index > -1) {
      mdItemBomList.value.splice(bomDialog.index, 1, payload);
    } else {
      mdItemBomList.value.push(payload);
    }
    bomDialog.open = false;
  });
}

function handleDeleteMdItemBom() {
  if (checkedMdItemBom.value.length === 0) {
    proxy.$modal.msgError("请先选择要删除的BOM数据");
  } else {
    mdItemBomList.value = mdItemBomList.value.filter((_, index) => checkedMdItemBom.value.indexOf(index) === -1);
  }
}

function removeMdItemBom(index) {
  mdItemBomList.value.splice(index, 1);
}

function handleMdItemBomSelectionChange(selection) {
  checkedMdItemBom.value = selection.map(item => mdItemBomList.value.indexOf(item));
}

function handleAddMdItemSip() {
  resetSipDialog();
  sipDialog.title = "新增产品SIP";
  sipDialog.open = true;
}

function handleEditMdItemSip(row, index) {
  resetSipDialog();
  sipDialog.title = "修改产品SIP";
  sipDialog.index = index;
  sipDialog.form = { ...row };
  sipDialog.open = true;
}

function submitSipDialog() {
  sipRef.value.validate(valid => {
    if (!valid) return;
    const payload = { ...sipDialog.form };
    if (sipDialog.index > -1) {
      mdItemSipList.value.splice(sipDialog.index, 1, payload);
    } else {
      mdItemSipList.value.push(payload);
    }
    sipDialog.open = false;
  });
}

function handleDeleteMdItemSip() {
  if (checkedMdItemSip.value.length === 0) {
    proxy.$modal.msgError("请先选择要删除的SIP数据");
  } else {
    mdItemSipList.value = mdItemSipList.value.filter((_, index) => checkedMdItemSip.value.indexOf(index) === -1);
  }
}

function removeMdItemSip(index) {
  mdItemSipList.value.splice(index, 1);
}

function handleMdItemSipSelectionChange(selection) {
  checkedMdItemSip.value = selection.map(item => mdItemSipList.value.indexOf(item));
}

function handleAddProductSop() {
  resetSopDialog();
  sopDialog.title = "新增产品SOP";
  sopDialog.open = true;
}

function handleEditProductSop(row, index) {
  resetSopDialog();
  sopDialog.title = "修改产品SOP";
  sopDialog.index = index;
  sopDialog.form = { ...row };
  sopDialog.open = true;
}

function submitSopDialog() {
  sopRef.value.validate(valid => {
    if (!valid) return;
    const payload = { ...sopDialog.form };
    if (sopDialog.index > -1) {
      productSopList.value.splice(sopDialog.index, 1, payload);
    } else {
      productSopList.value.push(payload);
    }
    sopDialog.open = false;
  });
}

function handleDeleteProductSop() {
  if (checkedProductSop.value.length === 0) {
    proxy.$modal.msgError("请先选择要删除的SOP数据");
  } else {
    productSopList.value = productSopList.value.filter((_, index) => checkedProductSop.value.indexOf(index) === -1);
  }
}

function removeProductSop(index) {
  productSopList.value.splice(index, 1);
}

function handleProductSopSelectionChange(selection) {
  checkedProductSop.value = selection.map(item => productSopList.value.indexOf(item));
}

function handleExport() {
  proxy.download("mes:md/mditem/export", {
    ...queryParams.value
  }, `mditem_${new Date().getTime()}.xlsx`);
}

onMounted(() => {
  getList();
  loadUnitOptions();
  loadItemTypeOptions();
  resetBomDialog();
  resetSipDialog();
  resetSopDialog();
});
</script>

<style scoped>
.mditem-form-layout {
  display: flex;
  flex-direction: column;
  gap: 24px;
}

.mditem-hero {
  display: grid;
  grid-template-columns: minmax(0, 1fr) 320px;
  gap: 28px;
  align-items: start;
}

.mditem-hero-main {
  display: flex;
  flex-direction: column;
  gap: 18px;
}

.mditem-form-item {
  margin-bottom: 0;
}

.mditem-form-item :deep(.el-form-item__label) {
  color: #5c6470;
  font-size: 16px;
  font-weight: 600;
}

.mditem-form-item :deep(.el-input__wrapper),
.mditem-form-item :deep(.el-textarea__inner),
.mditem-form-item :deep(.el-select__wrapper) {
  min-height: 46px;
  border-radius: 12px;
  box-shadow: 0 0 0 1px #d8dfeb inset;
}

.mditem-code-row {
  display: flex;
  align-items: center;
  gap: 18px;
  width: 100%;
}

.mditem-code-row .el-input {
  flex: 1;
}

.mditem-inline-switch {
  display: inline-flex;
  align-items: center;
  gap: 12px;
  flex-shrink: 0;
  color: #303643;
  font-size: 16px;
  font-weight: 600;
}

.mditem-inline-switch__label {
  white-space: nowrap;
}

.mditem-grid-row {
  display: grid;
  grid-template-columns: minmax(0, 1fr) minmax(0, 1fr);
  gap: 24px;
}

.mditem-grid-row--align-end {
  align-items: end;
}

.mditem-form-item--grow {
  min-width: 0;
}

.mditem-flag-box {
  display: flex;
  align-items: center;
  gap: 14px;
  min-height: 48px;
  padding-bottom: 2px;
  color: #5c6470;
  font-size: 16px;
  font-weight: 600;
}

.mditem-preview {
  display: flex;
  justify-content: center;
  align-items: flex-start;
  padding-top: 6px;
}

.mditem-preview__frame {
  width: 100%;
  aspect-ratio: 1 / 1;
  border: 1px dashed #9ca7b8;
  border-radius: 14px;
  background: linear-gradient(180deg, #fbfcfe 0%, #f4f7fb 100%);
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 10px;
  color: #95a0b2;
}

.mditem-preview__icon {
  font-size: 30px;
}

.mditem-preview__image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  border-radius: 14px;
}

.mditem-status-strip {
  display: grid;
  grid-template-columns: repeat(3, minmax(0, 1fr));
  gap: 24px;
  padding: 2px 0;
}

.mditem-toggle-row {
  display: inline-flex;
  align-items: center;
  gap: 14px;
  color: #8b94a3;
  font-size: 16px;
  font-weight: 600;
}

.mditem-toggle-row .is-active {
  color: #2f88ff;
}

.mditem-detail-panel {
  border: 1px solid #dbe3ef;
  border-radius: 18px;
  overflow: hidden;
  background: #fff;
}

.mditem-tabs :deep(.el-tabs__header) {
  margin: 0;
  padding: 0 22px;
  background: linear-gradient(180deg, #fbfcfe 0%, #f5f7fb 100%);
  border-bottom: 1px solid #dbe3ef;
}

.mditem-tabs :deep(.el-tabs__nav-wrap::after) {
  display: none;
}

.mditem-tabs :deep(.el-tabs__item) {
  height: 64px;
  font-size: 16px;
  font-weight: 600;
}

.mditem-detail-content {
  padding: 20px 22px 22px;
}

.mditem-detail-toolbar {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 16px;
}

.mditem-detail-actions,
.mditem-detail-tools {
  display: flex;
  align-items: center;
  gap: 12px;
}

.mditem-detail-table :deep(.el-table__header th) {
  background: #f7f9fc;
  color: #495262;
  font-weight: 700;
}

:deep(.mditem-dialog .el-dialog),
:deep(.sub-dialog .el-dialog) {
  border-radius: 22px;
  overflow: hidden;
}

:deep(.mditem-dialog .el-dialog__header),
:deep(.sub-dialog .el-dialog__header) {
  margin-right: 0;
  padding: 24px 28px;
  border-bottom: 1px solid #e6ebf2;
}

:deep(.mditem-dialog .el-dialog__title),
:deep(.sub-dialog .el-dialog__title) {
  display: block;
  text-align: center;
  font-size: 22px;
  font-weight: 700;
  color: #1f2430;
}

:deep(.mditem-dialog .el-dialog__body),
:deep(.sub-dialog .el-dialog__body) {
  padding: 26px 32px 18px;
  background: #fff;
}

:deep(.mditem-dialog .el-dialog__footer),
:deep(.sub-dialog .el-dialog__footer) {
  padding: 0 32px 28px;
}

@media (max-width: 1200px) {
  .mditem-hero {
    grid-template-columns: 1fr;
  }

  .mditem-preview {
    justify-content: flex-start;
  }

  .mditem-preview__frame {
    max-width: 320px;
  }

  .mditem-status-strip,
  .mditem-grid-row {
    grid-template-columns: 1fr;
  }
}
</style>
