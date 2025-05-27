import { useFetch } from '#app'
import type { schemaAddVeiculo } from '~/schemas/SchemaAddVeiculo'
import type { ApiResponseWrapper } from '~/types/ApiResponseWrapper'
import type Veiculo from '~/types/Veiculo'

import type { z } from 'zod'
import type { schemaUpdateVeiculo } from '~/schemas/SchemaUpdateVeiculo'

const veiculos = ref<Veiculo[]>([])

export function useVeiculoApi() {
	const baseEndpoint = '/api/veiculos'

	const { data, status, error, refresh } = useFetch<ApiResponseWrapper<Veiculo[]>>(baseEndpoint, {
		method: 'GET',
		headers: {
			'Content-Type': 'application/json',
		},
	})

	watch(data, (val) => {
		veiculos.value = val?.data ?? []
	})

	const refreshVeiculos = async () => {
		await refresh()
	}

	// Busca com filtros (query parameters)
	async function getVeiculos(queryParameters: object): Promise<ApiResponseWrapper<Veiculo[]>> {
		const queryString = Object.entries(queryParameters || {})
			.filter(([, value]) => value !== undefined && value !== null && value !== '')
			.map(([key, value]) => `${encodeURIComponent(key)}=${encodeURIComponent(value)}`)
			.join('&')

		const endpoint = `${baseEndpoint}${queryString ? `?${queryString}` : ''}`

		const response = await $fetch<ApiResponseWrapper<Veiculo[]>>(endpoint, {
			method: 'GET',
			headers: {
				'Content-Type': 'application/json',
			},
			ignoreResponseError: true,
		})

		veiculos.value = response.data || []
		console.log('Response from getVeiculos:', response)

		return response
	}

	// Busca por ID
	async function getVeiculoPorId(id: number): Promise<ApiResponseWrapper<Veiculo>> {
		const endpoint = `${baseEndpoint}/${id}`

		const response = await $fetch<ApiResponseWrapper<Veiculo>>(endpoint, {
			method: 'GET',
			headers: {
				'Content-Type': 'application/json',
			},
			ignoreResponseError: true,
		})

		veiculos.value = response.data ? [response.data] : []

		return response
	}

	async function addVeiculo(veiculo: z.output<typeof schemaAddVeiculo>): Promise<ApiResponseWrapper<Veiculo | null>> {

		const body = createAddBody(veiculo)

		const response = await $fetch<ApiResponseWrapper<Veiculo | null>>(baseEndpoint, {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json',
			},
			ignoreResponseError: true,
			body: body,
		})

		if (response.data) {
			veiculos.value.push(response.data)
		}

		return response

	}

	async function deleteVeiculo(id: number): Promise<ApiResponseWrapper<null>> {

		const response = await $fetch<ApiResponseWrapper<null>>(`${baseEndpoint}/${id}`, {
			method: 'DELETE',
			headers: {
				'Content-Type': 'application/json',
			},
			ignoreResponseError: true,
		})

		return response

	}

	async function updateVeiculo(id: number, veiculo: z.output<typeof schemaUpdateVeiculo>): Promise<ApiResponseWrapper<Veiculo | null>> {
		const body = createUpdateBody(veiculo)
		const response = await $fetch<ApiResponseWrapper<Veiculo | null>>(`${baseEndpoint}/${id}`, {
			method: 'PUT',
			headers: {
				'Content-Type': 'application/json',
			},
			ignoreResponseError: true,
			body: body,
		})
		console.log('Response from updateVeiculo:', response)
		console.log('Updated veiculo:', veiculo)
		console.log('Request body:', body)
		return response

	}

	function createAddBody(veiculo: z.output<typeof schemaAddVeiculo>) {
		return {
			modelo: veiculo.modelo,
			fabricante: veiculo.fabricante,
			ano: veiculo.ano,
			preco: Number(veiculo.preco.replace(/[^\d,-]/g, '').replace(',', '.')),
			cor: veiculo.cor,
			tipo: veiculo.tipo.toUpperCase(),
			cilindrada: veiculo.cilindrada ? Number(veiculo.cilindrada) : undefined,
			quantidadePortas: veiculo.quantidadePortas ? Number(veiculo.quantidadePortas) : undefined,
			tipoCombustivel: veiculo.tipoCombustivel ? veiculo.tipoCombustivel.toUpperCase() : undefined,
		}
	}

	function createUpdateBody(veiculo: z.output<typeof schemaUpdateVeiculo>) {
		const body = {
			id: veiculo.id,
			modelo: veiculo.modelo,
			fabricante: veiculo.fabricante,
			ano: veiculo.ano,
			preco: Number(veiculo.preco),
			cor: veiculo.cor,
			tipo: veiculo.tipo.toUpperCase(),
			cilindrada: veiculo.cilindrada ? Number(veiculo.cilindrada) : undefined,
			quantidadePortas: veiculo.quantidadePortas ? Number(veiculo.quantidadePortas) : undefined,
			tipoCombustivel: veiculo.tipoCombustivel ? veiculo.tipoCombustivel.toUpperCase() : undefined,
		}
		if (veiculo.tipo === 'Moto') {
			delete body.quantidadePortas
			delete body.tipoCombustivel
		}

		if (veiculo.tipo === 'Carro') {
			delete body.cilindrada
		}

		return body
	}

	return {
		veiculos,
		status,
		error,
		getVeiculos,
		getVeiculoPorId,
		refreshVeiculos,
		addVeiculo,
		deleteVeiculo,
		updateVeiculo,
	}
}
