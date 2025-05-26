import type { ApiResponseWrapper } from "~/types/ApiResponseWrapper"
import type Veiculo from "~/types/Veiculo"

export default defineEventHandler(async (event) => {
	const body = await readBody(event)
	const runtimeConfig = useRuntimeConfig(event)
	const id = getRouterParam(event, 'id')
	const endpoint = runtimeConfig.public.apiBaseUrl + '/api/veiculos/' + id

	const response = await $fetch<ApiResponseWrapper<Veiculo | null>>(endpoint, {
		method: 'PUT',
		body: body,
		headers: {
			'Content-Type': 'application/json'
		},
		ignoreResponseError: true
	})

	if (response.success === false) {
		return response as ApiResponseWrapper<null>
	}

	return {
		success: response.success,
		code: response.code,
		data: response.data,
		message: response.message,
		errors: response.errors
	} as ApiResponseWrapper<Veiculo>
})
