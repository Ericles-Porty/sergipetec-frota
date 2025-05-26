import type { ApiResponseWrapper } from "~/types/ApiResponseWrapper";

export default defineEventHandler(async (event) => {
	const id = event.context.params?.id as string;

	if (!id) {
		throw createError({
			statusCode: 400,
			message: 'ID is required',
		})
	}

	const runtimeConfig = useRuntimeConfig(event);
	const endpoint = runtimeConfig.public.apiBaseUrl + '/api/veiculos/' + id;
	const response = await $fetch<ApiResponseWrapper<null>>(endpoint, {
		method: 'DELETE',
		headers: {
			'Content-Type': 'application/json',
		},
		ignoreResponseError: true,
	});

	console.log('deleteVeiculo response', response)
	if (response.success === false) {
		return {
			success: false,
			code: response.code,
			data: null,
			message: "Error",
			errors: ["Error"]
		} as ApiResponseWrapper<null>
	}

	if (response.code === 401) {
		return {
			success: false,
			code: 401,
			data: null,
			message: 'Unauthorized',
			errors: [],
		} as ApiResponseWrapper<null>
	}

	if (response.code === 403) {
		return {
			success: false,
			code: 403,
			data: null,
			message: 'Forbidden',
			errors: [],
		} as ApiResponseWrapper<null>
	}
	if (response.code === 404) {
		return {
			success: false,
			code: 404,
			data: null,
			message: 'Not Found',
			errors: [],
		} as ApiResponseWrapper<null>
	}
	if (response.code === 422) {
		return {
			success: false,
			code: 422,
			data: null,
			message: 'Unprocessable Entity',
			errors: response.errors,
		} as ApiResponseWrapper<null>
	}
	if (response.code === 500) {
		return {
			success: false,
			code: 500,
			data: null,
			message: 'Internal Server Error',
			errors: [],
		} as ApiResponseWrapper<null>
	}

	if (response.code !== 200) {
		return {
			success: false,
			code: response.code,
			data: null,
			message: response.message,
			errors: response.errors,
		} as ApiResponseWrapper<null>
	}

	return {
		success: response.success,
		code: response.code,
		data: null,
		message: response.message,
		errors: response.errors,
	} as ApiResponseWrapper<null>
})