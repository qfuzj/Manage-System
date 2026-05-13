<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="98px">
      <el-form-item label="工位编码" prop="workstationCode">
        <el-input v-model="queryParams.workstationCode" placeholder="请输入工位编码" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="工位名称" prop="workstationName">
        <el-input v-model="queryParams.workstationName" placeholder="请输入工位名称" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="所属车间ID" prop="workshopId">
        <el-input v-model="queryParams.workshopId" placeholder="请输入所属车间ID" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="所属工序ID" prop="processId">
        <el-input v-model="queryParams.processId" placeholder="请输入所属工序ID" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="是否启用" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择是否启用" clearable style="width: 150px">
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
          v-hasPermi="['mes:md:workstation:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="Edit" :disabled="single" @click="handleUpdate"
          v-hasPermi="['mes:md:workstation:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['mes:md:workstation:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="Download" @click="handleExport"
          v-hasPermi="['mes:md:workstation:export']">导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="workstationList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="工位编码" align="center" prop="workstationCode">
        <template #default="scope">
          <el-button link type="primary" @click="handleView(scope.row)">{{
            scope.row.workstationCode
            }}</el-button>
        </template>
      </el-table-column>
      <el-table-column label="工位名称" align="center" prop="workstationName" />
      <el-table-column label="所属车间ID" align="center" prop="workshopId" />
      <el-table-column label="所属工序ID" align="center" prop="processId" />
      <el-table-column label="物理位置" align="center" prop="location" />
      <el-table-column label="是否启用" align="center" prop="status">
        <template #default="scope">
          <dict-tag :options="common_yes_no" :value="scope.row.status" />
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['mes:md:workstation:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)"
            v-hasPermi="['mes:md:workstation:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize" @pagination="getList" />

    <el-dialog :title="title" v-model="open" width="1080px" append-to-body>
      <el-form ref="workstationRef" :model="form" :rules="rules" label-width="92px" :disabled="dialogMode === 'view'">
        <el-row :gutter="16">
          <el-col :span="12">
            <el-form-item label="工位编码" prop="workstationCode">
              <div class="workstation-code-row">
                <el-input v-model="form.workstationCode" placeholder="请输入工位编码"
                  :disabled="autoGenerateCode && dialogMode !== 'view'" />
                <el-switch v-if="dialogMode !== 'view'" v-model="autoGenerateCode" class="code-switch"
                  active-text="自动生成" inactive-text="手动填写" @change="handleAutoCodeSwitchChange" />
                <span v-else class="auto-generate-tip">自动生成</span>
              </div>
            </el-form-item>
          </el-col>
          <el-col :span="12"><el-form-item label="工位名称" prop="workstationName"><el-input v-model="form.workstationName"
                placeholder="请输入工位名称" /></el-form-item></el-col>
          <el-col :span="12">
            <el-form-item label="所属车间ID" prop="workshopId">
              <el-select v-model="form.workshopId" placeholder="请选择所属车间" filterable style="width: 100%">
                <el-option v-for="item in workshopList" :key="item.id" :label="item.workshopName" :value="item.id" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12"><el-form-item label="所属工序ID" prop="processId">
              <!-- <el-input v-model="form.processId" placeholder="请输入所属工序ID" /> -->
               <el-select v-model="form.processId" placeholder="请选择所属工序" filterable style="width: 100%">
                <el-option v-for="item in processList" :key="item.id" :label="item.processName" :value="item.id" />
               </el-select>
            </el-form-item></el-col>
          <el-col :span="12"><el-form-item label="物理位置" prop="location"><el-input v-model="form.location"
                placeholder="请输入物理位置" /></el-form-item></el-col>
          <el-col :span="12">
            <el-form-item label="状态" prop="status">
              <el-radio-group v-model="form.status">
                <el-radio v-for="dict in common_yes_no" :key="dict.value" :label="parseInt(dict.value)">{{ dict.label
                  }}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="24"><el-form-item label="备注" prop="remark"><el-input v-model="form.remark" type="textarea"
                placeholder="请输入内容" /></el-form-item></el-col>
        </el-row>

        <el-divider content-position="center">工作站资源</el-divider>
        <el-carousel :interval="4000" type="card" height="320px" indicator-position="outside">
          <el-carousel-item>
            <div class="resource-card">
              <div class="resource-header">
                <span>设备资源</span>
                <el-button v-if="dialogMode !== 'view'" type="primary" link
                  @click="openDeviceResourceDialog()">新增</el-button>
              </div>
              <el-table :data="relStationDeviceList" size="small" max-height="230">
                <el-table-column label="设备编号" prop="deviceCode" />
                <el-table-column label="设备名称" prop="deviceName" />
                <el-table-column label="数量" prop="quantity" width="100" align="center" />
                <el-table-column v-if="dialogMode !== 'view'" label="操作" width="90" align="center">
                  <template #default="scope">
                    <el-button link type="danger" @click="removeDeviceResource(scope.$index)">删除</el-button>
                  </template>
                </el-table-column>
              </el-table>
            </div>
          </el-carousel-item>

          <el-carousel-item>
            <div class="resource-card">
              <div class="resource-header">
                <span>人力资源</span>
                <el-button v-if="dialogMode !== 'view'" type="primary" link
                  @click="openPositionResourceDialog()">新增</el-button>
              </div>
              <el-table :data="relStationPositionList" size="small" max-height="230">
                <el-table-column label="岗位名称" prop="positionName" />
                <el-table-column label="数量" prop="standardCount" width="100" align="center" />
                <el-table-column v-if="dialogMode !== 'view'" label="操作" width="130" align="center">
                  <template #default="scope">
                    <el-button link type="primary" @click="
                      openPositionResourceDialog(scope.row, scope.$index)
                      ">修改</el-button>
                    <el-button link type="danger" @click="removePositionResource(scope.$index)">删除</el-button>
                  </template>
                </el-table-column>
              </el-table>
            </div>
          </el-carousel-item>

          <el-carousel-item>
            <div class="resource-card">
              <div class="resource-header">
                <span>工装夹具</span>
                <el-button v-if="dialogMode !== 'view'" type="primary" link
                  @click="openToolResourceDialog()">新增</el-button>
              </div>
              <el-table :data="relStationToolList" size="small" max-height="230">
                <el-table-column label="类型名称" prop="toolName" />
                <el-table-column label="数量" prop="quantity" width="100" align="center" />
                <el-table-column v-if="dialogMode !== 'view'" label="操作" width="130" align="center">
                  <template #default="scope">
                    <el-button link type="primary"
                      @click="openToolResourceDialog(scope.row, scope.$index)">修改</el-button>
                    <el-button link type="danger" @click="removeToolResource(scope.$index)">删除</el-button>
                  </template>
                </el-table-column>
              </el-table>
            </div>
          </el-carousel-item>
        </el-carousel>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button v-if="dialogMode !== 'view'" type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>

    <el-dialog v-model="positionResourceOpen" :title="positionForm.index > -1 ? '修改人力资源' : '添加人力资源'" width="420px"
      append-to-body>
      <el-form ref="positionResourceRef" :model="positionForm" :rules="positionRules" label-width="80px">
        <el-form-item label="岗位" prop="positionId">
          <el-select v-model="positionForm.positionId" placeholder="请选择岗位" filterable style="width: 100%"
            @change="handlePositionChange">
            <el-option v-for="item in positionOptions" :key="item.id" :label="item.positionName" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="数量" prop="standardCount">
          <el-input-number v-model="positionForm.standardCount" :min="1" :step="1" style="width: 100%" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button type="primary" @click="confirmPositionResource">确 定</el-button>
        <el-button @click="positionResourceOpen = false">取 消</el-button>
      </template>
    </el-dialog>

    <el-dialog v-model="toolResourceOpen" :title="toolForm.index > -1 ? '修改工装夹具' : '添加工装夹具'" width="420px"
      append-to-body>
      <el-form ref="toolResourceRef" :model="toolForm" :rules="toolRules" label-width="80px">
        <el-form-item label="夹具" prop="toolId">
          <el-select v-model="toolForm.toolId" placeholder="请选择夹具" filterable style="width: 100%"
            @change="handleToolChange">
            <el-option v-for="item in toolOptions" :key="item.id" :label="item.toolName" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="数量" prop="quantity">
          <el-input-number v-model="toolForm.quantity" :min="1" :step="1" style="width: 100%" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button type="primary" @click="confirmToolResource">确 定</el-button>
        <el-button @click="toolResourceOpen = false">取 消</el-button>
      </template>
    </el-dialog>

    <el-dialog v-model="deviceResourceOpen" title="添加设备资源" width="900px" top="5vh" append-to-body>
      <el-form :model="deviceQueryParams" ref="deviceQueryRef" :inline="true" label-width="68px">
        <el-form-item label="设备编号" prop="deviceCode">
          <el-input v-model="deviceQueryParams.deviceCode" placeholder="请输入设备编号" clearable
            @keyup.enter="handleDeviceQuery" />
        </el-form-item>
        <el-form-item label="设备名称" prop="deviceName">
          <el-input v-model="deviceQueryParams.deviceName" placeholder="请输入设备名称" clearable
            @keyup.enter="handleDeviceQuery" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="Search" @click="handleDeviceQuery">搜索</el-button>
          <el-button icon="Refresh" @click="resetDeviceQuery">重置</el-button>
        </el-form-item>
      </el-form>
      <el-table v-loading="deviceLoading" :data="deviceOptions" height="320" highlight-current-row
        :row-key="(row) => row.id" @current-change="handleDeviceCurrentChange">
        <el-table-column label="选择" width="70" align="center">
          <template #default="scope">
            <el-radio :model-value="selectedDeviceId" :label="scope.row.id">{{
              ""
              }}</el-radio>
          </template>
        </el-table-column>
        <el-table-column label="设备编码" prop="deviceCode" />
        <el-table-column label="设备名称" prop="deviceName" />
        <el-table-column label="品牌" prop="brand" />
      </el-table>
      <pagination v-show="deviceTotal > 0" :total="deviceTotal" v-model:page="deviceQueryParams.pageNum"
        v-model:limit="deviceQueryParams.pageSize" @pagination="getDeviceOptions" />
      <el-form label-width="68px" class="qty-form">
        <el-form-item label="数量">
          <el-input-number v-model="deviceResourceQty" :min="1" :step="1" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button type="primary" @click="confirmDeviceResource">确 定</el-button>
        <el-button @click="deviceResourceOpen = false">取 消</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="Workstation">
import {
  listWorkstation,
  getWorkstation,
  delWorkstation,
  addWorkstation,
  updateWorkstation,
  getAutoWorkstationCode,
} from "@/api/mes/md/workstation";
import { listWorkshopName } from "@/api/mes/md/workshop";
import { listProcess } from "@/api/mes/pro/process";
import { listPosition } from "@/api/system/position";
import { listTool } from "@/api/system/tool";
import { listDevice } from "@/api/system/device";

const { proxy } = getCurrentInstance();
const { common_yes_no } = proxy.useDict("common_yes_no");

const workstationList = ref([]);
const relStationPositionList = ref([]);
const relStationToolList = ref([]);
const relStationDeviceList = ref([]);
const workshopList = ref([]);
const processList = ref([]);

const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");
const dialogMode = ref("edit");

const positionOptions = ref([]);
const toolOptions = ref([]);
const deviceOptions = ref([]);

const positionResourceOpen = ref(false);
const toolResourceOpen = ref(false);
const deviceResourceOpen = ref(false);

const deviceLoading = ref(false);
const deviceTotal = ref(0);
const selectedDevice = ref(null);
const selectedDeviceId = ref(null);
const deviceResourceQty = ref(1);
const autoGenerateCode = ref(true);

const positionForm = reactive({
  index: -1,
  positionId: null,
  positionName: "",
  standardCount: 1,
});
const toolForm = reactive({
  index: -1,
  toolId: null,
  toolName: "",
  quantity: 1,
});

const positionRules = {
  positionId: [{ required: true, message: "岗位不能为空", trigger: "change" }],
  standardCount: [{ required: true, message: "数量不能为空", trigger: "blur" }],
};
const toolRules = {
  toolId: [{ required: true, message: "夹具不能为空", trigger: "change" }],
  quantity: [{ required: true, message: "数量不能为空", trigger: "blur" }],
};

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    workstationCode: null,
    workstationName: null,
    workshopId: null,
    processId: null,
    status: null,
  },
  deviceQueryParams: {
    pageNum: 1,
    pageSize: 10,
    deviceCode: null,
    deviceName: null,
  },
  rules: {
    workstationCode: [
      { required: true, message: "工位编码不能为空", trigger: "blur" },
    ],
    workstationName: [
      { required: true, message: "工位名称不能为空", trigger: "blur" },
    ],
    workshopId: [
      { required: true, message: "所属车间ID不能为空", trigger: "blur" },
    ],
    status: [{ required: true, message: "状态不能为空", trigger: "change" }],
  },
});

const { queryParams, form, rules, deviceQueryParams } = toRefs(data);

function getList() {
  loading.value = true;
  listWorkstation(queryParams.value).then((response) => {
    workstationList.value = response.rows;
    total.value = response.total;
    loading.value = false;
  });
}

/**
 * 获取车间列表，用于下拉选择
 */
function getWorkShopList() {
  listWorkshopName().then((response) => {
    workshopList.value = response.data || [];
  });
}

/**
 * 获取工序列表，用于下拉选择
 */
function getProcessList() {
  listProcess().then((response) => {
    processList.value = response.rows || [];
  });
}

function loadPositionOptions() {
  listPosition({ pageNum: 1, pageSize: 9999 }).then((res) => {
    positionOptions.value = res.rows || [];
  });
}

function loadToolOptions() {
  listTool({ pageNum: 1, pageSize: 9999 }).then((res) => {
    toolOptions.value = res.rows || [];
  });
}

function getDeviceOptions() {
  deviceLoading.value = true;
  listDevice(deviceQueryParams.value).then((res) => {
    deviceOptions.value = res.rows || [];
    deviceTotal.value = res.total || 0;
    deviceLoading.value = false;
  });
}

function cancel() {
  open.value = false;
  reset();
  dialogMode.value = "edit";
}

function reset() {
  form.value = {
    id: null,
    workstationCode: null,
    workstationName: null,
    workshopId: null,
    processId: null,
    location: null,
    status: null,
    remark: null,
    createTime: null,
    updateTime: null,
    isDeleted: null,
  };
  autoGenerateCode.value = true;
  relStationPositionList.value = [];
  relStationToolList.value = [];
  relStationDeviceList.value = [];
  selectedDevice.value = null;
  selectedDeviceId.value = null;
  deviceResourceQty.value = 1;
  proxy.resetForm("workstationRef");
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
  ids.value = selection.map((item) => item.id);
  single.value = selection.length !== 1;
  multiple.value = !selection.length;
}

function handleAdd() {
  reset();
  getWorkShopList();
  getProcessList();
  dialogMode.value = "add";
  open.value = true;
  title.value = "添加工作站";
  loadPositionOptions();
  loadToolOptions();
  requestAutoWorkstationCode();
}

function handleUpdate(row) {
  reset();
  getWorkShopList();
  getProcessList();
  dialogMode.value = "edit";
  const _id = row.id || ids.value;
  getWorkstation(_id).then((response) => {
    form.value = response.data;
    autoGenerateCode.value = false;
    relStationPositionList.value = response.data.relStationPositionList || [];
    relStationToolList.value = response.data.relStationToolList || [];
    relStationDeviceList.value = response.data.relStationDeviceList || [];
    open.value = true;
    title.value = "修改工作站";
    loadPositionOptions();
    loadToolOptions();
  });
}

function handleView(row) {
  reset();
  getWorkShopList();
  getProcessList();
  dialogMode.value = "view";
  const _id = row.id;
  getWorkstation(_id).then((response) => {
    form.value = response.data;
    relStationPositionList.value = response.data.relStationPositionList || [];
    relStationToolList.value = response.data.relStationToolList || [];
    relStationDeviceList.value = response.data.relStationDeviceList || [];
    open.value = true;
    title.value = "查看工作站";
  });
}

function submitForm() {
  if (dialogMode.value === "view") return;
  proxy.$refs["workstationRef"].validate((valid) => {
    if (valid) {
      form.value.relStationPositionList = relStationPositionList.value.map(
        (item) => ({
          positionId: item.positionId,
          positionName: item.positionName,
          standardCount: item.standardCount,
        }),
      );
      form.value.relStationToolList = relStationToolList.value.map((item) => ({
        toolId: item.toolId,
        toolName: item.toolName,
        quantity: item.quantity,
      }));
      form.value.relStationDeviceList = relStationDeviceList.value.map(
        (item) => ({
          deviceId: item.deviceId,
          deviceCode: item.deviceCode,
          deviceName: item.deviceName,
          quantity: item.quantity,
        }),
      );
      if (form.value.id != null) {
        updateWorkstation(form.value).then(() => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addWorkstation(form.value).then(() => {
          proxy.$modal.msgSuccess("新增成功");
          open.value = false;
          getList();
        });
      }
    }
  });
}

function handleDelete(row) {
  const _ids = row.id || ids.value;
  proxy.$modal
    .confirm('是否确认删除工作站编号为"' + _ids + '"的数据项？')
    .then(function () {
      return delWorkstation(_ids);
    })
    .then(() => {
      getList();
      proxy.$modal.msgSuccess("删除成功");
    })
    .catch(() => { });
}

function handleExport() {
  proxy.download(
    "mes:md/workstation/export",
    {
      ...queryParams.value,
    },
    `workstation_${new Date().getTime()}.xlsx`,
  );
}

function openPositionResourceDialog(row, index = -1) {
  if (!positionOptions.value.length) {
    loadPositionOptions();
  }
  positionForm.index = index;
  positionForm.positionId = row?.positionId ?? null;
  positionForm.positionName = row?.positionName ?? "";
  positionForm.standardCount = row?.standardCount ?? 1;
  positionResourceOpen.value = true;
}

function handlePositionChange(value) {
  const hit = positionOptions.value.find((item) => item.id === value);
  positionForm.positionName = hit?.positionName || "";
}

function confirmPositionResource() {
  proxy.$refs.positionResourceRef.validate((valid) => {
    if (!valid) return;
    const payload = {
      positionId: positionForm.positionId,
      positionName: positionForm.positionName,
      standardCount: positionForm.standardCount,
    };
    if (positionForm.index > -1) {
      relStationPositionList.value.splice(positionForm.index, 1, payload);
    } else {
      relStationPositionList.value.push(payload);
    }
    positionResourceOpen.value = false;
  });
}

function removePositionResource(index) {
  relStationPositionList.value.splice(index, 1);
}

function openToolResourceDialog(row, index = -1) {
  if (!toolOptions.value.length) {
    loadToolOptions();
  }
  toolForm.index = index;
  toolForm.toolId = row?.toolId ?? null;
  toolForm.toolName = row?.toolName ?? "";
  toolForm.quantity = row?.quantity ?? 1;
  toolResourceOpen.value = true;
}

function handleToolChange(value) {
  const hit = toolOptions.value.find((item) => item.id === value);
  toolForm.toolName = hit?.toolName || "";
}

function confirmToolResource() {
  proxy.$refs.toolResourceRef.validate((valid) => {
    if (!valid) return;
    const payload = {
      toolId: toolForm.toolId,
      toolName: toolForm.toolName,
      quantity: toolForm.quantity,
    };
    if (toolForm.index > -1) {
      relStationToolList.value.splice(toolForm.index, 1, payload);
    } else {
      relStationToolList.value.push(payload);
    }
    toolResourceOpen.value = false;
  });
}

function removeToolResource(index) {
  relStationToolList.value.splice(index, 1);
}

function handleAutoCodeSwitchChange(value) {
  if (value) {
    requestAutoWorkstationCode();
    return;
  }
  form.value.workstationCode = null;
}

function requestAutoWorkstationCode() {
  getAutoWorkstationCode()
    .then((res) => {
      form.value.workstationCode =
        typeof res === "string" ? res : res?.data || "";
    })
    .catch(() => {
      autoGenerateCode.value = false;
      proxy.$modal.msgError("自动生成编码失败，请手动填写");
    });
}

function openDeviceResourceDialog() {
  selectedDevice.value = null;
  selectedDeviceId.value = null;
  deviceResourceQty.value = 1;
  deviceQueryParams.value.pageNum = 1;
  deviceResourceOpen.value = true;
  getDeviceOptions();
}

function handleDeviceQuery() {
  deviceQueryParams.value.pageNum = 1;
  getDeviceOptions();
}

function resetDeviceQuery() {
  proxy.resetForm("deviceQueryRef");
  handleDeviceQuery();
}

function handleDeviceCurrentChange(row) {
  selectedDevice.value = row;
  selectedDeviceId.value = row?.id || null;
}

function confirmDeviceResource() {
  if (!selectedDevice.value) {
    proxy.$modal.msgError("请选择一个设备");
    return;
  }
  relStationDeviceList.value = [
    {
      deviceId: selectedDevice.value.id,
      deviceCode: selectedDevice.value.deviceCode,
      deviceName: selectedDevice.value.deviceName,
      quantity: deviceResourceQty.value,
    },
  ];
  deviceResourceOpen.value = false;
}

function removeDeviceResource(index) {
  relStationDeviceList.value.splice(index, 1);
}

getList();
getWorkShopList();

</script>

<style scoped>
.resource-card {
  background: #fff;
  border-radius: 8px;
  border: 1px solid #ebeef5;
  padding: 12px;
  height: 300px;
  box-sizing: border-box;
}

.workstation-code-row {
  width: 100%;
  display: flex;
  align-items: center;
  gap: 12px;
}

.workstation-code-row .el-input {
  flex: 1;
}

.code-switch {
  flex-shrink: 0;
}

.auto-generate-tip {
  color: var(--el-text-color-secondary);
  font-size: 12px;
  white-space: nowrap;
}

.resource-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 8px;
  font-weight: 600;
}

.qty-form {
  margin-top: 12px;
}
</style>
