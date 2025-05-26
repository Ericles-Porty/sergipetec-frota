package xyz.xpto.frota.domain.entities;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.NoArgsConstructor;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "carro")
@PrimaryKeyJoinColumn(name = "veiculo_id")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
public class Carro extends Veiculo {

    @Column(nullable = false)
    private Integer quantidadePortas;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoCombustivel tipoCombustivel;

    public enum TipoCombustivel {
        GASOLINA, ETANOL, DIESEL, FLEX
    }
}