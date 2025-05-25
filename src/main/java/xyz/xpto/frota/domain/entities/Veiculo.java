package xyz.xpto.frota.domain.entities;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import xyz.xpto.frota.domain.dtos.VeiculoDTO;

@Entity
@SqlResultSetMapping(
    name = "VeiculoDTOResult",
    classes = @ConstructorResult(
        targetClass = VeiculoDTO.class,
        columns = {
            @ColumnResult(name = "id", type = Long.class),
            @ColumnResult(name = "modelo", type = String.class),
            @ColumnResult(name = "fabricante", type = String.class),
            @ColumnResult(name = "ano", type = Integer.class),
            @ColumnResult(name = "preco", type = BigDecimal.class),
            @ColumnResult(name = "tipo", type = String.class)
        }
    )
)
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "veiculo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Veiculo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;

	@Column(nullable = false)
	protected String modelo;

	@Column(nullable = false)
	protected String fabricante;

	@Column(nullable = false)
	protected Integer ano;

	@Column(nullable = false)
	protected BigDecimal preco;

	@Column(nullable = false)
	protected String tipo;

}
