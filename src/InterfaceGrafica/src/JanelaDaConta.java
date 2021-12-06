package InterfaceGrafica.src;

import javax.swing.*;

import ClassesUtilitarias.ValidacaoDeParametros;
import ExceptionsCustomizadas.CadastroException;
import ManipulacaoBancoDeDados.ControleDeCadastroDeClientes;
import ManipulacaoBancoDeDados.ControleDeVendas;
import RegrasDeNegocio.Cliente;
import RegrasDeNegocio.Venda;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Objects;

public class JanelaDaConta {
	private Venda novaVenda;

    JanelaDaConta(JButton botao, Venda novaVenda) {
		this.novaVenda = novaVenda;

        JFrame janelaDaConta = new JFrame("Conta");
        janelaDaConta.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        janelaDaConta.setBackground(Color.GRAY);
        janelaDaConta.setLayout(new BorderLayout());
        janelaDaConta.setSize( 600, 600 );
        janelaDaConta.setLocation(740,0);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        topPanel.setBackground(Color.DARK_GRAY);
        janelaDaConta.add(topPanel, BorderLayout.NORTH);

        JPanel loginPanel = new JPanel();
        loginPanel.setBackground(Color.GRAY);
        loginPanel.setLayout(new GridBagLayout());
        GridBagConstraints loginGBC = new GridBagConstraints();
        loginGBC.gridx = 0;
        loginGBC.gridy = 0;
        loginGBC.insets = new Insets(5,5,5,5);
        loginGBC.anchor = GridBagConstraints.FIRST_LINE_START;
        janelaDaConta.add(loginPanel, BorderLayout.CENTER);

        JPanel cadastroPanel = new JPanel();
        cadastroPanel.setLayout(new GridBagLayout());
        GridBagConstraints cadastroGBC = new GridBagConstraints();
        cadastroGBC.gridx = 0;
        cadastroGBC.gridy = 0;
        cadastroGBC.insets = new Insets(5,5,5,5);
        cadastroGBC.anchor = GridBagConstraints.FIRST_LINE_START;
        cadastroPanel.setBackground(Color.GRAY);

        JButton cadastroButton = new JButton("Clique aqui para cadastrar-se");
        cadastroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cadastroButton.getText() == "Clique aqui para cadastrar-se") {
                    janelaDaConta.remove(loginPanel);
                    janelaDaConta.add(cadastroPanel, BorderLayout.CENTER);
                    janelaDaConta.repaint();
                    janelaDaConta.revalidate();
                    cadastroButton.setText("Voltar");
                } else {
                    janelaDaConta.remove(cadastroPanel);
                    janelaDaConta.add(loginPanel, BorderLayout.CENTER);
                    janelaDaConta.repaint();
                    janelaDaConta.revalidate();
                    cadastroButton.setText("Clique aqui para cadastrar-se");
                }
            }
        });
        topPanel.add(cadastroButton);

        JLabel userCPFLabel = new JLabel("CPF do usuário:");
        JTextField userCPFTextField = new JTextField("Digite o CPF do usuário");
        userCPFTextField.setColumns(15);
        userCPFTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (Objects.equals(userCPFTextField.getText(), "Digite o CPF do usuário"))
				userCPFTextField.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (Objects.equals(userCPFTextField.getText(), "")) {
                    userCPFTextField.setText("Digite o CPF do usuário");
                }
            }
        });

        loginPanel.add(userCPFLabel, loginGBC);
        loginGBC.gridx = 1;
        loginPanel.add(userCPFTextField, loginGBC);

        JButton loginButton = new JButton("Login");
        loginGBC.gridwidth = 2;
        loginGBC.gridx = 1;
        loginGBC.gridy = 1;
        loginGBC.anchor = GridBagConstraints.LINE_END;
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Fazer login
				try{
					novaVenda.setCliente(ControleDeCadastroDeClientes.procurarCliente(userCPFTextField.getText()));
                	botao.setText("Conta");
                	janelaDaConta.dispatchEvent(new WindowEvent(janelaDaConta, WindowEvent.WINDOW_CLOSING));
				}catch(IOException ioe) {
					ioe.printStackTrace();
				}catch(CadastroException ce) {
					System.out.println("Cliente excluido");
				}
				
            }
        });

		loginPanel.add(loginButton, loginGBC);

        JButton visitanteButton = new JButton("Login de Visitante");
        loginGBC.gridx = 0;
        loginGBC.anchor = GridBagConstraints.LINE_START;
        loginPanel.add(visitanteButton, loginGBC);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
				novaVenda.setCliente(new Cliente());
                janelaDaConta.dispatchEvent(new WindowEvent(janelaDaConta, WindowEvent.WINDOW_CLOSING));
            }
        });

        JLabel userNameLabel2 = new JLabel("Usuário:");
        JTextField userNameTextField2 = new JTextField("Digite o nome do usuário");
        userNameTextField2.setColumns(15);
        userNameTextField2.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (Objects.equals(userNameTextField2.getText(), "Digite o nome do usuário"))
                    userNameTextField2.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (Objects.equals(userNameTextField2.getText(), "")) {
                    userNameTextField2.setText("Digite o nome do usuário");
                }
            }
        });

        cadastroPanel.add(userNameLabel2, cadastroGBC);
        cadastroGBC.gridx = 1;
        cadastroPanel.add(userNameTextField2, cadastroGBC);

        JLabel cpfLabel = new JLabel("CPF:");
        cadastroGBC.gridx = 0;
        cadastroGBC.gridy = 1;
        cadastroPanel.add(cpfLabel, cadastroGBC);

        JTextField cpfField = new JTextField("Digite seu CPF", 15);
        cpfField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (Objects.equals(cpfField.getText(), "Digite seu CPF")) {
                    cpfField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (Objects.equals(cpfField.getText(), "")) {
                    cpfField.setText("Digite seu CPF");
                }
            }
        });
        cadastroGBC.gridx = 1;
        cadastroPanel.add(cpfField, cadastroGBC);

        JLabel cepLabel = new JLabel("CEP:");
        cadastroGBC.gridx = 0;
        cadastroGBC.gridy = 2;
        cadastroPanel.add(cepLabel, cadastroGBC);

        JTextField cepField = new JTextField("Digite seu CEP", 15);
        cepField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (Objects.equals(cepField.getText(), "Digite seu CEP")) {
                    cepField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (Objects.equals(cepField.getText(), "")) {
                    cepField.setText("Digite seu CEP");
                }

				//Validar CEP aqui
            }
        });
        cadastroGBC.gridx = 1;
        cadastroPanel.add(cepField, cadastroGBC);

        JButton cadastrarButton = new JButton("Cadastrar");
        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // cadastrar
				try{
					if(ValidacaoDeParametros.valida(cpfField.getText()) && userNameTextField2.getText().equals("")){
						ControleDeCadastroDeClientes.cadastrarCliente(new Cliente(userNameTextField2.getText(), cpfField.getText(), cepField.getText()));
						janelaDaConta.remove(cadastroPanel);
						janelaDaConta.add(loginPanel, BorderLayout.CENTER);
						janelaDaConta.repaint();
						janelaDaConta.revalidate();
						cadastroButton.setText("Clique aqui para cadastrar-se");
					}
					//Abrir janela de mensagem
						
				}catch(IOException ioe) {
					ioe.printStackTrace();
				}
				

            }
        });
        cadastroGBC.gridx = 0;
        cadastroGBC.gridwidth = 2;
        cadastroGBC.anchor = GridBagConstraints.CENTER;
        cadastroGBC.gridy = 3;
        cadastroPanel.add(cadastrarButton, cadastroGBC);


        janelaDaConta.setVisible(true);

        if (Objects.equals(botao.getText(), "Conta")) {
            janelaDaConta.dispatchEvent(new WindowEvent(janelaDaConta, WindowEvent.WINDOW_CLOSING));

            JFrame janelaDaConta2 = new JFrame("Conta2");
            janelaDaConta2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            janelaDaConta2.setBackground(Color.GRAY);
            janelaDaConta2.setLayout(new BorderLayout());
            janelaDaConta2.setSize( 600, 600 );
            janelaDaConta2.setLocation(740,0);


            JPanel contaPanel = new JPanel();
            contaPanel.setLayout(new BorderLayout());
            contaPanel.setBackground(Color.GRAY);
            janelaDaConta2.add(contaPanel, BorderLayout.CENTER);

            JPanel superiorContaPanel = new JPanel();
            superiorContaPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
            superiorContaPanel.setBackground(Color.DARK_GRAY);
            janelaDaConta2.add(superiorContaPanel, BorderLayout.NORTH);

            JTextArea historicoTextArea = new JTextArea();
            JScrollPane scrollPane = new JScrollPane(historicoTextArea);
            scrollPane.setPreferredSize(new Dimension(600, 200));
            scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
            contaPanel.add(scrollPane, BorderLayout.CENTER);

            JButton historicoCompraButton = new JButton("Histórico de Compras");
            historicoCompraButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //exibir histórico de compra
					try{
						historicoTextArea.setText(ControleDeVendas.listarVendasPorCliente(novaVenda.getCliente().getCPF()));
					}catch(IOException ioe) {
						ioe.printStackTrace();
					}
					
                }
            });
            superiorContaPanel.add(historicoCompraButton);

            JButton logoutButton = new JButton("Logout");
            logoutButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // logout na conta
					novaVenda.setCliente(new Cliente());
                    botao.setText("Login");
                    janelaDaConta2.dispatchEvent(new WindowEvent(janelaDaConta, WindowEvent.WINDOW_CLOSING));
                }
            });
            superiorContaPanel.add(logoutButton);


            janelaDaConta2.setVisible(true);
        }


    }

    public static void main(String[] args) {
        new InterfaceGrafica();
    }
}
