<template>
	<div class="flex flex-wrap gap-4 items-end p-4 border rounded-lg">
		<UInput v-model="filters.id" type="number" placeholder="Filtrar por ID" class="w-32" />
		<UInput v-model="filters.ano" type="number" placeholder="Filtrar por Ano" class="w-32" />
		<UInput v-model="filters.modelo" type="text" placeholder="Filtrar por Modelo" class="w-36" />
		<UInput v-model="filters.cor" type="text" placeholder="Filtrar por Cor" class="w-32" />
		<USelect v-model="filters.tipo" :items="['Carro', 'Moto']" placeholder="Filtrar por Tipo" class="w-36" />
		<UButton icon="i-lucide-filter" @click="aplicarFiltros">Filtrar</UButton>
		<UButton icon="i-lucide-x" color="neutral" variant="ghost" @click="limparFiltros">Limpar</UButton>
	</div>
</template>

<script setup lang="ts">

const {refreshVeiculos ,getVeiculos, getVeiculoPorId } = useVeiculoApi()

const filters = reactive({
	id: undefined as number | undefined,
	modelo: '',
	ano: undefined as number | undefined,
	cor: '',
	tipo: '',
})

function aplicarFiltros() {
	const { id, modelo, ano, cor, tipo } = filters

	if (id) {
		getVeiculoPorId(id)
	} else {
		getVeiculos({ modelo, ano, cor, tipo })
	}
}

function limparFiltros() {
	filters.id = undefined
	filters.modelo = ''
	filters.ano = undefined
	filters.cor = ''
	filters.tipo = ''

	refreshVeiculos()
}

</script>
