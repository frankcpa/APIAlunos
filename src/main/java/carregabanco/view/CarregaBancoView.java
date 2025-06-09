package carregabanco.view;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import carregabanco.controller.CarregaBancoController;
import carregabanco.model.AlunoModel;

public class CarregaBancoView {
	public CarregaBancoView() {
		CarregaBancoController carregaBancoController = new CarregaBancoController();
		JFileChooser fileChooser = new JFileChooser();

		int opcao = 0;
		// Verifica se o usuário selecionou um arquivo
		do {
			try {
				// Exibe a janela de seleção de arquivo
				opcao = Integer.parseInt(JOptionPane.showInputDialog("1 - Escolher arquivo\n2 - Sair"));
			} catch (NumberFormatException e) {
				opcao = 3;
			}
			//opcao = Integer.parseInt(JOptionPane.showInputDialog("1 - Escolher arquivo\n2 - Sair"));
			switch (opcao) {
				case 1:
					// Define o título da janela
					fileChooser.setDialogTitle("Selecione um arquivo");

					// Exibe a janela e captura a resposta do usuário
					int userSelection = fileChooser.showOpenDialog(null);
					if (userSelection == JFileChooser.APPROVE_OPTION) {
						File selectedFile = fileChooser.getSelectedFile();
						System.out.println("Arquivo selecionado: " + selectedFile.getAbsolutePath());
						ArrayList<AlunoModel> valores = carregaBancoController.loader(selectedFile.getAbsolutePath());
						if (!valores.isEmpty()) {
							carregaBancoController.inserirNoBanco(valores);
						}
					} else {
						JOptionPane.showMessageDialog(null, "Nenhum arquivo foi selecionado.");
					}
					break;
				case 2:
					JOptionPane.showMessageDialog(null, "Saindo da aplicação.");
					break;
				default:
					JOptionPane.showMessageDialog(null, "Opção inválida. Tente novamente.");
					break;
			}

		} while (opcao != 2);

		// A parte da visualização
		// System.out.println(valores.size());
		// for(AlunoModel n: valores)
		// System.out.println(n.getCampus() + " - " + n.getNome_estudante()+ " - " +
		// n.getIdade());

	}
}