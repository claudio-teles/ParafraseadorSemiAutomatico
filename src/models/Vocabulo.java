package models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Vocabulo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8545714262588933127L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(length = 20)
	private String palavra;
	private int frequencia;
	
	public Vocabulo(String palavra, int frequencia) {
		super();
		this.palavra = palavra;
		this.frequencia = frequencia;
	}

}
