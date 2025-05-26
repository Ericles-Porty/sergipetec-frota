import { useFetch } from '#app'
import type { schemaAddVeiculo } from '~/schemas/SchemaAddVeiculo'
import type { ApiResponseWrapper } from '~/types/ApiResponseWrapper'
import type Veiculo from '~/types/Veiculo'

import type { z } from 'zod'

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

	async function addVeiculo(
		veiculo: z.output<typeof schemaAddVeiculo>

	): Promise<ApiResponseWrapper<null>> {
		{
			const response = await $fetch<ApiResponseWrapper<null>>(baseEndpoint, {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json',
				},
				ignoreResponseError: true,
				body: {
					...veiculo,
				},
			})

			return response
		}
	}

	async function deleteVeiculo(id: number): Promise<ApiResponseWrapper<null>> {
		{
			const response = await $fetch<ApiResponseWrapper<null>>(`${baseEndpoint}/${id}`, {
				method: 'DELETE',
				headers: {
					'Content-Type': 'application/json',
				},
				ignoreResponseError: true,
			})

			return response
		}
	}

	async function updateVeiculo(id: number, nome: string): Promise<ApiResponseWrapper<null>> {

		const response = await $fetch<ApiResponseWrapper<null>>(`${baseEndpoint}/${id}`, {
			method: 'PUT',
			headers: {
				'Content-Type': 'application/json',
			},
			ignoreResponseError: true,
			body: {
				id,
				nome,
			},
		})

		return response

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
