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
import * as levelService from 'src/service/levelService';
import { QTableColumn } from 'quasar';
import { LevelsPayload } from 'src/payload/types';
import { useRouter } from 'vue-router';
import { isValidToken } from 'src/router/authGuard';

export default defineComponent({
  setup() {
    const levels = ref<LevelsPayload[]>([]);
    const editingRow = ref<LevelsPayload | null>();
    const originalData = ref<LevelsPayload>();
    const router = useRouter();

    const fetchLevels = async () => {
      levels.value = await levelService.getAllLevels();
    };
    onMounted(() => {
      fetchLevels();
    });

    // levels.value.push({lineNo: null, id: 0, name: '', salaryRate: ''});

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

    const isAddRow = (levelRow: LevelsPayload) => {
      return levelRow.id === 0;
    };

    const addRow = async (levelRow: LevelsPayload) => {
      await levelService.addLevel(levelRow);
      fetchLevels();
    };

    const isFieldEditable = (colName: string) => {
      return colName !== 'lineNo' && colName !== 'action';
    };

    const startEditing = (levelRow: LevelsPayload) => {
      editingRow.value = levelRow;
      originalData.value = { ...levelRow };
    };

    const isEditing = (levelRow: LevelsPayload) => {
      return editingRow.value === levelRow;
    };

    const updateRow = async (levelRow: LevelsPayload) => {
      if (await isValidToken(router) && editingRow.value === levelRow) {
        await levelService.updateLevel(levelRow);
        fetchLevels();
      }
    };

    const deleteRow = async (levelRow: LevelsPayload) => {
      await levelService.deleteLevel(levelRow);
      fetchLevels();
    };

    const cancelEdit = () => {
      fetchLevels();
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
        rowsPerPage: levels.value.length,
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
