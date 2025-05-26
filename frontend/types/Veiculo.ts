
type VeiculoParams = {
	id: number
	modelo: string
	fabricante: string
	ano: number
	preco: number
	cor: string
	tipo: string
}

export default class Veiculo {
	public id: number;
	public modelo: string;
	public fabricante: string;
	public ano: number;
	public preco: number;
	public cor: string;
	public tipo: string;

	constructor({ id, modelo, fabricante, ano, preco, cor, tipo }: VeiculoParams
	) {
		this.id = id
		this.modelo = modelo
		this.fabricante = fabricante
		this.ano = ano
		this.preco = preco
		this.cor = cor
		this.tipo = tipo
	}
}