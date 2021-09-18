package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;

import dao.VocabuloDao;
import models.Vocabulo;

public class GUI extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8375595066259798834L;
	private JPanel contentPane;
	private JButton btnLimpar;
	private JButton btnParafrasear;
	private JEditorPane editorPane;
	private String pn = "o,os,a,as,um,uns,uma,umas," + 
			"ao,aos,à,às," + 
			"do,dos,da,das,dum,duns,duma,dumas," + 
			"no,nos,na,nas,num,nuns,numa,numas," + 
			"pelo,pelos,pela,pelas," +
			"eu,tu,ele,nós,vós,eles" +
			"me,te,se,lhe,nos,vos,se,lhes,mim,ti,ela,si,elas," +
			"você,vocês,comigo,contigo,conosco,convosco,consigo" +
			"meu,minha,meus,minhas,teu,tua,teus,tuas,seu,sua,seus,suas,nosso,nossa,nossos,nossas,vosso,vossa,vossos,vossas," +
			"este,esta,aquela,estas,essas,aquelas,este,esse,aquele,estes,esses,aqueles,isto,isso,aquilo" +
			"senhor,senhora,vossa,excelência,magnificência,majestade,alteza,santidade,eminência,reverendíssima" +
			"algum,alguma,alguns,algumas,nenhum,nenhuma,nenhuns,nenhumas,muito,muita,muitos,muitas,pouco,pouca,poucos,poucas,todo,toda,todos,todas," +
			"outro,outra,outros,outras,certo,certa,certos,certas,vário,vária,vários,várias,tanto,tanta,tantos,tantas,quanto,quanta,quantos,quantas," + 
			"qualquer,quaisquer,qual,quais," +
			"qual,quais,cujo,cuja,cujos,cujas,quem,que,onde" +
			"ante,após,até,com,contra,de,desde,em,entre,para,per,perante,por,sem,sob,sobre,trás," +
			"e,mas,ainda,também,nem," +
			"contudo,entretanto,não,obstante,entanto,porém,todavia," +
			"já,ou,ora,quer" +
			"assim,então,logo,pois,por,conseguinte,portanto,porquanto,porque" +
			"como,embora,conquanto,mesmo,apesar,caso,quando,sem," +
			"conforme,como";
	
	private List<String> listaDeLinhas;
	
	private String texto;
	private JTextField txtPalavrasNegativas;
	
	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public String getPn() {
		return pn;
	}
	
	public void setPn(String pn) {
		this.pn = pn;
	}

	public List<String> listarPalavrasNegativas() {
		return Arrays.asList(pn.split(","));
	}
	
	public List<String> listarLinhasDicionario(String path) throws IOException {
		return Files.readAllLines(Paths.get(path), Charset.forName("UTF-8"));
	}
	
	/**
	 * @param palavra
	 * @return
	 */
	public List<String> gerarListaSinonimos(String palavra) {
		String consulta = "(Sinônimo)"+palavra+"|"+palavra+"|";
		String linha = "";
		
		for (int i = 0; i < listaDeLinhas.size(); i++) {
			if (listaDeLinhas.get(i).contains(consulta)) {
				linha = listaDeLinhas.get(i);
				break;
			}
		}
		
		int inicioDaSelecao = linha.indexOf("|", ("(Sinônimo)"+palavra+"|").length() + palavra.length()) + 1;
		int fimDaSelecao = linha.length();
		String trechoSeleciondado = linha.substring(inicioDaSelecao, fimDaSelecao).replace("|", ",");
		
		return Arrays.asList(trechoSeleciondado.split(","));
	}
	
	/**
	 * @param quantidadeCaracteresPalavra
	 * @param todosCaractersAlfabeticos
	 * @param palavra
	 * @return
	 */
	public boolean saoTodosAlfabeticos(int quantidadeCaracteresPalavra, boolean todosCaractersAlfabeticos, String palavra) {
		for (int i = 0; i < palavra.length(); i++) {
			if (Character.isAlphabetic(palavra.charAt(i))) {// Analiza se todos os caracteres de uma palavra são alfabéticos.
				quantidadeCaracteresPalavra += 1;
			}
			if (quantidadeCaracteresPalavra == palavra.length()) {
				todosCaractersAlfabeticos = true;
				break;
			}
		}
		return todosCaractersAlfabeticos;
	}
	
	class PopUpDemo extends JPopupMenu {
	    /**
		 * 
		 */
		private static final long serialVersionUID = -637225326582313431L;
		JMenuItem anItem;
		
		public PopUpDemo() {}
		
	    public PopUpDemo(List<String> sinonimos) {
	    	sinonimos.forEach(sinonimo -> {
	    		add(new JMenuItem(new AbstractAction(sinonimo) {
					
					/**
					 * 
					 */
					private static final long serialVersionUID = 3516032981405453707L;

					@Override
					public void actionPerformed(ActionEvent e) {
						editorPane.replaceSelection(sinonimo);
					}
				}));
	    	});
	    }
	}
	
	class PopClickListener extends MouseAdapter {
	    public void mousePressed(MouseEvent e) {
	        if (e.isPopupTrigger())
	            doPop(e);
	    }

	    public void mouseReleased(MouseEvent e) {
	        if (e.isPopupTrigger())
	            doPop(e);
	    }

	    private void doPop(MouseEvent e) {
			int quantidadeCaracteresPalavra = 0;
			boolean todosCaractersAlfabeticos = false;
			String palavraSelecionada = editorPane.getSelectedText();
			todosCaractersAlfabeticos = saoTodosAlfabeticos(quantidadeCaracteresPalavra, todosCaractersAlfabeticos, palavraSelecionada);
			
			if (todosCaractersAlfabeticos) {
				try {
					PopUpDemo menu = new PopUpDemo(gerarListaSinonimos(palavraSelecionada));
					menu.show(e.getComponent(), e.getX(), e.getY());
				} catch (java.lang.NullPointerException npe) {
					
				}
			}
	    }
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI() {
		listaDeLinhas = new ArrayList<>();
		try {
			listaDeLinhas.addAll(listarLinhasDicionario("src/gui/th_pt_BR.txt"));
			System.out.println("Carregando a lista de linhas...");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		System.out.println("Lista de linhas carregada!");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 790, 495);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		btnLimpar = new JButton("Limpar");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editorPane.setText("");
			}
		});
		contentPane.add(btnLimpar, BorderLayout.NORTH);
		
		btnParafrasear = new JButton("Parafrasear");
		btnParafrasear.addActionListener(new ActionListener() {
			@SuppressWarnings("unlikely-arg-type")
			public void actionPerformed(ActionEvent e) {
				setTexto(editorPane.getText().toLowerCase());
				String palNeg = txtPalavrasNegativas.getText().replaceAll(", ", ",");
				setPn(getPn() + "," + palNeg);
				List<String> listaDePalavras = Arrays.asList(getTexto().split(" "));
				
				for (int a = 0; a < listaDePalavras.size(); a++) {
					int quantidadeCaracteresPalavra = 0;
					boolean todosCaractersAlfabeticos = false;
					String palavra = listaDePalavras.get(a);
					
					todosCaractersAlfabeticos = saoTodosAlfabeticos(quantidadeCaracteresPalavra, todosCaractersAlfabeticos, palavra);
					if (todosCaractersAlfabeticos) {
						if (!listarPalavrasNegativas().contains(palavra)) {// Procurar no dicionário a palavra, se tiver, encontrar a lista de palavras sinônimas
							listarPalavrasNegativas().forEach(System.out::println);
							
							List<String> listaDeSinonimos = gerarListaSinonimos(palavra);
							
							List<Vocabulo> subListaVocabulos = new ArrayList<>();
							for (int b = 0; b < listaDeSinonimos.size(); b++) {
								VocabuloDao vd = new VocabuloDao();
								String sinonimo = listaDeSinonimos.get(b);
								boolean temSinonimo = vd.contar(sinonimo) == 1 ? true : false;
								
								List<Vocabulo> vocabulos = new ArrayList<>();
								if (temSinonimo) {
									vocabulos.add(new Vocabulo(sinonimo, vd.encontrarVocabulario(sinonimo).getFrequencia()));
								} else {
									vocabulos.add(new Vocabulo(palavra, 0));
								}
								
								for (int i = 0; i < vocabulos.size(); i++) {// Adiciona todos os sinônimos com frequência maior que 0 a sublista de sinônimos
									if (vocabulos.get(i).getFrequencia() > 0 && !(vocabulos.get(i).equals(""))) {
										subListaVocabulos.add(vocabulos.get(i));
									}
								}
								
								if (subListaVocabulos.size() == 0 && !(palavra.equals(""))) {
									listaDePalavras.set(a, palavra);
								} else if (subListaVocabulos.size() > 0) {
									// Sorteio do sinônimo da sublista de sinônimo com as maiores frequências
									String pal = subListaVocabulos.get(new Random().nextInt(((subListaVocabulos.size() - 1) - 0) + 1) + 0).getPalavra();
									if (!(pal.equals("")) ) {
										listaDePalavras.set(a, pal);
									}
								}
							}
						}
					}
					
					String p1 = "";
					
					for (int c = 0; c < listaDePalavras.size(); c++) {
						p1 += listaDePalavras.get(c)+" ";
					}
					
					editorPane.setText("");
					
					SimpleAttributeSet simpleAttributeSet = new SimpleAttributeSet();
					
					Document document = editorPane.getDocument();
					try {
						document.insertString(document.getLength(), p1, simpleAttributeSet);
					} catch (BadLocationException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		contentPane.add(btnParafrasear, BorderLayout.SOUTH);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		editorPane = new JEditorPane();
		editorPane.addMouseListener(new PopClickListener());
		scrollPane.setViewportView(editorPane);
		
		txtPalavrasNegativas = new JTextField();
		txtPalavrasNegativas.setBackground(SystemColor.controlHighlight);
		txtPalavrasNegativas.setToolTipText("Insira as palavras negativas separadas por virgula e um espa\u00E7o, ex: palavra1, palavra2, palavra3");
		scrollPane.setColumnHeaderView(txtPalavrasNegativas);
		txtPalavrasNegativas.setColumns(10);
	}

}
