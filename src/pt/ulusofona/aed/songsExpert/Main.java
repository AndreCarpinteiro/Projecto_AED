package pt.ulusofona.aed.songsExpert;

import java.io.*;
import java.util.*;
//ID grupo 287

public class Main {

    private static ArrayList<TemaMusical> album = new ArrayList<>();
    //Globais para ler ficheiros e guardar e vai para o album
    private static HashMap<String, Artista> listaArtistas = new HashMap<>();
    private static HashMap<String, Artista> listaIDArtistas = new HashMap<>();
    private static HashMap<String, TemaMusical> listaIDMusica = new HashMap<>();
    private static HashMap<String, ArrayList<TemaMusical>> listaArtista_e_Musicas = new HashMap<>();


    public static void main(String[] args) {

        lerFicheiros();
        obterTemasMusicais();

        System.out.println("Bem-vindos ao Especialista Musical do DEISI...");
        Scanner in = new Scanner(System.in);
        String line = in.nextLine();

        while (line != null && !line.equals("QUIT")) {
            long start = System.currentTimeMillis();
            String result = askTheExpert(line);
            long end = System.currentTimeMillis();
            System.out.println(result);
            System.out.println("(demorou " + (end - start) + " ms)");

            line = in.nextLine();
        }

        System.out.println("Adeus.");
    }

    public static String askTheExpert(String query) {

        if (query.equals("COUNT_SONGS")) {//OBRIGATORIO---------------------------------
            return String.valueOf(album.size());
        } else if (query.equals("COUNT_SONGS_WITHOUT_YEAR")) { //OBRIGATORIO---------------
            int countCSWY = 0;
            for (TemaMusical temaMusical : album) {
                if (temaMusical.ano == 0) {
                    countCSWY++;
                }
            }
            return String.valueOf(countCSWY);

//----------------------------------------------------------------------------------------------------------------------
        } else if (query.startsWith("COUNT_ARTISTS_MANY_SONGS")) {//OBRIGATORIO-------------
            int countCAMS = 0;
            String[] dados = query.split(" ");
            int numCAMS = Integer.parseInt(dados[1]);

            for (Artista i : listaArtistas.values()) {
                if (i.numeroDeMusicas > numCAMS) {
                    countCAMS++;
                }
            }
            return String.valueOf(countCAMS);

//----------------------------------------------------------------------------------------------------------------------
        } else if (query.startsWith("COUNT_SONGS_MANY_ARTISTS")) {//OBRIGATORIO-------------
            String[] dados = query.split(" ");
            int numCSMA = Integer.parseInt(dados[1]);
            int countCSMA = 0;

            for (TemaMusical temaMusical : album) {
                if (temaMusical.artistasEnvolvidos.size() > numCSMA) {
                    countCSMA++;
                }
            }
            return String.valueOf(countCSMA);

//---------------------------------------------------------------------------------------------------------------------
        } else if (query.equals("COUNT_UNIQUE_TITLES")) {//OBRIGATORIO-------------------
            //Cria um hashset que nao deixa existir elementos repetidos
            //e vou percorrer o album e adiciono o titulo da musia, se for repetido ele ignora
            HashSet<String> hset = new HashSet<String>();
            for (TemaMusical temaMusical : album) {
                hset.add(temaMusical.titulo);
            }
            return String.valueOf(hset.size());

//----------------------------------------------------------------------------------------------------------------------
        } else if (query.startsWith("GET_SONGS_WITH_ARTISTS")) {//OBRIGATORIO------------

            //GET_SONGS_WITH_ARTISTS Placido Domingo,Renata Scotto
            String[] dados = query.split(" ", 2);
            String[] dadosArt = dados[1].split(",");
            String texto = "";
            int countGSWA = 0;
            ArrayList<String> idMusicasGravados = new ArrayList<>();
            ArrayList<String> idVerificadas = new ArrayList<>();
            idMusicasGravados = listaArtistas.get(dadosArt[0]).idmusicas;
            for (String s : idMusicasGravados) {
                countGSWA = 0;
                for (int i = 1; i < dadosArt.length; i++) {
                    Artista temp = listaArtistas.get(dadosArt[i]);
                    if (temp != null) {
                        if (temp.idmusicas == null) {
                            return "N/A";
                        }
                        if (temp.idmusicas.contains(s)) {
                            countGSWA++;
                        }
                        if (countGSWA == dadosArt.length - 1) {
                            idVerificadas.add(s);
                        }
                    }
                }
            }
            for (int i = 0; i < idVerificadas.size(); i++) {
                if (i > 0) {
                    texto += "|";
                }
                texto += listaIDMusica.get(idVerificadas.get(i)).titulo;
            }

            if (texto.equals("")) {
                return "N/A";
            }
            return texto;
//----------------------------------------------------------------------------------------------------------------------
        } else if (query.startsWith("GET_TOP_ARTISTS_WITH_SONGS_IN_YEAR")) {
            String[] dados = query.split(" ", 3);
            int topArtists = Integer.parseInt(dados[1]);
            int anoGTAWSIY = Integer.parseInt(dados[2]);
            StringBuilder resultado = new StringBuilder();

            HashMap<String, Integer> artistasNumeroMusicas = new HashMap<String, Integer>();

            long start = System.currentTimeMillis();

            for (TemaMusical tm : listaIDMusica.values()) {

                if (tm.ano == anoGTAWSIY) {
                    for (Artista a : tm.artistasEnvolvidos) {
                        if (artistasNumeroMusicas.containsKey(a.nome)) {
                            artistasNumeroMusicas.put(a.nome, artistasNumeroMusicas.get(a.nome) + 1);
                        } else {
                            artistasNumeroMusicas.put(a.nome, 1);
                        }
                    }
                }
            }
            long end1 = System.currentTimeMillis();
            System.out.println("PARTE 1 demorou " + (end1 - start) + "ms");

            List<String> artistasSemNome = new ArrayList<>(artistasNumeroMusicas.keySet());
            List<Integer> artistasSemNumero = new ArrayList<>(artistasNumeroMusicas.values());

            Collections.sort(artistasSemNome);
            artistasSemNumero.sort(Collections.reverseOrder());

            long end2 = System.currentTimeMillis();
            System.out.println("PARTE 2 demorou " + (end2 - end1) + "ms");
            int countTNSA = 0;
            boolean fim = false;
            String anterior = "";
            System.out.println(artistasSemNome);
            System.out.println(artistasNumeroMusicas);
            if (artistasNumeroMusicas.size() != 0 || artistasNumeroMusicas.size() > topArtists) {
                while (countTNSA < topArtists) {
                    for (String s : artistasSemNome) {
                        if (artistasSemNumero.get(countTNSA).equals(artistasNumeroMusicas.get(s))) {
                            if (countTNSA == topArtists - 1) {
                                resultado.append(s).append(" ").append(artistasSemNumero.get(countTNSA));
                                fim = true;
                                break;
                            } else {
                                resultado.append(s).append(" ").append(artistasSemNumero.get(countTNSA)).append("\n");
                                countTNSA++;
                                artistasSemNome.remove(s);
                                break;
                            }
                        }
                    }
                    if (fim) {
                        break;
                    }
                }
                long end3 = System.currentTimeMillis();
                System.out.println("PARTE 3 demorou " + (end3 - end2) + "ms");
                return String.valueOf(resultado);
            } else {
                long end3 = System.currentTimeMillis();
                System.out.println("PARTE 3 demorou " + (end3 - end2) + "ms");
                return "N/A";
            }
//----------------------------------------------------------------------------------------------------------------------
        } else if (query.startsWith("GET_UNIQUE_SONGS_BY_ARTIST")) {//DONE--------------

            String[] dados = query.split(" ", 2);
            String nomeArtista = dados[1];
            String resultado;

            HashSet<String> hsetMusicas = new HashSet<String>();

            for (TemaMusical tm : listaArtista_e_Musicas.get(nomeArtista)) {
                hsetMusicas.add(tm.titulo);
            }

            ArrayList<String> musicasValidadas = new ArrayList<>(hsetMusicas);
            musicasValidadas.sort(Collections.reverseOrder());

            resultado = String.join("|", musicasValidadas);
            return resultado;

//----------------------------------------------------------------------------------------------------------------------
        } else if (query.startsWith("COUNT_ARTISTS_WITH_WORD_IN_SONG_TITLE")) {//OBRIGATORIO----------------
            String[] dados = query.split(" ");
            String palavraChave = dados[1];

            HashSet<String> hset = new HashSet<String>();

            for (String s : listaIDMusica.keySet()) {
                if (listaIDMusica.get(s).titulo.contains(palavraChave)) {
                    for (Artista artista : listaIDMusica.get(s).artistasEnvolvidos) {
                        hset.add(artista.nome);
                        System.out.println(listaIDMusica.get(s).titulo + artista.nome);
                    }
                }
            }
            return String.valueOf(hset.size());

//----------------------------------------------------------------------------------------------------------------------
        } else if (query.startsWith("GET_ARTISTS_WITH_LOCATION")) {// OBRIGATORIO-----------------------
            String[] dados = query.split(" ", 2);
            String resultado = "";
            ArrayList<String> artistasNoLocal = new ArrayList<>();
            for (String s : listaArtistas.keySet()) {
                Artista temp = listaArtistas.get(s);
                if (temp.localizacao != null) {
                    if (temp.localizacao.contains(dados[1])) {
                        artistasNoLocal.add(s);
                    }
                }
            }

            Collections.sort(artistasNoLocal); //Coloca por ordem alfabetica
            for (int i = 0; i < artistasNoLocal.size(); i++) {
                String s = artistasNoLocal.get(i);
                if (i == artistasNoLocal.size() - 1) {
                    resultado += s;
                } else {
                    resultado += s + "\n";
                }
            }
            return resultado;

            //---------------------------------------------------------------------------------------------------------
        } else if (query.startsWith("TOP_N_ARTISTS_WITH_LOCATION")) {
            String[] dados = query.split(" ", 3);

            HashMap<String, Integer> artistasNoLocal = new HashMap<>();
            ArrayList<Integer> nMusicas = new ArrayList<>();
            int numeroTop = Integer.parseInt(dados[1]);
            String localizacao = dados[2];
            String resultado = "";

            for (String s : listaArtistas.keySet()) {
                if (listaArtistas.get(s).localizacao != null) {
                    if (listaArtistas.get(s).localizacao.contains(localizacao)) {
                        artistasNoLocal.put(s, listaArtistas.get(s).numeroDeMusicas);
                        nMusicas.add(listaArtistas.get(s).numeroDeMusicas);
                    }
                }
            }

            List<String> artistasOrdenados = new ArrayList<>(artistasNoLocal.keySet());
            Collections.sort(artistasOrdenados);

            //Ordenar as musicas em Bubble Sort
            int n = nMusicas.size();
            int aux = 0;
            for (int i = 0; i < n; i++) {
                for (int j = 1; j < (n - i); j++) {
                    if (nMusicas.get(j - 1) < nMusicas.get(j)) {
                        aux = nMusicas.get(j - 1);
                        nMusicas.set(j - 1, nMusicas.get(j));
                        nMusicas.set(j, aux);
                    }

                }
            }

            int countTNAWL = 0;
            boolean fim = false;
            while (countTNAWL < numeroTop) {
                for (String s : artistasOrdenados) {
                    if (countTNAWL < nMusicas.size()) {
                        int numerodeMusicas = nMusicas.get(countTNAWL);
                        int numerodaMusicaDoArtista = artistasNoLocal.get(s);
                        if (countTNAWL == numeroTop - 1 && numerodaMusicaDoArtista == numerodeMusicas) {//FIXME indexoutofbound 9 size 9
                            resultado += s + ":" + numerodeMusicas;
                            fim = true;
                            break;
                        } else if (numerodaMusicaDoArtista == numerodeMusicas) {
                            resultado += s + ":" + numerodeMusicas + "\n";
                            countTNAWL++;
                        }
                    } else {
                        fim = true;
                        break;
                    }
                }
                if (fim) {
                    break;
                }
            }
            return resultado;

//----------------------------------------------------------------------------------------------------------------------
        } else if (query.startsWith("MOST_FREQUENT_WORD_TITLES")) {
            String[] dados = query.split(" ", 2);
            HashMap<String, Integer> hmap = new HashMap<String, Integer>();
            HashMap<Integer, String> hmapInverted = new HashMap<Integer, String>();
            // Iterate through array of words
            for (TemaMusical musica : listaIDMusica.values()) {
                String[] palavrasDaMusica_i = musica.titulo.toLowerCase().split(" ");
                for (String s : palavrasDaMusica_i) {
                    if (s.length() == Integer.parseInt(dados[1])) {
                        // If word already exist in HashMap then increase it's count by 1
                        if (hmap.containsKey(s)) {
                            hmap.put(s, hmap.get(s) + 1);
                            hmapInverted.put(hmap.get(s) + 1, s);
                        } else {// Otherwise add word to HashMap
                            hmap.put(s, 1);
                            hmapInverted.put(1, s);
                        }
                    }
                }
            }

            int max = 0;
            String palavraMaisPopular = "";
            for (int i : hmap.values()) {
                if (i > max) {
                    max = i;
                    palavraMaisPopular = hmapInverted.get(i);
                }
            }
            return palavraMaisPopular;

//----------------------------------------------------------------------------------------------------------------------
        } else if (query.startsWith("ARTIST_MOST_UNIQUE_SONGS")) {
            String[] dados = query.split(" ", 2);
            int anoAMUS = Integer.parseInt(dados[1]);
            String resultado = "";
            HashMap<String, TemaMusical> musicasUnicas = new HashMap<String, TemaMusical>();
            HashMap<String, Integer> artistaNumMusicas = new HashMap<String, Integer>();
            for (TemaMusical tm : listaIDMusica.values()) {
                if (tm.ano == anoAMUS) {
                    musicasUnicas.put(tm.titulo, tm);
                }
            }

            for (TemaMusical tm : musicasUnicas.values()) {
                for (Artista a : tm.artistasEnvolvidos) {
                    if (artistaNumMusicas.containsKey(a.nome)) {
                        artistaNumMusicas.put(a.nome, artistaNumMusicas.get(a.nome) + 1);
                    } else {
                        artistaNumMusicas.put(a.nome, 1);
                    }
                }
            }
            if (artistaNumMusicas.size() == 0) {
                return "N/A";
            }

            int max = 0;
            for (String s : artistaNumMusicas.keySet()) {
                if (artistaNumMusicas.get(s) > max) {
                    max = artistaNumMusicas.get(s);
                    resultado = s;
                }
            }
            return resultado;

//----------------------------------------------------------------------------------------------------------------------
        } else if (query.startsWith("TOP_N_SIMILAR_ARTISTS")) {
            String[] dados = query.split(" ", 2);
            int numeroTop = Integer.parseInt(dados[1]);
            StringBuilder resultado = new StringBuilder();
            HashMap<String, Integer> artistaNumDeSemelhantes = new HashMap<String, Integer>();

            for (Artista a : listaArtistas.values()) {
                artistaNumDeSemelhantes.put(a.nome, a.artistasSemelhantes.size());
            }

            List<String> artistasSemNome = new ArrayList<>(artistaNumDeSemelhantes.keySet());
            List<Integer> artistasSemNumero = new ArrayList<>(artistaNumDeSemelhantes.values());

            Collections.sort(artistasSemNome);
            artistasSemNumero.sort(Collections.reverseOrder());

            int countTNSA = 0;
            boolean fim = false;

            String anterior = "";

            while (countTNSA < numeroTop) {
                for (String s : artistasSemNome) {
                    if (artistasSemNumero.get(countTNSA).equals(artistaNumDeSemelhantes.get(s)) && !s.equals(anterior)) {
                        if (countTNSA == numeroTop - 1) {
                            resultado.append(s).append(":").append(artistasSemNumero.get(countTNSA));
                            fim = true;
                            break;
                        } else {
                            resultado.append(s).append(":").append(artistasSemNumero.get(countTNSA)).append("\n");
                            countTNSA++;
                            anterior = s;
                            break;
                        }
                    }
                }

                if (fim) {
                    break;
                }
            }
            return resultado.toString();

//----------------------------------------------------------------------------------------------------------------------
        } else if (query.startsWith("WHY_SO_SIMILAR?")) {
            String[] dados = query.split(" ", 3);
            String resultado;
            HashSet<String> hset = new HashSet<>();
            ArrayList<String> nomesValidados = new ArrayList<String>();
            for (int i = 0; i < listaIDArtistas.get(dados[1]).artistasSemelhantes.size(); i++) {
                hset.add(listaIDArtistas.get(dados[1]).artistasSemelhantes.get(i).nome);
            }
            for (int j = 0; j < listaIDArtistas.get(dados[2]).artistasSemelhantes.size(); j++) {
                String artistaSemelhante = listaIDArtistas.get(dados[2]).artistasSemelhantes.get(j).nome;//FIXME indexoutofbound 9 : 9 <--- já deve funcionar
                if (hset.contains(artistaSemelhante)) {
                    nomesValidados.add(artistaSemelhante);
                }
            }
            Collections.sort(nomesValidados);

            if (nomesValidados.size() != 0) {
                resultado = String.join("\n", nomesValidados);
                return resultado;
            } else {
                return "N/A";
            }

//----------------------------------------------------------------------------------------------------------------------
        } else if (query.startsWith("COUNT_SONGS_BY_ARTIST_WITH_RESTRICTIONS")) {
            String[] dados = query.split(" ", 2);
            String verdOuFalso = dados[1].substring(dados[1].length() - 4);
            //System.out.println(verdOuFalso);
            if (verdOuFalso.equals("true")) {
                String artistaCSBAWR = dados[1].substring(0, dados[1].lastIndexOf("t") - 1);
                //System.out.println(artistaCSBAWR);

                int countCSBAWR = 0;
                for (TemaMusical tm : album) {
                    if (tm.artistasEnvolvidos.size() == 1) {
                        if (artistaCSBAWR.equals(tm.artistasEnvolvidos.get(0).nome)) {
                            countCSBAWR++;
                        }
                    }
                }

                return String.valueOf(countCSBAWR);
            } else {
                String artistaCSBAWR = dados[1].substring(0, dados[1].lastIndexOf("f") - 1);
                int countCSBAWR = 0;
                for (TemaMusical tm : album) {
                    for (Artista a : tm.artistasEnvolvidos) {
                        if (artistaCSBAWR.equals(a.nome)) {
                            countCSBAWR++;
                        }
                    }
                }

                return String.valueOf(countCSBAWR);
            }

//----------------------------------------------------------------------------------------------------------------------
        } else if (query.startsWith("SONGS_WITH_SIMILAR_ARTISTS?")) {
            String[] dados = query.split(" ", 3);

            if (listaIDMusica.containsKey(dados[1]) && listaIDMusica.containsKey(dados[2])) {
                for (int i = 0; i < listaIDMusica.get(dados[1]).artistasEnvolvidos.size(); i++) {
                    String idDoPrimeiroArtita = listaIDMusica.get(dados[1]).artistasEnvolvidos.get(i).idArt;
                    for (int j = 0; j < listaIDMusica.get(dados[2]).artistasEnvolvidos.size(); j++) {
                        if (listaIDMusica.get(dados[2]).artistasEnvolvidos.get(j).idArt.equals(idDoPrimeiroArtita)) {
                            return "YAY";
                        }
                    }
                }

                return "NAY";
            } else {
                return "N/A";
            }

//----------------------------------------------------------------------------------------------------------------------
        } else if (query.startsWith("INSERT_SONG")) {
            String[] partes = query.split(" ", 2);
            if (!partes[1].equals("")) {
                String[] dados = partes[1].split(",", 5);
                String ID = dados[0];
                String titulo = dados[1];
                if (!dados[2].equals("")) {
                    String ano = dados[2];
                    String nomeArt = dados[3];
                    if (listaArtistas.containsKey(nomeArt)) {
                        TemaMusical musica = new TemaMusical();
                        musica.id = ID;
                        musica.titulo = titulo;
                        musica.ano = Integer.parseInt(ano);

                        //adiciono 1 ao numero de musicas do artista e adiciono o ID da musica
                        listaArtistas.get(nomeArt).numeroDeMusicas++;
                        listaArtistas.get(nomeArt).idmusicas.add(ID);

                        listaArtista_e_Musicas.get(nomeArt).add(musica);
                        //adicona o artista à musica apartir do dicionario
                        musica.artistasEnvolvidos.add(listaArtistas.get(nomeArt));

                        //adiciona a musica ao dicionario;
                        listaIDMusica.put(ID, musica);

                        //adiciona ao album a musica;
                        album.add(musica);

                        return "OK";
                    } else {
                        return "Erro";
                    }
                } else {
                    return "Erro";
                }
            } else {
                return "Erro";
            }

//----------------------------------------------------------------------------------------------------------------------
        } else if (query.startsWith("REMOVE_SONG")) {
            String[] partes = query.split(" ", 2);
            String idMusica = partes[1];
            ArrayList<String> musicasDoArtista;
            boolean removido = false;
            for (String s : listaArtistas.keySet()) {
                musicasDoArtista = listaArtistas.get(s).idmusicas;
                if (musicasDoArtista.contains(idMusica)) {

                    TemaMusical tm = listaIDMusica.get(idMusica);
                    listaArtistas.get(s).idmusicas.remove(idMusica);
                    listaArtistas.get(s).numeroDeMusicas--;
                    listaArtista_e_Musicas.get(s).remove(tm);
                    listaIDMusica.remove(idMusica);
                    album.remove(tm);
                    removido = true;
                }
            }

            if (removido) {
                return "OK";
            } else {
                return "Erro";
            }

        } else if (query.equals("THE_POLICE")) {
            System.out.println(listaArtistas.get("The Police").numeroDeMusicas);
            System.out.println(listaArtistas.get("The Police").artistasSemelhantes.size());
            return "";
        } else {
            return "Query com formato inválido. Tente novamente.";
        }

    }

    static ArrayList<TemaMusical> obterTemasMusicais() {
        return album;
    }

    public static void lerFicheiros() {
        lerFicheiros("deisi_artists.csv", "deisi_songs.csv", "deisi_artist_location.csv", "deisi_artist_similarity.csv");
    }

    static void lerFicheiros(String deisi_artists, String deisi_songs, String deisi_artist_location, String deisi_artist_similarity) {

        album = new ArrayList<>();
        listaArtistas = new HashMap<>();
        listaIDArtistas = new HashMap<>();
        listaIDMusica = new HashMap<>();
        //---------------------------------------------deisi_artists-----------------------------------------------
        BufferedReader leitorFicheiro = null;

        long start = System.currentTimeMillis();
        try {
            File ficheiro = new File(deisi_artists);
            leitorFicheiro = new BufferedReader(new FileReader(ficheiro)); //mudar para buffer qualquer coisa

            //enquanto o ficheiro tiver linhas não lidas
            String linha;
            while ((linha = leitorFicheiro.readLine()) != null) {

                //partir a linha no caractere separador
                String dados[] = linha.split(",");

                if (dados.length == 2) {
                    String ID = dados[0];
                    String nome = dados[1];

                    //Preencher Class
                    if (!listaArtistas.containsKey(nome)) {
                        Artista artista = new Artista();
                        artista.idArt = ID;
                        artista.nome = nome;
                        artista.numeroDeMusicas = 0;

                        //adiciona o artista aos dicionarios por nome e por ID
                        listaArtistas.put(nome, artista);
                        listaIDArtistas.put(ID, artista);
                        listaArtista_e_Musicas.put(nome, new ArrayList<TemaMusical>());
                    }
                }
            }
            leitorFicheiro.close();
        } catch (IOException e) {
            String mensagem = "Erro: o ficheiro " + deisi_artists + " nao foi encontrado.";
            System.out.println(mensagem);
            e.printStackTrace();
        }

//------------------------------------------deisi_songs--------------------------------------

        long end1 = System.currentTimeMillis();
        System.out.println("PARTE 1 demorou " + (end1 - start) + "ms");
        leitorFicheiro = null;

        try {
            File ficheiro = new File(deisi_songs);
            leitorFicheiro = new BufferedReader(new FileReader(ficheiro)); //mudar para buffer qualquer coisa

            //enquanto o ficheiro tiver linhas não lidas
            String linha;
            while ((linha = leitorFicheiro.readLine()) != null) {

                //partir a linha no caractere separador
                String dados[] = linha.split(",");
                if (dados.length == 4) {
                    String arrayArt[] = dados[3].split(";");

                    //APENAS UM ARTISTA NUMA MUSICA-------------------------------------------------
                    if (arrayArt.length <= 1) {
                        String ID = dados[0];
                        String song = dados[1];
                        String nome = dados[3];
                        int ano;

                        if (dados[2].equals("") || dados[2].equals(" ")) {
                            dados[2] = " ";
                            ano = 0;
                        } else {
                            try {
                                ano = Integer.parseInt(dados[2]);
                            } catch (NumberFormatException e) {
                                ano = 2;
                            }
                        }
                        //verifica se existe no dicionário
                        if (listaArtistas.containsKey(nome)) {

                            //Preencher Class
                            TemaMusical musica = new TemaMusical();
                            musica.id = ID;
                            musica.titulo = song;
                            musica.ano = ano;

                            //adiciono 1 ao numero de musicas do artista e adiciono o ID da musica
                            listaArtistas.get(nome).numeroDeMusicas++;
                            listaArtistas.get(nome).idmusicas.add(ID);

                            listaArtista_e_Musicas.get(nome).add(musica);
                            //adicona o artista à musica apartir do dicionario
                            musica.artistasEnvolvidos.add(listaArtistas.get(nome));

                            //adiciona a musica ao dicionario;
                            listaIDMusica.put(ID, musica);


                            //adiciona ao album a musica;
                            album.add(musica);
                        }
                        //VARIOS ARTISTAS NUMA MUSICA----------------------------------------------
                    } else if (dados[3].length() > 1) {
                        String ID = dados[0];
                        String song = dados[1];
                        int ano;
                        if (dados[2].equals("") || dados[2].equals(" ")) {
                            dados[2] = " ";
                            ano = 0;
                        } else {
                            try {
                                ano = Integer.parseInt(dados[2]);
                            } catch (NumberFormatException e) {
                                ano = 2;
                            }
                        }

                        TemaMusical musica = new TemaMusical();
                        musica.id = ID;
                        musica.titulo = song;
                        musica.ano = ano;

                        int countArtVal = 0;

                        for (String s : arrayArt) {
                            if (listaArtistas.containsKey(s)) {
                                //adiciono 1 ao numero de musicas do artista e adiciono o ID da musica
                                listaArtistas.get(s).numeroDeMusicas++;
                                listaArtistas.get(s).idmusicas.add(ID);

                                listaArtista_e_Musicas.get(s).add(musica);
                                //adicona o artista à musica apartir do dicionario
                                musica.artistasEnvolvidos.add(listaArtistas.get(s));

                                //adiciona um para saber se no fim se pode adicionar a musica ao album
                                countArtVal++;
                            }
                        }



                        if (countArtVal > 0) {

                            //adiciona a musica ao dicionario;
                            listaIDMusica.put(ID, musica);
                            //adiciona ao album a musica;
                            album.add(musica);
                        }
                    }
                }
            }
            leitorFicheiro.close();
        } catch (IOException e) {
            String mensagem = "Erro: o ficheiro " + deisi_songs + " nao foi encontrado.";
            System.out.println(mensagem);
            e.printStackTrace();
        }

        long end2 = System.currentTimeMillis();
        System.out.println("PARTE 2 demorou " + (end2 - end1) + "ms");
//----------------------------------deisi_artist_location----------------------------------
        leitorFicheiro = null;

        try {
            File ficheiro = new File(deisi_artist_location);
            leitorFicheiro = new BufferedReader(new FileReader(ficheiro)); //mudar para buffer qualquer coisa

            //enquanto o ficheiro tiver linhas não lidas
            String linha;
            while ((linha = leitorFicheiro.readLine()) != null) {

                //partir a linha no caractere separador
                String dados[] = linha.split(",", 2);

                String ID = dados[0];
                String Localizacao = dados[1];

                //Preencher Class
                if (dados.length == 2) {
                    listaIDArtistas.get(ID).localizacao = Localizacao;
                }

            }
            leitorFicheiro.close();
        } catch (IOException e) {
            String mensagem = "Erro: o ficheiro " + deisi_artist_location + " nao foi encontrado.";
            System.out.println(mensagem);
            e.printStackTrace();
        }

        long end3 = System.currentTimeMillis();
        System.out.println("PARTE 3 demorou " + (end3 - end2) + "ms");
//---------------------------------deisi_artist_similarity---------------------------------------
        leitorFicheiro = null;

        try {
            File ficheiro = new File(deisi_artist_similarity);
            leitorFicheiro = new BufferedReader(new FileReader(ficheiro)); //mudar para buffer qualquer coisa

            //enquanto o ficheiro tiver linhas não lidas
            String linha;
            while ((linha = leitorFicheiro.readLine()) != null) {

                //partir a linha no caractere separador
                String[] dados = linha.split(",");
                String ID1 = dados[0];
                String ID2 = dados[1];

                Artista artistaID1 = new Artista();
                Artista artistaID2 = new Artista();

                int countArt1e2 = 0;

                listaIDArtistas.get(ID1).artistasSemelhantes.add(listaIDArtistas.get(ID2));
            }
            leitorFicheiro.close();
        } catch (IOException e) {
            String mensagem = "Erro: o ficheiro " + deisi_artist_similarity + " nao foi encontrado.";
            System.out.println(mensagem);
            e.printStackTrace();
        }

        long end4 = System.currentTimeMillis();
        System.out.println("PARTE 4 demorou " + (end4 - end3) + "ms");
    }
}