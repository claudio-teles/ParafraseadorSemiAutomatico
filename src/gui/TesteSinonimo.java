package gui;

import java.util.ArrayList;
import java.util.List;

import models.Vocabulo;

public class TesteSinonimo {

	public static void main(String[] args) {
		List<Vocabulo> voc = new ArrayList<>();
		voc.add(new Vocabulo("palavra1", 0));
		voc.add(new Vocabulo("palavra2", 1));
		voc.add(new Vocabulo("palavra3", 8));
		voc.add(new Vocabulo("palavra4", 2));
		voc.add(new Vocabulo("palavra5", 0));
		
		String palavra = "";
		int frequencia = 0;
		
		for (int i = 0; i < voc.size(); i++) {
			if (voc.get(i).getFrequencia() > frequencia) {
				palavra = voc.get(i).getPalavra();
				frequencia = voc.get(i).getFrequencia();
			}
		}
		System.out.println("A palavra "+palavra+" tem a maior frequência que é igual a: "+frequencia);
	}

}
