<!-- components/VeiculosTable.vue -->
<template>
	<div v-if="status === 'pending'">
		<UTable sticky :loading="true" :columns="columns" :loading-animation="'carousel'"
			class="flex-1 max-h-[512px] overflow-y-auto" />
	</div>
	<div v-else-if="status === 'error'">Erro: {{ error }}</div>
	<div v-else class="flex flex-col items-center justify-center gap-4">
		<UTable sticky :data="dataTable" :columns="columns" :sorting="sorting"
			class="flex-1 max-h-[512px] overflow-y-auto" />

	</div>
</template>

<script setup lang="ts">
import type Veiculo from '~/types/Veiculo'
import type { TableColumn } from '@nuxt/ui'
import { useVeiculoApi } from '~/composables/useVeiculoApi'
import type { Column, ColumnSort } from '@tanstack/vue-table'

const { veiculos, status, error, deleteVeiculo } = useVeiculoApi()
const toast = useToast()

const UButton = resolveComponent('UButton')
const UDropdownMenu = resolveComponent('UDropdownMenu')

const emit = defineEmits<{
	(e: 'deletedVehicleEvent', event: string): void
	(e: 'openUpdateVehicleModalEvent', veiculo: Veiculo): void
}>()

type VeiculoTable = {
	id: number,
	modelo: string,
	fabricante: string,
	ano: number,
	preco: number,
	cor: string,
	tipo: string,
	quantidadePortas?: number,
	cilindrada?: number,
	tipoCombustivel?: 'Gasolina' | 'Etanol' | 'Diesel' | 'Flex'
}

const dataTable = computed<VeiculoTable[]>(
	() =>
		veiculos.value.map((veiculo: Veiculo) => {
			return {
				id: veiculo.id,
				modelo: veiculo.modelo,
				fabricante: veiculo.fabricante,
				ano: veiculo.ano,
				preco: veiculo.preco,
				cor: veiculo.cor,
				tipo: veiculo.tipo,
				quantidadePortas: veiculo.quantidadePortas,
				cilindrada: veiculo.cilindrada,
				tipoCombustivel: veiculo.tipoCombustivel
			} as VeiculoTable
		})
)

const columns: TableColumn<VeiculoTable>[] = [
	{
		accessorKey: 'id',
		header: ({ column }) => getHeader(column, 'ID'),
		cell: ({ row }) => row.getValue('id')
	},
	{
		accessorKey: 'modelo',
		header: ({ column }) => getHeader(column, 'Modelo'),
		cell: ({ row }) => row.getValue('modelo')
	},
	{
		accessorKey: 'fabricante',
		header: ({ column }) => getHeader(column, 'Fabricante'),
		cell: ({ row }) => row.getValue('fabricante')
	},
	{
		accessorKey: 'ano',
		header: ({ column }) => getHeader(column, 'Ano'),
		cell: ({ row }) => row.getValue('ano')
	},
	{
		accessorKey: 'preco',
		header: ({ column }) => getHeader(column, 'Preço'),
		cell: ({ row }) => {
			const preco = row.getValue('preco') as number
			return preco.toLocaleString('pt-BR', {
				style: 'currency',
				currency: 'BRL'
			})
		}
	},
	{
		accessorKey: 'cor',
		header: ({ column }) => getHeader(column, 'Cor'),
		cell: ({ row }) => row.getValue('cor')
	},
	{
		accessorKey: 'tipo',
		header: ({ column }) => getHeader(column, 'Tipo'),
		cell: ({ row }) => row.getValue('tipo')
	},
	{
		accessorKey: 'quantidadePortas',
		header: ({ column }) => getHeader(column, 'Quantidade de Portas'),
		cell: ({ row }) => {
			const quantidadePortas = row.getValue('quantidadePortas')
			return quantidadePortas !== undefined ? quantidadePortas : '-'
		}
	},
	{
		accessorKey: 'cilindrada',
		header: ({ column }) => getHeader(column, 'Cilindrada'),
		cell: ({ row }) => {
			const cilindrada = row.getValue('cilindrada')
			return cilindrada !== undefined ? `${cilindrada} cc` : '-'
		}
	},
	{
		accessorKey: 'tipoCombustivel',
		header: ({ column }) => getHeader(column, 'Tipo de Combustível'),
		cell: ({ row }) => {
			const tipoCombustivel = row.getValue('tipoCombustivel')
			return tipoCombustivel !== undefined ? tipoCombustivel : '-'
		}
	},
	{
		accessorKey: 'editar',
		header: 'Editar',
		cell: ({ row }) => {
			const veiculo = row.original as VeiculoTable
			return h(UButton, {
				icon: 'i-lucide-edit-2',
				color: 'primary',
				variant: 'ghost',
				class: 'text-blue-500 hover:text-blue-700 focus:text-blue-700 cursor-pointer',
				onClick: () => {
					emit('openUpdateVehicleModalEvent', {
						id: veiculo.id,
						modelo: veiculo.modelo,
						fabricante: veiculo.fabricante,
						ano: veiculo.ano,
						preco: veiculo.preco,
						cor: veiculo.cor,
						tipo: veiculo.tipo,
						quantidadePortas: veiculo.quantidadePortas,
						cilindrada: veiculo.cilindrada,
						tipoCombustivel: veiculo.tipoCombustivel
					} as Veiculo)
				},
				'aria-label': `Editar ${veiculo.modelo} ${veiculo.ano} ${veiculo.cor}`
			})
		}
	}
	,
	{
		accessorKey: 'delete',
		header: 'Apagar',
		cell: ({ row }) => {
			const veiculo = row.original as VeiculoTable
			return h(UButton, {
				icon: 'i-lucide-trash-2',
				color: 'danger',
				variant: 'ghost',
				class: 'text-red-500 hover:text-red-700 focus:text-red-700 cursor-pointer',
				onClick: async () => {
					if (confirm(`Deseja apagar ${veiculo.modelo} ${veiculo.ano} ${veiculo.cor}?`) === false) return

					const result = await deleteVeiculo(veiculo.id)
					if (result.success === false) {
						toast.add({
							title: 'Erro ao apagar veiculo',
							description: result.errors?.join(', ') ?? 'Erro desconhecido',
							duration: 5000,
							color: 'error'
						})

						return
					}

					emit('deletedVehicleEvent', `${veiculo.modelo} ${veiculo.ano} ${veiculo.cor}`)
				},
				'aria-label': `Apagar ${veiculo.modelo} ${veiculo.ano} ${veiculo.cor}`
			})
		}
	}
]

const sorting: ColumnSort[] = [
	{
		id: 'id',
		desc: false
	},
	{
		id: 'modelo',
		desc: false
	},
	{
		id: 'fabricante',
		desc: false
	},
	{
		id: 'ano',
		desc: false
	},
	{
		id: 'preco',
		desc: false
	},
	{
		id: 'cor',
		desc: false
	},
	{
		id: 'tipo',
		desc: false
	},
	{
		id: 'quantidadePortas',
		desc: false
	},
	{
		id: 'cilindrada',
		desc: false
	},
	{
		id: 'tipoCombustivel',
		desc: false
	}
]

function getHeader(column: Column<VeiculoTable>, label: string) {
	const isSorted = column.getIsSorted()

	return h(
		UDropdownMenu,
		{
			content: {
				align: 'start'
			},
			'aria-label': 'Actions dropdown',
			items: [
				{
					label: 'Asc',
					type: 'checkbox',
					icon: 'i-lucide-arrow-up-narrow-wide',
					checked: isSorted === 'asc',
					onSelect: () => {
						if (isSorted === 'asc') {
							column.clearSorting()
						} else {
							column.toggleSorting(false)
						}
					}
				},
				{
					label: 'Desc',
					icon: 'i-lucide-arrow-down-wide-narrow',
					type: 'checkbox',
					checked: isSorted === 'desc',
					onSelect: () => {
						if (isSorted === 'desc') {
							column.clearSorting()
						} else {
							column.toggleSorting(true)
						}
					}
				}
			]
		},
		() =>
			h(UButton, {
				color: 'neutral',
				variant: 'ghost',
				label,
				icon: isSorted
					? isSorted === 'asc'
						? 'i-lucide-arrow-up-narrow-wide'
						: 'i-lucide-arrow-down-wide-narrow'
					: 'i-lucide-arrow-up-down',
				class: '-mx-2.5 data-[state=open]:bg-(--ui-bg-elevated) cursor-pointer',
				'aria-label': `Sort by ${isSorted === 'asc' ? 'descending' : 'ascending'}`
			})
	)
}
</script>

<style scoped></style>