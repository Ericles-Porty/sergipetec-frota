import { useFetch } from '#app'
import type { ApiResponseWrapper } from '~/types/ApiResponseWrapper'
import type Veiculo from '~/types/Veiculo'

export function useVeiculoApi() {
	const endpoint = '/api/veiculos'

	const { data, status, error, refresh } = useFetch<ApiResponseWrapper<Veiculo[]>>(endpoint, {
		method: 'GET',
		headers: {
			'Content-Type': 'application/json',
		},
	})

	const veiculos: Ref<Veiculo[]> = computed(() => data.value?.data ?? [])

	const refreshVeiculos = async () => {
		await refresh()
	}

	async function addVeiculo(nome: string): Promise<ApiResponseWrapper<null>> {
		{
			const response = await $fetch<ApiResponseWrapper<null>>(endpoint, {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json',
				},
				ignoreResponseError: true,
				body: { nome },
			})

			return response
		}
	}

	async function deleteVeiculo(id: number): Promise<ApiResponseWrapper<null>> {
		{
			const response = await $fetch<ApiResponseWrapper<null>>(`${endpoint}/${id}`, {
				method: 'DELETE',
				headers: {
					'Content-Type': 'application/json',
				},
				ignoreResponseError: true,
			})

			console.log('deleteVeiculo', response)

			return response
		}
	}

	async function updateVeiculo(id: number, nome: string): Promise<ApiResponseWrapper<null>> {

		const response = await $fetch<ApiResponseWrapper<null>>(`${endpoint}/${id}`, {
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
		refreshVeiculos,
		addVeiculo,
		deleteVeiculo,
		updateVeiculo,
	}
}
