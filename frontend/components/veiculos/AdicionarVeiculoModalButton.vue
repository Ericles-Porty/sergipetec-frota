<!-- components/veiculos/AdicionarVeiculoModalButton.vue -->
<template>
	<UModal v-model:open="isModalOpen" title="Adicionar Veículo" description="Adicionar um novo veículo ao sistema">

		<UButton icon="lucide:plus" label="Adicionar Veículo" color="neutral" variant="subtle"
			@click="isModalOpen = true" />

		<template #body>
			<UForm :schema="schemaAddVeiculo" :state="stateAddVeiculo" @submit="onSubmitAddVeiculo">
				<div class="flex flex-col gap-4 items-end">
					<UFormField label="Modelo do veiculo" name="modelo" class="w-full">
						<div class="flex flex-row justify-between items-center">
							<UInput v-model="stateAddVeiculo.modelo" placeholder="Modelo do veiculo" required
								class="w-4/10" />
						</div>
					</UFormField>
					<UFormField label="Fabricante do veiculo" name="fabricante" class="w-full">
						<div class="flex flex-row justify-between items-center">
							<UInput v-model="stateAddVeiculo.fabricante" placeholder="Fabricante do veiculo"
								required class="w-4/10" />
						</div>
					</UFormField>
					<UFormField label="Ano do veiculo" name="ano" class="w-full">
						<div class="flex flex-row justify-between items-center">
							<UInput v-model="stateAddVeiculo.ano" type="number" placeholder="Ano do veiculo"
								required class="w-2/10" />
						</div>
					</UFormField>
					<UFormField label="Preço do veiculo" name="preco" class="w-full">
						<div class="flex flex-row justify-between items-center">
							<UInput v-model="stateAddVeiculo.preco" type="number" placeholder="Preço do veiculo"
								required class="w-3/10" />
						</div>
					</UFormField>
					<UFormField label="Cor do veiculo" name="cor" class="w-full">
						<div class="flex flex-row justify-between items-center">
							<UInput v-model="stateAddVeiculo.cor" placeholder="Cor do veiculo" required
								class="w-3/10" />
						</div>
					</UFormField>
					<UFormField label="Tipo do veiculo" name="tipo" class="w-full">
						<div class="flex flex-row justify-between items-center">
							<USelect v-model="stateAddVeiculo.tipo" :items="tiposVeiculo"
								placeholder="Tipo do veiculo" required class="w-6/10" />
						</div>
					</UFormField>
					<!-- if tipo veiculo = moto add field cilindrada -->
					<UFormField v-if="stateAddVeiculo.tipo === 'Moto'" label="Cilindrada da moto" name="cilindrada"
						class="w-full">
						<div class="flex flex-row justify-between items-center">
							<UInput v-model="stateAddVeiculo.cilindrada" type="number"
								placeholder="Cilindrada da moto" required class="w-3/10" />
						</div>
					</UFormField>
					<!-- if tipo veiculo = carro add field numero de portas -->
					<UFormField v-if="stateAddVeiculo.tipo === 'Carro'" label="Número de portas do carro"
						name="quantidadePortas" class="w-full">
						<div class="flex flex-row justify-between items-center">
							<UInput v-model="stateAddVeiculo.quantidadePortas" type="number"
								placeholder="Número de portas do carro" required class="w-3/10" />
						</div>
					</UFormField>
					<!-- if tipo veiculo = carro add field tipo de combustivel -->	
					<UFormField v-if="stateAddVeiculo.tipo === 'Carro'" label="Tipo de combustível do carro"
						name="tipoCombustivel" class="w-full">
						<div class="flex flex-row justify-between items-center">
							<USelect v-model="stateAddVeiculo.tipoCombustivel" :items="tiposCombustivel"
								placeholder="Tipo de combustível do carro" required class="w-6/10" />
						</div>
					</UFormField>
					<UFormField class="w-full">
						<div class="flex flex-row justify-end items-center gap-4">
							<UButton icon="lucide:x" label="Cancelar" color="neutral" variant="subtle"
								@click="isModalOpen = false" class="h-8 w-3/10" />


							<UButton icon="lucide:save" label="Adicionar" color="primary" variant="solid" type="submit"
								loading-auto class="h-8 w-3/10" />
						</div>
					</UFormField>
				</div>
			</UForm>
		</template>
	</UModal>
</template>

<script setup lang="ts">
import { useToast } from '#imports';
import type { FormSubmitEvent } from '@nuxt/ui'
import type * as z from 'zod'
import { schemaAddVeiculo } from '~/schemas/SchemaAddVeiculo';

const { addVeiculo } = useVeiculoApi()
const toast = useToast()

const stateAddVeiculo = reactive({
	modelo: '',
	fabricante: '',
	ano: 0,
	preco: 0,
	cor: '',
	tipo: '',
	cilindrada: 150,
	quantidadePortas: 1,
	tipoCombustivel: '',
})

const tiposVeiculo = ['Carro', 'Moto']
const tiposCombustivel = ['Gasolina', 'Etanol', 'Diesel', "Flex"]

const isModalOpen = ref(false)

const emit = defineEmits<{
	(e: 'newVehicleAddedEvent', event: string): void
}>()

const onSubmitAddVeiculo = async (event: FormSubmitEvent<z.output<typeof schemaAddVeiculo>>) => {
	isModalOpen.value = false
	stateAddVeiculo.modelo = ''
	stateAddVeiculo.fabricante = ''
	stateAddVeiculo.ano = 0
	stateAddVeiculo.preco = 0
	stateAddVeiculo.cor = ''
	stateAddVeiculo.tipo = ''
	stateAddVeiculo.cilindrada = 150
	stateAddVeiculo.quantidadePortas = 1
	stateAddVeiculo.tipoCombustivel = ''

	const response = await addVeiculo(event.data)

	if (response.success === false) {
		toast.add({
			title: 'Erro ao adicionar veiculo',
			description: response.errors?.join(', ') ?? 'Erro desconhecido',
			duration: 5000,
			color: 'error',
		})

		return
	}

	emit('newVehicleAddedEvent', event.data)
}

</script>