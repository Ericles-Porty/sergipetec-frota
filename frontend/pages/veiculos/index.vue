<script setup lang="ts">
import AdicionarVeiculoModalButton from '~/components/veiculos/AdicionarVeiculoModalButton.vue';
import VeiculosTable from '~/components/veiculos/VeiculosTable.vue';
import { useVeiculoApi } from '~/composables/useVeiculoApi'

const { refreshVeiculos } = useVeiculoApi()

const toast = useToast()

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
</script>

<template>
	<div class="h-full text-center flex flex-col items-center justify-start gap-6">
		<h1 class="text-6xl">Listagem de veiculos</h1>
		<AdicionarVeiculoModalButton @new-vehicle-added-event="newVehicleAddedEventHandler" />
		<VeiculosTable @deleted-vehicle-event="deletedVehicleEventHandler" />
	</div>
</template>
