//package br.usp.icmc.biblioteca;
import java.util.Scanner;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;

public class Main {
  public static void main(String[] args) {
    Biblioteca bib = new Biblioteca();
    int opcao = -1;

    FileWriter log;
    try {log = new FileWriter("historico.log", true);}
    catch (FileNotFoundException e) {}
    catch (IOException e) {}

    try {
      String csv;
      //======================================================
      //              Carrega usuarios do banco
      //======================================================
      FileReader fileReaderU = new FileReader("usuarios.csv");
      BufferedReader usuarios_csv = new BufferedReader(fileReaderU);

      while ((csv = usuarios_csv.readLine()) != null) {
        Usuario u = new Usuario(csv);
        bib.adiciona_usuario(u.getNome(), u.getCPF(), u.getTipo());
      }
      fileReaderU.close();
      //======================================================
      //              Carrega livros do banco
      //======================================================
      FileReader fileReaderL = new FileReader("livros.csv");
      BufferedReader livros_csv = new BufferedReader(fileReaderL);

      while ((csv = livros_csv.readLine()) != null) {
        Livro l = new Livro(csv);
        bib.adiciona_livro(l.getTitulo(), l.getAutor(), l.getCodigo(), l.getQuantidade(), l.getTipo());
      }
      fileReaderL.close();
      //======================================================
      //              Carrega emprestimos do banco
      //======================================================
      FileReader fileReaderE = new FileReader("emprestimos.csv");
      BufferedReader emprestimos_csv = new BufferedReader(fileReaderE);

      while ((csv = emprestimos_csv.readLine()) != null) {
        String[] values = csv.split(",");
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date dateE = new Date();
        try { dateE = df.parse(values[0]);}
        catch (Exception ex) { ex.printStackTrace();}

        Livro livro = new Livro();
        livro = bib.getObjLivro(Integer.parseInt(values[2]));
        Usuario u = new Usuario();
        u = bib.getObjUsuario(values[3]);

        bib.empresta(u, livro, dateE);
      }

      fileReaderE.close();

    } catch (FileNotFoundException e) {}
      catch (IOException e) {}

    /* Checa se, ao carregar banco de dados, existe atraso */
      Scanner s = new Scanner(System. in );
    System.out.println("\n\n\n .: Bem vindo ao DEDALUS :.\n");
    Date dt = dataDeHoje();
    registraLog("\n-----------------------------\n" +
      "Sistema Iniciado - " +
      new SimpleDateFormat("dd/MM/yyyy").format(dt) +
      "\n-----------------------------\n", " ");
    registraLog("Carregou banco de Dados.", new SimpleDateFormat("dd/MM/yyyy").format(dt));
    bib.verifica_atraso(dt);

    //======================================================
    //                  Menu 0 - principal
    //======================================================
    while (opcao != 0) {
      System.out.print(
        "\n\n\n .: Menu :.\n\n" +
        "1 - Menu livro\n" +
        "2 - Menu Usuário\n" +
        "3 - Alterar data de hoje\n" +
        "4 - Registrar empréstimo\n" +
        "5 - Registrar devolução\n" +
        "6 - Listar todos os livros\n" +
        "7 - Listar todos os usuários\n" +
        "8 - Listar empréstimos\n" +
        "9 - Listar atrasos\n" +
        "0 - Sair do programa\n\n" +
        "Opção: ");
      opcao = s.nextInt();

      switch (opcao) {
        case 1:
          menuLivro(bib, dt);
          break;
        case 2:
          menuUsuario(bib, dt);
          break;
        case 3:
          dt = dataDeHoje();
          bib.verifica_atraso(dt);
          break;
        case 4:
          registraEmprestimo(bib, dt);
          break;
        case 5:
          registraDevolucao(bib, dt);
          break;
        case 6:
          bib.listar_livros();
          break;
        case 7:
          bib.listar_usuarios();
          break;
        case 8:
          bib.listar_emprestimos();
          break;
        case 9:
          bib.listar_atrasos(dt);
          break;
        case 0:
          try {
            //======================================================
            //              Escreve usuarios no banco
            //======================================================
            FileWriter writerU = new FileWriter("usuarios.csv");
            for (int i = 0; i < bib.usuarios_cadastrados.size(); i++) {
              DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
              writerU.append(bib.usuarios_cadastrados.get(i).getNome());
              writerU.append(",");
              writerU.append(bib.usuarios_cadastrados.get(i).getCPF());
              writerU.append(",");
              writerU.append(String.valueOf(bib.usuarios_cadastrados.get(i).getTipo()));
              writerU.append(",");
              writerU.append(String.valueOf(bib.usuarios_cadastrados.get(i).getQte_Livros_Emprestados()));
              writerU.append(",");
              writerU.append(String.valueOf(bib.usuarios_cadastrados.get(i).getSuspenso()));
              writerU.append(",");
              writerU.append(String.valueOf(bib.usuarios_cadastrados.get(i).getNum_Max_De_Emprestimos()));
              writerU.append(",");
              writerU.append(String.valueOf(bib.usuarios_cadastrados.get(i).getDias_Max_De_Emprestimo()));
              writerU.append(",");
              if(bib.usuarios_cadastrados.get(i).getSuspenso())
                writerU.append(df.format(bib.usuarios_cadastrados.get(i).getSuspenso_Ate()));
              else
                writerU.append("0");
              if (i != bib.usuarios_cadastrados.size()) writerU.append("\n");
            }
            writerU.close();
            //======================================================
            //              Escreve livros no banco
            //======================================================
            FileWriter writerL = new FileWriter("livros.csv");
            for (int i = 0; i < bib.livros_cadastrados.size(); i++) {
              writerL.append(bib.livros_cadastrados.get(i).getTitulo());
              writerL.append(",");
              writerL.append(bib.livros_cadastrados.get(i).getAutor());
              writerL.append(",");
              writerL.append(String.valueOf(bib.livros_cadastrados.get(i).getCodigo()));
              writerL.append(",");
              writerL.append(String.valueOf(bib.livros_cadastrados.get(i).getQuantidade()));
              writerL.append(",");
              writerL.append(String.valueOf(bib.livros_cadastrados.get(i).getTipo()));
              if (i != bib.livros_cadastrados.size()) writerL.append("\n");
            }
            writerL.close();
            //======================================================
            //              Escreve emprestimos no banco
            //======================================================
            FileWriter writerE = new FileWriter("emprestimos.csv");
            for (int i = 0; i < bib.emprestimos.size(); i++) {
              DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
              writerE.append(df.format(bib.emprestimos.get(i).getData_Emprestimo()));
              writerE.append(",");
              writerE.append(df.format(bib.emprestimos.get(i).getData_Devolucao()));
              writerE.append(",");
              writerE.append(String.valueOf(bib.emprestimos.get(i).getLivro().getCodigo()));
              writerE.append(",");
              writerE.append(String.valueOf(bib.emprestimos.get(i).getUsuario().getCPF()));
              if (i != bib.emprestimos.size()) writerE.append("\n");
            }
            writerE.close();

            registraLog("Banco de Dados atualizado.", new SimpleDateFormat("dd/MM/yyyy").format(dt));

          } catch (Exception e) { System.out.println("Error in CsvFileWriter !!!"); e.printStackTrace();
          }

          break;
        default:
          break;
      }
    }
  }

  //======================================================
  //                  Menu 1 - Livro
  //======================================================
  public static void menuLivro(Biblioteca bib, Date dt) {
    Scanner s = new Scanner(System. in );
    int opcao = -1;

    while (opcao != 0) {
      System.out.print(
        "\n\n\n .: Menu Livro :.\n\n" +
        "1 - Cadastrar livro\n" +
        "2 - Editar livro\n" +
        "3 - Excluir livro\n" +
        "0 - Voltar ao menu inicial\n" +
        "Opção: ");
      opcao = s.nextInt();

      switch (opcao) {
        case 1:
          adicionaLivro(bib, dt);
          break;
        case 2:
          edita_livro(bib);
          break;
        case 3:
          exclui_livro(bib);
          break;
        default:
          break;
      }
    }
  }

  //======================================================
  //               Adiciona um livro
  //======================================================
  public static void adicionaLivro(Biblioteca bib, Date dt) {
    Scanner s = new Scanner(System. in );
    System.out.print("Digite o título do livro: ");
    String stringTitulo = s.nextLine();
    System.out.print("Digite o autor do livro: ");
    String stringAutor = s.nextLine();
    System.out.print("Digite o ISBN livro: ");
    int isbn = s.nextInt();
    System.out.print("Digite o tipo do livro segundo a tabela a seguir:\n1 - Livro texto\n2 - Livro geral\nOpcao: ");
    int tipo = s.nextInt();
    System.out.print("Digite a quantidade de livros: ");
    int quantidade = s.nextInt();

    bib.adiciona_livro(stringTitulo, stringAutor, isbn, quantidade, tipo);
    registraLog("Livro " + stringTitulo + " adicionado", new SimpleDateFormat("dd/MM/yyyy").format(dt));
  }

  //======================================================
  //                Edita Livro existente
  //======================================================
  public static void edita_livro(Biblioteca bib) {

    Scanner s = new Scanner(System. in );

    System.out.println("\n\nLivros disponíveis:\n");
    bib.listar_livros();
    System.out.print("\n\nDigite o ISBN do livro que deseja editar: ");
    int isbn = s.nextInt();

    if (bib.livroExiste(isbn)) {
      int l = bib.getIndiceLivro(isbn);
      int opcao = -1;

      while (opcao != 0) {
        System.out.print(
          "\n\n\n .: Editar livro :.\n\n" +
          "1 - Editar título\n" +
          "2 - Editar autor\n" +
          "3 - Editar quantidade\n" +
          "4 - Editar tipo\n" +
          "0 - Sair da edição de livro\n" +
          "Opção: ");
        opcao = s.nextInt();

        switch (opcao) {
          case 1:
            System.out.print("\n\nDigite o novo título: ");
            s = new Scanner(System. in );
            String novotitulo = s.nextLine();
            bib.livros_cadastrados.get(l).setTitulo(novotitulo);
            System.out.print("\n\nTítulo alterado com sucesso!\n\n");
            break;
          case 2:
            System.out.print("\n\nDigite o novo autor: ");
            s = new Scanner(System. in );
            String novoautor = s.nextLine();
            bib.livros_cadastrados.get(l).setAutor(novoautor);
            System.out.print("\n\nAutor alterado com sucesso!\n\n");
            break;
          case 3:
            System.out.print("\n\nDigite a nova quantidade: ");
            int novaquant = s.nextInt();
            bib.livros_cadastrados.get(l).setQuantidade(novaquant);
            System.out.print("\n\nQuantidade alterada com sucesso!\n\n");
            break;
          case 4:
            System.out.print("\n\nDigite o novo tipo:\n1 - Livro texto\n2 - Livro geral\nTipo: ");
            int novotipo = s.nextInt();
            bib.livros_cadastrados.get(l).setTipo(novotipo);
            System.out.print("\n\nTipo alterado com sucesso!\n\n");
            break;
          default:
            break;
        }
      }
    } else System.out.println("\n\nEste livro não está cadastrado!\n\n");
  }

  //======================================================
  //                Exclui livro existente
  //======================================================
  public static void exclui_livro(Biblioteca bib) {

    Scanner s = new Scanner(System. in );

    System.out.println("\n\nLivros cadastrados:\n");
    bib.listar_livros();
    System.out.print("\n\nDigite o ISBN do livro que deseja excluir: ");
    int isbn = s.nextInt();

    if (bib.livroExiste(isbn)) {
      int i;
      for (i = 0; i < bib.livros_cadastrados.size(); i++) {
        if (bib.livros_cadastrados.get(i).getCodigo() == isbn) {
          if (bib.livros_cadastrados.get(i).getQuantidade() == 0) {
            System.out.println("\n\nLivro não pode ser excluido porque está emprestado!\n\n");
            return;
          } else if (bib.livros_cadastrados.get(i).getQuantidade() == 1) {
            bib.livros_cadastrados.remove(bib.getIndiceLivro(isbn));
            System.out.println("\n\nLivro excluido com sucesso!\n\n");
            return;
          } else {
            bib.livros_cadastrados.get(i).setQuantidade(bib.livros_cadastrados.get(i).getQuantidade() - 1);
            System.out.println("\n\nLivro excluido com sucesso!\n\n");
            return;
          }
        }
      }
    } else System.out.println("\n\nEste livro não está cadastrado!\n\n");
  }




  //======================================================
  //                  Menu 2 - Usuario
  //======================================================
  public static void menuUsuario(Biblioteca bib, Date dt) {
    Scanner s = new Scanner(System. in );
    int opcao = -1;

    while (opcao != 0) {
      System.out.print(
        "\n\n\n .: Menu Usuário :.\n\n" +
        "1 - Cadastrar usuário\n" +
        "2 - Editar usuário\n" +
        "3 - Excluir usuário\n" +
        "0 - Voltar ao menu inicial\n" +
        "Opção: ");
      opcao = s.nextInt();
      switch (opcao) {
        case 1:
          adicionaUsuario(bib, dt);
          break;
        case 2:
          edita_usuario(bib);
          break;
        case 3:
          exclui_usuario(bib);
          break;
        default:
          break;
      }
    }
  }

  //======================================================
  //               Adiciona um usuario
  //======================================================
  public static void adicionaUsuario(Biblioteca bib, Date dt) {
    Scanner s = new Scanner(System. in );
    System.out.print("Digite o nome do usuário: ");
    String stringNome = s.nextLine();
    System.out.print("Digite o CPF do usuário: ");
    String stringCPF = s.nextLine();
    System.out.print("Digite o tipo do usuário segundo a tabela a seguir:\n1 - Aluno\n2 - Professor\n3 - Comunidade\n\nTipo: ");
    int tipo = s.nextInt();

    if (bib.adiciona_usuario(stringNome, stringCPF, tipo) == false) System.out.print("\nUsuário ja cadastrado!\n");
    else registraLog("Usuario " + stringNome + " adicionado", new SimpleDateFormat("dd/MM/yyyy").format(dt));
  }

  //======================================================
  //                Edita usuario exixtente
  //======================================================
  public static void edita_usuario(Biblioteca bib) {

    Scanner s = new Scanner(System. in );

    System.out.println("\n\nUsuários cadastrados:\n");
    bib.listar_usuarios();
    System.out.print("\n\nDigite o CPF do usuário que deseja editar: ");
    String cpf = s.nextLine();

    if (bib.usuarioExiste(cpf)) {
      int u = bib.getIndiceUsuario(cpf);
      int opcao = -1;

      while (opcao != 0) {
        System.out.print(
          "\n\n\n .: Editar usuário :.\n\n" +
          "1 - Editar nome\n" +
          "2 - Editar tipo\n" +
          "0 - Sair da edição de usuário\n" +
          "Opção: ");
        opcao = s.nextInt();

        switch (opcao) {
          case 1:
            System.out.print("\n\nDigite o novo nome: ");
            s = new Scanner(System. in );
            String novonome = s.nextLine();
            bib.usuarios_cadastrados.get(u).setNome(novonome);
            System.out.print("\n\nNome alterado com sucesso!\n\n");
            break;
          case 2:
            System.out.print("\n\nDigite o novo tipo:\n1 - Aluno\n2 - Professor\n3 - Comunidade\ntipo: ");
            int novotipo = s.nextInt();
            bib.usuarios_cadastrados.get(u).setTipo(novotipo);
            System.out.print("\n\nTipo alterado com sucesso!\n\n");
            break;
          default:
            break;
        }
      }
    } else System.out.println("\n\nEste usuário não está cadastrado!\n\n");
  }

  //======================================================
  //                Exclui usuario existente
  //======================================================
  public static void exclui_usuario(Biblioteca bib) {

    Scanner s = new Scanner(System. in );

    System.out.println("\n\nUsuários cadastrados:\n");
    bib.listar_usuarios();
    System.out.print("\n\nDigite o CPF do usuário que deseja excluir: ");
    String cpf = s.nextLine();

    if (bib.usuarioExiste(cpf)) {
      if (bib.usuarioPossuiEmprestimo(cpf)) {
        System.out.println("\n\nUsuário não pode ser excluido porque possui empréstimos pendentes!\n\n");
        return;
      } else {
        bib.usuarios_cadastrados.remove(bib.getIndiceUsuario(cpf));
        System.out.println("\n\nUsuário excluido com sucesso!\n\n");
      }
    } else System.out.println("\n\nEste usuário não está cadastrado!\n\n");
  }




  //======================================================
  //     Recebe uma data(input) e simula data atual
  //======================================================
  public static Date dataDeHoje() {
    Scanner s = new Scanner(System. in );
    System.out.print("Digite a data de hoje (dd/mm/aaaa): ");
    String stringData = s.nextLine();
    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    Date dt = new Date();

    try { dt = df.parse(stringData);}
    catch (Exception ex) { ex.printStackTrace();}

    return dt;
  }




  //======================================================
  //               Adiciona um emprestimo
  //======================================================
  public static void registraEmprestimo(Biblioteca bib, Date dt) {
    Scanner s = new Scanner(System. in );
    System.out.print("Digite o CPF do usuário: ");
    String stringCPF = s.nextLine();

    Usuario u = new Usuario();
    if (!bib.usuarioExiste(stringCPF)) {
      System.out.print("\nUsuário não cadastrado!\n");
      return;
    }
    u = bib.getObjUsuario(stringCPF);

    System.out.print("Digite o ISBN do livro: ");
    int isbn = s.nextInt();

    Livro l = new Livro();
    if (!bib.livroExiste(isbn)) {
      System.out.print("\nLivro não cadastrado!\n");
      return;
    }
    l = bib.getObjLivro(isbn);

    if (bib.verifica_suspensao(u, dt)) {
      System.out.println("\nUsuario esta suspenso ate " + new SimpleDateFormat("dd/MM/yyyy").format(u.getSuspenso_Ate()) + "\n");
      return;
    }

    int empresta = bib.empresta(u, l, dt);

    if (empresta == 1) {
      System.out.print("\nEmprestimo realizado com sucesso!\n");
      registraLog("Usuario " + stringCPF + " emprestou o livro " + l.getTitulo(), new SimpleDateFormat("dd/MM/yyyy").format(dt));
    }

    if (empresta == 2) { System.out.print("\nEste usuario ja emprestou este livro!\n"); }

    if (empresta == 3) { System.out.print("\nUsuario ja atingiu sua cota maxima de emprestimos!\n"); }

    if (empresta == 4) { System.out.print("\nUsuario nao permitido para este tipo de livro.\n"); }

    if (empresta == 5) { System.out.print("\nO usuario esta suspenso!\n"); }

    if (empresta == 6) { System.out.print("\nNao ha este livro em estoque no momento!\n"); }
  }



  //======================================================
  //              Devolve um livro
  //======================================================
  public static void registraDevolucao(Biblioteca bib, Date dt) {
    Scanner s = new Scanner(System. in );
    System.out.print("Digite o CPF do usuário: ");
    String stringCPF = s.nextLine();

    Usuario u = new Usuario();
    if (!bib.usuarioExiste(stringCPF)) {
      System.out.print("\nUsuário não cadastrado!\n");
      return;
    }
    u = bib.getObjUsuario(stringCPF);

    System.out.print("Digite o ISBN do livro: ");
    int isbn = s.nextInt();

    Livro l = new Livro();
    if (!bib.livroExiste(isbn)) {
      System.out.print("\nLivro não cadastrado!\n");
      return;
    }
    l = bib.getObjLivro(isbn);

    int devolve = bib.devolve(u, l, dt);

    if (devolve == 1) {
      System.out.print("\nDevolucao realizada com sucesso!\n");
      registraLog("Usuario " + stringCPF + " devolveu o livro " + l.getTitulo(), new SimpleDateFormat("dd/MM/yyyy ").format(dt));
    }

    if (devolve == 2) { System.out.print("\nEste livro nao foi emprestado por este usuario!\n"); }
  }



  //======================================================
  //               Adiciona registro no log
  //======================================================
  public static void registraLog(String mensagem, String dt) {
    try {
      FileWriter log = new FileWriter("historico.log", true);
      if(dt == " ")
        log.append(mensagem);
      else
        log.append(dt + " - " + new SimpleDateFormat("H:mm ").format(new Date()) + mensagem + "\n");
      log.close();
    }

    catch (IOException e) {}
  }

}
