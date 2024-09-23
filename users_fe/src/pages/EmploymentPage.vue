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
                    @click="cancelEdit(props.row)"
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
import { defineComponent, ref } from 'vue';
import * as employmentService from 'src/service/employmentService';
import { User, EmploymentRow, Level, Position } from 'src/model/types';
import * as util from 'src/utils/util';
import { QTableColumn } from 'quasar';

export default defineComponent({
  setup() {
    const users = ref<EmploymentRow[]>([]);
    const levels = ref<Level[]>([]);
    const positions = ref<Position[]>([]);
    const editingRow = ref<EmploymentRow>();
    const originalData = ref<EmploymentRow>();

    const getEmployments = async () => {
      try {
        const response = await employmentService.getAllEmployments();
        users.value = response.users.map(
          (user: User, index: number):EmploymentRow => {
            return {
              id: user.id,
              lineNo: index + 1,
              fullName: `${user.firstName} ${user.lastName}`,
              level: user.employment?.level,
              position: user.employment?.position,
              salary: util.calculateSalary(user.employment),
              status: user.employment?.status || 'Employed',
            };
          }
        );
        levels.value = response.levels.map((level: Level) => ({
          id: level.id,
          name: level.name,
        }));
        positions.value = response.positions.map((position: Position) => ({
          id: position.id,
          name: position.name,
        }));
      } catch (error) {
        console.error('Failed to fetch Employments: ', error);
      }
    };
    getEmployments();

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
      { label: 'SALARY', field: 'salary', name: 'salary', align: 'center' },
      { label: 'STATUS', field: 'status', name: 'status', align: 'center' },
      { label: 'ACTION', field: '', name: 'action', align: 'center' },
    ];

    const isFieldEditable = (colName: string) => {
      return colName === 'level' || colName === 'position';
    };

    const startEditing = (userRow: EmploymentRow) => {
      editingRow.value = userRow;
      originalData.value = { ...userRow };
    };

    const isEditing = (userRow: EmploymentRow) => {
      return editingRow.value === userRow;
    };

    const updateRow = async (userRow: EmploymentRow) => {
      if (editingRow.value === userRow) {
        try {
          const updatedData = {
            user: userRow.id,
            level: userRow.level.id,
            position: userRow.position.id,
          };
          await employmentService.updateEmployment(updatedData);
          getEmployments();
        } catch (error) {
          console.error('Failed to update userRow: ', error);
        }
      }
    };

    const deleteRow = (userRow: EmploymentRow) => {
      users.value = users.value.filter((r) => r.id !== userRow.id);
    };

    const cancelEdit = (userRow: EmploymentRow) => {
      const index = users.value.findIndex((r: EmploymentRow) => r.id === userRow.id);
      if (index !== -1 && originalData.value) {
        users.value[index] = { ...originalData.value };
      }
    };

    const getSelectOptions = (colName: string) => {
      if (colName === 'level') {
        return levels.value.map((level: Level) => ({
          label: level.name,
          value: level.id
        }));
      } else if (colName === 'position') {
        return positions.value.map((position: Position) => ({
          label: position.name,
          value: position.id
        }));
      }
      return [];
    };

    return {
      rows: users,
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
        rowsPerPage: 10, // Set the fixed number of userRows per page to 10
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
