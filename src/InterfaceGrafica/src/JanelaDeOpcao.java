package InterfaceGrafica.src;

import javax.swing.*;

import ManipulacaoBancoDeDados.ControleDeEstoque;
import RegrasDeNegocio.Produtos.EnumCategoriaDeProdutos;
import RegrasDeNegocio.Produtos.*;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Objects;
import java.util.Set;

public class JanelaDeOpcao {

    public JanelaDeOpcao() {

        JFrame janelaDeOpcao = new JFrame("Opção");
        janelaDeOpcao.setBounds(0, 100, 900, 600);
        janelaDeOpcao.setLayout(new BorderLayout());

        JPanel painelDeVisualizacao = new JPanel();
        //painelDeVisualizacao.setBackground(Color.GRAY);
        painelDeVisualizacao.setLayout(new BorderLayout());
        painelDeVisualizacao.setPreferredSize(new Dimension(600, 495));
        janelaDeOpcao.add(painelDeVisualizacao, BorderLayout.SOUTH);

        JPanel painelProduto = new JPanel();
        painelProduto.setBackground(Color.DARK_GRAY);
        painelProduto.setPreferredSize(new Dimension(600, 35));
        painelProduto.setLayout(new FlowLayout(FlowLayout.CENTER));
        janelaDeOpcao.add(painelProduto, BorderLayout.NORTH);

        JButton botaoDeCadastrarProduto = new JButton("Cadastrar produto");
        painelProduto.add(botaoDeCadastrarProduto);
        botaoDeCadastrarProduto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                painelDeVisualizacao.removeAll();
                painelDeVisualizacao.repaint();
                painelDeVisualizacao.revalidate();

                JPanel painelSuperiorCadastrarProduto = new JPanel();
                //painelSuperiorCadastrarProduto.setBackground(Color.BLUE);
                painelSuperiorCadastrarProduto.setLayout(new FlowLayout(FlowLayout.CENTER));
                painelDeVisualizacao.add(painelSuperiorCadastrarProduto, BorderLayout.NORTH);


                String[] listaTipoProduto = EnumCategoriaDeProdutos.getCategoriasDeProduto();
                JComboBox cadastrarProdutoBox = new JComboBox(listaTipoProduto);
                painelSuperiorCadastrarProduto.add(cadastrarProdutoBox);
                cadastrarProdutoBox.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        painelDeVisualizacao.removeAll();
                        JPanel painelInferiorCadastrarJogo = new JPanel();
                        painelInferiorCadastrarJogo.setLayout(new GridBagLayout());
                        GridBagConstraints InferiorCadastrarJogoGBC = new GridBagConstraints();
                        InferiorCadastrarJogoGBC.gridx = 0;
                        InferiorCadastrarJogoGBC.gridy = 0;
                        InferiorCadastrarJogoGBC.anchor = GridBagConstraints.LINE_START;
                        painelInferiorCadastrarJogo.setPreferredSize(new Dimension(600, 200));
                        InferiorCadastrarJogoGBC.weighty = 1;
                        InferiorCadastrarJogoGBC.insets = new Insets(5,5,5,5);

                        JPanel painelInferiorCadastrarConsole = new JPanel();
                        painelInferiorCadastrarConsole.setLayout(new GridBagLayout());
                        painelInferiorCadastrarConsole.setPreferredSize(new Dimension(600, 200));
                        GridBagConstraints InferiorCadastrarConsoleGBC = new GridBagConstraints();
                        InferiorCadastrarConsoleGBC.gridx = 0;
                        InferiorCadastrarConsoleGBC.gridy = 0;
                        InferiorCadastrarConsoleGBC.weighty = 1;
                        InferiorCadastrarConsoleGBC.anchor = GridBagConstraints.LINE_START;
                        InferiorCadastrarConsoleGBC.insets = new Insets(5,5,5,5);

                        JPanel painelInferiorCadastrarFone = new JPanel();
                        painelInferiorCadastrarFone.setLayout(new GridBagLayout());
                        painelInferiorCadastrarFone.setPreferredSize(new Dimension(600, 200));
                        GridBagConstraints InferiorCadastrarFoneGBC = new GridBagConstraints();
                        InferiorCadastrarFoneGBC.gridx = 0;
                        InferiorCadastrarFoneGBC.gridy = 0;
                        InferiorCadastrarFoneGBC.weighty = 1;
                        InferiorCadastrarFoneGBC.anchor = GridBagConstraints.LINE_START;
                        InferiorCadastrarFoneGBC.insets = new Insets(5,5,5,5);

                        JPanel painelInferiorCadastrarMouse = new JPanel();
                        painelInferiorCadastrarMouse.setLayout(new GridBagLayout());
                        painelInferiorCadastrarMouse.setPreferredSize(new Dimension(600, 200));
                        GridBagConstraints InferiorCadastrarMouseGBC = new GridBagConstraints();
                        InferiorCadastrarMouseGBC.gridx = 0;
                        InferiorCadastrarMouseGBC.gridy = 0;
                        InferiorCadastrarMouseGBC.weighty = 1;
                        InferiorCadastrarMouseGBC.anchor = GridBagConstraints.LINE_START;
                        InferiorCadastrarMouseGBC.insets = new Insets(5,5,5,5);

                        JPanel painelInferiorCadastrarTeclado = new JPanel();
                        painelInferiorCadastrarTeclado.setLayout(new GridBagLayout());
                        painelInferiorCadastrarTeclado.setPreferredSize(new Dimension(600, 200));
                        GridBagConstraints InferiorCadastrarTecladoGBC = new GridBagConstraints();
                        InferiorCadastrarTecladoGBC.gridx = 0;
                        InferiorCadastrarTecladoGBC.gridy = 0;
                        InferiorCadastrarTecladoGBC.weighty = 1;
                        InferiorCadastrarTecladoGBC.anchor = GridBagConstraints.LINE_START;
                        InferiorCadastrarTecladoGBC.insets = new Insets(5,5,5,5);

                        JPanel painelCentralCadastrarProduto = new JPanel();
                        painelCentralCadastrarProduto.setLayout(new GridBagLayout());
                        GridBagConstraints centralCadastrarProdutoGBC = new GridBagConstraints();
                        centralCadastrarProdutoGBC.gridx = 0;
                        centralCadastrarProdutoGBC.gridy = 0;
                        centralCadastrarProdutoGBC.anchor = GridBagConstraints.LINE_START;
                        centralCadastrarProdutoGBC.insets = new Insets(5,5,5,5);

                        JLabel nomeProdutoLabel = new JLabel("Nome:");
                        painelCentralCadastrarProduto.add(nomeProdutoLabel, centralCadastrarProdutoGBC);

                        JTextField nomeProdutoField = new JTextField("Digite o nome do produto", 20);
                        nomeProdutoField.addFocusListener(new FocusListener() {
                            @Override
                            public void focusGained(FocusEvent e) {
                                if (Objects.equals(nomeProdutoField.getText(), "Digite o nome do produto")) {
                                    nomeProdutoField.setText("");
                                }
                            }

                            @Override
                            public void focusLost(FocusEvent e) {
                                if (Objects.equals(nomeProdutoField.getText(), "")) {
                                    nomeProdutoField.setText("Digite o nome do produto");
                                }
                            }
                        });
                        centralCadastrarProdutoGBC.gridx += 1;
                        painelCentralCadastrarProduto.add(nomeProdutoField, centralCadastrarProdutoGBC);

                        JLabel estoqueProdutoLabel = new JLabel("Estoque:");
                        centralCadastrarProdutoGBC.gridx += 1;
                        painelCentralCadastrarProduto.add(estoqueProdutoLabel, centralCadastrarProdutoGBC);

                        JTextField estoqueProdutoField = new JTextField("Digite a quantidade em estoque", 20);
                        estoqueProdutoField.addFocusListener(new FocusListener() {
                            @Override
                            public void focusGained(FocusEvent e) {
                                if (Objects.equals(estoqueProdutoField.getText(), "Digite a quantidade em estoque")) {
                                    estoqueProdutoField.setText("");
                                }
                            }

                            @Override
                            public void focusLost(FocusEvent e) {
                                if (Objects.equals(estoqueProdutoField.getText(), "")) {
                                    estoqueProdutoField.setText("Digite a quantidade em estoque");
                                }
                            }
                        });
                        centralCadastrarProdutoGBC.gridx += 1;
                        painelCentralCadastrarProduto.add(estoqueProdutoField, centralCadastrarProdutoGBC);

                        JLabel descricaoLabel = new JLabel("Descrição:");
                        centralCadastrarProdutoGBC.gridx = 0;
                        centralCadastrarProdutoGBC.gridy += 1;
                        painelCentralCadastrarProduto.add(descricaoLabel, centralCadastrarProdutoGBC);

                        JTextField descricaoField = new JTextField("Digite a descrição do produto", 49);
                        descricaoField.addFocusListener(new FocusListener() {
                            @Override
                            public void focusGained(FocusEvent e) {
                                if (Objects.equals(descricaoField.getText(), "Digite a descrição do produto")) {
                                    descricaoField.setText("");
                                }
                            }
                            @Override
                            public void focusLost(FocusEvent e) {
                                if (Objects.equals(descricaoField.getText(), "")) {
                                    descricaoField.setText("Digite a descrição do produto");
                                }
                            }
                        });
                        centralCadastrarProdutoGBC.gridx = 1;
                        centralCadastrarProdutoGBC.gridwidth = 3;
                        painelCentralCadastrarProduto.add(descricaoField, centralCadastrarProdutoGBC);

                        JLabel precoLabel = new JLabel("Preço:");
                        centralCadastrarProdutoGBC.gridx = 0;
                        centralCadastrarProdutoGBC.gridwidth = 1;
                        centralCadastrarProdutoGBC.gridy += 1;
                        painelCentralCadastrarProduto.add(precoLabel, centralCadastrarProdutoGBC);

                        JTextField precoField = new JTextField("Digite o preço do produto", 15);
                        precoField.addFocusListener(new FocusListener() {
                            @Override
                            public void focusGained(FocusEvent e) {
                                if (Objects.equals(precoField.getText(), "Digite o preço do produto")) {
                                    precoField.setText("");
                                }
                            }

                            @Override
                            public void focusLost(FocusEvent e) {
                                if (Objects.equals(precoField.getText(), "")) {
                                    precoField.setText("Digite o preço do produto");
                                }
                            }
                        });
                        centralCadastrarProdutoGBC.gridx = 1;
                        centralCadastrarProdutoGBC.gridwidth = 2;
                        painelCentralCadastrarProduto.add(precoField, centralCadastrarProdutoGBC);

                        JLabel plataformaLabel = new JLabel("Plataformas");
                        centralCadastrarProdutoGBC.gridx = 0;
                        centralCadastrarProdutoGBC.gridwidth = 1;
                        centralCadastrarProdutoGBC.gridy += 1;
                        painelCentralCadastrarProduto.add(plataformaLabel, centralCadastrarProdutoGBC);

                        JTextField plataformaField = new JTextField("Digite as plataformas compatíveis", 28);
                        plataformaField.addFocusListener(new FocusListener() {
                            @Override
                            public void focusGained(FocusEvent e) {
                                if (Objects.equals(plataformaField.getText(), "Digite as plataformas compatíveis")) {
                                    plataformaField.setText("");
                                }
                            }

                            @Override
                            public void focusLost(FocusEvent e) {
                                if (Objects.equals(plataformaField.getText(), "")) {
                                    plataformaField.setText("Digite as plataformas compatíveis");
                                }
                            }
                        });
                        centralCadastrarProdutoGBC.gridx = 1;
                        centralCadastrarProdutoGBC. gridwidth = 2;
                        painelCentralCadastrarProduto.add(plataformaField, centralCadastrarProdutoGBC);



                        if (Objects.equals(Objects.requireNonNull(cadastrarProdutoBox.getSelectedItem()).toString(), "Jogo")) {
                            painelDeVisualizacao.add(painelSuperiorCadastrarProduto, BorderLayout.NORTH);
                            painelDeVisualizacao.add(painelCentralCadastrarProduto, BorderLayout.CENTER);
                            painelDeVisualizacao.add(painelInferiorCadastrarJogo, BorderLayout.SOUTH);
                            painelDeVisualizacao.repaint();
                            painelDeVisualizacao.revalidate();

                            JLabel generosJogoLabel = new JLabel("Gêneros:");
                            painelInferiorCadastrarJogo.add(generosJogoLabel, InferiorCadastrarJogoGBC);

                            JTextField generosJogoField = new JTextField("Digite os gêneros aqui", 25);
                            generosJogoField.addFocusListener(new FocusListener() {
                                @Override
                                public void focusGained(FocusEvent e) {
                                    if (Objects.equals(generosJogoField.getText(), "Digite os gêneros aqui")) {
                                        generosJogoField.setText("");
                                    }
                                }

                                @Override
                                public void focusLost(FocusEvent e) {
                                    if (Objects.equals(generosJogoField.getText(), "")) {
                                        generosJogoField.setText("Digite os gêneros aqui");
                                    }
                                }
                            });
                            InferiorCadastrarJogoGBC.gridx = 1;
                            InferiorCadastrarJogoGBC.gridwidth = 2;
                            painelInferiorCadastrarJogo.add(generosJogoField, InferiorCadastrarJogoGBC);

                            JButton cadastrarJogoButton = new JButton("Cadastrar Jogo");
                            cadastrarJogoButton.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    //Cadastrar
									try{
										String nome = nomeProdutoField.getText();
										float preco = Float.parseFloat(precoField.getText());
										int qntNoEstoque = Integer.parseInt(estoqueProdutoField.getText());
										String descricao = descricaoField.getText();
										Set<String> plataformas = Set.of(plataformaField.getText().split(","));
										Set<String> generos = Set.of(generosJogoField.getText().split(","));

										ControleDeEstoque.cadastrarProdutoNoEstoque(new Jogo(nome, preco, qntNoEstoque, descricao, plataformas, generos));
										painelDeVisualizacao.removeAll();
										painelDeVisualizacao.repaint();
										painelDeVisualizacao.revalidate();
									}catch(IOException ioe) {
										System.out.println("Falha na cominucação com o banco de dados");
									}
									
                                }
                            });
                            InferiorCadastrarJogoGBC.gridy += 1;
                            InferiorCadastrarJogoGBC.gridx = 1;
                            InferiorCadastrarConsoleGBC.gridwidth = 1;
                            painelInferiorCadastrarJogo.add(cadastrarJogoButton, InferiorCadastrarJogoGBC);

                        } else if (Objects.equals(Objects.requireNonNull(cadastrarProdutoBox.getSelectedItem()).toString(), "Console")) {
                            painelDeVisualizacao.add(painelSuperiorCadastrarProduto, BorderLayout.NORTH);
                            painelDeVisualizacao.add(painelCentralCadastrarProduto, BorderLayout.CENTER);
                            painelDeVisualizacao.add(painelInferiorCadastrarConsole, BorderLayout.SOUTH);
                            painelDeVisualizacao.repaint();
                            painelDeVisualizacao.revalidate();

                            JLabel memoriaLabel = new JLabel("Memória:");
                            painelInferiorCadastrarConsole.add(memoriaLabel, InferiorCadastrarConsoleGBC);

                            JTextField memoriaField = new JTextField("Digite a memória", 25);
                            memoriaField.addFocusListener(new FocusListener() {
                                @Override
                                public void focusGained(FocusEvent e) {
                                    if (Objects.equals(memoriaField.getText(), "Digite a memória")) {
                                        memoriaField.setText("");
                                    }
                                }

                                @Override
                                public void focusLost(FocusEvent e) {
                                    if (Objects.equals(memoriaField.getText(), "")) {
                                        memoriaField.setText("Digite a memória");
                                    }
                                }
                            });
                            InferiorCadastrarConsoleGBC.gridx = 1;
                            painelInferiorCadastrarConsole.add(memoriaField, InferiorCadastrarConsoleGBC);
                            InferiorCadastrarConsoleGBC.gridy += 1;
                            InferiorCadastrarConsoleGBC.gridwidth = 2;
                            InferiorCadastrarConsoleGBC.anchor = GridBagConstraints.CENTER;

                            JButton cadastrarConsoleButton = new JButton("Cadastrar Console");
                            cadastrarConsoleButton.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    // cadastrar
									try{
										String nome = nomeProdutoField.getText();
										float preco = Float.parseFloat(precoField.getText());
										int qntNoEstoque = Integer.parseInt(estoqueProdutoField.getText());
										String descricao = descricaoField.getText();
										Set<String> plataformas = Set.of(plataformaField.getText().split(","));
										String memoria = memoriaField.getText();

										ControleDeEstoque.cadastrarProdutoNoEstoque(new Console(nome, preco, qntNoEstoque, descricao, plataformas, memoria));
                                    	painelDeVisualizacao.removeAll();
                                 		painelDeVisualizacao.repaint();
                                    	painelDeVisualizacao.revalidate();
									}catch(IOException ioe) {
										System.out.println("FALHA");
									}
                                }
                            });

                            painelInferiorCadastrarConsole.add(cadastrarConsoleButton, InferiorCadastrarConsoleGBC);

                        } else if (Objects.equals(Objects.requireNonNull(cadastrarProdutoBox.getSelectedItem()).toString(), "Fone")) {
                            painelDeVisualizacao.add(painelSuperiorCadastrarProduto, BorderLayout.NORTH);
                            painelDeVisualizacao.add(painelCentralCadastrarProduto, BorderLayout.CENTER);
                            painelDeVisualizacao.add(painelInferiorCadastrarFone, BorderLayout.SOUTH);
                            painelDeVisualizacao.repaint();
                            painelDeVisualizacao.revalidate();

                            JLabel sensibilidadeLabel = new JLabel("Sensibilidade:");
                            painelInferiorCadastrarFone.add(sensibilidadeLabel, InferiorCadastrarFoneGBC);

                            JTextField sensibilidadeField = new JTextField("Digite o valor da sensibilidade");
                            sensibilidadeField.addFocusListener(new FocusListener() {
                                @Override
                                public void focusGained(FocusEvent e) {
                                    if (Objects.equals(sensibilidadeField.getText(), "Digite o valor da sensibilidade")) {
                                        sensibilidadeField.setText("");
                                    }
                                }

                                @Override
                                public void focusLost(FocusEvent e) {
                                    if (Objects.equals(sensibilidadeField.getText(), "")) {
                                        sensibilidadeField.setText("Digite o valor da sensibilidade");
                                    }
                                }
                            });
                            InferiorCadastrarFoneGBC.gridx = 1;
                            painelInferiorCadastrarFone.add(sensibilidadeField, InferiorCadastrarFoneGBC);

                            JLabel temMicrofoneLabel = new JLabel("Tem microfone?");
                            InferiorCadastrarFoneGBC.gridx = 0;
                            InferiorCadastrarFoneGBC.gridy += 1;
                            painelInferiorCadastrarFone.add(temMicrofoneLabel, InferiorCadastrarFoneGBC);

                            String[] simNao = {"Sim", "Não"};
                            JComboBox temMicrofoneBox = new JComboBox(simNao);
                            InferiorCadastrarFoneGBC.gridx = 1;
                            painelInferiorCadastrarFone.add(temMicrofoneBox, InferiorCadastrarFoneGBC);

                            InferiorCadastrarFoneGBC.gridx = 0;
                            InferiorCadastrarFoneGBC.gridy += 1;
                            JLabel temBluetoothLabel = new JLabel("Tem Bluetooth?");
                            painelInferiorCadastrarFone.add(temBluetoothLabel, InferiorCadastrarFoneGBC);

                            JComboBox temBluetoothBox = new JComboBox(simNao);
                            InferiorCadastrarFoneGBC.gridx += 1;
                            painelInferiorCadastrarFone.add(temBluetoothBox, InferiorCadastrarFoneGBC);

                            JButton cadastrarFoneButton = new JButton("Cadastrar fone");
                            cadastrarFoneButton.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
									String nome = nomeProdutoField.getText();
									float preco = Float.parseFloat(precoField.getText());
									int qntNoEstoque = Integer.parseInt(estoqueProdutoField.getText());
									String descricao = descricaoField.getText();
									Set<String> plataformas = Set.of(plataformaField.getText().split(","));
									Boolean temMicrofone = (temMicrofoneBox.getSelectedItem() == "Sim") ? true : false;
									
									//ControleDeEstoque.cadastrarProdutoNoEstoque(new Fone(nome, preco, qntNoEstoque, descricao, plataformas, ));
                                    painelDeVisualizacao.removeAll();
                                    painelDeVisualizacao.repaint();
                                    painelDeVisualizacao.revalidate();
                                }
                            });

                            InferiorCadastrarFoneGBC.gridx = 0;
                            InferiorCadastrarFoneGBC.gridy += 1;
                            InferiorCadastrarFoneGBC.gridwidth = 2;
                            InferiorCadastrarFoneGBC.anchor = GridBagConstraints.CENTER;
                            painelInferiorCadastrarFone.add(cadastrarFoneButton, InferiorCadastrarFoneGBC);
                        } else if (Objects.equals(Objects.requireNonNull(cadastrarProdutoBox.getSelectedItem()).toString(), "Mouse")) {
                            painelDeVisualizacao.add(painelSuperiorCadastrarProduto, BorderLayout.NORTH);
                            painelDeVisualizacao.add(painelCentralCadastrarProduto, BorderLayout.CENTER);
                            painelDeVisualizacao.add(painelInferiorCadastrarMouse, BorderLayout.SOUTH);
                            painelDeVisualizacao.repaint();
                            painelDeVisualizacao.revalidate();

                            JLabel dpiLabel = new JLabel("Dpi:");
                            painelInferiorCadastrarMouse.add(dpiLabel, InferiorCadastrarMouseGBC);

                            JTextField dpiField = new JTextField("Digite aqui o dpi", 20);
                            dpiField.addFocusListener(new FocusListener() {
                                @Override
                                public void focusGained(FocusEvent e) {
                                    if (Objects.equals(dpiField.getText(), "Digite aqui o dpi")) {
                                        dpiField.setText("");
                                    }
                                }

                                @Override
                                public void focusLost(FocusEvent e) {
                                    if (Objects.equals(dpiField.getText(), "")) {
                                        dpiField.setText("Digite aqui o dpi");
                                    }
                                }
                            });
                            InferiorCadastrarMouseGBC.gridx = 1;
                            painelInferiorCadastrarMouse.add(dpiField, InferiorCadastrarMouseGBC);
                            InferiorCadastrarMouseGBC.gridx = 0;
                            InferiorCadastrarMouseGBC.gridy += 1;

                            JLabel temBluetoothLabel = new JLabel("Tem Bluetooth?");
                            painelInferiorCadastrarMouse.add(temBluetoothLabel, InferiorCadastrarMouseGBC);

                            InferiorCadastrarMouseGBC.gridx = 1;
                            String[] simNao = {"Sim", "Não"};
                            JComboBox temBluetoothBox = new JComboBox(simNao);
                            painelInferiorCadastrarMouse.add(temBluetoothBox, InferiorCadastrarMouseGBC);



                            JButton cadastrarMouseButton = new JButton("Cadastrar Mouse");
                            cadastrarMouseButton.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    // cadastrar mouse
                                    painelDeVisualizacao.removeAll();
                                    painelDeVisualizacao.repaint();
                                    painelDeVisualizacao.revalidate();
                                }
                            });
                            InferiorCadastrarMouseGBC.gridy += 1;
                            InferiorCadastrarMouseGBC.gridx = 0;
                            InferiorCadastrarMouseGBC.anchor = GridBagConstraints.CENTER;
                            InferiorCadastrarMouseGBC.gridwidth = 2;
                            painelInferiorCadastrarMouse.add(cadastrarMouseButton, InferiorCadastrarMouseGBC);
                        } else if (Objects.equals(Objects.requireNonNull(cadastrarProdutoBox.getSelectedItem()).toString(), "Teclado Mecanico")) {
                            painelDeVisualizacao.add(painelSuperiorCadastrarProduto, BorderLayout.NORTH);
                            painelDeVisualizacao.add(painelCentralCadastrarProduto, BorderLayout.CENTER);
                            painelDeVisualizacao.add(painelInferiorCadastrarTeclado, BorderLayout.SOUTH);
                            painelDeVisualizacao.repaint();
                            painelDeVisualizacao.revalidate();

                            JLabel switchTecladoLabel = new JLabel("Tipo de switch:");
                            painelInferiorCadastrarTeclado.add(switchTecladoLabel, InferiorCadastrarTecladoGBC);

                            JTextField switchTecladoField = new JTextField("Digite o tipo de switch", 20);
                            switchTecladoField.addFocusListener(new FocusListener() {
                                @Override
                                public void focusGained(FocusEvent e) {
                                    if (Objects.equals(switchTecladoField.getText(), "Digite o tipo de switch")) {
                                        switchTecladoField.setText("");
                                    }
                                }

                                @Override
                                public void focusLost(FocusEvent e) {
                                    if (Objects.equals(switchTecladoField.getText(), "")) {
                                        switchTecladoField.setText("Digite o tipo de switch");
                                    }
                                }
                            });
                            InferiorCadastrarTecladoGBC.gridx = 1;
                            painelInferiorCadastrarTeclado.add(switchTecladoField, InferiorCadastrarTecladoGBC);

                            InferiorCadastrarTecladoGBC.gridy += 1;
                            InferiorCadastrarTecladoGBC.gridx = 0;
                            JLabel temBluetoothLabel = new JLabel("Tem Bluetooth?");
                            painelInferiorCadastrarTeclado.add(temBluetoothLabel, InferiorCadastrarTecladoGBC);

                            InferiorCadastrarTecladoGBC.gridx = 1;
                            String[] simNao = {"Sim", "Não"};
                            JComboBox temBluetoothBox = new JComboBox(simNao);
                            painelInferiorCadastrarTeclado.add(temBluetoothBox, InferiorCadastrarTecladoGBC);


                            InferiorCadastrarTecladoGBC.gridx = 0;
                            InferiorCadastrarTecladoGBC.gridy += 1;
                            InferiorCadastrarTecladoGBC.gridwidth = 2;
                            InferiorCadastrarTecladoGBC.anchor = GridBagConstraints.CENTER;

                            JButton cadastrarTecladoButton = new JButton("Cadastar teclado");
                            cadastrarTecladoButton.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    // cadastrar teclado
                                    painelDeVisualizacao.removeAll();
                                    painelDeVisualizacao.repaint();
                                    painelDeVisualizacao.revalidate();
                                }
                            });
                            painelInferiorCadastrarTeclado.add(cadastrarTecladoButton, InferiorCadastrarTecladoGBC);
                        }

                    }
                });
                painelDeVisualizacao.repaint();
                painelDeVisualizacao.revalidate();

            }
        });

        JButton botaoDeAlterarEstoque = new JButton("Alterar estoque");
        painelProduto.add(botaoDeAlterarEstoque);
        botaoDeAlterarEstoque.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                painelDeVisualizacao.removeAll();
                painelDeVisualizacao.repaint();
                painelDeVisualizacao.revalidate();

                JPanel painelAlterarEstoque = new JPanel();
                painelAlterarEstoque.setLayout(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridy = 0;
                gbc.gridx = 0;
                gbc.anchor = GridBagConstraints.LINE_START;
                gbc.insets = new Insets(5,5,5,5);

                painelDeVisualizacao.add(painelAlterarEstoque);

                JLabel nomeProdutoLabel = new JLabel("Nome:");
                painelAlterarEstoque.add(nomeProdutoLabel, gbc);
                gbc.gridx = 1;

                JTextField nomeProdutoField = new JTextField("Digite o nome do produto", 20);
                nomeProdutoField.addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        if (Objects.equals(nomeProdutoField.getText(), "Digite o nome do produto")) {
                            nomeProdutoField.setText("");
                        }
                    }

                    @Override
                    public void focusLost(FocusEvent e) {
                        if (Objects.equals(nomeProdutoField.getText(), "")) {
                            nomeProdutoField.setText("Digite o nome do produto");
                        }
                    }
                });
                painelAlterarEstoque.add(nomeProdutoField, gbc);

                gbc.gridx = 1;
                gbc.gridy = 1;
                JTextField estoqueField = new JTextField("Selecione um produto", 20);
                estoqueField.setEditable(false);
                painelAlterarEstoque.add(estoqueField, gbc);

                gbc.gridy = 0;
                gbc.gridx = 2;
                JButton selecionarProduto = new JButton("Selecionar");
                selecionarProduto.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String nome = nomeProdutoField.getText();
                        //chamar o produto nome
                        estoqueField.setEditable(true);
                        //estoqueField.setText(produto.getEstoque());

                    }
                });
                painelAlterarEstoque.add(selecionarProduto, gbc);

                gbc.gridy = 1;
                gbc.gridx = 0;
                JLabel estoqueLabel = new JLabel("Estoque:");
                painelAlterarEstoque.add(estoqueLabel, gbc);
                gbc.gridx = 2;

                JButton alterarEstoqueButton = new JButton("Alterar estoque");
                alterarEstoqueButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int estoque = Integer.parseInt(estoqueField.getText());
                        //produto.setEstoque(estoque);
                        //alterar estoque
                    }
                });
                painelAlterarEstoque.add(alterarEstoqueButton, gbc);


            }
        });

        JButton listarProdutosCadastradosButton = new JButton("Listar Produtos Cadastrados");
        painelProduto.add(listarProdutosCadastradosButton);
        listarProdutosCadastradosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                painelDeVisualizacao.removeAll();
                painelDeVisualizacao.repaint();
                painelDeVisualizacao.revalidate();

                JTextArea produtosCadastradosTextArea = new JTextArea();
                JScrollPane scrollPane = new JScrollPane(produtosCadastradosTextArea);
                scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
                painelDeVisualizacao.add(scrollPane, BorderLayout.CENTER);
				try{
					produtosCadastradosTextArea.setText(ControleDeEstoque.listarProdutosCadastrados());
				}catch(IOException ioe) {
					System.out.println("Falha");
				}
               

            }
        });

        JButton listarProdutosDisponiveisButton = new JButton("Listar Produtos Disponíveis");
        painelProduto.add(listarProdutosDisponiveisButton);
        listarProdutosDisponiveisButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                painelDeVisualizacao.removeAll();
                painelDeVisualizacao.repaint();
                painelDeVisualizacao.revalidate();

                JTextArea produtosDisponiveisTextArea = new JTextArea();
                JScrollPane scrollPane = new JScrollPane(produtosDisponiveisTextArea);
                scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
                painelDeVisualizacao.add(scrollPane, BorderLayout.CENTER);

                // imprimir produtos Disponíveis

            }
        });

        JButton listarProdutosIndisponiveisButton = new JButton("Listar Produtos Indisponíveis");
        painelProduto.add(listarProdutosIndisponiveisButton);
        listarProdutosIndisponiveisButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                painelDeVisualizacao.removeAll();
                painelDeVisualizacao.repaint();
                painelDeVisualizacao.revalidate();

                JTextArea produtosIndisponiveisTextArea = new JTextArea();
                JScrollPane scrollPane = new JScrollPane(produtosIndisponiveisTextArea);
                scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
                painelDeVisualizacao.add(scrollPane, BorderLayout.CENTER);

                // imprimir produtos indisponiveis

            }
        });


        JPanel painelCadastro = new JPanel();
        painelCadastro.setBackground(Color.DARK_GRAY);
        painelCadastro.setPreferredSize(new Dimension(600, 35));
        painelCadastro.setLayout(new FlowLayout(FlowLayout.CENTER));
        janelaDeOpcao.add(painelCadastro, BorderLayout.CENTER);

        JButton botaoDeListarClientes = new JButton("Listar clientes cadastrados");
        painelCadastro.add(botaoDeListarClientes);
        botaoDeListarClientes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                painelDeVisualizacao.removeAll();
                painelDeVisualizacao.repaint();
                painelDeVisualizacao.revalidate();

                JTextArea listarClientesTextArea = new JTextArea();
                JScrollPane scrollPane = new JScrollPane(listarClientesTextArea);
                scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
                painelDeVisualizacao.add(scrollPane, BorderLayout.CENTER);

                // imprimir todos os clientes cadastrados
            }
        });

        JButton botaoDeListarClientesExcluidos = new JButton("Listar clientes excluídos");
        painelCadastro.add(botaoDeListarClientesExcluidos);
        botaoDeListarClientesExcluidos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                painelDeVisualizacao.removeAll();
                painelDeVisualizacao.repaint();
                painelDeVisualizacao.revalidate();

                JTextArea listarClientesExcluidosTextArea = new JTextArea();
                JScrollPane scrollPane = new JScrollPane(listarClientesExcluidosTextArea);
                scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
                painelDeVisualizacao.add(scrollPane, BorderLayout.CENTER);

                // imprimir todos os clientes excluídos
            }
        });

        JButton botaoDeListarTodasAsVendas = new JButton("Listar todas as vendas");
        painelCadastro.add(botaoDeListarTodasAsVendas);
        botaoDeListarTodasAsVendas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                painelDeVisualizacao.removeAll();
                painelDeVisualizacao.repaint();
                painelDeVisualizacao.revalidate();

                JTextArea listarTodasVendasTextArea = new JTextArea();
                JScrollPane scrollPane = new JScrollPane(listarTodasVendasTextArea);
                scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
                painelDeVisualizacao.add(scrollPane, BorderLayout.CENTER);

                // imprimir todas as vendas
            }
        });

        janelaDeOpcao.setVisible(true);
    }
}
