package pt.ulusofona.aed.songsExpert;
import java.util.ArrayList;
public class Artista {
    String idArt;
    String nome;
    String localizacao;
    ArrayList<Artista> artistasSemelhantes = new ArrayList<>();
    int numeroDeMusicas;
    ArrayList<String> idmusicas = new ArrayList<>();

    public Artista(String ID, String Nome, String Localizacao, ArrayList<Artista> artistasSemelhantes){
        this.idArt = ID;
        this.nome = Nome;
        this.localizacao = Localizacao;
        this.artistasSemelhantes = artistasSemelhantes;
        //this.numeroDeMusicas = numeroDeMusicas;
        this.idmusicas = idmusicas;
    }

    public Artista(){
    }
}
