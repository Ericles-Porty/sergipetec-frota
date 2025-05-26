import type { ApiResponseWrapper } from "~/types/ApiResponseWrapper"
import type Veiculo from "~/types/Veiculo"

type VeiculoResponse = {
	id: number
	modelo: string
	fabricante: string
	ano: number
	preco: number
	cor: string
	tipo: string
}

export default defineEventHandler(async (event) => {
	const runtimeConfig = useRuntimeConfig(event)
	const id = getRouterParam(event, 'id')

	if (!id) {
		throw createError({ statusCode: 400, message: 'ID é obrigatório' })
	}

	const endpoint = `${runtimeConfig.public.apiBaseUrl}/api/veiculos/${id}`

	const response = await $fetch<ApiResponseWrapper<VeiculoResponse>>(endpoint)

	if (response.code !== 200 || !response.data) {
		return response as ApiResponseWrapper<Veiculo>
	}

	const v = response.data

	const veiculo: Veiculo = {
		id: v.id,
		modelo: v.modelo,
		fabricante: v.fabricante,
		ano: v.ano,
		preco: v.preco,
		cor: v.cor,
		tipo: v.tipo.charAt(0).toUpperCase() + v.tipo.slice(1).toLowerCase()
	}

	return {
		success: response.success,
		code: response.code,
		data: veiculo,
		message: response.message,
		errors: response.errors
	}
})
