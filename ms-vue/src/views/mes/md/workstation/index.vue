<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="工位编码" prop="workstationCode">
        <el-input
          v-model="queryParams.workstationCode"
          placeholder="请输入工位编码"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="工位名称" prop="workstationName">
        <el-input
          v-model="queryParams.workstationName"
          placeholder="请输入工位名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="所属车间ID" prop="workshopId">
        <el-input
          v-model="queryParams.workshopId"
          placeholder="请输入所属车间ID"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="所属工序ID" prop="processId">
        <el-input
          v-model="queryParams.processId"
          placeholder="请输入所属工序ID"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
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
          v-hasPermi="['mes:md:workstation:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['mes:md:workstation:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['mes:md:workstation:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['mes:md:workstation:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="workstationList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="工位编码" align="center" prop="workstationCode">
        <template #default="scope">
          <el-button link type="primary" @click="handleView(scope.row)">{{ scope.row.workstationCode }}</el-button>
        </template>
      </el-table-column>
      <el-table-column label="工位名称" align="center" prop="workstationName" />
      <el-table-column label="所属车间ID" align="center" prop="workshopId" />
      <el-table-column label="所属工序ID" align="center" prop="processId" />
      <el-table-column label="物理位置" align="center" prop="location" />
      <el-table-column label="状态" align="center" prop="status">
        <template #default="scope">
          <dict-tag :options="common_yes_no" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['mes:md:workstation:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['mes:md:workstation:remove']">删除</el-button>
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

    <!-- 添加或修改工作站对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="workstationRef" :model="form" :rules="rules" label-width="80px" :disabled="dialogMode === 'view'">
        <el-form-item label="工位编码" prop="workstationCode">
          <el-input v-model="form.workstationCode" placeholder="请输入工位编码" />
        </el-form-item>
        <el-form-item label="工位名称" prop="workstationName">
          <el-input v-model="form.workstationName" placeholder="请输入工位名称" />
        </el-form-item>
        <el-form-item label="所属车间ID" prop="workshopId">
          <el-input v-model="form.workshopId" placeholder="请输入所属车间ID" />
        </el-form-item>
        <el-form-item label="所属工序ID" prop="processId">
          <el-input v-model="form.processId" placeholder="请输入所属工序ID" />
        </el-form-item>
        <el-form-item label="物理位置" prop="location">
          <el-input v-model="form.location" placeholder="请输入物理位置" />
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
        <el-divider content-position="center">关联-工位岗位配置信息</el-divider>
        <el-row :gutter="10" class="mb8" v-if="dialogMode !== 'view'">
          <el-col :span="1.5">
            <el-button type="primary" icon="Plus" @click="handleAddRelStationPosition">添加</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" icon="Delete" @click="handleDeleteRelStationPosition">删除</el-button>
          </el-col>
        </el-row>
        <el-table :data="relStationPositionList" :row-class-name="rowRelStationPositionIndex" @selection-change="handleRelStationPositionSelectionChange" ref="relStationPosition">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="序号" align="center" prop="index" width="50"/>
          <el-table-column label="岗位ID" prop="positionId" width="150">
            <template #default="scope">
              <el-input v-model="scope.row.positionId" placeholder="请输入岗位ID" :disabled="dialogMode === 'view'" />
            </template>
          </el-table-column>
          <el-table-column label="标准定员人数" prop="standardCount" width="150">
            <template #default="scope">
              <el-input v-model="scope.row.standardCount" placeholder="请输入标准定员人数" :disabled="dialogMode === 'view'" />
            </template>
          </el-table-column>
        </el-table>
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

<script setup name="Workstation">
import { listWorkstation, getWorkstation, delWorkstation, addWorkstation, updateWorkstation } from "@/api/mes/md/workstation";

const { proxy } = getCurrentInstance();
const { common_yes_no } = proxy.useDict('common_yes_no');

const workstationList = ref([]);
const relStationPositionList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const checkedRelStationPosition = ref([]);
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
    workstationCode: null,
    workstationName: null,
    workshopId: null,
    processId: null,
    status: null,
  },
  rules: {
    workstationCode: [
      { required: true, message: "工位编码不能为空", trigger: "blur" }
    ],
    workstationName: [
      { required: true, message: "工位名称不能为空", trigger: "blur" }
    ],
    workshopId: [
      { required: true, message: "所属车间ID不能为空", trigger: "blur" }
    ],
    status: [
      { required: true, message: "状态不能为空", trigger: "change" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询工作站列表 */
function getList() {
  loading.value = true;
  listWorkstation(queryParams.value).then(response => {
    workstationList.value = response.rows;
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
    workstationCode: null,
    workstationName: null,
    workshopId: null,
    processId: null,
    location: null,
    status: null,
    createTime: null,
    updateTime: null,
    isDeleted: null
  };
  relStationPositionList.value = [];
  proxy.resetForm("workstationRef");
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
  title.value = "添加工作站";
}

function handleUpdate(row) {
  reset();
  dialogMode.value = "edit";
  const _id = row.id || ids.value
  getWorkstation(_id).then(response => {
    form.value = response.data;
    relStationPositionList.value = response.data.relStationPositionList;
    open.value = true;
    title.value = "修改工作站";
  });
}

function handleView(row) {
  reset();
  dialogMode.value = "view";
  const _id = row.id;
  getWorkstation(_id).then(response => {
    form.value = response.data;
    relStationPositionList.value = response.data.relStationPositionList;
    open.value = true;
    title.value = "查看工作站";
  });
}

/** 提交按钮 */
function submitForm() {
  if (dialogMode.value === "view") return;
  proxy.$refs["workstationRef"].validate(valid => {
    if (valid) {
      form.value.relStationPositionList = relStationPositionList.value;
      if (form.value.id != null) {
        updateWorkstation(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addWorkstation(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除工作站编号为"' + _ids + '"的数据项？').then(function() {
    return delWorkstation(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 关联-工位岗位配置序号 */
function rowRelStationPositionIndex({ row, rowIndex }) {
  row.index = rowIndex + 1;
}

/** 关联-工位岗位配置添加按钮操作 */
function handleAddRelStationPosition() {
  let obj = {};
  obj.positionId = "";
  obj.standardCount = "";
  relStationPositionList.value.push(obj);
}

/** 关联-工位岗位配置删除按钮操作 */
function handleDeleteRelStationPosition() {
  if (checkedRelStationPosition.value.length == 0) {
    proxy.$modal.msgError("请先选择要删除的关联-工位岗位配置数据");
  } else {
    const relStationPositions = relStationPositionList.value;
    const checkedRelStationPositions = checkedRelStationPosition.value;
    relStationPositionList.value = relStationPositions.filter(function(item) {
      return checkedRelStationPositions.indexOf(item.index) == -1
    });
  }
}

/** 复选框选中数据 */
function handleRelStationPositionSelectionChange(selection) {
  checkedRelStationPosition.value = selection.map(item => item.index)
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('mes:md/workstation/export', {
    ...queryParams.value
  }, `workstation_${new Date().getTime()}.xlsx`)
}

getList();
</script>
