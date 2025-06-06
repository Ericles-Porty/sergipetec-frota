import type { ApiResponseWrapper } from "~/types/ApiResponseWrapper"
import type Veiculo from "~/types/Veiculo"

type VeiculosResponse = {
	id: number
	modelo: string
	fabricante: string
	ano: number
	preco: number
	cor: string
	tipo: string
	cilindrada?: number
	quantidadePortas?: number
	tipoCombustivel?: string
}


export default defineEventHandler(async (event) => {
	const runtimeConfig = useRuntimeConfig(event)
	const query = getQuery(event)
	
	const queryString = Object.entries(query)
	.filter(([, value]) => value !== undefined && value !== null && value !== '')
	.map(([key, value]) => `${encodeURIComponent(key)}=${encodeURIComponent(value as string)}`)
	.join('&')
	
	const endpoint = `${runtimeConfig.public.apiBaseUrl}/api/veiculos${queryString ? `?${queryString}` : ''}`

	const response = await $fetch<ApiResponseWrapper<VeiculosResponse[]>>(endpoint)

	if (response.code !== 200 || !response.data) {
		return response as ApiResponseWrapper<Veiculo[]>
	}

	const veiculos = mapVeiculosResponseToVeiculos(response.data as VeiculosResponse[])

	return {
		success: response.success,
		code: response.code,
		data: veiculos,
		message: response.message,
		errors: response.errors
	} as ApiResponseWrapper<Veiculo[]>
})

function mapVeiculosResponseToVeiculos(responses: VeiculosResponse[]): Veiculo[] {
	return responses.map((veiculo) => {
		return {
			id: veiculo.id,
			modelo: veiculo.modelo,
			fabricante: veiculo.fabricante,
			ano: veiculo.ano,
			preco: veiculo.preco,
			cor: veiculo.cor,
			tipo: veiculo.tipo.charAt(0).toUpperCase() + veiculo.tipo.slice(1).toLowerCase(),
			cilindrada: veiculo.cilindrada,
			quantidadePortas: veiculo.quantidadePortas,
			tipoCombustivel: veiculo.tipoCombustivel ? veiculo.tipoCombustivel.charAt(0).toUpperCase() + veiculo.tipoCombustivel.slice(1).toLowerCase() : undefined
		}
	})
}