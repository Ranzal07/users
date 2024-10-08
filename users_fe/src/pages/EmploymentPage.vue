<template>
  <q-page padding>
    <div class="row justify-center">
      <div class="col-12 col-md-8">
        <h2 class="text-center">Employment Management</h2>
        <q-table
          class="custom-table"
          :columns="columns"
          :rows="rows"
          flat
          bordered
          v-model:pagination="pagination"
          :rows-per-page-options="[]"
          :separator="'vertical'"
        >
          <template v-slot:body="props">
            <q-tr :props="props">
              <q-td
                v-for="col in props.cols"
                :key="col.name"
                :props="props"
                :class="{
                  'line-no-col': col.name === 'lineNo',
                  'action-col': col.name === 'action',
                  'dynamic-col': col.name !== 'lineNo' && col.name !== 'action',
                }"
              >
                <q-select
                  v-if="isFieldEditable(col.name) && isEditing(props.row)"
                  :options="getSelectOptions(col.name)"
                  v-model="props.row[col.field].id"
                  outlined
                  dense
                  dark
                  emit-value
                  map-options
                />
                <span v-else>{{ !isFieldEditable(col.name) ? props.row[col.name] : props.row[col.name].name }}</span>
                <template v-if="col.name === 'action'">
                  <q-btn
                    v-if="!isEditing(props.row)"
                    color="primary"
                    label="Edit"
                    outline
                    style="margin: 8px"
                    @click="startEditing(props.row)"
                  />
                  <q-btn
                    v-if="isEditing(props.row)"
                    color="primary"
                    label="Update"
                    outline
                    style="margin: 8px"
                    @click="updateRow(props.row)"
                  />
                  <q-btn
                    v-if="!isEditing(props.row)"
                    color="negative"
                    label="Delete"
                    outline
                    @click="deleteRow(props.row)"
                  />
                  <q-btn
                    v-if="isEditing(props.row)"
                    color="secondary"
                    label="Cancel"
                    outline
                    @click="cancelEdit()"
                  />
                </template>
              </q-td>
            </q-tr>
          </template>
        </q-table>
      </div>
    </div>
  </q-page>
</template>

<script lang="ts">
import { defineComponent, onMounted, ref } from 'vue';
import * as employmentService from 'src/service/employmentService';
import { EmploymentsPayload, LevelsPayload, PositionsPayload } from 'src/payload/types';
import { QTableColumn } from 'quasar';
import { useRouter } from 'vue-router';
import { isValidToken } from 'src/router/authGuard';

export default defineComponent({
  setup() {
    const employments = ref<EmploymentsPayload[]>([]);
    const levels = ref<LevelsPayload[]>([]);
    const positions = ref<PositionsPayload[]>([]);
    const editingRow = ref<EmploymentsPayload>();
    const originalData = ref<EmploymentsPayload>();
    const router = useRouter();

    const fetchEmployments = async () => {
      const response = await employmentService.getAllEmployments();
      employments.value = response.employments;
      levels.value = response.levels.filter((level: LevelsPayload) => level.id !== 0);
      positions.value = response.positions.filter((position: PositionsPayload) => position.id !== 0);
    };
    onMounted(() => {
      fetchEmployments();
    });

    const columns: QTableColumn[] = [
      { label: 'LINE NO', field: 'lineNo', name: 'lineNo', align: 'center' },
      {
        label: 'FULL NAME',
        field: 'fullName',
        name: 'fullName',
        align: 'center',
      },
      { label: 'LEVEL', field: 'level', name: 'level', align: 'center' },
      {
        label: 'POSITION',
        field: 'position',
        name: 'position',
        align: 'center',
      },
      { label: 'SALARY', field: 'salary', name: 'salary', align: 'center', format: (val) => `${val}%` },
      { label: 'STATUS', field: 'status', name: 'status', align: 'center' },
      { label: 'ACTION', field: '', name: 'action', align: 'center' },
    ];

    const isFieldEditable = (colName: string) => {
      return colName === 'level' || colName === 'position';
    };

    const startEditing = (employmentRow: EmploymentsPayload) => {
      editingRow.value = employmentRow;
      originalData.value = { ...employmentRow };
    };

    const isEditing = (employmentRow: EmploymentsPayload) => {
      return editingRow.value === employmentRow;
    };

    const updateRow = async (employmentRow: EmploymentsPayload) => {
      if (await isValidToken(router) && editingRow.value === employmentRow) {
        const updatedData = {
          userId: employmentRow.id,
          levelId: employmentRow.level.id,
          positionId: employmentRow.position.id,
        };
        await employmentService.updateEmployment(updatedData);
        fetchEmployments();
      }
    };

    const deleteRow = (employmentRow: EmploymentsPayload) => {
      employments.value = employments.value.filter((r) => r.id !== employmentRow.id);
      fetchEmployments();
    };

    const cancelEdit = () => {
      fetchEmployments();
    };

    const getSelectOptions = (colName: string) => {
      if (colName === 'level') {
        return levels.value.map((level: LevelsPayload) => ({
          label: level.name,
          value: level.id
        }));
      } else if (colName === 'position') {
        return positions.value.map((position: PositionsPayload) => ({
          label: position.name,
          value: position.id
        }));
      }
      return [];
    };

    return {
      rows: employments,
      columns,
      startEditing,
      updateRow,
      isEditing,
      deleteRow,
      cancelEdit,
      isFieldEditable,
      getSelectOptions,
      pagination: {
        page: 1,
        rowsPerPage: employments.value.length,
      },
    };
  },
});
</script>

<style scoped>
.container {
  margin-top: 20px;
}
</style>
