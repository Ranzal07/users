<template>
  <q-page padding>
    <div class="row justify-center">
      <div class="col-12 col-md-8">
        <h2 class="text-center">List of Levels</h2>
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
                    v-if="isEditing(props.row)"
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
import * as levelService from 'src/service/levelService';
import { QTableColumn } from 'quasar';
import { Level } from 'src/model/types';

export default defineComponent({
  setup() {
    const levels = ref<Level[]>([]);
    const editingRow = ref<Level>();
    const originalData = ref<Level>();

    const getLevels = async () => {
      try {
        const response = await levelService.getAllLevels();
        levels.value = response.map(
          (level: Level, index: number) => {
            return {
              id: level.id,
              lineNo: index + 1,
              name: level.name,
              salaryRate: level.salaryRate,
              action: '',
            };
          }
        );
        levels.value.push({id: 0, name: '', salaryRate: null});
      } catch (error) {
        console.error('Failed to fetch levels', error);
      }
    };
    getLevels();

    const columns: QTableColumn[] = [
      { label: 'LINE NO', field: 'lineNo', name: 'lineNo', align: 'center' },
      { label: 'NAME', field: 'name', name: 'name', align: 'center' },
      {
        label: 'SALARY RATE',
        field: 'salaryRate',
        name: 'salaryRate',
        align: 'center',
      },
      { label: 'ACTION', field: 'action', name: 'action', align: 'center' },
    ];

    const isAddRow = (levelRow: Level) => {
      return levelRow.id === 0;
    };

    const addRow = async (levelRow: Level) => {
      try {
        await levelService.addLevel(levelRow);
        getLevels();
      } catch (error) {
        console.error('Failed to update positionRow: ', error);
      }
    };


    const isFieldEditable = (colName: string) => {
      return colName !== 'lineNo' && colName !== 'action';
    };

    const startEditing = (levelRow: Level) => {
      editingRow.value = levelRow;
      originalData.value = { ...levelRow };
    };

    const isEditing = (levelRow: Level) => {
      return editingRow.value === levelRow;
    };

    const updateRow = async (levelRow: Level) => {
      if (editingRow.value === levelRow) {
        try {
          await levelService.updateLevel(levelRow);
          getLevels();
        } catch (error) {
          console.error('Failed to update levelRow: ', error);
        }
      }
    };

    const deleteRow = async (levelRow: Level) => {
      try {
        await levelService.deleteLevel(levelRow);
        getLevels();
      } catch (error) {
        console.error('Failed to delete levelRow: ', error);
      }
    };

    const cancelEdit = (levelRow: Level) => {
      const index = levels.value.findIndex((r: Level) => r.id === levelRow.id);
      if (index !== -1 && originalData.value) {
        levels.value[index] = { ...originalData.value };
      }
    };

    return {
      rows: levels,
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
