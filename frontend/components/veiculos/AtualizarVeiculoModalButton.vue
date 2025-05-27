<!-- components/veiculos/AtualizarVeiculoModalButton.vue -->
<template>
	<UModal v-model:open="isUpdateModalOpenLocal" title="Atualizar Veículo"
		description="Atualizar um veículo existente no sistema">
		<template #body>
			<UForm :schema="schemaUpdateVeiculo" :state="stateUpdateVeiculo" @submit="onSubmitUpdateVeiculo">
				<UFormField label="ID do veiculo" name="id" class="w-full">
					<div class="flex flex-row justify-between items-center">
						<UInput v-model="stateUpdateVeiculo.id" type="number" placeholder="ID do veiculo" required
							class="w-2/10" disabled />
					</div>
				</UFormField>
				<div class="flex flex-col gap-4 items-end">
					<UFormField label="Modelo do veiculo" name="modelo" class="w-full">
						<div class="flex flex-row justify-between items-center">
							<UInput v-model="stateUpdateVeiculo.modelo" placeholder="Modelo do veiculo" required
								class="w-4/10" />
						</div>
					</UFormField>
					<UFormField label="Fabricante do veiculo" name="fabricante" class="w-full">
						<div class="flex flex-row justify-between items-center">
							<UInput v-model="stateUpdateVeiculo.fabricante" placeholder="Fabricante do veiculo" required
								class="w-4/10" />
						</div>
					</UFormField>
					<UFormField label="Ano do veiculo" name="ano" class="w-full">
						<div class="flex flex-row justify-between items-center">
							<UInput v-model="stateUpdateVeiculo.ano" type="number" placeholder="Ano do veiculo" required
								class="w-2/10" />
						</div>
					</UFormField>
					<UFormField label="Preço do veiculo" name="preco" class="w-full">
						<div class="flex flex-row justify-between items-center">
							<UInput v-model="precoFormatado" type="text" placeholder="Preço do veiculo" required
								class="w-3/10" />
						</div>
					</UFormField>
					<UFormField label="Cor do veiculo" name="cor" class="w-full">
						<div class="flex flex-row justify-between items-center">
							<UInput v-model="stateUpdateVeiculo.cor" placeholder="Cor do veiculo" required
								class="w-3/10" />
						</div>
					</UFormField>
					<UFormField label="Tipo do veiculo" name="tipo" class="w-full">
						<div class="flex flex-row justify-between items-center">
							<!-- Não pode alterar o tipo de veiculo -->
							<USelect v-model="stateUpdateVeiculo.tipo" :items="[stateUpdateVeiculo.tipo!]" disabled
								placeholder="Tipo do veiculo" required class="w-3-5/10" />
						</div>
					</UFormField>
					<!-- if tipo veiculo = moto update field cilindrada -->
					<UFormField v-if="stateUpdateVeiculo.tipo === 'Moto'" label="Cilindrada da moto" name="cilindrada"
						class="w-full">
						<div class="flex flex-row justify-between items-center">
							<UInput v-model="stateUpdateVeiculo.cilindrada" type="number"
								placeholder="Cilindrada da moto" required class="w-3/10" />
						</div>
					</UFormField>
					<!-- if tipo veiculo = carro update field numero de portas -->
					<UFormField v-if="stateUpdateVeiculo.tipo === 'Carro'" label="Número de portas do carro"
						name="quantidadePortas" class="w-full">
						<div class="flex flex-row justify-between items-center">
							<UInput v-model="stateUpdateVeiculo.quantidadePortas" type="number"
								placeholder="Número de portas do carro" required class="w-3/10" />
						</div>
					</UFormField>
					<!-- if tipo veiculo = carro update field tipo de combustivel -->
					<UFormField v-if="stateUpdateVeiculo.tipo === 'Carro'" label="Tipo de combustível do carro"
						name="tipoCombustivel" class="w-full">
						<div class="flex flex-row justify-between items-center">
							<USelect v-model="stateUpdateVeiculo.tipoCombustivel" :items="tiposCombustivel"
								placeholder="Tipo de combustível do carro" required class="w-6/10" />
						</div>
					</UFormField>
					<UFormField class="w-full">
						<div class="flex flex-row justify-end items-center gap-4">
							<UButton icon="lucide:x" label="Cancelar" color="neutral" variant="subtle"
								class="h-8 w-3/10" @click="onCloseUpdateVehicleModal" />


							<UButton icon="lucide:save" label="Atualizar" color="primary" variant="solid" type="submit"
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
import { schemaUpdateVeiculo } from '~/schemas/SchemaUpdateVeiculo';
import { useVeiculoApi } from '~/composables/useVeiculoApi';
import type Veiculo from '~/types/Veiculo';

const { updateVeiculo } = useVeiculoApi()

const precoFormatado = computed({
	get() {
		const preco = parseFloat(stateUpdateVeiculo.preco)
		if (isNaN(preco)) return ''
		return new Intl.NumberFormat('pt-BR', {
			style: 'currency',
			currency: 'BRL',
		}).format(preco)
	},
	set(valor) {
		const somenteNumeros = valor.replace(/\D/g, '') || '0'
		const numero = parseFloat(somenteNumeros) / 100
		stateUpdateVeiculo.preco = numero.toFixed(2)
	}
})

const toast = useToast()

const props = defineProps<{
	veiculo: Veiculo | null,
	isUpdateModalOpen: boolean
}>()

const isUpdateModalOpenLocal = ref(props.isUpdateModalOpen)


watch(() => props.isUpdateModalOpen, (val) => {
	console.log('watch isUpdateModalOpen', val, isUpdateModalOpenLocal.value)
	isUpdateModalOpenLocal.value = val
})

watch(isUpdateModalOpenLocal, (val) => {
	console.log('watch isUpdateModalOpenLocal', val , props.isUpdateModalOpen)
	if (!val) {
		emit('closeUpdateVehicleModalEvent')
		console.log('isUpdateModalOpenLocal mudou para false, emitindo evento')
	}
})

const stateUpdateVeiculo = reactive<{
	id: number
	modelo: string
	fabricante: string
	ano: number
	preco: string
	cor: string
	tipo: "Carro" | "Moto" | undefined
	cilindrada: number
	quantidadePortas: number
	tipoCombustivel: "Gasolina" | "Etanol" | "Diesel" | "Flex" | undefined
}>({
	id: props.veiculo?.id ?? 0,
	modelo: props.veiculo?.modelo ?? '',
	fabricante: props.veiculo?.fabricante ?? '',
	ano: props.veiculo?.ano ?? new Date().getFullYear(),
	preco: props.veiculo?.preco.toFixed(2) ?? '',
	cor: props.veiculo?.cor ?? '',
	tipo: props.veiculo?.tipo as "Carro" | "Moto" | undefined,
	cilindrada: props.veiculo?.cilindrada ?? 150,
	quantidadePortas: props.veiculo?.quantidadePortas ?? 1,
	tipoCombustivel: props.veiculo?.tipoCombustivel as "Gasolina" | "Etanol" | "Diesel" | "Flex" | undefined,
})

watch(() => props.veiculo, (val) => {
	if (val) {
		stateUpdateVeiculo.id = val.id
		stateUpdateVeiculo.modelo = val.modelo
		stateUpdateVeiculo.fabricante = val.fabricante
		stateUpdateVeiculo.ano = val.ano
		stateUpdateVeiculo.preco = val.preco.toFixed(2)
		stateUpdateVeiculo.cor = val.cor
		stateUpdateVeiculo.tipo = val.tipo as "Carro" | "Moto" | undefined
		stateUpdateVeiculo.cilindrada = val.cilindrada ?? 150
		stateUpdateVeiculo.quantidadePortas = val.quantidadePortas ?? 1
		stateUpdateVeiculo.tipoCombustivel = val.tipoCombustivel as "Gasolina" | "Etanol" | "Diesel" | "Flex" | undefined
	}
})

const tiposCombustivel = ['Gasolina', 'Etanol', 'Diesel', "Flex"]

const emit = defineEmits<{
	(e: 'newVehicleUpdatedEvent', event: string): void
	(e: 'closeUpdateVehicleModalEvent'): void
}>()

const onSubmitUpdateVeiculo = async (event: FormSubmitEvent<z.output<typeof schemaUpdateVeiculo>>) => {
	console.log('onSubmitUpdateVeiculo', event.data)
	const response = await updateVeiculo(event.data.id, event.data)

	if (response.success === false) {
		toast.add({
			title: 'Erro ao adicionar veiculo',
			description: response.errors?.join(', ') ?? 'Erro desconhecido',
			duration: 5000,
			color: 'error',
		})

		return
	}

	stateUpdateVeiculo.id = 0
	stateUpdateVeiculo.modelo = ''
	stateUpdateVeiculo.fabricante = ''
	stateUpdateVeiculo.ano = new Date().getFullYear()
	stateUpdateVeiculo.preco = ''
	stateUpdateVeiculo.cor = ''
	stateUpdateVeiculo.tipo = undefined
	stateUpdateVeiculo.cilindrada = 150
	stateUpdateVeiculo.quantidadePortas = 1
	stateUpdateVeiculo.tipoCombustivel = undefined

	console.log('Veículo atualizado com sucesso:', response.data)
	emit('newVehicleUpdatedEvent', `${response.data?.modelo} ${response.data?.ano} ${response.data?.cor}`)
}

const onCloseUpdateVehicleModal = () => {
	emit('closeUpdateVehicleModalEvent')
	isUpdateModalOpenLocal.value = false
	stateUpdateVeiculo.modelo = ''
	stateUpdateVeiculo.fabricante = ''
	stateUpdateVeiculo.ano = new Date().getFullYear()
	stateUpdateVeiculo.preco = ''
	stateUpdateVeiculo.cor = ''
	stateUpdateVeiculo.tipo = undefined
	stateUpdateVeiculo.cilindrada = 150
	stateUpdateVeiculo.quantidadePortas = 1
	stateUpdateVeiculo.tipoCombustivel = undefined
}

</script>