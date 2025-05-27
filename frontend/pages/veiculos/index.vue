<script setup lang="ts">
import AdicionarVeiculoModalButton from '~/components/veiculos/AdicionarVeiculoModalButton.vue';
import AtualizarVeiculoModalButton from '~/components/veiculos/AtualizarVeiculoModalButton.vue';
import VeiculosTable from '~/components/veiculos/VeiculosTable.vue';
import { useVeiculoApi } from '~/composables/useVeiculoApi'
import type Veiculo from '~/types/Veiculo';

const { refreshVeiculos } = useVeiculoApi()

const toast = useToast()

const veiculoParaAtualizar = ref<Veiculo | null>(null)
const isUpdateModalOpen = ref(false)

const newVehicleAddedEventHandler = async (event: string) => {
	toast.add({
		title: `Sucesso ao adicionar ${event}`,
		duration: 5000,
		description: `${event} foi adicionada com sucesso`,
		close: true,
	})

	await refreshVeiculos()
}

const deletedVehicleEventHandler = async (event: string) => {
	toast.add({
		title: `Sucesso ao apagar ${event}`,
		duration: 5000,
		description: `${event} foi apagada com sucesso`,
		close: true,
	})

	await refreshVeiculos()
}

const openUpdateVehicleModalEventHandler = async (veiculo: Veiculo) => {
	veiculoParaAtualizar.value = veiculo;
	isUpdateModalOpen.value = true;
}

const newVehicleUpdatedEventHandler = async (event: string) => {
	toast.add({
		title: `Sucesso ao atualizar ${event}`,
		duration: 5000,
		description: `${event} foi atualizada com sucesso`,
		close: true,
	})

	await refreshVeiculos()
	isUpdateModalOpen.value = false;
}

const closeUpdateVehicleModalEventHandler = () => {
	isUpdateModalOpen.value = false;
	veiculoParaAtualizar.value = null;
}

watch(isUpdateModalOpen, (newValue) => {
	if (newValue === false) {
		veiculoParaAtualizar.value = null;
	}
})

</script>

<template>
	<div class="h-full text-center flex flex-col items-center justify-start gap-6">
		<h1 class="text-4xl">Listagem de veiculos</h1>

		<AdicionarVeiculoModalButton @new-vehicle-added-event="newVehicleAddedEventHandler" />
		<AtualizarVeiculoModalButton :veiculo=veiculoParaAtualizar :is-update-modal-open=isUpdateModalOpen
			@new-vehicle-updated-event="newVehicleUpdatedEventHandler"
			@close-update-vehicle-modal-event="closeUpdateVehicleModalEventHandler" />
		<VeiculosFilterBar />
		<VeiculosTable @deleted-vehicle-event="deletedVehicleEventHandler"
			@open-update-vehicle-modal-event="openUpdateVehicleModalEventHandler" />
	</div>
</template>
