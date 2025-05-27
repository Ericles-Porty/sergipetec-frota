
type VeiculoParams = {
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

export default class Veiculo {
	public id: number;
	public modelo: string;
	public fabricante: string;
	public ano: number;
	public preco: number;
	public cor: string;
	public tipo: string;
	public cilindrada?: number;
	public quantidadePortas?: number;
	public tipoCombustivel?: string;

	constructor({ id, modelo, fabricante, ano, preco, cor, tipo, cilindrada, quantidadePortas, tipoCombustivel }: VeiculoParams
	) {
		this.id = id
		this.modelo = modelo
		this.fabricante = fabricante
		this.ano = ano
		this.preco = preco
		this.cor = cor
		this.tipo = tipo
		this.cilindrada = cilindrada
		this.quantidadePortas = quantidadePortas
		this.tipoCombustivel = tipoCombustivel
	}
}