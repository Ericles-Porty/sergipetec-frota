package xyz.xpto.frota.domain.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.NoArgsConstructor;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "moto")
@PrimaryKeyJoinColumn(name = "veiculo_id")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Moto extends Veiculo {

	@Column(nullable = false)
	private Integer cilindrada;
}