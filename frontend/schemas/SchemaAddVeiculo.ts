import { z } from 'zod'

export const schemaAddVeiculo = z.object({
	modelo: z.string()
		.nonempty('Nome da veiculo é obrigatório')
		.min(3, 'Nome da veiculo deve ter no mínimo 3 caracteres')
		.max(100, 'Nome da veiculo deve ter no máximo 100 caracteres'),
	fabricante: z.string()
		.nonempty('Fabricante é obrigatório')
		.min(3, 'Fabricante deve ter no mínimo 3 caracteres')
		.max(100, 'Fabricante deve ter no máximo 100 caracteres'),
	ano: z.number()
		.int('Ano deve ser um número inteiro')
		.min(1000, 'Ano deve ser maior ou igual a 1000')
		.max(new Date().getFullYear(), 'Ano deve ser menor ou igual ao ano atual'),
	preco: z.number()
		.nonnegative('Preço não pode ser negativo')
		.min(0, 'Preço deve ser maior ou igual a 0'),
	cor: z.string()
		.nonempty('Cor é obrigatória')
		.min(3, 'Cor deve ter no mínimo 3 caracteres')
		.max(50, 'Cor deve ter no máximo 50 caracteres'),
	tipo: z.enum(['Carro', 'Moto'], {
		required_error: 'Tipo é obrigatório',
		invalid_type_error: 'Tipo deve ser um dos seguintes: Carro, Moto',
		description: 'Tipo de veículo deve ser Carro ou Moto',
		message: 'Tipo deve ser Carro ou Moto'
	}),
	cilindrada: z.number()
		.int('Cilindrada deve ser um número inteiro')
		.min(150, 'Cilindrada deve ser maior ou igual a 150')
		.optional(),
	quantidadePortas: z.number()
		.int('Quantidade de portas deve ser um número inteiro')
		.min(1, 'Quantidade de portas deve ser maior ou igual a 1')
		.optional(),
	tipoCombustivel: z.enum(['Gasolina', 'Etanol', 'Diesel', 'Flex'], {
		required_error: 'Tipo de combustível é obrigatório',
		invalid_type_error: 'Tipo de combustível deve ser um dos seguintes: gasolina, etanol, diesel, flex',
		description: 'Tipo de combustível deve ser gasolina, etanol, diesel ou flex',
		message: 'Tipo de combustível deve ser gasolina, etanol, diesel ou flex'
	}).optional(),

}).superRefine((data, ctx) => {
	if (data.tipo === 'Moto') {
		if (data.cilindrada === undefined) {
			ctx.addIssue({
				path: ['cilindrada'],
				code: z.ZodIssueCode.custom,
				message: 'Cilindrada é obrigatória para Motos',
			});
		}

		if (data.quantidadePortas !== undefined) {
			ctx.addIssue({
				path: ['quantidadePortas'],
				code: z.ZodIssueCode.custom,
				message: 'Quantidade de portas não se aplica a motos',
			});
		}

		if (data.tipoCombustivel !== undefined) {
			ctx.addIssue({
				path: ['tipoCombustivel'],
				code: z.ZodIssueCode.custom,
				message: 'Tipo de combustível não se aplica a motos',
			});
		}
	}

	if (data.tipo === 'Carro') {
		if (data.quantidadePortas === undefined) {
			ctx.addIssue({
				path: ['quantidadePortas'],
				code: z.ZodIssueCode.custom,
				message: 'Quantidade de portas é obrigatória para carros',
			});
		}

		if (data.tipoCombustivel === undefined) {
			ctx.addIssue({
				path: ['tipoCombustivel'],
				code: z.ZodIssueCode.custom,
				message: 'Tipo de combustível é obrigatório para carros',
			});
		}

		if (data.cilindrada !== undefined) {
			ctx.addIssue({
				path: ['cilindrada'],
				code: z.ZodIssueCode.custom,
				message: 'Cilindrada não se aplica a carros',
			});
		}
	}
});

