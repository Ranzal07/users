<template>
  <q-page padding>
    <div class="row justify-center">
      <div class="col-12 col-md-8">
        <h2 class="text-center">List of Positions</h2>
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
                  'dynamic-col': isFieldEditable(col.name),
                }"
              >
                <q-input
                  v-if="(isFieldEditable(col.name) && (isEditing(props.row) || isAddRow(props.row)))"
                    v-model="props.row[col.field]"
                    outlined
                    dense
                />
                <span v-else>{{ props.row[col.field] }}</span>
                <template v-if="col.name === 'action'">
                  <q-btn
                    v-if="!isEditing(props.row) && !isAddRow(props.row)"
                    color="primary"
                    label="Edit"
                    outline
                    style="margin: 8px"
                    @click="startEditing(props.row)"
                  />
                  <q-btn
                    v-else-if="isAddRow(props.row)"
                    color="primary"
                    label="Add"
                    outline
                    style="margin: 8px"
                    @click="addRow(props.row)"
                  />
                  <q-btn
                    v-else
                    color="primary"
                    label="Update"
                    outline
                    style="margin: 8px"
                    @click="updateRow(props.row)"
                  />
                  <q-btn
                    v-if="!isEditing(props.row) && !isAddRow(props.row)"
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
import * as positionService from 'src/service/positionService';
import { QTableColumn } from 'quasar';
import { Position } from 'src/model/types';

export default defineComponent({
  setup() {
    const positions = ref<Position[]>([]);
    const editingRow = ref<Position>();
    const originalData = ref<Position>();

    const getPositions = async () => {
      try {
        const response = await positionService.getAllPositions();
        positions.value = response.map(
          (position: Position, index: number) => {
            return {
              id: position.id,
              lineNo: index + 1,
              name: position.name,
              salary: position.salary?.toLocaleString(),
              role: position.role,
              action: '',
            };
          }
        );
        positions.value.push({id: 0, name: '', salary: null, role: ''});
      } catch (error) {
        console.error('Failed to fetch positions', error);
      }
    };
    getPositions();

    const columns: QTableColumn[] = [
      { label: 'LINE NO', field: 'lineNo', name: 'lineNo', align: 'center' },
      { label: 'NAME', field: 'name', name: 'name', align: 'center' },
      {
        label: 'SALARY',
        field: 'salary',
        name: 'salary',
        align: 'center',
      },
      {
        label: 'ROLE',
        field: 'role',
        name: 'role',
        align: 'center',
      },
      { label: 'ACTION', field: 'action', name: 'action', align: 'center' },
    ];

    const isAddRow = (positionRow: Position) => {
      return positionRow.id === 0;
    };

    const addRow = async (positionRow: Position) => {
      try {
        await positionService.addPosition(positionRow);
        getPositions();
      } catch (error) {
        console.error('Failed to update positionRow: ', error);
      }
    };

    const isFieldEditable = (colName: string) => {
      return colName !== 'lineNo' && colName !== 'action';
    };

    const startEditing = (positionRow: Position) => {
      editingRow.value = positionRow;
      originalData.value = { ...positionRow };
    };

    const isEditing = (positionRow: Position) => {
      return editingRow.value === positionRow;
    };

    const updateRow = async (positionRow: Position) => {
      if (editingRow.value === positionRow) {
        try {
          await positionService.updatePosition(positionRow);
          getPositions();
        } catch (error) {
          console.error('Failed to update positionRow: ', error);
        }
      }
    };

    const deleteRow = async (positionRow: Position) => {
      try {
        await positionService.deletePosition(positionRow);
        getPositions();
      } catch (error) {
        console.error('Failed to delete positionRow: ', error);
      }
    };

    const cancelEdit = (positionRow: Position) => {
      const index = positions.value.findIndex(
        (r: Position) => r.id === positionRow.id
      );
      if (index !== -1 && originalData.value) {
        positions.value[index] = { ...originalData.value };
      }
    };

    return {
      rows: positions,
      columns,
      isAddRow,
      addRow,
      isFieldEditable,
      startEditing,
      updateRow,
      isEditing,
      deleteRow,
      cancelEdit,
      pagination: {
        page: 1,
        rowsPerPage: 10,
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
