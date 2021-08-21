package service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dao.VocabuloDao;
import models.Vocabulo;

public class VocabuloService {
	
	private String palavrasNegativas = "o,os,a,as,um,uns,uma,umas" + 
			"ao,aos,à,às" + 
			"do,dos,da,das,dum,duns,duma,dumas" + 
			"no,nos,na,nas,num,nuns,numa,numas" + 
			"pelo,pelos,pela,pelas" +
			"eu,tu,ele,ela,nós,vós,eles,elas" + 
			"me,te,se,o,a,lhe,nos,vos,se,os,as,lhes" + 
			"mo,mos,ma,mas,to,tos,ta,tas,lho,lhos,lha," +
			"lhas,lo,los,la,las" + 
			"mim,comigo,ti,contigo,ele,ela,nós,conosco,eles,elas" +
			"me,mim,te,ti,se,si,consigo,nos,vos" +
			"vossa,alteza,eminência,reverendíssima,excelência,magnificência,majestade,santidade,senhoria,onipotência" +
			"senhor,senhora,você,vocês" +
			"pois,visto,porquanto,já,desde"+
			"meu,meus,minha,minhas,teu,teus,tua,tuas,nosso,nossos,nossa,nossas,vosso,vossos,vossa,vossas,seu,seus,sua,suas" + 
			"este,esta,esse,essa,aquele,aquela,deste,desta,aquilo,mesmo,mesmos,mesma,mesmas,próprio,próprios,própria,próprias,semelhante,semelhantes,tal,tais" +
			"algum,alguns,alguma,algumas,bastante,bastantes,muito,muitos,demais" +
			"mais,menos,nenhum,nenhuns,nenhuma,nenhumas,outro,outros,outra,outras"+
			"pouco,pouca,poucas,qualquer,quaisquer,qual,quanto,quantos"+
			"quanta,quantas,tanto,tantas,todo,todos,toda,todas"+
			"um,uns,uma,umas,vário,vários,várias,todo,ninguém"+
			"qualquer,quaisquer"+
			"qual,quais,cujo"+
			"quanto,quantos,quem"+
			"a,ante,após,até,com,contra,de,desde,em,entre,para,per,perante,por"+
			"sem,sob,sobre,trás"+
			"e,nem,não,só,mas,também,bem,como,ainda"+
			"porém,contudo,todavia,entretanto,entanto,obstante"+
			"ou,ora,quer,seja,talvez"+
			"que,mais,logo,pois,portanto,conseguinte,isso,assim,portanto"+
			"porque,pois,senãos"+
			"embora,ainda,apesar,mesmo,posto,conquanto"+
			"se,caso,contanto,menos,sem"+
			"conforme,segundo,consoante"+
			"quando,enquanto,antes,depois,logo"+
			"sempre,assim,agora,mal,cada";
	
	public void registrarVocabulo(String texto) {
		List<String> tokens = Arrays.asList(texto.split(" "));
		List<String> listaPalavrasNegativas = Arrays.asList(palavrasNegativas.split(","));
		List<String> listaDePalavras = new ArrayList<>();
		List<String> conjuntoDePalavras = new ArrayList<>();
		for (int i = 0; i < tokens.size(); i++) {
			
			String p1 = tokens.get(i).toLowerCase();
			int q1 = p1.length();
			int q2 = 0;
			
			for (int k = 0; k < q1; k++) {
				if (Character.isAlphabetic(p1.codePointAt(k))) {
					q2 += 1;
				}
			}
			
			if (q1 == q2) {
				if (
						!p1.startsWith(",") &&
						!p1.startsWith(";") &&
						!p1.startsWith(".") &&
						!p1.startsWith("!") &&
						!p1.startsWith("?") &&
						!p1.startsWith("\"") &&
						!p1.startsWith("'") &&
						!p1.endsWith(",") &&
						!p1.endsWith(";") &&
						!p1.endsWith(".") &&
						!p1.endsWith("!") &&
						!p1.endsWith("?") &&
						!p1.endsWith("\"") &&
						!p1.endsWith("'") &&
						!p1.endsWith("(") &&
						!p1.endsWith(")") &&
						!p1.endsWith("{") &&
						!p1.endsWith("}") &&
						!p1.endsWith(" (") &&
						!p1.endsWith(" )") &&
						!p1.endsWith(" {") &&
						!p1.endsWith(" }")
						) {
						if (!listaPalavrasNegativas.contains(tokens.get(i).toLowerCase())) {
							listaDePalavras.add(tokens.get(i).toLowerCase());
						}
					}
				}
			
			for (int j = 0; j < listaPalavrasNegativas.size(); j++) {
				String palavra = tokens.get(i).toLowerCase();
				
				int l1 = palavra.length();
				int l2 = 0;
				
				for (int k = 0; k < q1; k++) {
					if (Character.isAlphabetic(palavra.codePointAt(k))) {
						l2 += 1;
					}
				}
				
				if (l1 == l2) {
					if (
							!palavra.startsWith(",") &&
							!palavra.startsWith(";") &&
							!palavra.startsWith(".") &&
							!palavra.startsWith("!") &&
							!palavra.startsWith("?") &&
							!palavra.startsWith("\"") &&
							!palavra.startsWith("'") &&
							!palavra.endsWith(",") &&
							!palavra.endsWith(";") &&
							!palavra.endsWith(".") &&
							!palavra.endsWith("!") &&
							!palavra.endsWith("?") &&
							!palavra.endsWith("\"") &&
							!palavra.endsWith("'") &&
							!palavra.endsWith("(") &&
							!palavra.endsWith(")") &&
							!palavra.endsWith("{") &&
							!palavra.endsWith("}") &&
							!palavra.endsWith(" (") &&
							!palavra.endsWith(" )") &&
							!palavra.endsWith(" {") &&
							!palavra.endsWith(" }")
						) {
						if (!conjuntoDePalavras.contains(palavra) && !listaPalavrasNegativas.contains(palavra)) {
							conjuntoDePalavras.add(palavra);
						}
					}
				}
			}
		}
		
		listaDePalavras.forEach(palavra -> {
			int ocorrencia = new VocabuloDao().contar(palavra);
			if (ocorrencia == 0) {
				new VocabuloDao().criar(new Vocabulo(palavra, 1));
			} else {
				Vocabulo vocabulo = new VocabuloDao().encontrarVocabulario(palavra);
				vocabulo.setFrequencia(vocabulo.getFrequencia() + 1);
				new VocabuloDao().atualizar(vocabulo);
			}
		});
	}
	
	public List<String> obterListaPalavrasNegativas() {
		return Arrays.asList(palavrasNegativas.split(","));
	}
	
}
